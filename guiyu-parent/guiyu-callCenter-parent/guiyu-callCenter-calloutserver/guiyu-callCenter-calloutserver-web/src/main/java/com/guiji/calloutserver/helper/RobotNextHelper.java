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
                        Channel channel = channelService.findByUuid(callId);
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
                        log.info("-------------start  robotRemote aiCallNext callId:" + callId);
                        Result.ReturnData<AiCallNext> result = robotRemote.aiCallNext(aiCallNextReq);
                        AiCallNext aiCallNext = result.getBody();
                        String status = aiCallNext.getHelloStatus();
                        if (status.equals("play")) {

                            //判断当前通道是否被锁定，如果锁定的话，则跳过后续处理
                            if (channelHelper.isChannelLock(callId)) {
                                log.info("通道媒体[{}]已被锁定，跳过该次识别请求 startAiCallNextTimer", callId);
                                return;
                            }

                            String resp = aiCallNext.getSellbotJson();

                            log.debug("robotRemote.flowMsgPush getSellbotJson[{}]", resp);

                            SellbotResponse sellbotResponse = CommonUtil.jsonToJavaBean(resp, SellbotResponse.class);
                            Preconditions.checkState(sellbotResponse != null && sellbotResponse.isValid(), "invalid aiCallNext response");

                            AIResponse aiResponse = new AIResponse();
                            aiResponse.setResult(true);
                            aiResponse.setMatched(true);
                            aiResponse.setAccurateIntent(sellbotResponse.getAccurate_intent());
                            aiResponse.setAiId(aiCallNext.getAiNo());
                            aiResponse.setCallId(callId);
                            aiResponse.setReason(sellbotResponse.getReason());

                            String wavFilename = getWavFilename(sellbotResponse.getWav_filename(),aiCallNextReq.getTemplateId());
                            Preconditions.checkNotNull(wavFilename, "wavFilename is null error");
                            aiResponse.setWavFile(wavFilename);

                            aiResponse.setResponseTxt(sellbotResponse.getAnswer());
                            aiResponse.setAiResponseType(sellbotResponse.getEnd());

                            Double wavDruation = fsAgentManager.getWavDruation(aiCallNextReq.getTemplateId(), wavFilename);
                            Preconditions.checkNotNull(wavDruation, "wavDruation is null error");
                            aiResponse.setWavDuration(wavDruation);
                            dealWithResponse(aiResponse);
                        }
                    } catch (Exception e) {
                        log.error("scheduledExecutorService.scheduleAtFixedRate has error: ",e);
                    }
                },
                0, 500, TimeUnit.MILLISECONDS);
        scheduleConcurrentHashMap.put(callId, schedule);

    }


    public void dealWithResponse(AIResponse aiResponse) {
        String callId = aiResponse.getCallId();
//        CallOutDetail callDetail = callOutDetailService.getLastDetailCustomer(callId);
//
//        if (callDetail == null || StringUtils.isNotBlank(callDetail.getBotAnswerText())) {
        CallOutDetail callDetail = new CallOutDetail();
            callDetail.setCallId(callId);
            callDetail.setCallDetailId(IdGenUtil.uuid());
            setDetailValues(aiResponse, callDetail, callId);
            callOutDetailService.save(callDetail);

            CallOutDetailRecord callDetailRecord = new CallOutDetailRecord();
            callDetailRecord.setCallDetailId(callDetail.getCallDetailId());
            callDetailRecord.setBotRecordFile(aiResponse.getWavFile());
            callOutDetailRecordService.save(callDetailRecord);
//        } else {
//            setDetailValues(aiResponse, callDetail, callId);
//            callOutDetailService.update(callDetail);
//
//            CallOutDetailRecord callDetailRecord = new CallOutDetailRecord();
//            callDetailRecord.setCallDetailId(callDetail.getCallDetailId());
//            callDetailRecord.setBotRecordFile(aiResponse.getWavFile());
//            callOutDetailRecordService.update(callDetailRecord);
//        }

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

        callDetail.setBotAnswerText(aiResponse.getResponseTxt());
        callDetail.setBotAnswerTime(new Date());
        callDetail.setAccurateIntent(aiResponse.getAccurateIntent());
        callDetail.setReason(aiResponse.getReason());
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

    /**
     * 返回标准的文件名称
     */
    public static String getWavFilename(String filename, String tempId) {

        if (tempId.endsWith("_en")) {
            tempId = tempId.replace("_en", "_rec");
        }

        if (filename != null) {
            if (filename.contains("/")) {
                String[] arr = filename.split("/");
                String result = arr[arr.length - 1];
                return tempId + "/" + result;
            }
            if(!filename.endsWith(".wav")){
                filename =filename+".wav";
            }
            return tempId + "/" + filename;
        }
        return null;
    }

}
