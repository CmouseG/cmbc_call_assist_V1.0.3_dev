package com.guiji.dispatch.model;

import lombok.Data;

/**
 * @Classname IQueryPlanThirdRo
 * @Description TODO
 * @Date 2019/5/22 16:30
 * @Created by qinghua
 */
@Data
public class QueryPlanThirdRo {

    /**
     * 批次名称
     */
    private String batchName;

    /**
     * 当前页码
     */
    private int page;

    /**
     * 每页条数
     */
    private int pageNum;

    private Integer userId;

}
