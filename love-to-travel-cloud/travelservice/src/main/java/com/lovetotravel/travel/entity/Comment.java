package com.lovetotravel.travel.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "comment")
public class Comment {

    @ApiModelProperty("评论id")
    @Id
    private String id;

    @ApiModelProperty("游记id")
    private String noteId;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("评论点赞数")
    private Integer like;

    @ApiModelProperty("评论回复数")
    private Integer reply;

    @ApiModelProperty("上级评论id")
    private String parentId;

    @ApiModelProperty("评论时间")
    private String createTime;


}
