package com.typetest.personalities.domain;

import com.typetest.personalities.data.AnswerType;
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
    private Integer resultCount;

    @OneToMany(mappedBy = "typeInfo", cascade = CascadeType.ALL)
    List<TypeDescription> descriptions = new ArrayList<>();

    @OneToMany(mappedBy = "typeInfo", cascade = CascadeType.ALL)
    List<TypeImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "typeInfo", cascade = CascadeType.ALL)
    List<TypeRelation> relations = new ArrayList<>();

    public void addDescription(TypeDescription description) {
        this.descriptions.add(description);
        description.setTypeInfo(this);
    }

    public void addImage(TypeImage image) {
        this.images.add(image);
        image.setTypeInfo(this);
    }

    public TypeInfo(TestCodeInfo testCode, String typeCode, String typeName) {

        this.testCode = testCode;
        this.typeCode = typeCode;
        this.typeName = typeName;
    }
}
