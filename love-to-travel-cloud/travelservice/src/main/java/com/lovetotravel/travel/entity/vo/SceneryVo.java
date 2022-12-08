package com.lovetotravel.travel.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneryVo {
//    @ApiModelProperty("景区id")
//    @TableId(type = IdType.ASSIGN_ID)
//    private Long Id;

    @ApiModelProperty("景区名称")
    private String name;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("票价")
    private String ticker;

    @ApiModelProperty("开放时间")
    private String opening;

    @ApiModelProperty("经度")
    private String lng;

    @ApiModelProperty("维度")
    private String lat;

    @ApiModelProperty("等级")
    private String level;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("对应城市名称")
    private String cityName;

    @ApiModelProperty("对应省份名称")
    private String provinceName;

    @ApiModelProperty("图片地址")
    private String url;

    @ApiModelProperty("联系方式")
    private String tele;

//    @ApiModelProperty("逻辑删除")
//    @TableLogic
//    private String deleted;
//
//    @ApiModelProperty("创建时间")
//    private String createTime;
//
//    @ApiModelProperty("更新时间")
//    private String updateTime;

}
