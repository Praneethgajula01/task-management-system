package com.ardentix.taskmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * User Entity Class
 * 
 * @Entity: Marks this class as a JPA entity (maps to database table)
 * @Table: Specifies table name (optional, defaults to class name)
 * 
 * JPA (Java Persistence API) automatically:
 * - Creates a table named "users" in database
 * - Maps class fields to table columns
 * - Handles relationships between tables
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    /**
     * @Id: Marks this as primary key
     * @GeneratedValue: Auto-generates ID value (auto-increment)
     * Strategy.IDENTITY: Uses database auto-increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * @Column: Maps to database column
     * unique=true: Ensures no duplicate emails
     * nullable=false: Email is required
     */
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password; // Will be encrypted using BCrypt
    
    @Column(nullable = false)
    private String name;
    
    /**
     * @OneToMany: One user can have many tasks
     * mappedBy="user": Task entity has a "user" field that references this
     * cascade=CascadeType.ALL: If user is deleted, all their tasks are deleted
     * orphanRemoval=true: If task is removed from list, delete it from database
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

