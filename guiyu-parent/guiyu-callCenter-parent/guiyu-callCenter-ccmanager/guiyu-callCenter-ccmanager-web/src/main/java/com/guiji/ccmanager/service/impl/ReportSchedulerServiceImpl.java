package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.ReportCallDayMapper;
import com.guiji.callcenter.dao.StatisticMapper;
import com.guiji.callcenter.dao.entity.ReportCallDay;
import com.guiji.callcenter.dao.entity.ReportCallHour;
import com.guiji.ccmanager.service.ReportSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    ReportCallDayMapper reportCallDayMapper;


    @Override
    public void reportCallDayScheduler() {

        statisticMapper.deleteReportCallDay();

        List<ReportCallDay> list30 = statisticMapper.countReportCallDayDruation30();
        List<ReportCallDay> list10 = statisticMapper.countReportCallDayDruation10();
        List<ReportCallDay> list5 = statisticMapper.countReportCallDayDruation5();
        List<ReportCallDay> list0 = statisticMapper.countReportCallDayDruation0();

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

        statisticMapper.insertReportCallDay(list);

    }

    @Override
    public void reportCallHourScheduler() {
        List<ReportCallHour>  listOut = statisticMapper.countReportCallHourOut();
        List<ReportCallHour> listConnect = statisticMapper.countReportCallHourConnect();
        if(listOut!=null && listOut.size()>0 && listConnect!=null && listConnect.size()>0){
            for(ReportCallHour out:listOut){
                for(ReportCallHour connect:listConnect){
                    if(out.getCustomerId().equals(connect.getCustomerId())){
                        out.setConnectCount(connect.getConnectCount());
                    }
                }
            }
        }

        statisticMapper.insertReportCallHour(listOut);

    }


}
