package com.guiji.calloutserver.eventbus.handler;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.calloutserver.enm.ECallDetailType;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.entity.AIInitRequest;
import com.guiji.calloutserver.entity.AIRequest;
import com.guiji.calloutserver.entity.AIResponse;
import com.guiji.calloutserver.eventbus.event.*;
import com.guiji.calloutserver.fs.LocalFsServer;
import com.guiji.calloutserver.helper.ChannelHelper;
import com.guiji.calloutserver.helper.RobotNextHelper;
import com.guiji.calloutserver.manager.*;
import com.guiji.calloutserver.service.*;
import com.guiji.robot.model.AiCallNextReq;
import com.guiji.utils.IdGenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FsBotHandler {
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
    RobotNextHelper robotNextHelper;

    @Autowired
    CallingCountManager callingCountManager;

    @Autowired
    LocalFsServer localFsServer;
    @Autowired
    CallOutRecordService callOutRecordService;
    @Autowired
    CallLineAvailableManager callLineAvailableManager;
    @Autowired
    CallService callService;
    @Autowired
    LineListManager lineListManager;
    @Autowired
    CallLineResultService callLineResultService;

    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    //收到channel_answer事件，申请sellbot端口，并调用sellbot的restore方法
    @Subscribe
    @AllowConcurrentEvents
    public void handleAnswer(ChannelAnswerEvent event) {
        log.info("收到ChannelAnswer事件[{}], 准备进行处理", event);
        //[ChannelAnswerEvent(uuid=4e293cdf-94a6-442d-9787-87c8e19fefbc, callerNum=0000000000, calledNum=18651372376, accessNum=null)], 准备进行处理
        try {
            String uuid = event.getUuid();
            BigInteger bigIntegerId = null;
            try{
                bigIntegerId = new BigInteger(uuid);
            }catch (Exception e){
                return;
            }
            CallOutPlan callPlan = callOutPlanService.findByCallId(bigIntegerId);
            if (callPlan == null) {
                log.info("未知的应答事件，事件uuid[{}]，跳过处理", event.getUuid());
                return;
            }
            //防止没有收到channel_progress事件，将状态改为线路已通
            callLineAvailableManager.lineAreAvailable(uuid);

            AIInitRequest request = new AIInitRequest(String.valueOf(callPlan.getCallId()),callPlan.getPlanUuid(), callPlan.getTempId(), callPlan.getPhoneNum(), String.valueOf(callPlan.getCustomerId()), callPlan.getAiId());

            Long startTime = new Date().getTime();

            AIResponse aiResponse = null;
            try{
                aiResponse = aiManager.applyAi(request);
                Preconditions.checkNotNull(aiResponse, "aiResponse is null error");
            }catch (Exception e){//申请资源异常 ，回调调度中心，更改calloutplan的状态  //todo 此处应该有告警，没有机器人资源
                log.error("-----error---------aiManager.applyAi:"+e);
                //回掉给调度中心，更改通话记录
                callPlan.setCallState(ECallState.norobot_fail.ordinal());
                if(callPlan.getAccurateIntent()!=null){
                    callPlan.setAccurateIntent("W");
                    callPlan.setReason(e.getMessage());
                }
                callOutPlanService.update(callPlan);
//                dispatchService.successSchedule(callPlan.getCallId(),callPlan.getPhoneNum(),"W");
                return;
            }

            Preconditions.checkNotNull(aiResponse, "null ai apply response");
            Long endTime = new Date().getTime();
            channelHelper.playAiReponse(aiResponse, false,true);

            //需要重新查询一次，存在hangup事件已经结束，状态已经改变的情况  todo 情况是否还有，需要验证
            CallOutPlan callPlanNew = callOutPlanService.findByCallId(new BigInteger(event.getUuid()));
            if (callPlanNew.getCallState() == null || callPlanNew.getCallState() < ECallState.answer.ordinal()) {
                callPlan.setCallState(ECallState.answer.ordinal());
            }
            callPlan.setAnswerTime(new Date());
//            callPlan.setAccurateIntent(aiResponse.getAccurateIntent());
            callPlan.setIsAnswer(1);
            callOutPlanService.update(callPlan);

            //插入通话记录详情
//            String detailID = IdGenUtil.uuid();
            CallOutDetail callDetail = new CallOutDetail();
            callDetail.setCallId(callPlan.getCallId());
//            callDetail.setCallDetailId(detailID);
            callDetail.setAiDuration(Math.toIntExact(endTime - startTime));
            callDetail.setTotalDuration(callDetail.getAiDuration());
            callDetail.setBotAnswerText(aiResponse.getResponseTxt());
            callDetail.setBotAnswerTime(new Date());
            callDetail.setAccurateIntent(aiResponse.getAccurateIntent());
            callDetail.setReason(aiResponse.getReason());
            callDetail.setCallDetailType(ECallDetailType.INIT.ordinal());
            callOutDetailService.save(callDetail);

            //插入录音文件信息
            CallOutDetailRecord callOutDetailRecord = new CallOutDetailRecord();
            callOutDetailRecord.setCallDetailId(callDetail.getCallDetailId());
            callOutDetailRecord.setCallId(callPlan.getCallId());
            callOutDetailRecord.setCallDetailId(callDetail.getCallDetailId());
            callOutDetailRecord.setBotRecordFile(aiResponse.getWavFile());
            callOutDetailRecord.setCallDetailId(callDetail.getCallDetailId());
            callOutDetailRecordService.save(callOutDetailRecord);

            //启动定时器，500ms请求一次aiCallNext接口
            AiCallNextReq aiCallNextReq = new AiCallNextReq();
            aiCallNextReq.setUserId(String.valueOf(callPlan.getCustomerId()));
            aiCallNextReq.setTemplateId(callPlan.getTempId());
            aiCallNextReq.setAiNo(aiResponse.getAiId());
            aiCallNextReq.setPhoneNo(callPlan.getPhoneNum());
            aiCallNextReq.setSeqId(String.valueOf(callPlan.getCallId()));
            robotNextHelper.startAiCallNextTimer(aiCallNextReq,callPlan.getAgentGroupId());
        } catch (Exception ex) {
            //TODO:报警
            log.warn("在处理ChannelAnswer时出错异常", ex);
        }
    }


    @Subscribe
    @AllowConcurrentEvents
    public void handleCustomerAsrEvent(AsrCustomerEvent event) {
        try {
//            CallOutPlan callPlan = callOutPlanService.findByCallId(new BigInteger(event.getUuid()));
            CallOutPlan callPlan = event.getCallPlan();
//            if (callPlan == null) {
//                log.warn("收到的CustomerAsr，没有根据uuid[{}]找到对应的callPlan，跳过处理", event.getUuid());
//                return;
//            }

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

            aiManager.sendAiRequest(aiRequest);

            buildCallOutDetail(callPlan.getCallId(), event);

        } catch (Exception ex) {
            log.warn("handleNormalAsr: 在处理AliAsr时出错异常", ex);
        }
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
                localFsServer.hangup(callPlan.getCallId());
            }
        }
    }

    /**
     * 正常应答播放
     *
     * @param callDetail
     */
    public void playNormal(String uuid, AIResponse aiResponse, CallOutDetail callDetail) {
        callDetail.setCallDetailType(ECallDetailType.NORMAL.ordinal());
        channelHelper.playFile(uuid, aiResponse.getWavFile(), aiResponse.getWavDuration(), false, false);
    }

    /**
     * 转人工
     *
     * @param sellbotResponse
     */
    public void playToAgent(AIResponse sellbotResponse,String agentGroupId) {
        //获取转人工的队列号
        CallOutPlan callPlan = new CallOutPlan();
        callPlan.setCallId(new BigInteger(sellbotResponse.getCallId()));
        callPlan.setAgentStartTime(new Date());
        callPlan.setCallState(ECallState.to_agent.ordinal());
        callOutPlanService.update(callPlan);

        //播放完提示音后，将当前呼叫转到座席组
        channelHelper.playAndTransferToAgentGroup(sellbotResponse.getCallId(), sellbotResponse.getWavFile(),sellbotResponse.getWavDuration(), agentGroupId);

        log.info("在开始转人工后，释放ai资源");
        CallOutPlan realCallPlan = callOutPlanService.findByCallId(new BigInteger(sellbotResponse.getCallId()));
        aiManager.releaseAi(realCallPlan);

        //停止定时任务
        robotNextHelper.stopAiCallNextTimer(callPlan.getCallId().toString());
        //构建事件抛出
        ToAgentEvent toAgentEvent = new ToAgentEvent(callPlan);
        asyncEventBus.post(toAgentEvent);
    }

    public void buildCallOutDetail(BigInteger callId, AsrCustomerEvent event) {

        //开场白之前的asr识别不记录，识别太灵敏了，导致太多无用通话记录
        if (StringUtils.isNotBlank(event.getAsrText())) {
            CallOutDetail callDetail = new CallOutDetail();
            callDetail.setCallId(callId);

            Long duration = event.getAsrDuration();
            if (event.getAsrDuration() == null || event.getAsrDuration() <= 0) {
                duration = 1000L;
            }

            //估算用户说话时间 = 当前时间 - asr识别时长
            LocalDateTime currentTime = LocalDateTime.now();
            currentTime = currentTime.minus(duration, ChronoField.MILLI_OF_DAY.getBaseUnit());
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

    //收到hangup事件的时候，释放sellbot资源
    @Subscribe
    @AllowConcurrentEvents
    public void handleHangup(ChannelHangupEvent event) {
        log.info("收到Hangup事件[{}], 准备进行处理", event);

        try {
            String uuid = event.getUuid();
            BigInteger bigIntegerId = null;
            try{
                bigIntegerId = new BigInteger(uuid);
            }catch (Exception e){
                return;
            }
            CallOutPlan callPlan = callOutPlanService.findByCallId(bigIntegerId);
            if (callPlan == null) {
                log.warn("处理ChannelHangupEvent失败，因为未根据uuid[{}]找到对应的callPlan", event.getUuid());
                return;
            }else{
                log.info("根据挂断事件找到CallOutPlan[{}]", callPlan.getCallId());
            }

            boolean goEndProcess = false;
            //如果通了，则走原来的流程...
            if (callLineAvailableManager.isAvailable(uuid)) {
                log.info("线路是可用的，callId[{}],lineId[{}]",uuid,callPlan.getLineId());
                callLineResultService.addLineResult(callPlan, true);
                goEndProcess = true;
            } else if (lineListManager.isEnd(uuid)) {//最后一条线路了，则走原来的流程...
                log.info("最后一条线路了，callId[{}],lineId[{}]",uuid,callPlan.getLineId());
                callLineResultService.addLineResult(callPlan, false);
                goEndProcess = true;
            } else { //这次没有打通，还有线路，继续打下一条线路
                callLineResultService.addLineResult(callPlan, false);

                //重置callOutPlan的一些状态,line_id,call_start_time,call_state
                Integer lineId = lineListManager.popNewLine(uuid);
                callPlan.setLineId(lineId);
                Date date = new Date();
                callPlan.setCallStartTime(date);
                callPlan.setCreateTime(date);
                callPlan.setCallState(ECallState.make_call.ordinal());
                callOutPlanService.update(callPlan);

                log.info("选择新的线路重新发起呼叫 callPlan[{}]", callPlan);
                callService.makeCall(callPlan, uuid + ".wav");
            }

            if(goEndProcess){

                String hangUp = event.getSipHangupCause();
                Integer duration = event.getDuration();
                Integer billSec = event.getBillSec();
                //将calloutdetail里面的意向标签更新到calloutplan里面
                CallOutDetail callOutDetail = callOutDetailService.getLastDetail(event.getUuid());
                callPlan.setTalkNum(callOutDetailService.getTalkNum(new BigInteger(event.getUuid())));
                if (callOutDetail != null) {
                    log.info("[{}]电话拨打成功，开始设置意向标签[{}]和原因[{}]", callPlan.getCallId(), callOutDetail.getAccurateIntent(), callOutDetail.getReason());
                    callPlan.setAccurateIntent(callOutDetail.getAccurateIntent());
                    callPlan.setReason(callOutDetail.getReason());
                }else {//电话没打出去
                    if (callPlan.getAccurateIntent() == null) {
                        if (duration != null) {
                            if (duration.intValue() > 0 && billSec != null && billSec.intValue() == 0) { //设置F类
                                log.info("挂断后，设置意向标签为F,callId[{}]", uuid);
                                callPlan.setAccurateIntent("F");
                                if (duration.intValue() >= 55) {
                                    log.info("超过55秒，设置备注为无人接听,callId[{}]", uuid);
                                    callPlan.setReason("无人接听");
                                }
                            }
                        }
                    }

                    if (callPlan.getAccurateIntent() == null) {
                        if (!callLineAvailableManager.isAvailable(uuid)) { //线路不可用,设置为W
                            log.info("线路是不可用的，设置意向标签为W,callId[{}]", uuid);
                            callPlan.setAccurateIntent("W");
                            if (callPlan.getReason() == null && hangUp != null) {
                                callPlan.setReason(hangUp);
                            }
                        }
                    }

                }
                callPlan.setHangupTime(event.getHangupStamp());
                callPlan.setAnswerTime(event.getAnswerStamp());

                if (duration != null){
                    callPlan.setDuration(duration);
                    if(duration.intValue()>0 && billSec!=null && billSec.intValue() ==0){
                        callPlan.setIsCancel(1);
                    }
                }
                if (billSec != null) {
                    callPlan.setBillSec(billSec);
                    if(billSec.intValue() > 0){
                        callPlan.setIsAnswer(1);
                    }
                }

               if (!Strings.isNullOrEmpty(hangUp)) {
                    callPlan.setHangupCode(hangUp);
                }

                if (!Strings.isNullOrEmpty(event.getSipHangupCause()) && event.getBillSec() <= 0) {
                    callPlan.setCallState(ECallState.hangup_fail.ordinal());
                } else {
                    callPlan.setCallState(ECallState.hangup_ok.ordinal());
                }

                callOutPlanService.updateNotOverWriteIntent(callPlan);

                //报表统计事件
                log.info("构建StatisticReportEvent，报表流转");
                StatisticReportEvent statisticReportEvent = new StatisticReportEvent(callPlan);
                asyncEventBus.post(statisticReportEvent);

                //释放实时通道相关资源
                log.info("开始释放Channel资源,uuid[{}]", event.getUuid());
                channelHelper.hangup(event.getUuid());

                //释放ai资源
                log.info("开始释放ai资源,callplanId[{}], aiId[{}]", callPlan.getCallId(), callPlan.getAiId());
                aiManager.releaseAi(callPlan);

                //构建事件，进行后续流转, 上传七牛云，推送呼叫结果
                log.info("构建afterCallEvent，上传录音，回调调度中心");
                AfterCallEvent afterCallEvent = new AfterCallEvent(callPlan);
                asyncEventBus.post(afterCallEvent);

                //计数减一
                callingCountManager.removeOneCall();
            }
        } catch (Exception ex) {
            log.warn("在处理Hangup时出错异常", ex);
        }
    }
}
