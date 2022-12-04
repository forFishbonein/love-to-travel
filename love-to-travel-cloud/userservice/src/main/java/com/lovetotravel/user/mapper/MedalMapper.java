package com.lovetotravel.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lovetotravel.user.entity.Medal;
import com.lovetotravel.user.entity.vo.MedalVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MedalMapper extends BaseMapper<Medal> {

    void applyMedal(MedalVo medalVo);

}
