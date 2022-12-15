package com.lovetotravel.travel.entity.vo.team;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamCreateVo {

    @ApiModelProperty("行程id")
    private String planId;

    @ApiModelProperty("团队名称")
    private String teamName;

    @ApiModelProperty("所有者")
    private String ownerId;


    @ApiModelProperty("途径地点/规划的地点")
    private String[] place;

    @ApiModelProperty("出行时间")
    private String depart;

    @ApiModelProperty("出行天数")
    private String day;

    @ApiModelProperty("预算")
    private String budget;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("口号")
    private String slogan;

    @ApiModelProperty("预期成员数量")
    private String num;
}
