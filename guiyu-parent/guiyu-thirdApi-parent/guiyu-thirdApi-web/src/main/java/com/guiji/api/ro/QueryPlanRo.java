package com.guiji.api.ro;

import com.guiji.common.GenericRo;
import lombok.Data;

/**
 * 查询计划信息
 */
@Data
public class QueryPlanRo extends GenericRo {

    private Long userId;

    private String batchName;

}
