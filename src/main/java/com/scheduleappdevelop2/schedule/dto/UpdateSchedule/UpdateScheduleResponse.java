package com.scheduleappdevelop2.schedule.dto.UpdateSchedule;

import com.scheduleappdevelop2.schedule.entity.Schedule;
import java.time.LocalDateTime;

/**
 * UpdateScheduleResponse
 * - 일정 수정 후 클라이언트에게 돌려주는 응답 DTO.
 * - 수정된 엔터티 내용을 안전하게 담아 반환한다.
 * - 모든 필드는 불변이며, from()으로만 생성한다.
 */
public class UpdateScheduleResponse {

    private final Long id;                  // 일정 ID
    private final String title;             // 제목
    private final String content;           // 내용
    private final String userEmail;         // 작성자 이메일
    private final LocalDateTime createdAt;  // 생성 시간
    private final LocalDateTime modifiedAt; // 수정 시간

    /** 외부 직접 생성 금지 */
    private UpdateScheduleResponse(Long id, String title, String content, String userEmail, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userEmail = userEmail;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    /** 엔터티 → DTO 변환 */
    public static UpdateScheduleResponse from(Schedule schedule) {
        return new UpdateScheduleResponse(
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
