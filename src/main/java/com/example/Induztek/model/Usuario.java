package com.example.Induztek.model;

import com.example.Induztek.enumeration.RolUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(length = 64, nullable = false, updatable = false)
    private String id;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column (length = 100, nullable = false)
    private String name;

    @Column(name = "password_hash", length = 64, nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private RolUsuario role;

    public Usuario() {}

    public Usuario(String id, String username, String name, String passwordHash, RolUsuario role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public RolUsuario getRole() { return role; }
    public void setRole(RolUsuario role) { this.role = role; }
}