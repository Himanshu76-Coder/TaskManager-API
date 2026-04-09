package com.taskmanager.taskmanager_api.exception;

// Custom exception thrown when a task is not found in the database
public class ResourceNotFoundException extends RuntimeException {

    // Pass the error message when throwing this exception
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
