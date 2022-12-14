package com.lovetotravel.travel.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Been {

    @ApiModelProperty("城市代码")
    private String cityId;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("添加时间")
    private String createTime;

    @ApiModelProperty("评分")
    private String score;

}
