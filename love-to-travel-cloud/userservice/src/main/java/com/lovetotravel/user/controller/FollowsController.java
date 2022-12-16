package com.lovetotravel.user.controller;

import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.FollowerVo;
import com.lovetotravel.user.result.Result;
import com.lovetotravel.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "关注粉丝接口")
@RestController
@RequestMapping("/follow")
public class FollowsController {

    final UserService userService;


    public FollowsController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("关注用户")
    @PostMapping("/add")
    public Result<String> addFollower(@RequestBody FollowerVo followerVo) {
        userService.addFollower(followerVo);
        return Result.success("关注成功");
    }

    @ApiOperation("取消关注")
    @PostMapping("/remove")
    public Result<String> removeFollower(@RequestBody FollowerVo followerVo) {
        userService.removeFollower(followerVo);
        return Result.success("取消关注成功");
    }

    @ApiOperation("是否关注")
    @PostMapping("/isfollow")
    public Result<Boolean> isFollow(@RequestBody FollowerVo followerVo) {
        return Result.success(userService.isFollow(followerVo));
    }

    @ApiOperation("查看关注数量")
    @PostMapping("/sumfollower")
    public Result<Long> sumFollower(@RequestBody FollowerVo followerVo) {
        return Result.success(userService.sumFollower(followerVo.getId()));
    }

    @ApiOperation("查看粉丝数量")
    @PostMapping("/sumfollowee")
    public Result<Long> sumFollowee(@RequestBody FollowerVo followerVo) {
        return Result.success(userService.sumFollowee(followerVo.getId()));
    }

    @ApiOperation("获取所有关注信息")
    @PostMapping("/getallfollower")
    public Result<List<User>> getAllFollower(@RequestBody FollowerVo followerVo) {
        userService.getAllFollower(followerVo.getId());
        return Result.success(userService.getAllFollower(followerVo.getId()));
    }

    @ApiOperation("获取所有粉丝信息")
    @PostMapping("/getallfollowee")
    public Result<List<User>> getAllFollowee(@RequestBody FollowerVo followerVo) {
        userService.getAllFollowee(followerVo.getId());
        return Result.success(userService.getAllFollowee(followerVo.getId()));
    }


}
