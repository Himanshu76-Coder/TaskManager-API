package com.taskmanager.taskmanager_api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

// This class holds the data that the API sends back to the client
@Data
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
