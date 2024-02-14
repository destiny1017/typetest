package com.typetest.admin.testadmin.data;

import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.PersonalityAnswer;
import com.typetest.personalities.domain.PersonalityQuestion;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeIndicator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerDto extends EntityState {
//    private Long id;
    private String answer;
    private Integer point;
    private String answerImage;
    private Tendency tendency;
    private Long typeIndicatorId;
    private TypeIndicator typeIndicator;

//    private Integer updated = 0;
//    private Integer deleted = 0;

    public AnswerDto(PersonalityAnswer answer) {
        this.id = answer.getId();
        this.answer = answer.getAnswer();
        this.point = answer.getPoint();
        this.answerImage = answer.getAnswerImage();
        this.tendency = answer.getTendency();
        this.typeIndicatorId = answer.getTypeIndicator().getId();
    }

    public PersonalityAnswer toEntity(TestCodeInfo testCodeInfo, PersonalityQuestion question, TypeIndicator indicator) {
        return PersonalityAnswer.builder()
                .id(this.id)
                .testCode(testCodeInfo)
                .personalityQuestion(question)
                .answer(this.answer)
                .point(this.point)
                .answerImage(this.answerImage)
                .tendency(this.tendency)
                .typeIndicator(indicator)
                .build();
    }

    public boolean emptyValueCheck() {
        return  (answer == null || answer.isEmpty()) &&
                (answerImage == null || answerImage.isEmpty()) &&
                point == null &&
                tendency == null &&
                typeIndicatorId == null;
    }

}
