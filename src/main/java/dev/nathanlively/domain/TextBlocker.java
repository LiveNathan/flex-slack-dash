package dev.nathanlively.domain;

public final class TextBlocker implements Blocker {
    private final String description;

    public TextBlocker(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}