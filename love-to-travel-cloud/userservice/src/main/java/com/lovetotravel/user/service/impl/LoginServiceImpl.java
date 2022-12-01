package com.lovetotravel.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lovetotravel.user.common.result.CodeMsg;
import com.lovetotravel.user.entity.Login;
import com.lovetotravel.user.entity.vo.RegisterVo;
import com.lovetotravel.user.exception.GlobalException;
import com.lovetotravel.user.mapper.LoginMapper;
import com.lovetotravel.user.service.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements LoginService {


    final LoginMapper loginMapper;

    public LoginServiceImpl(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    // 注册信息 登陆部分添加到login表
    public void insert(RegisterVo registerVo) {
        //判断用户是否存在
        QueryWrapper<Login> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Login::getEmail, registerVo.getEmail());
        Login loginInMysql = loginMapper.selectOne(queryWrapper);
        if (loginInMysql != null) {
            throw new GlobalException(CodeMsg.USER_EXIST);
        }

        Login login = new Login();
        BeanUtils.copyProperties(registerVo, login);

        loginMapper.insert(login);
    }
}
