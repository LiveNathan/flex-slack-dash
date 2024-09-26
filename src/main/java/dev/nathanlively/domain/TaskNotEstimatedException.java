package dev.nathanlively.domain;

public class TaskNotEstimatedException extends TaskException {
    public TaskNotEstimatedException() {
        super("Cannot start task before it is estimated.");
    }
}
