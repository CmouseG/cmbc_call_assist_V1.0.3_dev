package com.guiji.calloutserver.eventbus.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.ReportCallTodayMapper;
import com.guiji.callcenter.dao.StatisticMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.eventbus.event.StatisticReportEvent;
import com.guiji.calloutserver.service.CallOutPlanService;
import com.guiji.calloutserver.service.SendNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;

@Slf4j
@Service
public class StatisticReportHandler {

    @Autowired
    ReportCallTodayMapper reportCallTodayMapper;

    @Autowired
    AsyncEventBus asyncEventBus;

    @Autowired
    StatisticMapper statisticMapper;
    @Autowired
    SendNoticeService sendNoticeService;
    @Autowired
    ChargeHandler chargeHandler;
    @Autowired
    CallOutPlanService callOutPlanService;

    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleAfterCall(StatisticReportEvent statisticReportEvent) {
        // todo 可优化，对数据库操作太多
        CallOutPlan callOutPlan = callOutPlanService.findByCallId(statisticReportEvent.getCallPlan().getCallId(),
                statisticReportEvent.getCallPlan().getOrgId());
        //计费
        chargeHandler.handleAfterCall(callOutPlan);
        String intent = callOutPlan.getAccurateIntent();
        //发送消息通知
        if(StringUtils.isNotEmpty(intent)){
            sendNoticeService.sendNotice(callOutPlan);
        }
        //将记录插入到today表中
//        updateReportToday(callOutPlan);
    }

    /**
     * 将记录插入到today表中
     */
/*    public void updateReportToday(CallOutPlan callOutPlan){
        String intent = callOutPlan.getAccurateIntent();
        String reason = callOutPlan.getReason();
        String tempId = callOutPlan.getTempId();
        String orgCode = callOutPlan.getOrgCode();
        int durationType = 0;
        long duration = 0l;
        if(callOutPlan.getBillSec()!=null){
            duration = callOutPlan.getBillSec().longValue();
            durationType = getDurationType(duration);
        }

        String callDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        ReportCallTodayExample example = new ReportCallTodayExample();
        ReportCallTodayExample.Criteria criteria = example.createCriteria()
                .andDurationTypeEqualTo(durationType)
                .andIntentEqualTo(intent)
                .andTempidEqualTo(tempId)
                .andOrgCodeEqualTo(orgCode)
                .andCallDateEqualTo(callDate);

        //reason字段只需要记录F类是的reason即可，其他情况下的reason用不到
        if(intent != null && intent.equals("F") && StringUtils.isNotBlank(reason)){
            criteria.andReasonEqualTo(reason);
        }

        List<ReportCallToday> list = reportCallTodayMapper.selectByExample(example);

        if(list ==null || list.size()==0){
            ReportCallToday reportCallToday = new ReportCallToday();
            reportCallToday.setIntent(intent);
            if(intent != null && intent.equals("F") && StringUtils.isNotBlank(reason)){
                reportCallToday.setReason(reason);
            }else if(callOutPlan.getBillSec()>0){
                reportCallToday.setReason("已接通");
            }
            reportCallToday.setDurationAll(duration);
            reportCallToday.setDurationType(durationType);
            reportCallToday.setCallCount(1);
            reportCallToday.setOrgCode(orgCode);
            reportCallToday.setTempid(tempId);
            reportCallToday.setCallDate(callDate);
            reportCallTodayMapper.insert(reportCallToday);
        }else{
            ReportCallToday reportCallToday = list.get(0);
            reportCallToday.setDurationAll(duration);
            statisticMapper.updateTodayCountAndDruation(reportCallToday);
        }

    }*/


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
