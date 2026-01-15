package org.example.todobackend.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;

import javax.naming.NotContextException;

// Custom exception used when a TodoObject with a given id does not exist.
    public class TodoNotFoundException extends RuntimeException {

        public TodoNotFoundException(String id) {
            // Message helps debugging and can be returned to the client
            super("Todo with ID:" +id + " not found!");
        }
}
