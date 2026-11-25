package com.example.Induztek.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipos_equipo")
public class TipoEquipo {

    @Id
    @Column(length = 64, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "facility_id", nullable = false)
    private Instalacion facility;

    @Column(length = 120, nullable = false)
    private String name;

    public TipoEquipo() {}

    public TipoEquipo(String id, Instalacion facility, String name) {
        this.id = id;
        this.facility = facility;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Instalacion getFacility() { return facility; }
    public void setFacility(Instalacion facility) { this.facility = facility; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}