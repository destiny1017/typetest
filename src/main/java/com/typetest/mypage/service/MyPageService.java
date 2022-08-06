package com.typetest.mypage.service;

import com.typetest.login.domain.User;
import com.typetest.login.repository.LoginRepository;
import com.typetest.mypage.dto.TypeInfoData;
import com.typetest.personalities.data.TestCode;
import com.typetest.personalities.repository.PersonalityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final PersonalityTypeRepository personalityTypeRepository;

    /**
     * # 사용자의 검사결과 데이터를 받아와서 가장 최근의 결과를 필터링 해서 돌려주는 메서드
     * @param user
     * @return
     */
    public Map<TestCode, TypeInfoData> getUserTypeInfo(User user) {
        List<TypeInfoData> typeList = personalityTypeRepository.getUserTypeList(user);
        Map<TestCode, TypeInfoData> typeMap = new HashMap<>();
        // 불러온 유형 데이터중 가장 최근의 유형결과만 TestCode별로 따로 추출
        for (TypeInfoData typeInfoData : typeList) {
            TypeInfoData mappedData = typeMap.get(typeInfoData.getTestCode());
            if(mappedData != null) {
                if (typeInfoData.getCreateDate().isAfter(mappedData.getCreateDate())) {
                    typeMap.put(typeInfoData.getTestCode(), typeInfoData);
                }
            } else {
                typeMap.put(typeInfoData.getTestCode(), typeInfoData);
            }
        }
        return typeMap;
    }
}