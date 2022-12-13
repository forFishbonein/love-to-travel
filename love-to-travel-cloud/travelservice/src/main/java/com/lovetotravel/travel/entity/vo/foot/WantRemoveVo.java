package com.lovetotravel.travel.entity.vo.foot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WantRemoveVo {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("城市代码")
    private String cityId;

    @ApiModelProperty("添加时间")
    private String createTime;

}
