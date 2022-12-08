package com.lovetotravel.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Log {

    @ApiModelProperty("uuid")
    private String uuid;

    @ApiModelProperty("用户邮箱")
    private String userEmail;

    @ApiModelProperty("操作系统")
    private String osName;

    @ApiModelProperty("浏览器")
    private String browserName;

    @ApiModelProperty("IP地址")
    private String ipAddress;

    @ApiModelProperty("创建时间")
    private String createTime;

}
