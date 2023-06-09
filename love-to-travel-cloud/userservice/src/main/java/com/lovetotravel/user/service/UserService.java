package com.lovetotravel.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lovetotravel.user.entity.Log;
import com.lovetotravel.user.entity.PageVo;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.*;
import com.lovetotravel.user.entity.vo.user.UpdateHeadVo;
import com.lovetotravel.user.entity.vo.user.UpdatePasswordVo;

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

    Page<User> getPage(PageVo pageVo);

    void updatePassword(UpdatePasswordVo updatePasswordVo);

    void updateProfile(User user);

    void updateHead(UpdateHeadVo updateHeadVo);

    void addFollower(FollowerVo followerVo);

    void removeFollower(FollowerVo followerVo);

    Boolean isFollow(FollowerVo followerVo);

    Long sumFollower(Long id);

    List<User> getAllFollower(Long id);

    Long sumFollowee(Long id);

    List<User> getAllFollowee(Long id);

    Page<Log> getAllLog(PageVo pageVo);

    List<NewNum> getNewUserNum();

    List<NewNum> getNewLogNum();

    Integer getAllNum();

    Integer getAllLog();

    List<User> getUserRedommond(String id);

}
