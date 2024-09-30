package dev.nathanlively.application;

import dev.nathanlively.adapter.in.web.mytasks.TaskDto;
import dev.nathanlively.application.port.AccountRepository;
import dev.nathanlively.application.port.TaskRepository;
import dev.nathanlively.domain.Person;
import dev.nathanlively.domain.Task;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateTaskTest {
    @Test
    void with() throws Exception {
        AccountRepository accountRepository = InMemoryAccountRepository.createEmpty();
        TaskRepository taskRepository = InMemoryTaskRepository.createEmpty(accountRepository);
        UpdateTask updateTask = new UpdateTask(taskRepository);
        String username = "travsi@micework.ch";
        String id = "22-swanson";
        String taskTitle = "Task title";
        TaskDto taskDto = new TaskDto(taskTitle, id);
        String email = "travsi@micework.ch";
        String password = "<PASSWORD>";
        String name = "Travis";
        Person person = Person.create(name, email);
        Task expected = Task.create(id, taskTitle, person);

        RichResult<Task> actual = updateTask.with(taskDto);

        ResultAssertions.assertThat(actual).isSuccess();
        ResultAssertions.assertThat(actual).failureMessages().isEmpty();
        ResultAssertions.assertThat(actual).successValues().contains(expected);

        assertThat(taskRepository.findAll()).hasSize(1);
    }

}