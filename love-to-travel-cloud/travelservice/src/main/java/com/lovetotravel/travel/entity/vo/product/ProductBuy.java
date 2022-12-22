package com.lovetotravel.travel.entity.vo.product;

import lombok.Data;

@Data
public class ProductBuy {

    private String id;

    private String productId;

    private String userId;

    private String cost;

    private String status;

    private String paystate;

    private String createTime;

    private String payTime;
}
