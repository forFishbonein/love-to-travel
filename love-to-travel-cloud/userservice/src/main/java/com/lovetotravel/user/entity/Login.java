package com.lovetotravel.user.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Login {
    private Long id;

    private String email;

    private String password;

    private String status;

    @TableLogic
    private String deleted;

    private String createTime;

    private String updateTime;

}
