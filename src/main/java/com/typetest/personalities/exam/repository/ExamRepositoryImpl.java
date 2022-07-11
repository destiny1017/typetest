package com.typetest.personalities.exam.repository;

import com.typetest.personalities.dto.PersonalitiesAnswerInfo;
import com.typetest.personalities.repository.PersonalityTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ExamRepositoryImpl implements ExamRepository, PersonalityTestRepository {

    private final EntityManager em;

    @Override
    public void savePersonalityType(String userId, String testCode, String type) {

    }

    @Override
    public void savePersonalityTypeDetail(PersonalitiesAnswerInfo answerInfo) {

    }


}
