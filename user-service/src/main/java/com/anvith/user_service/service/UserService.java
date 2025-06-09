package com.anvith.user_service.service;

import com.anvith.user_service.config.JwtProvider;
import com.anvith.user_service.entity.User;
import com.anvith.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public User getUserProfile(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.err.println("Invalid Authorization header: " + authHeader); // Debug log
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }
        String jwt = authHeader.substring(7).trim(); // Remove "Bearer " and trim spaces
        System.out.println("Extracted JWT: " + jwt); // Debug log
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
