package com.example.demo.service;

import com.example.demo.entity.Login;
import com.example.demo.mapper.LoginMapper;
import com.example.demo.mapper.MedalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Medal;
import java.util.List;

@Service
public class MedalServiceImpl implements MedalService {
    @Autowired
    private MedalMapper medalMapper;
    @Override
    public List<Medal> findMedalAll() {
        return medalMapper.findMedalAll();
    }

    @Override
    public Medal findMedalByID(Integer id) {
        return medalMapper.findMedalByID(id);
    }

    @Override
    public Integer insertMedal(Medal medal) {
//        Integer num = (int)((Math.random()*9+1)*1000);
//        login.setID(num.toString());
//        System.out.println(login.toString());
        return medalMapper.insertMedal(medal);
    }

    @Override
    public Integer updateMedal(Medal medal) {
        return medalMapper.updateMedal(medal);
    }

    @Override
    public Integer deleteMedal(Integer id) {
        return medalMapper.deleteMedal(id);
    }
}

