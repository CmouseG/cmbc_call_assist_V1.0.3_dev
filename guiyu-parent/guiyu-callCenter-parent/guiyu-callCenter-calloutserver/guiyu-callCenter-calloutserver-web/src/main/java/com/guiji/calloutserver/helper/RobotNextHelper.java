package com.guiji.calloutserver.helper;

import com.google.common.base.Preconditions;
import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.calloutserver.enm.EAIResponseType;
import com.guiji.calloutserver.enm.ECallDetailType;
import com.guiji.calloutserver.entity.AIResponse;
import com.guiji.calloutserver.entity.Channel;
import com.guiji.calloutserver.entity.SellbotResponse;
import com.guiji.calloutserver.eventbus.handler.FsBotHandler;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.calloutserver.service.CallOutDetailRecordService;
import com.guiji.calloutserver.service.CallOutDetailService;
import com.guiji.calloutserver.service.ChannelService;
import com.guiji.calloutserver.util.CommonUtil;
import com.guiji.component.result.Result;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.AiCallNext;
import com.guiji.robot.model.AiCallNextReq;
import com.guiji.utils.IdGenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/12 0012 15:49
 * @Description:
 */
@Slf4j
@Component
public class RobotNextHelper {

    @Autowired
    IRobotRemote robotRemote;
    @Autowired
    ChannelService channelService;
    @Autowired
    FsAgentManager fsAgentManager;
    @Autowired
    FsBotHandler fsBotHandler;
    @Autowired
    ChannelHelper channelHelper;
    @Autowired
    CallOutDetailService callOutDetailService;
    @Autowired
    CallOutDetailRecordService callOutDetailRecordService;

    ScheduledExecutorService scheduledExecutorService;
    ConcurrentHashMap<String, ScheduledFuture> scheduleConcurrentHashMap;

    @PostConstruct
    private void init() {

        scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduleConcurrentHashMap = new ConcurrentHashMap<>();
    }

    public void startAiCallNextTimer(AiCallNextReq aiCallNextReq) {
        String callId = aiCallNextReq.getSeqId();
        ScheduledFuture<?> schedule = scheduledExecutorService.scheduleAtFixedRate(() -> {
                    try {
                        log.info("-------------start  schedule aiCallNext callId:" + callId);
                        Channel channel = channelService.findByUuid(callId);
                        log.info("------------->>>>>>> channel [{}]", channel);
                        Long startTime = channel.getStartPlayTime().getTime();

                        if (channel.getEndPlayTime() != null && startTime < channel.getEndPlayTime().getTime()) {//播放结束
                            if (channel.getIsPrologue() != null && channel.getIsPrologue()) {//开场白
                                aiCallNextReq.setStatus("999");
                            } else {
                                aiCallNextReq.setStatus("0");
                            }
                            aiCallNextReq.setTimestamp(channel.getEndPlayTime().getTime());
                        } else {//播音中
                            aiCallNextReq.setStatus("1");
                            aiCallNextReq.setTimestamp(channel.getStartPlayTime().getTime());
                        }
                        log.info("--------------------robotRemote.aiCallNext aiCallNextReq[{}]", aiCallNextReq);
                        Result.ReturnData<AiCallNext> result = robotRemote.aiCallNext(aiCallNextReq);
                        log.info("=====================robotRemote.aiCallNext result[{}]", result);
// [ReturnData [code=0, msg=请求成功, success=true, body=AiCallNext(aiNo=192168150150132018121388527561, helloStatus=null, sellbotJson={"UserInfo":"","accurate_intent":"D","answer":"那打扰您了,祝您生活愉快，再见！",
// "answered_branch":"failed_enter","answered_domain":"结束","cfg_ver":"","dtmfend":"","dtmfflag":"false","dtmflen":"1","dtmfresult":"-1","dtmftimeout":"10","end":1,"intent":"D","match_nothing":0,
// "reason":"得分 <= 9.0 or 得分 < 0.0，实际得分: -10，或者接通即挂断","sentence":"我不是我","seqid":"30e9da92-fe81-4f85-988c-141253c52f3f","state":"结束","used_time_ms":69.617,"user_attentions":
// "[('结束', 2), ('开场白', 1)]","wav_filename":"csqtz_47115_rec/5.wav"}
                        AiCallNext aiCallNext = result.getBody();
                        String status = aiCallNext.getHelloStatus();
                        if (status.equals("play")) {
                            String resp = aiCallNext.getSellbotJson();

                            log.info("robotRemote.flowMsgPush getSellbotJson[{}]", resp);

                            SellbotResponse sellbotResponse = CommonUtil.jsonToJavaBean(resp, SellbotResponse.class);
                            Preconditions.checkState(sellbotResponse != null && sellbotResponse.isValid(), "invalid aiCallNext response");

                            AIResponse aiResponse = new AIResponse();
                            aiResponse.setResult(true);
                            aiResponse.setMatched(true);
                            aiResponse.setAccurateIntent(sellbotResponse.getAccurate_intent());
                            aiResponse.setAiId(aiCallNext.getAiNo());
                            aiResponse.setCallId(callId);
                            aiResponse.setReason(sellbotResponse.getReason());
                            aiResponse.setWavFile(sellbotResponse.getWav_filename());
                            aiResponse.setResponseTxt(sellbotResponse.getAnswer());
                            aiResponse.setAiResponseType(sellbotResponse.getEnd());
                            aiResponse.setWavDuration(fsAgentManager.getWavDruation(aiCallNextReq.getTemplateId(), sellbotResponse.getWav_filename()));
                            dealWithResponse(aiResponse);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("scheduledExecutorService.scheduleAtFixedRate has error: callId[{}]",callId);
                    }
                },
                0, 500, TimeUnit.MILLISECONDS);
        scheduleConcurrentHashMap.put(callId, schedule);

    }


    public void dealWithResponse(AIResponse aiResponse) {
        String callId = aiResponse.getCallId();
        CallOutDetail callDetail = callOutDetailService.getLastDetail(callId);

        if (callDetail == null || StringUtils.isNotBlank(callDetail.getBotAnswerText())) {
            callDetail = new CallOutDetail();
            callDetail.setCallId(callId);
            callDetail.setCallDetailId(IdGenUtil.uuid());
            setDetailValues(aiResponse, callDetail, callId);
            callOutDetailService.save(callDetail);

            CallOutDetailRecord callDetailRecord = new CallOutDetailRecord();
            callDetailRecord.setCallDetailId(callDetail.getCallDetailId());
            callDetailRecord.setBotRecordFile(aiResponse.getWavFile());
            callOutDetailRecordService.save(callDetailRecord);
        } else {
            setDetailValues(aiResponse, callDetail, callId);
            callOutDetailService.update(callDetail);

            CallOutDetailRecord callDetailRecord = new CallOutDetailRecord();
            callDetailRecord.setCallDetailId(callDetail.getCallDetailId());
            callDetailRecord.setBotRecordFile(aiResponse.getWavFile());
            callOutDetailRecordService.update(callDetailRecord);
        }

    }


    public void setDetailValues(AIResponse aiResponse, CallOutDetail callDetail, String callId) {
        log.info("此时为非转人工状态下的客户识别结果，继续下一步处理");
        if (aiResponse.getAiResponseType() == EAIResponseType.NORMAL) {       //机器人正常放音
            log.info("sellbot结果为正常放音");
            callDetail.setCallDetailType(ECallDetailType.NORMAL.ordinal());
            fsBotHandler.playNormal(callId, aiResponse, callDetail);
        } else if (aiResponse.getAiResponseType() == EAIResponseType.TO_AGENT) {      //转人工
            log.info("sellbot的结果为转人工");
            callDetail.setCallDetailType(ECallDetailType.TOAGENT_INIT.ordinal());
            fsBotHandler.playToAgent(aiResponse);
        } else if (aiResponse.getAiResponseType() == EAIResponseType.END) {           //sellbot提示结束，则在播放完毕后挂机
            log.info("sellbot的结果为通话结束");
            callDetail.setCallDetailType(ECallDetailType.END.ordinal());
            channelHelper.playFileToChannelAndHangup(callId, aiResponse.getWavFile(), aiResponse.getWavDuration());
        } else {
            callDetail.setCallDetailType(ECallDetailType.ASR_EMPTY.ordinal());
            log.warn("未知的sellbot返回类型[{}], 跳过处理", aiResponse.getAiResponseType());
        }

//        long stopTime = new Date().getTime();
//        callDetail.setAiDuration((int)(aiEndTime - aiStartTime));
        callDetail.setBotAnswerText(aiResponse.getResponseTxt());
        callDetail.setBotAnswerTime(new Date());
        callDetail.setAccurateIntent(aiResponse.getAccurateIntent());
        callDetail.setReason(aiResponse.getReason());
//        callDetail.setTotalDuration((int)(stopTime - initStartTime + callDetail.getAsrDuration()));
    }


    /**
     * 停止计时器
     *
     * @param uuid
     */
    public void stopAiCallNextTimer(String uuid) {
        if (scheduleConcurrentHashMap.containsKey(uuid)) {
            try {
                log.info("stop send aiCallNext timer task，uuid[{}]", uuid);
                scheduleConcurrentHashMap.get(uuid).cancel(true);
            } catch (Exception ex) {
                log.error("stop send aiCallNext timer task has error:", ex);
            }
        }
    }
}
