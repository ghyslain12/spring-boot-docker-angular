package com.example.backend.repository;

import com.example.backend.entity.Sale;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
	boolean existsByTitre(String titre);
}