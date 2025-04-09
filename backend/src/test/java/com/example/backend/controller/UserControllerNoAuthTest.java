package com.example.backend.controller;

import com.example.backend.entity.User;
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
public class UserControllerNoAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.enable:true}")
    private boolean jwtEnabled;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        testUser = new User();
        testUser.setName("TestUser");
        testUser.setEmail("testuser_" + System.currentTimeMillis() + "@example.com");
        testUser.setPassword("password");
        userRepository.saveAndFlush(testUser);
    }

    @Test
    void testGetAllUsersWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/utilisateur")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/api/utilisateur")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").value("TestUser"));
        }
    }

    @Test
    void testGetUserByIdWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/utilisateur/" + testUser.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/api/utilisateur/" + testUser.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("TestUser"));
        }
    }

    @Test
    void testGetUserByIdWithoutAuthNotFound() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/utilisateur/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/api/utilisateur/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testCreateUserWithoutAuth() throws Exception {
        String newUserJson = "{\"name\":\"NewUser\",\"email\":\"newuser@example.com\",\"password\":\"newpassword\"}";
        if (jwtEnabled) {
            mockMvc.perform(post("/api/utilisateur")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newUserJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(post("/api/utilisateur")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newUserJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("NewUser"));
        }
    }

    @Test
    void testUpdateUserWithoutAuth() throws Exception {
        String updatedUserJson = "{\"name\":\"UpdatedUser\",\"email\":\"updateduser@example.com\",\"password\":\"updatedpassword\"}";
        if (jwtEnabled) {
            mockMvc.perform(put("/api/utilisateur/" + testUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedUserJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(put("/api/utilisateur/" + testUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedUserJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("UpdatedUser"));
        }
    }

    @Test
    void testUpdateUserWithoutAuthNotFound() throws Exception {
        String updatedUserJson = "{\"name\":\"UpdatedUser\",\"email\":\"updateduser@example.com\",\"password\":\"updatedpassword\"}";
        if (jwtEnabled) {
            mockMvc.perform(put("/api/utilisateur/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedUserJson))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(put("/api/utilisateur/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedUserJson))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testDeleteUserWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(delete("/api/utilisateur/" + testUser.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(delete("/api/utilisateur/" + testUser.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void testDeleteUserWithoutAuthNotFound() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(delete("/api/utilisateur/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(delete("/api/utilisateur/999")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testPingWithoutAuth() throws Exception {
        if (jwtEnabled) {
            mockMvc.perform(get("/api/utilisateur/ping")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/api/utilisateur/ping")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }
}