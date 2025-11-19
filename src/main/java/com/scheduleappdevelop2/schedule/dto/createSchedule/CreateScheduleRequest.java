package com.scheduleappdevelop2.schedule.dto.createSchedule;

/**
 * CreateScheduleRequest
 * - 일정 생성 시 클라이언트가 보내는 JSON 데이터(요청 바디)를 담는 DTO.
 * - title, content 2가지만 받으며 로그인 유저 정보는 세션에서 따로 가져온다.
 * - 불변성을 위해 모든 필드는 final, 생성자를 통해서만 값이 설정된다.
 */
public class CreateScheduleRequest {

    /** 일정 제목 */
    private final String title;

    /** 일정 내용 */
    private final String content;

    /** JSON 요청 값 매핑용 생성자 */
    public CreateScheduleRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //getter
    public String getTitle() { return title; }
    public String getContent() { return content; }
}
