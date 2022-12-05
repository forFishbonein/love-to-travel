package com.lovetotravel.travel.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Route {

    @ApiModelProperty("起点")
    private Integer[] origin;

    @ApiModelProperty("终点")
    private Integer[] destination;

    @ApiModelProperty("停留时间")
    private Integer departTime;

    @ApiModelProperty("交通工具")
    private String vehicle;

    @ApiModelProperty("预算")
    private String budget;

    @ApiModelProperty("天气")
    private String weather;
}
