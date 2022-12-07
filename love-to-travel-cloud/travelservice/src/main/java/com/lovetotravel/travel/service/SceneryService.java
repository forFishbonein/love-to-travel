package com.lovetotravel.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lovetotravel.travel.entity.Scenery;

import java.util.List;

public interface SceneryService  extends IService<Scenery> {

    List<Scenery> getAll();



}
