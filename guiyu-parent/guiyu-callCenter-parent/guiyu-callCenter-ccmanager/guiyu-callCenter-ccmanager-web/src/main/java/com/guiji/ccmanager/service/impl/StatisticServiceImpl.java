package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.StatisticMapper;
import com.guiji.ccmanager.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Map> getDashboardCallData(Long aLong, String startDate, String endDate) {

        //今天
        //非今天


        return null;
    }

}
