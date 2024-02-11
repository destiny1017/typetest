package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.TypeRelationDto;
import lombok.Builder;
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

    @Builder
    public TypeRelation(Long id, TypeInfo typeInfo, TypeInfo bestType, TypeInfo worstType) {
        this.id = id;
        this.typeInfo = typeInfo;
        this.bestType = bestType;
        this.worstType = worstType;
    }

}
