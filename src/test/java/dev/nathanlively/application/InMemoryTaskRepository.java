package dev.nathanlively.application;

import dev.nathanlively.application.port.AccountRepository;
import dev.nathanlively.application.port.TaskRepository;
import dev.nathanlively.domain.Account;
import dev.nathanlively.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {
    private final AccountRepository accountRepository;

    public InMemoryTaskRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public static InMemoryTaskRepository createEmpty(AccountRepository accountRepository) {
        return new InMemoryTaskRepository(accountRepository);
    }

    @Override
    public void save(Task task, String username) {
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.person() != null) {
            account.person().tasks().add(task);
            // Re-save the account so in-memory store reflects changes.
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
}
