package com.scheduleappdevelop2.user.entity;

import com.scheduleappdevelop2.global.baseTimeEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

/**
 * User 엔티티
 * - 회원 정보(users 테이블)와 매핑되는 도메인 객체.
 * - 이름, 이메일, 비밀번호 같은 핵심 데이터와
 *   생성/수정 시간(BaseTimeEntity)을 함께 관리한다.
 * - JPA가 관리하며, 서비스 계층에서 비즈니스 로직을 처리할 때 사용된다.
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
     * - 중복 허용
     */
    @Column(length = 50, nullable = false)
    private String name;

    /**
     * 이메일
     * - 필수값, 최대 20자
     * - unique=true → 중복 허용 안 함
     */
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    /**
     * 비밀번호
     * - 필수값, 최대 16자
     * - 중복 허용
     */
    @Column(length = 100, nullable = false)
    private String password;

    /**
     * 엔티티 생성용 생성자
     * - Builder 로만 객체가 생성되도록 제한하여 안정성을 높인다.
     */
    @Builder
    private User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * 유저 정보 수정
     * - null 아닌 값만 골라 엔티티 상태를 갱신한다.
     * - 변경 후 트랜잭션 종료 시 자동으로 DB에 반영된다 (Dirty Checking).
     */
    public static User of(String name, String email, String password){
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

    /**
     * 유저 정보 수정
     * - null 아닌 값만 골라 엔티티 상태를 갱신한다.
     * - 변경 후 트랜잭션 종료 시 자동으로 DB에 반영된다 (Dirty Checking).
     */
    public void update(String name, String email) {
        if(name != null) {this.name = name; }
        if(email != null) {this.email = email; }
    }

    //getter
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
