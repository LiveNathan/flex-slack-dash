package dev.nathanlively.config;

import dev.nathanlively.adapter.out.eclipse.EclipseAccountAdapter;
import dev.nathanlively.application.RegisterAccount;
import dev.nathanlively.application.port.AccountRepository;
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
    public RegisterAccount registerAccount(AccountRepository accountRepository) {
        return new RegisterAccount(accountRepository);
    }
}
