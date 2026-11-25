package com.example.Induztek.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.Induztek.dto.ClienteDTO;
import com.example.Induztek.dto.InstalacionDTO;
import com.example.Induztek.dto.TipoEquipoDTO;
import com.example.Induztek.model.Cliente;
import com.example.Induztek.model.Instalacion;
import com.example.Induztek.model.TipoEquipo;
import com.example.Induztek.repository.ClienteRepository;
import com.example.Induztek.repository.InstalacionRepository;
import com.example.Induztek.repository.TipoEquipoRepository;

@Service
public class ClienteCatalogoService {

    private final ClienteRepository clienteRepository;
    private final InstalacionRepository instalacionRepository;
    private final TipoEquipoRepository tipoEquipoRepository;

    public ClienteCatalogoService(ClienteRepository clienteRepository,
                                  InstalacionRepository instalacionRepository,
                                  TipoEquipoRepository tipoEquipoRepository) {
        this.clienteRepository = clienteRepository;
        this.instalacionRepository = instalacionRepository;
        this.tipoEquipoRepository = tipoEquipoRepository;
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> findAllClientes() {
        return clienteRepository.findAllByOrderByNameAsc().stream()
                .map(this::toClienteDTO)
                .toList();
    }


    @Transactional
    public ClienteDTO createCliente(ClienteDTO dto) {
        String id = (dto.getId() != null && !dto.getId().isBlank()) ? dto.getId() : UUID.randomUUID().toString();
        Cliente cliente = new Cliente(id, dto.getName().trim());
        Cliente guardado = clienteRepository.save(cliente);

        if (dto.getFacilities() != null) {
            for (InstalacionDTO insDto : dto.getFacilities()) {
                guardarInstalacionConTipos(guardado, insDto);
            }
        }

        Cliente recargado = clienteRepository.findById(guardado.getId()).orElse(guardado);
        return toClienteDTO(recargado);
    }

    @Transactional
    public ClienteDTO updateCliente(String id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        cliente.setName(dto.getName().trim());
        Cliente guardado = clienteRepository.save(cliente);

        if (dto.getFacilities() != null && !dto.getFacilities().isEmpty()) {
            // Reemplaza instalaciones y tipos de equipo existentes del cliente
            List<Instalacion> existentes = instalacionRepository.findByClientId(guardado.getId());
            for (Instalacion ins : existentes) {
                instalacionRepository.deleteById(ins.getId());
            }
            instalacionRepository.flush();

            for (InstalacionDTO insDto : dto.getFacilities()) {
                guardarInstalacionConTipos(guardado, insDto);
            }
        }

        Cliente recargado = clienteRepository.findById(guardado.getId()).orElse(guardado);
        return toClienteDTO(recargado);
    }

    private void guardarInstalacionConTipos(Cliente cliente, InstalacionDTO insDto) {
        String insId = (insDto.getId() != null && !insDto.getId().isBlank())
                ? insDto.getId()
                : UUID.randomUUID().toString();

        Double lat = insDto.getLatitude() != null ? insDto.getLatitude() : 0.0;
        Double lon = insDto.getLongitude() != null ? insDto.getLongitude() : 0.0;

        Instalacion instalacion = new Instalacion(
                insId,
                cliente,
                insDto.getName().trim(),
                lat,
                lon
        );

        Instalacion insGuardada = instalacionRepository.save(instalacion);

        if (insDto.getEquipmentTypes() != null) {
            for (TipoEquipoDTO tipoDto : insDto.getEquipmentTypes()) {
                String tipoId = (tipoDto.getId() != null && !tipoDto.getId().isBlank())
                        ? tipoDto.getId()
                        : UUID.randomUUID().toString();
                TipoEquipo tipo = new TipoEquipo(tipoId, insGuardada, tipoDto.getName().trim());
                tipoEquipoRepository.save(tipo);
            }
        }
    }

    @Transactional
    public void deleteCliente(String id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<InstalacionDTO> findInstalacionesByCliente(String clientId) {
        return instalacionRepository.findByClientId(clientId).stream()
                .map(this::toInstalacionDTO)
                .toList();
    }


    @Transactional
    public InstalacionDTO createInstalacion(InstalacionDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        String id = (dto.getId() != null && !dto.getId().isBlank()) ? dto.getId() : UUID.randomUUID().toString();
        Instalacion instalacion = new Instalacion(
                id, cliente, dto.getName().trim(), dto.getLatitude(), dto.getLongitude()
        );
        return toInstalacionDTO(instalacionRepository.save(instalacion));
    }

    @Transactional
    public InstalacionDTO updateInstalacion(String id, InstalacionDTO dto) {
        Instalacion instalacion = instalacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instalación no encontrada"));
        instalacion.setName(dto.getName().trim());
        instalacion.setLatitude(dto.getLatitude());
        instalacion.setLongitude(dto.getLongitude());
        return toInstalacionDTO(instalacionRepository.save(instalacion));
    }

    @Transactional
    public void deleteInstalacion(String id) {
        if (!instalacionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instalación no encontrada");
        }
        instalacionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TipoEquipoDTO> findTiposEquipoByInstalacion(String facilityId) {
        return tipoEquipoRepository.findByFacilityId(facilityId).stream()
                .map(this::toTipoEquipoDTO)
                .toList();
    }


    @Transactional
    public TipoEquipoDTO createTipoEquipo(TipoEquipoDTO dto) {
        Instalacion instalacion = instalacionRepository.findById(dto.getFacilityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instalación no encontrada"));
        String id = (dto.getId() != null && !dto.getId().isBlank()) ? dto.getId() : UUID.randomUUID().toString();
        TipoEquipo tipo = new TipoEquipo(id, instalacion, dto.getName().trim());
        return toTipoEquipoDTO(tipoEquipoRepository.save(tipo));
    }

    @Transactional
    public void deleteTipoEquipo(String id) {
        if (!tipoEquipoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de equipo no encontrado");
        }
        tipoEquipoRepository.deleteById(id);
    }

    private ClienteDTO toClienteDTO(Cliente cliente) {
        List<InstalacionDTO> facilities = cliente.getFacilities().stream()
                .map(this::toInstalacionDTO)
                .toList();
        return new ClienteDTO(cliente.getId(), cliente.getName(), facilities);
    }

    private InstalacionDTO toInstalacionDTO(Instalacion ins) {
        List<TipoEquipoDTO> types = ins.getEquipmentTypes().stream()
                .map(this::toTipoEquipoDTO)
                .toList();
        return new InstalacionDTO(ins.getId(), ins.getClient().getId(), ins.getName(),
                ins.getLatitude(), ins.getLongitude(), types);
    }

    private TipoEquipoDTO toTipoEquipoDTO(TipoEquipo tipo) {
        return new TipoEquipoDTO(tipo.getId(), tipo.getFacility().getId(), tipo.getName());
    }
}
