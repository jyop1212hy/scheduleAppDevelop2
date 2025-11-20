package com.scheduleappdevelop2.schedule.dto.UpdateSchedule;

/**
 * UpdateScheduleRequest
 * - 일정 수정(PATCH) 시 클라이언트가 보내는 요청 데이터를 담는다.
 * - 수정은 선택적이므로 title, content는 null이 올 수도 있다.
 * - 불변성을 위해 final + 생성자 방식으로 유지한다.
 */
public class UpdateScheduleRequest {

    private final String title;   // 수정할 제목
    private final String content; // 수정할 내용


    /** JSON 요청 값 매핑용 생성자 */
    public UpdateScheduleRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //getter
    public String getTitle() { return title; }
    public String getContent() { return content; }
}