package com.guiji.callcenter.fsmanager.entity;

import lombok.Data;

import java.util.List;

/**
 * 分页类
 */
@Data
public class PagingVO {
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
