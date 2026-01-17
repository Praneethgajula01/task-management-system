package com.ardentix.taskmanagement.controller;

import com.ardentix.taskmanagement.dto.AuthResponse;
import com.ardentix.taskmanagement.dto.ErrorResponse;
import com.ardentix.taskmanagement.dto.LoginRequest;
import com.ardentix.taskmanagement.dto.RegisterRequest;
import com.ardentix.taskmanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller
 * 
 * @RestController: Combines @Controller + @ResponseBody
 * - @Controller: Marks as Spring MVC controller
 * - @ResponseBody: Converts return values to JSON
 * 
 * @RequestMapping: Base URL path for all endpoints in this controller
 * 
 * REST API Endpoints:
 * - POST /api/auth/register - Register new user
 * - POST /api/auth/login - Login user
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    /**
     * Register Endpoint
     * 
     * @PostMapping: Handles POST requests
     * @Valid: Triggers validation annotations (@NotBlank, @Email, etc.)
     * @RequestBody: Converts JSON request body to RegisterRequest object
     * 
     * HTTP Status Codes:
     * - 200 OK: Success
     * - 400 Bad Request: Validation failed
     * - 500 Internal Server Error: Server error
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Email already exists or other error
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "REGISTRATION_ERROR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    
    /**
     * Login Endpoint
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Invalid credentials
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "LOGIN_ERROR");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}

