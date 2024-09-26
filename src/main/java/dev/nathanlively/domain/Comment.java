package dev.nathanlively.domain;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public class Comment {
    private String author;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
    private CommentState state;  // NEW or EDITED
    private List<Attachment> attachments;
    private Set<Reaction> reactions;  // Set of emojis or reactions

    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
        this.createdAt = Instant.now();
        this.state = CommentState.NEW;
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
