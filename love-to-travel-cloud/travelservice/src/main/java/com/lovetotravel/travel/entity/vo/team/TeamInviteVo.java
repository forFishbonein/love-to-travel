package com.lovetotravel.travel.entity.vo.team;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamInviteVo {

    @ApiModelProperty("组队id")
    private String teamId;

    @ApiModelProperty("队名")
    private String teamName;

    @ApiModelProperty("邀请人id")
    private String inviterId;

    @ApiModelProperty("用户邮箱")
    private String userEmail;

}
