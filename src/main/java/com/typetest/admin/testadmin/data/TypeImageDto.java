package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TypeImageDto {
    private Integer imgNum;
    private String imageUrl;

    public TypeImageDto(TypeImage typeImage) {
        this.imgNum = typeImage.getImgNum();
        this.imageUrl = typeImage.getImageUrl();
    }
}
