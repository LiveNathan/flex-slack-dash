package dev.nathanlively.domain;

public class DataRoot {
    private final Accounts accounts = new Accounts();
    private final Tasks tasks = new Tasks();

    public DataRoot() {
        super();
    }

    public Accounts accounts() {
        return accounts;
    }

    public Tasks tasks() {
        return tasks;
    }
}
