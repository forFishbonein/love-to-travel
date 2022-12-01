package com.lovetotravel.user.service;


import com.lovetotravel.user.entity.vo.LoginVo;
import com.lovetotravel.user.entity.vo.RegisterVo;

public interface LoginService{


    void insert(RegisterVo registerVo);

    void checkCode(LoginVo loginVo);

    void checkPass(LoginVo loginVo);

}
