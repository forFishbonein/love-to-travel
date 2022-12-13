package com.lovetotravel.travel.entity.vo.foot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BeenRemoveVo {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("城市代码")
    private String cityId;

    @ApiModelProperty("评分")
    private String score;

    @ApiModelProperty("添加时间")
    private String createTime;
}
