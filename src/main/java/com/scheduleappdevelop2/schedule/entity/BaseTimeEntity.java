package com.scheduleappdevelop2.schedule.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 공통으로 사용되는 생성일/수정일 필드를 관리하는 베이스 엔티티.
 * 모든 엔터티에 중복해서 넣지 않도록 상속 구조로 분리했다.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 엔티티 생성/수정 시점을 자동으로 감지해서 필드에 반영하도록 설정
public class BaseTimeEntity {

    /**
     * 엔티티가 최초로 저장되는 시점에 자동으로 채워지는 시간.
     * 개발자가 직접 넣지 않아도 되고, 한 번 저장되면 변경되지 않는다.
     */
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * 엔티티가 수정될 때마다 자동으로 갱신되는 시간.
     * update 등 변경이 발생하면 JPA Auditing이 값을 채워준다.
     */
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    //getter
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getModifiedAt() { return modifiedAt; }
}


