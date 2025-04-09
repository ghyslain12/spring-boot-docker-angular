package com.example.backend.repository;

import com.example.backend.entity.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(excludeAutoConfiguration = SecurityAutoConfiguration.class) 
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
@ActiveProfiles("test")
public class MaterialRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MaterialRepository materialRepository;

    @BeforeEach
    void setUp() {
        materialRepository.deleteAll();
    }

    @Test
    void testSaveMaterial() {
        Material material = new Material();
        material.setDesignation("TestMaterial");

        Material savedMaterial = materialRepository.save(material);

        assertNotNull(savedMaterial.getId());
        assertEquals("TestMaterial", savedMaterial.getDesignation());
    }

    @Test
    void testFindById() {
        Material material = new Material();
        material.setDesignation("TestMaterial");
        entityManager.persist(material);

        Material foundMaterial = materialRepository.findById(material.getId()).orElse(null);

        assertNotNull(foundMaterial);
        assertEquals("TestMaterial", foundMaterial.getDesignation());
    }

    @Test
    void testExistsByDesignation() {
        Material material = new Material();
        material.setDesignation("TestMaterial");
        entityManager.persist(material);

        boolean exists = materialRepository.existsByDesignation("TestMaterial");

        assertTrue(exists);
        assertFalse(materialRepository.existsByDesignation("UnknownMaterial"));
    }

    @Test
    void testDeleteMaterial() {
        Material material = new Material();
        material.setDesignation("TestMaterial");
        entityManager.persist(material);

        materialRepository.delete(material);

        assertFalse(materialRepository.findById(material.getId()).isPresent());
    }
}