package dev.nathanlively.domain;

import java.util.Objects;

public class ValidationUtilities {
    private ValidationUtilities() {
        // Private constructor to prevent instantiation
    }

    public static String validateNotBlank(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    public static float validateNonNegative(float value, String message) {
        if (value < 0) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static <T> T requireNonNull(T obj, String message) {
        return Objects.requireNonNull(obj, message);
    }
}
