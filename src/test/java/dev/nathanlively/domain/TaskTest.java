package dev.nathanlively.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTest {
    @Test
    void create() {
        Person person = new Person("Nathan", "nathanlively@gmail.com");
        MyClock fixedClock = FixedClockFactory.feb2at9am();

        Task actual = Task.create("banana22", "Task title", person, fixedClock);

        assertThat(actual.id()).isEqualTo("banana22");
        assertThat(actual.title()).isEqualTo("Task title");
        assertThat(actual.requester()).isEqualTo(person);
        assertThat(actual.owners()).contains(person);
        assertThat(actual.status()).isEqualTo(TaskStatus.UNSTARTED);
        assertThat(actual.hoursEstimate()).isEqualTo(0.0f);
        assertThat(actual.priority()).isEqualTo(Priority.P3_LOW);
        assertThat(actual.followers()).contains(person);
        assertThat(actual.createdAt()).isEqualTo(fixedClock.now());
        assertThat(actual.modifiedAt()).isEqualTo(fixedClock.now());
    }
}