package com.typetest.personalities.repository;

import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface PersonalityTestRepository {
    void savePersonalityType(String userId, String testCode, String type);
    void savePersonalityTypeDetail(PersonalitiesAnswerInfo answerInfo);
}
