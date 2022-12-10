package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Plan;
import com.lovetotravel.travel.entity.dto.SubPlan;

import java.util.List;

public interface PlanService {

    void insert(Plan plan);

    void update(Plan plan);

    List<Plan> getByUserId(String userId);

    List<SubPlan> getByCityId(String cityId);

}
