package dev.nathanlively.adapter.in.web.mytasks;

import jakarta.validation.constraints.NotBlank;

public class TaskDto {
    @NotBlank private String title;

    public TaskDto() {}

    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }
}
