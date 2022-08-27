package com.typetest.admin.testadmin.data;

import com.typetest.personalities.domain.TypeImage;
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

    public boolean isNewEntity() {
        return id == null;
    }
}
