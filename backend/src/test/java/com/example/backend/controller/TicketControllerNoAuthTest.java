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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class TicketControllerNoAuthTest {

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

    @Value("${jwt.enable:true}")
    private boolean jwtEnabled;

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
    void testGetAllTicketsWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/ticket")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/api/ticket")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].titre").value("TestTicket"));
        }
    }

    @Test
    void testGetTicketByIdWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/ticket/" + testTicket.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/api/ticket/" + testTicket.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.titre").value("TestTicket"));
        }
    }

    @Test
    void testGetTicketByIdWithoutAuthNotFound() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/ticket/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/api/ticket/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testCreateTicketWithoutAuth() throws Exception {
        String newTicketJson = "{\"titre\":\"NewTicket\",\"description\":\"New Description\",\"sale_id\":" + testSale.getId() + "}";
        if (jwtEnabled) {
            mockMvc.perform(post("/api/ticket")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newTicketJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(post("/api/ticket")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newTicketJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.titre").value("NewTicket"));
        }
    }

    @Test
    void testCreateTicketWithoutAuthInvalidSale() throws Exception {
        String newTicketJson = "{\"titre\":\"NewTicket\",\"description\":\"New Description\",\"sale_id\":999}";
        if (jwtEnabled) {
            mockMvc.perform(post("/api/ticket")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newTicketJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(post("/api/ticket")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newTicketJson))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testUpdateTicketWithoutAuth() throws Exception {
        String updatedTicketJson = "{\"titre\":\"UpdatedTicket\",\"description\":\"Updated Description\",\"sale_id\":" + testSale.getId() + "}";
        if (jwtEnabled) {
            mockMvc.perform(put("/api/ticket/" + testTicket.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedTicketJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(put("/api/ticket/" + testTicket.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedTicketJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.titre").value("UpdatedTicket"));
        }
    }

    @Test
    void testUpdateTicketWithoutAuthNotFound() throws Exception {
        String updatedTicketJson = "{\"titre\":\"UpdatedTicket\",\"description\":\"Updated Description\",\"sale_id\":" + testSale.getId() + "}";
        if (jwtEnabled) {
            mockMvc.perform(put("/api/ticket/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedTicketJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(put("/api/ticket/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedTicketJson))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testDeleteTicketWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(delete("/api/ticket/" + testTicket.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(delete("/api/ticket/" + testTicket.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void testDeleteTicketWithoutAuthNotFound() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(delete("/api/ticket/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(delete("/api/ticket/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }
}