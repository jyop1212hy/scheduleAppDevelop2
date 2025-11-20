package com.scheduleappdevelop2.global.exception;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice //<<--- 해당 프로젝트에서 일괄적으로 처리
@Slf4j
public class GlobalExceptionHandler {

    // 잘못된 요청 : 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {

        log.error("MethodArgumentNotValidException 발생 {} : ", error.getMessage());

        String message = Objects.requireNonNull(error.getBindingResult().getFieldError()).getDefaultMessage();

        return ResponseEntity
                .status(error.getStatusCode())
                .body(new ErrorResponse(error.getStatusCode(), message));
    }

    // 상태 관련 문제(로그인 안됨/권한 없음 등) : 403
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("CustomException 발생 {} : ", e.getMessage());

        return ResponseEntity
                .status(e.getErrorMessage().getStatus())
                .body(new ErrorResponse(e.getErrorMessage()));
    }
}
