package dev.nathanlively.domain;

public class PersonNotManagerException extends TaskException {
    public PersonNotManagerException() {
        super("Person does not have the necessary privileges to accept the task.");
    }
}
