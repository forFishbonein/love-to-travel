package com.lovetotravel.travel.entity.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo<T> {


    @ApiModelProperty("当前页")
    private Integer pageNum;

    @ApiModelProperty("页大小")
    private Integer pageSize;

    @ApiModelProperty("总记录数")
    private long total;

    @ApiModelProperty("分页结果")
    private List<T> records;

}
