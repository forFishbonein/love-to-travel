package com.lovetotravel.user.controller;


import com.lovetotravel.user.common.result.Result;
import com.lovetotravel.user.entity.vo.LoginVo;
import com.lovetotravel.user.service.EmailService;
import com.lovetotravel.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
    public Result<String> codeLogin(HttpServletResponse response, @RequestBody LoginVo loginVo) {
        return Result.success(userService.codeLogin(response, loginVo));
    }

    @ApiOperation("密码登录")
    @PostMapping("/login")
    public Result<String> passLogin(HttpServletResponse response, @RequestBody LoginVo loginVo) {
        return Result.success(userService.passLogin(response, loginVo));
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result<String> logout(String token) {
        return Result.success(userService.logout(token));
    }


}
