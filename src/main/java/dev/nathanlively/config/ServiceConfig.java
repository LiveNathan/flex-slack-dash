package dev.nathanlively.config;

import dev.nathanlively.adapter.out.eclipse.EclipseAccountAdapter;
import dev.nathanlively.adapter.out.eclipse.EclipseTaskAdapter;
import dev.nathanlively.application.ReadTask;
import dev.nathanlively.application.RegisterAccount;
import dev.nathanlively.application.UpdateTask;
import dev.nathanlively.application.port.AccountRepository;
import dev.nathanlively.application.port.TaskRepository;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public AccountRepository accountRepository(EmbeddedStorageManager storageManager) {
        return new EclipseAccountAdapter(storageManager);
    }

    @Bean
    public TaskRepository taskRepository(AccountRepository accountRepository) {
        return new EclipseTaskAdapter(accountRepository);
    }

    @Bean
    public RegisterAccount registerAccount(AccountRepository accountRepository) {
        return new RegisterAccount(accountRepository);
    }

    @Bean
    public ReadTask readTask(TaskRepository taskRepository) {
        return new ReadTask(taskRepository);
    }

    @Bean
    public UpdateTask updateTask(TaskRepository taskRepository) {
        return new UpdateTask(taskRepository);
    }
}
