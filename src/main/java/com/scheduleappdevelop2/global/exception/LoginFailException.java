package com.scheduleappdevelop2.global.exception;

import lombok.Getter;

@Getter
public class LoginFailException extends CustomException {

    public LoginFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
