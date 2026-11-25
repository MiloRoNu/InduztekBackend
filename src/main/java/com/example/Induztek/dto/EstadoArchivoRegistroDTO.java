package com.example.Induztek.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EstadoArchivoRegistroDTO {
    @NotBlank
    private String recordId;

    private boolean archived;

    @NotBlank
    private String action; // "ARCHIVED" o "RESTORED"

    @NotNull
    private LocalDateTime updatedAt;

    public EstadoArchivoRegistroDTO() {}

    public EstadoArchivoRegistroDTO(String recordId, boolean archived, String action, LocalDateTime updatedAt) {
        this.recordId = recordId;
        this.archived = archived;
        this.action = action;
        this.updatedAt = updatedAt;
    }

    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }
    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}