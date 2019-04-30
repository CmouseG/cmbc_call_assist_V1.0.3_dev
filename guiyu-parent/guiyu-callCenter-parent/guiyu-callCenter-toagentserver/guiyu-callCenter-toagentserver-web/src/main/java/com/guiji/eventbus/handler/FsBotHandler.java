package com.guiji.eventbus.handler;

import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.config.FsBotConfig;
import com.guiji.constant.Constant;
import com.guiji.entity.*;
import com.guiji.eventbus.SimpleEventSender;
import com.guiji.eventbus.event.*;
import com.guiji.fs.FsManager;
import com.guiji.fs.pojo.AgentState;
import com.guiji.service.*;
import com.guiji.util.DateUtil;
import com.guiji.web.request.AgentInfo;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
@Slf4j
public class FsBotHandler {
    @Autowired
    FsBotConfig fsBotConfig;

    @Autowired
    FsManager fsManager;

    @Autowired
    CallPlanService callPlanService;

    @Autowired
    SimpleEventSender simpleEventSender;

    @Autowired
    QueueService queueService;

    @Autowired
    AgentService userService;

    @Autowired
    TierService tierService;

    @Autowired
    CallOutPlanService callOutPlanService;

    @PostConstruct
    public void init(){
        simpleEventSender.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleAnswer(ChannelAnswerEvent event){
        log.info("收到ChannelAnswer事件[{}], 准备进行处理", event);

        try {
            if(!event.isCallToAgent() && !event.getIsXiehu()) {
                log.info("未知的应答事件[{}], 非转人工或协呼，跳过处理", event);
                return;
            }

            String seqId = event.getSeqId();
            String callid;
            Integer orgId;
            try {
                String[] arr = seqId.split(Constant.UUID_SEPARATE);
                callid = arr[0];
                orgId = Integer.valueOf(arr[1]);
            }catch (Exception e){
                return;
            }

            CallPlan callPlan = callPlanService.findByCallId(callid, event.getCallDirection(),orgId);

            if(event.isCallToAgent()){
                log.debug("开始处理转人工事件[{}]", event);
                //判断座席组是否有座席成员
                List<Agent> agents = tierService.findAgentsByQueueId(event.getQueueId());
                if (agents == null && agents.size() == 0) {
                    doWithUnWorkTimeVistor(event, callPlan);
                    log.warn("当前座席组中没有分配座席，提示退出，queueId:[{}]", event.getQueueId());
                    return;
                }

                //将当前呼入电话转入座席组中
                fsManager.transferToAgentGroup(event.getUuid(), event.getCallerNum(), event.getQueueId().toString());
            }else{
                handleXiehu(event, callPlan);
            }
        }catch (Exception ex){
            log.warn("在处理channel_answer时出现异常", ex);
        }
    }

    /**
     * 处理协呼应答事件
     * @param event
     * @param callPlan
     */
    private void handleXiehu(ChannelAnswerEvent event, CallPlan callPlan) {
        //构建AgentAnswerEvent，便于后续事件流转
        AgentAnswerEvent agentEvent = new AgentAnswerEvent();
        agentEvent.setCallDirection(event.getCallDirection());
        agentEvent.setAgentAnswerTime(DateUtil.getCurrentDateTime());
        agentEvent.setAgentId(event.getAgentId());
        agentEvent.setAgentUuid(event.getUuid());
        agentEvent.setCustomerNum(event.getCallerNum());
        agentEvent.setCustomerUuid(event.getOtherLegUuid());
        agentEvent.setSeqId(event.getSeqId());

        simpleEventSender.sendEvent(agentEvent);
    }

    /**
     * 处于非工作时间来电
     * @param event
     */
    private void doWithUnWorkTimeVistor(ChannelAnswerEvent event, CallPlan callPlan) {
        //播放录音提示 未处于工作时间，则播放语音提示“对不起，坐席前忙”，跳过后续处理
        fsManager.playToChannel(event.getUuid(), "notworktime.wav");
        fsManager.scheduleHangup(event.getUuid(), 15D);
    }
}
