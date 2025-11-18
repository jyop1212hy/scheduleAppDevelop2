package com.scheduleappdevelop2.user.dto.userCreate;

public class UserCreateRequest {

    private final String name;
    private final String email;
    private final String password;

    public UserCreateRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    //getter
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
