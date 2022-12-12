package com.lovetotravel.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lovetotravel.travel.entity.City;
import com.lovetotravel.travel.entity.PageVo;
import com.lovetotravel.travel.entity.Province;
import com.lovetotravel.travel.entity.Scenery;
import com.lovetotravel.travel.mapper.CityMapper;
import com.lovetotravel.travel.mapper.ProvinceMapper;
import com.lovetotravel.travel.result.Result;
import com.lovetotravel.travel.service.SceneryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "景点接口")
@RestController
@RequestMapping
public class SceneryController {

    final CityMapper cityMapper;
    final ProvinceMapper provinceMapper;
    final SceneryService sceneryService;

    public SceneryController(CityMapper cityMapper, ProvinceMapper provinceMapper, SceneryService sceneryService) {
        this.cityMapper = cityMapper;
        this.provinceMapper = provinceMapper;
        this.sceneryService = sceneryService;
    }


    @ApiOperation("根据城市id查询城市")
    @GetMapping("/city/{id}")
    public Result<City> getByCityId(@PathVariable("id") Long id) {
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(City::getCityId, id);
        return Result.success(cityMapper.selectOne(queryWrapper));
    }

    @ApiOperation("根据景区id查询景区")
    @GetMapping("/scenery/{id}")
    public Result<Scenery> getById(HttpServletRequest request, @PathVariable("id") Long id) {
        System.out.println(id);
        return Result.success(sceneryService.getById(id));
    }

    @ApiOperation("根据城市id查询景区")
    @GetMapping("/scenery/city/{id}")
    public Result<List<Scenery>> getByCityId(@PathVariable("id") String id) {
        System.out.println(id);
        return Result.success(sceneryService.getByCityId(id));
    }

    @ApiOperation("获取热门城市")
    @GetMapping("/city/hot")
    public Result<List<City>> getHotCity() {
        return Result.success(cityMapper.getHotCity());
    }

    @ApiOperation("获取热门景区")
    @GetMapping("/scenery/hot")
    public Result<List<Scenery>> getHotScenery() {
        // TODO*******************
        QueryWrapper<Scenery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Scenery::getCityId, "310000");
        return Result.success(sceneryService.getAll());
    }

    @ApiOperation("获取所有省份")
    @GetMapping("/province")
    public Result<List<Province>> getAllProvince() {
        return Result.success(provinceMapper.selectList(null));
    }

    @ApiOperation("获取所有城市")
    @GetMapping("/city")
    public Result<List<City>> getAllCity() {
        return Result.success(cityMapper.selectList(null));
    }

    @ApiOperation("获取所有景区")
    @GetMapping("/scenery")
    public Result<List<Scenery>> getAllScenery() {
        return Result.success(sceneryService.getAll());
    }

    @ApiOperation("获取所有省份")
    @PostMapping("/province/page")
    public Result<List<Province>> getPageProvince(@RequestBody PageVo pageVo) {
        Page<Province> page = Page.of(pageVo.getCurrent(), pageVo.getSize());
        provinceMapper.selectPage(page, null);
        return Result.success(page.getRecords());
    }

    @ApiOperation("获取所有城市")
    @PostMapping("/city/page")
    public Result<List<City>> getPageCity(@RequestBody PageVo pageVo) {
        Page<City> page = Page.of(pageVo.getCurrent(), pageVo.getSize());
        cityMapper.selectPage(page, null);
        return Result.success(page.getRecords());
    }

    @ApiOperation("获取所有景区")
    @PostMapping("/scenery/page")
    public Result<List<Scenery>> getPageScenery(@RequestBody PageVo pageVo) {
        return Result.success(sceneryService.getPage(pageVo));
    }


}
