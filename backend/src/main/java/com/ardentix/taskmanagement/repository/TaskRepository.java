package com.ardentix.taskmanagement.repository;

import com.ardentix.taskmanagement.entity.Task;
import com.ardentix.taskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Task Repository Interface
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    /**
     * Finds all tasks belonging to a specific user
     * Spring generates: SELECT * FROM tasks WHERE user_id = ? ORDER BY id
     */
    List<Task> findByUserOrderByCreatedAtDesc(User user);
    
    /**
     * Finds task by ID and user (for security: ensures user can only access their own tasks)
     */
    java.util.Optional<Task> findByIdAndUser(Long id, User user);
}

