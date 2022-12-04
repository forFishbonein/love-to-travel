package com.lovetotravel.user.service.impl;

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
        medalMapper.insert(medal);
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