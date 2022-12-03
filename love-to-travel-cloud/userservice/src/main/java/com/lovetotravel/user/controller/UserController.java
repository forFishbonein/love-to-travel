package com.lovetotravel.user.controller;

import com.lovetotravel.user.common.result.Result;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.RegisterVo;
import com.lovetotravel.user.entity.vo.UpdatePasswordVo;
import com.lovetotravel.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

@Api(tags = "用户信息管理接口")
@RestController
@RequestMapping("/user")
public class UserController {

    final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterVo registerVo) {
        userService.insert(registerVo);
        return Result.success("注册成功");
    }

    @ApiOperation("根据id查询用户")
    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @ApiOperation("查询所有用户")
    @GetMapping
    public Result<List<User>> getAll() {
        return Result.success(userService.getAll());
    }

    @ApiOperation("根据token获取用户")
    @GetMapping("/token")
    public Result<User> getByToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        return Result.success(userService.getByToken(response, token));
    }

    @ApiOperation("更改密码")
    @PostMapping("/updatepassword")
    public Result<String> updatePassword(@RequestBody UpdatePasswordVo updatePasswordVo) {
        userService.updatePassword(updatePasswordVo);
        return Result.success("修改成功");
    }

    @ApiOperation("更改个人信息")
    @PostMapping("/updateprofile")
    public Result<String> updatePassword(@RequestBody User user) {
        userService.updateProfile(user);
        return Result.success("修改成功");
    }


}
