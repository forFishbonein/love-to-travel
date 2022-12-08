package com.lovetotravel.travel.controller;

import com.lovetotravel.travel.entity.City;
import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.Province;
import com.lovetotravel.travel.entity.Scenery;
import com.lovetotravel.travel.mapper.CityMapper;
import com.lovetotravel.travel.mapper.ProvinceMapper;
import com.lovetotravel.travel.result.Result;
import com.lovetotravel.travel.service.SceneryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "景点接口")
@RestController
@RequestMapping("/scenery")
public class SceneryController {

    final CityMapper cityMapper;
    final ProvinceMapper provinceMapper;
    final SceneryService sceneryService;

    public SceneryController(CityMapper cityMapper, ProvinceMapper provinceMapper, SceneryService sceneryService) {
        this.cityMapper = cityMapper;
        this.provinceMapper = provinceMapper;
        this.sceneryService = sceneryService;
    }

    @ApiOperation("获取所有城市")
    @GetMapping("/city")
    public Result<List<City>> getAllCity() {
        return Result.success(cityMapper.selectList(null));
    }

    @ApiOperation("获取所有省份")
    @GetMapping("/province")
    public Result<List<Province>> getAllProvince() {
        return Result.success(provinceMapper.selectList(null));
    }

    @ApiOperation("获取所有景区")
    @GetMapping
    public Result<List<Scenery>> getAll() {
        return Result.success(sceneryService.getAll());
    }

    @ApiOperation("根据景区id查询景区")
    @GetMapping("/{id}")
    public Result<Scenery> getById(@PathVariable("id") String id) {
        System.out.println(id);
        return Result.success(sceneryService.getById(id));
    }






}
