package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportCallCount;

import java.util.List;
import java.util.Map;

public interface StatisticMapper {
    void deleteYesterday();
    void insertIntoReportCallCount(List<ReportCallCount> list);
    List<ReportCallCount> selectFromCallOutPlan();

    List<Map> getIntentCountOnTime(Map map);
}