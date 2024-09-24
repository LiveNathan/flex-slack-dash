package dev.nathanlively.data;

public interface UserRepository {

    User findByUsername(String username);
}
