package dev.nathanlively.adapter.in.web.mytasks;

import dev.nathanlively.domain.Task;
import jakarta.validation.constraints.NotBlank;

public class TaskDto {
    @NotBlank private String title;
    @NotBlank private String id;

    public TaskDto() {}

    public @NotBlank String getTitle() {
        return title;
    }

    public TaskDto(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public static TaskDto from(Task task) {
        return new TaskDto(task.title(), task.id());
    }

    public @NotBlank String id() {
        return id;
    }

}
