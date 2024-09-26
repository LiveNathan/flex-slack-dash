package dev.nathanlively.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TaskTest {

    private final MyClock fixedClock = FixedClockFactory.feb2at9am();
    private final Person person = new Person("Nathan", "nathanlively@gmail.com", JobTitle.TECHNICIAN);

    @Test
    void create() {
        Task actual = Task.create("banana22", "Task title", person, fixedClock);

        assertThat(actual.id()).isEqualTo("banana22");
        assertThat(actual.title()).isEqualTo("Task title");
        assertThat(actual.requester()).isEqualTo(person);
        assertThat(actual.owners()).isEmpty();
        assertThat(actual.status()).isEqualTo(TaskStatus.UNSTARTED);
        assertThat(actual.hoursEstimate()).isEqualTo(0.0f);
        assertThat(actual.priority()).isEqualTo(Priority.P3_LOW);
        assertThat(actual.followers()).contains(person);
        assertThat(actual.createdAt()).isEqualTo(fixedClock.now());
        assertThat(actual.modifiedAt()).isEqualTo(fixedClock.now());
    }

    @Test
    void modifyStatus() throws Exception {
        Task actual = Task.create("banana22", "Task title", person, fixedClock);
        actual.estimate(1f);
        actual.start(person);

        assertThat(actual.status()).isEqualTo(TaskStatus.STARTED);
        assertThat(actual.owners()).contains(person);
    }

    @Test
    void taskMustBeEstimatedBeforeStarted() throws Exception {
        Task actual = Task.create("banana22", "Task title", person, fixedClock);

        assertThatThrownBy(() -> actual.start(person))
                .isInstanceOf(TaskNotEstimatedException.class)
                .hasMessage("Cannot start task before it is estimated.");
    }
    
    @Test
    void onlyManagerCanAcceptReject() throws Exception {
        Task actual = Task.create("banana22", "Task title", person, fixedClock);
        assertThatThrownBy(() -> actual.accept(person))
                .isInstanceOf(PersonNotManagerException.class)
                .hasMessage("Person does not have the necessary privileges to accept the task.");
    }
}