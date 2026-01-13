package org.example.todobackend.dto;

import lombok.NonNull;
import lombok.With;

@With
public record RegTodoDto(@NonNull String title, @NonNull String description) {
}
