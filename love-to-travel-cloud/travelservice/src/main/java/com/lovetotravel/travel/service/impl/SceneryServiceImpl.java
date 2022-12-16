package com.lovetotravel.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lovetotravel.feign.clients.UserClient;
import com.lovetotravel.feign.entity.Result;
import com.lovetotravel.feign.entity.User;
import com.lovetotravel.travel.entity.City;
import com.lovetotravel.travel.entity.Scenery;
import com.lovetotravel.travel.entity.SceneryComment;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.page.QueryPageVo;
import com.lovetotravel.travel.entity.vo.scenery.*;
import com.lovetotravel.travel.exception.GlobalException;
import com.lovetotravel.travel.mapper.SceneryCommentMapper;
import com.lovetotravel.travel.mapper.SceneryMapper;
import com.lovetotravel.travel.result.CodeMsg;
import com.lovetotravel.travel.service.SceneryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SceneryServiceImpl extends ServiceImpl<SceneryMapper, Scenery> implements SceneryService {


    final SceneryMapper sceneryMapper;
    final SceneryCommentMapper sceneryCommentMapper;
    final UserClient userClient;

    public SceneryServiceImpl(SceneryMapper sceneryMapper, SceneryCommentMapper sceneryCommentMapper, UserClient userClient) {
        this.sceneryMapper = sceneryMapper;
        this.sceneryCommentMapper = sceneryCommentMapper;
        this.userClient = userClient;
    }

    @Override
    public List<Scenery> getAll() {
        QueryWrapper<Scenery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Scenery::getDeleted, "0");
        return sceneryMapper.selectList(queryWrapper);
    }

    @Override
    public Page<Scenery> getPage(PageVo pageVo) {
        Page<Scenery> page = Page.of(pageVo.getPageNum(), pageVo.getPageSize());
        page.addOrder(OrderItem.desc("score"));
        Page<Scenery> sceneryPage = sceneryMapper.selectPage(page, null);
        return sceneryPage;
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
    public List<Scenery> getByCityName(String name) {
        QueryWrapper<Scenery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Scenery::getDeleted, "0").like(Scenery::getName, name);
        return sceneryMapper.selectList(queryWrapper);
    }

    @Override
    public void insert(SceneryInsertVo sceneryInsertVo) {
        if (sceneryInsertVo == null) {
            throw new GlobalException(CodeMsg.SCENERY_NOT_EXIST);
        }
        Scenery scenery = new Scenery();
        BeanUtils.copyProperties(sceneryInsertVo, scenery);
        sceneryMapper.insert(scenery);
    }

    @Override
    public void update(SceneryUpdateVo sceneryUpdateVo) {
        if (sceneryUpdateVo == null) {
            throw new GlobalException(CodeMsg.SCENERY_NOT_EXIST);
        }
        QueryWrapper<Scenery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Scenery::getId, sceneryUpdateVo.getId());
        Scenery sceneryInMysql = sceneryMapper.selectOne(queryWrapper);
        if (sceneryInMysql == null) {
            throw new GlobalException(CodeMsg.SCENERY_NOT_EXIST);
        }
        Scenery scenery = new Scenery();
        BeanUtils.copyProperties(sceneryUpdateVo, scenery);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        scenery.setUpdateTime(currentTimeStamp);
        sceneryMapper.update(scenery, queryWrapper);
    }

    @Override
    public Page<Scenery> getSceneryByStr(QueryPageVo pageVo) {
        Page<Scenery> page = new Page<>(pageVo.getPageNum(), pageVo.getPageSize());
        QueryWrapper<Scenery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(Scenery::getIntroduction, pageVo.getQueryStr());
        Page<Scenery> cityPage = sceneryMapper.selectPage(page, queryWrapper);
        return cityPage;
    }


    @Override
    public void removeById(Long id) {
//        if (id == null) {
//            throw new GlobalException(CodeMsg.SCENERY_NOT_EXIST);
//        }
//        QueryWrapper<Scenery> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(Scenery::getId, id);
//        Scenery SceneryInMysql = sceneryMapper.selectOne(queryWrapper);
//        if (SceneryInMysql == null) {
//            throw new GlobalException(CodeMsg.SCENERY_NOT_EXIST);
//        }
//        Scenery scenery = new Scenery();
//        BeanUtils.copyProperties(SceneryInMysql, scenery);
//
//        System.out.println("scenery = " + scenery);
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String currentTimeStamp = dateFormat.format(date);
//        scenery.setUpdateTime(currentTimeStamp);
//        scenery.setDeleted("1");
//        sceneryMapper.update(scenery, queryWrapper);
        sceneryMapper.deleteById(id);
    }

    @Override
    public void removeList(Long[] ids) {
        if (ids.length != 0) {
            sceneryMapper.deleteBatchIds(Arrays.asList(ids));
        }
    }

    @Override
    public void comment(SceneryCommentVo sceneryCommentVo) {
        Result<User> result = userClient.getById(Long.valueOf(sceneryCommentVo.getUserId()));
        User user = result.getData();
        if (user == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }

        SceneryComment sceneryComment = new SceneryComment();
        BeanUtils.copyProperties(sceneryCommentVo, sceneryComment);
        if (user.getName() == null || user.getName().equals("")) {
            user.setName("来自远方的驴友");
        }
        sceneryComment.setUserName(user.getName());
        sceneryCommentMapper.insert(sceneryComment);

        QueryWrapper<Scenery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Scenery::getId, sceneryCommentVo.getSceneryId());
        Scenery scenery = sceneryMapper.selectOne(queryWrapper);
        if (scenery == null) {
            throw new GlobalException(CodeMsg.SCENERY_NOT_EXIST);
        }
        QueryWrapper<SceneryComment> listQueryWrapper = new QueryWrapper<>();
        listQueryWrapper.lambda().eq(SceneryComment::getSceneryId, sceneryCommentVo.getSceneryId());
        List<SceneryComment> commentInMysql = sceneryCommentMapper.selectList(listQueryWrapper);
        if (commentInMysql.size() == 0) {
            scenery.setScore(sceneryCommentVo.getScore().toString());
            sceneryMapper.update(scenery, queryWrapper);
            return;
        }
        Double score = 0.00;
        for (SceneryComment s : commentInMysql) {
            score = s.getScore() + score;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        scenery.setScore(df.format(score / commentInMysql.size()));
        sceneryMapper.update(scenery, queryWrapper);
    }

    @Override
    public GetSceneryComment getSceneryComment(Long id) {

        QueryWrapper<SceneryComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SceneryComment::getSceneryId, id);
        List<SceneryComment> commentInMysql = sceneryCommentMapper.selectList(queryWrapper);
        Integer total = sceneryCommentMapper.selectCount(queryWrapper);
        GetSceneryComment getSceneryComment = new GetSceneryComment();
        getSceneryComment.setSceneryCommentList(commentInMysql);
        getSceneryComment.setTotal(total);
        Scenery scenery = sceneryMapper.getById(id);
        getSceneryComment.setAvgScore(Double.valueOf(scenery.getScore()));
        return getSceneryComment;
    }

    @Override
    public GetUserComment getUserComment(Long id) {
        QueryWrapper<SceneryComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SceneryComment::getUserId, id);
        List<SceneryComment> commentInMysql = sceneryCommentMapper.selectList(queryWrapper);
        Integer total = sceneryCommentMapper.selectCount(queryWrapper);
        GetUserComment getUserComment = new GetUserComment();
        getUserComment.setSceneryCommentList(commentInMysql);
        getUserComment.setTotal(total);
        return getUserComment;
    }

}
