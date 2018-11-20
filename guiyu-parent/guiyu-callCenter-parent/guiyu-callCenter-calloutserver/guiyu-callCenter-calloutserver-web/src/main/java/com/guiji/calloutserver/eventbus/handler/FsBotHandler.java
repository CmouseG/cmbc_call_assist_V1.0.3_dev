package com.guiji.calloutserver.eventbus.handler;

import com.google.common.base.Strings;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.enm.EAIResponseType;
import com.guiji.calloutserver.enm.ECallDetailType;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.entity.AIInitRequest;
import com.guiji.calloutserver.entity.AIRequest;
import com.guiji.calloutserver.entity.AIResponse;
import com.guiji.calloutserver.eventbus.event.AfterCallEvent;
import com.guiji.calloutserver.eventbus.event.AsrCustomerEvent;
import com.guiji.calloutserver.eventbus.event.ChannelAnswerEvent;
import com.guiji.calloutserver.eventbus.event.ChannelHangupEvent;
import com.guiji.calloutserver.helper.ChannelHelper;
import com.guiji.calloutserver.manager.AIManager;
import com.guiji.calloutserver.manager.FsManager;
import com.guiji.calloutserver.config.FsBotConfig;
import com.guiji.calloutserver.service.CallOutDetailRecordService;
import com.guiji.calloutserver.service.CallOutDetailService;
import com.guiji.calloutserver.service.CallOutPlanService;
import com.guiji.utils.IdGenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
@Slf4j
public class FsBotHandler {
    @Autowired
    FsBotConfig fsBotConfig;

    @Autowired
    FsManager fsManager;

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
    AsyncEventBus asyncEventBus;
    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }
    
    //收到channel_answer事件，申请sellbot端口，并调用sellbot的restore方法
    @Subscribe
    public void handleAnswer(ChannelAnswerEvent event){
        log.info("收到ChannelAnswer事件[{}], 准备进行处理", event);
        //[ChannelAnswerEvent(uuid=4e293cdf-94a6-442d-9787-87c8e19fefbc, callerNum=0000000000, calledNum=18651372376, accessNum=null)], 准备进行处理
        try {
            CallOutPlan callPlan = callOutPlanService.findByCallId(event.getUuid());
            if(callPlan == null){
                log.info("未知的应答事件，事件uuid[{}]，跳过处理", event.getUuid());
                return;
            }

            AIInitRequest request = new AIInitRequest(callPlan.getCallId(), callPlan.getTempId(), callPlan.getPhoneNum(), callPlan.getCustomerId());

            Long startTime = new Date().getTime();
            AIResponse aiResponse = aiManager.applyAi(request);
            Long endTime = new Date().getTime();
            channelHelper.playAiReponse(aiResponse,false);

            //更新callplan
            callPlan.setAiId(aiResponse.getAiId());
            callPlan.setCallState(ECallState.answer.ordinal());
            callPlan.setAnswerTime(new Date());
            callOutPlanService.update(callPlan);

            //插入通话记录详情
            String detailID = IdGenUtil.uuid();
            CallOutDetail callDetail = new CallOutDetail();
            callDetail.setCallId(callPlan.getCallId());
            callDetail.setCallDetailId(detailID);
            callDetail.setAiDuration(Math.toIntExact(endTime-startTime));
            callDetail.setTotalDuration(callDetail.getAiDuration());
            callDetail.setBotAnswerText(aiResponse.getResponseTxt());
            callDetail.setBotAnswerTime(new Date());
            callDetail.setAccurateIntent(aiResponse.getAccurateIntent());
            callDetail.setReason(aiResponse.getReason());
            callDetail.setCallDetailType(ECallDetailType.INIT.ordinal());
            callOutDetailService.save(callDetail);

            //插入录音文件信息
            CallOutDetailRecord callOutDetailRecord = new CallOutDetailRecord();
            callOutDetailRecord.setCallDetailId(detailID);
            callOutDetailRecord.setCallId(callPlan.getCallId());
            callOutDetailRecord.setCallDetailId(callDetail.getCallDetailId());
            callOutDetailRecord.setBotRecordFile(aiResponse.getWavFile());
            callOutDetailRecord.setCallDetailId(callDetail.getCallDetailId());
            callOutDetailRecordService.save(callOutDetailRecord);
        }catch (Exception ex){
            log.warn("在处理ChannelAnswer时出错异常", ex);
        }
    }


    @Subscribe
    public void handleCustomerAsrEvent(AsrCustomerEvent event){
        try{
            CallOutPlan callPlan = callOutPlanService.findByCallId(event.getUuid());
            if(callPlan==null){
                log.warn("收到的CustomerAsr，没有根据uuid[{}]找到对应的callPlan，跳过处理", event.getUuid());
                return;
            }

            if(callPlan.getCallState() == ECallState.agent_answer.ordinal()){
                handleAfterAgentAsr(callPlan, event);
            }else{
                handleNormalAsr(callPlan, event);
            }
        }catch (Exception ex){
            log.warn("处理AsrCustomerEvent出现异常", ex);
        }
    }

    /**
     * 处理转人工之后的客户asr处理
     * @param event
     */
    private void handleAfterAgentAsr(CallOutPlan callPlan, AsrCustomerEvent event){
        log.info("收到转人工后的客户AliAsr事件[{}], 准备进行处理", event);
    }

    /**
     * 处理正常情况下的客户asr处理
     * @param event
     */
    private void handleNormalAsr(CallOutPlan callPlan, AsrCustomerEvent event){
        log.info("收到正常情况下的客户AliAsr事件[{}], 准备进行处理", event);
        long initStartTime = new Date().getTime();
        log.info("-------------------call state: "+callPlan.getCallState());
        try {
            //判断通道状态，如果还未接听，则跳过
            if (callPlan.getCallState() == ECallState.init.ordinal() ||
                    callPlan.getCallState() == ECallState.call_prepare.ordinal() ||
                    callPlan.getCallState() == ECallState.make_call.ordinal()) {
                log.warn("通道[{}]还未接听，暂时跳过AliAsr", event.getChannel());
                //TODO: 做F类识别
                return;
            }

            //如果当前正处于转人工的状态，则忽略该asr识别
            if(callPlan.getCallState() == ECallState.to_agent.ordinal()){
                log.warn("当前通话[{}]正在转人工，忽略掉客户说话asr[{}]", callPlan.getCallId(), event.getAsrText());
                return;
            }

            //判断当前通道是否被锁定，如果锁定的话，则跳过后续处理
            if (channelHelper.isChannelLock(event.getUuid())) {
                log.info("通道媒体[{}]已被锁定，跳过该次识别请求", event.getUuid());
                return;
            }

            //发起ai请求
            long aiStartTime = new Date().getTime();
            AIRequest aiRequest = new AIRequest(event);
            AIResponse aiResponse = aiManager.sendAiRequest(aiRequest);
            long aiEndTime = new Date().getTime();

            if (!aiResponse.isMatched()) {
                log.info("sellbot识别未匹配，识别内容为[{}]，跳过后续的放音处理。", aiRequest);
                CallOutDetail callDetail = buildCallOutDetail(callPlan, event);
                callDetail.setCallDetailType(ECallDetailType.UNMATCH.ordinal());
                callOutDetailService.save(callDetail);
                return;
            }

            CallOutDetail callDetail = buildCallOutDetail(callPlan, event);
            log.info("此时为非转人工状态下的客户识别结果，继续下一步处理");
            if(aiResponse.getAiResponseType() == EAIResponseType.NORMAL) {       //机器人正常放音
                log.info("sellbot结果为正常放音");
                playNormal(event, callPlan, aiResponse, callDetail);
            }else if(aiResponse.getAiResponseType() == EAIResponseType.TO_AGENT){      //转人工
                log.info("sellbot的结果为转人工");
                callDetail.setCallDetailType(ECallDetailType.TOAGENT_INIT.ordinal());
                playToAgent(callPlan, aiResponse);
            }else if(aiResponse.getAiResponseType() == EAIResponseType.END){           //sellbot提示结束，则在播放完毕后挂机
                log.info("sellbot的结果为通话结束");
                callDetail.setCallDetailType(ECallDetailType.END.ordinal());
                channelHelper.playFileToChannelAndHangup(event.getUuid(), aiResponse.getWavFile(), aiResponse.getWavDuration());
            }else{
                log.warn("未知的sellbot返回类型[{}], 跳过处理", aiResponse.getAiResponseType());
            }

            long stopTime = new Date().getTime();
            callDetail.setAiDuration((int)(aiEndTime - aiStartTime));
            callDetail.setBotAnswerText(aiResponse.getResponseTxt());
            callDetail.setBotAnswerTime(new Date());
            callDetail.setAccurateIntent(aiResponse.getAccurateIntent());
            callDetail.setReason(aiResponse.getReason());
            callDetail.setTotalDuration((int)(stopTime - initStartTime + callDetail.getAsrDuration()));
            callOutDetailService.save(callDetail);

            //保存CallDetailRecord
            callOutDetailRecordService.add(callPlan.getCallId(), callDetail.getCallDetailId(), event.getFileName(), aiResponse.getWavFile());
        }catch (Exception ex){
            log.warn("handleNormalAsr: 在处理AliAsr时出错异常", ex);
        }
    }

    /**
     * 正常应答播放
     * @param event
     * @param callPlan
     * @param callDetail
     */
    private void playNormal(AsrCustomerEvent event, CallOutPlan callPlan, AIResponse aiResponse, CallOutDetail callDetail) {
        if (event.isGenerated()) {
            callDetail.setCallDetailType(ECallDetailType.ASR_EMPTY_GENERATED.ordinal());
        } else {
            callDetail.setCallDetailType(ECallDetailType.NORMAL.ordinal());
        }

        boolean isLock = !fsBotConfig.isAllowDisturbed();
        channelHelper.playFile(event.getUuid(), aiResponse.getWavFile(), aiResponse.getWavDuration(), isLock);
    }

    /**
     * 转人工
     * @param callPlan
     * @param sellbotResponse
     */
    private void playToAgent(CallOutPlan callPlan, AIResponse sellbotResponse) {
//        //获取转人工的队列号
//        Template template = templateRepository.findByTempId(callPlan.getTempId());
//        callPlan.setAgentGroupId(template.getAgentGroupId());
//        callPlan.setAgentStartTime(DateUtil.getCurrentDateTime());
//        callPlan.setCallState(CallState.to_agent);
//        callPlanRepository.save(callPlan);
//
//        //播放完提示音后，将当前呼叫转到座席组
//        channelHelper.playAndTransferToAgentGroup(callPlan, sellbotResponse.getChannelMedia(), template.getAgentGroupId());
//
//        //构建事件抛出
//        ToAgentEvent toAgentEvent = new ToAgentEvent(callPlan);
//        simpleEventSender.sendEvent(toAgentEvent);
    }

    private CallOutDetail buildCallOutDetail(CallOutPlan callPlan, AsrCustomerEvent event) {
        CallOutDetail callDetail = new CallOutDetail();
        callDetail.setCallId(callPlan.getCallId());
        callDetail.setCustomerSayTime(new Date());
        callDetail.setCustomerSayText(event.getAsrText());
        callDetail.setAsrDuration(Math.toIntExact(event.getAsrDuration()));

        callDetail.setTotalDuration(Math.toIntExact(event.getAsrDuration()));
        callDetail.setCallDetailId(IdGenUtil.uuid());
        return callDetail;
    }

    //收到hangup事件的时候，释放sellbot资源
    @Subscribe
    public void handleHangup(ChannelHangupEvent event){
        log.info("收到Hangup事件[{}], 准备进行处理", event);
        try {
            CallOutPlan callPlan = callOutPlanService.findByCallId(event.getUuid());
            if(callPlan == null){
                log.warn("处理ChannelHangupEvent失败，因为未根据uuid[{}]找到对应的callPlan", event.getUuid());
                return;
            }

            callPlan.setHangupTime(event.getHangupStamp());
            callPlan.setAnswerTime(event.getAnswerStamp());
            callPlan.setDuration(event.getDuration());
            callPlan.setBillSec(event.getBillSec());
            callPlan.setHangupCode(event.getSipHangupCause());

            if(!Strings.isNullOrEmpty(event.getSipHangupCause()) && event.getBillSec() <=0){
                callPlan.setCallState(ECallState.hangup_fail.ordinal());
            }else{
                callPlan.setCallState(ECallState.hangup_ok.ordinal());
            }

            callOutPlanService.update(callPlan);

            //构建事件，进行后续流转, 上传七牛云，推送呼叫结果
            AfterCallEvent afterCallEvent = new AfterCallEvent(callPlan,false);
            asyncEventBus.post(afterCallEvent);

            //释放ai资源
            aiManager.releaseAi(event.getUuid());
        }catch (Exception ex){
            log.warn("在处理AliAsr时出错异常", ex);
        }
    }
}
