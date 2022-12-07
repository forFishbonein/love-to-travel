package com.lovetotravel.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Log {

    @ApiModelProperty("用户邮箱")
    private String uuid;

    @ApiModelProperty("用户邮箱")
    private String userEmail;

    @ApiModelProperty("用户邮箱")
    private String osName;

    @ApiModelProperty("用户邮箱")
    private String browserName;

    @ApiModelProperty("用户邮箱")
    private String ipAddress;

}
