package com.lovetotravel.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lovetotravel.travel.entity.Comment;
import com.lovetotravel.travel.entity.vo.comment.CommentLike;
import com.lovetotravel.travel.entity.vo.comment.CommentVo;
import com.lovetotravel.travel.mapper.CommentLikeMapper;
import com.lovetotravel.travel.service.CommentService;
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
public class CommentServiceImpl implements CommentService {

    final MongoTemplate mongoTemplate;
    final CommentLikeMapper commentLikeMapper;

    public CommentServiceImpl(MongoTemplate mongoTemplate, CommentLikeMapper commentLikeMapper) {
        this.mongoTemplate = mongoTemplate;
        this.commentLikeMapper = commentLikeMapper;
    }

    @Override
    public List<Comment> getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("noteId").is(id));
        return mongoTemplate.find(query, Comment.class);
    }

    @Override
    public void insert(CommentVo commentVo) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentVo, comment);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        comment.setCreateTime(currentTimeStamp);
        comment.setLike(0);
        mongoTemplate.insert(comment);


    }

    @Override
    public void removeById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("commentId").is(id));
        mongoTemplate.remove(query, Comment.class);
    }

    @Override
    public void like(CommentLike commentLike) {
        //查询用户是否点赞
        QueryWrapper<CommentLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommentLike::getUserId, commentLike.getUserId()).eq(CommentLike::getCommentId, commentLike.getCommentId());
        CommentLike commentLikeInMysql = commentLikeMapper.selectOne(queryWrapper);
        if (commentLikeInMysql == null) {
            //增加点赞数
            Query query = new Query();
            query.addCriteria(Criteria.where("commentId").is(commentLike.getCommentId()));
            Comment comment = mongoTemplate.findOne(query, Comment.class);
            Update update = new Update();
            update.set("like", comment.getLike()+1);
            mongoTemplate.upsert(query, update, Comment.class);

            //保存用户点赞信息
            commentLikeMapper.insert(commentLike);
        }





    }

    @Override
    public void reply(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("commentId").is(id));
        Comment comment = mongoTemplate.findOne(query, Comment.class);
        Update update = new Update();
        update.set("reply", comment.getReply()+1);
        mongoTemplate.upsert(query, update, Comment.class);
    }


}
