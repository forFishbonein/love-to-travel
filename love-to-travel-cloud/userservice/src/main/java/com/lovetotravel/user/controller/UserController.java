package com.lovetotravel.user.controller;

import com.lovetotravel.user.common.result.Result;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.RegisterVo;
import com.lovetotravel.user.service.LoginService;
import com.lovetotravel.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    final UserService userService;
    final LoginService loginService;

    public UserController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PutMapping
    public Result<String> register(@RequestBody RegisterVo registerVo) {
        userService.insert(registerVo);
        User user = userService.getByEmail(registerVo.getEmail());
        registerVo.setId(user.getId());
        loginService.insert(registerVo);
        return Result.success("注册成功");
    }
}
