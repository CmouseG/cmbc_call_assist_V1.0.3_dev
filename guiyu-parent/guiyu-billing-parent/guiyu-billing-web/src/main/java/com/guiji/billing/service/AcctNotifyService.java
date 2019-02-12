package com.guiji.billing.service;

public interface AcctNotifyService {

    /**
     * 通知欠费
     * @param arrearageNotifyDto
     * @return
     */
    boolean notifyArrearage(com.guiji.vo.ArrearageNotifyVo arrearageNotifyDto);
}
