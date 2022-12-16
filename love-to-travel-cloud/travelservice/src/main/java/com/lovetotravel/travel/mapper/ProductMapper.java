package com.lovetotravel.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lovetotravel.travel.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
