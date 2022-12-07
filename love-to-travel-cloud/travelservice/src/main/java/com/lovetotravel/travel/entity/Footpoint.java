package com.lovetotravel.travel.entity;

import com.lovetotravel.travel.entity.dto.Been;
import com.lovetotravel.travel.entity.dto.Want;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Footpoint {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("想去")
    private Want[] wants;

    @ApiModelProperty("去过")
    private Been[] beens;

}
