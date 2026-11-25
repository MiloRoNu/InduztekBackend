package com.example.Induztek.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @Column(length = 64, nullable = false, updatable = false)
    private String id;

    @Column(length = 150, nullable = false)
    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Instalacion> facilities = new ArrayList<>();

    public Cliente() {}

    public Cliente(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Instalacion> getFacilities() { return facilities; }
    public void setFacilities(List<Instalacion> facilities) { this.facilities = facilities; }
}

