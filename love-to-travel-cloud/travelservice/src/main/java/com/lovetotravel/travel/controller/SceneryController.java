package com.lovetotravel.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lovetotravel.feign.entity.User;
import com.lovetotravel.travel.entity.*;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.page.QueryPageVo;
import com.lovetotravel.travel.entity.vo.scenery.*;
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

    @ApiOperation("根据城市名查询城市")
    @GetMapping("/city/name/{name}")
    public Result<List<City>> getByCityName(@PathVariable("name") String name) {
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(City::getCityName, name);
        return Result.success(cityMapper.selectList(queryWrapper));
    }

    @ApiOperation("根据景区id查询景区")
    @GetMapping("/scenery/{id}")
    public Result<Scenery> getById(HttpServletRequest request, @PathVariable("id") Long id) {
        System.out.println(id);
        String token = request.getHeader("Authorization");
        System.out.println("token = " + token);
        System.out.println("token = " + token);
        System.out.println("token = " + token);

        return Result.success(sceneryService.getById(id));
    }

    @ApiOperation("根据城市id查询景区")
    @GetMapping("/scenery/city/{id}")
    public Result<List<Scenery>> getSceneryByCityId(@PathVariable("id") String id) {
        System.out.println(id);
        return Result.success(sceneryService.getByCityId(id));
    }

    @ApiOperation("根据城市名查询景区")
    @GetMapping("/scenery/city/name/{name}")
    public Result<List<Scenery>> getSceneryByCityName(@PathVariable("name") String name) {
        System.out.println(name);
        return Result.success(sceneryService.getByCityName(name));
    }

    @ApiOperation("新增景区")
    @PostMapping("/scenery")
    public Result<String> insert(@RequestBody SceneryInsertVo sceneryInsertVo) {
        sceneryService.insert(sceneryInsertVo);
        return Result.success("新增成功");
    }

    @ApiOperation("修改景区")
    @PutMapping("/scenery")
    public Result<String> update(@RequestBody SceneryUpdateVo sceneryUpdateVo) {
        sceneryService.update(sceneryUpdateVo);
        return Result.success("修改成功");
    }

    @ApiOperation("根据id删除景区")
    @DeleteMapping("/scenery/{id}")
    public Result<String> remove(@PathVariable("id") Long id) {
        sceneryService.removeById(id);
        return Result.success("删除成功");
    }

    @ApiOperation("批量删除")
    @DeleteMapping("/list")
    public Result<String> removeList(@RequestBody Long[] ids) {
        sceneryService.removeList(ids);
        return Result.success("删除成功");
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

    @ApiOperation("省份分页")
    @PostMapping("/province/page")
    public Result<Page<Province>> getPageProvince(@RequestBody PageVo pageVo) {
        Page<Province> page = Page.of(pageVo.getPageNum(), pageVo.getPageSize());
        Page<Province> provincePage = provinceMapper.selectPage(page, null);
        return Result.success(provincePage);
    }

    @ApiOperation("城市分页")
    @PostMapping("/city/page")
    public Result<Page<City>> getPageCity(@RequestBody PageVo pageVo) {
        Page<City> page = Page.of(pageVo.getPageNum(), pageVo.getPageSize());
        Page<City> cityPage = cityMapper.selectPage(page, null);
        return Result.success(cityPage);
    }

    @ApiOperation("景区分页")
    @PostMapping("/scenery/page")
    public Result<Page<Scenery>> getPageScenery(@RequestBody PageVo pageVo) {
        return Result.success(sceneryService.getPage(pageVo));
    }

    @ApiOperation("城市分页+模糊查询")
    @PostMapping("/city/query")
    public Result<Page<City>> getCityByStr(@RequestBody QueryPageVo pageVo) {
        Page<City> page = new Page<>(pageVo.getPageNum(), pageVo.getPageSize());
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(City::getIntroduction, pageVo.getQueryStr());
        Page<City> cityPage = cityMapper.selectPage(page, queryWrapper);
        return Result.success(cityPage);
    }

    @ApiOperation("景区模糊查询")
    @PostMapping("/scenery/query")
    public Result<Page<Scenery>> getSceneryByStr(@RequestBody QueryPageVo pageVo) {
        return Result.success(sceneryService.getSceneryByStr(pageVo));
    }

    @ApiOperation("景区模糊查询2")
    @PostMapping("/scenery/query2")
    public Result<Page<Scenery>> getSceneryByStr2(@RequestBody QueryPageVo pageVo) {
        System.out.println(pageVo);
        return Result.success(sceneryService.getSceneryByStr2(pageVo));
    }

    @ApiOperation("景区评论")
    @PostMapping("/scenery/comment")
    public Result<String> comment(@RequestBody SceneryCommentVo sceneryCommentVo) {
        sceneryService.comment(sceneryCommentVo);
        return Result.success("评论成功");
    }

    @ApiOperation("根据景区id查询所有评论")
    @GetMapping("/scenery/comment/{id}")
    public Result<GetSceneryComment> getSceneryComment(@PathVariable("id") Long id) {
        return Result.success(sceneryService.getSceneryComment(id));
    }

    @ApiOperation("根据用户id查询所有评论")
    @GetMapping("/scenery/comment/user/{id}")
    public Result<GetUserComment> getUserComment(@PathVariable("id") Long id) {
        return Result.success(sceneryService.getUserComment(id));
    }

    @ApiOperation("景区列表页，根据用户推荐景区")
    @GetMapping("/scenery/recommond/user/{id}")
    public Result<List<Scenery>> getSceneryRedommond(@PathVariable("id") Long id) {
        return Result.success(sceneryService.getSceneryRedommond(id));
    }

}
