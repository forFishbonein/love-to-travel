package com.lovetotravel.travel.service.impl;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.exception.GlobalException;
import com.lovetotravel.travel.result.CodeMsg;
import com.lovetotravel.travel.service.NoteService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class NoteServiceImpl implements NoteService {

    final MongoTemplate mongoTemplate;

    public NoteServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
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
     * 新增
     *
     * @param note
     */
    @Override
    public void insert(Note note) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        note.setCreateTime(currentTimeStamp);
        note.setUpdateTime(currentTimeStamp);
        note.setDeleted("0");
        mongoTemplate.insert(note);
    }


    /**
     * 更新
     *
     * @param note
     */
    @Override
    public void update(Note note) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(note.getId()));
        query.addCriteria(Criteria.where("deleted").is("0"));

        Update update = new Update();

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        update.set("title", note.getTitle())
                .set("content", note.getContent())
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
        mongoTemplate.remove(query, Note.class);
    }
}
