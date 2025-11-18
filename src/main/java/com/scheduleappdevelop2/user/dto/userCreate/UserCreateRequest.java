package com.scheduleappdevelop2.user.dto.userCreate;

public class UserCreateRequest {

    private final String name;
    private final String email;

    public UserCreateRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    //getter
    public String getName() { return name; }
    public String getEmail() { return email; }
}
