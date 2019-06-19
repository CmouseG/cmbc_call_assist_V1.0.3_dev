package com.guiji.calloutserver.helper;

import com.google.common.base.Preconditions;
import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.calloutserver.constant.Constant;
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
import com.guiji.fsagent.entity.TtsWav;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.AiCallNext;
import com.guiji.robot.model.AiCallNextReq;
import com.guiji.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    @Autowired
    RedisUtil redisUtil;

    ScheduledExecutorService scheduledExecutorService;
    ConcurrentHashMap<String, ScheduledFuture> scheduleConcurrentHashMap;

    @PostConstruct
    private void init() {

        scheduledExecutorService = Executors.newScheduledThreadPool(30);
        scheduleConcurrentHashMap = new ConcurrentHashMap<>();
    }

    public void startAiCallNextTimer(AiCallNextReq aiCallNextReq, String agentGroupId, String callId, Integer orgId) {
        log.info("开始启动AiNextTimer[{}],500毫秒执行一次", aiCallNextReq);
        String uuid = aiCallNextReq.getSeqId();
        Channel channelInit = channelService.findByUuid(uuid);
        if(channelInit == null){ //channelInit为null表示已经hangup了，不需要后面的循环调用了,直接return
            log.info("startAiCallNextTimer findByUuid channelInit is null uuid[{}]",uuid);
            return;
        }

        ScheduledFuture<?> schedule = scheduledExecutorService.scheduleAtFixedRate(() -> {
                    try {
                        log.debug("根据callId[{}]获取channel", uuid);
                        Channel channel = channelService.findByUuid(uuid);
                        if(channel == null){
                            log.info("startAiCallNextTimer findByUuid is null callId[{}]",uuid);
                            stopAiCallNextTimer(uuid);
                            return;
                        }
                        Long startTime = channel.getStartPlayTime().getTime();

                        if (channel.getEndPlayTime() != null && startTime < channel.getEndPlayTime().getTime()) {//播放结束
                            aiCallNextReq.setStatus("0");
                            aiCallNextReq.setTimestamp(channel.getEndPlayTime().getTime());
                            aiCallNextReq.setWaitCnt((int) (new Date().getTime()-channel.getEndPlayTime().getTime())/1000);
                        } else {//播音中
                            if (channel.getIsPrologue() != null && channel.getIsPrologue()) {//开场白
                                aiCallNextReq.setStatus("999");
                            } else {
                                aiCallNextReq.setStatus("1");
                            }
                            aiCallNextReq.setTimestamp(channel.getStartPlayTime().getTime());
                            aiCallNextReq.setWaitCnt(0);
                        }
                        log.info("-------------start  robotRemote aiCallNext aiCallNextReq[{}]",aiCallNextReq);
                        Result.ReturnData<AiCallNext> result = robotRemote.aiCallNext(aiCallNextReq);
                        AiCallNext aiCallNext = result.getBody();
                        if(result.getCode().equals("00060006")){ //机器人不存在
                            log.info("机器人不存在，停止定时任务aiCallNext, uuid[{}]",uuid);
                            stopAiCallNextTimer(uuid);
                            return;
                        }else {
                            if (aiCallNext != null && aiCallNext.getHelloStatus() != null && aiCallNext.getHelloStatus().equals("play")) {
                                log.info("-------------end  robotRemote aiCallNext result[{}]", result);
                                //判断当前通道是否被锁定，如果锁定的话，则跳过后续处理
                                if (channelHelper.isChannelLock(uuid)) {
                                    log.info("通道媒体[{}]已被锁定，跳过该次识别请求 startAiCallNextTimer", uuid);
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

                                String wavFilename = getWavFilename(sellbotResponse.getWav_filename(), aiCallNextReq.getTemplateId(), callId);
                                Preconditions.checkNotNull(wavFilename, "wavFilename is null error");
                                aiResponse.setWavFile(wavFilename);

                                aiResponse.setResponseTxt(sellbotResponse.getAnswer());
                                aiResponse.setAiResponseType(sellbotResponse.getEnd());
                                aiResponse.setKeyWords(sellbotResponse.getKeywords());
                                aiResponse.setWordSegmentResult(sellbotResponse.getWord_segment_result());

                                double wavDruation = fsAgentManager.getWavDruation(aiCallNextReq.getTemplateId(), wavFilename, callId);
//                                Preconditions.checkNotNull(wavDruation, "wavDruation is null error");
                                aiResponse.setWavDuration(wavDruation);
                                dealWithResponse(aiResponse, agentGroupId, orgId);
                            }
                        }
                    } catch (Exception e) {
                        log.error("scheduledExecutorService.scheduleAtFixedRate has error: ", e);
                    }
                },
                0, 500, TimeUnit.MILLISECONDS);

        scheduleConcurrentHashMap.put(uuid, schedule);

    }


    public void dealWithResponse(AIResponse aiResponse,String agentGroupId, Integer orgId) {
        String callId = aiResponse.getCallId();
        CallOutDetail callDetail = new CallOutDetail();
        callDetail.setCallId(new BigInteger(callId));
        callDetail.setOrgId(orgId);
        setDetailValues(aiResponse, callDetail, agentGroupId, orgId);
        callOutDetailService.save(callDetail);

        CallOutDetailRecord callDetailRecord = new CallOutDetailRecord();
        callDetailRecord.setCallDetailId(callDetail.getCallDetailId());
        callDetailRecord.setBotRecordFile(aiResponse.getWavFile());
        callOutDetailRecordService.save(callDetailRecord);
    }


    public void setDetailValues(AIResponse aiResponse, CallOutDetail callDetail, String agentGroupId, Integer orgId) {
        log.info("此时为非转人工状态下的客户识别结果，继续下一步处理");
        if (aiResponse.getAiResponseType() == EAIResponseType.NORMAL) {       //机器人正常放音
            log.info("sellbot结果为正常放音");
            callDetail.setCallDetailType(ECallDetailType.NORMAL.ordinal());
            fsBotHandler.playNormal(aiResponse.getCallId()+Constant.UUID_SEPARATE+orgId, aiResponse, callDetail);
        } else if (aiResponse.getAiResponseType() == EAIResponseType.TO_AGENT) {      //转人工
            log.info("sellbot的结果为转人工");
            callDetail.setCallDetailType(ECallDetailType.TOAGENT_INIT.ordinal());
            fsBotHandler.playToAgent(aiResponse,agentGroupId,orgId);
        } else if (aiResponse.getAiResponseType() == EAIResponseType.END) {           //sellbot提示结束，则在播放完毕后挂机
            log.info("sellbot的结果为通话结束");
            callDetail.setCallDetailType(ECallDetailType.END.ordinal());
            channelHelper.playFileToChannelAndHangup(aiResponse.getCallId()+Constant.UUID_SEPARATE+orgId,
                    aiResponse.getWavFile(), aiResponse.getWavDuration());
        } else {
            callDetail.setCallDetailType(ECallDetailType.ASR_EMPTY.ordinal());
            log.warn("未知的sellbot返回类型[{}], 跳过处理", aiResponse.getAiResponseType());
        }

        callDetail.setBotAnswerText(aiResponse.getResponseTxt());
        callDetail.setBotAnswerTime(new Date());
        callDetail.setAccurateIntent(aiResponse.getAccurateIntent());
        callDetail.setReason(aiResponse.getReason());
        callDetail.setKeywords(aiResponse.getKeyWords());

        callDetail.setWordSegmentResult(subWordSegment(aiResponse.getWordSegmentResult()));
    }

    private String subWordSegment(String wordSegment){

        String result="";
        if(wordSegment!=null && wordSegment.length()>250){
            try {
                String before = wordSegment.substring(0, 250);
                String[] beforeArr = before.split("', '");
                String end = beforeArr[beforeArr.length - 1];
                if (before.endsWith("', '")) {
                    result = before.substring(0, before.length() - end.length() - 8) + "']";
                } else {
                    result = before.substring(0, before.length() - end.length() - 4) + "']";
                }
            }catch (Exception e){
                log.error("subWordSegment出现异常",e);
            }
        }else{
            result = wordSegment;
        }
        return result;
    }


    /**
     * 停止计时器
     *
     * @param uuid 带orgId
     */
    public void stopAiCallNextTimer(String uuid) {
        if (scheduleConcurrentHashMap.containsKey(uuid)) {
            try {
                log.info("stop send aiCallNext timer task，uuid[{}]", uuid);
                scheduleConcurrentHashMap.get(uuid).cancel(false);
                scheduleConcurrentHashMap.remove(uuid);
            } catch (Exception ex) {
                log.error("stop send aiCallNext timer task has error:", ex);
            }
        }
    }

    /**
     * 返回标准的文件名称
     */
    public String getWavFilename(String filename, String tempId, String callId) {

        if (tempId.endsWith("_en")) {
            tempId = tempId.substring(0,tempId.length()-3)+"_rec";
        }

        if (filename != null) {

            if(filename.contains(",")){
                String[] fileArr = filename.split(",");
                String result="";
                for(String fileNameOne:fileArr){
                    String oneName = getOneWavFileName(fileNameOne,tempId,callId);
                    result += oneName+",";
                }
                return result.substring(0,result.length()-1);
            }else{
                return getOneWavFileName(filename,tempId,callId);
            }
        }
        return null;
    }

    public String getOneWavFileName(String filename, String tempId, String callId){
        if (filename.contains("/")) {
            String[] arr = filename.split("/");
            filename = arr[arr.length - 1];
        }
        if(!filename.endsWith(".wav")){
            filename =filename+".wav";
        }

        //查询是否存在tts文件
        Object value = redisUtil.get("callOutServer_ttsFile_"+callId);
        if(value!=null){
            List<TtsWav> list = (List<TtsWav>) value;
            if(list.size()>0){
                for(TtsWav ttsWav:list){
                    if(ttsWav.getFileName().equals(filename)){
                        return tempId+"/tts/"+callId+ "/" + filename;
                    }
                }
            }
        }
        return tempId + "/" + filename;
    }


}
