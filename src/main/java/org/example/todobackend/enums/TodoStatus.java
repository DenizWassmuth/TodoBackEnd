package org.example.todobackend.enums;

public enum TodoStatus {
    DOING("todo"),
    DONE("done");

    private final String code;

    TodoStatus(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static TodoStatus fromCode(String raw) {
        if (raw == null) return null;
        String normalized = raw.trim().toLowerCase();

        // values() contains the enumerations of this class in order they are declared
        for (TodoStatus todoStatus : values()) {
            if (todoStatus.code.equals(normalized)) {
                return todoStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status code: " + raw);
    }
}
