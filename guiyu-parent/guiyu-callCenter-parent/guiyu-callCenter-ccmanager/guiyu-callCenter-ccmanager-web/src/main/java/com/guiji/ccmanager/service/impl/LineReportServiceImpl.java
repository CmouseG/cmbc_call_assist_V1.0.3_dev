package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.LineInfoMapper;
import com.guiji.callcenter.dao.ReportLineStatusMapper;
import com.guiji.callcenter.dao.StastisticReportLineMapper;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.ccmanager.service.LineReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LineReportServiceImpl implements LineReportService {

    private final Logger logger = LoggerFactory.getLogger(LineReportServiceImpl.class);

    @Autowired
    StastisticReportLineMapper stastisticReportLineMapper;
    @Autowired
    ReportLineStatusMapper reportLineStatusMapper;
    @Autowired
    LineInfoMapper lineInfoMapper;


    @Override
    @Transactional
    public void statisticsReportLineCode() throws ParseException {

        //获取当前时间，分钟跟5取模
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        int minInt = Integer.valueOf(dateStr.substring(15));
        int moMin = minInt%5;
        String before = dateStr.substring(0,15);

        String endDateStr = before +(minInt-moMin);

        Date date5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDateStr+":00");
        Date startTime = getDateMinutesAgo(date5,15);
        Date endTime = getDateMinutesAgo(date5,10);

        logger.info("开始统计report_line_code,startTime[{}],endTime[{}]",startTime,endTime);

        //先删除，防止重跑
        stastisticReportLineMapper.deleteReportLineCode(endTime);
        List<ReportLineCode> list = stastisticReportLineMapper.selectLineHangupCodeReport(startTime,endTime);

        if(list!=null && list.size()>0){

            //todo 需要优化
            for(ReportLineCode reportLineCode:list){
                reportLineCode.setCreateTime(endTime);
            }

            stastisticReportLineMapper.insertReportLineCodeBatch(list);
        }

        logger.info("结束统计report_line_code,startTime[{}],endTime[{}]",startTime,endTime);

        logger.info("开始统计report_line_status,startTime[{}],endTime[{}]",startTime,endTime);
        //先删除，防止重跑
        stastisticReportLineMapper.deleteReportLineStatus(endTime);
        List<ReportLineStatus> listStatus = stastisticReportLineMapper.selectReportLineStatusFromCode(endTime);
        if(listStatus!=null && listStatus.size()>0){
            stastisticReportLineMapper.insertReportLineStatusBatch(listStatus);
        }
        logger.info("结束统计report_line_status,startTime[{}],endTime[{}]",startTime,endTime);
    }

    /**
     * 获取几分钟之前的时间日期
     */
    public Date getDateMinutesAgo(Date date,int minutesAgo){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE,-minutesAgo);
        return c.getTime();
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
            if(errorSumMapList!=null && errorSumMapList.size()>0){
                Map errorSumMap = new HashMap();
                for(Map map :errorSumMapList){
                    errorSumMap.put(map.get("hangup_code"),map.get("totalCalls"));
                }

                List<Map> errorNumsMapList = stastisticReportLineMapper.getLineHangupCodeErrorNums(lineId, startTime, enTime);
                List<Map> errorNumsMapListCancel = stastisticReportLineMapper.getLineHangupCodeErrorNumsCancel(lineId, startTime, enTime);
                if(errorNumsMapList!=null && errorNumsMapListCancel != null && errorNumsMapListCancel.size()>0){
                    errorNumsMapList.addAll(errorNumsMapListCancel);
                }

                overViewMap.put("errorSum",errorSumMap);
                overViewMap.put("errorNums",errorNumsMapList);
            }

            Map resultMap = new HashMap();
            resultMap.put("callRecordSum",overViewMap);
            return resultMap;
        }

        return null;
    }
}
