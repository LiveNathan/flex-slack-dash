package dev.nathanlively.services;

import dev.nathanlively.domain.Task;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

//    private final TaskRepository repository;
//
//    public TaskService(TaskRepository repository) {
//        this.repository = repository;
//    }
//
    public Optional<Task> get(Long id) {
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
//
//    public Page<Task> list(Pageable pageable) {
//        return repository.findAll(pageable);
//    }
//
//    public Page<Task> list(Pageable pageable, Specification<Task> filter) {
//        return repository.findAll(filter, pageable);
//    }
//
//    public int count() {
//        return (int) repository.count();
//    }

}
