package com.lovetotravel.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lovetotravel.user.entity.Medal;
import com.lovetotravel.user.mapper.MedalMapper;
import com.lovetotravel.user.service.MedalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedalServiceImpl extends ServiceImpl<MedalMapper, Medal> implements MedalService {


    @Override
    public Medal getById(Integer id) {
        return null;
    }

    @Override
    public List<Medal> getByUserId(Long userId) {
        return null;
    }
}
