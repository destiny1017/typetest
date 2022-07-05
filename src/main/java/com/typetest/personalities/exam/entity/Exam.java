package com.typetest.personalities.exam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Exam {

    @Id @GeneratedValue
    private Long id;

}
