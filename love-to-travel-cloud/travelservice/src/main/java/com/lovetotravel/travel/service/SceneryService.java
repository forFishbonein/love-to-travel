package com.lovetotravel.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lovetotravel.travel.entity.PageVo;
import com.lovetotravel.travel.entity.Scenery;
import com.lovetotravel.travel.entity.SceneryComment;
import com.lovetotravel.travel.entity.vo.scenery.*;

import java.util.List;

public interface SceneryService extends IService<Scenery> {

    List<Scenery> getAll();

    Page<Scenery> getPage(PageVo pageVo);

    Scenery getById(Long id);

    List<Scenery> getByCityId(String id);

    void insert(SceneryInsertVo sceneryInsertVo);

    void update(SceneryUpdateVo sceneryUpdateVo);

    void removeById(Long id);

    void removeList(Long[] ids);

    void comment(SceneryCommentVo sceneryCommentVo);

    GetSceneryComment getSceneryComment(Long id);

    GetUserComment getUserComment(Long id);

}
