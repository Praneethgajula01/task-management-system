package com.ardentix.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application Class
 * 
 * @SpringBootApplication annotation combines:
 * - @Configuration: Marks this as a configuration class
 * - @EnableAutoConfiguration: Enables Spring Boot auto-configuration
 * - @ComponentScan: Scans for Spring components in this package and sub-packages
 * 
 * When you run this class, Spring Boot:
 * 1. Starts an embedded Tomcat server (default port 8080)
 * 2. Scans for components (@Service, @Repository, @Controller, etc.)
 * 3. Configures beans and dependencies
 * 4. Connects to database
 */
@SpringBootApplication
public class TaskManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
        System.out.println("✅ Task Management System is running on http://localhost:8080");
    }
}

