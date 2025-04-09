package com.example.backend.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

public class MaterialTest {

    @Test
    void testMaterialCreationAndGetters() {
        Material material = new Material();
        material.setId(1);
        material.setDesignation("Wood");
        material.setCreatedAt(LocalDateTime.of(2025, 1, 1, 0, 0));
        material.setUpdatedAt(LocalDateTime.of(2025, 1, 2, 0, 0));
        material.setSales(new HashSet<>());

        assertEquals(1, material.getId());
        assertEquals("Wood", material.getDesignation());
        assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0), material.getCreatedAt());
        assertEquals(LocalDateTime.of(2025, 1, 2, 0, 0), material.getUpdatedAt());
        assertTrue(material.getSales().isEmpty());
        assertEquals("Material{id=1, designation=Wood}", material.toString());
    }
}