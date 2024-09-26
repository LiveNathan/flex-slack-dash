package dev.nathanlively.domain;

public sealed interface Blocker permits TaskBlocker, TextBlocker {
    String getDescription();  // Get description for displaying
}