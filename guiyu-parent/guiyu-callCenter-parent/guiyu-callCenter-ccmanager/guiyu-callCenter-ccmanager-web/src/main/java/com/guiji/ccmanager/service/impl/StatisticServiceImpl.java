package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.StatisticMapper;
import com.guiji.callcenter.dao.entityext.DashboardOverView;
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
    public List<DashboardOverView> getDashboardOverView(Long userId, String startDate, String endDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        List<DashboardOverView> listResult = new ArrayList<DashboardOverView>();

        //今天
        if(endDate.equals(today)){
           List<DashboardOverView> listToday = statisticMapper.getDashboardOverViewToday();
           listResult.addAll(listToday);
        }
        //非今天
//        if(!startDate.equals(today)){
//            List<DashboardOverView> listAgo = statisticMapper.getDashboardOverViewAgo(startDate,endDate);
//            listResult.addAll(listAgo);
//        }

        return listResult;
    }

}
