package com.scheduleappdevelop2.user.dto.userResponse;

import com.scheduleappdevelop2.user.entity.User;
import java.time.LocalDateTime;

/**
 * UserResponse
 * - 유저 조회 시 클라이언트에게 전달되는 DTO.
 * - User 엔티티를 그대로 노출하지 않고 안전하게 필요한 정보만 반환한다.
 */
public class UserResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private UserResponse(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
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
