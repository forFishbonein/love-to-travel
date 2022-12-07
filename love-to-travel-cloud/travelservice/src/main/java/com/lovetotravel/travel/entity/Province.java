package com.lovetotravel.travel.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Province {

    @ApiModelProperty("省份id")
    private String provinceId;

    @ApiModelProperty("城市名称")
    private String provinceName;

    @ApiModelProperty("图片地址")
    private String url;

    @ApiModelProperty("简介")
    private String introduction;

}
