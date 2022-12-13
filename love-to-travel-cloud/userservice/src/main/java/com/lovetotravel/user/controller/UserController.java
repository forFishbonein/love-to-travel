package com.lovetotravel.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lovetotravel.user.entity.PageVo;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.RegisterVo;
import com.lovetotravel.user.entity.vo.UpdatePasswordVo;
import com.lovetotravel.user.result.Result;
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
    public Result<String> register(HttpServletResponse response, @RequestBody RegisterVo registerVo) {
        return Result.success(userService.insert(response, registerVo));
    }

    @ApiOperation("根据id查询用户")
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable("id") Long id) {
        return Result.success(userService.getById(id));
    }

    @ApiOperation("根据邮箱查询用户")
    @GetMapping("/email/{email}")
    public Result<User> getByEmail(@PathVariable("email") String email) {
        return Result.success(userService.getByEmail(email));
    }

    @ApiOperation("查询所有用户")
    @GetMapping
    public Result<List<User>> getAll() {
        return Result.success(userService.getAll());
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public Result<Page<User>> getPage(@RequestBody PageVo pageVo) {
        System.out.println(pageVo);
        return Result.success(userService.getPage(pageVo));
    }

    @ApiOperation("根据token获取用户信息")
    @GetMapping("/token")
    public Result<User> getByToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        return Result.success(userService.getByToken(response, token));
    }

    @ApiOperation("更改密码")
    @PutMapping("/updatepassword")
    public Result<String> updatePassword(@RequestBody UpdatePasswordVo updatePasswordVo) {
        userService.updatePassword(updatePasswordVo);
        return Result.success("修改成功");
    }

    @ApiOperation("更改个人信息")
    @PutMapping("/updateprofile")
    public Result<String> updatePassword(@RequestBody User user) {
        userService.updateProfile(user);
        return Result.success("修改成功");
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public Result<String> removeById(@PathVariable("id") Long id) {
        userService.removeById(id);
        return Result.success("删除成功");
    }


}
