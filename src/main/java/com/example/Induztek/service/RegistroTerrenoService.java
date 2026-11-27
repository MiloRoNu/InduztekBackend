package com.example.Induztek.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.Induztek.dto.DetalleMantenimientoDTO;
import com.example.Induztek.dto.EstadoArchivoRegistroDTO;
import com.example.Induztek.dto.HistorialCambioDTO;
import com.example.Induztek.dto.PruebaElectricaDTO;
import com.example.Induztek.dto.RegistroComparacionDTO;
import com.example.Induztek.dto.RegistroTerrenoCreateDTO;
import com.example.Induztek.dto.RegistroTerrenoResponseDTO;
import com.example.Induztek.dto.RegistroTerrenoUpdateDTO;
import com.example.Induztek.model.Cliente;
import com.example.Induztek.model.DetalleMantenimiento;
import com.example.Induztek.model.HistorialCambio;
import com.example.Induztek.model.Instalacion;
import com.example.Induztek.model.PruebaElectrica;
import com.example.Induztek.model.RegistroTerreno;
import com.example.Induztek.model.TipoEquipo;
import com.example.Induztek.model.Usuario;
import com.example.Induztek.repository.ClienteRepository;
import com.example.Induztek.repository.HistorialCambioRepository;
import com.example.Induztek.repository.InstalacionRepository;
import com.example.Induztek.repository.RegistroTerrenoRepository;
import com.example.Induztek.repository.TipoEquipoRepository;
import com.example.Induztek.repository.UsuarioRepository;

@Service
public class RegistroTerrenoService {

    private static final double MAX_DISTANCE_METERS = 100.0;
    private static final double EARTH_RADIUS_METERS = 6_371_000.0;

    private final RegistroTerrenoRepository registroRepository;
    private final ClienteRepository clienteRepository;
    private final InstalacionRepository instalacionRepository;
    private final TipoEquipoRepository tipoEquipoRepository;
    private final UsuarioRepository usuarioRepository;
    private final HistorialCambioRepository historialRepository;

    public RegistroTerrenoService(RegistroTerrenoRepository registroRepository,
                                  ClienteRepository clienteRepository,
                                  InstalacionRepository instalacionRepository,
                                  TipoEquipoRepository tipoEquipoRepository,
                                  UsuarioRepository usuarioRepository,
                                  HistorialCambioRepository historialRepository) {
        this.registroRepository = registroRepository;
        this.clienteRepository = clienteRepository;
        this.instalacionRepository = instalacionRepository;
        this.tipoEquipoRepository = tipoEquipoRepository;
        this.usuarioRepository = usuarioRepository;
        this.historialRepository = historialRepository;
    }

    @Transactional(readOnly = true)
    public List<RegistroTerrenoResponseDTO> findRegistros(boolean archived, String equipmentTypeId) {
        List<RegistroTerreno> list = (equipmentTypeId != null && !equipmentTypeId.isBlank())
                ? registroRepository.findByArchivedAndEquipmentTypeIdOrderByCreatedAtDesc(archived, equipmentTypeId)
                : registroRepository.findByArchivedOrderByCreatedAtDesc(archived);

        return list.stream().map(this::toResponseDTO).toList();
    }

    public RegistroTerrenoResponseDTO findById(String id) {
        RegistroTerreno reg = registroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
        return toResponseDTO(reg);
    }

    public RegistroComparacionDTO getComparacionAnterior(String facilityId, String equipmentTypeId) {
        return registroRepository
                .findFirstByFacilityIdAndEquipmentTypeIdAndArchivedFalseOrderByCreatedAtDesc(facilityId, equipmentTypeId)
                .map(prev -> {
                    List<DetalleMantenimientoDTO> items = prev.getMaintenanceItems().stream()
                            .map(d -> new DetalleMantenimientoDTO(d.getComponentName(), d.getStatus(), d.getObservations()))
                            .toList();
                    PruebaElectrica pe = prev.getElectricalTest();
                    PruebaElectricaDTO testDto = pe != null
                            ? new PruebaElectricaDTO(pe.getInsulation(), pe.getContact(), pe.getVoltage(), pe.getCurrent(), pe.getVerdict(), pe.getTechnicalNotes())
                            : null;
                    return new RegistroComparacionDTO(
                            prev.getId(),
                            prev.getCreatedAt(),
                            pe != null ? pe.getVerdict() : null,
                            items,
                            testDto
                    );
                })
                .orElse(null);
    }

        @Transactional
        public RegistroTerrenoResponseDTO create(RegistroTerrenoCreateDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        Instalacion instalacion = instalacionRepository.findById(dto.getFacilityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instalación no encontrada"));
        TipoEquipo tipoEquipo = tipoEquipoRepository.findById(dto.getEquipmentTypeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de equipo no encontrado"));
        Usuario tecnico = usuarioRepository.findById(dto.getTechnicianId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Técnico no encontrado"));

        if (!instalacion.getClient().getId().equals(cliente.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La instalación no pertenece al cliente indicado");
        }
        if (!tipoEquipo.getFacility().getId().equals(instalacion.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de equipo no pertenece a la instalación indicada");
        }

        double distance = distanceMeters(
                dto.getUserLatitude(), dto.getUserLongitude(),
                instalacion.getLatitude(), instalacion.getLongitude()
        );
        if (distance > MAX_DISTANCE_METERS) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("Debe estar a 100 m o menos de la instalación (distancia actual: %.1f m)", distance)
                );
        }

        String recordId = (dto.getId() != null && !dto.getId().isBlank()) ? dto.getId() : UUID.randomUUID().toString();
        RegistroTerreno registro = new RegistroTerreno();
        registro.setId(recordId);
        registro.setClient(cliente);
        registro.setFacility(instalacion);
        registro.setEquipmentType(tipoEquipo);
        registro.setTechnician(tecnico);
        registro.setUserLatitude(dto.getUserLatitude());
        registro.setUserLongitude(dto.getUserLongitude());
        registro.setCodEquipo(dto.getCodEquipo());
        registro.setPhotoUrl(dto.getPhotoUrlOrBase64());
        registro.setArchived(false);
        registro.setCreatedAt(LocalDateTime.now());

        List<DetalleMantenimientoDTO> itemsDto = dto.getMaintenanceItems() != null
                ? dto.getMaintenanceItems()
                : List.of();
        List<DetalleMantenimiento> detalles = itemsDto.stream()
                .map(item -> new DetalleMantenimiento(
                        UUID.randomUUID().toString(),
                        registro,
                        item.getComponentName(),
                        item.getStatus(),
                        item.getObservations()
                ))
                .toList();
        registro.setMaintenanceItems(new ArrayList<>(detalles));

        PruebaElectricaDTO testDto = dto.getElectricalTest();
        if (testDto != null) {
                PruebaElectrica prueba = new PruebaElectrica(
                        UUID.randomUUID().toString(),
                        registro,
                        testDto.getInsulation(),
                        testDto.getContact(),
                        testDto.getVoltage(),
                        testDto.getCurrent(),
                        testDto.getVerdict(),
                        testDto.getTechnicalNotes()
                );
                registro.setElectricalTest(prueba);
        }

        return toResponseDTO(registroRepository.save(registro));
        }


    @Transactional
    public RegistroTerrenoResponseDTO update(String id, RegistroTerrenoUpdateDTO dto) {
        RegistroTerreno registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado"));

        List<String> changedFields = new ArrayList<>();

        if (!Objects.equals(registro.getPhotoUrl(), dto.getPhotoUrlOrBase64())) {
            changedFields.add("photoUrl");
            registro.setPhotoUrl(dto.getPhotoUrlOrBase64());
        }

        PruebaElectrica prueba = registro.getElectricalTest();
        PruebaElectricaDTO nuevoTest = dto.getElectricalTest();
        if (prueba != null && nuevoTest != null) {
            if (!Objects.equals(prueba.getInsulation(), nuevoTest.getInsulation())) {
                changedFields.add("insulation");
                prueba.setInsulation(nuevoTest.getInsulation());
            }
            if (!Objects.equals(prueba.getContact(), nuevoTest.getContact())) {
                changedFields.add("contact");
                prueba.setContact(nuevoTest.getContact());
            }
            if (!Objects.equals(prueba.getVoltage(), nuevoTest.getVoltage())) {
                changedFields.add("voltage");
                prueba.setVoltage(nuevoTest.getVoltage());
            }
            if (!Objects.equals(prueba.getCurrent(), nuevoTest.getCurrent())) {
                changedFields.add("current");
                prueba.setCurrent(nuevoTest.getCurrent());
            }
            if (!Objects.equals(prueba.getVerdict(), nuevoTest.getVerdict())) {
                changedFields.add("verdict");
                prueba.setVerdict(nuevoTest.getVerdict());
            }
        }

        changedFields.add("maintenanceItems");
        registro.getMaintenanceItems().clear();
        for (DetalleMantenimientoDTO item : dto.getMaintenanceItems()) {
            registro.getMaintenanceItems().add(new DetalleMantenimiento(
                    UUID.randomUUID().toString(),
                    registro,
                    item.getComponentName(),
                    item.getStatus(),
                    item.getObservations()
            ));
        }

        HistorialCambio log = new HistorialCambio(
                UUID.randomUUID().toString(),
                registro,
                LocalDateTime.now(),
                dto.getEditorUsername(),
                String.join(", ", changedFields)
        );
        registro.getHistory().add(log);

        return toResponseDTO(registroRepository.save(registro));
    }

    @Transactional
    public EstadoArchivoRegistroDTO setArchived(String id, boolean archived, String actionLabel) {
        RegistroTerreno registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
        registro.setArchived(archived);
        registroRepository.save(registro);
        return new EstadoArchivoRegistroDTO(registro.getId(), registro.isArchived(), actionLabel, LocalDateTime.now());
    }

    public List<HistorialCambioDTO> getHistorial(String recordId) {
        if (!registroRepository.existsById(recordId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado");
        }
        return historialRepository.findByRecordIdOrderByEditedAtDesc(recordId).stream()
                .map(h -> new HistorialCambioDTO(
                        h.getId(),
                        h.getRecord().getId(),
                        h.getEditedAt(),
                        h.getEditedByUsername(),
                        Arrays.asList(h.getChangedFields().split("\\s*,\\s*"))
                ))
                .toList();
    }

    private RegistroTerrenoResponseDTO toResponseDTO(RegistroTerreno reg) {
        List<DetalleMantenimientoDTO> items = reg.getMaintenanceItems().stream()
                .map(d -> new DetalleMantenimientoDTO(d.getComponentName(), d.getStatus(), d.getObservations()))
                .toList();
        PruebaElectrica pe = reg.getElectricalTest();
        PruebaElectricaDTO testDto = pe != null
                ? new PruebaElectricaDTO(pe.getInsulation(), pe.getContact(), pe.getVoltage(), pe.getCurrent(), pe.getVerdict(), pe.getTechnicalNotes())
                : null;

        return new RegistroTerrenoResponseDTO(
                reg.getId(),
                reg.getClient().getId(),
                reg.getClient().getName(),
                reg.getFacility().getId(),
                reg.getFacility().getName(),
                reg.getEquipmentType().getId(),
                reg.getEquipmentType().getName(),
                reg.getTechnician().getId(),
                reg.getTechnician().getUsername(),
                reg.getTechnician().getName(),
                reg.getCodEquipo(),
                reg.getCreatedAt(),
                reg.getPhotoUrl(),
                reg.isArchived(),
                items,
                testDto,
                null
        );
    }

    private double distanceMeters(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_METERS * c;
    }
}
