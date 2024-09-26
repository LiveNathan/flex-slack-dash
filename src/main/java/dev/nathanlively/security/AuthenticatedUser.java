package dev.nathanlively.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import dev.nathanlively.data.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticatedUser {

    private final AuthenticationContext authenticationContext;

    public AuthenticatedUser(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    public Optional<User> get() {
//        return authenticationContext.getAuthenticatedUser(UserDetails.class)
//                .map(userDetails -> userRepository.findByUsername(userDetails.getUsername()));
        return null;
    }

    public void logout() {
        authenticationContext.logout();
    }

}
