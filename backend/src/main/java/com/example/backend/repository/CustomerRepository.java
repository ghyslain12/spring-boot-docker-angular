package com.example.backend.repository;

import com.example.backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	boolean existsBySurnom(String surnom);
}