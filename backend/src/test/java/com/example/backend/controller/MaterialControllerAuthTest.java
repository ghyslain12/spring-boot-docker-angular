package com.example.backend.controller;

import com.example.backend.entity.Material;
import com.example.backend.repository.MaterialRepository;
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
public class MaterialControllerAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MaterialRepository materialRepository;

    private Material testMaterial;

    @BeforeEach
    void setUp() {
        materialRepository.deleteAll();

        testMaterial = new Material();
        testMaterial.setDesignation("TestMaterial");
        materialRepository.save(testMaterial);
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testGetAllMaterialsWithAuthSuccess() throws Exception {
        mockMvc.perform(get("/api/material")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].designation").value("TestMaterial"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testGetMaterialByIdWithAuthSuccess() throws Exception {
        mockMvc.perform(get("/api/material/" + testMaterial.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("TestMaterial"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testGetMaterialByIdWithAuthNotFound() throws Exception {
        mockMvc.perform(get("/api/material/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testCreateMaterialWithAuthSuccess() throws Exception {
        String newMaterialJson = "{\"designation\":\"NewMaterial\"}";

        mockMvc.perform(post("/api/material")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newMaterialJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("NewMaterial"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testCreateMaterialWithAuthDuplicateDesignation() throws Exception {
        String newMaterialJson = "{\"designation\":\"TestMaterial\"}";

        mockMvc.perform(post("/api/material")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newMaterialJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testUpdateMaterialWithAuthSuccess() throws Exception {
        String updatedMaterialJson = "{\"designation\":\"UpdatedMaterial\"}";

        mockMvc.perform(put("/api/material/" + testMaterial.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedMaterialJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("UpdatedMaterial"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testUpdateMaterialWithAuthNotFound() throws Exception {
        String updatedMaterialJson = "{\"designation\":\"UpdatedMaterial\"}";

        mockMvc.perform(put("/api/material/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedMaterialJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testDeleteMaterialWithAuthSuccess() throws Exception {
        mockMvc.perform(delete("/api/material/" + testMaterial.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testDeleteMaterialWithAuthNotFound() throws Exception {
        mockMvc.perform(delete("/api/material/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testPingWithAuthSuccess() throws Exception {
        mockMvc.perform(get("/api/material/ping")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("ping"));
    }
}