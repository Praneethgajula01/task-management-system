package com.ardentix.taskmanagement.security;

import com.ardentix.taskmanagement.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT Authentication Filter
 * 
 * This filter runs before every request to:
 * 1. Extract JWT token from Authorization header
 * 2. Validate token
 * 3. Set authentication in Spring Security context
 * 
 * OncePerRequestFilter: Ensures filter runs only once per request
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        // Skip JWT filter for public endpoints
        String path = request.getRequestURI();
        if (path.startsWith("/api/auth/") || path.startsWith("/h2-console/")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Get Authorization header
        String authHeader = request.getHeader("Authorization");
        
        // Check if header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // No token, continue to next filter
            filterChain.doFilter(request, response);
            return;
        }
        
        // Extract token (remove "Bearer " prefix)
        String token = authHeader.substring(7);
        
        try {
            // Validate token
            if (jwtUtil.validateToken(token)) {
                // Extract email from token
                String email = jwtUtil.getEmailFromToken(token);
                
                // Create authentication object
                // We don't need credentials here since token is already validated
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                new ArrayList<>() // No roles/authorities for now
                        );
                
                // Set details
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Set authentication in Security Context
                // This allows @PreAuthorize and other security features to work
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Invalid token, continue without authentication
            logger.error("JWT validation failed", e);
        }
        
        // Continue filter chain
        filterChain.doFilter(request, response);
    }
}

