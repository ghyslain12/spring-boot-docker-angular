package com.example.backend.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void testGenerateToken() {
        String email = "testuser@example.com";
        String token = jwtUtil.generateToken(email);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testGenerateTokenNullEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            jwtUtil.generateToken(null);
        });

        assertEquals("Email cannot be null or empty when generating token", exception.getMessage());
    }

    @Test
    void testExtractUsername() {
        String email = "testuser@example.com";
        String token = jwtUtil.generateToken(email);

        String extractedEmail = jwtUtil.extractUsername(token);

        assertEquals(email, extractedEmail);
    }

    @Test
    void testValidateTokenValid() {
        String email = "testuser@example.com";
        String token = jwtUtil.generateToken(email);

        boolean isValid = jwtUtil.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void testValidateTokenInvalid() {
        boolean isValid = jwtUtil.validateToken("invalidtoken");

        assertFalse(isValid);
    }
}