package dev.nathanlively.adapter.out.eclipse;

import dev.nathanlively.application.port.AccountRepository;
import dev.nathanlively.application.port.TaskRepository;
import dev.nathanlively.domain.Account;
import dev.nathanlively.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class EclipseTaskAdapter implements TaskRepository {
    private final AccountRepository accountRepository;

    public EclipseTaskAdapter(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void save(Task task, String username) {

    }

    @Override
    public Page<Task> findAllByUsername(String username, Pageable pageable) {
        Account account = accountRepository.findByUsername(username);

        if (account == null) {
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
