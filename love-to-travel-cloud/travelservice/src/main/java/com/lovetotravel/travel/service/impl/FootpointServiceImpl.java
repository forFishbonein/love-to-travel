package com.lovetotravel.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lovetotravel.travel.entity.City;
import com.lovetotravel.travel.entity.Footpoint;
import com.lovetotravel.travel.entity.dto.Been;
import com.lovetotravel.travel.entity.dto.Want;
import com.lovetotravel.travel.entity.vo.foot.BeenRemoveVo;
import com.lovetotravel.travel.entity.vo.foot.BeenVo;
import com.lovetotravel.travel.entity.vo.foot.WantRemoveVo;
import com.lovetotravel.travel.entity.vo.foot.WantVo;
import com.lovetotravel.travel.exception.GlobalException;
import com.lovetotravel.travel.mapper.CityMapper;
import com.lovetotravel.travel.result.CodeMsg;
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
    final CityMapper cityMapper;

    public FootpointServiceImpl(MongoTemplate mongoTemplate, CityMapper cityMapper) {
        this.mongoTemplate = mongoTemplate;
        this.cityMapper = cityMapper;
    }

    @Override
    public Footpoint getByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        System.out.println(mongoTemplate.findOne(query, Footpoint.class));
        return mongoTemplate.findOne(query, Footpoint.class);
    }

    public void insertWants(WantVo wantVo) {
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(City::getCityId, wantVo.getCityId());
        City city = cityMapper.selectOne(queryWrapper);
        if (city == null) {
            throw new GlobalException(CodeMsg.CITY_NOT_EXIST);
        }
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
            want.setCityName(city.getCityName());

            Double[] pos = new Double[2];
            pos[0] = Double.valueOf(city.getLng());
            pos[1] = Double.valueOf(city.getLat());
            want.setPos(pos);

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
            want.setCityName(city.getCityName());
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

            Double[] pos = new Double[2];
            pos[0] = Double.valueOf(city.getLng());
            pos[1] = Double.valueOf(city.getLat());
            want.setPos(pos);

            wants.add(want);
            footpoint.setWants(wants);
            mongoTemplate.remove(query, Footpoint.class);
            mongoTemplate.insert(footpoint);
        }

    }

    public void insertBeens(BeenVo beenVo) {
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(City::getCityId, beenVo.getCityId());
        City city = cityMapper.selectOne(queryWrapper);
        System.out.println(beenVo);
        System.out.println(city);
        if (city == null) {
            throw new GlobalException(CodeMsg.CITY_NOT_EXIST);
        }
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
            been.setCityName(city.getCityName());

            Double[] pos = new Double[2];
            pos[0] = Double.valueOf(city.getLng());
            pos[1] = Double.valueOf(city.getLat());
            been.setPos(pos);

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
            been.setCityName(city.getCityName());
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

            Double[] pos = new Double[2];
            pos[0] = Double.valueOf(city.getLng());
            pos[1] = Double.valueOf(city.getLat());
            been.setPos(pos);

            been.setScore(beenVo.getScore());
            beens.add(been);
            footpoint.setBeens(beens);
            mongoTemplate.remove(query, Footpoint.class);
            mongoTemplate.insert(footpoint);
        }
    }

    @Override
    public void removeWants(WantRemoveVo wantRemoveVo) {
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(City::getCityId, wantRemoveVo.getCityId());
        City city = cityMapper.selectOne(queryWrapper);
        if (city == null) {
            throw new GlobalException(CodeMsg.CITY_NOT_EXIST);
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(wantRemoveVo.getUserId()));
        Footpoint footpointInMongo = mongoTemplate.findOne(query, Footpoint.class);
        Footpoint footpoint;
        List<Want> wants;
        if (footpointInMongo != null) {
            footpoint = footpointInMongo;
            System.out.println(footpoint);
            wants = footpoint.getWants();
            if (wants != null) {
                Want want = new Want();
                want.setCityId(wantRemoveVo.getCityId());
                want.setCityName(city.getCityName());

                Double[] pos = new Double[2];
                pos[0] = Double.valueOf(city.getLng());
                pos[1] = Double.valueOf(city.getLat());
                want.setPos(pos);

                want.setCreateTime(wantRemoveVo.getCreateTime());
                wants.remove(want);
                footpoint.setWants(wants);
                mongoTemplate.remove(query, Footpoint.class);
                mongoTemplate.insert(footpoint);
            }
        }
    }

    @Override
    public void removeBeens(BeenRemoveVo beenRemoveVo) {
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(City::getCityId, beenRemoveVo.getCityId());
        City city = cityMapper.selectOne(queryWrapper);
        if (city == null) {
            throw new GlobalException(CodeMsg.CITY_NOT_EXIST);
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(beenRemoveVo.getUserId()));
        Footpoint footpointInMongo = mongoTemplate.findOne(query, Footpoint.class);
        Footpoint footpoint;
        List<Been> beens;
        if (footpointInMongo != null) {
            footpoint = footpointInMongo;
            System.out.println(footpoint);
            beens = footpoint.getBeens();
            if (beens != null) {
                Been been = new Been();
                been.setCityId(beenRemoveVo.getCityId());
                been.setCityName(city.getCityName());

                Double[] pos = new Double[2];
                pos[0] = Double.valueOf(city.getLng());
                pos[1] = Double.valueOf(city.getLat());
                been.setPos(pos);

                been.setCreateTime(beenRemoveVo.getCreateTime());
                beens.remove(been);
                footpoint.setBeens(beens);
                mongoTemplate.remove(query, Footpoint.class);
                mongoTemplate.insert(footpoint);
            }
        }
    }
}
