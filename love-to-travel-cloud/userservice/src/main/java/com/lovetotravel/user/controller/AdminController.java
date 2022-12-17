package com.lovetotravel.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lovetotravel.user.entity.PageVo;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.Admin;
import com.lovetotravel.user.exception.GlobalException;
import com.lovetotravel.user.mapper.AdminMapper;
import com.lovetotravel.user.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    final AdminMapper adminMapper;

    public AdminController(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @ApiOperation("根据id查询管理员")
    @GetMapping("/{id}")
    public Result<Admin> getById(@PathVariable("id") Long id) {
        return Result.success(adminMapper.selectById(id));
    }

    @ApiOperation("根据邮箱查询管理员")
    @GetMapping("/email/{email}")
    public Result<Admin> getByEmail(@PathVariable("email") String email) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Admin::getEmail, email);
        return Result.success(adminMapper.selectOne(queryWrapper));
    }

    @ApiOperation("检验密码")
    @PostMapping("/login")
    public Result<String> login(@RequestBody Admin admin) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Admin::getEmail, admin.getEmail()).eq(Admin::getPassword, admin.getPassword());
        Admin one = adminMapper.selectOne(queryWrapper);
        if (one == null) {
            return Result.success("登陆失败");
        }
        return Result.success("登陆成功");
    }

    @ApiOperation("新增")
    @PostMapping
    public Result<String> insert(@RequestBody Admin admin) {
        adminMapper.insert(admin);
        return Result.success("新增成功");
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public Result<String> remove(@PathVariable("id") Long id) {
        adminMapper.deleteById(id);
        return Result.success("删除成功");
    }

    @ApiOperation("查询所有管理员")
    @GetMapping
    public Result<List<Admin>> getAll() {
        return Result.success(adminMapper.selectList(null));
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public Result<Page<Admin>> getPage(@RequestBody PageVo pageVo) {
        System.out.println(pageVo);
        Page<Admin> page = Page.of(pageVo.getCurrent(),pageVo.getSize());
        page.addOrder(OrderItem.desc("create_time"));
        Page<Admin> adminPage = adminMapper.selectPage(page, null);
        return Result.success(adminPage);
    }


}
