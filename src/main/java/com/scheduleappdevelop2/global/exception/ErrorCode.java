package com.scheduleappdevelop2.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 400
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 400, "요청 값이 올바르지 않습니다."),
    NOT_VALID_OWNER(HttpStatus.FORBIDDEN, 400, "자원에 접근할 권한이 없습니다."),
    // 401
    NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, 401, "로그인이 필요합니다."),
    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "유저를 찾을 수 없습니다."),
    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "알 수 없는 서버 에러가 발생했습니다.");


    private final HttpStatus status;
    private final int code;
    private final String message;

    ErrorCode(HttpStatus status, int code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
