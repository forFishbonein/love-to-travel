package com.lovetotravel.travel.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Plan {

    @ApiModelProperty("行程id")
    @Id
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("行程")
    private Integer[] origin;

    @ApiModelProperty("出行时间")
    private String depart;

    @ApiModelProperty("出行方式")
    private String vehicle;

    @ApiModelProperty("总体预算")
    private String budget;









    @ApiModelProperty("逻辑删除")
    @TableLogic
    private String deleted;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("修改时间")
    private String updateTime;



}
