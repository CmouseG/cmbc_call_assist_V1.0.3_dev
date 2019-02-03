package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.daoNoSharing.LineRateMapper;
import com.guiji.ccmanager.entity.LineRateResponse;
import com.guiji.ccmanager.service.LineRateService;
import com.guiji.ccmanager.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LineRateServiceImpl implements LineRateService {

    @Autowired
    LineRateMapper lineRateMapper;


    @Override
    public LineRateResponse getLineRate(Integer lineId, Date startTime, Date endTime) {

        //call_line_result保留30天的数据
        //30天之前的数据到call_line_day_report里面查询
        LineRateResponse lineRateResponse = null;
        List<LineRateResponse> list = lineRateMapper.getLineRateFromResult(lineId,startTime,endTime);
        if(list!=null && list.size()>0){
            lineRateResponse = list.get(0);
        }

        Date dateAgo =  DateUtils.getDaysAgo(29);

        if(startTime.before(dateAgo)){
            List<LineRateResponse> list2 = lineRateMapper.getLineRateFromDayReport(lineId,startTime,dateAgo);
            if(list2!=null && list2.size()>0){
                LineRateResponse lineRateDaysAgo = list2.get(0);
                if(lineRateResponse!=null){
                    int allCount = lineRateResponse.getAllCount();
                    int successCount = lineRateResponse.getSuccessCount();
                    lineRateResponse.setAllCount(allCount+lineRateDaysAgo.getAllCount());
                    lineRateResponse.setSuccessCount(successCount+lineRateDaysAgo.getSuccessCount());
                }else{
                    lineRateResponse=lineRateDaysAgo;
                }
            }
        }

        return lineRateResponse;
    }

    @Override
    public List<LineRateResponse> getLineRateAll(Date startTime, Date endTime) {
        //call_line_result保留30天的数据
        //30天之前的数据到call_line_day_report里面查询

       return lineRateMapper.getLineRateAllFromDayReport(startTime,endTime);
       /* List<LineRateResponse> lineRateResult = lineRateMapper.getLineRateAllFromResult(startTime,endTime);

        Date dateAgo =  DateUtils.getDaysAgo(29);

        if(startTime.before(dateAgo)){
            List<LineRateResponse> list2 = lineRateMapper.getLineRateAllFromDayReport(startTime,dateAgo);
            if(list2!=null && list2.size()>0){

                if(lineRateResult!=null && lineRateResult.size()>0){

                    list2.addAll(lineRateResult);

                    for(LineRateResponse lineRate2:list2){


                    }
                }else{
                    return list2;
                }

            }
        }

        return lineRateResult;*/
    }
}
