package com.guiji.ccmanager.service;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/6 0006 15:52
 * @Description:
 */
public interface ReportSchedulerService {

    void reportCallDayScheduler();

    void reportCallHourScheduler();

    void reportCallTodayTruncate();
}
