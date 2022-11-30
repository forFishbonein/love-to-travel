package com.lovetotravel.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.mapper.UserMapper;
import com.lovetotravel.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    final
    RedisTemplate redisTemplate;

    public UserServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public User getById(long id) {
        return null;
    }
}
