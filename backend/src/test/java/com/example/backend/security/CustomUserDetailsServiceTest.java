package com.example.backend.security;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("$2a$10$examplehashedpassword"); 
        testUser.setName("TestUser");
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        when(userRepository.findByEmail("testuser@example.com")).thenReturn(testUser);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser@example.com");

        assertNotNull(userDetails);
        assertEquals("testuser@example.com", userDetails.getUsername());
        assertEquals("$2a$10$examplehashedpassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("USER")));
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("unknown@example.com");
        });

        assertEquals("User not found with email: unknown@example.com", exception.getMessage());
    }
}