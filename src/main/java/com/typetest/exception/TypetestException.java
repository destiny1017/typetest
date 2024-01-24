package com.typetest.exception;

import com.typetest.constant.ErrorCode;
import lombok.Getter;

@Getter
public class TypetestException extends RuntimeException {

    private final String[] keys;
    private final ErrorCode errorCode;

    public TypetestException(ErrorCode errorCode) {
        super(errorCode.getDetail());
        this.errorCode = errorCode;
        this.keys = new String[]{};
    }

    public TypetestException(ErrorCode errorCode, String... keys) {
        super(errorCode.getDetail() + ", key = " + String.join(",", keys));
        this.errorCode = errorCode;
        this.keys = keys;
    }


}
