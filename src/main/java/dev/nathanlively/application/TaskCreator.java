package dev.nathanlively.application;

import com.github.kkuegler.HumanReadableIdGenerator;
import com.github.kkuegler.PermutationBasedHumanReadableIdGenerator;
import dev.nathanlively.domain.Person;
import dev.nathanlively.domain.Task;

public class TaskCreator {
    private final HumanReadableIdGenerator idGen = new PermutationBasedHumanReadableIdGenerator();

    public Task createNewTask(String title, Person creator) {
        String id = idGen.generate();
        Task task = Task.create(id, title, creator);
        return task;
    }
}
