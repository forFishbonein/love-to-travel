package com.lovetotravel.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.FollowerVo;
import com.lovetotravel.user.entity.vo.LoginVo;
import com.lovetotravel.user.entity.vo.RegisterVo;
import com.lovetotravel.user.entity.vo.UpdatePasswordVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService extends IService<User> {


    User getById(long id);

    String insert(HttpServletResponse response, RegisterVo registerVo);

    User getByEmail(String email);

    void addCookie(HttpServletResponse response, String token, User user);

    User getByToken(HttpServletResponse response, String token);

    String codeLogin(HttpServletResponse response, LoginVo loginVo);

    String passLogin(HttpServletResponse response, LoginVo loginVo);

    void logout(String token);

    List<User> getAll();

    void updatePassword(UpdatePasswordVo updatePasswordVo);

    void updateProfile(User user);

    void addFollower(FollowerVo followerVo);

    void removeFollower(FollowerVo followerVo);

    Long sumFollower(Long id);

    List<User> getAllFollower(Long id);

    Long sumFollowee(Long id);

    List<User> getAllFollowee(Long id);

}
