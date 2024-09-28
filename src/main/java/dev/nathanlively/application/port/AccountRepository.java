package dev.nathanlively.application.port;

import dev.nathanlively.domain.Account;

import java.util.List;

public interface AccountRepository {
    void save(Account account);

    Account findByUsername(String username);

    List<Account> findAll();
}
