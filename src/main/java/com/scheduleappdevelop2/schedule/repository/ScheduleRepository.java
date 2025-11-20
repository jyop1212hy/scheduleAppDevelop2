package com.scheduleappdevelop2.schedule.repository;

import com.scheduleappdevelop2.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ScheduleRepository
 * - Schedule 엔터티에 대한 CRUD 기능을 자동으로 제공하는 JPA 인터페이스.
 * - JpaRepository<Schedule, Long> 상속만으로 save(), findAll(), findById(), deleteById() 같은 기능이 자동 생성된다.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}