package com.taskmanager.taskmanager_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

// This class holds the data that the client sends when creating or updating a task
@Data
public class TaskRequestDTO {

    // Title is required - cannot be blank
    @NotBlank(message = "Title is required")
    private String title;

    // Description is optional
    private String description;

    // Status is optional - defaults to PENDING if not provided
    private String status;

    // Priority is optional - LOW, MEDIUM, or HIGH
    private String priority;

    // Due date is optional
    private LocalDate dueDate;
}
