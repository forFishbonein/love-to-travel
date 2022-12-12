package com.lovetotravel.travel.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "note")
public class Note {

    @ApiModelProperty("游记id")
    @Id
    private String id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("行程")
    private String planId;

    @ApiModelProperty("图片地址")
    private String url;

    @ApiModelProperty("内容")
    private String content;

//    @ApiModelProperty("评分")
//    private String score;

    @ApiModelProperty("评论量")
    private Long comment;

    @ApiModelProperty("浏览量")
    private Long view;

    @ApiModelProperty("点赞量")
    private Long like;

    @ApiModelProperty("收藏量")
    private Long star;

    @ApiModelProperty("标签")
    private String[] trip;

    @ApiModelProperty("逻辑删除")
    @TableLogic
    private String deleted;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("修改时间")
    private String updateTime;

}
