package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.LineInfoMapper;
import com.guiji.callcenter.dao.ReportLineStatusMapper;
import com.guiji.callcenter.dao.StastisticReportLineMapper;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.ccmanager.service.LineReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LineReportServiceImpl implements LineReportService {

    @Autowired
    StastisticReportLineMapper stastisticReportLineMapper;
    @Autowired
    ReportLineStatusMapper reportLineStatusMapper;
    @Autowired
    LineInfoMapper lineInfoMapper;


    @Override
    public List<ReportLineCode> statisticsReportLineCode() {

        List<ReportLineCode> list = stastisticReportLineMapper.selectLineHangupCodeReport();
        return list;
    }


    public List<Map> getLineMonitorReport(Integer lineId, Long userId, Date startTime) {

        if (lineId != null) {
            return stastisticReportLineMapper.getLineMonitorReportByLineId(lineId, startTime);
        } else {
            return stastisticReportLineMapper.getLineMonitorReportByUserId(userId, startTime);
        }
    }

    @Override
    public Map getLineHangupDetail(Integer lineId, Date startTime, Date enTime) {

        List<Map> overViewMapList = stastisticReportLineMapper.getLineHangupCodeOverView(lineId, startTime, enTime);
        if (overViewMapList != null && overViewMapList.size() > 0) {
            Map overViewMap = overViewMapList.get(0);

            List<Map> errorSumMapList = stastisticReportLineMapper.getLineHangupCodeErrorSum(lineId, startTime, enTime);
            Map errorSumMap = new HashMap();
            for(Map map :errorSumMapList){
                errorSumMap.put(map.get("hangup_code"),map.get("totalCalls"));
            }

            List<Map> errorNumsMapList = stastisticReportLineMapper.getLineHangupCodeErrorNums(lineId, startTime, enTime);
            List<Map> errorNumsMapListCancel = stastisticReportLineMapper.getLineHangupCodeErrorNumsCancel(lineId, startTime, enTime);
            errorNumsMapList.addAll(errorNumsMapListCancel);
            overViewMap.put("errorSum",errorSumMap);
            overViewMap.put("errorNums",errorNumsMapList);

            Map resultMap = new HashMap();
            resultMap.put("callRecordSum",overViewMap);
            return resultMap;
        }

        return null;
    }
}
