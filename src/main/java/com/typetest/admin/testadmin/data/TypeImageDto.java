package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeImage;
import com.typetest.personalities.domain.TypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TypeImageDto extends EntityState {
    private Integer imgNum;
    private String imageUrl;

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

}
