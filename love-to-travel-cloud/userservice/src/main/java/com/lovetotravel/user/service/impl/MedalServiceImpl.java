package com.lovetotravel.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lovetotravel.user.common.result.CodeMsg;
import com.lovetotravel.user.entity.Medal;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.entity.vo.MedalVo;
import com.lovetotravel.user.exception.GlobalException;
import com.lovetotravel.user.mapper.MedalMapper;
import com.lovetotravel.user.service.MedalService;
import com.lovetotravel.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedalServiceImpl extends ServiceImpl<MedalMapper, Medal> implements MedalService {

    final MedalMapper medalMapper;
    final UserService userService;

    public MedalServiceImpl(MedalMapper medalMapper, UserService userService) {
        this.medalMapper = medalMapper;
        this.userService = userService;
    }

    @Override
    public Medal getById(Integer id) {
        return medalMapper.selectById(id);
    }

    @Override
    public List<Medal> getByUserId(Long userId) {
        return null;
    }

    @Override
    public void insert(Medal medal) {
        if (medal == null) {
            throw new GlobalException(CodeMsg.MEDAL_NOT_EXIST);
        }
        medalMapper.insert(medal);
    }

    @Override
    public void update(Medal medal) {
        if (medal == null) {
            throw new GlobalException(CodeMsg.MEDAL_NOT_EXIST);
        }
        QueryWrapper<Medal> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Medal::getId, medal.getId());
        Medal MedalInMysql = getOne(queryWrapper);
        if (MedalInMysql == null) {
            throw new GlobalException(CodeMsg.MEDAL_NOT_EXIST);
        }
        medalMapper.update(medal, queryWrapper);
    }

    @Override
    public void removeById(Integer id) {
        if (id == null) {
            throw new GlobalException(CodeMsg.MEDAL_NOT_EXIST);
        }
        Medal medal = getById(id);
        if (medal == null) {
            throw new GlobalException(CodeMsg.MEDAL_NOT_EXIST);
        }
        medalMapper.deleteById(id);
    }

    @Override
    public void applyMedal(MedalVo medalVo) {
        User user = userService.getById(medalVo.getUserId());
        Medal medal = getById(medalVo.getMedalId());
        if (user.getExperience() < medal.getExperience()) {
            throw new GlobalException(CodeMsg.EXPERIENCE_LACK);
        }
        medalMapper.applyMedal(medalVo);
    }

}