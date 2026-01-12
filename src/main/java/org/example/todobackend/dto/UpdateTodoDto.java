package org.example.todobackend.dto;

import lombok.NonNull;

public record UpdateTodoDto(@NonNull String title, @NonNull String description) {
}
