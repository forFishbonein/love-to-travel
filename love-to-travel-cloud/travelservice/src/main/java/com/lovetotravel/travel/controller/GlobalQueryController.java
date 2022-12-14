package com.lovetotravel.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lovetotravel.travel.entity.City;
import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.Scenery;
import com.lovetotravel.travel.entity.page.QueryPageVo;
import com.lovetotravel.travel.entity.vo.GlobalQuery;
import com.lovetotravel.travel.mapper.CityMapper;
import com.lovetotravel.travel.mapper.ProvinceMapper;
import com.lovetotravel.travel.result.Result;
import com.lovetotravel.travel.service.NoteService;
import com.lovetotravel.travel.service.SceneryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "全局查询")
@RestController
@RequestMapping("/query")
public class GlobalQueryController {

    final NoteService noteService;
    final ProvinceMapper provinceMapper;
    final CityMapper cityMapper;
    final SceneryService sceneryService;

    public GlobalQueryController(NoteService noteService, ProvinceMapper provinceMapper, CityMapper cityMapper, SceneryService sceneryService) {
        this.noteService = noteService;
        this.provinceMapper = provinceMapper;
        this.cityMapper = cityMapper;
        this.sceneryService = sceneryService;
    }

    @ApiOperation("全局查询")
    @PostMapping
    public Result<GlobalQuery> doQuery(String queryStr) {
        GlobalQuery globalQuery = new GlobalQuery();
        QueryPageVo pageVo = new QueryPageVo();
        pageVo.setQueryStr(queryStr);
        pageVo.setPageNum(1);
        pageVo.setPageSize(5);
        //游记
        QueryPageVo<Note> noteQueryPageVo = noteService.fuzzyQuery(pageVo);
        List<Note> noteList = noteQueryPageVo.getRecords();
        //城市
        Page<City> page = new Page<>(pageVo.getPageNum(), pageVo.getPageSize());
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(City::getIntroduction, pageVo.getQueryStr());
        Page<City> cityPage = cityMapper.selectPage(page, queryWrapper);
        List<City> cityList = cityPage.getRecords();
        //景区
        Page<Scenery> sceneryByStr = sceneryService.getSceneryByStr(pageVo);
        List<Scenery> sceneryList = sceneryByStr.getRecords();

        globalQuery.setNoteList(noteList);
        globalQuery.setCityList(cityList);
        globalQuery.setSceneryList(sceneryList);
        return Result.success(globalQuery);
    }
}
