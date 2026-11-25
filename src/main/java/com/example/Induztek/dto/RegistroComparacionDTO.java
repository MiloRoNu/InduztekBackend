package com.example.Induztek.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.Induztek.enumeration.VeredictoPrueba;

public class RegistroComparacionDTO {
    private String previousRecordId;
    private LocalDateTime previousCreatedAt;
    private VeredictoPrueba previousVerdict;
    private List<DetalleMantenimientoDTO> previousMaintenanceItems;
    private PruebaElectricaDTO previousElectricalTest;

    public RegistroComparacionDTO() {}

    public RegistroComparacionDTO(String previousRecordId, LocalDateTime previousCreatedAt, VeredictoPrueba previousVerdict,
                                  List<DetalleMantenimientoDTO> previousMaintenanceItems, PruebaElectricaDTO previousElectricalTest) {
        this.previousRecordId = previousRecordId;
        this.previousCreatedAt = previousCreatedAt;
        this.previousVerdict = previousVerdict;
        this.previousMaintenanceItems = previousMaintenanceItems;
        this.previousElectricalTest = previousElectricalTest;
    }

    public String getPreviousRecordId() { return previousRecordId; }
    public void setPreviousRecordId(String previousRecordId) { this.previousRecordId = previousRecordId; }
    public LocalDateTime getPreviousCreatedAt() { return previousCreatedAt; }
    public void setPreviousCreatedAt(LocalDateTime previousCreatedAt) { this.previousCreatedAt = previousCreatedAt; }
    public VeredictoPrueba getPreviousVerdict() { return previousVerdict; }
    public void setPreviousVerdict(VeredictoPrueba previousVerdict) { this.previousVerdict = previousVerdict; }
    public List<DetalleMantenimientoDTO> getPreviousMaintenanceItems() { return previousMaintenanceItems; }
    public void setPreviousMaintenanceItems(List<DetalleMantenimientoDTO> previousMaintenanceItems) { this.previousMaintenanceItems = previousMaintenanceItems; }
    public PruebaElectricaDTO getPreviousElectricalTest() { return previousElectricalTest; }
    public void setPreviousElectricalTest(PruebaElectricaDTO previousElectricalTest) { this.previousElectricalTest = previousElectricalTest; }
}