package com.example.demo.controller;

import com.example.demo.entity.Login;
import com.example.demo.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "登录信息管理接口")
@CrossOrigin
@RestController
@RequestMapping("userservice")
public class LoginRestController {
    @Autowired
    private LoginService loginService;

    @ApiOperation("查找所有登录信息")
    //http://localhost:8080/userservice/login get请求
    @GetMapping("/login")
    public List<Login> getAllStudent() {
        return loginService.findLoginAll();
    }

    @ApiOperation("根据ID查找登录信息")
    // http://localhost:8080/userservice/login get
    @GetMapping("/login/{id}")
    public Login getLoginBYID(@PathVariable Integer id) {
        return loginService.findLoginByID(id);
    }

    @ApiOperation("添加登录信息")
    @PostMapping("login")
    public Integer addStudentInfo(Login login) {
        System.out.println(login);
        return loginService.insertLogin(login);
    }

    @ApiOperation("修改登录信息")
    @PutMapping("/login")
    public Integer updateLogin(Login login) {
        System.out.println(login);
        return loginService.updateLogin(login);
    }

    @ApiOperation("根据ID删除登录信息")
    @DeleteMapping("/login/{id}")
    public Integer deleteLogin(@PathVariable("id") Integer id) {
        return loginService.deleteLogin(id);
    }
}
