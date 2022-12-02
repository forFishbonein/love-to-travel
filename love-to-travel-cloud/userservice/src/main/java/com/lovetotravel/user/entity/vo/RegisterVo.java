package com.lovetotravel.user.entity.vo;

import lombok.Data;

@Data
public class RegisterVo {
    private Long id;

    private String email;

    private String code;

    private String password;

    private String url;

    private String name;

    private String tele;

    private String birthday;

    private String post;

    private String profession;

    private String gender;

    private String address;

    private String status;
}
