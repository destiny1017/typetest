package com.typetest.personalities.domain;

import com.typetest.admin.testadmin.data.TypeInfoDto;
import com.typetest.personalities.data.AnswerType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class TypeInfo {

    @Id @GeneratedValue
    @Column(name = "TYPE_INFO_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEST_CODE")
    private TestCodeInfo testCode;

    private String typeCode;
    private String typeName;
    private int resultCount;

    @OneToMany(mappedBy = "typeInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeDescription> descriptions = new ArrayList<>();

    @OneToMany(mappedBy = "typeInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeImage> images = new ArrayList<>();

    @OneToOne(mappedBy = "typeInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private TypeRelation typeRelation;

    public void addDescription(TypeDescription description) {
        this.descriptions.add(description);
        description.setTypeInfo(this);
    }

    public void addImage(TypeImage image) {
        this.images.add(image);
        image.setTypeInfo(this);
    }

    public void updateTypeRelation(TypeRelation typeRelation) {
        this.typeRelation = typeRelation;
    }

    public TypeInfo(TestCodeInfo testCode, String typeCode, String typeName) {
        this.testCode = testCode;
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    @Builder
    public TypeInfo(Long id, TestCodeInfo testCode, String typeCode, String typeName, int resultCount, List<TypeDescription> descriptions, List<TypeImage> images, TypeRelation typeRelation) {
        this.id = id;
        this.testCode = testCode;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.resultCount = resultCount;
        this.descriptions = descriptions;
        this.images = images;
        this.typeRelation = typeRelation;
    }

    public int plusResultCount(int cnt) {
        resultCount += cnt;
        return resultCount;
    }
}
