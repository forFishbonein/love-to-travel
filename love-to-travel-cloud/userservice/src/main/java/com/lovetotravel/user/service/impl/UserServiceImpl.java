package com.lovetotravel.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lovetotravel.user.common.result.CodeMsg;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.LoginVo;
import com.lovetotravel.user.entity.vo.RegisterVo;
import com.lovetotravel.user.exception.GlobalException;
import com.lovetotravel.user.mapper.UserMapper;

import com.lovetotravel.user.redis.CodeKey;
import com.lovetotravel.user.redis.RedisService;
import com.lovetotravel.user.redis.UserKey;
import com.lovetotravel.user.redis.utils.UUIDUtil;
import com.lovetotravel.user.service.UserService;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    final UserMapper userMapper;
    final RedisService redisService;

    public UserServiceImpl(UserMapper userMapper, RedisService redisService) {
        this.userMapper = userMapper;
        this.redisService = redisService;
    }


    @Override
    public User getById(long id) {
        return userMapper.selectById(id);
    }


    /**
     * 注册
     * @param registerVo
     */
    @Override
    public void insert(RegisterVo registerVo) {
        if (registerVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String email = registerVo.getEmail();
        String code = registerVo.getCode();
        //判断用户（邮箱）是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getEmail, registerVo.getEmail());
        User userInMysql = userMapper.selectOne(queryWrapper);
        if (userInMysql != null) {
            throw new GlobalException(CodeMsg.USER_EXIST);
        }
        String codeInRedis = redisService.get(CodeKey.code, email, String.class);
        if (codeInRedis == null || !codeInRedis.equals(code)) {
            throw new GlobalException(CodeMsg.CODE_ERROR);
        }
        redisService.delete(CodeKey.code, email);
        User user = new User();
        BeanUtils.copyProperties(registerVo, user);
        userMapper.insert(user);
    }

    /**
     * 根据email获取user
     * @param email
     * @return
     */
    @Override
    public User getByEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getEmail, email);
        User userInMysql = userMapper.selectOne(queryWrapper);
        return userInMysql;
    }

    @Override
    public String login(HttpServletResponse response, LoginVo loginVo) {
        String token = UUIDUtil.uuid();
        User user = getByEmail(loginVo.getEmail());
        addCookie(response, token, user);
        return token;
    }

    public void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");//设置为网站根目录
        response.addCookie(cookie);
    }

    @Override
    public User getByToken(HttpServletResponse response, String token) {
        return null;
    }


    @Override
    public String logout(String token) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

}
