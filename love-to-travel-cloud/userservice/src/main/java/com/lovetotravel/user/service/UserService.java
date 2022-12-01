package com.lovetotravel.user.service;

import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.LoginVo;
import com.lovetotravel.user.entity.vo.RegisterVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {


    User getById(long id);

    void insert(RegisterVo registerVo);

    User getByEmail(String email);

    String login(HttpServletResponse response, LoginVo loginVo);

    void addCookie(HttpServletResponse response, String token, User user);

    User getByToken(HttpServletResponse response, String token);

    void sendEmail(LoginVo loginVo);

//    void register(HttpServletResponse response, TeacherRegisterVo registerVo);

    String logout(String token);

//    String registered(Teacher teacher);

//    ShowVo show(String id);

    List<User> getAll();


}
