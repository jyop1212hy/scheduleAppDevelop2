package com.scheduleappdevelop2.user.dto.login;

import com.scheduleappdevelop2.user.entity.User;
import java.time.LocalDateTime;

/**
 * 로그인 성공 후 클라이언트에게 반환되는 응답 DTO
 * - User 엔티티 전체를 노출하지 않고 필요한 정보만 선택해서 제공한다.
 * - 응답 정보는 불변(immutable)하게 유지하여 안정성과 일관성을 높인다.
 */
public class LoginResponse {

    /**
     * 로그인한 사용자의 고유 ID
     * - 주로 클라이언트에서 사용자 식별이 필요할 때 사용
     */
    private final Long id;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    /**
     * 모든 필드를 초기화하는 생성자
     * - DTO는 불변객체로 유지하는 것이 좋기 때문에 setter를 제공하지 않는다.
     */
    public LoginResponse(Long id, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    /**
     * User 엔티티에서 필요한 정보만 추출하여 LoginResponse로 변환하는 정적 팩토리 메서드
     * - 서비스 레이어에서 호출 시 코드 가독성이 좋아지고 유지보수가 쉬워진다.
     */
    public static LoginResponse from(User user) {
        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getModifiedAt() { return modifiedAt; }

}
