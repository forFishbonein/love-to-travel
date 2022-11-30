package com.lovetotravel.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.mapper.UserMapper;
import com.lovetotravel.user.service.UserService;
import org.springframework.stereotype.Service;

/*
 *
 * @Time : 2022/11/30
 * @Author : https://blog.csdn.net/m0_56170277
 * @File : love-to-travel
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
