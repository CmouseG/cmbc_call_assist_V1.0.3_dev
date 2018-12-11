package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportCallCount;
import com.guiji.callcenter.dao.entity.ReportCallDay;
import com.guiji.callcenter.dao.entity.ReportCallHour;
import com.guiji.callcenter.dao.entity.ReportCallToday;
import com.guiji.callcenter.dao.entityext.DashboardOverView;
import org.apache.ibatis.annotations.Param;

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

    List<DashboardOverView> getDashboardOverViewAgoDurationAll(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("customerId") String customerId);
    List<DashboardOverView> getDashboardOverViewAgoNotConnect(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("customerId") String customerId);
    List<DashboardOverView> getDashboardOverViewAgoConnect(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("customerId") String customerId);
    List<DashboardOverView> getDashboardOverViewAgoDuration5(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("customerId") String customerId);
    List<DashboardOverView> getDashboardOverViewAgoDuration10(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("customerId") String customerId);
    List<DashboardOverView> getDashboardOverViewAgoDuration30(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("customerId") String customerId);

    List<DashboardOverView> getDashboardOverViewTodayDurationAll(@Param("customerId") String customerId);
    List<DashboardOverView> getDashboardOverViewTodayNotConnect(@Param("customerId") String customerId);
    List<DashboardOverView> getDashboardOverViewTodayConnect(@Param("customerId") String customerId);
    List<DashboardOverView> getDashboardOverViewTodayDuration5(@Param("customerId") String customerId);
    List<DashboardOverView> getDashboardOverViewTodayDuration10(@Param("customerId") String customerId);
    List<DashboardOverView> getDashboardOverViewTodayDuration30(@Param("customerId") String customerId);
}