package com.example.Induztek.dto;

import com.example.Induztek.enumeration.RolUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuthResponseDTO {
    @NotBlank
    private String token;

    @NotBlank
    private String id;

    @NotBlank
    private String username;

    @NotNull
    private RolUsuario role;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, String id, String username, RolUsuario role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public RolUsuario getRole() { return role; }
    public void setRole(RolUsuario role) { this.role = role; }
}
