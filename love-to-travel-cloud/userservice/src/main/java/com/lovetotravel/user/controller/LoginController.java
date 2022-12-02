package com.lovetotravel.user.controller;


import com.lovetotravel.user.common.result.Result;
import com.lovetotravel.user.entity.vo.LoginVo;
import com.lovetotravel.user.service.EmailService;
import com.lovetotravel.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping
public class LoginController {

    final UserService userService;
    final EmailService emailService;

    public LoginController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public Result<String> sendMimeMail(@RequestBody LoginVo loginVo) {
        emailService.sendEmail(loginVo);
        return Result.success("已发送");
    }

    @PostMapping("/codelogin")
    public Result<String> codeLogin(HttpServletResponse response, @RequestBody LoginVo loginVo) {
        return Result.success(userService.codeLogin(response, loginVo));
    }

    @PostMapping("/login")
    public Result<String> passLogin(HttpServletResponse response, @RequestBody LoginVo loginVo) {
        return Result.success(userService.passLogin(response, loginVo));
    }

}
