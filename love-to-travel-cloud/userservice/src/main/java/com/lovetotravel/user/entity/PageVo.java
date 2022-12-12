package com.lovetotravel.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageVo {

    @ApiModelProperty("当前页码")
    private Long current;

    @ApiModelProperty("每页显示的数据数量")
    private Long size;

}
