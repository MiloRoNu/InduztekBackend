package com.example.Induztek.dto;

import jakarta.validation.constraints.NotBlank;

public class TipoEquipoDTO {
    private String id;

    @NotBlank
    private String facilityId;

    @NotBlank
    private String name;

    public TipoEquipoDTO() {}

    public TipoEquipoDTO(String id, String facilityId, String name) {
        this.id = id;
        this.facilityId = facilityId;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFacilityId() { return facilityId; }
    public void setFacilityId(String facilityId) { this.facilityId = facilityId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}