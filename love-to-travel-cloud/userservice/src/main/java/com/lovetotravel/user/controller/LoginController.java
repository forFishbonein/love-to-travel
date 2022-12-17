package com.lovetotravel.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lovetotravel.user.entity.Log;
import com.lovetotravel.user.entity.PageVo;
import com.lovetotravel.user.entity.vo.LoginVo;
import com.lovetotravel.user.entity.vo.NewNum;
import com.lovetotravel.user.result.Result;
import com.lovetotravel.user.service.EmailService;
import com.lovetotravel.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "登录信息管理接口")
@RestController
@RequestMapping
public class LoginController {

    final UserService userService;
    final EmailService emailService;


    public LoginController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @ApiOperation("发送邮件")
    @PostMapping("/send")
    public Result<String> sendMimeMail(@RequestBody LoginVo loginVo) {
        emailService.sendEmail(loginVo);
        return Result.success("已发送");
    }

    @ApiOperation("验证码登录")
    @PostMapping("/codelogin")
    public Result<String> codeLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginVo loginVo) {
        return Result.success(userService.codeLogin(response, loginVo));
    }

    @ApiOperation("密码登录")
    @PostMapping("/login")
    public Result<String> passLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginVo loginVo) {
        return Result.success(userService.passLogin(response, loginVo));
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        userService.logout(token);
        return Result.success("退出登录成功");
    }

    @ApiOperation("日志查询")
    @PostMapping("/log")
    public Result<Page<Log>> getAllLog(@RequestBody PageVo pageVo) {
        return Result.success(userService.getAllLog(pageVo));
    }

    @ApiOperation("7天日志统计")
    @GetMapping("/log/new")
    public Result<List<NewNum>> getNewLogNum() {
        return Result.success(userService.getNewLogNum());
    }

    @ApiOperation("获取登录总数")
    @GetMapping("/log/all")
    public Result<Integer> getAllLog() {
        return Result.success(userService.getAllLog());
    }
}
