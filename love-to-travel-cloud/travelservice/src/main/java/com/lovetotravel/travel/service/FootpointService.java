package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Footpoint;

import java.util.List;

public interface FootpointService {


    Footpoint getByUserId(String userId);

    void update(Footpoint footpoint);

    void remove(Footpoint footpoint);

}
