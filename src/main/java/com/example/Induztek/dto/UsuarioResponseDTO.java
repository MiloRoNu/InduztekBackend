package com.example.Induztek.dto;

import com.example.Induztek.enumeration.RolUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioResponseDTO {
    @NotBlank
    private String id;

    @NotBlank
    private String username;

    @NotBlank
    private String name;

    @NotNull
    private RolUsuario role;

    public UsuarioResponseDTO() {}

    public UsuarioResponseDTO(String id, String username, String name, RolUsuario role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public RolUsuario getRole() { return role; }
    public void setRole(RolUsuario role) { this.role = role; }
}