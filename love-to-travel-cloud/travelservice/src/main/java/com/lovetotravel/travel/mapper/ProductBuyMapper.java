package com.lovetotravel.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lovetotravel.travel.entity.Product;
import com.lovetotravel.travel.entity.vo.NewNum;
import com.lovetotravel.travel.entity.vo.product.ProductBuy;
import com.lovetotravel.travel.entity.vo.product.ProductShowVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductBuyMapper extends BaseMapper<ProductBuy> {

    List<NewNum> getNewProductNum();


    List<NewNum> getNewProductBuyNum();

    NewNum getCost();

    List<ProductShowVo> getBuyInfo(String id);
}
