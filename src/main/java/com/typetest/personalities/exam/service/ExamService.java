package com.typetest.personalities.exam.service;

import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.service.PersonalityTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@Qualifier("examService")
@RequiredArgsConstructor
public class ExamService implements PersonalityTestService {

    @Override
    public String calcType(PersonalitiesAnswerInfo answerInfo) {
        return null;
    }

    @Override
    public void saveTestInfo(PersonalitiesAnswerInfo answerInfo, String type) {

    }

}
