package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ErrorMatch;
import com.guiji.callcenter.dao.entity.ReportCallDay;
import com.guiji.callcenter.dao.entity.ReportCallHour;
import com.guiji.callcenter.dao.entity.ReportCallToday;
import com.guiji.callcenter.dao.entityext.CallCountHour;
import com.guiji.callcenter.dao.entityext.DashboardOverView;
import com.guiji.callcenter.dao.entityext.IntentCount;
import com.guiji.callcenter.dao.entityext.ReasonCount;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticMapper {
//    List<Map> getIntentCountOnTime(Map map);

    void updateTodayCountAndDruation(ReportCallToday reportCallToday);

    List<ReportCallDay> countReportCallToday30(@Param("orgIdList") List<Integer> orgIdList);
    List<ReportCallDay> countReportCallToday10(@Param("orgIdList") List<Integer> orgIdList);
    List<ReportCallDay> countReportCallToday5(@Param("orgIdList") List<Integer> orgIdList);
    List<ReportCallDay> countReportCallToday0(@Param("orgIdList") List<Integer> orgIdList);

    List<ReportCallDay> countReportCallDayDruation30(@Param("orgIdList") List<Integer> orgIdList);
    List<ReportCallDay> countReportCallDayDruation10(@Param("orgIdList") List<Integer> orgIdList);
    List<ReportCallDay> countReportCallDayDruation5(@Param("orgIdList") List<Integer> orgIdList);
    List<ReportCallDay> countReportCallDayDruation0(@Param("orgIdList") List<Integer> orgIdList);

    void insertReportCallDay(List<ReportCallDay> list);
    void insertReportCallToday(List<ReportCallDay> list);

    void deleteReportCallDay();

    List<ReportCallHour> countReportCallHourOut(@Param("orgIdList") List<Integer> orgIdList);
    List<ReportCallHour> countReportCallHourConnect(@Param("orgIdList") List<Integer> orgIdList);

    void insertReportCallHour(List<ReportCallHour> listOut);

    List<DashboardOverView> getDashboardOverViewAgoDurationAll(@Param("startDate") String startDate,@Param("endDate") String endDate, @Param("orgCode") String orgCode,
                                                               @Param("tempId") String tempId,@Param("authLevel") int authLevel,@Param("userId") long userId);
    List<DashboardOverView> getDashboardOverViewAgoNotConnect(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("orgCode") String orgCode,
                                                              @Param("tempId") String tempId,@Param("authLevel") int authLevel,@Param("userId") long userId);
    List<DashboardOverView> getDashboardOverViewAgoConnect(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("orgCode") String orgCode,
                                                           @Param("tempId") String tempId,@Param("authLevel") int authLevel,@Param("userId") long userId);
    List<DashboardOverView> getDashboardOverViewAgoDuration5(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("orgCode") String orgCode,
                                                             @Param("tempId") String tempId,@Param("authLevel") int authLevel,@Param("userId") long userId);
    List<DashboardOverView> getDashboardOverViewAgoDuration10(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("orgCode") String orgCode,
                                                              @Param("tempId") String tempId,@Param("authLevel") int authLevel,@Param("userId") long userId);
    List<DashboardOverView> getDashboardOverViewAgoDuration30(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("orgCode") String orgCode,
                                                              @Param("tempId") String tempId,@Param("authLevel") int authLevel,@Param("userId") long userId);

    List<DashboardOverView> getDashboardOverViewTodayDurationAll(@Param("orgCode") String orgCode,@Param("tempId") String tempId,
                                                                 @Param("authLevel") int authLevel,@Param("userId") long userId);
    List<DashboardOverView> getDashboardOverViewTodayNotConnect(@Param("orgCode") String orgCode,@Param("tempId") String tempId,
                                                                @Param("authLevel") int authLevel,@Param("userId") long userId);
    List<DashboardOverView> getDashboardOverViewTodayConnect(@Param("orgCode") String orgCode,@Param("tempId") String tempId,
                                                             @Param("authLevel") int authLevel,@Param("userId") long userId);
    List<DashboardOverView> getDashboardOverViewTodayDuration5(@Param("orgCode") String orgCode,@Param("tempId") String tempId,
                                                               @Param("authLevel") int authLevel,@Param("userId") long userId);
    List<DashboardOverView> getDashboardOverViewTodayDuration10(@Param("orgCode") String orgCode,@Param("tempId") String tempId,
                                                                @Param("authLevel") int authLevel,@Param("userId") long userId);
    List<DashboardOverView> getDashboardOverViewTodayDuration30(@Param("orgCode") String orgCode,@Param("tempId") String tempId,
                                                                @Param("authLevel") int authLevel,@Param("userId") long userId);

    List<IntentCount> getIntentCountAgo(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("orgCode") String orgCode,
                                        @Param("tempId") String tempId, @Param("authLevel") int authLevel, @Param("userId") long userId,@Param("queryUser") Integer queryUser);
    List<IntentCount> getIntentCountToday(@Param("orgCode") String orgCode, @Param("tempId") String tempId,
                                          @Param("authLevel") int authLevel, @Param("userId") long userId,@Param("queryUser") Integer queryUser);

    List<CallCountHour> getConnectDataHour(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("orgCode") String orgCode,
                                           @Param("tempId") String tempId,@Param("authLevel") int authLevel,@Param("userId") long userId);


    List<ReasonCount> getReasonCountAgo(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("orgCode") String orgCode,
                                        @Param("tempId") String tempId,@Param("authLevel") int authLevel,@Param("userId") long userId);
    List<ReasonCount> getReasonCountToday(@Param("orgCode") String orgCode,@Param("tempId") String tempId,
                                          @Param("authLevel") int authLevel,@Param("userId") long userId);

    //    void reportCallTodayTruncate();
    void deleteCallTodayTruncate();

    void deleteReportCallHour();

    List<ErrorMatch> getErrorMaths();

    Map getLineCountAndConcurrent(@Param("customerId") String customerId, @Param("orgCode") String orgCode);


}