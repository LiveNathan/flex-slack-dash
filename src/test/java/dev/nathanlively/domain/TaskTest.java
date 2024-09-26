package dev.nathanlively.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTest {
    @Test
    void create() throws Exception {
        Person person = new Person("Nathan", "nathanlively@gmail.com");
        MyClock fixedClock = FixedClockFactory.feb2at9am();

        Task actual = Task.create("banana22", "Task title", person, fixedClock);

        assertThat(actual.title())
                .isNotNull();
    }
}