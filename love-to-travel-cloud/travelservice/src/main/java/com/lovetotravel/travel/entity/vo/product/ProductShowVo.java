package com.lovetotravel.travel.entity.vo.product;

import com.lovetotravel.travel.entity.Plan;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ProductShowVo {

    @Id
    private Integer id;

    private String name;

    private String introduction;

    private Plan plan;

    private String price;

    private String createTime;

}