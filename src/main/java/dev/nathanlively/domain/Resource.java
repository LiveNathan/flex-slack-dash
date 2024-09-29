package dev.nathanlively.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Resource extends Named{
    private final List<Task> tasks;

    protected Resource(String name) {
        super(name);
        this.tasks = new ArrayList<>();
    }

    public List<Task> tasks() {
        return tasks;
    }
}
