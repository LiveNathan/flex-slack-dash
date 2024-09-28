package dev.nathanlively.application;

import dev.nathanlively.adapter.in.web.registration.UserDto;
import dev.nathanlively.application.port.AccountRepository;
import dev.nathanlively.domain.Account;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

public class RegisterAccount {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RegisterAccount(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public RichResult<Account> with(UserDto userDto) {
        Objects.requireNonNull(userDto);
        if (accountRepository.findByUsername(userDto.getEmail()) != null) {
            return RichResult.failure("Username already exists.");
        }
        Account account = userDto.toDomain(passwordEncoder);
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            return RichResult.failure("Problem saving user: " + e.getMessage());
        }
        return RichResult.success(account);
    }
}
