package com.lovetotravel.travel.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.lovetotravel.travel.entity.dto.SubPlan;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "plan")
public class Plan {

    @ApiModelProperty("行程id")
    @MongoId
    private ObjectId id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("总体预算")
    private String budget;

    @ApiModelProperty("出行时间")
    private String depart;

    @ApiModelProperty("行程中的一个城市")
    private SubPlan[] subPlans;

    //===========

    @ApiModelProperty("逻辑删除")
    @TableLogic
    private String deleted;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("修改时间")
    private String updateTime;


}
