package com.lovetotravel.user.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Admin {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String email;

    private String password;

    @TableLogic
    private String deleted;

    private String createTime;

    private String updateTime;

}
