package com.typetest.exception;

import com.typetest.constant.ErrorCode;
import lombok.Getter;

@Getter
public class TypetestException extends RuntimeException {

    private final String key;
    private final ErrorCode errorCode;

    public TypetestException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.key = "";
    }

    public TypetestException(ErrorCode errorCode, String key) {
        this.errorCode = errorCode;
        this.key = key;
    }
}
