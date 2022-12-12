package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Footpoint;
import com.lovetotravel.travel.entity.vo.foot.BeenVo;
import com.lovetotravel.travel.entity.vo.foot.WantVo;

import java.util.List;

public interface FootpointService {


    Footpoint getByUserId(String userId);

    void insertWants(WantVo wantVo);

    void insertBeens(BeenVo beens);

    void update(Footpoint footpoint);

    void remove(Footpoint footpoint);

}
