package com.lovetotravel.feign.entity;

import lombok.Data;

@Data
public class User {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String email;

    private String password;

    private String status;

    private String url;

    private String name;

    private String grade;

    private Integer experience;

    private String tele;

    private String birthday;

    private String post;

    private String profession;

    private String signature;

    private String gender;

    private String address;

    private String visits;

    private String deleted;

    private String createTime;

    private String updateTime;
}
