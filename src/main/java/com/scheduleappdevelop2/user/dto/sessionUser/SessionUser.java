package com.scheduleappdevelop2.user.dto.sessionUser;

public class SessionUser {

    private final Long id;
    private final String email;

    public SessionUser(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
}
