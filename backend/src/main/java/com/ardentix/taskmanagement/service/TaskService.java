package com.ardentix.taskmanagement.service;

import com.ardentix.taskmanagement.dto.TaskRequest;
import com.ardentix.taskmanagement.dto.TaskResponse;
import com.ardentix.taskmanagement.entity.Task;
import com.ardentix.taskmanagement.entity.User;
import com.ardentix.taskmanagement.repository.TaskRepository;
import com.ardentix.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Task Service
 * Contains business logic for task operations
 */
@Service
@RequiredArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    
    /**
     * Creates a new task for a user
     */
    public TaskResponse createTask(TaskRequest request, Long userId) {
        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Create task
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setUser(user);
        
        // Save to database
        task = taskRepository.save(task);
        
        // Convert to DTO and return
        return convertToResponse(task);
    }
    
    /**
     * Gets all tasks for a user
     */
    public List<TaskResponse> getAllTasks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Task> tasks = taskRepository.findByUserOrderByCreatedAtDesc(user);
        
        // Convert list of entities to list of DTOs
        return tasks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets a single task by ID (only if it belongs to the user)
     */
    public TaskResponse getTaskById(Long taskId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Task task = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        return convertToResponse(task);
    }
    
    /**
     * Updates an existing task
     */
    public TaskResponse updateTask(Long taskId, TaskRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Find task and ensure it belongs to user
        Task task = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        // Update fields
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        
        // Save updated task
        task = taskRepository.save(task);
        
        return convertToResponse(task);
    }
    
    /**
     * Deletes a task
     */
    public void deleteTask(Long taskId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Find task and ensure it belongs to user
        Task task = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        // Delete task
        taskRepository.delete(task);
    }
    
    /**
     * Converts Task entity to TaskResponse DTO
     * This hides internal structure and prevents exposing sensitive data
     */
    private TaskResponse convertToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}

