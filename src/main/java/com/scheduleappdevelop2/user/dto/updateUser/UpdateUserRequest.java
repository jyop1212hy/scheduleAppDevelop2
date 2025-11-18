package com.scheduleappdevelop2.user.dto.updateUser;

public class UpdateUserRequest {

    private final String name;
    private final String email;

    public UpdateUserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    //getter
    public String getName() { return name; }
    public String getEmail() { return email; }
}
