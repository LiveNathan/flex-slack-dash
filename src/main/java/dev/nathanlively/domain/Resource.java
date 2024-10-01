package dev.nathanlively.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Resource extends Named {
    private final List<Task> tasks;

    protected Resource(String name) {
        super(name);
        this.tasks = new ArrayList<>();
    }

    public List<Task> tasks() {
        return tasks;
    }

    public void addTask(Task task) {
        Objects.requireNonNull(task, "Task cannot be null");
        tasks.add(task);
        Tasks.getInstance().add(task);
    }
}
