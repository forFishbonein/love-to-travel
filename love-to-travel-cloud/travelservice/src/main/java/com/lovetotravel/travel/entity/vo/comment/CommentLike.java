package com.lovetotravel.travel.entity.vo.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentLike {

    @ApiModelProperty("评论id")
    private String commentId;

    @ApiModelProperty("用户id")
    private String userId;

}
