package com.typetest.personalities.exam.service;

import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.PersonalityAnswer;
import com.typetest.personalities.domain.PersonalityQuestion;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.exam.dto.ExamQuestionDto;
import com.typetest.personalities.repository.PersonalityQuestionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ExamServiceTest {

    @Autowired
    private ExamService examService;

    @Autowired
    private EntityManager em;

    @Autowired
    private PersonalityQuestionRepository personalityQuestionRepository;

    @Test
    void getQuestionTest() {
        //given
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TestCodeInfo testCodeInfo2 = new TestCodeInfo("CARDTEST", "CARD예제", AnswerType.CARD);
        em.persist(testCodeInfo1);
        em.persist(testCodeInfo2);
        em.flush();

        //when
        List<PersonalityQuestion> questionList = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion" + i, i));
        }

        for (int i = 0; i < questionList.size(); i++) {
            for (int j = 1; j <= 5; j++) {
                questionList.get(i).addAnswer(PersonalityAnswer.builder()
                        .personalityQuestion(questionList.get(i))
                        .testCode(testCodeInfo1)
                        .point(j)
                        .tendency(Tendency.A)
                        .build());
            }
        }

        personalityQuestionRepository.saveAll(questionList);

        //when
        List<List<ExamQuestionDto>> questions = examService.getQuestions("EXAMTEST");

        //then
        assertThat(questions).hasSize(2); // 12개이므로 2페이지
        assertThat(questions.get(0)).hasSize(10); // 1페이지에 10개
        assertThat(questions.get(1)).hasSize(2); // 2페이지에 2개
        assertThat(questions.get(0).get(0).getAnswerList()).hasSize(5); //1페이지 첫번째 질문에 답변 5개
        assertThat(questions.get(1).get(1).getAnswerList()).hasSize(5); //2페이지 두번째 질문에 답변 5개

    }

}