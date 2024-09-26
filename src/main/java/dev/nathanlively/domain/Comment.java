package dev.nathanlively.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Comment {
    private Person author;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
    private CommentState state;  // NEW or EDITED
    private List<Attachment> attachments;
    private Set<Reaction> reactions;  // Set of emojis or reactions

    private Comment(Person author, String content, Instant createdAt, Instant updatedAt, List<Attachment> attachments, Set<Reaction> reactions) {
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt == null ? createdAt : updatedAt;
        this.state = CommentState.NEW;
        this.attachments = attachments == null ? new ArrayList<>() : new ArrayList<>(attachments);
        this.reactions = reactions == null ? new HashSet<>() : new HashSet<>(reactions);
    }

    public Comment create(Person author, String content) {
        return new Comment(author, content, new MySystemClock().now(), null, null, null);
    }

    public Comment create(Person author, String content, MyClock clock) {
        return new Comment(author, content, clock.now(), null, null, null);
    }

    public void editContent(String newContent) {
        this.content = newContent;
        this.state = CommentState.EDITED;
        this.updatedAt = Instant.now();
    }

    public void setContent(String newContent) {
        this.content = newContent;
        this.updatedAt = Instant.now();
    }

    // Other methods, getters, setters
}
