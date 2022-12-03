package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户信息管理接口")
@CrossOrigin
@RestController
@RequestMapping("userservice")
public class UserRestContrller {
    @Autowired
    private UserService userService;

    @ApiOperation("查找所有用户信息")
    @GetMapping("/user")
    public List<User> getAllMedal() {
        return userService.findUserAll();
    }

    @ApiOperation("根据ID查找用户信息")
    @GetMapping("/user/{id}")
    public User getMedalBYID(@PathVariable Integer id) {
        return userService.findUserByID(id);
    }

    @ApiOperation("添加用户信息")
    @PostMapping("user")
    public Integer addStudentInfo(User user) {
        System.out.println(user);
        return userService.insertUser(user);
    }

    @ApiOperation("修改用户信息")
    @PutMapping("/user")
    public Integer updateMedal(User user) {
        System.out.println(user);
        return userService.updateUser(user);
    }

    @ApiOperation("根据ID删除用户信息")
    @DeleteMapping("/uesr/{id}")
    public Integer deleteUser(@PathVariable("id") Integer id) {
        return userService.deleteUser(id);
    }
}
