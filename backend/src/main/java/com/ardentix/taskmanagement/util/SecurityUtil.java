package com.ardentix.taskmanagement.util;

import com.ardentix.taskmanagement.entity.User;
import com.ardentix.taskmanagement.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Security Utility
 * Helper class to get current authenticated user
 */
@Component
public class SecurityUtil {
    
    private final UserRepository userRepository;
    
    public SecurityUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Gets the current authenticated user
     * Uses Spring Security Context to get email from JWT token
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    /**
     * Gets current user ID
     */
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}

