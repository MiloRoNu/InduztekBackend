package com.example.Induztek.model;

import com.example.Induztek.enumeration.VeredictoPrueba;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pruebas_electricas")
public class PruebaElectrica {

    @Id
    @Column(length = 64, nullable = false, updatable = false)
    private String id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false, unique = true)
    private RegistroTerreno record;

    @Column(nullable = false)
    private Double insulation;

    @Column(nullable = false)
    private Double contact;

    @Column(nullable = false)
    private Double voltage;

    @Column(nullable = false)
    private Double current;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private VeredictoPrueba verdict;

    @Column(nullable = true)
    private String technicalNotes;

    public PruebaElectrica() {}

    public PruebaElectrica(String id, RegistroTerreno record, Double insulation, Double contact,
                           Double voltage, Double current, VeredictoPrueba verdict, String technicalNotes) {
        this.id = id;
        this.record = record;
        this.insulation = insulation;
        this.contact = contact;
        this.voltage = voltage;
        this.current = current;
        this.verdict = verdict;
        this.technicalNotes = technicalNotes;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public RegistroTerreno getRecord() { return record; }
    public void setRecord(RegistroTerreno record) { this.record = record; }
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
