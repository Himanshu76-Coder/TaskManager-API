package com.taskmanager.taskmanager_api.controller;

import com.taskmanager.taskmanager_api.dto.TaskRequestDTO;
import com.taskmanager.taskmanager_api.dto.TaskResponseDTO;
import com.taskmanager.taskmanager_api.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Handles all incoming HTTP requests for task operations
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // POST /api/tasks - Create a new task
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO request) {
        TaskResponseDTO response = taskService.createTask(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET /api/tasks - Get all tasks, or filter by status/priority, or search by title
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String title) {

        // If status param is provided, filter by status
        if (status != null) {
            return ResponseEntity.ok(taskService.getTasksByStatus(status));
        }

        // If priority param is provided, filter by priority
        if (priority != null) {
            return ResponseEntity.ok(taskService.getTasksByPriority(priority));
        }

        // If title param is provided, search by title keyword
        if (title != null) {
            return ResponseEntity.ok(taskService.searchTasksByTitle(title));
        }

        // No params - return all tasks
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // GET /api/tasks/{id} - Get a single task by ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO response = taskService.getTaskById(id);
        return ResponseEntity.ok(response);
    }

    // PUT /api/tasks/{id} - Update an existing task by ID
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO request) {
        TaskResponseDTO response = taskService.updateTask(id, request);
        return ResponseEntity.ok(response);
    }

    // DELETE /api/tasks/{id} - Delete a task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Task deleted successfully");
        return ResponseEntity.ok(response);
    }
}
