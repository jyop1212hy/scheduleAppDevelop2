package com.scheduleappdevelop2.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //<<--- 해당 프로젝트에서 일괄적으로 처리
public class GlobalExceptionHandler {

    // 잘못된 요청 : 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException error) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("[잘못된 요청] " + error.getMessage());
    }

    // 상태 관련 문제(로그인 안됨/권한 없음 등) : 403
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException error) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("[권한 문제] " + error.getMessage());
    }

    // ====== 커스텀 예외 ======

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<String> handleNotLoggedIn(NotLoggedInException error) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException error) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error.getMessage());
    }

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<String> handleScheduleNotFound(ScheduleNotFoundException error) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error.getMessage());
    }
}
