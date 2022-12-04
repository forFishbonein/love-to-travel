package com.lovetotravel.user.controller;

import com.lovetotravel.user.common.result.Result;
import com.lovetotravel.user.entity.Medal;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.MedalVo;
import com.lovetotravel.user.entity.vo.RegisterVo;
import com.lovetotravel.user.entity.vo.UpdatePasswordVo;
import com.lovetotravel.user.service.MedalService;
import com.lovetotravel.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "用户信息管理接口")
@RestController
@RequestMapping("/medal")
public class MedalController {


    final MedalService medalService;

    public MedalController(MedalService medalService) {
        this.medalService = medalService;
    }

    @ApiOperation("新增勋章")
    @PostMapping
    public Result<String> applyMedal(@RequestBody Medal medal) {
        medalService.insert(medal);
        return Result.success("新增成功");
    }

    @ApiOperation("申请勋章")
    @PostMapping("/apply")
    public Result<String> applyMedal(@RequestBody MedalVo medalVo) {
        medalService.applyMedal(medalVo);
        return Result.success("申请成功");
    }





}
