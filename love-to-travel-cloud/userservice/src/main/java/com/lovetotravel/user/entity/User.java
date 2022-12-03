package com.lovetotravel.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String email;

    private String password;

    private String status;

    private String url;

    private String name;

    private String grade;

    private String experience;

    private String tele;

    private String birthday;

    private String post;

    private String profession;

    private String signature;

    private String gender;

    private String address;

    private String visits;

    @TableLogic
    private String deleted;

    private String createTime;

    private String updateTime;

}
