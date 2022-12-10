package com.lovetotravel.travel.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubPlan {

    @ApiModelProperty("城市id")
    private String cityId;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("行程中的一天")
    private Days[] days;

    @ApiModelProperty("当前城市停留天数")
    private Integer dayLength;

}
