package com.lovetotravel.travel.handler;

import com.lovetotravel.travel.redis.RedisService;
import com.lovetotravel.travel.service.NoteService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


//指定为切面类
@Aspect
@Component
public class MyAspect {
    final NoteService noteService;
    final RedisService redisService;

    public MyAspect(NoteService noteService, RedisService redisService) {
        this.noteService = noteService;
        this.redisService = redisService;
    }

    @Pointcut("execution(public * com.lovetotravel.travel.controller.NoteController.getById(..))")
    public void viewPointCut(){}

    @After("viewPointCut()")
    public void doViewAfter(JoinPoint joinPoint) throws Throwable {
        Object[] objs=joinPoint.getArgs();
        String id=(String) objs[0];
        //根据id更新浏览量
        noteService.incrView(id);
    }


}