package dev.nathanlively.application;

import dev.nathanlively.adapter.in.web.mytasks.TaskDto;
import dev.nathanlively.application.port.TaskRepository;
import dev.nathanlively.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReadTask {

    private final TaskRepository repository;

    public ReadTask(TaskRepository repository) {
        this.repository = repository;
    }

    public Optional<TaskDto> get(String id) {
//        return repository.findById(id);
        return null;
    }
//
//    public Task update(Task entity) {
//        return repository.save(entity);
//    }
//
//    public void delete(Long id) {
//        repository.deleteById(id);
//    }

    public Page<TaskDto> all(Pageable pageable, String username) {
        Page<Task> tasks = repository.findAllByUsername(username, pageable);
        return tasks.map(TaskDto::from);
    }

//    public Page<Task> all(Pageable pageable, Specification<Task> filter) {
//        return repository.findAll(filter, pageable);
//    }
//
//    public int count() {
//        return (int) repository.count();
//    }

}
