package com.example.Induztek.model;

import com.example.Induztek.enumeration.EstadoComponente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalles_mantenimiento")
public class DetalleMantenimiento {

    @Id
    @Column(length = 64, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false)
    private RegistroTerreno record;

    @Column(name = "component_name", length = 120, nullable = false)
    private String componentName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EstadoComponente status;

    @Column(length = 500)
    private String observations;

    public DetalleMantenimiento() {}

    public DetalleMantenimiento(String id, RegistroTerreno record, String componentName,
                                EstadoComponente status, String observations) {
        this.id = id;
        this.record = record;
        this.componentName = componentName;
        this.status = status;
        this.observations = observations;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public RegistroTerreno getRecord() { return record; }
    public void setRecord(RegistroTerreno record) { this.record = record; }
    public String getComponentName() { return componentName; }
    public void setComponentName(String componentName) { this.componentName = componentName; }
    public EstadoComponente getStatus() { return status; }
    public void setStatus(EstadoComponente status) { this.status = status; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
}
