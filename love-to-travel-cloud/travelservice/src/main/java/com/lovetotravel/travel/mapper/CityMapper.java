package com.lovetotravel.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lovetotravel.travel.entity.City;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CityMapper extends BaseMapper<City> {

    List<City> getHotCity();

}
