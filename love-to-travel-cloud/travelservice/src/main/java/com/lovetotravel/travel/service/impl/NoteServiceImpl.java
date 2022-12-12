package com.lovetotravel.travel.service.impl;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.vo.NoteVo;
import com.lovetotravel.travel.exception.GlobalException;
import com.lovetotravel.travel.redis.NoteKey;
import com.lovetotravel.travel.redis.RedisService;
import com.lovetotravel.travel.result.CodeMsg;
import com.lovetotravel.travel.service.NoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    final MongoTemplate mongoTemplate;
    final RedisService redisService;

    public NoteServiceImpl(MongoTemplate mongoTemplate, RedisService redisService) {
        this.mongoTemplate = mongoTemplate;
        this.redisService = redisService;
    }

    /**
     * 根据游记id获取游记
     *
     * @param id
     * @return
     */
    @Override
    public Note getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        query.addCriteria(Criteria.where("deleted").is("0"));
        return mongoTemplate.findOne(query, Note.class);
    }

    /**
     * 根据用户id获取游记
     *
     * @param userId
     * @return
     */
    @Override
    public List<Note> getByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("deleted").is("0"));
        return mongoTemplate.find(query, Note.class);
    }

    @Override
    public List<Note> getAll() {
        Query query = new Query();
        query.addCriteria(Criteria.where("deleted").is("0"));
        return mongoTemplate.find(query, Note.class);
    }

    @Override
    public PageVo<Note> getPage(PageVo pageVo) {
        Integer pageSize = pageVo.getPageSize();
        Integer pageNum = pageVo.getPageNum();
        List<Note> list;
        try {
            Query query = new Query(new Criteria());
            long total = mongoTemplate.count(query, Note.class);
            //默认值为5，
            pageSize = pageSize < 0 ? 5 : pageSize;
            query.limit(pageSize);
            query.skip((pageNum - 1) * pageSize);
            list = mongoTemplate.find(query, Note.class);
            pageVo.setRecords(list);
            pageVo.setTotal(total);
            return pageVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增
     *
     * @param noteVo
     */
    @Override
    public void insert(NoteVo noteVo) {
        Note note = new Note();
        BeanUtils.copyProperties(noteVo, note);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        note.setCreateTime(currentTimeStamp);
        note.setUpdateTime(currentTimeStamp);
        note.setDeleted("0");
        note.setLike(0L);
        note.setComment(0L);
        note.setView(0L);
        mongoTemplate.insert(note);
    }

    /**
     * 更新
     *
     * @param noteVo
     */
    @Override
    public void update(NoteVo noteVo) {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(noteVo.getId()));
        query.addCriteria(Criteria.where("deleted").is("0"));

        Update update = new Update();

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        update.set("title", noteVo.getTitle())
                .set("url", noteVo.getUrl())
                .set("plan", noteVo.getPlanId())
                .set("content", noteVo.getContent())
                .set("comment", noteVo.getComment())
                .set("view", noteVo.getView())
                .set("like", noteVo.getLike())
                .set("updateTime", currentTimeStamp);
        mongoTemplate.updateFirst(query, update, Note.class);
        System.out.println(update);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void removeById(String id) {
        if (id == null) {
            throw new GlobalException(CodeMsg.NOTE_NOT_EXIST);
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
        mongoTemplate.updateFirst(query, update, Note.class);
    }

    @Override
    public void removeList(String[] ids) {
        if (ids.length != 0) {

            for (String id : ids) {
                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(id));
                query.addCriteria(Criteria.where("deleted").is("0"));
                Update update = new Update();
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String currentTimeStamp = dateFormat.format(date);
                update.set("deleted", "1")
                        .set("updateTime", currentTimeStamp);
                mongoTemplate.updateFirst(query, update, Note.class);
            }
        }
    }

    @Override
    public void incrComment(String id) {
        redisService.incr(NoteKey.getComment, id);
    }

    @Override
    public void incrLike(String id) {
        redisService.incr(NoteKey.getLike, id);
    }

    @Override
    public void incrView(String id) {
        redisService.incr(NoteKey.getView, id);
    }


}
