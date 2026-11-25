package com.example.Induztek.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "registros_terreno")
public class RegistroTerreno {

    @Id
    @Column(length = 64, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Cliente client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "facility_id", nullable = false)
    private Instalacion facility;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_type_id", nullable = false)
    private TipoEquipo equipmentType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "technician_id", nullable = false)
    private Usuario technician;

    @Column(name = "user_latitude", nullable = false)
    private Double userLatitude;

    @Column(name = "user_longitude", nullable = false)
    private Double userLongitude;

    @Column(name = "cod_equipo", nullable = false)
    private String codEquipo;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(nullable = false)
    private boolean archived = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleMantenimiento> maintenanceItems = new ArrayList<>();

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private PruebaElectrica electricalTest;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialCambio> history = new ArrayList<>();

    public RegistroTerreno() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Cliente getClient() { return client; }
    public void setClient(Cliente client) { this.client = client; }
    public Instalacion getFacility() { return facility; }
    public void setFacility(Instalacion facility) { this.facility = facility; }
    public TipoEquipo getEquipmentType() { return equipmentType; }
    public void setEquipmentType(TipoEquipo equipmentType) { this.equipmentType = equipmentType; }
    public Usuario getTechnician() { return technician; }
    public void setTechnician(Usuario technician) { this.technician = technician; }
    public Double getUserLatitude() { return userLatitude; }
    public void setUserLatitude(Double userLatitude) { this.userLatitude = userLatitude; }
    public Double getUserLongitude() { return userLongitude; }
    public void setUserLongitude(Double userLongitude) { this.userLongitude = userLongitude; }
    public String getCodEquipo() { return codEquipo; }
    public void setCodEquipo(String codEquipo) { this.codEquipo = codEquipo; }
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<DetalleMantenimiento> getMaintenanceItems() { return maintenanceItems; }
    public void setMaintenanceItems(List<DetalleMantenimiento> maintenanceItems) { this.maintenanceItems = maintenanceItems; }
    public PruebaElectrica getElectricalTest() { return electricalTest; }
    public void setElectricalTest(PruebaElectrica electricalTest) { this.electricalTest = electricalTest; }
    public List<HistorialCambio> getHistory() { return history; }
    public void setHistory(List<HistorialCambio> history) { this.history = history; }
}