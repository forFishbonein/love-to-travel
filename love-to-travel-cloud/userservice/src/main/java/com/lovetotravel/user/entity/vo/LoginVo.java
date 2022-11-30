package com.lovetotravel.user.entity.vo;

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
