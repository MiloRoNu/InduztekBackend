package com.example.Induztek.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegistroTerrenoCreateDTO {
    private String id; // Opcional si la app genera ID local/UUID al sincronizar

    @NotBlank
    private String clientId;

    @NotBlank
    private String facilityId;

    @NotBlank
    private String equipmentTypeId;

    @NotBlank
    private String technicianId;
    
    @NotBlank
    private String technicianUsername;

    @NotNull
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double userLatitude;

    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double userLongitude;

    private String codEquipo;

    private String photoUrlOrBase64;

    @NotNull
    @Size(min = 7, max = 7)
    private List<@Valid DetalleMantenimientoDTO> maintenanceItems;

    @Valid
    private PruebaElectricaDTO electricalTest;

    private String generalObservations;

    public RegistroTerrenoCreateDTO() {}

    public RegistroTerrenoCreateDTO(String id, String clientId, String facilityId, String equipmentTypeId,
                                    String technicianId,
                                    String technicianUsername, Double userLatitude, Double userLongitude,
                                    String codEquipo,
                                    String photoUrlOrBase64, List<DetalleMantenimientoDTO> maintenanceItems,
                                    PruebaElectricaDTO electricalTest, String generalObservations) {
        this.id = id;
        this.clientId = clientId;
        this.facilityId = facilityId;
        this.equipmentTypeId = equipmentTypeId;
        this.technicianId = technicianId;
        this.technicianUsername = technicianUsername;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.codEquipo = codEquipo;
        this.photoUrlOrBase64 = photoUrlOrBase64;
        this.maintenanceItems = maintenanceItems;
        this.electricalTest = electricalTest;
        this.generalObservations = generalObservations;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getFacilityId() { return facilityId; }
    public void setFacilityId(String facilityId) { this.facilityId = facilityId; }
    public String getEquipmentTypeId() { return equipmentTypeId; }
    public void setEquipmentTypeId(String equipmentTypeId) { this.equipmentTypeId = equipmentTypeId; }
    public String getTechnicianId() { return technicianId; }
    public void setTechnicianId(String technicianId) { this.technicianId = technicianId; }
    public String getTechnicianUsername() { return technicianUsername; }
    public void setTechnicianUsername(String technicianUsername) { this.technicianUsername = technicianUsername; }
    public Double getUserLatitude() { return userLatitude; }
    public void setUserLatitude(Double userLatitude) { this.userLatitude = userLatitude; }
    public Double getUserLongitude() { return userLongitude; }
    public void setUserLongitude(Double userLongitude) { this.userLongitude = userLongitude; }
    public String getPhotoUrlOrBase64() { return photoUrlOrBase64; }
    public void setPhotoUrlOrBase64(String photoUrlOrBase64) { this.photoUrlOrBase64 = photoUrlOrBase64; }
    public String getCodEquipo() { return codEquipo; }
    public void setCodEquipo(String codEquipo) { this.codEquipo = codEquipo; }
    public List<DetalleMantenimientoDTO> getMaintenanceItems() { return maintenanceItems; }
    public void setMaintenanceItems(List<DetalleMantenimientoDTO> maintenanceItems) { this.maintenanceItems = maintenanceItems; }
    public PruebaElectricaDTO getElectricalTest() { return electricalTest; }
    public void setElectricalTest(PruebaElectricaDTO electricalTest) { this.electricalTest = electricalTest; }
    public String getGeneralObservations() { return generalObservations; }
    public void setGeneralObservations(String generalObservations) { this.generalObservations = generalObservations; }
}