package com.lovetotravel.travel.service.impl;

import com.lovetotravel.feign.clients.UserClient;
import com.lovetotravel.feign.entity.Result;
import com.lovetotravel.feign.entity.User;
import com.lovetotravel.travel.entity.Team;
import com.lovetotravel.travel.entity.dto.Member;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.vo.team.*;
import com.lovetotravel.travel.exception.GlobalException;
import com.lovetotravel.travel.result.CodeMsg;
import com.lovetotravel.travel.service.EmailService;
import com.lovetotravel.travel.service.TeamService;
import org.bson.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    final MongoTemplate mongoTemplate;
    final UserClient userClient;
    final EmailService emailService;

    public TeamServiceImpl(MongoTemplate mongoTemplate, UserClient userClient, EmailService emailService) {
        this.mongoTemplate = mongoTemplate;
        this.userClient = userClient;
        this.emailService = emailService;
    }

    @Override
    public Team getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        query.addCriteria(Criteria.where("deleted").is("0"));
        return mongoTemplate.findOne(query, Team.class);
    }

    @Override
    public List<Team> getCreatedById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(id));
        query.addCriteria(Criteria.where("deleted").is("0"));
        return mongoTemplate.find(query, Team.class);
    }

    @Override
    public List<Team> getJoinedById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("members").elemMatch(Criteria.where("userId").is(id)));
        query.addCriteria(Criteria.where("deleted").is("0"));

        return mongoTemplate.find(query, Team.class);
    }

    @Override
    public List<Team> getAll() {
        Query query = new Query();
        query.addCriteria(Criteria.where("deleted").is("0")).with(Sort.by(Sort.Order.desc("createTime")));
        return mongoTemplate.find(query, Team.class);
    }

    @Override
    public PageVo<Team> getPage(PageVo pageVo) {
        Integer pageSize = pageVo.getPageSize();
        Integer pageNum = pageVo.getPageNum();
        List<Team> list;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("deleted").is("0"));
            long total = mongoTemplate.count(query, Team.class);
            //默认值为5，
            pageSize = pageSize < 0 ? 5 : pageSize;
            query.limit(pageSize);
            query.skip((pageNum - 1) * pageSize);
            list = mongoTemplate.find(query, Team.class);
            pageVo.setRecords(list);
            pageVo.setTotal(total);
            return pageVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(TeamCreateVo teamCreateVo) {
        Team team = new Team();
        BeanUtils.copyProperties(teamCreateVo, team);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        team.setCreateTime(currentTimeStamp);
        team.setUpdateTime(currentTimeStamp);
        team.setDeleted("0");
        mongoTemplate.insert(team);
    }

    @Override
    public void update(TeamUpdateVo teamUpdateVo) {
        System.out.println(teamUpdateVo);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(teamUpdateVo.getId()));
        query.addCriteria(Criteria.where("deleted").is("0"));

        Update update = new Update();

        update.set("planId", teamUpdateVo.getPlanId());
        update.set("teamName", teamUpdateVo.getBudget());
        update.set("ownerId", teamUpdateVo.getOwnerId());
        update.set("place", teamUpdateVo.getPlace());
        update.set("depart", teamUpdateVo.getBudget());
        update.set("day", teamUpdateVo.getDay());
        update.set("budget", teamUpdateVo.getBudget());
        update.set("introduction", teamUpdateVo.getIntroduction());
        update.set("slogan", teamUpdateVo.getSlogan());
        update.set("introduction", teamUpdateVo.getIntroduction());
        update.set("num", teamUpdateVo.getNum());

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);

        update.set("updateTime", currentTimeStamp);
        mongoTemplate.updateFirst(query, update, Team.class);

    }

    @Override
    public void removeById(String id) {
        if (id == null) {
            throw new GlobalException(CodeMsg.TEAM_NOT_EXIST);
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        query.addCriteria(Criteria.where("deleted").is("0"));
        Update update = new Update();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        update.set("deleted", "1")
                .set("updateTime", currentTimeStamp);
        mongoTemplate.updateFirst(query, update, Team.class);
    }

    @Override
    public void invite(TeamInviteVo teamInviteVo) {
        Result<User> result = userClient.getById(Long.valueOf(teamInviteVo.getInviterId()));
        User inviter = result.getData();
        if (inviter == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        User user = userClient.getByEmail(teamInviteVo.getUserEmail());
        if (user == null) {
            throw new GlobalException(CodeMsg.EMAIL_EMPTY);
        }
        emailService.sendEmail(teamInviteVo, inviter, user);
    }

    @Override
    public void kick(TeamVo teamVo) {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(teamVo.getTeamId()));
        query.addCriteria(Criteria.where("deleted").is("0"));
        Team team = mongoTemplate.findOne(query, Team.class);

        Update update = new Update();

        if (team.getMembers() != null) {
            Member[] members = team.getMembers();

            int membersLength = members.length;

            for (int i = 0; i < membersLength; i++) {
                if (!members[i].getUserId().equals(teamVo.getUserId())) {
                    update.set("members." + i + ".userId", members[i].getUserId());
                    update.set("members." + i + ".userName", members[i].getUserName());
                    update.set("members." + i + ".email", members[i].getEmail());
                    update.set("members." + i + ".tele", members[i].getTele());
                }
            }
        }

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);

        update.set("updateTime", currentTimeStamp);
        mongoTemplate.updateFirst(query, update, Team.class);


    }

    @Override
    public void join(TeamJoinVo teamJoinVo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(teamJoinVo.getTeamId()));
        query.addCriteria(Criteria.where("deleted").is("0"));
        Document updateDoc = new Document();
        Member member = new Member();
        BeanUtils.copyProperties(teamJoinVo, member);
        updateDoc.append("$addToSet", new Document().append("members", member));
        BasicUpdate update = new BasicUpdate(updateDoc);
        mongoTemplate.upsert(query, update, Team.class);
    }

    @Override
    public Boolean isJoin(TeamVo teamVo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(teamVo.getTeamId()));
        query.addCriteria(Criteria.where("members").elemMatch(Criteria.where("userId").is(teamVo.getUserId())));
        query.addCriteria(Criteria.where("deleted").is("0"));
        Team team = mongoTemplate.findOne(query, Team.class);
        return team != null;
    }

    @Override
    public void leave(TeamLeaveVo teamLeaveVo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(teamLeaveVo.getTeamId()));
        query.addCriteria(Criteria.where("deleted").is("0"));
        Update update = new Update();
        Document doc = new Document();
        doc.put("userId", teamLeaveVo.getUserId());
        update.pull("members", doc);
        mongoTemplate.updateMulti(query, update, Team.class);
    }


}
