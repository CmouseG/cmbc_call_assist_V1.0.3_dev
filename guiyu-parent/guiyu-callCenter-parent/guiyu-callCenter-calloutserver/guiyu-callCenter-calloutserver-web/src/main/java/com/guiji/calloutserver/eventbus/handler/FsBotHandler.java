package com.guiji.calloutserver.eventbus.handler;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.ErrorMatch;
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
import com.guiji.calloutserver.service.ErrorMatchService;
import com.guiji.utils.IdGenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
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
    ErrorMatchService errorMatchService;

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
            Preconditions.checkNotNull(aiResponse, "null ai apply response");
            Long endTime = new Date().getTime();
            channelHelper.playAiReponse(aiResponse,false);

            //更新callplan
            callPlan.setAiId(aiResponse.getAiId());
            callPlan.setCallState(ECallState.answer.ordinal());
            callPlan.setAnswerTime(new Date());
            callPlan.setAccurateIntent(aiResponse.getAccurateIntent());
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
            //TODO:报警
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
                log.warn("通道[{}]还未接听，需要对收到的asr进行F类识别", event.getUuid());
                //进行F类识别
                doWithErrorResponse(callPlan, event);
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

            //如果是系统产生的事件，则跳过isMatch处理，因为需要该事件来触发下一步的说话内容
            if(!event.isGenerated()){
                //只有在播放状态，才去调用isMatch接口，如果不是播放状态，则直接调用hello
                if(channelHelper.isInPlay(event.getUuid())){
                    //判断是否匹配到关键词
                    if(!aiManager.isMatch(event.getUuid(), event.getAsrText())){
                        log.debug("sellbot识别未匹配，识别内容为[{}]，跳过后续的放音处理。", event.getAsrText());
                        CallOutDetail callDetail = buildCallOutDetail(callPlan, event);
                        callDetail.setCallDetailType(ECallDetailType.UNMATCH.ordinal());
                        callDetail.setBotAnswerTime(new Date());//此字段用来排序，不为空
                        callOutDetailService.save(callDetail);
                        return;
                    }
                }
            }

            if(!channelHelper.isAllowDisturb(event.getUuid())){
                log.debug("通道媒体[{}]不符合打断条件，禁止打断", event.getUuid());
                return;
            }

            //发起ai请求
            long aiStartTime = new Date().getTime();
            AIRequest aiRequest = new AIRequest(event);
            aiRequest.setCallMatch(false);
            AIResponse aiResponse = aiManager.sendAiRequest(aiRequest);
            long aiEndTime = new Date().getTime();

            CallOutDetail callDetail = buildCallOutDetail(callPlan, event);
            log.info("此时为非转人工状态下的客户识别结果，继续下一步处理");
            if(aiResponse.getAiResponseType() == EAIResponseType.NORMAL) {       //机器人正常放音
                log.info("sellbot结果为正常放音");
                callDetail.setCallDetailType(ECallDetailType.NORMAL.ordinal());
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
                callDetail.setCallDetailType(ECallDetailType.ASR_EMPTY.ordinal());
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
     * 进行F类识别
     * @param callPlan
     * @param event
     */
    private void doWithErrorResponse(CallOutPlan callPlan, AsrCustomerEvent event) {
        if(Strings.isNullOrEmpty(event.getAsrText())){
            log.warn("F类识别失败，因asr识别结果为空");
            return;
        }

        ErrorMatch errorMatch = errorMatchService.findError(event.getAsrText());
        if(errorMatch!=null){
            log.debug("F类识别结果为[{}]", errorMatch);
            callPlan.setAccurateIntent("F");
            callPlan.setReason(errorMatch.getErrorName());
            callOutPlanService.update(callPlan);
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
//        callPlanRepository.add(callPlan);
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

        Long duration = event.getAsrDuration();
        if(event.getAsrDuration()== null || event.getAsrDuration()<=0){
            duration = 1000L;
        }

        //估算用户说话时间 = 当前时间 - asr识别时长
        LocalDateTime currentTime = LocalDateTime.now();
        currentTime = currentTime.minus(duration, ChronoField.MILLI_OF_DAY.getBaseUnit());
        callDetail.setCustomerSayTime(java.sql.Timestamp.valueOf(currentTime));

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

            //将calloutdetail里面的意向标签更新到calloutplan里面
            CallOutDetail callOutDetail = callOutDetailService.getLastDetail(event.getUuid());
            if(callOutDetail!=null){
                callPlan.setAccurateIntent(callOutDetail.getAccurateIntent());
                callPlan.setReason(callOutDetail.getReason());
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

            //释放实时通道相关资源
            channelHelper.hangup(event.getUuid());
        }catch (Exception ex){
            log.warn("在处理AliAsr时出错异常", ex);
        }
    }
}
