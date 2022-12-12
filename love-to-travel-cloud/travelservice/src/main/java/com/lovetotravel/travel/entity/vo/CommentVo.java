package com.lovetotravel.travel.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentVo {

    @ApiModelProperty("游记id")
    private String noteId;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("上级评论id")
    private String parentId;
}
