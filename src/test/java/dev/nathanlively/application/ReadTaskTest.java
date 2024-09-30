package dev.nathanlively.application;

import dev.nathanlively.adapter.in.web.mytasks.TaskDto;
import dev.nathanlively.application.port.AccountRepository;
import dev.nathanlively.application.port.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

class ReadTaskTest {
    @Test
    void all() {
        AccountRepository accountRepository = InMemoryAccountRepository.createEmpty();
        TaskRepository taskRepository = InMemoryTaskRepository.createEmpty(accountRepository);
        ReadTask readTask = new ReadTask(taskRepository);
        Pageable pageable = Pageable.ofSize(10);
        String username = "travsi@micework.ch";

        Page<TaskDto> actual = readTask.all(pageable, username);

        assertThat(actual).isEmpty();
    }
}