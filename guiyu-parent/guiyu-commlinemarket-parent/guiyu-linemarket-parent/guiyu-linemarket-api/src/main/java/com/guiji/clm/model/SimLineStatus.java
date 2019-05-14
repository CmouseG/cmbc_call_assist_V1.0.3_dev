package com.guiji.clm.model;

import lombok.Data;

/**
 * @Classname SimLineStatus
 * @Description TODO
 * @Date 2019/5/14 14:00
 * @Created by qinghua
 */
@Data
public class SimLineStatus {

    /**
     * 线路id
     */
    private Integer lineId;

    /**
     * 线路状态
     * IDLE(1, "空闲"),
     *
     *     BUSY(2, "忙碌"),
     *
     *     CLOSED(3, "关机"),
     *
     *     ERROR_CONN(4, "通信失败"),
     *
     *     INIT(5, "初始化"),
     *
     *     NOT_REGIST(6, "未注册"),
     *
     *     EMPTY(7, "空的"),
     *
     *     NO_SIM(8, "无卡"),
     *
     *     OTHER(9, "其他")
     */
    private Integer status;

    /**
     * 线路状态描述信息
     */
    private String statusMsg;
}
