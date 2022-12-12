package com.lovetotravel.travel.entity.vo.scenery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneryUpdateVo {

    @ApiModelProperty("景区id")
    private Long Id;

    @ApiModelProperty("景区名称")
    private String name;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("评分")
    private String score;

    @ApiModelProperty("票价")
    private String ticket;

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

    @ApiModelProperty("适宜时节")
    private String season;

    @ApiModelProperty("小贴士")
    private String tips;

    @ApiModelProperty("对应城市id")
    private String cityId;

    @ApiModelProperty("图片地址")
    private String url;

    @ApiModelProperty("联系方式")
    private String tele;

    @ApiModelProperty("聚类类别")
    private String cluster;

}
