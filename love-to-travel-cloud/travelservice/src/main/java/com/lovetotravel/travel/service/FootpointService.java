package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Footpoint;

import java.util.List;

public interface FootpointService {


    List<Footpoint> getByUserId(String userId);

    void update(Footpoint footpoint);

}
