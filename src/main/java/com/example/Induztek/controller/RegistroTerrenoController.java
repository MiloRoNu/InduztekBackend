package com.example.Induztek.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Induztek.dto.EstadoArchivoRegistroDTO;
import com.example.Induztek.dto.HistorialCambioDTO;
import com.example.Induztek.dto.RegistroComparacionDTO;
import com.example.Induztek.dto.RegistroTerrenoCreateDTO;
import com.example.Induztek.dto.RegistroTerrenoResponseDTO;
import com.example.Induztek.dto.RegistroTerrenoUpdateDTO;
import com.example.Induztek.service.RegistroTerrenoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/registros")
public class RegistroTerrenoController {
    
    private static final Logger log = LoggerFactory.getLogger(RegistroTerrenoController.class);
    private final RegistroTerrenoService registroService;

    public RegistroTerrenoController(RegistroTerrenoService registroService) {
        this.registroService = registroService;
    }

    @GetMapping
    public ResponseEntity<List<RegistroTerrenoResponseDTO>> getRegistros(
            @RequestParam(defaultValue = "false") boolean archived,
            @RequestParam(required = false) String equipmentTypeId) {
        return ResponseEntity.ok(registroService.findRegistros(archived, equipmentTypeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroTerrenoResponseDTO> getRegistroById(@PathVariable String id) {
        return ResponseEntity.ok(registroService.findById(id));
    }

    @GetMapping("/comparacion")
    public ResponseEntity<RegistroComparacionDTO> getComparacionAnterior(
            @RequestParam String facilityId,
            @RequestParam String equipmentTypeId) {
        RegistroComparacionDTO comparacion = registroService.getComparacionAnterior(facilityId, equipmentTypeId);
        if (comparacion == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comparacion);
    }

    @GetMapping("/{id}/historial")
    public ResponseEntity<List<HistorialCambioDTO>> getHistorial(@PathVariable String id) {
        return ResponseEntity.ok(registroService.getHistorial(id));
    }

    @PostMapping
    public ResponseEntity<?> createRegistro(@RequestBody RegistroTerrenoCreateDTO request) {
        log.info(
            "POST /api/registros recibido -> clientId={}, facilityId={}, equipmentTypeId={}, technicianId={}",
            request.getClientId(),
            request.getFacilityId(),
            request.getEquipmentTypeId(),
            request.getTechnicianId()
        );

        try {
            var creado = registroService.create(request);
            log.info("POST /api/registros guardado con id={}", creado);
            return ResponseEntity.ok(creado);
        } catch (Exception ex) {
            log.error("Error procesando POST /api/registros: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroTerrenoResponseDTO> updateRegistro(
            @PathVariable String id,
            @Valid @RequestBody RegistroTerrenoUpdateDTO dto) {
        return ResponseEntity.ok(registroService.update(id, dto));
    }

    @PutMapping("/archive/{id}")
    public ResponseEntity<EstadoArchivoRegistroDTO> archiveRegistro(@PathVariable String id) {
        return ResponseEntity.ok(registroService.setArchived(id, true, "ARCHIVED"));
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<EstadoArchivoRegistroDTO> restoreRegistro(@PathVariable String id) {
        return ResponseEntity.ok(registroService.setArchived(id, false, "RESTORED"));
    }
}
