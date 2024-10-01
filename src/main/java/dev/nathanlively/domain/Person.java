package dev.nathanlively.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Person extends Resource {
    private String email;
    private JobTitle jobTitle;

    protected Person(String name, String email, JobTitle jobTitle, Tasks tasksManager) {
        super(name);
        this.email = email;
        this.jobTitle = jobTitle;
    }

    public static Person create(@NotBlank String name, @NotBlank @Email String email) {
        return new Person(name, email, null, null);
    }

    public JobTitle jobTitle() {
        return jobTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;
        return email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
