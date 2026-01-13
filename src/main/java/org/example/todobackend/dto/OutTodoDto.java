package org.example.todobackend.dto;

import lombok.NonNull;
import lombok.With;
import org.example.todobackend.enums.TodoStatus;

import java.time.Instant;

@With
public record OutTodoDto(String id, @NonNull String title, @NonNull String description, Instant timestamp, TodoStatus status) {
}
