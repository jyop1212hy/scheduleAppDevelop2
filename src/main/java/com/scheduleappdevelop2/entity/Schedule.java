package com.scheduleappdevelop2.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = PROTECTED) // JPA 전용 기본 생성자 (개발자는 사용 X)
public class Schedule {

    /**
     * 고유 식별자(PK)
     * - DB가 자동 증가시키는 값이다.
     * - 개발자가 직접 넣지 않고, 저장 시점에 DB가 채워준다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 일정 제목
     * - 필수 값이며 최대 100자까지 저장 가능
     * - 비어 있으면 DB에서 에러 발생
     */
    @Column(length = 100, nullable = false)
    private String title;

    /**
     * 일정 상세 내용
     * - 길이가 길 수 있으므로 TEXT 타입으로 지정
     * - null 불가능
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    /**
     * 작성자 이름
     * - 필수 값, 최대 100자
     */
    @Column(length = 100, nullable = false)
    private String writer;

    /**
     * 개발자가 직접 사용하는 생성자
     * - id는 자동 생성되므로 받지 않는다.
     * - 필수 값만 입력받아 엔티티 초기화
     */
    @Builder
    private Schedule(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public static Schedule of(String title, String content, String writer) {
        return Schedule.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }

    /**
     * 일정 수정 기능
     * - 들어온 값이 null이 아닐 때만 해당 필드를 변경한다.
     * - 엔터티가 스스로 자신의 상태를 변경하도록 설계한 메서드 (도메인 로직)
     * - 서비스가 직접 setter를 호출하는 것보다 유지보수가 훨씬 좋다.
     */
    //업데이트
    public void update(String toDoTitle, String toDoContent){
        if(toDoTitle != null)this.title = toDoTitle;
        if(toDoContent != null)this.content = toDoContent;
    }

    //getter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWriter() { return writer; }

    /**
     * Setter (비교용)
     * - 블로그에서 update 방식과 setter 방식 비교 설명할 때 사용하기 위해 남김
     * - 실무에서는 setter 사용을 최소화하는 것이 일반적이다.
     */
    //setter
    public void setTitle(String toDoTitle) { this.title = toDoTitle; }
    public void setContent(String toDoContent) { this.content = toDoContent; }
}
