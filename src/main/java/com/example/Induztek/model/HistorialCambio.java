package com.example.Induztek.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historial_cambios")
public class HistorialCambio {

    @Id
    @Column(length = 64, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false)
    private RegistroTerreno record;

    @Column(name = "edited_at", nullable = false)
    private LocalDateTime editedAt;

    @Column(name = "edited_by_username", length = 100, nullable = false)
    private String editedByUsername;

    @Column(name = "changed_fields", length = 1000, nullable = false)
    private String changedFields;

    public HistorialCambio() {}

    public HistorialCambio(String id, RegistroTerreno record, LocalDateTime editedAt,
                           String editedByUsername, String changedFields) {
        this.id = id;
        this.record = record;
        this.editedAt = editedAt;
        this.editedByUsername = editedByUsername;
        this.changedFields = changedFields;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public RegistroTerreno getRecord() { return record; }
    public void setRecord(RegistroTerreno record) { this.record = record; }
    public LocalDateTime getEditedAt() { return editedAt; }
    public void setEditedAt(LocalDateTime editedAt) { this.editedAt = editedAt; }
    public String getEditedByUsername() { return editedByUsername; }
    public void setEditedByUsername(String editedByUsername) { this.editedByUsername = editedByUsername; }
    public String getChangedFields() { return changedFields; }
    public void setChangedFields(String changedFields) { this.changedFields = changedFields; }
}