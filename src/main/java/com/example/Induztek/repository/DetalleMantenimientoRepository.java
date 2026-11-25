package com.example.Induztek.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Induztek.model.DetalleMantenimiento;

public interface DetalleMantenimientoRepository extends JpaRepository<DetalleMantenimiento, String> {

    List<DetalleMantenimiento> findByRecordId(String recordId);
}