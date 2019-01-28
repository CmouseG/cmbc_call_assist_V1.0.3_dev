package com.guiji.eventbus.handler;

import com.google.common.base.Strings;
import com.guiji.entity.*;
import com.guiji.eventbus.SimpleEventSender;
import com.guiji.eventbus.event.AgentAnswerEvent;
import com.guiji.eventbus.event.AsrAgentEvent;
import com.guiji.eventbus.event.AsrCustomerEvent;
import com.guiji.fs.FsManager;
import com.guiji.service.CallDetailService;
import com.guiji.service.CallPlanService;
import com.guiji.util.DateUtil;
import com.google.common.base.Preconditions;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ToAgentHandler {
    @Autowired
    FsManager fsManager;

    @Autowired
    CallPlanService callPlanService;

    @Autowired
    CallDetailService callDetailService;

    @Autowired
    SimpleEventSender simpleEventSender;

    @PostConstruct
    public void init(){
        simpleEventSender.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleAgentAnswerEvent(AgentAnswerEvent event){
        log.info("收到AgentAnswerEvent[{}]", event);
        try{
            CallPlan callPlan = callPlanService.findByCallId(event.getSeqId(), event.getCallDirection());
            Preconditions.checkNotNull(callPlan, "not exist callPlan:" + event.getCustomerUuid());

            callPlan.setAgentId(event.getAgentId());
            callPlan.setAgentGroupId(event.getAgentGroupId());
            callPlan.setAgentAnswerTime(new Date());
            callPlan.setAgentChannelUuid(event.getAgentUuid());
            callPlan.setCallState(CallState.agent_answer.ordinal());
            callPlan.setAccurateIntent("B");

            callPlanService.update(callPlan);

            //读取之前的机器人对话内容，然后推送到前台
            List<CallDetail> callDetailList = callDetailService.findByCallPlanId(event.getSeqId(), event.getCallDirection());
            Preconditions.checkState(callDetailList!=null && callDetailList.size()>0, "null call detail to customer:" + event.getCustomerUuid());
            log.info("读取到的机器人对话内容数量为[{}],准备推送到verto", callDetailList.size());
            for (CallDetail callDetail : callDetailList) {
                VChatMsg chatMsg;
                if(!Strings.isNullOrEmpty(callDetail.getBotAnswerText())){
                    //发送机器人说话内容
                    chatMsg = VChatMsg.customerInstance();
                    chatMsg.setAsrtext(callDetail.getBotAnswerText());
                    chatMsg.setCallernum(callPlan.getPhoneNum());
                    chatMsg.setAsrtime(DateUtil.toString(callDetail.getBotAnswerTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
                    fsManager.vchat(event.getAgentId(), chatMsg.toBase64());
                }else if(!Strings.isNullOrEmpty(callDetail.getCustomerSayText())){
                    //发送客户说话内容
                    chatMsg = VChatMsg.aiInstance();
                    chatMsg.setAsrtext(callDetail.getCustomerSayText());
                    chatMsg.setCallernum(callPlan.getPhoneNum());
                    chatMsg.setAsrtime(DateUtil.toString(callDetail.getCustomerSayTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
                    fsManager.vchat(event.getAgentId(), chatMsg.toBase64());
                }
            }
        }catch (Exception ex){
            log.warn("处理AgentAnswerEvent事件出现异常", ex);
        }
    }


    /**
     * 处理转人工收到的客户asr消息
     * @param event
     */
    @Subscribe
    @AllowConcurrentEvents
    public void handleCustomerAsrEvent(AsrCustomerEvent event){
            log.info("收到AsrCustomerEvent，准备向座席发送消息");
            
            CallPlan callPlan = event.getCallPlan();
            CallDetail callDetail = new CallDetail();
            callDetail.setCallId(callPlan.getCallId());
            callDetail.setCustomerSayText(event.getAsrText());
            callDetail.setCustomerSayTime(DateUtil.getDateByDateAndFormat(event.getAsrStartTime(),DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
            callDetail.setCustomerRecordFile(event.getFilePath());

            callDetail.setCallDetailType(ECallDetailType.TOAGENT_CUSTOMER_SAY.ordinal());
            callDetail.setTotalDuration(event.getAsrDuration().intValue());
            callDetail.setAsrDuration(event.getAsrDuration().intValue());

            callDetailService.insert(callDetail, event.getCallDirection());

            String agentNum = event.getCallPlan().getAgentId()+"";
            String asrText = event.getAsrText();
        try{
            //发送客户说话内容
            VChatMsg customerMsg = VChatMsg.customerInstance();
            customerMsg.setAgentnum(agentNum);
            customerMsg.setCallernum(event.getCallPlan().getPhoneNum());
            customerMsg.setAsrtext(asrText);
            customerMsg.setAsrtime(event.getAsrStartTime());

            fsManager.vchat(agentNum, customerMsg.toBase64());
        }catch (Exception ex){
            log.warn("在处理AsrCustomerAfterAgentEvent时出现异常", ex);
        }
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleAgentAsrEvent(AsrAgentEvent event){
        log.info("收到AsrAgentEvent[{}]", event);

        CallPlan callPlan = event.getCallPlan();

        CallDetail callDetail = new CallDetail();
        callDetail.setCallId(callPlan.getCallId());
        callDetail.setCallDetailType(ECallDetailType.TOAGENT_AGENT_ANSWER.ordinal());
        callDetail.setAsrDuration(event.getAsrDuration().intValue());
        callDetail.setTotalDuration(event.getAsrDuration().intValue());
        callDetail.setAgentAnswerTime(DateUtil.getDateByDateAndFormat(event.getAsrStartTime(),DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
        callDetail.setAgentAnswerText(event.getAsrText());
        callDetail.setAgentRecordFile(event.getFilePath());
        callDetailService.insert(callDetail, event.getCallDirection());

        try{
            //发送座席说话内容
            VChatMsg customerMsg = VChatMsg.agentInstance();
            customerMsg.setAgentnum(callPlan.getAgentId());
            customerMsg.setCallernum(callPlan.getPhoneNum());
            customerMsg.setAsrtext(event.getAsrText());
            customerMsg.setAsrtime(event.getAsrStartTime());
            fsManager.vchat(callPlan.getAgentId(), customerMsg.toBase64());
        }catch (Exception ex){
            log.warn("在处理AsrAgentEvent事件时出现异常", ex);
        }
    }
}
