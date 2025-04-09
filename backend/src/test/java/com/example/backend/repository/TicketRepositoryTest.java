package com.example.backend.repository;

import com.example.backend.entity.Sale;
import com.example.backend.entity.Ticket;
import com.example.backend.entity.Customer;
import com.example.backend.entity.User;
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
public class TicketRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TicketRepository ticketRepository;

    private Sale testSale;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setName("TestUser");
        user.setEmail("testuser_" + System.currentTimeMillis() + "@example.com");
        user.setPassword("password");
        entityManager.persist(user);

        Customer customer = new Customer();
        customer.setSurnom("TestCustomer");
        customer.setUser(user);
        entityManager.persist(customer);

        testSale = new Sale();
        testSale.setTitre("TestSale");
        testSale.setDescription("Test Description");
        testSale.setCustomer(customer);
        entityManager.persist(testSale);
    }

    @Test
    void testSaveTicket() {
        Ticket ticket = new Ticket();
        ticket.setTitre("TestTicket");
        ticket.setDescription("Test Description");
        ticket.addSale(testSale);

        Ticket savedTicket = ticketRepository.save(ticket);

        assertNotNull(savedTicket.getId());
        assertEquals("TestTicket", savedTicket.getTitre());
        assertEquals(1, savedTicket.getSales().size());
    }

    @Test
    void testFindByIdWithSales() {
        Ticket ticket = new Ticket();
        ticket.setTitre("TestTicket");
        ticket.setDescription("Test Description");
        ticket.addSale(testSale);
        entityManager.persist(ticket);

        Ticket foundTicket = ticketRepository.findByIdWithSales(ticket.getId()).orElse(null);

        assertNotNull(foundTicket);
        assertEquals("TestTicket", foundTicket.getTitre());
        assertEquals(1, foundTicket.getSales().size());
        assertEquals("TestSale", foundTicket.getSales().iterator().next().getTitre());
    }

    @Test
    void testExistsByTitre() {
        Ticket ticket = new Ticket();
        ticket.setTitre("TestTicket");
        ticket.setDescription("Test Description");
        entityManager.persist(ticket);

        boolean exists = ticketRepository.existsByTitre("TestTicket");

        assertTrue(exists);
        assertFalse(ticketRepository.existsByTitre("UnknownTicket"));
    }

    @Test
    void testDeleteTicket() {
        Ticket ticket = new Ticket();
        ticket.setTitre("TestTicket");
        ticket.setDescription("Test Description");
        entityManager.persist(ticket);

        ticketRepository.delete(ticket);

        assertFalse(ticketRepository.findById(ticket.getId()).isPresent());
    }
}