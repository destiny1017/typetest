package com.typetest.admin.testadmin.controller;

import com.typetest.admin.testadmin.data.TestInfoDto;
import com.typetest.admin.testadmin.service.TestAdminService;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.TestCodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return "admin/testadmin/testAdminPage";
    }

    @GetMapping("/testAdmin/step1Submit")
    public String step1Submit(@ModelAttribute TestInfoDto testInfoDto, Model model) {
        log.info("testInfoDto = {}", testInfoDto);
        testAdminService.saveTestInfo(testInfoDto);
        return "redirect:/testAdminPage/" + testInfoDto.getTestCode();
    }
}
