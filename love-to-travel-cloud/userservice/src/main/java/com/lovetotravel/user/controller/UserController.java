package com.lovetotravel.user.controller;

import com.lovetotravel.user.common.result.Result;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.RegisterVo;
import com.lovetotravel.user.entity.vo.UpdatePasswordVo;
import com.lovetotravel.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterVo registerVo) {
        userService.insert(registerVo);
        return Result.success("注册成功");
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping
    public Result<List<User>> getAll() {
        return Result.success(userService.getAll());
    }

    @GetMapping("/token")
    public Result<User> getByToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        return Result.success(userService.getByToken(response, token));
    }

    @PostMapping("/updatepassword")
    public Result<String> updatePassword(@RequestBody UpdatePasswordVo updatePasswordVo) {
        userService.updatePassword(updatePasswordVo);
        return Result.success("修改成功");
    }

    @PostMapping("/updateprofile")
    public Result<String> updatePassword(@RequestBody User user) {
        userService.updateProfile(user);
        return Result.success("修改成功");
    }


}
