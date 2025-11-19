package com.scheduleappdevelop2.schedule.dto.checkSchedule;

import com.scheduleappdevelop2.schedule.entity.Schedule;
import java.time.LocalDateTime;

/**
 * ScheduleResponse
 * - 일정 조회 시 클라이언트에게 전달되는 응답 DTO.
 * - 전체/단건 조회 모두 이 DTO를 사용한다.
 * - 엔터티를 그대로 노출하지 않고 필요한 정보만 추려 제공한다.
 */
public class ScheduleResponse {

    private final Long id;                // 일정 ID
    private final String title;           // 제목
    private final String content;         // 내용
    private final String userEmail;     // 작성자 이메일
    private final LocalDateTime createdAt; // 생성 날짜
    private final LocalDateTime modifiedAt; // 수정 날짜

    /**
     * 외부에서 직접 객체 생성을 하지 못하게 막은 private 생성자.
     * - 응답 DTO는 반드시 from() 정적 메서드로만 만들도록 강제하는 패턴.
     */
    private ScheduleResponse(Long id, String title, String content, String userEmail, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userEmail = userEmail;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    /**
     * 엔터티 → DTO 변환용 정적 메서드
     * - 조회 시 서비스 계층에서 Schedule 엔터티를 받고,
     *   이 메서드를 통해 안전하게 DTO로 변환해 클라이언트에게 전달한다.
     */
    public static ScheduleResponse from(Schedule schedule) {
        return new ScheduleResponse(
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