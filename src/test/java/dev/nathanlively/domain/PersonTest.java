package dev.nathanlively.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonTest {
    private final MyClock fixedClock = FixedClockFactory.feb2at9am();
    @BeforeEach
    void setUp() {
        Tasks.getInstance().clear(); // to avoid cross-test pollution
    }

    @Test
    void addTaskAlsoAddToTasks() {
        String name = "Travis";
        String email = "travsi@micework.ch";
        Person person = Person.create(name, email);
        String taskId = "banana22";
        Task task = Task.create(taskId, "Task title", person, fixedClock);

        person.addTask(task);

        assertThat(person.tasks()).contains(task);
        Task fetchedTask = Tasks.getInstance().byId(taskId);
        assertThat(fetchedTask).isNotNull();
    }

}