package com.example.Induztek.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Induztek.model.HistorialCambio;

public interface HistorialCambioRepository extends JpaRepository<HistorialCambio, String> {

    List<HistorialCambio> findByRecordIdOrderByEditedAtDesc(String recordId);
}