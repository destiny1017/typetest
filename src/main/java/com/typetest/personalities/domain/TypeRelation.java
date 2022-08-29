package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.TypeRelationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TypeRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "TYPE_INFO_ID")
    private TypeInfo typeInfo;

    @ManyToOne
    @JoinColumn(name = "BEST_TYPE")
    private TypeInfo bestType;

    @ManyToOne
    @JoinColumn(name = "WORST_TYPE")
    private TypeInfo worstType;

    public TypeRelation(TypeInfo typeInfo, TypeInfo bestType) {
        this.typeInfo = typeInfo;
        this.bestType = bestType;
    }

    public TypeRelation(TypeInfo typeInfo, TypeInfo bestType, TypeInfo worstType) {
        this.typeInfo = typeInfo;
        this.bestType = bestType;
        this.worstType = worstType;
    }

    public TypeRelation(TypeRelationDto typeRelationDto) {
        this.id = typeRelationDto.getId();
        this.typeInfo = typeInfo;
        this.bestType = bestType;
        this.worstType = worstType;
    }
}
