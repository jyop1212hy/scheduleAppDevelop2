package com.scheduleappdevelop2.user.dto.userCreate;

import com.scheduleappdevelop2.user.entity.User;
import java.time.LocalDateTime;

/**
 * UserCreateResponse
 * - 회원가입 성공 후 클라이언트에 반환되는 DTO.
 * - User 엔티티 전체를 노출하지 않고 필요한 필드만 선택해 전달한다.
 * - 불변 객체로 유지하여 안정성 증가.
 */
public class UserCreateResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private UserCreateResponse(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserCreateResponse from(User user) {
        return new UserCreateResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    //getter
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getModifiedAt() { return modifiedAt; }
}
