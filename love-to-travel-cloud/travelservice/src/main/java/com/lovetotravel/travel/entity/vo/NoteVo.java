package com.lovetotravel.travel.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class NoteVo {

    @ApiModelProperty("游记id")
    private ObjectId id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("行程")
    private String planId;

    @ApiModelProperty("图片地址")
    private String url;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private Long content;

    @ApiModelProperty("评论量")
    private Long comment;

    @ApiModelProperty("浏览量")
    private Long view;

    @ApiModelProperty("点赞量")
    private String like;

}