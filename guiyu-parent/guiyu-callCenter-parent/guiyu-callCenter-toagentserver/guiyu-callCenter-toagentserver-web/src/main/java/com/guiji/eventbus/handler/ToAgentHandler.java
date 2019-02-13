package com.guiji.eventbus.handler;

import com.google.common.base.Strings;
import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.entity.*;
import com.guiji.eventbus.SimpleEventSender;
import com.guiji.eventbus.event.*;
import com.guiji.fs.FsManager;
import com.guiji.helper.VChatMsgHelper;
import com.guiji.service.AgentService;
import com.guiji.service.CallDetailService;
import com.guiji.service.CallPlanService;
import com.guiji.service.PhoneService;
import com.guiji.util.DateUtil;
import com.google.common.base.Preconditions;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.guiji.web.response.QueryRecordInDetail;
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

    @Autowired
    AgentService agentService;

    @Autowired
    PhoneService phoneService;

    @PostConstruct
    public void init(){
        simpleEventSender.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleVertoLogin(VertoLoginEvent vertoLoginEvent){
        log.info("收到verto登录事件[{}]", vertoLoginEvent);
        Agent agent = vertoLoginEvent.getAgent();
        if(agent.getAnswerType()==EAnswerType.WEB.ordinal()){
            log.info("verto登录之后，将[{}]状态设置为在线", agent.getUserId());
            agentService.agentStateByVerto(EUserState.ONLINE,agent);
        }
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleVertoDisconnect(VertoDisconnectEvent event){
        log.info("收到座席verto断开事件[{}]", event);
        Agent agent = event.getAgent();
        if(agent.getAnswerType()==EAnswerType.WEB.ordinal()){
            log.info("verto断开之后，将[{}]状态设置为离线", agent.getUserId());
            agentService.agentStateByVerto(EUserState.OFFLINE,agent);
        }
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
                    chatMsg = VChatMsg.aiInstance();
                    chatMsg.setAsrtext(callDetail.getBotAnswerText());
                    chatMsg.setCallernum(callPlan.getPhoneNum());
                    chatMsg.setAsrtime(DateUtil.toString(callDetail.getBotAnswerTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
                    fsManager.vchat(event.getAgentId(), chatMsg.toBase64());
                }else if(!Strings.isNullOrEmpty(callDetail.getCustomerSayText())){
                    //发送客户说话内容
                    chatMsg = VChatMsg.customerInstance();
                    chatMsg.setAsrtext(callDetail.getCustomerSayText());
                    chatMsg.setCallernum(callPlan.getPhoneNum());
                    chatMsg.setAsrtime(DateUtil.toString(callDetail.getCustomerSayTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
                    fsManager.vchat(event.getAgentId(), chatMsg.toBase64());
                }
            }

            //在机器人对话消息结束后，发送结束消息
            if(callDetailList.size()>0){
                fsManager.vchat(event.getAgentId(), VChatMsg.aiEndInstance().toBase64());
            }

            //发送通话基本信息给verto终端
            sendPhoneInfo(event, callPlan);
        }catch (Exception ex){
            log.warn("处理AgentAnswerEvent事件出现异常", ex);
        }
    }

    /**
     * 发送通话基本信息给verto终端
     * @param event
     * @param callPlan
     */
    private void sendPhoneInfo(AgentAnswerEvent event, CallPlan callPlan) {
        log.info("开始给verto终端[{}]发送通话信息", event.getAgentId());
        QueryRecordInDetail queryRecordInDetail = new QueryRecordInDetail();
        queryRecordInDetail.setPhone(callPlan.getPhoneNum());
        queryRecordInDetail.setLabel(callPlan.getAccurateIntent());
        queryRecordInDetail.setArea(phoneService.findLocationByPhone(callPlan.getPhoneNum()));
        queryRecordInDetail.setBillSec(callPlan.getBillSec());

        String answerTime = DateUtil.toString(callPlan.getAnswerTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC);
        queryRecordInDetail.setAnswerTime(answerTime);

        queryRecordInDetail.setCallrecordId(callPlan.getPlanUuid());

        log.info("构建好的phoneinfo为[{}], 准备发送给座席[{}]", queryRecordInDetail, event.getAgentId());
        fsManager.vchat(event.getAgentId(), VChatMsgHelper.buildPhoneInfoMsg(queryRecordInDetail));
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
