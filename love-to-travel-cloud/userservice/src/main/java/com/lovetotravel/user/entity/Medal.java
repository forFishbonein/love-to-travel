package com.lovetotravel.user.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户登录")
public class Medal {

    @ApiModelProperty("勋章id")
    @TableId
    private Integer id;

    @ApiModelProperty("勋章名称")
    private String name;

    @ApiModelProperty("勋章图片")
    private String url;

    @ApiModelProperty("经验值")
    private Integer experience;

    @ApiModelProperty("勋章描述")
    private String describe;

    @ApiModelProperty("逻辑删除")
    @TableLogic
    private String deleted;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("修改时间")
    private String updateTime;

}
