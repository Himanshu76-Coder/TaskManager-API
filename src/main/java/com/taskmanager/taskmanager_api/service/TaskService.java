package com.taskmanager.taskmanager_api.service;

import com.taskmanager.taskmanager_api.dto.TaskRequestDTO;
import com.taskmanager.taskmanager_api.dto.TaskResponseDTO;
import com.taskmanager.taskmanager_api.entity.Task;
import com.taskmanager.taskmanager_api.exception.ResourceNotFoundException;
import com.taskmanager.taskmanager_api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Contains all the business logic for task operations
@Service
public class TaskService {

    // Valid status values allowed for a task
    private static final List<String> VALID_STATUSES = List.of("PENDING", "IN_PROGRESS", "COMPLETED");

    // Valid priority values allowed for a task
    private static final List<String> VALID_PRIORITIES = List.of("LOW", "MEDIUM", "HIGH");

    @Autowired
    private TaskRepository taskRepository;

    // Create a new task and save it to the database
    public TaskResponseDTO createTask(TaskRequestDTO request) {
        // Validate status if provided
        if (request.getStatus() != null && !VALID_STATUSES.contains(request.getStatus().toUpperCase())) {
            throw new IllegalArgumentException("Invalid status value: " + request.getStatus());
        }

        // Validate priority if provided
        if (request.getPriority() != null && !VALID_PRIORITIES.contains(request.getPriority().toUpperCase())) {
            throw new IllegalArgumentException("Invalid priority value: " + request.getPriority());
        }

        // Map request data to Task entity
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus().toUpperCase() : "PENDING");
        task.setPriority(request.getPriority() != null ? request.getPriority().toUpperCase() : null);
        task.setDueDate(request.getDueDate());

        // Save task to database
        Task savedTask = taskRepository.save(task);
        return convertToResponseDTO(savedTask);
    }

    // Get all tasks from the database
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return convertToResponseDTOList(tasks);
    }

    // Get a single task by its ID
    public TaskResponseDTO getTaskById(Long id) {
        @SuppressWarnings("null")
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return convertToResponseDTO(task);
    }

    // Update an existing task by its ID
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO request) {
        // Check if task exists
        @SuppressWarnings("null")
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        // Validate status if provided
        if (request.getStatus() != null && !VALID_STATUSES.contains(request.getStatus().toUpperCase())) {
            throw new IllegalArgumentException("Invalid status value: " + request.getStatus());
        }

        // Validate priority if provided
        if (request.getPriority() != null && !VALID_PRIORITIES.contains(request.getPriority().toUpperCase())) {
            throw new IllegalArgumentException("Invalid priority value: " + request.getPriority());
        }

        // Update task fields with new values
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus().toUpperCase() : task.getStatus());
        task.setPriority(request.getPriority() != null ? request.getPriority().toUpperCase() : task.getPriority());
        task.setDueDate(request.getDueDate());

        // Save updated task to database
        Task updatedTask = taskRepository.save(task);
        return convertToResponseDTO(updatedTask);
    }

    // Delete a task by its ID
    @SuppressWarnings("null")
    public void deleteTask(Long id) {
        // Check if task exists before deleting
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        taskRepository.delete(task);
    }

    // Get tasks filtered by status
    public List<TaskResponseDTO> getTasksByStatus(String status) {
        // Validate the status value
        if (!VALID_STATUSES.contains(status.toUpperCase())) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
        List<Task> tasks = taskRepository.findByStatus(status.toUpperCase());
        return convertToResponseDTOList(tasks);
    }

    // Get tasks filtered by priority
    public List<TaskResponseDTO> getTasksByPriority(String priority) {
        // Validate the priority value
        if (!VALID_PRIORITIES.contains(priority.toUpperCase())) {
            throw new IllegalArgumentException("Invalid priority value: " + priority);
        }
        List<Task> tasks = taskRepository.findByPriority(priority.toUpperCase());
        return convertToResponseDTOList(tasks);
    }

    // Search tasks by title keyword (case-insensitive)
    public List<TaskResponseDTO> searchTasksByTitle(String title) {
        List<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(title);
        return convertToResponseDTOList(tasks);
    }

    // Convert a single Task entity to TaskResponseDTO
    private TaskResponseDTO convertToResponseDTO(Task task) {
        TaskResponseDTO response = new TaskResponseDTO();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setDueDate(task.getDueDate());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        return response;
    }

    // Convert a list of Task entities to a list of TaskResponseDTOs
    private List<TaskResponseDTO> convertToResponseDTOList(List<Task> tasks) {
        List<TaskResponseDTO> responseList = new ArrayList<>();
        for (Task task : tasks) {
            responseList.add(convertToResponseDTO(task));
        }
        return responseList;
    }
}
