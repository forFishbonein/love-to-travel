package com.lovetotravel.travel.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HotCity {

    @ApiModelProperty("城市id")
    private String cityId;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("经度")
    private String lng;

    @ApiModelProperty("维度")
    private String lat;

}
