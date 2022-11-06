package com.typetest.personalities.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
public class UserTendencyInfo {
    Map<Tendency, Long> tendencyMap;

    public UserTendencyInfo(Map<Tendency, Long> tendencyMap) {
        this.tendencyMap = tendencyMap;
    }
}
