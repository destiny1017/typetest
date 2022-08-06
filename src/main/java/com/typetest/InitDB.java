package com.typetest;

import com.typetest.login.domain.Role;
import com.typetest.login.domain.User;
import com.typetest.personalities.data.AnswerType;
import com.typetest.personalities.domain.PersonalityType;
import com.typetest.personalities.domain.TypeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {

        private final EntityManager em;
        private final Environment env;

        public void init() {
            if(!env.getProperty("spring.profiles.active").equals("test")) {
                TypeInfo typeInfo1 = new TypeInfo(AnswerType.BASIC, "BBA", "비비에이");
                TypeInfo typeInfo2 = new TypeInfo(AnswerType.BASIC, "AAA", "에에에이");
                TypeInfo typeInfo3 = new TypeInfo(AnswerType.BASIC, "BAB", "비에이비");
                TypeInfo typeInfo4 = new TypeInfo(AnswerType.CARD, "INTP", "인팁");
                TypeInfo typeInfo5 = new TypeInfo(AnswerType.BASIC, "BBB", "비비비이");
                em.persist(typeInfo1);
                em.persist(typeInfo2);
                em.persist(typeInfo3);
                em.persist(typeInfo4);
                em.persist(typeInfo5);

                User user = new User("김대호",
                        "eogh6428@gmail.com",
                        "https://lh3.googleusercontent.com/a-/AFdZucr_8gjDmt791JrOHftPA1UX3kvt1WiRxW19AH4JdQ=s96-c",
                        Role.USER, "디앙");
                PersonalityType pt1 = new PersonalityType(user, AnswerType.BASIC, "AAA");
                PersonalityType pt2 = new PersonalityType(user, AnswerType.BASIC, "BBA");
                PersonalityType pt3 = new PersonalityType(user, AnswerType.CARD, "INTP");
                em.persist(user);
                em.persist(pt1);
                em.persist(pt2);
                em.persist(pt3);

                em.flush();
            }

        }
    }
}
