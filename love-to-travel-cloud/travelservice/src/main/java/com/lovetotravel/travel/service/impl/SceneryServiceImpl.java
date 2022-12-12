package com.lovetotravel.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lovetotravel.travel.entity.PageVo;
import com.lovetotravel.travel.entity.Scenery;
import com.lovetotravel.travel.entity.vo.scenery.SceneryInsertVo;
import com.lovetotravel.travel.entity.vo.scenery.SceneryUpdateVo;
import com.lovetotravel.travel.exception.GlobalException;
import com.lovetotravel.travel.mapper.SceneryMapper;
import com.lovetotravel.travel.result.CodeMsg;
import com.lovetotravel.travel.service.SceneryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public Page<Scenery> getPage(PageVo pageVo) {
        Page<Scenery> page = Page.of(pageVo.getCurrent(), pageVo.getSize());
        sceneryMapper.selectPage(page, null);
        return page;
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

    @Override
    public void insert(SceneryInsertVo sceneryInsertVo) {
        if (sceneryInsertVo == null) {
            throw new GlobalException(CodeMsg.SCENERY_NOT_EXIST);
        }
        Scenery scenery = new Scenery();
        BeanUtils.copyProperties(sceneryInsertVo,scenery);
        sceneryMapper.insert(scenery);
    }

    @Override
    public void update(SceneryUpdateVo sceneryUpdateVo) {
        if (sceneryUpdateVo == null) {
            throw new GlobalException(CodeMsg.SCENERY_NOT_EXIST);
        }
        QueryWrapper<Scenery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Scenery::getId, sceneryUpdateVo.getId());
        Scenery SceneryInMysql = sceneryMapper.selectOne(queryWrapper);
        if (SceneryInMysql == null) {
            throw new GlobalException(CodeMsg.SCENERY_NOT_EXIST);
        }
        Scenery scenery = new Scenery();
        BeanUtils.copyProperties(sceneryUpdateVo,scenery);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        scenery.setUpdateTime(currentTimeStamp);
        sceneryMapper.update(scenery, queryWrapper);
    }

    @Override
    public void removeById(Long id) {
        if (id == null) {
            throw new GlobalException(CodeMsg.SCENERY_NOT_EXIST);
        }
        QueryWrapper<Scenery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Scenery::getId, id);
        Scenery SceneryInMysql = sceneryMapper.selectOne(queryWrapper);
        if (SceneryInMysql == null) {
            throw new GlobalException(CodeMsg.SCENERY_NOT_EXIST);
        }
        Scenery scenery = new Scenery();
        scenery.setDeleted("0");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        scenery.setUpdateTime(currentTimeStamp);
        sceneryMapper.update(scenery, queryWrapper);
    }

}
