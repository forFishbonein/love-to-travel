package com.lovetotravel.user.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Medal {

    @TableId
    private Integer id;

    private String name;

    private String url;

    private Integer experience;

    private String describe;

    @TableLogic
    private String deleted;

    private String createTime;

    private String updateTime;

}
