package com.lovetotravel.user.controller;


import com.lovetotravel.user.common.result.Result;
import com.lovetotravel.user.entity.vo.LoginVo;
import com.lovetotravel.user.service.EmailService;
import com.lovetotravel.user.service.LoginService;
import com.lovetotravel.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
public class LoginController {

    final LoginService loginService;
    final UserService userService;
    final EmailService emailService;

    public LoginController(LoginService loginService, UserService userService, EmailService emailService) {
        this.loginService = loginService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public Result<String> sendMimeMail(@RequestBody String email) {
        emailService.sendEmail(email);
        return Result.success("已发送");
    }


    @PostMapping("/codeLogin")
    public Result<String> codeLogin(HttpServletResponse response, @RequestBody LoginVo loginVo) {
        loginService.checkCode(loginVo);
        userService.login(response, loginVo);
        return Result.success("登录成功");
    }

    @PostMapping("/login")
    public Result<String> passLogin(HttpServletResponse response, @RequestBody LoginVo loginVo) {
        loginService.checkPass(loginVo);
        userService.login(response, loginVo);
        return Result.success("登录成功");
    }

}
