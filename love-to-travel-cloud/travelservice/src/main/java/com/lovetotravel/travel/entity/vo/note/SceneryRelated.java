package com.lovetotravel.travel.entity.vo.note;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneryRelated {

    @ApiModelProperty("游记id")
    private String noteId;

    @ApiModelProperty("景区id")
    private String sceneryId;

    @ApiModelProperty("景区名称")
    private String sceneryName;


}
