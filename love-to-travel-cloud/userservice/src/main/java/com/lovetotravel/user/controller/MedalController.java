package com.lovetotravel.user.controller;

import com.lovetotravel.user.entity.Medal;
import com.lovetotravel.user.entity.vo.MedalVo;
import com.lovetotravel.user.result.Result;
import com.lovetotravel.user.service.MedalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户信息管理接口")
@RestController
@RequestMapping("/medal")
public class MedalController {


    final MedalService medalService;

    public MedalController(MedalService medalService) {
        this.medalService = medalService;
    }


    @ApiOperation("申请勋章")
    @PostMapping("/apply")
    public Result<String> applyMedal(@RequestBody MedalVo medalVo) {
        medalService.applyMedal(medalVo);
        return Result.success("申请成功");
    }

    @ApiOperation("新增勋章")
    @PostMapping
    public Result<String> insert(@RequestBody Medal medal) {
        medalService.insert(medal);
        return Result.success("新增成功");
    }

    @ApiOperation("修改勋章")
    @PutMapping
    public Result<String> update(@RequestBody Medal medal) {
        medalService.update(medal);
        return Result.success("新增成功");
    }

    @ApiOperation("根据id删除勋章")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable("id") Integer id) {
        medalService.removeById(id);
        return Result.success("删除成功");
    }



}
