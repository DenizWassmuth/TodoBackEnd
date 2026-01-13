package org.example.todobackend.dto;

import lombok.NonNull;
import org.example.todobackend.enums.TodoStatus;

public record UpdateTodoDto(@NonNull String title, @NonNull String description, TodoStatus status) {
}
