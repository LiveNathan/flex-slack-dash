package dev.nathanlively.adapter.in.web.mytasks;

import dev.nathanlively.domain.Task;
import jakarta.validation.constraints.NotBlank;

public class TaskDto {
    @NotBlank private String title;

    public TaskDto() {}

    public @NotBlank String getTitle() {
        return title;
    }

    public TaskDto(String title) {
        this.title = title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public static TaskDto from(Task task) {
        return new TaskDto(task.title());
    }
}
