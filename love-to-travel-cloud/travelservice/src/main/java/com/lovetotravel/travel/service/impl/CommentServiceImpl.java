package com.lovetotravel.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lovetotravel.feign.clients.UserClient;
import com.lovetotravel.feign.entity.Result;
import com.lovetotravel.feign.entity.User;
import com.lovetotravel.travel.entity.Comment;
import com.lovetotravel.travel.entity.vo.comment.CommentLike;
import com.lovetotravel.travel.entity.vo.comment.CommentVo;
import com.lovetotravel.travel.exception.GlobalException;
import com.lovetotravel.travel.mapper.CommentLikeMapper;
import com.lovetotravel.travel.result.CodeMsg;
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
    final UserClient userClient;

    public CommentServiceImpl(MongoTemplate mongoTemplate, CommentLikeMapper commentLikeMapper, UserClient userClient) {
        this.mongoTemplate = mongoTemplate;
        this.commentLikeMapper = commentLikeMapper;
        this.userClient = userClient;
    }

    @Override
    public List<Comment> getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("noteId").is(id));
        return mongoTemplate.find(query, Comment.class);
    }



    @Override
    public void insert(CommentVo commentVo) {
        System.out.println(commentVo);
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentVo, comment);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        comment.setCreateTime(currentTimeStamp);
        comment.setLike(0);
        comment.setReply(0);

        //查询用户名
        commentVo.getUserId();
        Result<User> result = userClient.getById(Long.valueOf(commentVo.getUserId()));
        System.out.println("result = " + result);
        User user = result.getData();
        System.out.println("user = " + user);
        comment.setUserName(user.getName());

        mongoTemplate.insert(comment);

        if (commentVo.getParentId() != "0") {
            //父评论增加评论数
            Query query = new Query();
            query.addCriteria(Criteria.where("parentId").is(commentVo.getParentId()));
            Comment parentComment = mongoTemplate.findOne(query, Comment.class);
            System.out.println("parentComment = " + parentComment);
            if (parentComment == null) {
                throw new GlobalException(CodeMsg.COMMENT_NOT_EXIST);
            }
            Update update = new Update();
            if (parentComment.getReply() == null) {
                parentComment.setReply(0);
            }
            update.set("reply", parentComment.getReply() + 1);
            mongoTemplate.upsert(query, update, Comment.class);
        }


    }

    @Override
    public void removeById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
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
            query.addCriteria(Criteria.where("id").is(commentLike.getCommentId()));
            Comment comment = mongoTemplate.findOne(query, Comment.class);
            System.out.println("comment = " + comment);
            if (comment.getLike() == null) {
                comment.setLike(0);
            }
            Update update = new Update();
            update.set("like", comment.getLike() + 1);
            mongoTemplate.upsert(query, update, Comment.class);
            //保存用户点赞信息
            commentLikeMapper.insert(commentLike);
        }
    }

}
