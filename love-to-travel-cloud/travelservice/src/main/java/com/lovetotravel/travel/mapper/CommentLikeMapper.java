package com.lovetotravel.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lovetotravel.travel.entity.vo.comment.CommentLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentLikeMapper extends BaseMapper<CommentLike> {
}
