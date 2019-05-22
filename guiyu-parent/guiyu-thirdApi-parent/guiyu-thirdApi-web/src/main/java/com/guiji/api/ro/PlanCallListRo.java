package com.guiji.api.ro;

import com.guiji.common.GenericRo;
import lombok.Data;

/**
 * 查询计划信息
 */
@Data
public class PlanCallListRo extends GenericRo {

    private String batchName;

    private Integer pageNum;

    private Integer page;

    private String notifyUrl;

}
