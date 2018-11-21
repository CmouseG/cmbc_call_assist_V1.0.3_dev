package com.guiji.calloutserver.manager.impl;

import com.google.common.base.Preconditions;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.entity.*;
import com.guiji.calloutserver.manager.AIManager;
import com.guiji.calloutserver.service.CallOutPlanService;
import com.guiji.calloutserver.util.CommonUtil;
import com.guiji.component.result.Result;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/9 17:38
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class AIManagerImpl implements AIManager {

    @Autowired
    CallOutPlanService callOutPlanService;

    @Autowired
    IRobotRemote robotRemote;

    /**
     *  申请新的ai资源
     */
    public AIResponse applyAi(AIInitRequest aiRequest) throws Exception {

        try {
            AiCallStartReq aiCallStartReq = new  AiCallStartReq();
            aiCallStartReq.setPhoneNo(aiRequest.getPhoneNum());
            aiCallStartReq.setTemplateId(aiRequest.getTempId());
//            aiCallStartReq.setTemplateId("fpjkqzjhwsm");//test
            aiCallStartReq.setSeqid(aiRequest.getUuid());
            aiCallStartReq.setUserId(aiRequest.getUserId());
            Result.ReturnData<AiCallNext> result =  robotRemote.aiCallStart(aiCallStartReq);

            //申请资源失败，抛出异常
            Preconditions.checkState(result.success && result.getCode().equals(Constant.SUCCESS_COMMON), "failed robotRemote applyAi ");

            AiCallNext aiCallNext =  result.getBody();
            String resp = aiCallNext.getSellbotJson();
            log.info(" aiCallStart result.getBody().getSellbotJson: "+resp);

            SellbotResponse sellbotResponse = CommonUtil.jsonToJavaBean(resp, SellbotResponse.class);
            Preconditions.checkState(sellbotResponse!=null && sellbotResponse.isValid(), "invalid applyAi response");
            log.info("获取到的sellbot restore结果为: response[{}]", sellbotResponse);
            AIResponse aiResponse = new AIResponse();
            aiResponse.setResult(true);
            aiResponse.setMatched(true);
            aiResponse.setCallId(aiRequest.getUuid());
            aiResponse.setAccurateIntent(sellbotResponse.getAccurate_intent());
            aiResponse.setReason(sellbotResponse.getReason());
            aiResponse.setWavFile(sellbotResponse.getWav_filename());
            aiResponse.setAiId(aiCallNext.getAiNo());
            aiResponse.setResponseTxt(sellbotResponse.getAnswer());
            aiResponse.setWavDuration(5000d);
            return aiResponse;
        }catch (Exception ex){
            log.warn("sellbot的aiCallStart出现异常", ex);
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * 发起ai请求
     * @param aiRequest
     * @return
     * @throws Exception
     */
    public AIResponse sendAiRequest(AIRequest aiRequest) throws Exception {

        log.info("开始发起sellbot请求，request[{}]", aiRequest);
        AIResponse aiResponse;
        try {
            if(aiRequest.isCallMatch()){
                log.info("请求sellbot之前，先调用isMatch判断关键词是否匹配");
                SellbotIsMatchResponse sellbotIsMatchResponse = sendIsMatchRequest(aiRequest.getUuid(), aiRequest.getSentence());
                if(!sellbotIsMatchResponse.isMatched()){
                    log.info("该识别未匹配到sellbot关键字，退出");
                    aiResponse = new AIResponse();
                    aiResponse.setResult(true);
                    aiResponse.setMatched(false);
                    aiResponse.setResponseTxt(sellbotIsMatchResponse.getSentence());
                    return aiResponse;
                }
            }

            CallOutPlan callPlan = callOutPlanService.findByCallId(aiRequest.getUuid());

            AiCallNextReq aiCallNextReq = new  AiCallNextReq();
            aiCallNextReq.setPhoneNo(callPlan.getPhoneNum());
            aiCallNextReq.setAiNo(callPlan.getAiId());
            aiCallNextReq.setSentence(aiRequest.getSentence());
            aiCallNextReq.setSeqid(aiRequest.getUuid());
            aiCallNextReq.setTemplateId(callPlan.getTempId());
            aiCallNextReq.setUserId(callPlan.getCustomerId());
            Result.ReturnData<AiCallNext> result =  robotRemote.aiCallNext(aiCallNextReq);
            AiCallNext aiCallNext= result.getBody();
            String resp = aiCallNext.getSellbotJson();

            log.info("robotRemote.aiCallNext getSellbotJson[{}]", resp);
//            [{"state": "拒绝", "answer": "我觉得您要不还是到我们这儿来实地考察一下吧？", "wav_filename": "xtw_rec/164.wav", "sentence": "不行", "end": 0,
//                    "log_file": "/home/log_sellbot/logic/20181122_15000/95266lafb9a34f62b992d3a1ec00cfaf.log", "intent": "C", "accurate_intent": "C",
//                    "reason": "有效对话轮数:2", "user_attentions": "[('拒绝', 2), ('开场白', 1)]", "UserInfo": "", "used_time_ms": 2.729, "match_nothing": 1,
//                    "dtmfflag": "false", "dtmflen": "1", "dtmftimeout": "10", "dtmfend": "", "dtmfresult": "-1"}]

            SellbotResponse sellbotResponse = CommonUtil.jsonToJavaBean(resp, SellbotResponse.class);
            Preconditions.checkState(sellbotResponse!=null && sellbotResponse.isValid(), "invalid applyAi response");

            aiResponse = new AIResponse();
            aiResponse.setResult(true);
            aiResponse.setMatched(true);
            aiResponse.setAccurateIntent(sellbotResponse.getAccurate_intent());
            aiResponse.setAiId(aiCallNext.getAiNo());
            aiResponse.setCallId(callPlan.getCallId());
            aiResponse.setReason(sellbotResponse.getReason());
            aiResponse.setWavFile(sellbotResponse.getWav_filename());
            aiResponse.setResponseTxt(sellbotResponse.getAnswer());
            aiResponse.setAiResponseType(sellbotResponse.getEnd());
            return aiResponse;
        }catch (Exception ex){
            log.warn("sellbot的aiCallNext出现异常", ex);
            throw new Exception(ex.getMessage());
        }

    }


    /**
     * 判断是否需要打断
     * @return
     */

    private SellbotIsMatchResponse sendIsMatchRequest(String channelId, String sentence) throws Exception {
        try {

            AiCallLngKeyMatchReq aiCallLngKeyMatchReq = new AiCallLngKeyMatchReq();
            aiCallLngKeyMatchReq.setSentence(sentence);
            aiCallLngKeyMatchReq.setSeqid(channelId);
            Result.ReturnData<AiCallNext> result = robotRemote.aiLngKeyMatch(aiCallLngKeyMatchReq);

            String resp =result.getBody().getSellbotJson();
            log.info(" robotRemote sendIsMatchRequest getSellbotJson：[{}]", resp); // {"match_keywords":["",""],"matched":0,"sentence":"嗯可以"}
            SellbotIsMatchResponse sellbotResponse = CommonUtil.jsonToJavaBean(resp, SellbotIsMatchResponse.class);
            log.info("获取到的sellbot结果为[{}]", sellbotResponse);
            return sellbotResponse;
        }catch (Exception ex){
            log.warn("sellbot的aiLngKeyMatch出现异常", ex);
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public void releaseAi(String uuid) {
        AiHangupReq hangupReq = new AiHangupReq();
        hangupReq.setSeqid(uuid);
        robotRemote.aiHangup(hangupReq);
        log.info("------------------- releaseAi success ");
    }



}
