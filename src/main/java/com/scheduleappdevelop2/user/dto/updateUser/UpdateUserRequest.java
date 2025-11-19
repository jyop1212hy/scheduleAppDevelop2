package com.scheduleappdevelop2.user.dto.updateUser;

/**
 * UpdateUserRequest
 * - 유저 정보 수정(PATCH) 요청에서 전달되는 DTO.
 * - null이 들어올 수 있으며, 서비스에서 null 아닌 필드만 수정한다.
 */
public class UpdateUserRequest {

    private final String name;  // 수정할 이름
    private final String email; // 수정할 이메일

    public UpdateUserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    //getter
    public String getName() { return name; }
    public String getEmail() { return email; }
}
