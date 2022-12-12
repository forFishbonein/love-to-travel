package com.lovetotravel.travel.controller;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.Plan;
import com.lovetotravel.travel.entity.dto.SubPlan;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.vo.NoteVo;
import com.lovetotravel.travel.result.Result;
import com.lovetotravel.travel.service.NoteService;
import com.lovetotravel.travel.service.PlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "行程接口")
@RestController
@RequestMapping("/plan")
public class PlanController {

    final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }


    @ApiOperation("根据行程id查询行程")
    @GetMapping("/{id}")
    public Result<Note> getById(@PathVariable("id") String id) {
        return null;
    }

    @ApiOperation("根据用户id查询行程")
    @GetMapping("/user/{id}")
    public Result<List<Plan>> getByUserId(@PathVariable("id") String id) {
        return Result.success(planService.getByUserId(id));
    }

    @ApiOperation("获取全部行程")
    @GetMapping
    public Result<List<Plan>> getAll() {
        return Result.success(planService.getAll());
    }

    @ApiOperation("行程分页")
    @PostMapping("/page")
    public Result<PageVo<Plan>> getPage(@RequestBody PageVo pageVo) {
        return Result.success(planService.getPage(pageVo));
    }


    @ApiOperation("新增行程")
    @PostMapping
    public Result<String> insert(@RequestBody Plan plan) {
        planService.insert(plan);
        return Result.success("新增成功");
    }

    @ApiOperation("更新行程")
    @PutMapping
    public Result<String> update(@RequestBody Plan plan) {
        planService.update(plan);
        return Result.success("更新成功");
    }

    @ApiOperation("根据城市id查询行程")
    @GetMapping("/city/{id}")
    public Result<List<SubPlan>> getByCityId(@PathVariable("id") String id) {
        return Result.success(planService.getByCityId(id));
    }



}
