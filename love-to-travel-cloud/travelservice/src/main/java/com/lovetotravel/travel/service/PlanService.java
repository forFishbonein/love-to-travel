package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Plan;

import java.util.List;

public interface PlanService {

    void insert(Plan plan);

    void update(Plan plan);

    List<Plan> getByUserId(String userId);

    List<Plan> getByCityId(String cityId);

}
