package dev.nathanlively.domain;

public enum Priority {
    P0_CRITICAL("P0 - Critical"),
    P1_HIGH("P1 - High"),
    P2_MEDIUM("P2 - Medium"),
    P3_LOW("P3 - Low"),
    NONE("None");

    private final String displayName;

    Priority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
