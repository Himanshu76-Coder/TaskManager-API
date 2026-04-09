package com.taskmanager.taskmanager_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

// This class represents the 'tasks' table in the database
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    // Primary key - auto incremented by the database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Task title - required field
    @Column(nullable = false)
    private String title;

    // Task description - optional
    @Column(columnDefinition = "TEXT")
    private String description;

    // Task status - PENDING, IN_PROGRESS, or COMPLETED
    @Column(nullable = false)
    private String status;

    // Task priority - LOW, MEDIUM, or HIGH
    private String priority;

    // Optional due date for the task
    private LocalDate dueDate;

    // Automatically set when the task is created
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Automatically updated when the task is modified
    private LocalDateTime updatedAt;

    // Set createdAt before saving for the first time
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        // Set default status to PENDING if not provided
        if (this.status == null) {
            this.status = "PENDING";
        }
    }

    // Update updatedAt every time the task is modified
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
