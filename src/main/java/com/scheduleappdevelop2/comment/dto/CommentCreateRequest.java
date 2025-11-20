package com.scheduleappdevelop2.comment.dto;

public class CommentCreateRequest {
    private final String content;

    public CommentCreateRequest(String content) {
        this.content = content;
    }

    public String getContent() { return content; }
}
