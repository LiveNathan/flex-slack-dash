package dev.nathanlively.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
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

    public Task() {
    }

    private Task(String id, String title, String description, Person requester, Set<Person> owners, Instant dueDate,
                 float hoursEstimate, Set<Tag> tags, Priority priority, Set<Person> followers,
                 List<Blocker> blockers, Instant createdAt, List<Comment> comments) {

        this.id = ValidationUtilities.validateNotBlank(id, "ID must not be blank");
        this.title = ValidationUtilities.validateNotBlank(title, "Title must not be blank");
        this.description = description;
        this.requester = ValidationUtilities.requireNonNull(requester, "Requester must not be null");
        this.owners = owners == null ? Set.of(requester) : new HashSet<>(owners);
        this.dueDate = dueDate;
        this.status = TaskStatus.UNSTARTED;
        this.hoursEstimate = ValidationUtilities.validateNonNegative(hoursEstimate, "Hours estimate must not be negative");
        this.tags = tags == null ? new HashSet<>() : new HashSet<>(tags);
        this.priority = priority == null ? Priority.P3_LOW : priority;
        this.followers = followers == null ? new HashSet<>(Set.of(requester)) : new HashSet<>(followers);
        this.blockers = blockers == null ? new ArrayList<>() : new ArrayList<>(blockers);
        this.createdAt = ValidationUtilities.requireNonNull(createdAt, "CreatedAt must not be null");
        this.modifiedAt = createdAt;
        this.comments = comments == null ? new ArrayList<>() : new ArrayList<>(comments);
    }

    public static Task create(String id, String title, Person creator) {
        return new Task(id, title, null, creator, null, null, 0.0f, null,
                null, null, null, new MySystemClock().now(), null);
    }

    public static Task create(String id, String title, Person creator, MyClock clock) {
        return new Task(id, title, null, creator, null, null, 0.0f, null,
                null, null, null, clock.now(), null);
    }

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

    public String title() {
        return title;
    }

    public Person requester() {
        return requester;
    }

    public Set<Person> owners() {
        return owners;
    }

    public TaskStatus status() {
        return status;
    }

    public float hoursEstimate() {
        return hoursEstimate;
    }

    public Priority priority() {
        return priority;
    }

    public Set<Person> followers() {
        return followers;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant modifiedAt() {
        return modifiedAt;
    }

    public void start() {
        if (this.hoursEstimate == 0.0f) {
            throw new TaskNotEstimatedException();
        }
        this.status = TaskStatus.STARTED;
    }

    public void estimate(float estimatedHours) {
        this.hoursEstimate = ValidationUtilities.validateNonNegative(estimatedHours, "Hours estimate must not be negative");    }
}
