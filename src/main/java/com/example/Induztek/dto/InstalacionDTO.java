package com.example.Induztek.dto;

import java.util.List;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InstalacionDTO {
    private String id;

    @NotBlank
    private String clientId;

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitude;

    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;

    private List<TipoEquipoDTO> equipmentTypes;

    public InstalacionDTO() {}

    public InstalacionDTO(String id, String clientId, String name, Double latitude, Double longitude, List<TipoEquipoDTO> equipmentTypes) {
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.equipmentTypes = equipmentTypes;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public List<TipoEquipoDTO> getEquipmentTypes() { return equipmentTypes; }
    public void setEquipmentTypes(List<TipoEquipoDTO> equipmentTypes) { this.equipmentTypes = equipmentTypes; }
}