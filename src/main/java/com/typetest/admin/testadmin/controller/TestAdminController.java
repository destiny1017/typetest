package com.typetest.admin.testadmin.controller;

import com.typetest.admin.testadmin.data.IndicatorForm;
import com.typetest.admin.testadmin.data.TestInfoDto;
import com.typetest.admin.testadmin.service.TestAdminService;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeIndicator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @ModelAttribute(name = "testList")
    public List<TestCodeInfo> testList() {
        return testAdminService.findAllTestInfo();
    }

    @GetMapping("/testAdminPage/{testCode}")
    public String testAdminPage(@PathVariable String testCode, Model model) {
        model.addAttribute("testInfoDto", testAdminService.createTestInfoDto(testCode));
        List<TypeIndicator> indicatorList = testAdminService.findIndicatorInfo(testCode);
        IndicatorForm indicatorForm = new IndicatorForm();
        indicatorForm.setTestCode(testCode);

        indicatorForm.setIndicatorList(indicatorList);

        model.addAttribute("indicatorForm", indicatorForm);
        log.info("indicatorWrapper.indicatorList = {}", indicatorForm.getIndicatorList());
        return "admin/testadmin/testAdminPage";
    }

    @GetMapping("/testAdmin/step1Submit")
    public String step1Submit(@ModelAttribute TestInfoDto testInfoDto, Model model) {
        log.info("testInfoDto = {}", testInfoDto);
        testAdminService.saveTestInfo(testInfoDto);
        return "redirect:/testAdminPage/" + testInfoDto.getTestCode();
    }

    @GetMapping("/testAdmin/step2Submit")
    public String step2Submit(@ModelAttribute IndicatorForm indicatorForm) {
        log.info("indicatorForm = {}", indicatorForm.getIndicatorList());
        return "redirect:/testAdminPage/" + indicatorForm.getTestCode();
    }

}
