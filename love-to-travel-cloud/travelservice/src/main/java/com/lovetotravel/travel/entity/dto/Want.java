package com.lovetotravel.travel.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Want {

    @ApiModelProperty("城市代码")
    private String cityCode;

    @ApiModelProperty("添加时间")
    private String createTime;

}
