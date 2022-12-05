package com.lovetotravel.travel.service.impl;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.Plan;
import com.lovetotravel.travel.entity.dto.Route;
import com.lovetotravel.travel.entity.dto.Days;
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

        if (plan.getDays() != null) {
            int daysLength = plan.getDays().length;

            Days[] days = plan.getDays();

            for (int i = 0; i < daysLength; i++) {
//                update.set("days." + i + ".city", days[i].getCity());

                if (days[i].getRoute() != null) {
                    int routeLength = days[i].getRoute().length;

                    Route[] routes = days[i].getRoute();
                    for (int j = 0; j < routeLength; j++) {
                        update.set("days." + i + ".route." + j + ".origin", routes[j].getOrigin());
                        update.set("days." + i + ".route." + j + ".destination", routes[j].getDestination());
                        update.set("days." + i + ".route." + j + ".departTime", routes[j].getDepartTime());
                        update.set("days." + i + ".route." + j + ".vehicle", routes[j].getVehicle());
                        update.set("days." + i + ".route." + j + ".budget", routes[j].getBudget());
                        update.set("days." + i + ".route." + j + ".weather", routes[j].getWeather());
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
