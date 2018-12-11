package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entityext.DashboardOverView;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/5 0005 15:42
 * @Description:
 */
public interface StatisticService {
    List<Map> getIntentCountOnTime(Long userId, String startDate, String endDate) throws ParseException;

    List<DashboardOverView> getDashboardOverView(Long userId, String startDate, String endDate);
}
