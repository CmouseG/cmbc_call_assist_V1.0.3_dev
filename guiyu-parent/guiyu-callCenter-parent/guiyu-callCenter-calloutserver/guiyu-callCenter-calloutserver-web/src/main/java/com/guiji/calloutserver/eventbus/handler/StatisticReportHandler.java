package com.guiji.calloutserver.eventbus.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.ReportCallTodayMapper;
import com.guiji.callcenter.dao.StatisticMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.ReportCallToday;
import com.guiji.callcenter.dao.entity.ReportCallTodayExample;
import com.guiji.calloutserver.eventbus.event.StatisticReportEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
public class StatisticReportHandler {

    @Autowired
    ReportCallTodayMapper reportCallTodayMapper;

    @Autowired
    AsyncEventBus asyncEventBus;

    @Autowired
    StatisticMapper statisticMapper;

    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleAfterCall(StatisticReportEvent statisticReportEvent) {

        CallOutPlan callOutPlan = statisticReportEvent.getCallPlan();

        String intent = callOutPlan.getAccurateIntent();
        String reason = callOutPlan.getReason();
        String tempId = callOutPlan.getTempId();
        String orgCode = callOutPlan.getOrgCode();
        long duration = callOutPlan.getDuration().longValue();
        int durationType = getDurationType(duration);

        ReportCallTodayExample example = new ReportCallTodayExample();
        ReportCallTodayExample.Criteria criteria = example.createCriteria()
                .andDurationTypeEqualTo(durationType)
                .andIntentEqualTo(intent)
                .andTempidEqualTo(tempId)
                .andOrgCodeEqualTo(orgCode);

        if(intent != null && intent.equals("F") && StringUtils.isNotBlank(reason)){
            criteria.andReasonEqualTo(reason);
        }

        List<ReportCallToday> list = reportCallTodayMapper.selectByExample(example);

        if(list ==null || list.size()==0){
            ReportCallToday reportCallToday = new ReportCallToday();
            reportCallToday.setIntent(intent);
            if(intent != null && intent.equals("F") && StringUtils.isNotBlank(reason)){
                reportCallToday.setReason(reason);
            }else if(intent != null && intent.equals("W") && StringUtils.isNotBlank(reason)){
                reportCallToday.setReason(reason);
            }else{
                reportCallToday.setReason("已接通");
            }
            reportCallToday.setDurationAll(duration);
            reportCallToday.setDurationType(durationType);
            reportCallToday.setCallCount(1);
            reportCallToday.setOrgCode(orgCode);
            reportCallToday.setTempid(tempId);
            reportCallTodayMapper.insert(reportCallToday);
        }else{
            ReportCallToday reportCallToday = list.get(0);
            reportCallToday.setDurationAll(duration);
            statisticMapper.updateTodayCountAndDruation(reportCallToday);
        }


    }

    int getDurationType(long duration) {
        if (duration > 30) {
            return 3;
        } else if (duration <= 30 && duration > 10) {
            return 2;
        } else if (duration <= 10 && duration > 5) {
            return 1;
        } else {
            return 0;
        }
    }

}
