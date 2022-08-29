package com.typetest.admin.testadmin.controller;

import com.typetest.admin.testadmin.data.*;
import com.typetest.admin.testadmin.service.TestAdminService;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TestAdminController {

    private final TestAdminService testAdminService;

    @GetMapping("/testAdminPage")
    public String testAdminPage() {
        return "admin/testadmin/testAdminPage";
    }

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

    @GetMapping({"/testAdminPage/{testCode}", "/testAdminPage/{testCode}/{tab}"})
    public String testAdminPage(@PathVariable String testCode,
                                @PathVariable(value = "tab", required = false) Integer tab, Model model) {
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

        model.addAttribute("indicatorForm", indicatorForm);
        model.addAttribute("questionForm", questionForm);
        model.addAttribute("typeInfoForm", typeInfoForm);
        model.addAttribute("testCode", testCode);

        if(tab == null) tab = 1;
        model.addAttribute("tab", tab);

        return "admin/testadmin/testAdminPage";
    }

    @GetMapping("/testAdmin/step1Submit")
    public String step1Submit(@ModelAttribute TestInfoDto testInfoDto) {
        testAdminService.saveTestInfo(testInfoDto);
        return "redirect:/testAdminPage/" + testInfoDto.getTestCode() + "/1";
    }

    @GetMapping("/testAdmin/step2Submit")
    public String step2Submit(@ModelAttribute IndicatorForm indicatorForm) {
        List<TypeIndicatorDto> indicatorList = indicatorForm.getIndicatorList();
        testAdminService.saveIndicatorInfo(indicatorList, indicatorForm.getIndicatorTestCode());
        return "redirect:/testAdminPage/" + indicatorForm.getIndicatorTestCode() + "/2";
    }

    @PostMapping("/testAdmin/step3Submit")
    public String step3Submit(@ModelAttribute QuestionForm questionForm) {
        List<QuestionDto> questionList = questionForm.getQuestionList();
        testAdminService.saveQuestionInfo(questionList, questionForm.getQuestionTestCode());
        return "redirect:/testAdminPage/" + questionForm.getQuestionTestCode() + "/3";
    }

    @PostMapping("/testAdmin/step4Submit")
    public String step4Submit(@ModelAttribute TypeInfoForm typeInfoForm) {
        List<TypeInfoDto> typeInfoList = typeInfoForm.getTypeInfoList();
        testAdminService.saveTypeInfo(typeInfoList, typeInfoForm.getTypeInfoTestCode());
        return "redirect:/testAdminPage/" + typeInfoForm.getTypeInfoTestCode() + "/4";
    }

    @GetMapping("/testAdminPage/{testCode}/essentialType")
    public String essentialTypeList(@PathVariable String testCode, Model model) {
        List<String> essentialTypeList = testAdminService.getEssentialTypeList(testCode);
        model.addAttribute("essentialTypeList", essentialTypeList);
        return "forward:/testAdminPage/" + testCode + "/4";
    }

}
