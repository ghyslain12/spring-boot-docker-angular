package com.example.backend.service;

import com.example.backend.entity.Material;
import com.example.backend.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public Material getMaterialById(Integer id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Material.class.getSimpleName() + " not found with id: " + id));
    }

    public Material createMaterial(Material material) {
    	if (materialRepository.existsByDesignation(material.getDesignation())) {
            throw new IllegalArgumentException("Un material avec la designation '" + material.getDesignation() + "' existe déjà.");
        }

        return materialRepository.save(material);
    }

    public Material updateMaterial(Integer id, Material materialDetail) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Material.class.getSimpleName() + " not found with id: " + id));
        
        material.setDesignation(materialDetail.getDesignation());
        material.setUpdatedAt(LocalDateTime.now());

        return materialRepository.save(material);
    }

    public void deleteMaterial(Integer id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Material.class.getSimpleName() + " not found with id: " + id));
        
        materialRepository.delete(material);
    }
}