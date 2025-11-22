package com.scheduleappdevelop2.global.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(LoginFailException e){
    ErrorCode errorCode = e.getErrorCode();

    log.error("CustomException 발생: code = {}, message = {}",errorCode.getCode(),errorCode.getMessage());

    return ResponseEntity
            .status(errorCode.getStatus())
            .body(ErrorResponse.of(errorCode));
    }
}
