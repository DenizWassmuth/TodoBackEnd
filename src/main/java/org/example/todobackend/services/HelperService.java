package org.example.todobackend.services;


import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class HelperService {

    public String createRandomId() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    public Instant getTimestamp() {
        return Instant.now();
    }
}
