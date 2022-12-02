package com.lovetotravel.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lovetotravel.user.common.result.CodeMsg;
import com.lovetotravel.user.entity.Login;
import com.lovetotravel.user.entity.vo.LoginVo;
import com.lovetotravel.user.entity.vo.RegisterVo;
import com.lovetotravel.user.exception.GlobalException;
import com.lovetotravel.user.mapper.LoginMapper;
import com.lovetotravel.user.redis.CodeKey;
import com.lovetotravel.user.redis.RedisService;
import com.lovetotravel.user.service.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements LoginService {


    final RedisService redisService;
    final LoginMapper loginMapper;

    public LoginServiceImpl(RedisService redisService, LoginMapper loginMapper) {
        this.redisService = redisService;
        this.loginMapper = loginMapper;
    }

    /**
     * 注册信息 登陆部分添加到login表
     * 简单md5加密
     * @param registerVo
     */
    @Override
    public void insert(RegisterVo registerVo) {
        //判断用户是否存在
        QueryWrapper<Login> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Login::getEmail, registerVo.getEmail());
        Login loginInMysql = loginMapper.selectOne(queryWrapper);
        if (loginInMysql != null) {
            throw new GlobalException(CodeMsg.USER_EXIST);
        }
        Login login = new Login();
        String oldPassword = registerVo.getPassword();
        String newPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        BeanUtils.copyProperties(registerVo, login);
        login.setPassword(newPassword);
        loginMapper.insert(login);
    }

    /**
     * 检验验证码是否正确
     * @param loginVo
     * @return
     */
    @Override
    public void checkCode(LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String email = loginVo.getEmail();
        String code = loginVo.getCode();
        QueryWrapper<Login> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Login::getEmail, email);
        Login userInMysql = getOne(queryWrapper);
        if (userInMysql == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        String codeInRedis = redisService.get(CodeKey.code, email, String.class);
        if (codeInRedis == null || !codeInRedis.equals(code)) {
            throw new GlobalException(CodeMsg.CODE_ERROR);
        }
        redisService.delete(CodeKey.code, email);
    }

    /**
     * 检验密码是否正确
     * @param loginVo
     */
    @Override
    public void checkPass(LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String email = loginVo.getEmail();
        String password = loginVo.getPassword();
        QueryWrapper<Login> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Login::getEmail, email);
        Login userInMysql = getOne(queryWrapper);
        if (userInMysql == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        String passInMysql = userInMysql.getPassword();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!passInMysql.equals(md5Password)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
    }


}
