package dev.nathanlively.domain;

public class Person extends Resource {
    private String email;
    private JobTitle jobTitle;

    protected Person(String name, String email, JobTitle jobTitle) {
        super(name);
        this.email = email;
        this.jobTitle = jobTitle;
    }

    public JobTitle jobTitle() {
        return jobTitle;
    }
}
