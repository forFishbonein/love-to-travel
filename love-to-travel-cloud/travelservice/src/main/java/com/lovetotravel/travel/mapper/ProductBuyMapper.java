package com.lovetotravel.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lovetotravel.travel.entity.vo.NewNum;
import com.lovetotravel.travel.entity.vo.product.ProductBuy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductBuyMapper extends BaseMapper<ProductBuy> {

    List<NewNum> getNewProductNum();

}
