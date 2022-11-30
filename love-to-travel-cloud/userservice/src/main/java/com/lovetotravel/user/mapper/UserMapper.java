package com.lovetotravel.user.mapper;
/*
 *
 * @Time : 2022/11/30
 * @Author : https://blog.csdn.net/m0_56170277
 * @File : love-to-travel
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lovetotravel.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
