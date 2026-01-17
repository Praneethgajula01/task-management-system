package com.ardentix.taskmanagement.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT Utility Class
 * 
 * JWT (JSON Web Token) is a stateless authentication mechanism:
 * - Token contains user info (email, ID) encoded as JSON
 * - Signed with secret key to prevent tampering
 * - No need to store sessions on server (stateless)
 * 
 * Token Structure: header.payload.signature
 * - Header: Algorithm and token type
 * - Payload: User data (claims)
 * - Signature: Ensures token hasn't been modified
 */
@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    /**
     * Generates a secret key from the secret string
     * HS512: HMAC-SHA512 algorithm for signing
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    /**
     * Generates JWT token for a user
     * 
     * @param email User's email (stored in token)
     * @param userId User's ID (stored in token)
     * @return JWT token string
     */
    public String generateToken(String email, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        return Jwts.builder()
                .subject(email) // Sets email as subject
                .claim("userId", userId) // Adds custom claim
                .issuedAt(now) // Token creation time
                .expiration(expiryDate) // Token expiration time
                .signWith(getSigningKey()) // Signs token with secret key
                .compact(); // Converts to string
    }
    
    /**
     * Extracts email from JWT token
     */
    public String getEmailFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }
    
    /**
     * Extracts user ID from JWT token
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }
    
    /**
     * Validates JWT token
     * - Checks if token is expired
     * - Verifies signature (ensures token wasn't tampered)
     */
    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Extracts claims (data) from token
     * Throws exception if token is invalid/expired
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Verifies signature
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

