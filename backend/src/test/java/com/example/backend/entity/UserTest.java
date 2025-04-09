package com.example.backend.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testUserCreationAndGetters() {
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("secret");
        user.setCreatedAt(LocalDateTime.of(2025, 1, 1, 0, 0));
        user.setUpdatedAt(LocalDateTime.of(2025, 1, 2, 0, 0));

        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("secret", user.getPassword());
        assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0), user.getCreatedAt());
        assertEquals(LocalDateTime.of(2025, 1, 2, 0, 0), user.getUpdatedAt());
        assertEquals("User{id=1, name=John Doe}", user.toString());
    }
}