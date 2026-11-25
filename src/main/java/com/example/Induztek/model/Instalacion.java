package com.example.Induztek.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "instalaciones")
public class Instalacion {

    @Id
    @Column(length = 64, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Cliente client;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TipoEquipo> equipmentTypes = new ArrayList<>();

    public Instalacion() {}

    public Instalacion(String id, Cliente client, String name, Double latitude, Double longitude) {
        this.id = id;
        this.client = client;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Cliente getClient() { return client; }
    public void setClient(Cliente client) { this.client = client; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public List<TipoEquipo> getEquipmentTypes() { return equipmentTypes; }
    public void setEquipmentTypes(List<TipoEquipo> equipmentTypes) { this.equipmentTypes = equipmentTypes; }
}