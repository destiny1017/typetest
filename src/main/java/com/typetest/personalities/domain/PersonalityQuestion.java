package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.QuestionDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PersonalityQuestion {

    @Id
    @GeneratedValue
    @Column(name = "PERSONALITY_QUESTION_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEST_CODE")
    private TestCodeInfo testCode;

    private String question;

    private Integer num;

    private String questionImage;

    @OneToMany(mappedBy = "personalityQuestion", cascade = CascadeType.ALL)
    private List<PersonalityAnswer> answerList = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addAnswer(PersonalityAnswer answer) {
        answerList.add(answer);
        answer.setQuestion(this);
    }

    public PersonalityQuestion(QuestionDto questionDto, TestCodeInfo testCode) {
        this.id = questionDto.getId();
        this.testCode = testCode;
        this.question = questionDto.getQuestion();
        this.num = questionDto.getNum();
        this.questionImage = questionDto.getQuestionImage();
    }

    public PersonalityQuestion(TestCodeInfo testCode, String question, int num) {
        this.testCode = testCode;
        this.question = question;
        this.num = num;
    }

    public PersonalityQuestion(TestCodeInfo testCode, String question, int num, String questionImage) {
        this.testCode = testCode;
        this.question = question;
        this.num = num;
        this.questionImage = questionImage;
    }

    @Builder
    public PersonalityQuestion(Long id, TestCodeInfo testCode, String question, Integer num, String questionImage, List<PersonalityAnswer> answerList) {
        this.id = id;
        this.testCode = testCode;
        this.question = question;
        this.num = num;
        this.questionImage = questionImage;
        this.answerList = answerList;
    }
}
