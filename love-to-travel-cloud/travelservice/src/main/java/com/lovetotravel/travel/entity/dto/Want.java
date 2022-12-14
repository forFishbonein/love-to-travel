package com.lovetotravel.travel.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Want {

    @ApiModelProperty("城市代码")
    private String cityId;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("经纬度")
    private Double[] pos;

    @ApiModelProperty("添加时间")
    private String createTime;



}
