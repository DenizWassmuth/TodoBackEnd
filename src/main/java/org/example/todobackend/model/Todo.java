package org.example.todobackend.model;

import lombok.NonNull;
import lombok.With;
import org.example.todobackend.enums.TodoStatus;

import java.time.Instant;

@With
public record Todo(@NonNull String id, @NonNull String title, @NonNull String description, Instant timestamp, TodoStatus status) {
}
