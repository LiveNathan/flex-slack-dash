package dev.nathanlively.application;

import dev.nathanlively.application.port.AccountRepository;
import dev.nathanlively.application.port.TaskRepository;
import dev.nathanlively.domain.Account;
import dev.nathanlively.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InMemoryTaskRepository implements TaskRepository {
    private final AccountRepository accountRepository;

    public InMemoryTaskRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public static InMemoryTaskRepository createEmpty(AccountRepository accountRepository) {
        return new InMemoryTaskRepository(accountRepository);
    }

    @Override  // create an existing task
    public void update(Task task) {
        Objects.requireNonNull(task);
        Objects.requireNonNull(task.id(), "ID must not be null");
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.empty();
    }

    @Override  // for new tasks
    public void create(Task task, String username) {
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.person() != null) {
            account.person().tasks().add(task);
            // Re-create the account so in-memory store reflects changes.
            accountRepository.save(account);
        }
    }

    @Override
    public Page<Task> findAllByUsername(String username, Pageable pageable) {
        Account account = accountRepository.findByUsername(username);

        if (account == null || account.person() == null) {
            return Page.empty(pageable);
        }

        List<Task> tasks = account.person().tasks();

        int totalElements = tasks.size();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Task> pagedTasks;

        if (tasks.size() < startItem) {
            pagedTasks = List.of();
        } else {
            int toIndex = Math.min(startItem + pageSize, tasks.size());
            pagedTasks = tasks.subList(startItem, toIndex);
        }

        return new PageImpl<>(pagedTasks, pageable, totalElements);
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        accountRepository.findAll().forEach(account -> {tasks.addAll(account.person().tasks());});
        return tasks;
    }


}
