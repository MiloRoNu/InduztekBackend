package com.example.Induztek.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Induztek.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);
}