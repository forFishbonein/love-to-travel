package com.lovetotravel.travel.entity.vo;

import com.lovetotravel.travel.entity.dto.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamUpdateVo {

    @ApiModelProperty("组队id")
    private String id;

    @ApiModelProperty("行程id")
    private String planId;

    @ApiModelProperty("团队名称")
    private String teamName;

    @ApiModelProperty("途径地点/规划的地点")
    private String[] place;

    @ApiModelProperty("出行时间")
    private String depart;

    @ApiModelProperty("预算")
    private String budget;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("预期成员数量")
    private String num;

    @ApiModelProperty("成员信息")
    private Member[] members;

}
