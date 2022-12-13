package com.lovetotravel.travel.controller;

import com.lovetotravel.travel.entity.Footpoint;
import com.lovetotravel.travel.entity.vo.foot.BeenRemoveVo;
import com.lovetotravel.travel.entity.vo.foot.BeenVo;
import com.lovetotravel.travel.entity.vo.foot.WantRemoveVo;
import com.lovetotravel.travel.entity.vo.foot.WantVo;
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
    public Result<Footpoint> getByUserId(@PathVariable("id") String id) {
        System.out.println(id);
        return Result.success(footpointService.getByUserId(id));
    }

    @ApiOperation("新增想去")
    @PostMapping("/want")
    public Result<String> insertWant(@RequestBody WantVo wantVo) {
        System.out.println(wantVo);
        footpointService.insertWants(wantVo);
        return Result.success("添加成功");
    }

    @ApiOperation("新增去过")
    @PostMapping("/been")
    public Result<String> insertBeen(@RequestBody BeenVo beenVo) {
        System.out.println(beenVo);
        footpointService.insertBeens(beenVo);
        return Result.success("添加成功");
    }

    @ApiOperation("删除想去")
    @DeleteMapping("/want")
    public Result<String> removeWant(@RequestBody WantRemoveVo wantRemoveVo) {
        System.out.println(wantRemoveVo);
        footpointService.removeWants(wantRemoveVo);
        return Result.success("删除成功");
    }


    @ApiOperation("删除去过")
    @DeleteMapping("/been")
    public Result<String> removeBeen(@RequestBody BeenRemoveVo beenRemoveVo) {
        System.out.println(beenRemoveVo);
        footpointService.removeBeens(beenRemoveVo);
        return Result.success("删除成功");
    }






//    @ApiOperation("想去/去过 新增修改")
//    @PostMapping
//    public Result<String> update(@RequestBody Footpoint footpoint) {
//        footpointService.update(footpoint);
//        return Result.success("修改成功");
//    }
//
//    @ApiOperation("想去/去过 删除")
//    @DeleteMapping
//    public Result<String> remove(@RequestBody Footpoint footpoint) {
//        footpointService.remove(footpoint);
//        return Result.success("修改成功");
//    }


}
