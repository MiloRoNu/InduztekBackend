package com.example.Induztek.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Induztek.model.PruebaElectrica;

public interface PruebaElectricaRepository extends JpaRepository<PruebaElectrica, String> {

    Optional<PruebaElectrica> findByRecordId(String recordId);
}