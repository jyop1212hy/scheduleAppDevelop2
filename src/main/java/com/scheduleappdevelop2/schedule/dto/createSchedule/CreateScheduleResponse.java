package com.scheduleappdevelop2.schedule.dto.createSchedule;

import com.scheduleappdevelop2.schedule.entity.Schedule;
import java.time.LocalDateTime;

/**
 * CreateScheduleResponse
 * - 일정 생성 후 클라이언트에게 전달되는 응답 DTO.
 * - 엔터티에서 필요한 정보만 추려 안전하게 반환한다.
 * - 모든 필드는 불변이며, from() 메서드로만 생성한다.
 */
public class CreateScheduleResponse {

    private final Long id;                // 일정 ID
    private final String title;           // 일정 제목
    private final String content;         // 일정 내용
    private final String userEmail;     // 작성자 이메일
    private final LocalDateTime createdAt; // 생성 시간
    private final LocalDateTime modifiedAt; // 수정 시간

    /**
     * 외부에서 직접 객체를 만들지 못하도록 private 생성자로 막아놓았다.
     * - 대신 from() 정적 메서드를 통해서만 응답 객체를 만들 수 있도록 강제한다.
     */
    private CreateScheduleResponse(Long id, String title, String content, String userEmail, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userEmail = userEmail;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    /**
     * 엔터티 → Response DTO 변환 담당 메서드
     * - 서비스 계층에서 Schedule 엔터티를 받아 DTO로 변환할 때 사용한다.
     * - 응답 DTO를 어떻게 만들지는 DTO가 스스로 책임지도록 설계한 패턴.
     */
    public static CreateScheduleResponse from(Schedule schedule) {
       return new CreateScheduleResponse(
               schedule.getId(),
               schedule.getTitle(),
               schedule.getContent(),
               schedule.getUser().getEmail(),
               schedule.getCreatedAt(),
               schedule.getModifiedAt()
        );
    }

    //getter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getUserEmail() { return userEmail; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getModifiedAt() { return modifiedAt; }
}
