package org.example.todobackend.model;

import lombok.NonNull;
import lombok.With;

import java.time.Instant;

@With
public record TodoRecord(String id, @NonNull String title, @NonNull String description, Instant timestamp) {
}
