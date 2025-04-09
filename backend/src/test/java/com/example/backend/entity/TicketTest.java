package com.example.backend.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    @Test
    void testTicketCreationAndRelations() {
        Sale sale = new Sale();
        sale.setId(1);

        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setTitre("Ticket Title");
        ticket.setDescription("Ticket Description");
        ticket.setCreatedAt(LocalDateTime.of(2025, 1, 1, 0, 0));
        ticket.setUpdatedAt(LocalDateTime.of(2025, 1, 2, 0, 0));
        ticket.addSale(sale);

        assertEquals(1, ticket.getId());
        assertEquals("Ticket Title", ticket.getTitre());
        assertEquals("Ticket Description", ticket.getDescription());
        assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0), ticket.getCreatedAt());
        assertEquals(LocalDateTime.of(2025, 1, 2, 0, 0), ticket.getUpdatedAt());
        assertTrue(ticket.getSales().contains(sale));
        assertEquals("Ticket{id=1, titre=Ticket Title}", ticket.toString());

        ticket.removeSale(sale);
        assertFalse(ticket.getSales().contains(sale));

        ticket.clearSales();
        assertTrue(ticket.getSales().isEmpty());
    }
}