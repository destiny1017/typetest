package com.typetest.mypage.service;

import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.data.UserTendencyInfo;
import com.typetest.user.domain.User;
import com.typetest.user.repository.UserRepository;
import com.typetest.mypage.data.TypeInfoData;
import com.typetest.personalities.repository.TestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final TestResultRepository testResultRepository;
    private final UserRepository userRepository;

    /**
     * # 사용자의 검사결과 데이터를 받아와서 가장 최근의 결과를 필터링 해서 돌려주는 메서드
     * @param user
     * @return
     */
    public Map<String, TypeInfoData> getUserTypeInfo(User user) {
        List<TypeInfoData> typeList = testResultRepository.getUserTypeList(user);
        Map<String, TypeInfoData> typeMap = new HashMap<>();
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

    public UserTendencyInfo getUserTendencyInfo(User user) {
        Map<Tendency, Long> tendencyMap = testResultRepository.countTendency(user);
        long totalCnt = 0;
        for (Tendency t : Tendency.values()) {
            long value = tendencyMap.getOrDefault(t, 0L);
            tendencyMap.put(t, value);
            totalCnt += value;
        }
        for (Tendency t : tendencyMap.keySet()) {
            tendencyMap.put(t, (tendencyMap.get(t)  * 10) / totalCnt);
        }
        //tendencyMap.keySet().stream().forEach(k -> System.out.println("key = " + k + ", val = " + tendencyMap.get(k)));
        return new UserTendencyInfo(tendencyMap);
    }

    public User updateNickname(Long id, String nickname) {
        userRepository.updateNickname(id, nickname);
        return userRepository.findById(id).get();
    }


}
