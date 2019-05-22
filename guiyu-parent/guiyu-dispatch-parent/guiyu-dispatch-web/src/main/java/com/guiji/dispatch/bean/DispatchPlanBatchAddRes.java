package com.guiji.dispatch.bean;

import lombok.Data;

/**
 * @Classname DispatchPlanResultVo
 * @Description TODO
 * @Date 2019/5/21 22:05
 * @Created by qinghua
 */

@Data
public class DispatchPlanBatchAddRes {

    private String batchName;

    /**
     * 接收数量
     */
    private Integer acceptCount;

    /**
     * 成功数量
     */
    private Integer successCount;

    /**
     * 失败数量
     */
    private Integer failCount;

    /**
     * 失败数据集合
     */
    private String failList;

}
