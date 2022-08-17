package com.typetest.personalities.data;

import com.typetest.personalities.domain.TypeInfo;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class TestResultDto {

    private String typeCode;
    private String typeName;
    private HashMap<Integer, String> descriptions;
    private HashMap<Integer, String> images;

    public TestResultDto(TypeInfo typeInfo) {
        this.typeCode = typeInfo.getTypeCode();
        this.typeName = typeInfo.getTypeName();
        descriptions = new HashMap<>();
        images = new HashMap<>();
        typeInfo.getDescriptions().stream().forEach(desc ->
                this.descriptions.put(desc.getDescNum(), desc.getDescription()));
        typeInfo.getImages().stream().forEach(img ->
                this.images.put(img.getImgNum(), img.getImageUrl()));
    }
}
