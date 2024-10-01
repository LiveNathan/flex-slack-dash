package dev.nathanlively.application;

import dev.nathanlively.adapter.in.web.mytasks.TaskDto;
import dev.nathanlively.application.port.TaskRepository;
import dev.nathanlively.domain.Task;

import java.util.Optional;

public class UpdateTask {
    private final TaskRepository repository;

    public UpdateTask(TaskRepository repository) {
        this.repository = repository;
    }

    public RichResult<Task> with(TaskDto taskDto) {
        Optional<Task> taskToUpdate = repository.findById(taskDto.id());
        if (taskToUpdate.isEmpty()) {
            return RichResult.failure("Task could not be found with id: " + taskDto.id());
        }
        Task task = taskToUpdate.get();
        // create task
        repository.update(task);
        return RichResult.success(task);
    }
}
