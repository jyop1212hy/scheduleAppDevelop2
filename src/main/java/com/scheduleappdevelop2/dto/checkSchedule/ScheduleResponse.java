package com.scheduleappdevelop2.dto.checkSchedule;

import com.scheduleappdevelop2.entity.Schedule;
import java.time.LocalDateTime;

/**
 * ScheduleResponse
 * - 일정 조회(전체/단건) 시 클라이언트에게 전달되는 응답 DTO.
 * - 엔터티를 그대로 노출하지 않고, 필요한 필드만 안전하게 추려서 반환한다.
 * - 모든 필드를 final 로 선언하여 불변성을 유지하고,
 *   생성자는 private으로 막아 외부에서 마음대로 만들지 못하게 했다.
 */
public class ScheduleResponse {

    /** 일정의 고유 ID(PK) */
    private final Long id;

    /** 일정 제목 */
    private final String title;

    /** 일정 내용 */
    private final String content;

    /** 작성자 */
    private final String writer;

    /** 생성 시간 */
    private final LocalDateTime createdAt;

    /** 수정 시간 */
    private final LocalDateTime modifiedAt;

    /**
     * 외부에서 직접 객체 생성을 하지 못하게 막은 private 생성자.
     * - 응답 DTO는 반드시 from() 정적 메서드로만 만들도록 강제하는 패턴.
     */
    private ScheduleResponse(Long id, String title, String content, String writer, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
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
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    //getter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWriter() { return writer; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getModifiedAt() { return modifiedAt; }

}