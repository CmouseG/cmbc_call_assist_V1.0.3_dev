package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.StatisticMapper;
import com.guiji.callcenter.dao.entityext.CallCountHour;
import com.guiji.callcenter.dao.entityext.DashboardOverView;
import com.guiji.callcenter.dao.entityext.IntentCount;
import com.guiji.callcenter.dao.entityext.ReasonCount;
import com.guiji.ccmanager.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/5 0005 15:42
 * @Description:
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    StatisticMapper statisticMapper;

    @Override
    public List<Map> getIntentCountOnTime(Long userId, String startDate, String endDate) throws ParseException {

        Map map = new HashMap();
        if(userId!=null){
            map.put("customer_id",userId);
        }
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        return statisticMapper.getIntentCountOnTime(map);

    }

    @Override
    public List<DashboardOverView> getDashboardOverView(String userId, String startDate, String endDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        List<DashboardOverView> listResult = new ArrayList<DashboardOverView>();


        //非今天
        if(!startDate.equals(today)){
            List<DashboardOverView> listAgoDurationAll = statisticMapper.getDashboardOverViewAgoDurationAll(startDate,endDate,userId);
            List<DashboardOverView> listAgoNotConnect = statisticMapper.getDashboardOverViewAgoNotConnect(startDate,endDate,userId);
            List<DashboardOverView> listAgoConnect = statisticMapper.getDashboardOverViewAgoConnect(startDate,endDate,userId);
            List<DashboardOverView> listAgoDuration5 = statisticMapper.getDashboardOverViewAgoDuration5(startDate,endDate,userId);
            List<DashboardOverView> listAgoDuration10 = statisticMapper.getDashboardOverViewAgoDuration10(startDate,endDate,userId);
            List<DashboardOverView> listAgoDuration30 = statisticMapper.getDashboardOverViewAgoDuration30(startDate,endDate,userId);
            if(listAgoDurationAll!=null && listAgoDurationAll.size()>0){
                for(DashboardOverView durationAll:listAgoDurationAll){
                    if(listAgoNotConnect!=null && listAgoNotConnect.size()>0) {
                        for (DashboardOverView notConnect:listAgoNotConnect) {
                            if(durationAll.getCallDate().equals(notConnect.getCallDate())){
                                durationAll.setNotConnect(notConnect.getNotConnect());
                            }
                        }
                    }
                    if(listAgoConnect!=null && listAgoConnect.size()>0) {
                        for (DashboardOverView connect:listAgoConnect) {
                            if(durationAll.getCallDate().equals(connect.getCallDate())){
                                durationAll.setConnect(connect.getConnect());
                            }
                        }
                    }
                    if(listAgoDuration5!=null && listAgoDuration5.size()>0) {
                        for (DashboardOverView duration5:listAgoDuration5) {
                            if(durationAll.getCallDate().equals(duration5.getCallDate())){
                                durationAll.setDuration5(duration5.getDuration5());
                            }
                        }
                    }
                    if(listAgoDuration10!=null && listAgoDuration10.size()>0) {
                        for (DashboardOverView duration10:listAgoDuration10) {
                            if(durationAll.getCallDate().equals(duration10.getCallDate())){
                                durationAll.setDuration10(duration10.getDuration10());
                            }
                        }
                    }
                    if(listAgoDuration30!=null && listAgoDuration30.size()>0) {
                        for (DashboardOverView duration30:listAgoDuration30) {
                            if(durationAll.getCallDate().equals(duration30.getCallDate())){
                                durationAll.setDuration30(duration30.getDuration30());
                            }
                        }
                    }
                }
            }
            listResult.addAll(listAgoDurationAll);
        }

        //今天
        if(endDate.equals(today)){
            List<DashboardOverView> listTodayDurationAll = statisticMapper.getDashboardOverViewTodayDurationAll(userId);
            List<DashboardOverView> listTodayNotConnect = statisticMapper.getDashboardOverViewTodayNotConnect(userId);
            List<DashboardOverView> listTodayConnect = statisticMapper.getDashboardOverViewTodayConnect(userId);
            List<DashboardOverView> listTodayDuration5 = statisticMapper.getDashboardOverViewTodayDuration5(userId);
            List<DashboardOverView> listTodayDuration10 = statisticMapper.getDashboardOverViewTodayDuration10(userId);
            List<DashboardOverView> listTodayDuration30 = statisticMapper.getDashboardOverViewTodayDuration30(userId);

            if(listTodayDurationAll!=null && listTodayDurationAll.size()>0){
                for(DashboardOverView durationAll:listTodayDurationAll){
                    if(listTodayNotConnect!=null && listTodayNotConnect.size()>0) {
                        for (DashboardOverView notConnect:listTodayNotConnect) {
                            if(durationAll.getCallDate().equals(notConnect.getCallDate())){
                                durationAll.setNotConnect(notConnect.getNotConnect());
                            }
                        }
                    }
                    if(listTodayConnect!=null && listTodayConnect.size()>0) {
                        for (DashboardOverView connect:listTodayConnect) {
                            if(durationAll.getCallDate().equals(connect.getCallDate())){
                                durationAll.setConnect(connect.getConnect());
                            }
                        }
                    }
                    if(listTodayDuration5!=null && listTodayDuration5.size()>0) {
                        for (DashboardOverView duration5:listTodayDuration5) {
                            if(durationAll.getCallDate().equals(duration5.getCallDate())){
                                durationAll.setDuration5(duration5.getDuration5());
                            }
                        }
                    }
                    if(listTodayDuration10!=null && listTodayDuration10.size()>0) {
                        for (DashboardOverView duration10:listTodayDuration10) {
                            if(durationAll.getCallDate().equals(duration10.getCallDate())){
                                durationAll.setDuration10(duration10.getDuration10());
                            }
                        }
                    }
                    if(listTodayDuration30!=null && listTodayDuration30.size()>0) {
                        for (DashboardOverView duration30:listTodayDuration30) {
                            if(durationAll.getCallDate().equals(duration30.getCallDate())){
                                durationAll.setDuration30(duration30.getDuration30());
                            }
                        }
                    }
                }
            }
            listResult.addAll(listTodayDurationAll);
        }

        return listResult;
    }

    @Override
    public List<IntentCount> getIntentCount(String userId, String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        List<IntentCount> listResult = new ArrayList<IntentCount>();

        //非今天
        if(!startDate.equals(today)){
            List<IntentCount> listAgo = statisticMapper.getIntentCountAgo(startDate,endDate,userId);
            listResult.addAll(listAgo);
        }

        //今天
        if(endDate.equals(today)){
            List<IntentCount> listToday = statisticMapper.getIntentCountToday(userId);
            listResult.addAll(listToday);
        }

        return listResult;
    }

    @Override
    public List<CallCountHour> getConnectDataHour(String userId, Date startDate, Date endDate){
        List<CallCountHour> list = statisticMapper.getConnectDataHour(startDate,endDate,userId);
        return list;
    }

    @Override
    public List<ReasonCount> getConnectReasonDay(String userId, String startDate, String endDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        List<ReasonCount> listResult = new ArrayList<ReasonCount>();

        //非今天
        if(!startDate.equals(today)){
            List<ReasonCount> listAgo = statisticMapper.getReasonCountAgo(startDate,endDate,userId);
            listResult.addAll(listAgo);
        }

        //今天
        if(endDate.equals(today)){
            List<ReasonCount> listToday = statisticMapper.getReasonCountToday(userId);
            listResult.addAll(listToday);
        }

        return listResult;
    }
}
