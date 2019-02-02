package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.LineRateMapper;
import com.guiji.ccmanager.entity.LineRateResponse;
import com.guiji.ccmanager.service.LineRateService;
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

        List<LineRateResponse> list = lineRateMapper.getLineRate(lineId,startTime,endTime);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<LineRateResponse> getLineRateAll(Date startTime, Date endTime) {
        List<LineRateResponse> list = lineRateMapper.getLineRateAll(startTime,endTime);
        return list;
    }
}
