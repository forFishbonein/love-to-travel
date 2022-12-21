package com.lovetotravel.travel.entity.vo.product;

import com.lovetotravel.travel.entity.Plan;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ProductShowVo {

    @Id
    private String id;

    private String name;

    private String introduction;

    private String planId;

    private Plan plan;

    private String price;

    private String tele;

    private String url;

    private String status;

    private String createTime;

    private String cost;

}
