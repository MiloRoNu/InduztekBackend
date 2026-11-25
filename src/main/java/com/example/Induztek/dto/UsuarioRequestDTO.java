package com.example.Induztek.dto;

import com.example.Induztek.enumeration.RolUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioRequestDTO {
    @NotBlank
    private String username;

    @NotBlank 
    private String name;

    private String password;

    @NotNull
    private RolUsuario role;

    public UsuarioRequestDTO() {}

    public UsuarioRequestDTO(String username, String name, String password, RolUsuario role) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public RolUsuario getRole() { return role; }
    public void setRole(RolUsuario role) { this.role = role; }
}
