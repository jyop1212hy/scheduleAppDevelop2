package com.scheduleappdevelop2.dto.createSchedule;

import com.scheduleappdevelop2.entity.Schedule;
import java.time.LocalDateTime;

/**
 * CreateScheduleResponse
 * - 일정 생성 후 클라이언트에게 돌려주는 응답 DTO.
 * - 엔터티 전체를 그대로 내려주지 않고, 필요한 정보만 추려서 전달하는 역할을 한다.
 * - 불변 객체로 유지하기 위해 모든 필드를 final 로 선언하고 생성자를 private 으로 막았다.
 * - 실제 매핑 작업은 정적 팩토리 메서드 from() 이 담당한다.
 */
public class CreateScheduleResponse {

    /** 생성된 일정의 DB PK(id) */
    private final Long id;

    /** 일정 제목 */
    private final String title;

    /** 일정 내용 */
    private final String content;

    /** 작성자 */
    private final String writer;

    /** 생성 시간 (BaseTimeEntity에서 자동 기록) */
    private final LocalDateTime createdAt;

    /** 수정 시간 (BaseTimeEntity에서 자동 기록) */
    private final LocalDateTime modifiedAt;

    /**
     * 외부에서 직접 객체를 만들지 못하도록 private 생성자로 막아놓았다.
     * - 대신 from() 정적 메서드를 통해서만 응답 객체를 만들 수 있도록 강제한다.
     */
    private CreateScheduleResponse(Long id, String title, String content, String writer, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
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
