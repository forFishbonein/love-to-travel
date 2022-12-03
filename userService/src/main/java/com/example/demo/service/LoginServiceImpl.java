package com.example.demo.service;

import com.example.demo.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Login;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Override
    public List<Login> findLoginAll() {
        return loginMapper.findLoginAll();
    }

    @Override
    public Login findLoginByID(Integer id) {
        return loginMapper.findLoginByID(id);
    }

    @Override
    public Integer insertLogin(Login login) {
//        Integer num = (int)((Math.random()*9+1)*1000);
//        login.setID(num.toString());
//        System.out.println(login.toString());
        return loginMapper.insertLogin(login);
    }

    @Override
    public Integer updateLogin(Login login) {
        return loginMapper.updateLogin(login);
    }

    @Override
    public Integer deleteLogin(Integer id) {
        return loginMapper.deleteLogin(id);
    }
}
