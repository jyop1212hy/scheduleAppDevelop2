package com.scheduleappdevelop2.user.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 로그인 요청을 받을 때 사용되는 DTO
 * - 클라이언트가 보낸 이메일(email)과 비밀번호(password)를 담는다.
 * - Controller에서 @Valid와 함께 사용되어 입력값 검증을 수행한다.
 * - 엔티티(User)를 직접 받지 않고 DTO로 분리함으로써 보안성과 확장성을 확보한다.
 */
public class LoginRequest {

    /**
     * 사용자가 입력한 이메일
     * - null 또는 빈 문자열이면 안 되고
     * - 반드시 이메일 형식이어야 함
     * - @Valid 검증 시 자동으로 체크됨
     */
    @NotBlank(message = "이메일을 다시 입력해 주세요")
    @Email(message = "이메일 형태로 입력해 주세요")
    private final String email;

    /**
     * 사용자가 입력한 비밀번호
     * - null 또는 빈 문자열이면 안 됨
     * - 패턴 검증이 필요한 경우 추가 가능 (ex: @Size)
     */
    @NotBlank(message = "비밀번호를 다시 입력해 주세요")
    private final String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //getter
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
