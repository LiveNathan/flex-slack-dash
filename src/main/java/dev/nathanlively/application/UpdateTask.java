package dev.nathanlively.application;

import dev.nathanlively.adapter.in.web.mytasks.TaskDto;
import dev.nathanlively.application.port.TaskRepository;
import dev.nathanlively.domain.Task;

public class UpdateTask {
    private final TaskRepository repository;

    public UpdateTask(TaskRepository repository) {
        this.repository = repository;
    }

    public RichResult<Task> with(TaskDto taskDto) {
        return null;
    }
}
