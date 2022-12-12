package com.lovetotravel.travel.entity.vo.foot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BeenVo {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("城市代码")
    private String cityId;

    @ApiModelProperty("评分")
    private String score;
}
