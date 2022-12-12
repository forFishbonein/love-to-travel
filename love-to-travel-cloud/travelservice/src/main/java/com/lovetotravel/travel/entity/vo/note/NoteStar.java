package com.lovetotravel.travel.entity.vo.note;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoteStar {

    @ApiModelProperty("游记id")
    private String noteId;

    @ApiModelProperty("用户id")
    private String userId;

}
