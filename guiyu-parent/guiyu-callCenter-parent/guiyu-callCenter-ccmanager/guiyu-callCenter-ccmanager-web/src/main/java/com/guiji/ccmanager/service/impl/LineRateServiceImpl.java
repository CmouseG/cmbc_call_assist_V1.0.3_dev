package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.daoNoSharing.LineRateMapper;
import com.guiji.ccmanager.entity.LineRateResponse;
import com.guiji.ccmanager.service.LineRateService;
import com.guiji.ccmanager.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LineRateServiceImpl implements LineRateService {

    @Autowired
    LineRateMapper lineRateMapper;


    @Override
    public LineRateResponse getLineRate(Integer lineId, Date startTime, Date endTime) {

        Date dateAgo = DateUtils.getDaysAgo(29);
        if (startTime.before(dateAgo)) {
            List<LineRateResponse>  list = lineRateMapper.getLineRateFromDayReport(lineId, startTime, endTime, dateAgo);
            if(list!=null && list.size()>0){
                return list.get(0);
            }
        } else {
            List<LineRateResponse> list = lineRateMapper.getLineRateFromResult(lineId, startTime, endTime);
            if(list!=null && list.size()>0){
                return list.get(0);
            }
        }
        return null;
    }

    @Override
    public List<LineRateResponse> getLineRateAll(Date startTime, Date endTime) {

        //call_line_result保留30天的数据
        //30天之前的数据到call_line_day_report里面查询

        Date dateAgo = DateUtils.getDaysAgo(29);
        if (startTime.before(dateAgo)) {
            return lineRateMapper.getLineRateAllFromResult(startTime, endTime);
        } else {
            return lineRateMapper.getLineRateAllFromDayReport(startTime, endTime, dateAgo);
        }

    }
}
