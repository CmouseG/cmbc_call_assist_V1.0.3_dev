package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.ReportCallDayMapper;
import com.guiji.callcenter.dao.ReportCallTodayMapper;
import com.guiji.callcenter.dao.StatisticMapper;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.callcenter.daoNoSharing.LineRateMapper;
import com.guiji.callcenter.daoNoSharing.StastisticReportLineMapper;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.ccmanager.service.ReportSchedulerService;
import com.guiji.ccmanager.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/6 0006 15:52
 * @Description:
 */
@Service
public class ReportSchedulerServiceImpl implements ReportSchedulerService {

    @Autowired
    StatisticMapper statisticMapper;
    @Autowired
    StastisticReportLineMapper stastisticReportLineMapper;
    @Autowired
    ReportCallTodayMapper reportCallTodayMapper;
    @Autowired
    ReportCallDayMapper reportCallDayMapper;
    @Autowired
    LineRateMapper lineRateMapper;
    @Autowired
    AuthService authService;

    @Override
    @Transactional
    public void reportCallDayScheduler() {

        List<Integer> orgIds = authService.getAllOrgIds();

        statisticMapper.deleteReportCallDay();
        //sharding jdbc 不支持 case when操作，所以分开来操作
        List<ReportCallDay> list30 = statisticMapper.countReportCallDayDruation30(orgIds);
        List<ReportCallDay> list10 = statisticMapper.countReportCallDayDruation10(orgIds);
        List<ReportCallDay> list5 = statisticMapper.countReportCallDayDruation5(orgIds);
        List<ReportCallDay> list0 = statisticMapper.countReportCallDayDruation0(orgIds);

        List<ReportCallDay> list = new ArrayList<>();
        if(list30!=null && list30.size() >0){
            list.addAll(list30);
        }
        if(list30!=null && list30.size() >0){
            list.addAll(list10);
        }
        if(list30!=null && list30.size() >0){
            list.addAll(list5);
        }
        if(list30!=null && list30.size() >0){
            list.addAll(list0);
        }

        if(list!=null && list.size()>0){
            statisticMapper.insertReportCallDay(list);
        }

    }

    @Override
    @Transactional
    public void reportCallTodayScheduler() {

        List<Integer> orgIds = authService.getAllOrgIds();
        //sharding jdbc 不支持 case when操作，所以分开来操作
        List<ReportCallDay> list30 = statisticMapper.countReportCallToday30(orgIds);
        List<ReportCallDay> list10 = statisticMapper.countReportCallToday10(orgIds);
        List<ReportCallDay> list5 = statisticMapper.countReportCallToday5(orgIds);
        List<ReportCallDay> list0 = statisticMapper.countReportCallToday0(orgIds);

        List<ReportCallDay> list = new ArrayList<>();
        if(list30!=null && list30.size() >0){
            list.addAll(list30);
        }
        if(list30!=null && list30.size() >0){
            list.addAll(list10);
        }
        if(list30!=null && list30.size() >0){
            list.addAll(list5);
        }
        if(list30!=null && list30.size() >0){
            list.addAll(list0);
        }
        statisticMapper.deleteCallTodayTruncate();
        if(list!=null && list.size()>0){
            statisticMapper.insertReportCallToday(list);
        }

    }

    @Override
    @Transactional
    public void reportCallHourScheduler() {
        List<Integer> orgIds = authService.getAllOrgIds();
        statisticMapper.deleteReportCallHour();
        List<ReportCallHour>  listOut = statisticMapper.countReportCallHourOut(orgIds);
        List<ReportCallHour> listConnect = statisticMapper.countReportCallHourConnect(orgIds);
        if(listOut!=null && listOut.size()>0){
            if(listConnect!=null && listConnect.size()>0){
                for(ReportCallHour out:listOut){
                    for(ReportCallHour connect:listConnect){
                        if(out.getOrgCode().equals(connect.getOrgCode()) && out.getTempid().equals(connect.getTempid()) &&
                                out.getUserId().intValue()==connect.getUserId().intValue()){
                            out.setConnectCount(connect.getConnectCount());
                        }
                    }
                }
            }
            statisticMapper.insertReportCallHour(listOut);
        }
    }

    @Override
    @Transactional
    public void reportCallTodayTruncate() {
//        statisticMapper.reportCallTodayTruncate();
        //清空30天之前的数据 call_line_result
        stastisticReportLineMapper.deleteCallLineResultDaysAgo(30);
        //清空一年之前的数据 report_line_code
        stastisticReportLineMapper.deleteReportLineCodeDaysAgo(365);
        //清空一年之前的数据 report_line_status
        stastisticReportLineMapper.deleteReportLineStatusDaysAgo(365);
    }

    @Override
    public Boolean isTruncateSuccess() {
        ReportCallTodayExample example = new ReportCallTodayExample();
        example.createCriteria()
                .andCallDateLessThan(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        int count = reportCallTodayMapper.countByExample(example);
        if(count==0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void reportCallTodayTruncateBefore() {
        ReportCallTodayExample example = new ReportCallTodayExample();
        example.createCriteria()
                .andCallDateLessThan(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        reportCallTodayMapper.deleteByExample(example);
    }

    @Override
    public boolean isDaySheduleSuccess() {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date date=cal.getTime();//昨天的日期

        ReportCallDayExample example = new ReportCallDayExample();
        example.createCriteria()
                .andCallDateEqualTo(new SimpleDateFormat("yyyy-MM-dd").format(date));
        int count =  reportCallDayMapper.countByExample(example);
        if(count>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void reportCallLineDayReportScheduler() {

        Date endTime =  DateUtils.getDayBegin();
        List<CallLineDayReport> list =  lineRateMapper.countDayReport(endTime);
        if(list !=null &&  list.size()>0){
            lineRateMapper.insertCallLineDayReportBatch(list);
        }
    }
}
