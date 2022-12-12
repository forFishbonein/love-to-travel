package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Plan;
import com.lovetotravel.travel.entity.dto.SubPlan;
import com.lovetotravel.travel.entity.page.PageVo;

import java.util.List;

public interface PlanService {

    void insert(Plan plan);

    void update(Plan plan);

    List<Plan> getByUserId(String userId);

    List<Plan> getAll();

    PageVo<Plan> getPage(PageVo pageVo);

    List<SubPlan> getByCityId(String cityId);

}
