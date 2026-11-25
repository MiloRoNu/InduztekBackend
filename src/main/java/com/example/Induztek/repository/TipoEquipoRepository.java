package com.example.Induztek.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Induztek.model.TipoEquipo;

public interface TipoEquipoRepository extends JpaRepository<TipoEquipo, String> {

    List<TipoEquipo> findByFacilityId(String facilityId);
}