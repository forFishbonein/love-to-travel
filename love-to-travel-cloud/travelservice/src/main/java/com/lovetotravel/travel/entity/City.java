package com.lovetotravel.travel.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class City {

    @ApiModelProperty("城市id")

    private String cityId;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("城市英文名称")
    private String cityEname;

    @ApiModelProperty("经度")
    private String lng;

    @ApiModelProperty("维度")
    private String lat;

    @ApiModelProperty("图片地址")
    private String url;

    @ApiModelProperty("简介")
    private String introduction;

}
