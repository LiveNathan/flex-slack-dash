package dev.nathanlively.domain;

public final class TaskBlocker implements Blocker {
    private final Task blockingTask;

    public TaskBlocker(Task blockingTask) {
        this.blockingTask = blockingTask;
    }

    @Override
    public String getDescription() {
        return "#" + blockingTask.id();  // Return task ID or other relevant info
    }

    public Task getBlockingTask() {
        return blockingTask;
    }
}
