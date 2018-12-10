package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.StatisticMapper;
import com.guiji.callcenter.dao.entity.ReportCallCount;
import com.guiji.callcenter.dao.entity.ReportCallDay;
import com.guiji.ccmanager.service.ReportSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/6 0006 15:52
 * @Description:
 */
@Service
public class ReportSchedulerServiceImpl implements ReportSchedulerService {

    @Autowired
    StatisticMapper statisticMapper;

    @Override
    public void reportCallCountScheduler() {
        statisticMapper.deleteYesterday();
        List<ReportCallCount> list = statisticMapper.selectFromCallOutPlan();
        statisticMapper.insertIntoReportCallCount(list);
    }

    @Override
    public void reportCallDayScheduler() {
//        statisticMapper.deleteYesterday();
        List<ReportCallDay> list = statisticMapper.countReportCallDay();
        statisticMapper.insertReportCallDay(list);
    }
}
