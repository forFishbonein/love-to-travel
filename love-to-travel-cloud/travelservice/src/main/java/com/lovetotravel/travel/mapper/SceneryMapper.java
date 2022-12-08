package com.lovetotravel.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lovetotravel.travel.entity.Scenery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SceneryMapper extends BaseMapper<Scenery> {

    Scenery getById(Long id);

}
