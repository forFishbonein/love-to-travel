package com.lovetotravel.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lovetotravel.user.common.result.CodeMsg;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.RegisterVo;
import com.lovetotravel.user.exception.GlobalException;
import com.lovetotravel.user.mapper.UserMapper;
import com.lovetotravel.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    final UserMapper userMapper;
    final RedisTemplate redisTemplate;

    public UserServiceImpl(RedisTemplate redisTemplate, UserMapper userMapper) {
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
    }


    @Override
    public User getById(long id) {


        return userMapper.selectById(id);
    }

    @Override
    public void insert(RegisterVo registerVo) {
        //判断用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getEmail, registerVo.getEmail());
        User userInMysql = userMapper.selectOne(queryWrapper);
        if (userInMysql != null) {
            throw new GlobalException(CodeMsg.USER_EXIST);
        }

        User user = new User();
        BeanUtils.copyProperties(registerVo, user);

        userMapper.insert(user);
    }

    @Override
    public User getByEmail(String email) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getEmail, email);
        User userInMysql = userMapper.selectOne(queryWrapper);

        return userInMysql;
    }


}
