package com.lovetotravel.user.entity.vo;
/*
 *
 * @Time : 2022/11/30
 * @Author : https://blog.csdn.net/m0_56170277
 * @File : love-to-travel
 */

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class LoginVo {

    private String email;

    private String password;

    private String code;

    private String status;

    @TableLogic
    private String deleted;

    private String createTime;

    private String updateTime;

}
