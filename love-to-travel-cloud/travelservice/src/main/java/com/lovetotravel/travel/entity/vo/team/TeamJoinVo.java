package com.lovetotravel.travel.entity.vo.team;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamJoinVo {

    @ApiModelProperty("组队id")
    private String teamId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("联系方式")
    private String tele;

    @ApiModelProperty("口号")
    private String slogan;

}
