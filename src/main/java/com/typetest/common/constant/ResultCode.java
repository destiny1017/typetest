package com.typetest.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultCode {

    NONE_INDICATOR_TEST("지표정보가 존재하지 않는 테스트"),
    EXIST_INDICATOR_TEST("지표정보가 존재하는 테스트");

    private final String detail;
}
