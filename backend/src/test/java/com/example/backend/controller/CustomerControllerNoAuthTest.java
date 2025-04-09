package com.example.backend.controller;

import com.example.backend.entity.Customer;
import com.example.backend.entity.User;
import com.example.backend.repository.CustomerRepository;
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
public class CustomerControllerNoAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${jwt.enable:true}")
    private boolean jwtEnabled;

    private Customer testCustomer;
    private User testUser;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        userRepository.deleteAll();

        long userCount = userRepository.count();
        if (userCount > 0) {
            throw new IllegalStateException("La table users n'est pas vide après deleteAll() : " + userCount + " utilisateurs trouvés");
        }

        String uniqueEmail = "testuser_" + System.currentTimeMillis() + "@example.com";

        testUser = new User();
        testUser.setName("TestUser");
        testUser.setEmail(uniqueEmail);
        testUser.setPassword("password");
        userRepository.save(testUser);

        testCustomer = new Customer();
        testCustomer.setSurnom("TestCustomer");
        testCustomer.setUser(testUser);
        customerRepository.save(testCustomer);
    }

    @Test
    void testGetAllCustomersWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/client")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/api/client")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].surnom").value("TestCustomer"));
        }
    }

    @Test
    void testGetCustomerByIdWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/client/" + testCustomer.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/api/client/" + testCustomer.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.surnom").value("TestCustomer"));
        }
    }

    @Test
    void testGetCustomerByIdWithoutAuthNotFound() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/client/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/api/client/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testCreateCustomerWithoutAuth() throws Exception {
        String newCustomerJson = "{\"surnom\":\"NewCustomer\",\"user_id\":" + testUser.getId() + "}";
        if (jwtEnabled) {
            mockMvc.perform(post("/api/client")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newCustomerJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(post("/api/client")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newCustomerJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.surnom").value("NewCustomer"));
        }
    }

    @Test
    void testCreateCustomerWithoutAuthInvalidUser() throws Exception {
        String newCustomerJson = "{\"surnom\":\"NewCustomer\",\"user_id\":999}";
        if (jwtEnabled) {
            mockMvc.perform(post("/api/client")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newCustomerJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(post("/api/client")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newCustomerJson))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testUpdateCustomerWithoutAuth() throws Exception {
        String updatedCustomerJson = "{\"surnom\":\"UpdatedCustomer\",\"user_id\":" + testUser.getId() + "}";
        if (jwtEnabled) {
            mockMvc.perform(put("/api/client/" + testCustomer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedCustomerJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(put("/api/client/" + testCustomer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedCustomerJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.surnom").value("UpdatedCustomer"));
        }
    }

    @Test
    void testUpdateCustomerWithoutAuthNotFound() throws Exception {
        String updatedCustomerJson = "{\"surnom\":\"UpdatedCustomer\",\"user_id\":" + testUser.getId() + "}";
        if (jwtEnabled) {
            mockMvc.perform(put("/api/client/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedCustomerJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(put("/api/client/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedCustomerJson))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testDeleteCustomerWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(delete("/api/client/" + testCustomer.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(delete("/api/client/" + testCustomer.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void testDeleteCustomerWithoutAuthNotFound() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(delete("/api/client/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(delete("/api/client/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }
}