package com.typetest.admin.testadmin.service;

import com.typetest.IntegrationTestSupport;
import com.typetest.admin.testadmin.data.*;
import com.typetest.user.domain.Role;
import com.typetest.user.domain.User;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.data.Tendency;
import com.typetest.personalities.domain.*;
import com.typetest.personalities.repository.PersonalityQuestionRepository;
import com.typetest.personalities.repository.TestCodeInfoRepository;
import com.typetest.personalities.repository.TypeIndicatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@Transactional
public class TestAdminServiceTest extends IntegrationTestSupport {

    @Autowired
    private TypeIndicatorRepository typeIndicatorRepository;

    @Autowired
    private PersonalityQuestionRepository personalityQuestionRepository;

    @Autowired
    private TestCodeInfoRepository testCodeInfoRepository;
    
    @Autowired
    private TestAdminService testAdminService;



}
