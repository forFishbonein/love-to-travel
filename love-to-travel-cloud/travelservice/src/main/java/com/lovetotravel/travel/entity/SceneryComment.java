package com.lovetotravel.travel.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class SceneryComment {

    @ApiModelProperty("评论id")
    @Id
    private String id;

    @ApiModelProperty("景区id")
    private String sceneryId;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("评分")
    private Integer score;

    @ApiModelProperty("评论时间")
    private String createTime;


}
