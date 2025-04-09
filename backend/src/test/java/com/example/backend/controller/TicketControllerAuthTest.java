package com.example.backend.controller;

import com.example.backend.entity.Customer;
import com.example.backend.entity.Sale;
import com.example.backend.entity.Ticket;
import com.example.backend.entity.User;
import com.example.backend.repository.CustomerRepository;
import com.example.backend.repository.SaleRepository;
import com.example.backend.repository.TicketRepository;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class TicketControllerAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private TicketRepository ticketRepository;

    private Ticket testTicket;
    private Sale testSale;

    @BeforeEach
    void setUp() {
        ticketRepository.deleteAll();
        saleRepository.deleteAll();
        customerRepository.deleteAll();
        userRepository.deleteAll();

        long userCount = userRepository.count();
        if (userCount > 0) {
            throw new IllegalStateException("La table users n'est pas vide après deleteAll() : " + userCount + " utilisateurs trouvés");
        }

        String uniqueEmail = "testuser_" + System.currentTimeMillis() + "@example.com";

        User user = new User();
        user.setName("TestUser");
        user.setEmail(uniqueEmail);
        user.setPassword("password");
        userRepository.save(user);

        Customer customer = new Customer();
        customer.setSurnom("TestCustomer");
        customer.setUser(user);
        customerRepository.save(customer);

        testSale = new Sale();
        testSale.setTitre("TestSale");
        testSale.setDescription("Sale Description");
        testSale.setCustomer(customer);
        saleRepository.save(testSale);

        testTicket = new Ticket();
        testTicket.setTitre("TestTicket");
        testTicket.setDescription("Ticket Description");
        testTicket.addSale(testSale);
        ticketRepository.save(testTicket);
    }

    @Test
    @WithMockUser(username = "testuser2@example.com", roles = {"USER"})
    void testGetAllTicketsWithAuthSuccess() throws Exception {
        mockMvc.perform(get("/api/ticket")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titre").value("TestTicket"));
    }

    @Test
    @WithMockUser(username = "testuser2@example.com", roles = {"USER"})
    void testGetTicketByIdWithAuthSuccess() throws Exception {
        mockMvc.perform(get("/api/ticket/" + testTicket.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titre").value("TestTicket"))
                .andExpect(jsonPath("$.sales[0].titre").value("TestSale"));
    }

    @Test
    @WithMockUser(username = "testuser2@example.com", roles = {"USER"})
    void testGetTicketByIdWithAuthNotFound() throws Exception {
        mockMvc.perform(get("/api/ticket/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser2@example.com", roles = {"USER"})
    void testCreateTicketWithAuthSuccess() throws Exception {
        String newTicketJson = "{\"titre\":\"NewTicket\",\"description\":\"New Description\",\"sale_id\":" + testSale.getId() + "}";

        mockMvc.perform(post("/api/ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTicketJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titre").value("NewTicket"))
                .andExpect(jsonPath("$.sales[0].titre").value("TestSale"));
    }

    @Test
    @WithMockUser(username = "testuser2@example.com", roles = {"USER"})
    void testCreateTicketWithAuthInvalidSale() throws Exception {
        String newTicketJson = "{\"titre\":\"NewTicket\",\"description\":\"New Description\",\"sale_id\":999}";

        mockMvc.perform(post("/api/ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTicketJson))
                .andExpect(status().isNotFound()); 
    }

    @Test
    @WithMockUser(username = "testuser2@example.com", roles = {"USER"})
    void testCreateTicketWithAuthDuplicateTitle() throws Exception {
        String newTicketJson = "{\"titre\":\"TestTicket\",\"description\":\"New Description\",\"sale_id\":" + testSale.getId() + "}";

        mockMvc.perform(post("/api/ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTicketJson))
                .andExpect(status().isBadRequest()); 
    }

    @Test
    @WithMockUser(username = "testuser2@example.com", roles = {"USER"})
    void testUpdateTicketWithAuthSuccess() throws Exception {
        String updatedTicketJson = "{\"titre\":\"UpdatedTicket\",\"description\":\"Updated Description\",\"sale_id\":" + testSale.getId() + "}";

        mockMvc.perform(put("/api/ticket/" + testTicket.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTicketJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titre").value("UpdatedTicket"))
                .andExpect(jsonPath("$.sales[0].titre").value("TestSale"));
    }

    @Test
    @WithMockUser(username = "testuser2@example.com", roles = {"USER"})
    void testUpdateTicketWithAuthNotFound() throws Exception {
        String updatedTicketJson = "{\"titre\":\"UpdatedTicket\",\"description\":\"Updated Description\",\"sale_id\":" + testSale.getId() + "}";

        mockMvc.perform(put("/api/ticket/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTicketJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser2@example.com", roles = {"USER"})
    void testDeleteTicketWithAuthSuccess() throws Exception {
        mockMvc.perform(delete("/api/ticket/" + testTicket.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser2@example.com", roles = {"USER"})
    void testDeleteTicketWithAuthNotFound() throws Exception {
        mockMvc.perform(delete("/api/ticket/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}