package com.lovetotravel.travel.entity.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo<T> {

    protected Integer pageNum;//当前页

    protected Integer pageSize;//页大小

    private long total;//总记录数

    private List<T> records;//分页结果

}
