package com.guiji.api.ro;

import com.guiji.common.GenericRo;
import lombok.Data;

/**
 * @Classname PlanRo
 * @Description TODO
 * @Date 2019/5/21 14:30
 * @Created by qinghua
 */
@Data
public class PlanRo extends GenericRo {

    /**
     * 批次名
     */
    private String batchName;

    /**
     * 话术模板id
     */
    private String botenceId;

    /**
     * lineId
     */
    private String lineIds;

    /**
     * 呼叫时间
     */
    private String callHour;

    /**
     * 呼叫日期
     */
    private String callDate;

    /**
     * 是否当日清除
     */
    private Integer isClear;

    /**
     * 坐席组id
     */
    private Integer groupId;

    /**
     * 手机号相关信息
     */
    private String mobileList;

    /**
     * 批次回调地址
     */
    private String notifyBatchUrl;

    /**
     * 单个回调地址
     */
    private String notifyUrl;

}
