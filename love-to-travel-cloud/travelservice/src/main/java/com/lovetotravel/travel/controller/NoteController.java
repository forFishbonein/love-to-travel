package com.lovetotravel.travel.controller;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.page.QueryPageVo;
import com.lovetotravel.travel.entity.vo.note.NoteLike;
import com.lovetotravel.travel.entity.vo.note.NoteStar;
import com.lovetotravel.travel.entity.vo.note.NoteVo;
import com.lovetotravel.travel.result.Result;
import com.lovetotravel.travel.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "游记接口")
@RestController
@RequestMapping("/note")
public class NoteController {

    final NoteService noteService;


    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @ApiOperation("根据游记id查询游记")
    @GetMapping("/{id}")
    public Result<Note> getById(HttpServletRequest request, @PathVariable("id") String id) {
        System.out.println(id);
        return Result.success(noteService.getById(id));
    }

    @ApiOperation("根据用户id查询游记")
    @GetMapping("/user/{id}")
    public Result<List<Note>> getByUserId(@PathVariable("id") String id) {
        System.out.println(id);
        return Result.success(noteService.getByUserId(id));
    }

    @ApiOperation("查询所有游记")
    @GetMapping
    public Result<List<Note>> getAll() {
        return Result.success(noteService.getAll());
    }

    @ApiOperation("游记分页")
    @PostMapping("/page")
    public Result<PageVo<Note>> getPage(@RequestBody PageVo pageVo) {
        return Result.success(noteService.getPage(pageVo));
    }

    @ApiOperation("游记模糊查询")
    @PostMapping("/query")
    public Result<QueryPageVo<Note>> getByStr(@RequestBody QueryPageVo pageVo) {
        return Result.success(noteService.fuzzyQuery(pageVo));
    }

    @ApiOperation("新增游记")
    @PostMapping
    public Result<String> insert(@RequestBody NoteVo noteVo) {
        noteService.insert(noteVo);
        return Result.success("新增成功");
    }

    @ApiOperation("修改游记")
    @PutMapping
    public Result<String> update(@RequestBody NoteVo noteVo) {
        noteService.update(noteVo);
        return Result.success("修改成功");
    }

    @ApiOperation("删除游记")
    @DeleteMapping("/{id}")
    public Result<String> removeById(@PathVariable("id") String id) {
        noteService.removeById(id);
        return Result.success("删除成功");
    }

    @ApiOperation("批量删除游记")
    @DeleteMapping("/list")
    public Result<String> removeList(@RequestBody String[] ids) {
        noteService.removeList(ids);
        return Result.success("删除成功");
    }

    @ApiOperation("点赞游记")
    @PostMapping("/like")
    public Result<String> like(@RequestBody NoteLike noteLike) {
        noteService.like(noteLike);
        return Result.success("点赞成功");
    }

    @ApiOperation("取消点赞")
    @PostMapping("/unlike")
    public Result<String> unLike(@RequestBody NoteLike noteLike) {
        noteService.unLike(noteLike);
        return Result.success("取消点赞成功");
    }

    @ApiOperation("是否点赞")
    @PostMapping("/islike")
    public Result<Boolean> isLike(@RequestBody NoteLike noteLike) {
        return Result.success(noteService.isLike(noteLike));
    }

    @ApiOperation("收藏游记")
    @PostMapping("/star")
    public Result<String> star(@RequestBody NoteStar noteStar) {
        noteService.star(noteStar);
        return Result.success("收藏成功");
    }

    @ApiOperation("取消收藏")
    @PostMapping("/unstar")
    public Result<String> unStar(@RequestBody NoteStar noteStar) {
        noteService.unStar(noteStar);
        return Result.success("取消收藏成功");
    }

    @ApiOperation("是否收藏")
    @PostMapping("/isstar")
    public Result<Boolean> isStar(@RequestBody NoteStar noteStar) {
        return Result.success(noteService.isStar(noteStar));
    }

    @ApiOperation("根据用户id查看收藏")
    @GetMapping("/star")
    public Result<List<Note>> getStarByUserId(@PathVariable("id") Long id) {
        return Result.success(noteService.getStarByUserId(id));
    }



}
