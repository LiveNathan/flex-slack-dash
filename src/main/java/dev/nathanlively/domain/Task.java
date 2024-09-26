package dev.nathanlively.domain;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public class Task {

    private String id;
    private String title;
    private String description;
    private Person requester;  // Person who created the task
    private Set<Person> owners;  // People responsible for the task
    private Instant dueDate;
    private TaskStatus status;
    private float hoursEstimate;
    private Set<Tag> tags;
    private Priority priority;
    private Set<Person> followers;  // People following the task
    private List<Blocker> blockers;  // Blockers for this task (string or task IDs)
    private Instant createdAt;
    private Instant modifiedAt;  // For tracking updates
    private List<Comment> comments;  // Comments with attachments and reactions

    public void updateTaskDetails(String newDescription, Priority newPriority) {
        this.description = newDescription;
        this.priority = newPriority;
        this.modifiedAt = Instant.now();  // Update modification date
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public String id() {
        return id;
    }
}
