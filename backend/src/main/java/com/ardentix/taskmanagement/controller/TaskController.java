package com.ardentix.taskmanagement.controller;

import com.ardentix.taskmanagement.dto.TaskRequest;
import com.ardentix.taskmanagement.dto.TaskResponse;
import com.ardentix.taskmanagement.service.TaskService;
import com.ardentix.taskmanagement.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Task Controller
 * 
 * All endpoints require authentication (JWT token in Authorization header)
 * 
 * REST API Endpoints:
 * - GET    /api/tasks - Get all tasks
 * - GET    /api/tasks/{id} - Get task by ID
 * - POST   /api/tasks - Create new task
 * - PUT    /api/tasks/{id} - Update task
 * - DELETE /api/tasks/{id} - Delete task
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    private final TaskService taskService;
    private final SecurityUtil securityUtil;
    
    public TaskController(TaskService taskService, SecurityUtil securityUtil) {
        this.taskService = taskService;
        this.securityUtil = securityUtil;
    }
    
    /**
     * Get all tasks for current user
     * 
     * @GetMapping: Handles GET requests
     * Returns list of tasks
     */
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        try {
            Long userId = securityUtil.getCurrentUserId();
            List<TaskResponse> tasks = taskService.getAllTasks(userId);
            return ResponseEntity.ok(tasks);
        } catch (RuntimeException e) {
            System.err.println("Error getting tasks: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    /**
     * Get task by ID
     * 
     * @PathVariable: Extracts {id} from URL
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        try {
            Long userId = securityUtil.getCurrentUserId();
            TaskResponse task = taskService.getTaskById(id, userId);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * Create new task
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        try {
            Long userId = securityUtil.getCurrentUserId();
            TaskResponse task = taskService.createTask(request, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(task);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Update existing task
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {
        try {
            Long userId = securityUtil.getCurrentUserId();
            TaskResponse task = taskService.updateTask(id, request, userId);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * Delete task
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            Long userId = securityUtil.getCurrentUserId();
            taskService.deleteTask(id, userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

