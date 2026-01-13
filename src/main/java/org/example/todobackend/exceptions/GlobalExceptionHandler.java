package org.example.todobackend.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// Global exception handler for your REST API.
// Converts TodoNotFoundException into a proper 404 response instead of a 500.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<?> handleTodoNotFound(TodoNotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage()
        ));
    }
}