package dev.nathanlively.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

public class Account {
    private String username;
    @JsonIgnore
    private String hashedPassword;
    private Set<Role> roles;
    private byte[] profilePicture;
    private Person person;

    public String username() {
        return username;
    }
}
