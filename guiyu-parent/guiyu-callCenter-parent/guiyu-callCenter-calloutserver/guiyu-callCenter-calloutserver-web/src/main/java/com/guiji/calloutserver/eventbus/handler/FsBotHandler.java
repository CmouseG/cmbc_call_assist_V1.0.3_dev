package com.guiji.calloutserver.eventbus.handler;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.constant.HangupDirectionEnum;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

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
    AsyncEventBus asyncEventBus;

    @Autowired
    RobotNextHelper robotNextHelper;

    @Autowired
    CallingCountManager callingCountManager;

    @Autowired
    LocalFsServer localFsServer;
    @Autowired
    CallLineAvailableManager callLineAvailableManager;
    @Autowired
    CallService callService;
    @Autowired
    LineListManager lineListManager;
    @Autowired
    CallLineResultService callLineResultService;
    @Value("${callInspect.open}")
    Boolean callInspectOpen;
    @Autowired
    SimLimitService simLimitService;

    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    //收到channel_answer事件，申请sellbot端口，并调用sellbot的restore方法
    @Subscribe
    @AllowConcurrentEvents
    public void handleAnswer(ChannelAnswerEvent event) {
        log.warn("{},{},{},{},{}", event.getCallerNum(), com.guiji.utils.DateUtil.formatDatetime(new Date()),
                "calloutserver", "收到channelAnser事件", event.getUuid());
        //[ChannelAnswerEvent(uuid=4e293cdf-94a6-442d-9787-87c8e19fefbc, callerNum=0000000000, calledNum=18651372376, accessNum=null)], 准备进行处理
        try {
            String uuid = event.getUuid();
            String callid;
            Integer orgId;
            BigInteger bigIntegerId = null;
            try {
                String[] arr = uuid.split(Constant.UUID_SEPARATE);
                callid = arr[0];
                orgId = Integer.valueOf(arr[1]);
                bigIntegerId = new BigInteger(callid);
            }catch (Exception e){
                return;
            }

            if(callInspectOpen){
                localFsServer.executeAsync(String.format("tone_detect %s %s_busy 450 r +9999999 hangup normal_clearing 3",uuid,uuid));
            }

            CallOutPlan callPlan = callOutPlanService.findByCallId(bigIntegerId, orgId);
            if (callPlan == null) {
                log.info("未知的应答事件，事件uuid[{}]，跳过处理", uuid);
                return;
            }
            //防止没有收到channel_progress事件，将状态改为线路已通
            callLineAvailableManager.lineAreAvailable(callid);
            callLineAvailableManager.channelAlreadyAnswer(callid);

            AIInitRequest request = new AIInitRequest(uuid,callPlan.getPlanUuid(), callPlan.getTempId(), callPlan.getPhoneNum(),
                    String.valueOf(callPlan.getCustomerId()), callPlan.getAiId());

            Long startTime = new Date().getTime();

            AIResponse aiResponse = null;
            try{
                aiResponse = aiManager.applyAi(request,callid);
                Preconditions.checkNotNull(aiResponse, "aiResponse is null error");
            }catch (Exception e){//申请资源异常 ，回调调度中心，更改calloutplan的状态  //todo 此处应该有告警，没有机器人资源
                log.error("-----error---------aiManager.applyAi:"+e);
                //回掉给调度中心，更改通话记录
                CallOutPlan callPlanUpdate = new CallOutPlan();
                callPlanUpdate.setCallId(callPlan.getCallId());
                callPlanUpdate.setOrgId(orgId);
                callPlanUpdate.setCallState(ECallState.norobot_fail.ordinal());
                if(callPlan.getAccurateIntent()==null){
                    callPlanUpdate.setAccurateIntent("W");
                    callPlanUpdate.setReason("606");
                }
                callOutPlanService.update(callPlanUpdate);
                //挂断电话
                localFsServer.hangup(uuid);
//                dispatchService.successSchedule(callPlan.getCallId(),callPlan.getPhoneNum(),"W");
                return;
            }

            Preconditions.checkNotNull(aiResponse, "null ai apply response");
            Long endTime = new Date().getTime();
            channelHelper.playAiReponse(aiResponse, false,true,orgId);

            //插入通话记录详情
            CallOutDetail callDetail = new CallOutDetail();
            callDetail.setCallId(callPlan.getCallId());
            callDetail.setOrgId(orgId);
            callDetail.setAiDuration(Math.toIntExact(endTime - startTime));
            callDetail.setTotalDuration(callDetail.getAiDuration());
            callDetail.setBotAnswerText(aiResponse.getResponseTxt());
            callDetail.setBotAnswerTime(new Date());
            callDetail.setAccurateIntent(aiResponse.getAccurateIntent());
            callDetail.setReason(aiResponse.getReason());
            callDetail.setCallDetailType(ECallDetailType.INIT.ordinal());
            callOutDetailService.save(callDetail);

            //需要重新查询一次，存在hangup事件已经结束，状态已经改变的情况
            CallOutPlan callPlanUpdate = new CallOutPlan();
            callPlanUpdate.setCallId(callPlan.getCallId());
            callPlanUpdate.setOrgId(callPlan.getOrgId());
            callPlanUpdate.setCallState(ECallState.answer.ordinal());
            callPlanUpdate.setIsAnswer(1);
            callOutPlanService.updateNotOverWriteCallState(callPlanUpdate);

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
            aiCallNextReq.setSeqId(uuid);
            robotNextHelper.startAiCallNextTimer(aiCallNextReq,callPlan.getAgentGroupId(),callid,orgId);
        } catch (Exception ex) {
            //TODO:报警
            log.warn("在处理ChannelAnswer时出错异常", ex);
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
    public void playToAgent(AIResponse sellbotResponse,String agentGroupId,Integer orgId) {

        String uuid = sellbotResponse.getCallId()+Constant.UUID_SEPARATE+orgId;
        CallOutPlan realCallPlan = callOutPlanService.findByCallId(new BigInteger(sellbotResponse.getCallId()), orgId);
        if(realCallPlan.getAgentId()==null){//非协呼

            //获取转人工的队列号
            CallOutPlan callPlan = new CallOutPlan();
            callPlan.setCallId(new BigInteger(sellbotResponse.getCallId()));
            callPlan.setOrgId(orgId);
            callPlan.setAgentStartTime(new Date());
            callPlan.setCallState(ECallState.to_agent.ordinal());
            callOutPlanService.update(callPlan);

            //播放完提示音后，将当前呼叫转到座席组
            channelHelper.playAndTransferToAgentGroup(uuid,sellbotResponse.getWavFile(),sellbotResponse.getWavDuration(), agentGroupId);
        }
        log.info("在开始转人工后，释放ai资源");
        aiManager.releaseAi(realCallPlan);

        //停止定时任务
        robotNextHelper.stopAiCallNextTimer(uuid);
        //构建事件抛出
        ToAgentEvent toAgentEvent = new ToAgentEvent(uuid);
        asyncEventBus.post(toAgentEvent);

    }

    //收到hangup事件的时候，释放sellbot资源
    @Subscribe
    @AllowConcurrentEvents
    public void handleHangup(ChannelHangupEvent event) {
        log.info("收到Hangup事件[{}], 准备进行处理", event);

        try {
            String uuid = event.getUuid();
            String callId;
            Integer orgId;
            BigInteger bigIntegerId = null;
            try {
                String[] arr = uuid.split(Constant.UUID_SEPARATE);
                callId = arr[0];
                orgId = Integer.valueOf(arr[1]);
                bigIntegerId = new BigInteger(callId);
            }catch (Exception e){
                return;
            }
            CallOutPlan callPlan = callOutPlanService.findByCallId(bigIntegerId, orgId);
            if (callPlan == null) {
                log.warn("处理ChannelHangupEvent失败，因为未根据uuid[{}]找到对应的callPlan", event.getUuid());
                return;
            }else{
                log.info("根据挂断事件找到CallOutPlan[{}]", callPlan.getCallId());
            }

            //记录sim卡拨打次数，防止sim卡拨打超限
            simLimitService.addSimCall(callId,callPlan.getLineId(),event.getBillSec());

            boolean goEndProcess = false;
            //如果通了，则走原来的流程...
            if (callLineAvailableManager.isAvailable(callId)) {
                log.info("线路是可用的，callId[{}],lineId[{}]",callId,callPlan.getLineId());
                callLineResultService.addLineResult(callPlan, true);
                goEndProcess = true;
            } else if (lineListManager.isEnd(callId)) {//最后一条线路了，则走原来的流程...
                log.info("最后一条线路了，callId[{}],lineId[{}]",callId,callPlan.getLineId());
                callLineResultService.addLineResult(callPlan, false);
                goEndProcess = true;
            } else { //这次没有打通，还有线路，继续打下一条线路
                callLineResultService.addLineResult(callPlan, false);

                //重置callOutPlan的一些状态,line_id,call_start_time,call_state
                Integer lineId = lineListManager.popNewLine(callId);
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
                CallOutDetail callOutDetail = callOutDetailService.getLastDetail(callId,orgId);
                callPlan.setTalkNum(callOutDetailService.getTalkNum(bigIntegerId,orgId));
                if (callOutDetail != null) {
                    log.info("[{}]电话拨打成功，开始设置意向标签[{}]和原因[{}]", callPlan.getCallId(), callOutDetail.getAccurateIntent(), callOutDetail.getReason());
                    callPlan.setAccurateIntent(callOutDetail.getAccurateIntent());
                    callPlan.setReason(callOutDetail.getReason());
                }else {//电话没打出去
                    if (callPlan.getAccurateIntent() == null) {
                        if (duration != null) {
                            if(duration.intValue() <= 10 && StringUtils.isNotBlank(hangUp) && StringUtils.isNumeric(hangUp) && Integer.valueOf(hangUp)>=400 ){
                                callPlan.setAccurateIntent("W");
                                if (callPlan.getReason() == null) {
                                    callPlan.setReason(hangUp);
                                }
                            }else if (duration.intValue() > 0 && billSec != null && billSec.intValue() == 0) { //设置F类
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
                        callPlan.setAccurateIntent("W");
                        if (callPlan.getReason() == null && hangUp != null) {
                            callPlan.setReason(hangUp);
                        }
                    }

                }
                callPlan.setHangupTime(event.getHangupStamp());
                callPlan.setAnswerTime(event.getAnswerStamp());
                callPlan.setCallStartTime(event.getStartStamp());
                if(event.getHangupDisposition()!=null){
                    if(event.getHangupDisposition().equals("send_bye")){//机器人挂断
                        callPlan.setHangupDirection(HangupDirectionEnum.ROBOT.ordinal());
                    }else{//用户挂断
                        callPlan.setHangupDirection(HangupDirectionEnum.USER.ordinal());
                    }
                }

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
                        //hangup和channelAnswer如果几乎同时过来，会有并发问题。线程等待5秒，等待channelAnswer把事情处理完
                        if(callPlan.getAccurateIntent().equals("W")){
                            log.info("billSec>0 ,但是意向却为W，需要重新查询意向标签");
                            try{
                                Thread.sleep(6000);
                            }catch (Exception e){
                                log.info("线程sleep出现异常",e);
                            }
                            CallOutDetail callDetailNew = callOutDetailService.getLastDetail(callId,orgId);
                            if (callDetailNew != null) {
                                log.info("再次查询意向标签[{}]和原因[{}]", callId, callDetailNew.getAccurateIntent(), callDetailNew.getReason());
                                callPlan.setAccurateIntent(callDetailNew.getAccurateIntent());
                                callPlan.setReason(callDetailNew.getReason());
                            }
                        }
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

                //释放实时通道相关资源
                log.info("开始释放Channel资源,uuid[{}]", uuid);
                channelHelper.hangup(uuid);

                //释放ai资源
                log.info("开始释放ai资源,callplanId[{}], aiId[{}]", callPlan.getCallId(), callPlan.getAiId());
                aiManager.releaseAi(callPlan);

                //报表统计事件
                log.info("构建StatisticReportEvent，报表流转");
                StatisticReportEvent statisticReportEvent = new StatisticReportEvent(callPlan);
                asyncEventBus.post(statisticReportEvent);

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
