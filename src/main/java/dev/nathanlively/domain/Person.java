package dev.nathanlively.domain;

public class Person extends Resource {
    private final String email;

    protected Person(String name, String email) {
        super(name);
        this.email = email;
    }


}
