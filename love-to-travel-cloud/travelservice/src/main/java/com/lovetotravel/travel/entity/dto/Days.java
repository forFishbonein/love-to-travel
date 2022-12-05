package com.lovetotravel.travel.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Days {

    @ApiModelProperty("路线")
    private Route[] route;

}
