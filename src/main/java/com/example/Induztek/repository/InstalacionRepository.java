package com.example.Induztek.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Induztek.model.Instalacion;

public interface InstalacionRepository extends JpaRepository<Instalacion, String> {

    List<Instalacion> findByClientId(String clientId);
}