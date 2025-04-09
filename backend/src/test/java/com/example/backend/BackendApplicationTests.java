package com.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class BackendApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${jwt.enable:true}")
    private boolean jwtEnabled;

    @Test
    void contextLoads() {
        assertNotNull(applicationContext, "Le contexte de l'application devrait se charger");
        System.out.println("JWT enabled: " + jwtEnabled);
    }
}