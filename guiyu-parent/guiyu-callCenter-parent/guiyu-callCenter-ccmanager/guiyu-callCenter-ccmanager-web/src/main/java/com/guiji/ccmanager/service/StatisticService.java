package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.ErrorMatch;
import com.guiji.callcenter.dao.entityext.CallCountHour;
import com.guiji.callcenter.dao.entityext.DashboardOverView;
import com.guiji.callcenter.dao.entityext.IntentCount;
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
    List<Map> getIntentCountOnTime(Long userId, String startDate, String endDate) throws ParseException;

    List<DashboardOverView> getDashboardOverView(String userId, String startDate, String endDate);

    List<IntentCount> getIntentCount(String userId, String startDate, String endDate);

    List<CallCountHour> getConnectDataHour(String userId, Date startDate, Date endDate);

    List<ReasonCount> getConnectReasonDay(String userId, String startDate, String endDate);

    List<ErrorMatch> getErrorMaths();
}
