package com.lovetotravel.travel.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.lovetotravel.travel.entity.dto.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "team")
public class Team {

    @ApiModelProperty("组队id")
    @MongoId
    private ObjectId id;

    @ApiModelProperty("行程id")
    private String planId;

    @ApiModelProperty("团队名称")
    private String teamName;

    @ApiModelProperty("途径地点/规划的地点")
    private String[] place;

    @ApiModelProperty("出行时间")
    private String depart;

    @ApiModelProperty("预算")
    private String budget;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("预期成员数量")
    private String num;

    @ApiModelProperty("成员信息")
    private Member[] members;

    @ApiModelProperty("逻辑删除")
    @TableLogic
    private String deleted;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("修改时间")
    private String updateTime;


}
