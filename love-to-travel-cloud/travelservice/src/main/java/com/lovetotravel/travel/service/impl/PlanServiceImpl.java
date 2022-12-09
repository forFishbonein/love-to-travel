package com.lovetotravel.travel.service.impl;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.Plan;
import com.lovetotravel.travel.entity.dto.Route;
import com.lovetotravel.travel.entity.dto.Days;
import com.lovetotravel.travel.entity.dto.SubPlan;
import com.lovetotravel.travel.service.PlanService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {


    final MongoTemplate mongoTemplate;

    public PlanServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void insert(Plan plan) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        plan.setCreateTime(currentTimeStamp);
        plan.setUpdateTime(currentTimeStamp);
        plan.setDeleted("0");
        mongoTemplate.insert(plan);
    }

    @Override
    public void update(Plan plan) {
        System.out.println(plan);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(plan.getId()));
        query.addCriteria(Criteria.where("deleted").is("0"));

        Update update = new Update();

        update.set("budget", plan.getBudget());

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);

        if (plan.getSubPlans() != null) {
            SubPlan[] subPlans = plan.getSubPlans();

            int subPlanLength = subPlans.length;

            for (int i = 0; i < subPlanLength; i++) {
                update.set("subPlans." + i + ".cityId", subPlans[i].getCityId());
                update.set("subPlans." + i + ".city", subPlans[i].getCity());
                update.set("subPlans." + i + ".dayLength", subPlans[i].getDayLength());

                if (subPlans[i].getDays() != null) {
                    Days[] days = subPlans[i].getDays();

                    int daysLength = days.length;

                    for (int j = 0; j < daysLength; j++) {

                        if (days[j].getRoute() != null) {

                            Route[] routes = days[i].getRoute();

                            int routeLength = routes.length;

                            for (int k = 0; k < routeLength; k++) {
                                update.set("subPlans." + i + "days." + k + ".route." + k + ".origin", routes[j].getOrigin());
                                update.set("subPlans." + i + "days." + k + ".route." + k + ".originName", routes[j].getOriginName());
                                update.set("subPlans." + i + "days." + k + ".route." + k + ".destination", routes[j].getDestination());
                                update.set("subPlans." + i + "days." + k + ".route." + k + ".destinationName", routes[j].getDestinationName());
                                update.set("subPlans." + i + "days." + k + ".route." + k + ".departTime", routes[j].getDepartTime());
                                update.set("subPlans." + i + "days." + k + ".route." + k + ".vehicle", routes[j].getVehicle());
                                update.set("subPlans." + i + "days." + k + ".route." + k + ".budget", routes[j].getBudget());
                                update.set("subPlans." + i + "days." + k + ".route." + k + ".weather", routes[j].getWeather());
                            }
                        }
                    }
                }



            }


        }



        update.set("updateTime", currentTimeStamp);
        System.out.println(update);
        System.out.println("=====================");
        System.out.println("=====================");
        mongoTemplate.updateFirst(query, update, Plan.class);
        System.out.println(update);
    }

    @Override
    public List<Plan> getByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("deleted").is("0"));
        return mongoTemplate.find(query, Plan.class);
    }

}
