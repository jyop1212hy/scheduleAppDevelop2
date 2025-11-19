package com.scheduleappdevelop2.global.exception;

public class UnauthorizedUserAccessException extends RuntimeException {
    public UnauthorizedUserAccessException(Long id) {
        super("[ID : " + id + "번 유저는 로그인 권한이 없습니다.]");
    }
}
