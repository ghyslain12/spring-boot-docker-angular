package com.example.backend.repository;

import com.example.backend.entity.Customer;
import com.example.backend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(excludeAutoConfiguration = SecurityAutoConfiguration.class) 
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setName("TestUser");
        testUser.setEmail("testuser_" + System.currentTimeMillis() + "@example.com");
        testUser.setPassword("password");
        entityManager.persistAndFlush(testUser);
    }

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setSurnom("TestCustomer");
        customer.setUser(testUser);

        Customer savedCustomer = customerRepository.save(customer);

        assertNotNull(savedCustomer.getId());
        assertEquals("TestCustomer", savedCustomer.getSurnom());
        assertEquals(testUser.getId(), savedCustomer.getUser().getId());
    }

    @Test
    void testFindById() {
        Customer customer = new Customer();
        customer.setSurnom("TestCustomer");
        customer.setUser(testUser);
        entityManager.persistAndFlush(customer);

        Customer foundCustomer = customerRepository.findById(customer.getId()).orElse(null);

        assertNotNull(foundCustomer);
        assertEquals("TestCustomer", foundCustomer.getSurnom());
    }

    @Test
    void testExistsBySurnom() {
        Customer customer = new Customer();
        customer.setSurnom("TestCustomer");
        customer.setUser(testUser);
        entityManager.persistAndFlush(customer);

        boolean exists = customerRepository.existsBySurnom("TestCustomer");

        assertTrue(exists);
        assertFalse(customerRepository.existsBySurnom("UnknownCustomer"));
    }

    @Test
    void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setSurnom("TestCustomer");
        customer.setUser(testUser);
        entityManager.persistAndFlush(customer);

        customerRepository.delete(customer);

        assertFalse(customerRepository.findById(customer.getId()).isPresent());
    }
}