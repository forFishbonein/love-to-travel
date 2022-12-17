package com.lovetotravel.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lovetotravel.travel.entity.Product;
import com.lovetotravel.travel.entity.vo.NewNum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    List<NewNum> getNewProductNum();


}
