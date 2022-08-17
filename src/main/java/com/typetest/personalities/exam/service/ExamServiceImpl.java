package com.typetest.personalities.exam.service;

import com.typetest.exception.NotFoundEntityException;
import com.typetest.personalities.domain.PersonalityQuestion;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.exam.dto.ExamQuestionDto;
import com.typetest.personalities.exam.dto.ExamResultInfo;
import com.typetest.personalities.repository.PersonalityAnswerRepository;
import com.typetest.personalities.repository.PersonalityQuestionRepository;
import com.typetest.personalities.repository.TestCodeInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Qualifier("examService")
public class ExamServiceImpl implements ExamService {

    private final PersonalityQuestionRepository personalityQuestionRepository;
    private final PersonalityAnswerRepository personalityAnswerRepository;
    private final TestCodeInfoRepository testCodeInfoRepository;

    @Override
    public List<List<ExamQuestionDto>> getQuestions(String testCode) {
        Optional<TestCodeInfo> testCodeInfoOp = testCodeInfoRepository.findById(testCode);
        if(testCodeInfoOp.isPresent()) {
            List<List<ExamQuestionDto>> pageQuestions = new ArrayList<>();
            List<PersonalityQuestion> questions = personalityQuestionRepository.findByTestCode(testCodeInfoOp.get());
            int cnt = -1;
            for (int i = 0; i < questions.size(); i++) {
                if((i) % 10 == 0) {
                    pageQuestions.add(new ArrayList<>());
                    cnt++; // 10개 추가될 때 마다 다음 인덱스에 담기
                }
                pageQuestions.get(cnt).add(new ExamQuestionDto(questions.get(i)));
            }

            return pageQuestions;
        } else {
            throw new NotFoundEntityException("테스트코드 [" + testCode + "] 에 해당하는 테스트정보를 찾을 수 없습니다.");
        }
    }

    @Override
    public Page<ExamQuestionDto> getQuestionsPage(String testCode) {
        Optional<TestCodeInfo> testCodeInfoOp = testCodeInfoRepository.findById(testCode);
        if(testCodeInfoOp.isPresent()) {
            Page<ExamQuestionDto> questions = personalityQuestionRepository.findByTestCode(testCodeInfoOp.get(), PageRequest.of(0, 10));
            return questions;
        } else {
            throw new NotFoundEntityException("테스트코드 [" + testCode + "] 에 해당하는 질문데이터를 찾을 수 없습니다.");
        }
    }

    @Override
    public Long getQuestionCnt(String testCode) {
        Optional<TestCodeInfo> testCodeInfoOp = testCodeInfoRepository.findById(testCode);
        if (testCodeInfoOp.isPresent()) {
            return personalityQuestionRepository.countByTestCode(testCodeInfoOp.get());
        } else {
            throw new NotFoundEntityException("테스트코드 [" + testCode + "] 에 해당하는 테스트정보를 찾을 수 없습니다.");
        }
    }

}
