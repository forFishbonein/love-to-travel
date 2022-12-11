package com.lovetotravel.user.handler;

import com.lovetotravel.user.entity.Log;
import com.lovetotravel.user.entity.vo.LoginVo;
import com.lovetotravel.user.mapper.LogMapper;
import com.lovetotravel.user.redis.RedisService;
import com.lovetotravel.user.utils.UUIDUtil;
import com.lovetotravel.user.result.Result;
import com.lovetotravel.user.service.UserService;
import com.lovetotravel.user.utils.LogUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


//指定为切面类
@Aspect
@Component
public class MyAspect {
    final UserService userService;
    final RedisService redisService;
    final LogMapper logMapper;

    public MyAspect(UserService userService, RedisService redisService, LogMapper logMapper) {
        this.userService = userService;
        this.redisService = redisService;
        this.logMapper = logMapper;
    }

    @Pointcut("execution(public * com.lovetotravel.user.controller.LoginController.passLogin(..)) || execution(public * com.lovetotravel.user.controller.LoginController.codeLogin(..))")
    public void loginPointCut(){}

    @AfterReturning(value = "loginPointCut()", returning = "result")
    public void doLoginAfter(JoinPoint joinPoint, Object result) throws Throwable {

        Object[] objs = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) objs[0];
        LoginVo login = (LoginVo) objs[2];
        String email = login.getEmail();

        Result r = (Result) result;

        if (r.getCode() == 0) {
            String osName = LogUtil.osName(request);
            String browserName = LogUtil.browserName(request);
            String ipAddress = LogUtil.getIpAddress(request);
            Log log = new Log();
            log.setUuid(UUIDUtil.uuid());
            log.setUserEmail(email);
            log.setOsName(osName);
            log.setBrowserName(browserName);
            log.setIpAddress(ipAddress);
            logMapper.insert(log);
        }

    }


}