package com.lovetotravel.travel.service.impl;

import com.lovetotravel.travel.entity.Footpoint;
import com.lovetotravel.travel.entity.dto.Been;
import com.lovetotravel.travel.entity.dto.Want;
import com.lovetotravel.travel.entity.vo.foot.BeenRemoveVo;
import com.lovetotravel.travel.entity.vo.foot.BeenVo;
import com.lovetotravel.travel.entity.vo.foot.WantRemoveVo;
import com.lovetotravel.travel.entity.vo.foot.WantVo;
import com.lovetotravel.travel.service.FootpointService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public void insertWants(WantVo wantVo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(wantVo.getUserId()));
        Footpoint footpointInMongo = mongoTemplate.findOne(query, Footpoint.class);
        Footpoint footpoint;
        List<Want> wants;
        if (footpointInMongo == null) {
            footpoint = new Footpoint();
            wants = new ArrayList<>();
            Want want = new Want();
            want.setCityId(wantVo.getCityId());
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String currentTimeStamp = dateFormat.format(date);
            want.setCreateTime(currentTimeStamp);
            wants.add(want);
            footpoint.setWants(wants);
            footpoint.setUserId(wantVo.getUserId());
            mongoTemplate.insert(footpoint);
        } else {
            footpoint = footpointInMongo;
            System.out.println(footpoint);
            wants = footpoint.getWants();
            Want want = new Want();
            want.setCityId(wantVo.getCityId());
            //判断是否添加过
            if (wants != null) {
                for (Want b : wants) {
                    if (b.getCityId().equals(wantVo.getCityId())) {
                        return;
                    }
                }
            } else {
                wants = new ArrayList<>();
            }
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String currentTimeStamp = dateFormat.format(date);
            want.setCreateTime(currentTimeStamp);
            wants.add(want);
            footpoint.setWants(wants);
            mongoTemplate.remove(query, Footpoint.class);
            mongoTemplate.insert(footpoint);
        }

    }

    public void insertBeens(BeenVo beenVo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(beenVo.getUserId()));
        Footpoint footpointInMongo = mongoTemplate.findOne(query, Footpoint.class);
        Footpoint footpoint;
        List<Been> beens;
        if (footpointInMongo == null) {
            footpoint = new Footpoint();
            beens = new ArrayList<>();
            Been been = new Been();
            been.setCityId(beenVo.getCityId());
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String currentTimeStamp = dateFormat.format(date);
            been.setCreateTime(currentTimeStamp);
            been.setScore(beenVo.getScore());
            beens.add(been);
            footpoint.setBeens(beens);
            footpoint.setUserId(beenVo.getUserId());
            mongoTemplate.insert(footpoint);
        } else {
            footpoint = footpointInMongo;
            System.out.println(footpoint);
            beens = footpoint.getBeens();
            Been been = new Been();
            been.setCityId(beenVo.getCityId());
            //判断是否添加过
            if (beens != null) {
                for (Been b : beens) {
                    if (b.getCityId().equals(beenVo.getCityId())) {
                        return;
                    }
                }
            } else {
                beens = new ArrayList<>();
            }
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String currentTimeStamp = dateFormat.format(date);
            been.setCreateTime(currentTimeStamp);
            been.setScore(beenVo.getScore());
            beens.add(been);
            footpoint.setBeens(beens);
            mongoTemplate.remove(query, Footpoint.class);
            mongoTemplate.insert(footpoint);
        }
    }

    @Override
    public void removeWants(WantRemoveVo wantRemoveVo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(wantRemoveVo.getUserId()));
        Footpoint footpointInMongo = mongoTemplate.findOne(query, Footpoint.class);
        Footpoint footpoint = footpointInMongo;
        System.out.println(footpoint);
        List<Want> wants = footpoint.getWants();
        Want want = new Want();
        want.setCityId(wantRemoveVo.getCityId());
        want.setCreateTime(wantRemoveVo.getCreateTime());
        wants.remove(want);
        footpoint.setWants(wants);
        mongoTemplate.remove(query, Footpoint.class);
        mongoTemplate.insert(footpoint);
    }

    @Override
    public void removeBeens(BeenRemoveVo beenRemoveVo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(beenRemoveVo.getUserId()));
        Footpoint footpointInMongo = mongoTemplate.findOne(query, Footpoint.class);
        Footpoint footpoint = footpointInMongo;
        List<Been> beens = footpoint.getBeens();
        Been been = new Been();
        been.setCityId(beenRemoveVo.getCityId());
        been.setCreateTime(beenRemoveVo.getCreateTime());
        been.setScore(beenRemoveVo.getScore());
        beens.remove(been);
        footpoint.setBeens(beens);
        mongoTemplate.remove(query, Footpoint.class);
        mongoTemplate.insert(footpoint);
    }
}
