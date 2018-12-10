package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportCallCount;
import com.guiji.callcenter.dao.entity.ReportCallDay;
import com.guiji.callcenter.dao.entity.ReportCallToday;

import java.util.List;
import java.util.Map;

public interface StatisticMapper {
    void deleteYesterday();
    void insertIntoReportCallCount(List<ReportCallCount> list);
    List<ReportCallCount> selectFromCallOutPlan();
    List<Map> getIntentCountOnTime(Map map);

    void updateTodayCountAndDruation(ReportCallToday reportCallToday);

    void insertReportCallDay(List<ReportCallDay> list);

    List<ReportCallDay> countReportCallDay();
}