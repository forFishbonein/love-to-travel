package com.lovetotravel.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lovetotravel.user.entity.Medal;
import com.lovetotravel.user.entity.vo.MedalVo;

import java.util.List;

public interface MedalService extends IService<Medal> {

    /**
     * 根据勋章id获取勋章信息
     *
     * @param id
     * @return
     */
    Medal getById(Integer id);

    /**
     * 获取用户的全部勋章
     *
     * @param userId
     * @return
     */
    List<Medal> getByUserId(Long userId);


    void insert(Medal medal);

    void update(Medal medal);

    void removeById(Integer id);

    /**
     * 申请勋章
     *
     * @param medalVo
     */
    void applyMedal(MedalVo medalVo);


}
