package com.hello.global.exception.custom;

import com.hello.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class SttException extends RuntimeException {

    private final ErrorCode errorCode;

    public SttException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
