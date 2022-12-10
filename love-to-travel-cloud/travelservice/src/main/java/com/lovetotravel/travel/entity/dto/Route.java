package com.lovetotravel.travel.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Route {

    @ApiModelProperty("起点")
    private Double[] origin;

    @ApiModelProperty("起点名称")
    private String originName;

    @ApiModelProperty("终点")
    private Double[] destination;

    @ApiModelProperty("终点名称")
    private String destinationName;

    @ApiModelProperty("停留时间")
    private Integer departTime;

    @ApiModelProperty("交通工具")
    private String vehicle;

    @ApiModelProperty("天气")
    private String weather;
}
