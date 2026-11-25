package com.example.Induztek.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Induztek.model.RegistroTerreno;

public interface RegistroTerrenoRepository extends JpaRepository<RegistroTerreno, String> {

    // Registros activos (no archivados) o archivados según vista
    List<RegistroTerreno> findByArchivedOrderByCreatedAtDesc(boolean archived);

    // Filtrar por tipo de equipo dentro de los registros activos o archivados
    List<RegistroTerreno> findByArchivedAndEquipmentTypeIdOrderByCreatedAtDesc(
            boolean archived, String equipmentTypeId);

    // Registros de un técnico específico
    List<RegistroTerreno> findByTechnicianIdAndArchivedOrderByCreatedAtDesc(
            String technicianId, boolean archived);

    // Último registro del mismo equipo/instalación para comparar mientras se completa el formulario
    Optional<RegistroTerreno> findFirstByFacilityIdAndEquipmentTypeIdAndArchivedFalseOrderByCreatedAtDesc(
            String facilityId, String equipmentTypeId);
}