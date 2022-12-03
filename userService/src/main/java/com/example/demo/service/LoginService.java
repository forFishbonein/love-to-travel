package com.example.demo.service;

import com.example.demo.entity.Login;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface LoginService {
    List<Login> findLoginAll();

    Login findLoginByID(Integer id);

    Integer insertLogin(Login login);

    Integer updateLogin(Login login);

    Integer deleteLogin(Integer id);
}
