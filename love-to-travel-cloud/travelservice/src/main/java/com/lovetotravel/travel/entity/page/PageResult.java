package com.lovetotravel.travel.entity.page;

import lombok.Data;

import java.util.List;
/**
 * mongoDb分页对象
 */
@Data
public class PageResult<T> {
    //当前业
    private Integer pageNum;
    //每页的数量
    private Integer pageSize;
    //总共的条数
    private Long total;
    //总共的页数
    private Integer pages;
    //实体类集合
    private List<T> list;





}


