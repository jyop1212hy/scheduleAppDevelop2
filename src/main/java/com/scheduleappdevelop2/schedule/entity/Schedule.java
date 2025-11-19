package com.scheduleappdevelop2.schedule.entity;

import com.scheduleappdevelop2.global.baseTimeEntity.BaseTimeEntity;
import com.scheduleappdevelop2.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

/**
 * Schedule 엔티티
 * - 일정 정보를 저장하는 핵심 도메인 객체이며 schedules 테이블과 매핑된다.
 * - 일정은 반드시 작성자(User)와 연결되며, 작성자 FK(user_id)를 통해 관계가 유지된다.
 * - 생성/수정 시간 정보는 BaseTimeEntity를 상속하여 자동으로 관리된다.
 */
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = PROTECTED) // JPA가 엔티티를 생성할 때 사용하는 기본 생성자. 개발자는 직접 사용하지 못하게 보호
public class Schedule extends BaseTimeEntity {

    /**
     * 일정 고유 식별자 (PK)
     * - AUTO_INCREMENT 전략을 사용해 DB가 자동 생성한다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA 기본 생성자 보호
    private Long id; // 기본 키. DB가 자동 증가시키며, 저장 시점에 값이 채워짐

    /**
     * 일정 제목
     * - 필수 값이며 최대 100자까지 저장 가능하다.
     */
    @Column(length = 100, nullable = false)
    private String title; // 일정 제목. 비워둘 수 없고 최대 100자까지 저장 가능.

    /**
     * 일정 상세 내용
     * - TEXT 타입을 사용하여 길이 제한 없이 저장한다.
     * - null 불가.
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 일정 내용. 길이에 제한이 없으므로 TEXT 타입 사용.

    /**
     * 일정 작성자 (User FK)
     * - 다대일(ManyToOne) 관계.
     * - LAZY 전략으로 필요할 때만 조회한다.
     * - user_id 컬럼으로 매핑되며 null이 될 수 없다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 실제 엔티티 생성 시 사용되는 생성자
     * - Builder 패턴을 사용하여 필수 필드만 설정하도록 제한한다.
     * - id는 자동 생성되므로 입력받지 않는다.
     */
    @Builder
    private Schedule(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    /**
     * 정적 팩토리 메서드
     * - 일정 생성 시 명확한 의도를 드러내는 생성 방식 제공.
     */
    public static Schedule of(String title, String content, User user) {
        return Schedule.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }

    /**
     * 일정 수정 메서드
     * - 전달받은 값이 null이 아닐 때 해당 필드만 변경한다.
     * - Dirty Checking에 의해 트랜잭션 종료 시 자동 반영된다.
     */
    public void update(String title, String content){
        if(title != null) { this.title = title; }
        if(content != null) { this.content = content; }
    }

    // Getter 메서드
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public User getUser() { return user; }
}
