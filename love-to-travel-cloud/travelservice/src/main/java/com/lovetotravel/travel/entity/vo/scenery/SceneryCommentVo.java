package com.lovetotravel.travel.entity.vo.scenery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneryCommentVo {

    @ApiModelProperty("景区id")
    private String noteId;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("评分")
    private Integer score;

    @ApiModelProperty("评论时间")
    private String createTime;

}
