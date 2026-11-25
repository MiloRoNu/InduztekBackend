package com.example.Induztek.dto;

import com.example.Induztek.enumeration.EstadoComponente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DetalleMantenimientoDTO {
    @NotBlank
    private String componentName;

    @NotNull
    private EstadoComponente status;

    private String observations;

    public DetalleMantenimientoDTO() {}

    public DetalleMantenimientoDTO(String componentName, EstadoComponente status, String observations) {
        this.componentName = componentName;
        this.status = status;
        this.observations = observations;
    }

    public String getComponentName() { return componentName; }
    public void setComponentName(String componentName) { this.componentName = componentName; }
    public EstadoComponente getStatus() { return status; }
    public void setStatus(EstadoComponente status) { this.status = status; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
}