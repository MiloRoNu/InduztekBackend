package com.example.Induztek.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HistorialCambioDTO {
    @NotBlank
    private String id;

    @NotBlank
    private String recordId;

    @NotNull
    private LocalDateTime editedAt;

    @NotBlank
    private String editedByUsername;

    @NotNull
    private List<String> changedFields;

    public HistorialCambioDTO() {}

    public HistorialCambioDTO(String id, String recordId, LocalDateTime editedAt, String editedByUsername, List<String> changedFields) {
        this.id = id;
        this.recordId = recordId;
        this.editedAt = editedAt;
        this.editedByUsername = editedByUsername;
        this.changedFields = changedFields;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }
    public LocalDateTime getEditedAt() { return editedAt; }
    public void setEditedAt(LocalDateTime editedAt) { this.editedAt = editedAt; }
    public String getEditedByUsername() { return editedByUsername; }
    public void setEditedByUsername(String editedByUsername) { this.editedByUsername = editedByUsername; }
    public List<String> getChangedFields() { return changedFields; }
    public void setChangedFields(List<String> changedFields) { this.changedFields = changedFields; }
}
