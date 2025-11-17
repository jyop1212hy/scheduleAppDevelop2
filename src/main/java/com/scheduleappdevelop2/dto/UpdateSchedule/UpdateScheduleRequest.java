package com.scheduleappdevelop2.dto.UpdateSchedule;

import com.scheduleappdevelop2.entity.Schedule;

/**
 * UpdateScheduleRequest
 * - 일정 수정(PATCH) 요청 시 들어오는 데이터 DTO.
 * - 수정은 선택값이므로(title, content, writer) null일 수도 있다.
 * - final + private 생성자로 불변 형태 유지, JSON 역직렬화로만 생성된다.
 */

public class UpdateScheduleRequest {

    // 수정할 제목 (null 가능)
    private final String title;

    // 수정할 내용 (null 가능)
    private final String content;

    // 수정할 작성자 (null 가능)
    private final String writer;

    // 외부에서 직접 생성 못하게 private
    public UpdateScheduleRequest(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    //getter
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWriter() { return writer; }
}


