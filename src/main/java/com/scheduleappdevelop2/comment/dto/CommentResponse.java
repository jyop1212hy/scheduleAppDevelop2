package com.scheduleappdevelop2.comment.dto;

import com.scheduleappdevelop2.comment.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponse {

    private final Long id;
    private final String content;
    private final String userEmail;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private CommentResponse(Long id, String content, String userEmail, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.content = content;
        this.userEmail = userEmail;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getEmail(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public String getUserEmail() { return userEmail; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getModifiedAt() { return modifiedAt; }
}
