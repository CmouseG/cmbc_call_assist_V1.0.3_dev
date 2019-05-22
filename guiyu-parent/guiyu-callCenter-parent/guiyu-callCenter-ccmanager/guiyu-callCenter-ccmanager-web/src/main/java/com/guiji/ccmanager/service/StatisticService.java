package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.ErrorMatch;
import com.guiji.callcenter.dao.entityext.CallCountHour;
import com.guiji.callcenter.dao.entityext.DashboardOverView;
import com.guiji.callcenter.dao.entityext.ReasonCount;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/5 0005 15:42
 * @Description:
 */
public interface StatisticService {
//    List<Map> getIntentCountOnTime(Long userId, String startDate, String endDate) throws ParseException;

    List<DashboardOverView> getDashboardOverView(int authLevel,long userId,String orgCode, String startDate, String endDate, String tempId);

    List<Map<String, Object>> getIntentCount(int authLevel,long userId,String orgCode, String startDate, String endDate, String tempId, Integer queryUser) throws ParseException;

    List<CallCountHour> getConnectDataHour(int authLevel,long userId,String orgCode, Date startDate, Date endDate, String tempId);

    List<ReasonCount> getConnectReasonDay(int authLevel,long userId,String orgCode, String startDate, String endDate, String tempId);

    List<ErrorMatch> getErrorMaths();

//    Map getLineCountAndConcurrent(Long userId, Boolean isSuperAdmin, String orgCode);
}
