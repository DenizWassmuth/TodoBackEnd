package org.example.todobackend.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.example.todobackend.misc.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Map;

// Global exception handler for REST API.
// Converts TodoNotFoundException into a proper 404 response instead of a 500.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleTodoNotFound(TodoNotFoundException ex, HttpServletRequest request) {
//        return ResponseEntity.status(404).body(Map.of(
//                "status", 404,
//                "error", "Not Found",
//                "message", ex.getMessage()

        return new ErrorMessage(ex.getMessage(), request.getRequestURL().toString(), Instant.now().toString());
    }
}