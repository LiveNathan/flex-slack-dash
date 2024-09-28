package dev.nathanlively.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

public class Account {
    private String username;
    @JsonIgnore
    private String hashedPassword;
    private Set<Role> roles;
    private byte[] profilePicture;
    private Person person;

    private Account(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.roles = new HashSet<>(Set.of(Role.USER));
        this.profilePicture = new byte[0];
        this.person = null;
    }

    public static Account create(String username, String hashedPassword) {
        return new Account(username, hashedPassword);
    }

    public String username() {
        return username;
    }

    public Person person() {
        return person;
    }

    public byte[] profilePicture() {
        return profilePicture;
    }

    public String hashedPassword() {
        return hashedPassword;
    }

    public Set<Role> roles() {
        return roles;
    }
}
