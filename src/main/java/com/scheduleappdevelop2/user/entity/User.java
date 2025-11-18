package com.scheduleappdevelop2.user.entity;

import com.scheduleappdevelop2.global.baseTimeEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

/**
 * User 엔티티
 * - 회원 정보를 담는 테이블(users)과 매핑된다.
 * - JPA로 관리되는 진짜 도메인 객체.
 */
@Entity
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED) // JPA용 기본 생성자 (외부 생성 막기)
public class User extends BaseTimeEntity {

    /**
     * PK (AUTO_INCREMENT)
     * - DB가 자동 생성하는 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 유저 이름
     * - 필수값, 최대 30자
     * - unique=true → 중복 허용 안 함
     */
    @Column(length = 30, nullable = false, unique = true)
    private String name;

    /**
     * 이메일
     * - 필수값, 최대 20자
     * - unique=true → 중복 허용 안 함
     */
    @Column(length = 20, nullable = false, unique = true)
    private String email;

    /**
     * 실제 개발자가 사용할 생성자
     * - Builder 패턴으로만 생성되도록 제한
     */
    @Builder
    private User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * 정적 팩토리 메서드
     * - User 객체 생성 시 안정적인 생성 경로 제공
     */
    public static User of(String name, String email){
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }

    /**
     * 유저 정보 수정
     * - 전달된 값이 null이 아닐 때만 필드를 변경
     * - Dirty Checking으로 트랜잭션 종료 시 DB 반영
     */
    public void update(String name, String email) {
        if(name != null) {this.name = name; }
        if(email != null) {this.email = email; }
    }

    //getter
    public Long getId() { return id;}
    public String getName() { return name; }
    public String getEmail() { return email; }
}
