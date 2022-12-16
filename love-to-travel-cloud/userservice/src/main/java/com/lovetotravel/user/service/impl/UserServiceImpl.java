package com.lovetotravel.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lovetotravel.user.entity.Log;
import com.lovetotravel.user.entity.PageVo;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.*;
import com.lovetotravel.user.exception.GlobalException;
import com.lovetotravel.user.mapper.LogMapper;
import com.lovetotravel.user.mapper.UserMapper;
import com.lovetotravel.user.redis.CodeKey;
import com.lovetotravel.user.redis.FollowKey;
import com.lovetotravel.user.redis.RedisService;
import com.lovetotravel.user.redis.UserKey;
import com.lovetotravel.user.utils.UUIDUtil;
import com.lovetotravel.user.result.CodeMsg;
import com.lovetotravel.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    final UserMapper userMapper;
    final RedisService redisService;
    final LogMapper logMapper;

    public UserServiceImpl(UserMapper userMapper, RedisService redisService, LogMapper logMapper) {
        this.userMapper = userMapper;
        this.redisService = redisService;
        this.logMapper = logMapper;
    }


    @Override
    public User getById(long id) {
        //对象缓存
        //如果缓存中有，则直接取，如果没有，则从数据库查找
        User user = redisService.get(UserKey.getById, "" + id, User.class);
        if (user != null) {
            return user;
        }
        //取数据库
        user = userMapper.selectById(id);
        //再存入缓存
        if (user != null) {
            redisService.set(UserKey.getById, "" + id, user);
        }
        return user;
    }

    /**
     * 注册
     *
     * @param registerVo
     */
    @Override
    public String insert(HttpServletResponse response, RegisterVo registerVo) {
        // 填写内容为空
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
        if (registerVo.getPassword() == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String md5Password = DigestUtils.md5DigestAsHex(registerVo.getPassword().getBytes());
        user.setPassword(md5Password);
        userMapper.insert(user);
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    /**
     * 根据email获取user
     *
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

    /**
     * 检验验证码是否正确
     *
     * @param loginVo
     * @return
     */
    @Override
    public String codeLogin(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String email = loginVo.getEmail();
        String code = loginVo.getCode();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getEmail, email);
        User userInMysql = getOne(queryWrapper);
        if (userInMysql == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        String codeInRedis = redisService.get(CodeKey.code, email, String.class);
        if (codeInRedis == null || !codeInRedis.equals(code)) {
            throw new GlobalException(CodeMsg.CODE_ERROR);
        }
        redisService.delete(CodeKey.code, email);
        String token = UUIDUtil.uuid();
        User user = getByEmail(loginVo.getEmail());
        addCookie(response, token, user);
        return token;
    }

    /**
     * 检验密码是否正确
     *
     * @param loginVo
     */
    @Override
    public String passLogin(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String email = loginVo.getEmail();
        String password = loginVo.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getEmail, email);
        User userInMysql = getOne(queryWrapper);
        if (userInMysql == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        String passInMysql = userInMysql.getPassword();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!passInMysql.equals(md5Password)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
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

    public void updatePassword(UpdatePasswordVo updatePasswordVo) {
        UpdatePasswordVo up = updatePasswordVo;
        //取user
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getId, up.getId());
        User userInMysql = getOne(queryWrapper);
        if (userInMysql == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        //判断验证码
        String codeInRedis = redisService.get(CodeKey.code, up.getEmail(), String.class);
        if (codeInRedis == null || !codeInRedis.equals(up.getCode())) {
            throw new GlobalException(CodeMsg.CODE_ERROR);
        }
        redisService.delete(CodeKey.code, up.getEmail());
        String passInMysql = userInMysql.getPassword();
        String md5Password = DigestUtils.md5DigestAsHex(up.getOldPassword().getBytes());
        if (!passInMysql.equals(md5Password)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        userInMysql.setPassword(DigestUtils.md5DigestAsHex(up.getNewPassword().getBytes()));
        userMapper.update(userInMysql, queryWrapper);
        //更新缓存：先删除再插入
        redisService.delete(UserKey.getById, "" + up.getId());
        redisService.delete(UserKey.token, up.getToken());
        redisService.set(UserKey.token, up.getToken(), userInMysql);
    }

    @Override
    public void updateProfile(User user) {
        //取user
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getId, user.getId());
        User userInMysql = getOne(queryWrapper);
        if (userInMysql == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        BeanUtils.copyProperties(user, userInMysql);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        userInMysql.setUpdateTime(dateFormat.format(date));
        userMapper.update(userInMysql, queryWrapper);
        //更新缓存：先删除再插入
        redisService.delete(UserKey.getById, "" + user.getId());
    }

    @Override
    public User getByToken(HttpServletResponse response, String token) {
        if (token == null || "".equals(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }


    @Override
    public void logout(String token) {
        redisService.delete(UserKey.token, token);
    }

    @Override
    public List<User> getAll() {
        return userMapper.selectList(null);
    }

    @Override
    public Page<User> getPage(PageVo pageVo) {
        Page<User> page = Page.of(pageVo.getCurrent(),pageVo.getSize());
        page.addOrder(OrderItem.desc("create_time"));
        Page<User> userPage = userMapper.selectPage(page, null);
        return userPage;
    }

    /**
     * 关注别人
     *
     * @param followerVo
     */
    @Override
    public void addFollower(FollowerVo followerVo) {
        if (followerVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String id = followerVo.getId().toString();
        String followerId = followerVo.getFollowerId().toString();
        if (id.equals(followerId)) {
            throw new GlobalException(CodeMsg.FOLLOW_ERROR);
        }
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(User::getId, id);
        User u1 = getOne(queryWrapper1);
        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.lambda().eq(User::getId, followerId);
        User u2 = getOne(queryWrapper2);
        if (u1 == null || u2 == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        redisService.sadd(FollowKey.getFollower, id, followerId);
        redisService.sadd(FollowKey.getFollowee, followerId, id);
    }

    /**
     * 移除关注
     *
     * @param followerVo
     */
    @Override
    public void removeFollower(FollowerVo followerVo) {
        if (followerVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String id = followerVo.getId().toString();
        String followerId = followerVo.getFollowerId().toString();
        if (id.equals(followerId)) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(User::getId, id);
        User u1 = getOne(queryWrapper1);
        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.lambda().eq(User::getId, followerId);
        User u2 = getOne(queryWrapper2);
        if (u1 == null || u2 == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        redisService.srem(FollowKey.getFollower, id, followerId);
        redisService.srem(FollowKey.getFollowee, followerId, id);
    }

    /**
     * 总关注数
     *
     * @param id
     */
    public Long sumFollower(Long id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getId, id);
        User userInMysql = getOne(queryWrapper);
        if (userInMysql == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        String idToString = id.toString();
        return redisService.scard(FollowKey.getFollower, idToString);
    }

    /**
     * 获取id所有关注
     *
     * @param id
     * @return
     */
    @Override
    public List<User> getAllFollower(Long id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getId, id);
        User userInMysql = getOne(queryWrapper);
        if (userInMysql == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        String idToString = id.toString();
        Set<String> set = redisService.smembers(FollowKey.getFollower, idToString);
        List<User> users = listByIds(set);
        System.out.println(users);

        return users;
    }

    /**
     * 总粉丝数
     *
     * @param id
     */
    public Long sumFollowee(Long id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getId, id);
        User userInMysql = getOne(queryWrapper);
        if (userInMysql == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        String idToString = id.toString();
        return redisService.scard(FollowKey.getFollowee, idToString);
    }

    /**
     * 获取id所有粉丝
     *
     * @param id
     * @return
     */
    @Override
    public List<User> getAllFollowee(Long id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getId, id);
        User userInMysql = getOne(queryWrapper);
        if (userInMysql == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        String idToString = id.toString();
        Set<String> set = redisService.smembers(FollowKey.getFollowee, idToString);
        List<User> users = listByIds(set);
        System.out.println(users);
        return users;
    }

    @Override
    public Page<Log> getAllLog(PageVo pageVo) {
        Page<Log> page = Page.of(pageVo.getCurrent(),pageVo.getSize());
        page.addOrder(OrderItem.desc("create_time"));
        Page<Log> logPage = logMapper.selectPage(page, null);
        return logPage;
    }

    @Override
    public List<NewNum> getNewUserNum() {
        List<NewNum> newNum = userMapper.getNewUserNum();
        return newNum;
    }

    @Override
    public List<NewNum> getNewLogNum() {
        List<NewNum> newNum = logMapper.getNewLogNum();
        return newNum;
    }
}
