package com.guiji.calloutserver.eventbus.handler;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.enm.ECallDetailType;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.entity.AIRequest;
import com.guiji.calloutserver.eventbus.event.AsrCustomerEvent;
import com.guiji.calloutserver.fs.LocalFsServer;
import com.guiji.calloutserver.helper.ChannelHelper;
import com.guiji.calloutserver.manager.AIManager;
import com.guiji.calloutserver.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AsrHandler {
    @Autowired
    AIManager aiManager;
    @Autowired
    ChannelHelper channelHelper;
    @Autowired
    CallOutPlanService callOutPlanService;
    @Autowired
    CallOutDetailService callOutDetailService;
    @Autowired
    CallOutDetailRecordService callOutDetailRecordService;
    @Autowired
    ErrorMatchService errorMatchService;
    @Autowired
    AsyncEventBus asyncEventBus;
    @Autowired
    LocalFsServer localFsServer;
    @Autowired
    CallOutRecordService callOutRecordService;

    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    private static Cache<String, Integer> caches;

    @PostConstruct
    public void init(){
        caches = CacheBuilder.newBuilder()
                .maximumSize(20000)
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build();
    }

    private final static String ASR_BLANK = "asr_blank_";

    @Subscribe
    @AllowConcurrentEvents
    public void handleCustomerAsrEvent(AsrCustomerEvent event) {
        try {
            CallOutPlan callPlan = event.getCallPlan();
            if (callPlan.getCallState() == ECallState.agent_answer.ordinal()) {
                handleAfterAgentAsr(callPlan, event);
            } else {
                handleNormalAsr(callPlan, event);
            }
        } catch (Exception ex) {
            log.warn("处理AsrCustomerEvent出现异常", ex);
        }
    }

    /**
     * 处理转人工之后的客户asr处理
     *
     * @param event
     */
    private void handleAfterAgentAsr(CallOutPlan callPlan, AsrCustomerEvent event) {
        log.info("收到转人工后的客户AliAsr事件[{}], 准备进行处理", event);
    }

    /**
     * 处理正常情况下的客户asr处理
     *
     * @param event
     */
    private void handleNormalAsr(CallOutPlan callPlan, AsrCustomerEvent event) {
        log.info("收到正常情况下的客户AliAsr事件[{}], 准备进行处理", event);
        try {
            //判断通道状态，如果还未接听，则进行F类判断
            if (callPlan.getCallState() == ECallState.init.ordinal() ||
                    callPlan.getCallState() == ECallState.call_prepare.ordinal() ||
                    callPlan.getCallState() == ECallState.make_call.ordinal() ||
                    callPlan.getCallState() == ECallState.progress.ordinal()) {
                log.warn("通道[{}]还未接听，需要对收到的asr进行F类识别", event.getUuid());
                //进行F类识别
                doWithErrorResponse(callPlan, event);
                return;
            }

            //如果当前正处于转人工的状态，则忽略该asr识别
            if (callPlan.getCallState() == ECallState.to_agent.ordinal()) {
                log.warn("当前通话[{}]正在转人工，忽略掉客户说话asr[{}]", callPlan.getCallId(), event.getAsrText());
                return;
            }

            //判断当前通道是否被锁定，如果锁定的话，则跳过后续处理
            if (channelHelper.isChannelLock(event.getUuid())) {
                log.info("通道媒体[{}]已被锁定，跳过该次识别请求", event.getUuid());
                return;
            }

            AIRequest aiRequest = new AIRequest(event);

            aiManager.sendAiRequest(aiRequest,callPlan);

            if(dealWithError3002(callPlan.getCallId().toString(), event.getAsrText(), callPlan.getOrgId())){
                event.setAsrText("线路异常3002");
            }

            buildCallOutDetail(callPlan.getCallId(), event, callPlan.getOrgId());

        } catch (Exception ex) {
            log.warn("handleNormalAsr: 在处理AliAsr时出错异常", ex);
        }
    }

    /**
     * 若收到的事件中，识别文本为空，则启动计数器
     * 若是计数器等于3，处理如下：
     * 设置::  意向标签->N，意向备注->“线路异常3002”
     * 挂断通话
     * 收到的事件中文本不为空，则删除计数器
     *
     * 线路异常3002返回true
     */
    public boolean dealWithError3002(String callId, String asrText, Integer orgId){
        String key = ASR_BLANK+callId;
        if(StringUtils.isBlank(asrText)){ //识别为空
            Integer value =  caches.getIfPresent(key);
            if(value!=null){
                if(value<2){
                    caches.put(key,value+1);
                }else if(value>=2 && value <100){
                    log.info("第三次空asr识别，挂断电话，callId[{}]",callId);
                    caches.put(key,100);
                    localFsServer.hangup(callId+Constant.UUID_SEPARATE+orgId); //挂断电话
                    //修改意向标签和意向备注
                    CallOutPlan callOutPlan = new CallOutPlan();
                    callOutPlan.setCallId(new BigInteger(callId));
                    callOutPlan.setOrgId(orgId);
                    callOutPlan.setAccurateIntent("N");
                    callOutPlan.setReason("线路异常3002");
                    callOutPlanService.update(callOutPlan);
                    return true;
                }
            }else{
                caches.put(key,1);
            }
        }else{
            caches.put(key,100);
        }
        return false;
    }


    /**
     * 进行F类识别
     *
     * @param callPlan
     * @param event
     */
    private void doWithErrorResponse(CallOutPlan callPlan, AsrCustomerEvent event) {
        if (Strings.isNullOrEmpty(event.getAsrText())) {
            log.warn("F类识别失败，因asr识别结果为空");
            return;
        }

        ErrorMatch errorMatch = errorMatchService.findError(event.getAsrText());
        if (errorMatch != null) {
            log.info("F类识别结果为[{}]", errorMatch);
            callPlan.setAccurateIntent("F");
            callPlan.setReason(errorMatch.getErrorName());
            callOutPlanService.update(callPlan);

            //保存录音文件信息
            CallOutRecord callOutRecord = new CallOutRecord();
            callOutRecord.setCallId(callPlan.getCallId());
            callOutRecord.setRecordFile(event.getFileName());
            callOutRecordService.update(callOutRecord);

            //判断是否已经做过F类判断，如果做过，则直接挂断该通话
            if(errorMatch.getErrorType()>=0){
                log.info("触发F类识别，需要手工挂断[{}]", callPlan.getCallId());
                localFsServer.hangup(callPlan.getCallId().toString()+Constant.UUID_SEPARATE+callPlan.getOrgId());
            }
        }
    }


    public void buildCallOutDetail(BigInteger callId, AsrCustomerEvent event ,Integer orgId) {

        //开场白之前的asr识别不记录，识别太灵敏了，导致太多无用通话记录
        if (StringUtils.isNotBlank(event.getAsrText())) {
            CallOutDetail callDetail = new CallOutDetail();
            callDetail.setCallId(callId);
            callDetail.setOrgId(orgId);

//            Long duration = event.getAsrDuration();
//            if (event.getAsrDuration() == null || event.getAsrDuration() <= 0) {
//                duration = 1000L;
//            }

            //估算用户说话时间 = 当前时间 - asr识别时长
            LocalDateTime currentTime = LocalDateTime.now();
//            currentTime = currentTime.minus(duration, ChronoField.MILLI_OF_DAY.getBaseUnit());
            callDetail.setCustomerSayTime(java.sql.Timestamp.valueOf(currentTime));

            callDetail.setCustomerSayText(event.getAsrText());
            callDetail.setAsrDuration(Math.toIntExact(event.getAsrDuration()));

            callDetail.setTotalDuration(Math.toIntExact(event.getAsrDuration()));
//            String detailId = IdGenUtil.uuid();
//            callDetail.setCallDetailId(detailId);
            callDetail.setCallDetailType(ECallDetailType.NORMAL.ordinal());
            callOutDetailService.save(callDetail);

            CallOutDetailRecord calloutDetailRecord = new CallOutDetailRecord();
            calloutDetailRecord.setCallDetailId(callDetail.getCallDetailId());
            calloutDetailRecord.setCallId(callId);
            calloutDetailRecord.setCustomerRecordFile(event.getFileName());

            callOutDetailRecordService.save(calloutDetailRecord);
        }

    }

}
