package com.typetest.admin.testadmin.data;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class IndicatorInfoDto {
    private int indicatorNum;
    private String indicatorName;
    private int cuttingPoint;
    private String result;

    public IndicatorInfoDto() {
    }

    @QueryProjection
    public IndicatorInfoDto(int indicatorNum, String indicatorName, int cuttingPoint, String result) {
        this.indicatorNum = indicatorNum;
        this.indicatorName = indicatorName;
        this.cuttingPoint = cuttingPoint;
        this.result = result;
    }
}
