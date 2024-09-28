package dev.nathanlively.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import dev.nathanlively.application.port.AccountRepository;
import dev.nathanlively.domain.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticatedUser {

    private final AuthenticationContext authenticationContext;
    private final AccountRepository accountRepository;

    public AuthenticatedUser(AuthenticationContext authenticationContext, AccountRepository accountRepository) {
        this.authenticationContext = authenticationContext;
        this.accountRepository = accountRepository;
    }

    public Optional<Account> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> accountRepository.findByUsername(userDetails.getUsername()));
    }

    public void logout() {
        authenticationContext.logout();
    }

}
