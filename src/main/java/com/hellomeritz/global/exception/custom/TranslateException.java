package com.hellomeritz.chat.global.exception.custom;

import com.hellomeritz.chat.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class TranslateException extends RuntimeException {

    private final ErrorCode errorCode;

    public TranslateException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}