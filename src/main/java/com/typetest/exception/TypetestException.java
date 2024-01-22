package com.typetest.exception;

import com.typetest.constant.ErrorCode;
import lombok.Getter;

@Getter
public class TypetestException extends RuntimeException {

    private final String key;
    private final ErrorCode errorCode;

    public TypetestException(ErrorCode errorCode) {
        super(errorCode.getDetail());
        this.errorCode = errorCode;
        this.key = "";
    }

    public TypetestException(ErrorCode errorCode, String key) {
        super(errorCode.getDetail() + ", key = " + key);
        this.errorCode = errorCode;
        this.key = key;
    }


}
