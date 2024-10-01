package dev.nathanlively.domain;

import java.util.*;

public class Tasks {
    private static final Tasks INSTANCE = new Tasks();
    private final Map<String, Task> taskIdToTask = new HashMap<>();

    public Tasks() {
        super();
    }
    public static Tasks getInstance() {
        return INSTANCE;
    }

    public void add(final Task task) {
        this.taskIdToTask.put(task.id(), task);
    }

    public void addAll(final Collection<? extends Task> tasks) {
        tasks.forEach(this::add);
    }

    public Task byId(String id) {
        return this.taskIdToTask.get(id);
    }

    public List<Task> all() {
        return new ArrayList<>(this.taskIdToTask.values());
    }

    public Set<String> getAllTaskIds() {
        return new TreeSet<>(taskIdToTask.keySet());
    }

    public void clear() {
        this.taskIdToTask.clear();
    }
}
