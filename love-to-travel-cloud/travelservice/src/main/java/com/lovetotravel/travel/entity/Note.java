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
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("行程")
    private Plan plan;

    @ApiModelProperty("图片地址")
    private String url;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("评分")
    private String score;

    @ApiModelProperty("浏览量")
    private String views;

    @ApiModelProperty("逻辑删除")
    @TableLogic
    private String deleted;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("修改时间")
    private String updateTime;

}
