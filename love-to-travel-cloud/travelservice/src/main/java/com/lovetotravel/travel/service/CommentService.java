package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Comment;
import com.lovetotravel.travel.entity.vo.comment.CommentLike;
import com.lovetotravel.travel.entity.vo.comment.CommentVo;

import java.util.List;

public interface CommentService {

    List<Comment> getById(String id);

    void insert(CommentVo commentVo);

    void removeById(String id);

    void like(CommentLike commentLike);


}
