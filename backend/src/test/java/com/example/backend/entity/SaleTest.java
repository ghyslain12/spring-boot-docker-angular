package com.example.backend.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class SaleTest {

    @Test
    void testSaleCreationAndRelations() {
        Customer customer = new Customer();
        customer.setId(1);

        Material material = new Material();
        material.setId(1);

        Sale sale = new Sale();
        sale.setId(1);
        sale.setCustomer(customer);
        sale.setTitre("Sale Title");
        sale.setDescription("Sale Description");
        sale.setCreatedAt(LocalDateTime.of(2025, 1, 1, 0, 0));
        sale.setUpdatedAt(LocalDateTime.of(2025, 1, 2, 0, 0));
        sale.addMaterial(material);

        assertEquals(1, sale.getId());
        assertEquals(customer, sale.getCustomer());
        assertEquals(1, sale.getCustomerId());
        assertEquals("Sale Title", sale.getTitre());
        assertEquals("Sale Description", sale.getDescription());
        assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0), sale.getCreatedAt());
        assertEquals(LocalDateTime.of(2025, 1, 2, 0, 0), sale.getUpdatedAt());
        assertTrue(sale.getMaterials().contains(material));
        assertEquals("Sale{id=1, titre=Sale Title}", sale.toString());

        sale.removeMaterial(material);
        assertFalse(sale.getMaterials().contains(material));

        sale.clearMaterials();
        assertTrue(sale.getMaterials().isEmpty());
    }
}