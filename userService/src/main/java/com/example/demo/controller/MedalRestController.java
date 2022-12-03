package com.example.demo.controller;

import com.example.demo.entity.Login;
import com.example.demo.entity.Medal;
import com.example.demo.service.MedalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "勋章信息管理接口")
@CrossOrigin
@RestController
@RequestMapping("userservice")
public class MedalRestController {
    @Autowired
    private MedalService medalService;

    @ApiOperation("查找所有勋章信息")
    @GetMapping("/medal")
    public List<Medal> getAllMedal() {
        return medalService.findMedalAll();
    }

    @ApiOperation("根据ID查找勋章信息")
    @GetMapping("/medal/{id}")
    public Medal getMedalBYID(@PathVariable Integer id) {
        return medalService.findMedalByID(id);
    }

    @ApiOperation("添加勋章信息")
    @PostMapping("medal")
    public Integer addStudentInfo(Medal medal) {
        System.out.println(medal);
        return medalService.insertMedal(medal);
    }

    @ApiOperation("修改勋章信息")
    @PutMapping("/medal")
    public Integer updateMedal(Medal medal) {
        System.out.println(medal);
        return medalService.updateMedal(medal);
    }

    @ApiOperation("根据ID删除登录信息")
    @DeleteMapping("/medal/{id}")
    public Integer deleteMedal(@PathVariable("id") Integer id) {
        return medalService.deleteMedal(id);
    }

}
