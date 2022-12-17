package com.lovetotravel.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Product {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    private String introduction;

    private String planId;

    private String price;

    private String tele;

    private String url;

    @TableLogic
    private String deleted;

    private String createTime;

}
