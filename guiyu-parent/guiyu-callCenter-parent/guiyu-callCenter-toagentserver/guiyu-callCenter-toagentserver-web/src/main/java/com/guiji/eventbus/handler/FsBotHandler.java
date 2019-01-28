package com.guiji.eventbus.handler;

import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.config.FsBotConfig;
import com.guiji.entity.*;
import com.guiji.eventbus.SimpleEventSender;
import com.guiji.eventbus.event.*;
import com.guiji.fs.FsManager;
import com.guiji.fs.pojo.AgentState;
import com.guiji.service.*;
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
            if(!event.isCallToAgent()) {
                log.info("未知的应答事件[{}]，跳过处理", event);
                return;
            }

            CallPlan callPlan = callPlanService.findByCallId(event.getSeqId(), event.getCallDirection());
            if (!event.isCallToAgent()) {
                log.warn("该接听事件不属于转人工或者asterisk送的字段不全，忽略");
                return;
            }

            //判断座席组是否有座席成员
            List<Agent> agents = tierService.findAgentsByQueueId(event.getQueueId());
            if (agents == null && agents.size() == 0) {
                doWithUnWorkTimeVistor(event, callPlan);
                log.warn("当前座席组中没有分配座席，提示退出，queueId:[{}]", event.getQueueId());
                return;
            }

            //判断当前队列是否处于可服务状态
            if (!isQueueInService(event.getQueueId(), agents)) {
                //TODO:如果当前没有座席处于服务状态，如何处理
                log.warn("当前座席组无法接听电话，需要提示crm进行登录, queueId:[{}]", event.getQueueId());
            }

            //将当前呼入电话转入座席组中
            fsManager.transferToAgentGroup(event.getUuid(), event.getCallerNum(), event.getQueueId().toString());
        }catch (Exception ex){
            log.warn("在处理channel_answer时出现异常", ex);
        }
    }

    //判断当前队列是否处于可服务器状态
    private boolean isQueueInService(Long queueId, List<Agent> agents) {
        for(Agent user: agents){
            //需要满足座席处于签入状态，并且登录
            if(user.getUserState() == EUserState.ONLINE.ordinal()){
                if(user.getAnswerType() == EAnswerType.MOBILE.ordinal()){
                    log.info("有座席处于在线，并且手机接听，则处于可服务状态, userId[{}]", user.getUserId());
                    return true;
                }else if(user.getAnswerType() == EAnswerType.WEB.ordinal()){
                    //判断verto是否在线
                    AgentInfo agent = fsManager.getAgent(user.getUserId().toString());
                    if(agent.getState() == AgentState.CheckIn
                           && agent.getState() == AgentState.InCall
                            && agent.getState() == AgentState.InProgress){
                        log.info("当前队列[{}]有座席[{}]verto处于登录状态，可以接听电话", queueId, user.getUserId());
                        return true;
                    }
                }else{
                    log.warn("未知的座席接听类型[{}]", user.getAnswerType());
                }
            }
        }

        log.info("队列[{}]目前没有可以接听电话的座席", queueId);
        return false;
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
