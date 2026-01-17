package com.ardentix.taskmanagement.repository;

import com.ardentix.taskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository Interface
 * 
 * @Repository: Marks this as a Spring Data repository
 * 
 * JpaRepository provides:
 * - save(), findById(), findAll(), delete(), etc.
 * - Automatic implementation (no need to write SQL queries)
 * - Type-safe queries
 * 
 * Spring Data JPA automatically creates implementation at runtime
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Custom query method
     * Spring Data JPA automatically implements this based on method name:
     * - findByEmail: finds by email field
     * - Returns Optional<User>: null-safe (prevents NullPointerException)
     * 
     * This generates SQL: SELECT * FROM users WHERE email = ?
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Checks if email exists
     * Generates: SELECT COUNT(*) > 0 FROM users WHERE email = ?
     */
    boolean existsByEmail(String email);
}

