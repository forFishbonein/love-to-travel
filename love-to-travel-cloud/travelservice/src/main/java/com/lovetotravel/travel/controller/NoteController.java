package com.lovetotravel.travel.controller;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.vo.NoteVo;
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

//    @ApiOperation("点赞游记")
//    @PostMapping("/like")
//    public Result<String> like(@PathVariable("id") String id) {
//        noteService.removeById(id);
//        return Result.success("删除成功");
//    }

}
