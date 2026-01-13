package org.example.todobackend.exceptions;

// Custom exception used when a TodoObject with a given id does not exist.
// We throw it in the service layer, and map it to HTTP 404 in a controller advice.
    public class TodoNotFoundException extends RuntimeException {

        public TodoNotFoundException(String id) {
            // Message helps debugging and can be returned to the client
            super("Todo not found: " + id);
        }
}
