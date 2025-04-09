package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(User.class.getSimpleName() + " not found with id: " + id));
    }

    public User createUser(User user) {
    	if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Un user avec l'email '" + user.getEmail() + "' existe déjà.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
     
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User userDetail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(User.class.getSimpleName() + " not found with id: " + id));
        
        user.setName(userDetail.getName());
        user.setEmail(userDetail.getEmail());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(User.class.getSimpleName() + " not found with id: " + id));
        
        userRepository.delete(user);
    }
}