package com.lovetotravel.travel.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubPlan {

    @ApiModelProperty("城市id")
    private Integer cityId;

    @ApiModelProperty("城市")
    private String city;

}
