package com.example.Induztek.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class ClienteDTO {
    private String id;

    @NotBlank
    private String name;

    private List<InstalacionDTO> facilities;

    public ClienteDTO() {}

    public ClienteDTO(String id, String name, List<InstalacionDTO> facilities) {
        this.id = id;
        this.name = name;
        this.facilities = facilities;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<InstalacionDTO> getFacilities() { return facilities; }
    public void setFacilities(List<InstalacionDTO> facilities) { this.facilities = facilities; }
}