package com.lovetotravel.travel.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Product {

    @Id
    private Long id;

    private String name;

    private String introduction;

    private String planId;

    private String price;

    @TableLogic
    private String deleted;

    private String createTime;

}
