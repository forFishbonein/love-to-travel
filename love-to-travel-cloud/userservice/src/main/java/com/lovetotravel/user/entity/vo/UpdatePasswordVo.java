package com.lovetotravel.user.entity.vo;

import lombok.Data;

@Data
public class UpdatePasswordVo {

    private Long id;

    private String email;

    private String code;

    private String oldPassword;

    private String newPassword;

    private String token;

}
