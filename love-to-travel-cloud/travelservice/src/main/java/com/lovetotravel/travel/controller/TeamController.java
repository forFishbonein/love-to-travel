package com.lovetotravel.travel.controller;

import com.lovetotravel.travel.entity.Team;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.vo.team.*;
import com.lovetotravel.travel.result.Result;
import com.lovetotravel.travel.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "组队接口")
@RestController
@RequestMapping("/team")
public class TeamController {

    final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @ApiOperation("根据队伍id查询队伍信息")
    @GetMapping("/{id}")
    public Result<Team> getById(@PathVariable("id") String id) {
        System.out.println(id);
        return Result.success(teamService.getById(id));
    }

    @ApiOperation("根据id查询创建的队伍")
    @GetMapping("/user/create/{id}")
    public Result<List<Team>> getCreatedById(@PathVariable("id") String id) {
        System.out.println(id);
        return Result.success(teamService.getCreatedById(id));
    }

    @ApiOperation("根据id查询加入的队伍")
    @GetMapping("/user/join/{id}")
    public Result<List<Team>> getJoinedById(@PathVariable("id") String id) {
        System.out.println(id);
        return Result.success(teamService.getJoinedById(id));
    }

    @ApiOperation("查询所有队伍")
    @GetMapping
    public Result<List<Team>> getAll() {
        return Result.success(teamService.getAll());
    }

    @ApiOperation("所有队伍分页")
    @GetMapping
    public Result<PageVo<Team>> getPage(@RequestBody PageVo pageVo) {
        return Result.success(teamService.getPage(pageVo));
    }

    @ApiOperation("创建队伍")
    @PostMapping
    public Result<String> insert(@RequestBody TeamCreateVo teamCreateVo) {
        teamService.insert(teamCreateVo);
        return Result.success("新增成功");
    }

    @ApiOperation("修改队伍信息")
    @PutMapping
    public Result<String> update(@RequestBody TeamUpdateVo teamUpdateVo) {
        teamService.update(teamUpdateVo);
        return Result.success("修改成功");
    }

    @ApiOperation("解散队伍")
    @DeleteMapping("/{id}")
    public Result<String> removeById(@PathVariable("id") String id) {
        teamService.removeById(id);
        return Result.success("删除成功");
    }

    @ApiOperation("发送邀请邮件")
    @PostMapping("/invite/{email}")
    public Result<String> invite(TeamInviteVo teamInviteVo) {
        teamService.invite(teamInviteVo);
        return Result.success("邀请成功");
    }

    @ApiOperation("移出成员")
    @DeleteMapping("kick")
    public Result<String> kick(@RequestBody TeamVo teamVo) {
        teamService.kick(teamVo);
        return Result.success("移出成员成功");
    }

    @ApiOperation("加入队伍")
    @PostMapping("/join")
    public Result<String> join(@RequestBody TeamJoinVo teamJoinVo) {
        teamService.join(teamJoinVo);
        return Result.success("加入成功");
    }

    @ApiOperation("是否加入队伍")
    @PostMapping("/isjoin")
    public Result<Boolean> isJoin(@RequestBody TeamVo teamVo) {
        return Result.success(teamService.isJoin(teamVo));
    }

    @ApiOperation("退出队伍")
    @PostMapping("/leave")
    public Result<String> leave(@RequestBody TeamLeaveVo teamLeaveVo) {
        teamService.leave(teamLeaveVo);
        return Result.success("退出成功");
    }

}
