package com.typetest.admin.testadmin.service;

import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.IndicatorSetting;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeIndicator;
import com.typetest.personalities.repository.TypeIndicatorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class TestAdminServiceTest {

    @Autowired
    private TypeIndicatorRepository typeIndicatorRepository;
    
    @Autowired
    TestAdminService testAdminService;

    @Autowired
    private EntityManager em;

    @Test
    void getEssentialTypeList() {
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);

        TypeIndicator indicatorA = new TypeIndicator(testCodeInfo1, 1, "A지표");
        TypeIndicator indicatorB = new TypeIndicator(testCodeInfo1, 2, "B지표");
        TypeIndicator indicatorC = new TypeIndicator(testCodeInfo1, 3, "C지표");

        IndicatorSetting indicatorSetting1 = new IndicatorSetting(indicatorA, testCodeInfo1, 0, "B");
        IndicatorSetting indicatorSetting2 = new IndicatorSetting(indicatorA, testCodeInfo1, 12, "A");
        IndicatorSetting indicatorSetting3 = new IndicatorSetting(indicatorB, testCodeInfo1, 0, "B");
        IndicatorSetting indicatorSetting4 = new IndicatorSetting(indicatorB, testCodeInfo1, 12, "A");
        IndicatorSetting indicatorSetting5 = new IndicatorSetting(indicatorC, testCodeInfo1, 0, "B");
        IndicatorSetting indicatorSetting6 = new IndicatorSetting(indicatorC, testCodeInfo1, 12, "A");

        em.persist(testCodeInfo1);
        em.persist(indicatorA);
        em.persist(indicatorB);
        em.persist(indicatorC);
        em.persist(indicatorSetting1);
        em.persist(indicatorSetting2);
        em.persist(indicatorSetting3);
        em.persist(indicatorSetting4);
        em.persist(indicatorSetting5);
        em.persist(indicatorSetting6);

        List<String> essentialTypeList = testAdminService.getEssentialTypeList(testCodeInfo1.getTestCode());

        assertThat(essentialTypeList).hasSize(8);
        assertThat(essentialTypeList).contains("BAB", "BBA", "AAA", "ABA");
    }
}
