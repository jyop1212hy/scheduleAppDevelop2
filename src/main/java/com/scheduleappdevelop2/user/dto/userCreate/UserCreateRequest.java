package com.scheduleappdevelop2.user.dto.userCreate;

/**
 * UserCreateRequest
 * - 회원가입 요청에서 전달되는 데이터(name, email, password)를 담는 DTO.
 * - 엔티티를 직접 받지 않고 DTO로 분리하여 보안을 강화한다.
 * - JSON 요청 바디를 생성자 파라미터로 자동 매핑해 사용한다.
 */
public class UserCreateRequest {

    private final String name;     // 입력한 이름
    private final String email;    // 입력한 이메일
    private final String password; // 입력한 비밀번호

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
