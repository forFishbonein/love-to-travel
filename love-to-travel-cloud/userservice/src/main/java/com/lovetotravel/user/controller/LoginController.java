package com.lovetotravel.user.controller;


import com.lovetotravel.user.common.result.Result;
import com.lovetotravel.user.entity.vo.LoginVo;
import com.lovetotravel.user.service.LoginService;
import com.lovetotravel.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
public class LoginController {

    final LoginService loginService;
    final UserService userService;

    public LoginController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @PostMapping("/codeLogin")
    public Result<String> codeLogin(HttpServletResponse response, @RequestBody LoginVo loginVo) {
        loginService.checkCode(loginVo);
        userService.login(response, loginVo);
        return Result.success("登录");
    }

    @PostMapping("/login")
    public Result<String> passLogin(HttpServletResponse response, @RequestBody LoginVo loginVo) {
        loginService.checkPass(loginVo);
        userService.login(response, loginVo);
        return Result.success("登录");
    }

}
