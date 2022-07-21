package com.typetest.personalities.service;

import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface PersonalityTestService {
    String calcType(PersonalitiesAnswerInfo answerInfo);
    void saveTestInfo(PersonalitiesAnswerInfo answerInfo, String type);
}
