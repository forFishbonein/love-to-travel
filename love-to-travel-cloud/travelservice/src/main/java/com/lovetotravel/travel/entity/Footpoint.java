package com.lovetotravel.travel.entity;

import com.lovetotravel.travel.entity.dto.Been;
import com.lovetotravel.travel.entity.dto.Want;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "footpoint")
public class Footpoint {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("想去")
    private List<Want> wants;

    @ApiModelProperty("去过")
    private List<Been> beens;

}
