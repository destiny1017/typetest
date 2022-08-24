package com.typetest.admin.testadmin.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class QuestionForm {
    private String questionTestCode;
    private List<QuestionDto> questionList = new ArrayList<>();
}
