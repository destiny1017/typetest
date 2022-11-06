package com.typetest.mypage.data;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TypeInfoResponse {
    private int returnCode;
    private HashMap<String, UserTypeInfoApiDto> data = new HashMap<>();

    public TypeInfoResponse(int returnCode, Map<String, TypeInfoData> typeInfoData) {
        this.returnCode = returnCode;
        if(typeInfoData != null) {
            typeInfoData.forEach((k, v) -> data.put(k, new UserTypeInfoApiDto(v)));
        }
    }
}