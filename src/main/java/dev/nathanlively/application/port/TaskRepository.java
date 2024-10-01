package dev.nathanlively.application.port;

import dev.nathanlively.domain.Task;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    void create(Task task, String username);

    Page<Task> findAllByUsername(String username, Pageable pageable);

    List<Task> findAll();

    void update(Task task);

    Optional<Task> findById(@NotBlank String id);
}
