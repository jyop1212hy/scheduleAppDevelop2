package com.scheduleappdevelop2.global.exception;

//유저를 찾지 못했을 때
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("[ID : " + id + "번 유저를 찾을 수 없습니다.]");
    }
}
