package com.ardentix.taskmanagement.util;

import com.ardentix.taskmanagement.entity.User;
import com.ardentix.taskmanagement.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Security Utility
 * Helper class to get current authenticated user
 */
@Component
public class SecurityUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
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
            logger.error("User not authenticated");
            throw new RuntimeException("User not authenticated");
        }
        
        String email = authentication.getName();
        logger.info("Getting user for email: {}", email);
        
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", email);
                    return new RuntimeException("User not found: " + email);
                });
    }
    
    /**
     * Gets current user ID
     */
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}

