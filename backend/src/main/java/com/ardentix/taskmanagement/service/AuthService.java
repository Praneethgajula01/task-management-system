package com.ardentix.taskmanagement.service;

import com.ardentix.taskmanagement.dto.AuthResponse;
import com.ardentix.taskmanagement.dto.LoginRequest;
import com.ardentix.taskmanagement.dto.RegisterRequest;
import com.ardentix.taskmanagement.entity.User;
import com.ardentix.taskmanagement.repository.UserRepository;
import com.ardentix.taskmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Authentication Service
 * 
 * @Service: Marks this as a Spring service (business logic layer)
 * @RequiredArgsConstructor: Lombok generates constructor with final fields
 * 
 * Service Layer Responsibilities:
 * - Contains business logic
 * - Validates business rules
 * - Coordinates between repository and controller
 * - Handles exceptions
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // BCrypt password encoder
    private final JwtUtil jwtUtil;
    
    /**
     * Registers a new user
     * 
     * Steps:
     * 1. Check if email already exists
     * 2. Encrypt password using BCrypt
     * 3. Save user to database
     * 4. Generate JWT token
     * 5. Return token and user info
     */
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        
        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        
        // Encrypt password (BCrypt automatically salts and hashes)
        // Never store plain text passwords!
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Save to database
        user = userRepository.save(user);
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        
        // Return response
        return new AuthResponse(token, user.getEmail(), user.getName(), user.getId());
    }
    
    /**
     * Authenticates user login
     * 
     * Steps:
     * 1. Find user by email
     * 2. Verify password matches
     * 3. Generate JWT token
     * 4. Return token and user info
     */
    public AuthResponse login(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        
        // Verify password
        // passwordEncoder.matches() compares plain text with encrypted password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        
        // Return response
        return new AuthResponse(token, user.getEmail(), user.getName(), user.getId());
    }
}

