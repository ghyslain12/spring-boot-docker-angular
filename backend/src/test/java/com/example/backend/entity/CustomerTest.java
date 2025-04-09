package com.example.backend.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    void testCustomerCreationAndGetters() {
        User user = new User();
        user.setId(1);

        Customer customer = new Customer();
        customer.setId(1);
        customer.setUser(user);
        customer.setSurnom("Johnny");
        customer.setCreatedAt(LocalDateTime.of(2025, 1, 1, 0, 0));
        customer.setUpdatedAt(LocalDateTime.of(2025, 1, 2, 0, 0));

        assertEquals(1, customer.getId());
        assertEquals(user, customer.getUser());
        assertEquals(1, customer.getUserId());
        assertEquals("Johnny", customer.getSurnom());
        assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0), customer.getCreatedAt());
        assertEquals(LocalDateTime.of(2025, 1, 2, 0, 0), customer.getUpdatedAt());
        assertEquals("Customer{id=1, surnom=Johnny}", customer.toString());
    }
}