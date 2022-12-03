package com.example.demo.service;

import com.example.demo.entity.Medal;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public interface MedalService {
    List<Medal> findMedalAll();

    Medal findMedalByID(Integer id);

    Integer insertMedal(Medal medal);

    Integer updateMedal(Medal medal);

    Integer deleteMedal(Integer id);

}
