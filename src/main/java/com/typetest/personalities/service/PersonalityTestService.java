package com.typetest.personalities.service;

import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface PersonalityTestService {
    String calcType(PersonalitiesAnswerInfo answerInfo);
}