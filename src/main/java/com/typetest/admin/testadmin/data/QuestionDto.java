package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.PersonalityQuestion;
import com.typetest.personalities.domain.TestCodeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
public class QuestionDto extends EntityState {
    private String question;
    private Integer num;
    private String questionImage;
    private List<AnswerDto> answerList = new ArrayList<>();

    public QuestionDto(PersonalityQuestion question) {
        this.id = question.getId();
        this.question = question.getQuestion();
        this.num = question.getNum();
        this.questionImage = question.getQuestionImage();
        this.answerList = question.getAnswerList().stream()
                .map(AnswerDto::new).collect(Collectors.toList());
    }

    public PersonalityQuestion toEntity(TestCodeInfo testCodeInfo) {
        return PersonalityQuestion.builder()
                .id(this.id)
                .testCode(testCodeInfo)
                .question(this.question)
                .num(this.num)
                .questionImage(this.questionImage)
                .build();
    }

    public boolean emptyValueCheck() {
        return  (question == null || question.isEmpty()) &&
                (questionImage == null || questionImage.isEmpty()) &&
                num == null;
    }

}
