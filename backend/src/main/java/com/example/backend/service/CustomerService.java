package com.example.backend.service;

import com.example.backend.dto.CustomerDTO;
import com.example.backend.entity.Customer;
import com.example.backend.entity.User;
import com.example.backend.repository.CustomerRepository;
import com.example.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Customer.class.getSimpleName() + " not found with id: " + id));
    }

    public Customer createCustomer(CustomerDTO customerDTO) {
    	if (customerRepository.existsBySurnom(customerDTO.getSurnom())) {
            throw new IllegalArgumentException("Un customer avec le surnom '" + customerDTO.getSurnom() + "' existe déjà.");
        }

        if (customerDTO.getUserId() == null) {
            throw new IllegalArgumentException("A valid user_id is required to create a Customer");
        }

        User user = userRepository.findById(customerDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + customerDTO.getUserId()));

        Customer customer = new Customer();
        customer.setSurnom(customerDTO.getSurnom());
        customer.setUser(user);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Integer id, CustomerDTO customerDTO) {
    	
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Customer.class.getSimpleName() + " not found with id: " + id));

        if (customerDTO.getUserId() == null) {
            throw new IllegalArgumentException("A valid user_id is required to create a Customer");
        }

        User user = userRepository.findById(customerDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + customerDTO.getUserId()));

        customer.setSurnom(customerDTO.getSurnom());
        customer.setUser(user);
        customer.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Customer.class.getSimpleName() + " not found with id: " + id));
        
        customerRepository.delete(customer);
    }
}