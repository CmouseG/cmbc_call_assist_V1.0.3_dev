package com.guiji.calloutserver.eventbus.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.entity.ChargeCallPlan;
import com.guiji.calloutserver.eventbus.event.StatisticReportEvent;
import com.guiji.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 计费，直接推到队列里面
 */
@Slf4j
@Service
public class ChargeHandler {

    @Autowired
    private AmqpTemplate rabbitTemplate;

/*    @Autowired
    AsyncEventBus asyncEventBus;

    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    @Async
    public void handleAfterCall(StatisticReportEvent statisticReportEvent) {

        CallOutPlan callOutPlan = statisticReportEvent.getCallPlan();*/
    @Async
    public void handleAfterCall(CallOutPlan callOutPlan) {
        if(callOutPlan.getBillSec()!=null && callOutPlan.getBillSec().intValue()>0){
            ChargeCallPlan chargeCallPlan = new ChargeCallPlan();
            chargeCallPlan.setUserId(callOutPlan.getCustomerId());
            chargeCallPlan.setBillSec(callOutPlan.getBillSec());
            chargeCallPlan.setEndTime(callOutPlan.getHangupTime());
            chargeCallPlan.setBeginTime(callOutPlan.getCallStartTime());
            chargeCallPlan.setPhone(callOutPlan.getPhoneNum());
            chargeCallPlan.setLineId(callOutPlan.getLineId());

            rabbitTemplate.convertAndSend("billing.ACCTCHARGING", JsonUtils.bean2Json(chargeCallPlan));

            log.info("---将计费对象 chargeCallPlan[{}] 推到mq",chargeCallPlan);
        }
    }

}
