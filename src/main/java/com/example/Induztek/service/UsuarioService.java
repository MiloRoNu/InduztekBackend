package com.example.Induztek.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.Induztek.dto.AuthResponseDTO;
import com.example.Induztek.dto.LoginRequestDTO;
import com.example.Induztek.dto.UsuarioRequestDTO;
import com.example.Induztek.dto.UsuarioResponseDTO;
import com.example.Induztek.enumeration.RolUsuario;
import com.example.Induztek.model.Usuario;
import com.example.Induztek.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        Usuario user = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        String incomingHash = sha256(request.getPassword());
        if (!incomingHash.equalsIgnoreCase(user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        String token = UUID.randomUUID().toString();
        return new AuthResponseDTO(token, user.getId(), user.getUsername(), user.getRole());
    }

    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario ya existe");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
        }

        Usuario user = new Usuario(
                UUID.randomUUID().toString(),
                request.getUsername().trim(),
                request.getName().trim(),
                sha256(request.getPassword()),
                request.getRole()
        );
        return toResponseDTO(usuarioRepository.save(user));
    }

    @Transactional
    public UsuarioResponseDTO update(String id, UsuarioRequestDTO request, String currentAdminUsername) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (user.getUsername().equals(currentAdminUsername) && request.getRole() != RolUsuario.ADMINISTRADOR) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Un administrador no puede quitarse su propio rol de administrador"
            );
        }

        user.setUsername(request.getUsername().trim());
        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName().trim());
        }
        user.setRole(request.getRole());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(sha256(request.getPassword()));
        }

        return toResponseDTO(usuarioRepository.save(user));
    }


    @Transactional
    public void delete(String id, String currentAdminUsername) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (user.getUsername().equals(currentAdminUsername)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Un administrador no puede eliminarse a sí mismo"
            );
        }

        usuarioRepository.delete(user);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario user) {
        return new UsuarioResponseDTO(user.getId(), user.getUsername(), user.getName(), user.getRole());
    }

    private String sha256(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Error al calcular SHA-256", e);
        }
    }
}
