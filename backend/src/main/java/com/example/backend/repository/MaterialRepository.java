package com.example.backend.repository;

import com.example.backend.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
	boolean existsByDesignation(String designation);
}