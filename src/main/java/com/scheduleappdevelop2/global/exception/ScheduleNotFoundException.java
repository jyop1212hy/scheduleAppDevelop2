package com.scheduleappdevelop2.global.exception;

// 일정(Schedule)을 찾지 못했을 때
public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(Long id) {
        super("[ID : " + id + "번 유저의 일정을 찾을 수 없습니다.]");
    }
}
