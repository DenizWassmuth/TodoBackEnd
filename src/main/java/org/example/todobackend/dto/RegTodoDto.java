package org.example.todobackend.dto;

import lombok.NonNull;

public record RegTodoDto(@NonNull String title, @NonNull String description) {
}
