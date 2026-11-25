package com.example.Induztek.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Induztek.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    List<Cliente> findAllByOrderByNameAsc();
}