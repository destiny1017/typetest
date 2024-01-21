package com.typetest.common.error;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorInfoDto {
    private Integer errorCode;
    private String errorName;
    private String errorMessage;
    private String errorMessage2;
}
