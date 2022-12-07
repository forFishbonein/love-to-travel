package com.lovetotravel.travel.controller;

import com.lovetotravel.travel.entity.Footpoint;
import com.lovetotravel.travel.result.Result;
import com.lovetotravel.travel.service.FootpointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "足迹接口")
@RestController
@RequestMapping("/foot")
public class FootpointController {

    final FootpointService footpointService;

    public FootpointController(FootpointService footpointService) {
        this.footpointService = footpointService;
    }

    @ApiOperation("根据用户id查询足迹")
    @GetMapping("/user/{id}")
    public Result<List<Footpoint>> getByUserId(@PathVariable("id") String id) {
        System.out.println(id);
        return Result.success(footpointService.getByUserId(id));
    }

    @ApiOperation("想去/去过 新增修改")
    @PostMapping
    public Result<String> update(@RequestBody Footpoint footpoint) {
        footpointService.update(footpoint);
        return Result.success("修改成功");
    }

    @ApiOperation("想去/去过 删除")
    @PostMapping
    public Result<String> remove(@RequestBody Footpoint footpoint) {
        footpointService.remove(footpoint);
        return Result.success("修改成功");
    }


}
