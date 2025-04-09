package com.example.backend.controller;

import com.example.backend.entity.Customer;
import com.example.backend.entity.Sale;
import com.example.backend.entity.User;
import com.example.backend.repository.CustomerRepository;
import com.example.backend.repository.SaleRepository;
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
public class SaleControllerNoAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Value("${jwt.enable:true}")
    private boolean jwtEnabled;

    private Sale testSale;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
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

        testCustomer = new Customer();
        testCustomer.setSurnom("TestCustomer");
        testCustomer.setUser(user);
        customerRepository.save(testCustomer);

        testSale = new Sale();
        testSale.setTitre("TestSale");
        testSale.setDescription("Description");
        testSale.setCustomer(testCustomer);
        saleRepository.save(testSale);
    }

    @Test
    void testGetAllSalesWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/sale")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/api/sale")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].titre").value("TestSale"));
        }
    }

    @Test
    void testGetSaleByIdWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/sale/" + testSale.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/api/sale/" + testSale.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.titre").value("TestSale"));
        }
    }

    @Test
    void testGetSaleByIdWithoutAuthNotFound() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/sale/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/api/sale/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testCreateSaleWithoutAuth() throws Exception {
        String newSaleJson = "{\"titre\":\"NewSale\",\"description\":\"New Description\",\"customer_id\":" + testCustomer.getId() + "}";
        if (jwtEnabled) {
            mockMvc.perform(post("/api/sale")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newSaleJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(post("/api/sale")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newSaleJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.titre").value("NewSale"));
        }
    }

    @Test
    void testCreateSaleWithoutAuthInvalidCustomer() throws Exception {
        String newSaleJson = "{\"titre\":\"NewSale\",\"description\":\"New Description\",\"customer_id\":999}";
        if (jwtEnabled) {
            mockMvc.perform(post("/api/sale")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newSaleJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(post("/api/sale")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newSaleJson))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testUpdateSaleWithoutAuth() throws Exception {
        String updatedSaleJson = "{\"titre\":\"UpdatedSale\",\"description\":\"Updated Description\",\"customer_id\":" + testCustomer.getId() + "}";
        if (jwtEnabled) {
            mockMvc.perform(put("/api/sale/" + testSale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedSaleJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(put("/api/sale/" + testSale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedSaleJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.titre").value("UpdatedSale"));
        }
    }

    @Test
    void testUpdateSaleWithoutAuthNotFound() throws Exception {
        String updatedSaleJson = "{\"titre\":\"UpdatedSale\",\"description\":\"Updated Description\",\"customer_id\":" + testCustomer.getId() + "}";
        if (jwtEnabled) {
            mockMvc.perform(put("/api/sale/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedSaleJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(put("/api/sale/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedSaleJson))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testDeleteSaleWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(delete("/api/sale/" + testSale.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(delete("/api/sale/" + testSale.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void testDeleteSaleWithoutAuthNotFound() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(delete("/api/sale/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(delete("/api/sale/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }
}