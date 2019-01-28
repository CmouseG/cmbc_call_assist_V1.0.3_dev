package com.guiji.web.response;

import lombok.Data;

import java.util.List;

/**
 * 分页类
 */
@Data
public class Paging {
    //每页多少条
    private Integer pageSize;
    //当前页
    private Integer pageNo;
    //总页数
    private Integer totalPage;
    //总条数
    private Long totalRecord;
    //具体的信息
    private List<Object> records;
}
