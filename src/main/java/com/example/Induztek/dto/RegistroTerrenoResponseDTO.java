package com.example.Induztek.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegistroTerrenoResponseDTO {
    @NotBlank
    private String id;

    @NotBlank
    private String clientId;

    @NotBlank
    private String clientName;

    @NotBlank
    private String facilityId;

    @NotBlank
    private String facilityName;

    @NotBlank
    private String equipmentTypeId;

    @NotBlank
    private String equipmentTypeName;

    @NotBlank
    private String technicianId;

    @NotBlank
    private String technicianUsername;

    @NotBlank
    private String technicianName;

    @NotBlank
    private String codEquipo;

    @NotNull
    private LocalDateTime createdAt;

    private String photoUrl;

    private boolean archived;

    @NotNull
    private List<DetalleMantenimientoDTO> maintenanceItems;

    private PruebaElectricaDTO electricalTest;

    private String generalObservations;

    public RegistroTerrenoResponseDTO() {}

    public RegistroTerrenoResponseDTO(String id, String clientId, String clientName, String facilityId,
                                      String facilityName, String equipmentTypeId, String equipmentTypeName,
                                      String technicianId,
                                      String technicianUsername,
                                      String technicianName,
                                      String codEquipo, LocalDateTime createdAt,
                                      String photoUrl, boolean archived,
                                      List<DetalleMantenimientoDTO> maintenanceItems,
                                      PruebaElectricaDTO electricalTest, String generalObservations) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.equipmentTypeId = equipmentTypeId;
        this.equipmentTypeName = equipmentTypeName;
        this.technicianId = technicianId;
        this.technicianUsername = technicianUsername;
        this.technicianName = technicianName;
        this.codEquipo = codEquipo;
        this.createdAt = createdAt;
        this.photoUrl = photoUrl;
        this.archived = archived;
        this.maintenanceItems = maintenanceItems;
        this.electricalTest = electricalTest;
        this.generalObservations = generalObservations;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public String getFacilityId() { return facilityId; }
    public void setFacilityId(String facilityId) { this.facilityId = facilityId; }
    public String getFacilityName() { return facilityName; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }
    public String getEquipmentTypeId() { return equipmentTypeId; }
    public void setEquipmentTypeId(String equipmentTypeId) { this.equipmentTypeId = equipmentTypeId; }
    public String getEquipmentTypeName() { return equipmentTypeName; }
    public void setEquipmentTypeName(String equipmentTypeName) { this.equipmentTypeName = equipmentTypeName; }
    public String getTechnicianId() { return technicianId; }
    public void setTechnicianId(String technicianId) { this.technicianId = technicianId; }
    public String getTechnicianUsername() { return technicianUsername; }
    public void setTechnicianUsername(String technicianUsername) { this.technicianUsername = technicianUsername; }
    public String getTechnicianName() { return technicianName; }
    public void setTechnicianName(String technicianName) { this.technicianName = technicianName; }
    public String getCodEquipo() { return codEquipo; }
    public void setCodEquipo(String codEquipo) { this.codEquipo = codEquipo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }
    public List<DetalleMantenimientoDTO> getMaintenanceItems() { return maintenanceItems; }
    public void setMaintenanceItems(List<DetalleMantenimientoDTO> maintenanceItems) { this.maintenanceItems = maintenanceItems; }
    public PruebaElectricaDTO getElectricalTest() { return electricalTest; }
    public void setElectricalTest(PruebaElectricaDTO electricalTest) { this.electricalTest = electricalTest; }
    public String getGeneralObservations() { return generalObservations; }
    public void setGeneralObservations(String generalObservations) { this.generalObservations = generalObservations; }
}