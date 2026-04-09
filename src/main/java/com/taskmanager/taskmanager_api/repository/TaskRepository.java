package com.taskmanager.taskmanager_api.repository;

import com.taskmanager.taskmanager_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// This interface handles all database operations for Task
// JpaRepository gives us save(), findAll(), findById(), delete() for free
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Find all tasks that match a given status
    List<Task> findByStatus(String status);

    // Find all tasks that match a given priority
    List<Task> findByPriority(String priority);

    // Find all tasks whose title contains the keyword (case-insensitive)
    List<Task> findByTitleContainingIgnoreCase(String title);
}
