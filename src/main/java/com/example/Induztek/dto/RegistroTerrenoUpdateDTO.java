package com.example.Induztek.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegistroTerrenoUpdateDTO {
    @NotBlank
    private String editorUsername; // Quien realiza la edición para el historial

    private String codEquipo;

    private String photoUrlOrBase64;

    @NotNull
    @Size(min = 7, max = 7)
    private List<@Valid DetalleMantenimientoDTO> maintenanceItems;

    @NotNull
    @Valid
    private PruebaElectricaDTO electricalTest;

    public RegistroTerrenoUpdateDTO() {}

    public RegistroTerrenoUpdateDTO(String editorUsername, String photoUrlOrBase64, String codEquipo,
                                    List<DetalleMantenimientoDTO> maintenanceItems,
                                    PruebaElectricaDTO electricalTest) {
        this.editorUsername = editorUsername;
        this.photoUrlOrBase64 = photoUrlOrBase64;
        this.codEquipo = codEquipo;
        this.maintenanceItems = maintenanceItems;
        this.electricalTest = electricalTest;
    }

    public String getEditorUsername() { return editorUsername; }
    public void setEditorUsername(String editorUsername) { this.editorUsername = editorUsername; }
    public String getPhotoUrlOrBase64() { return photoUrlOrBase64; }
    public void setPhotoUrlOrBase64(String photoUrlOrBase64) { this.photoUrlOrBase64 = photoUrlOrBase64; }
    public List<DetalleMantenimientoDTO> getMaintenanceItems() { return maintenanceItems; }
    public void setMaintenanceItems(List<DetalleMantenimientoDTO> maintenanceItems) { this.maintenanceItems = maintenanceItems; }
    public PruebaElectricaDTO getElectricalTest() { return electricalTest; }
    public void setElectricalTest(PruebaElectricaDTO electricalTest) { this.electricalTest = electricalTest; }
    public String getCodEquipo() { return codEquipo; }
    public void setCodEquipo(String codEquipo) { this.codEquipo = codEquipo; }
}