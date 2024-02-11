package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeImage;
import com.typetest.personalities.domain.TypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TypeImageDto {
    private Long id;
    private Integer imgNum;
    private String imageUrl;

    private Integer updated = 0;
    private Integer deleted = 0;

    public TypeImageDto(TypeImage typeImage) {
        this.id = typeImage.getId();
        this.imgNum = typeImage.getImgNum();
        this.imageUrl = typeImage.getImageUrl();
    }

    public TypeImage toEntity(TypeInfo typeInfo) {
        return TypeImage.builder()
                .id(this.id)
                .typeInfo(typeInfo)
                .imgNum(this.imgNum)
                .imageUrl(this.imageUrl)
                .build();
    }

    public boolean isDeletedEntity() {
        return id != null && this.deleted == 1;
    }

    public boolean isNewOrUpdatedEntity() {
        return id == null || this.updated == 1;
    }
}
