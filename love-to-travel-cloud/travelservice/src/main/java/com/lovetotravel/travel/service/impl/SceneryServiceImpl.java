package com.lovetotravel.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lovetotravel.travel.entity.PageVo;
import com.lovetotravel.travel.entity.Scenery;
import com.lovetotravel.travel.mapper.SceneryMapper;
import com.lovetotravel.travel.service.SceneryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneryServiceImpl extends ServiceImpl<SceneryMapper, Scenery> implements SceneryService {


    final SceneryMapper sceneryMapper;

    public SceneryServiceImpl(SceneryMapper sceneryMapper) {
        this.sceneryMapper = sceneryMapper;
    }

    @Override
    public List<Scenery> getAll() {
        QueryWrapper<Scenery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Scenery::getDeleted, "0");
        return sceneryMapper.selectList(queryWrapper);
    }

    @Override
    public List<Scenery> getPage(PageVo pageVo) {
        Page<Scenery> page = Page.of(pageVo.getCurrent(), pageVo.getSize());
        sceneryMapper.selectPage(page, null);
        return page.getRecords();
    }

    public Scenery getById(Long id) {
        return sceneryMapper.getById(id);
    }

    @Override
    public List<Scenery> getByCityId(String id) {
        QueryWrapper<Scenery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Scenery::getDeleted, "0").eq(Scenery::getCityId, id);
        return sceneryMapper.selectList(queryWrapper);
    }
}
