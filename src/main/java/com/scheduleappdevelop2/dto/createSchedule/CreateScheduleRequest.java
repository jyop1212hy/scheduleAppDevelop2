package com.scheduleappdevelop2.dto.createSchedule;

/**
 * CreateScheduleRequest
 * - 일정 생성 시 클라이언트가 보내는 데이터(요청 바디)를 담는 DTO.
 * - title / content / writer 3가지 값만 전달받는다.
 * - 불변 객체로 만들기 위해 모든 필드를 final 로 선언했다.
 * - 생성자를 통해서만 값이 세팅되며, setter는 없다.
 * - 스프링이 JSON 요청 바디를 이 생성자의 파라미터 이름에 맞춰 자동 매핑해준다.
 */
public class CreateScheduleRequest {

    /** 일정 제목 */
    private final String title;

    /** 일정 내용 */
    private final String content;

    /** 작성자 */
    private final String writer;

    /**
     * 요청 JSON 데이터를 매핑할 생성자
     * - 스프링이 title, content, writer 값을 여기에 자동으로 넣어준다.
     * - DTO를 불변으로 유지하기 위해 생성자를 사용한다.
     */
    public CreateScheduleRequest(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    //getter
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWriter() { return writer; }
}
