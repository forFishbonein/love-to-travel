package com.lovetotravel.travel.entity.vo.team;

import com.lovetotravel.travel.entity.dto.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamKickVo {

    @ApiModelProperty("组队id")
    private String teamId;

    @ApiModelProperty("用户")
    private Member member;

}
