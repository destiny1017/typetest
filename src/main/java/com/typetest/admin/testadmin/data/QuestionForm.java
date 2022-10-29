package com.typetest.admin.testadmin.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class QuestionForm {
    @NotEmpty
    private String questionTestCode;
    @NotNull
    private List<QuestionDto> questionList = new ArrayList<>();
}
