package com.typetest.personalities.service;

import com.typetest.personalities.dto.PersonalitiesAnswerInfo;

import java.util.Map;

public interface PersonalityTestService {
    String calcType(PersonalitiesAnswerInfo answerInfo);
    String getPersonalityType(PersonalitiesAnswerInfo answerInfo);
}
