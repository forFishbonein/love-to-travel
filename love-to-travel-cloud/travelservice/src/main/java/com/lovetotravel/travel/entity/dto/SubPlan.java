package com.lovetotravel.travel.entity.dto;

import com.lovetotravel.travel.entity.dto.Route;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubPlan {

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("路线")
    private Route route;

}
