package com.typetest.personalities.repository;

import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.PersonalityAnswer;
import com.typetest.personalities.domain.PersonalityQuestion;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeIndicator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuestionAndAnswerTest {

    @Autowired
    private PersonalityQuestionRepository personalityQuestionRepository;

    @Autowired
    private PersonalityAnswerRepository personalityAnswerRepository;

    @Autowired
    private TestCodeInfoRepository testCodeInfoRepository;

    @Autowired
    private EntityManager em;

    @Test
    void createTest() {
        //given
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TestCodeInfo testCodeInfo2 = new TestCodeInfo("CARDTEST", "CARD예제", AnswerType.CARD);
        TypeIndicator indicatorA = new TypeIndicator(testCodeInfo1, 1, "A지표");
        em.persist(testCodeInfo1);
        em.persist(testCodeInfo2);
        em.persist(indicatorA);
        em.flush();

        //when
        List<PersonalityQuestion> questionList = new ArrayList<>();
        List<PersonalityAnswer> answerList = new ArrayList<>();

        questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion1", 1, indicatorA));
        questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion2", 2, indicatorA));
        questionList.add(new PersonalityQuestion(testCodeInfo1, "examQuestion3", 3, indicatorA));



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

        //then
        List<PersonalityQuestion> findQuestions = personalityQuestionRepository.findByTestCode(testCodeInfo1);
        List<PersonalityAnswer> findAnswers = personalityAnswerRepository.findByTestCode(testCodeInfo1);
        assertThat(findQuestions).hasSize(3);
        assertThat(findAnswers).hasSize(15);

    }

}
