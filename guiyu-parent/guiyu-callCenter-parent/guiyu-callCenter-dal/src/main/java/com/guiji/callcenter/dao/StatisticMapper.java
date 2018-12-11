package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportCallCount;
import com.guiji.callcenter.dao.entity.ReportCallDay;
import com.guiji.callcenter.dao.entity.ReportCallHour;
import com.guiji.callcenter.dao.entity.ReportCallToday;
import com.guiji.callcenter.dao.entityext.DashboardOverView;

import java.util.List;
import java.util.Map;

public interface StatisticMapper {
    void deleteYesterday();
    void insertIntoReportCallCount(List<ReportCallCount> list);
    List<ReportCallCount> selectFromCallOutPlan();
    List<Map> getIntentCountOnTime(Map map);

    void updateTodayCountAndDruation(ReportCallToday reportCallToday);


    List<ReportCallDay> countReportCallDay();

    List<ReportCallDay> countReportCallDayDruation30();
    List<ReportCallDay> countReportCallDayDruation10();
    List<ReportCallDay> countReportCallDayDruation5();
    List<ReportCallDay> countReportCallDayDruation0();

    void insertReportCallDay(List<ReportCallDay> list);

    void deleteReportCallDay();

    List<ReportCallHour> countReportCallHourOut();
    List<ReportCallHour> countReportCallHourConnect();

    void insertReportCallHour(List<ReportCallHour> listOut);

    List<DashboardOverView> getDashboardOverViewAgoDurationAll();
    List<DashboardOverView> getDashboardOverViewAgoNotConnect();
    List<DashboardOverView> getDashboardOverViewAgoConnect();
    List<DashboardOverView> getDashboardOverViewAgoDuration5();
    List<DashboardOverView> getDashboardOverViewAgoDuration10();
    List<DashboardOverView> getDashboardOverViewAgoDuration30();

    List<DashboardOverView> getDashboardOverViewAgo(String startDate, String endDate);
    List<DashboardOverView> getDashboardOverViewToday();
}