package com.lovetotravel.travel.controller;

import com.lovetotravel.travel.entity.Comment;
import com.lovetotravel.travel.entity.vo.CommentVo;
import com.lovetotravel.travel.result.Result;
import com.lovetotravel.travel.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "评论接口")
@RestController
@RequestMapping("/comment")
public class CommentController {

    final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @ApiOperation("根据游记id查询评论")
    @GetMapping("/{id}")
    public Result<List<Comment>> getById(@PathVariable("id") String id) {
        System.out.println(id);
        return Result.success(commentService.getById(id));
    }

    @ApiOperation("根据游记id添加评论")
    @PostMapping
    public Result<String> insert(@RequestBody CommentVo commentVo) {
        commentService.insert(commentVo);
        return Result.success("评论成功");
    }

    @ApiOperation("根据评论id删除评论")
    @DeleteMapping("/{id}")
    public Result<String> removeById(@PathVariable("id") String id) {
        System.out.println(id);
        commentService.removeById(id);
        return Result.success("删除成功");
    }

    

}
