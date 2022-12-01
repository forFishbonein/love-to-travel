package com.lovetotravel.user.service;

import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.RegisterVo;

public interface UserService {


    User getById(long id);

    void insert(RegisterVo registerVo);

    User getByEmail(String email);




}
