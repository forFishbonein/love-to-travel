package com.lovetotravel.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("头像")
    private String url;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("等级")
    private String grade;

    @ApiModelProperty("经验")
    private String experience;

    @ApiModelProperty("电话")
    private String tele;

    @ApiModelProperty("出生日期")
    private String birthday;

    @ApiModelProperty("岗位")
    private String post;

    @ApiModelProperty("职业")
    private String profession;

    @ApiModelProperty("个性签名")
    private String signature;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("访客数量")
    private String visits;

    @ApiModelProperty("逻辑删除")
    @TableLogic
    private String deleted;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("修改时间")
    private String updateTime;

}
