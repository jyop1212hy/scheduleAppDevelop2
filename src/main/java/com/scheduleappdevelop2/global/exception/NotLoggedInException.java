package com.scheduleappdevelop2.global.exception;

//로그인이 안되어 있을 때
public class NotLoggedInException extends RuntimeException {
    public NotLoggedInException() {
        super("로그인이 필요합니다.");
    }
}
