package com.example.Induztek.dto;

import com.example.Induztek.enumeration.VeredictoPrueba;

import jakarta.validation.constraints.NotNull;

public class PruebaElectricaDTO {
    @NotNull
    private Double insulation;

    @NotNull
    private Double contact;

    @NotNull
    private Double voltage;

    @NotNull
    private Double current;

    @NotNull
    private VeredictoPrueba verdict;

    private String technicalNotes;

    public PruebaElectricaDTO() {}

    public PruebaElectricaDTO(Double insulation, Double contact, Double voltage, Double current, VeredictoPrueba verdict, String technicalNotes) {
        this.insulation = insulation;
        this.contact = contact;
        this.voltage = voltage;
        this.current = current;
        this.verdict = verdict;
        this.technicalNotes = technicalNotes;
    }

    public Double getInsulation() { return insulation; }
    public void setInsulation(Double insulation) { this.insulation = insulation; }
    public Double getContact() { return contact; }
    public void setContact(Double contact) { this.contact = contact; }
    public Double getVoltage() { return voltage; }
    public void setVoltage(Double voltage) { this.voltage = voltage; }
    public Double getCurrent() { return current; }
    public void setCurrent(Double current) { this.current = current; }
    public VeredictoPrueba getVerdict() { return verdict; }
    public void setVerdict(VeredictoPrueba verdict) { this.verdict = verdict; }
    public String getTechnicalNotes() { return technicalNotes; }
    public void setTechnicalNotes(String technicalNotes) { this.technicalNotes = technicalNotes; }
}