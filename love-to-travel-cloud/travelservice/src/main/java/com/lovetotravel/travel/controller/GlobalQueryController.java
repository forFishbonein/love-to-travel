package com.lovetotravel.travel.controller;

import com.lovetotravel.travel.entity.Comment;
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

        //游记





        //城市



        //景区







        return null;
    }


}
