package dev.nathanlively.application.port;

import dev.nathanlively.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskRepository {
    void save(Task task, String username);

    Page<Task> findAllByUsername(String username, Pageable pageable);
}
