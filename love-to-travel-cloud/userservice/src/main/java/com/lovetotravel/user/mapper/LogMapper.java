package com.lovetotravel.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lovetotravel.user.entity.Log;
import com.lovetotravel.user.entity.vo.NewNum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogMapper extends BaseMapper<Log> {

    List<NewNum> getNewLogNum();


}
