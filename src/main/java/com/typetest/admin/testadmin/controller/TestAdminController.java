package com.typetest.admin.testadmin.controller;

import com.typetest.admin.testadmin.data.*;
import com.typetest.admin.testadmin.service.TestAdminService;
import com.typetest.constant.ResultCode;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.IndicatorSetting;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeIndicator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TestAdminController {

    private final TestAdminService testAdminService;

    @ModelAttribute(name = "answerTypeList")
    public AnswerType[] answerTypeList() {
        return AnswerType.values();
    }

    @ModelAttribute(name = "tendencyList")
    public Tendency[] tendencyList() {
        return Tendency.values();
    }

    @ModelAttribute(name = "tendencyNameList")
    public List<String> tendencyNameList() {
        return Arrays.stream(Tendency.values()).map(i -> i.getFullName()).collect(Collectors.toList());
    }

    @ModelAttribute(name = "testList")
    public List<TestCodeInfo> testList() {
        return testAdminService.findAllTestInfo();
    }

    @GetMapping({"/adminPage/testAdminPage/{testCode}",
                "/adminPage/testAdminPage/{testCode}/{tab}",
                "/adminPage/testAdminPage/{testCode}/{tab}/{inactive}"})
    public String testAdminPage(@PathVariable String testCode,
                                @PathVariable(value = "tab", required = false) Integer tab,
                                @PathVariable(value = "inactive", required = false) String inactive,
                                Model model) {
        model.addAttribute("testInfoDto", testAdminService.createTestInfoDto(testCode));

        // 테스트 코드에 해당하는 지표정보 세팅하기
        List<TypeIndicatorDto> indicatorList = testAdminService.findIndicatorInfo(testCode);
        IndicatorForm indicatorForm = new IndicatorForm();
        indicatorForm.setIndicatorTestCode(testCode);
        indicatorForm.setIndicatorList(indicatorList);

        // 테스트 코드에 해당하는 질문답변 정보 세팅하기
        List<QuestionDto> questionList = testAdminService.findQuestionInfo(testCode);
        QuestionForm questionForm = new QuestionForm();
        questionForm.setQuestionTestCode(testCode);
        questionForm.setQuestionList(questionList);

        // 테스트 코드에 해당하는 유형 정보 세팅하기
        List<TypeInfoDto> typeInfoList = testAdminService.findTypeInfo(testCode);
        TypeInfoForm typeInfoForm = new TypeInfoForm();
        typeInfoForm.setTypeInfoTestCode(testCode);
        typeInfoForm.setTypeInfoList(typeInfoList);

        // 필수 유형정보 세팅
        if(!testCode.equals("NEW")) {
            List<String> essentialTypeList = testAdminService.getEssentialTypeList(testCode);
            model.addAttribute("essentialTypeList", essentialTypeList);
        }

        model.addAttribute("indicatorForm", indicatorForm);
        model.addAttribute("questionForm", questionForm);
        model.addAttribute("typeInfoForm", typeInfoForm);
        model.addAttribute("testCode", testCode);

        if(tab == null) tab = 1;
        model.addAttribute("tab", tab);

        if(inactive != null) {
            model.addAttribute("inactive", true);
        }

        return "admin/testadmin/testAdminPage";
    }

    @PostMapping("/adminPage/testAdmin/step1Submit")
    public String step1Submit(@ModelAttribute @Valid TestInfoDto testInfoDto) {
        testAdminService.saveTestInfo(testInfoDto);
        return "redirect:/adminPage/testAdminPage/" + testInfoDto.getTestCode() + "/1";
    }

    @PostMapping("/adminPage/testAdmin/step2Submit")
    public String step2Submit(@ModelAttribute @Valid IndicatorForm indicatorForm) {
        List<TypeIndicatorDto> indicatorList = indicatorForm.getIndicatorList();
        ResultCode result = testAdminService.saveIndicatorInfo(indicatorList, indicatorForm.getIndicatorTestCode());
        if(result == ResultCode.NONE_INDICATOR_TEST) {
            testAdminService.disableTest(indicatorForm.getIndicatorTestCode());
            return "redirect:/adminPage/testAdminPage/" + indicatorForm.getIndicatorTestCode() + "/2/inactive";
        }
        return "redirect:/adminPage/testAdminPage/" + indicatorForm.getIndicatorTestCode() + "/2";
    }

    @PostMapping("/adminPage/testAdmin/step3Submit")
    public String step3Submit(@ModelAttribute @Valid QuestionForm questionForm) {
        List<QuestionDto> questionList = questionForm.getQuestionList();
        testAdminService.saveQuestionInfo(questionList, questionForm.getQuestionTestCode());
        return "redirect:/adminPage/testAdminPage/" + questionForm.getQuestionTestCode() + "/3";
    }

    @PostMapping("/adminPage/testAdmin/step4Submit")
    public String step4Submit(@ModelAttribute @Valid TypeInfoForm typeInfoForm) {
        List<TypeInfoDto> typeInfoList = typeInfoForm.getTypeInfoList();
        testAdminService.saveTypeInfo(typeInfoList, typeInfoForm.getTypeInfoTestCode());
        return "redirect:/adminPage/testAdminPage/" + typeInfoForm.getTypeInfoTestCode() + "/4";
    }

}
