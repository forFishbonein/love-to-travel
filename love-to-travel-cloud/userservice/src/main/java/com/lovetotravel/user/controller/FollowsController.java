package com.lovetotravel.user.controller;

import com.lovetotravel.user.common.result.Result;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.FollowerVo;
import com.lovetotravel.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowsController {

    final UserService userService;


    public FollowsController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public Result<String> addFollower(@RequestBody FollowerVo followerVo) {
        userService.addFollower(followerVo);
        return Result.success("关注成功");
    }

    @PostMapping("/remove")
    public Result<String> removeFollower(@RequestBody FollowerVo followerVo) {
        userService.removeFollower(followerVo);
        return Result.success("取消关注成功");
    }

    @PostMapping("/sumfollower")
    public Result<Long> sumFollower(@RequestBody FollowerVo followerVo) {
        return Result.success(userService.sumFollower(followerVo.getId()));
    }

    @PostMapping("/sumfollowee")
    public Result<Long> sumFollowee(@RequestBody FollowerVo followerVo) {
        return Result.success(userService.sumFollowee(followerVo.getId()));
    }

    @PostMapping("/getallfollower")
    public Result<List<User>> getAllFollower(@RequestBody FollowerVo followerVo) {
        userService.getAllFollower(followerVo.getId());
        return Result.success(userService.getAllFollower(followerVo.getId()));
    }

    @PostMapping("/getallfollowee")
    public Result<List<User>> getAllFollowee(@RequestBody FollowerVo followerVo) {
        userService.getAllFollowee(followerVo.getId());
        return Result.success(userService.getAllFollowee(followerVo.getId()));
    }


}
