package com.lovetotravel.travel.handler;

import com.lovetotravel.feign.entity.User;
import com.lovetotravel.travel.redis.NoteKey;
import com.lovetotravel.travel.redis.RedisService;
import com.lovetotravel.travel.redis.UserKey;
import com.lovetotravel.travel.result.Result;
import com.lovetotravel.travel.service.NoteService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


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
    public void noteRecord() {
    }

    @AfterReturning(value = "noteRecord()", returning = "result")
    public void doNoteRecord(JoinPoint joinPoint, Object result) throws Throwable {
        Object[] objs = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) objs[0];
        String token = request.getHeader("Authorization");
        User user = redisService.get(UserKey.token, token, User.class);
        String noteId = (String) objs[1];
        Result r = (Result) result;
        if (r.getCode() == 0) {
            //根据id更新浏览量
            noteService.incrView(noteId);
            redisService.sadd(NoteKey.getNoteRecord, user.getId().toString(), noteId);
        }
    }

    @Pointcut("execution(public * com.lovetotravel.travel.controller.SceneryController.getById(..))")
    public void sceneryRecord(){}

    @AfterReturning(value = "sceneryRecord()", returning = "result")
    public void doSceneryRecord(JoinPoint joinPoint, Object result) throws Throwable {
        Object[] objs = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) objs[0];
        String token = request.getHeader("Authorization");
        if (token != null) {
            User user = redisService.get(UserKey.token, token, User.class);
            if (user != null) {
                Long sceneryId = (Long) objs[1];
                Result r = (Result) result;
                if (r.getCode() == 0) {
                    redisService.sadd(NoteKey.getNoteRecord, user.getId().toString(), sceneryId.toString());
                }
            }
        }
    }
}