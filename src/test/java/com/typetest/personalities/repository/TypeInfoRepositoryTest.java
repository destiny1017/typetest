package com.typetest.personalities.repository;

import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.TestCodeInfo;
import com.typetest.personalities.domain.TypeDescription;
import com.typetest.personalities.domain.TypeImage;
import com.typetest.personalities.domain.TypeInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class TypeInfoRepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private TypeInfoRepository typeInfoRepository;
    @Autowired
    private TypeDescriptionRepository typeDescriptionRepository;
    @Autowired
    private TypeImageRepository typeImageRepository;

    @Test
    @DisplayName("유형 설명정보 및 이미지정보 세팅 테스트")
    void typeInfoDescriptionTest() {
        TestCodeInfo testCodeInfo1 = new TestCodeInfo("EXAMTEST", "EXAM예제", AnswerType.EXAM);
        TypeInfo typeInfo1 = new TypeInfo(testCodeInfo1, "BBB", "비비비타입");
        TypeInfo typeInfo2 = new TypeInfo(testCodeInfo1, "AAA", "에에에타입");
        TypeInfo typeInfo3 = new TypeInfo(testCodeInfo1, "ABB", "에비비타입");
        em.persist(testCodeInfo1);
        em.persist(typeInfo1);
        em.persist(typeInfo2);
        em.persist(typeInfo3);

        TypeImage typeImage1 = new TypeImage(typeInfo1, "tempUrl1");
        TypeImage typeImage2 = new TypeImage(typeInfo1, "tempUrl2");
        TypeImage typeImage3 = new TypeImage(typeInfo2, "tempUrl3");
        TypeImage typeImage4 = new TypeImage(typeInfo2, "tempUrl4");
        TypeImage typeImage5 = new TypeImage(typeInfo3, "tempUrl5");
        TypeImage typeImage6 = new TypeImage(typeInfo3, "tempUrl6");
        em.persist(typeImage1);
        em.persist(typeImage2);
        em.persist(typeImage3);
        em.persist(typeImage4);
        em.persist(typeImage5);
        em.persist(typeImage6);

        TypeDescription description1 = new TypeDescription(typeInfo1, "description1");
        TypeDescription description2 = new TypeDescription(typeInfo1, "description2");
        TypeDescription description3 = new TypeDescription(typeInfo2, "description3");
        TypeDescription description4 = new TypeDescription(typeInfo2, "description4");
        TypeDescription description5 = new TypeDescription(typeInfo3, "description5");
        TypeDescription description6 = new TypeDescription(typeInfo3, "description6");
        em.persist(description1);
        em.persist(description2);
        em.persist(description3);
        em.persist(description4);
        em.persist(description5);
        em.persist(description6);

        em.flush();

        List<TypeDescription> byTypeInfo1 = typeDescriptionRepository.findByTypeInfo(typeInfo1);
        List<TypeDescription> byTypeInfo2 = typeDescriptionRepository.findByTypeInfo(typeInfo2);
        List<TypeDescription> byTypeInfo3 = typeDescriptionRepository.findByTypeInfo(typeInfo3);

        List<TypeImage> byTypeInfoImg1 = typeImageRepository.findByTypeInfo(typeInfo1);
        List<TypeImage> byTypeInfoImg2 = typeImageRepository.findByTypeInfo(typeInfo2);
        List<TypeImage> byTypeInfoImg3 = typeImageRepository.findByTypeInfo(typeInfo3);

        assertThat(byTypeInfo1.size()).isEqualTo(2);
        assertThat(byTypeInfo2.size()).isEqualTo(2);
        assertThat(byTypeInfo3.size()).isEqualTo(2);

        assertThat(byTypeInfoImg1.size()).isEqualTo(2);
        assertThat(byTypeInfoImg1.size()).isEqualTo(2);
        assertThat(byTypeInfoImg1.size()).isEqualTo(2);

        assertThat(byTypeInfo1).contains(description1, description2);
        assertThat(byTypeInfo2).contains(description3, description4);
        assertThat(byTypeInfo3).contains(description5, description6);

        assertThat(byTypeInfoImg1).contains(typeImage1, typeImage2);
        assertThat(byTypeInfoImg2).contains(typeImage3, typeImage4);
        assertThat(byTypeInfoImg3).contains(typeImage5, typeImage6);

    }
}
