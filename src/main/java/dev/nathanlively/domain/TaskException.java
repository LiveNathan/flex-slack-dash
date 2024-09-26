package dev.nathanlively.domain;

public class TaskException extends RuntimeException {
    public TaskException(String message) {
        super(message);
    }
}
