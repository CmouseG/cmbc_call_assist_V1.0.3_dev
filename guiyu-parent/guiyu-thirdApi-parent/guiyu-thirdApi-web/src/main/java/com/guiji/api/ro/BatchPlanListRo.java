package com.guiji.api.ro;

import com.guiji.common.GenericRo;
import lombok.Data;

/**
 * 查询批次信息
 */
@Data
public class BatchPlanListRo extends GenericRo {

    //批次名称
    private String batchName;

    private Integer pageNum;

    private Integer page;

    //回调url
    private String notifyUrl;

}
