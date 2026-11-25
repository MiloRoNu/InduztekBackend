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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Induztek.dto.AuthResponseDTO;
import com.example.Induztek.dto.LoginRequestDTO;
import com.example.Induztek.dto.UsuarioRequestDTO;
import com.example.Induztek.dto.UsuarioResponseDTO;
import com.example.Induztek.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthAndUsuarioController {

    private final UsuarioService usuarioService;

    public AuthAndUsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(usuarioService.login(request));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponseDTO> createUsuario(@Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(request));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(
            @PathVariable String id,
            @RequestHeader(value = "X-Current-User", defaultValue = "admin") String currentAdminUsername,
            @Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.ok(usuarioService.update(id, request, currentAdminUsername));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable String id,
            @RequestHeader(value = "X-Current-User", defaultValue = "admin") String currentAdminUsername) {
        usuarioService.delete(id, currentAdminUsername);
        return ResponseEntity.noContent().build();
    }
}
