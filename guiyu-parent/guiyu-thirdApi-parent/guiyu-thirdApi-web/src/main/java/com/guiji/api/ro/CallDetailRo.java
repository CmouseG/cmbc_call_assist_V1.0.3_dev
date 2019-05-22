package com.guiji.api.ro;

import com.guiji.common.GenericRo;
import lombok.Data;

/**
 * 查询通话详情
 */
@Data
public class CallDetailRo extends GenericRo {

    //批次名称
    private String batchName;

    private Integer page;

    private Integer pageNum;

}
