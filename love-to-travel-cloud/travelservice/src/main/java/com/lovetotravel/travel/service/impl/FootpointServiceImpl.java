package com.lovetotravel.travel.service.impl;

import com.lovetotravel.travel.entity.Footpoint;
import com.lovetotravel.travel.entity.dto.Been;
import com.lovetotravel.travel.entity.dto.Want;
import com.lovetotravel.travel.service.FootpointService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootpointServiceImpl implements FootpointService {


    final MongoTemplate mongoTemplate;

    public FootpointServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Footpoint getByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        System.out.println(mongoTemplate.findOne(query, Footpoint.class));
        return mongoTemplate.findOne(query, Footpoint.class);
    }

    public void insertWants(Want want) {
        Query query = new Query();




    }

    public void insertBeens(Been been) {

    }


    @Override
    public void update(Footpoint footpoint) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(footpoint.getUserId()));
        Update update = new Update();
        Want[] wants = footpoint.getWants();
        Been[] beens = footpoint.getBeens();
        if (footpoint.getWants() != null) {
            int wantsLength = footpoint.getWants().length;
            for (int i = 0; i < wantsLength; i++) {
                update.set("wants." + i + ".cityId", wants[i].getCityId());
                update.set("wants." + i + ".createTime", wants[i].getCreateTime());
            }
        }
        if (footpoint.getBeens() != null) {
            int beensLength = footpoint.getBeens().length;
            for (int i = 0; i < beensLength; i++) {
                update.set("beens." + i + ".cityId", beens[i].getCityId());
                update.set("wants." + i + ".createTime", beens[i].getCreateTime());
                update.set("wants." + i + ".score", beens[i].getScore());
            }
        }
        mongoTemplate.upsert(query, update, Footpoint.class);
        System.out.println(update);

    }

    @Override
    public void remove(Footpoint footpoint) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(footpoint.getUserId()));
        Update update = new Update();
        Want[] wants = footpoint.getWants();
        Been[] beens = footpoint.getBeens();
        if (wants == null && beens == null) {
            mongoTemplate.remove(query, Footpoint.class);
            return;
        }
        if (footpoint.getWants() != null) {
            int wantsLength = footpoint.getWants().length;
            for (int i = 0; i < wantsLength; i++) {
                update.set("wants." + i + ".cityCode", wants[i].getCityId());
                update.set("wants." + i + ".createTime", wants[i].getCreateTime());
            }
        }
        if (footpoint.getBeens() != null) {
            int beensLength = footpoint.getBeens().length;
            for (int i = 0; i < beensLength; i++) {
                update.set("beens." + i + ".cityCode", beens[i].getCityId());
                update.set("wants." + i + ".createTime", beens[i].getCreateTime());
                update.set("wants." + i + ".score", beens[i].getScore());
            }
        }
        mongoTemplate.upsert(query, update, Footpoint.class);
        System.out.println(update);


    }

}
