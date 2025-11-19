package com.scheduleappdevelop2.user.dto.sessionUser;

/**
 * SessionUser
 * - 실제 User 엔티티의 일부 정보(id, email)만 담아 세션에 저장하는 DTO.
 * - 엔티티 전체를 세션에 넣지 않기 위해 만든 가벼운 객체.
 * - 로그인 유지 및 권한 검사 시 사용된다.
 */
public class SessionUser {

    private final Long id;     // 로그인 유저 PK
    private final String email; // 로그인 유저 이메일

    public SessionUser(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
}
