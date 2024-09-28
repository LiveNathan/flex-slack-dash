package dev.nathanlively.application;

import dev.nathanlively.application.port.AccountRepository;
import dev.nathanlively.domain.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<String, Account> accounts;

    public InMemoryAccountRepository(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public static InMemoryAccountRepository create(Map<String, Account> accounts) {
        return new InMemoryAccountRepository(accounts);
    }

    public static AccountRepository createEmpty() {
        return create(new HashMap<>());
    }

    @Override
    public void save(Account account) {
        accounts.put(account.username(), account);
    }

    @Override
    public Account findByUsername(String username) {
        return accounts.get(username);
    }

    @Override
    public List<Account> findAll() {
        return List.copyOf(accounts.values());
    }
}
