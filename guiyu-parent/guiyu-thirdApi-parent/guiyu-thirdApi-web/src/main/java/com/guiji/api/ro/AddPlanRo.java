package com.guiji.api.ro;

import com.guiji.common.GenericRo;
import lombok.Data;

/**
 * 添加计划
 */
@Data
public class AddPlanRo extends GenericRo {

    //批次名称
    private String batchName;

    //策略名称
    private String ruleName;

    //电话列表phone
    //attach
    //params         phone^attach^params
    private String mobileList;

    //拨打日期
    private String callDate;

    //批次数量
    private Integer batchCount;

    //批次回调地址
    private String notifyBatchUrl;

    //回调url
    private String notifyUrl;

}
