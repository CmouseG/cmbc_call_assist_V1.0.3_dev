package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.ReportCallDayMapper;
import com.guiji.ccmanager.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    ReportCallDayMapper reportCallDayMapper;

    @Override
    public List<String> getAllLabelOneMonth(String orgCode,Long userId,Integer authLevel) {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Date date = c.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        List<String> allLableList =reportCallDayMapper.getAllLabelFromDate(orgCode, dateStr,userId,authLevel);
        List<String> todayLableList =reportCallDayMapper.getAllLabelFromToday(orgCode,userId,authLevel);

        if(allLableList!=null && allLableList.size()>0){

            if(todayLableList!=null && todayLableList.size()>0){
                for(String todayLable : todayLableList){
                    if(!allLableList.contains(todayLable)){
                        allLableList.add(todayLable);
                    }
                }
            }

            return allLableList;
        }else{
            return todayLableList;
        }


    }
}
