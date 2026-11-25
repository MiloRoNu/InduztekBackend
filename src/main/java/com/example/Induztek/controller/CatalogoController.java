package com.example.Induztek.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Induztek.dto.ClienteDTO;
import com.example.Induztek.dto.InstalacionDTO;
import com.example.Induztek.dto.TipoEquipoDTO;
import com.example.Induztek.service.ClienteCatalogoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CatalogoController {

    private final ClienteCatalogoService catalogoService;

    public CatalogoController(ClienteCatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    // --- Clientes ---

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDTO>> getClientes() {
        return ResponseEntity.ok(catalogoService.findAllClientes());
    }

    @PostMapping("/clientes")
    public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogoService.createCliente(dto));
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(
            @PathVariable String id,
            @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(catalogoService.updateCliente(id, dto));
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String id) {
        catalogoService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    // --- Instalaciones ---

    @GetMapping("/instalaciones")
    public ResponseEntity<List<InstalacionDTO>> getInstalacionesByCliente(@RequestParam String clientId) {
        return ResponseEntity.ok(catalogoService.findInstalacionesByCliente(clientId));
    }

    @PostMapping("/instalaciones")
    public ResponseEntity<InstalacionDTO> createInstalacion(@Valid @RequestBody InstalacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogoService.createInstalacion(dto));
    }

    @PutMapping("/instalaciones/{id}")
    public ResponseEntity<InstalacionDTO> updateInstalacion(
            @PathVariable String id,
            @Valid @RequestBody InstalacionDTO dto) {
        return ResponseEntity.ok(catalogoService.updateInstalacion(id, dto));
    }

    @DeleteMapping("/instalaciones/{id}")
    public ResponseEntity<Void> deleteInstalacion(@PathVariable String id) {
        catalogoService.deleteInstalacion(id);
        return ResponseEntity.noContent().build();
    }

    // --- Tipos de equipo ---

    @GetMapping("/tipos-equipo")
    public ResponseEntity<List<TipoEquipoDTO>> getTiposEquipoByInstalacion(@RequestParam String facilityId) {
        return ResponseEntity.ok(catalogoService.findTiposEquipoByInstalacion(facilityId));
    }

    @PostMapping("/tipos-equipo")
    public ResponseEntity<TipoEquipoDTO> createTipoEquipo(@Valid @RequestBody TipoEquipoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogoService.createTipoEquipo(dto));
    }

    @DeleteMapping("/tipos-equipo/{id}")
    public ResponseEntity<Void> deleteTipoEquipo(@PathVariable String id) {
        catalogoService.deleteTipoEquipo(id);
        return ResponseEntity.noContent().build();
    }
}
