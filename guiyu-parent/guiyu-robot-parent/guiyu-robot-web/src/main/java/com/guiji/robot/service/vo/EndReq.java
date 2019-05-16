package com.guiji.robot.service.vo;

import lombok.Data;

/**
 * @Classname EndReq
 * @Description 挂断时通知sellbot和飞龙进行后续清理工作
 * @Date 2019/5/6 16:13
 * @Created by qinghua
 */
@Data
public class EndReq {

    /**
     * 最后一句话
     */
    private String sentence;

    /**
     * 一通电话总时长
     */
    private Integer totalCallTime;

    /**
     * 0 - 未接通
     * 1 - 客户挂断
     * 2 - ai挂断
     */
    private Integer hangupType;

    /**
     * 一通电话seqId
     */
    private String seqId;

    /**
     * 0 - 播放完毕
     * 1 - 未播放完毕
     */
    private Integer hangupHalfWay;

    /**
     * 本轮通话时长
     */
    private Integer playTime;

    /**
     * 话术模板id
     */
    private String templateId;
}
