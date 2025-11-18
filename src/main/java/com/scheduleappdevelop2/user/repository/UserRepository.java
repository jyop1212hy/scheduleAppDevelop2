package com.scheduleappdevelop2.user.repository;

import com.scheduleappdevelop2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 * - User 엔티티에 대한 기본 CRUD 메서드를 제공하는 JPA 리포지토리.
 * - JpaRepository<User, Long> 을 상속하면 save(), findAll(), findById(), deleteById() 등을 자동 제공한다.
 * - 별도 구현 없이 데이터 접근 계층을 구성할 수 있음.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
