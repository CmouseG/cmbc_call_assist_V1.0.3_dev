package com.guiji.calloutserver.manager.impl;

import com.google.common.base.Preconditions;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.entity.AIInitRequest;
import com.guiji.calloutserver.entity.AIRequest;
import com.guiji.calloutserver.entity.AIResponse;
import com.guiji.calloutserver.entity.SellbotResponse;
import com.guiji.calloutserver.helper.RequestHelper;
import com.guiji.calloutserver.helper.RobotNextHelper;
import com.guiji.calloutserver.manager.AIManager;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.calloutserver.service.CallOutPlanService;
import com.guiji.calloutserver.service.DispatchLogService;
import com.guiji.calloutserver.util.CommonUtil;
import com.guiji.component.result.Result;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.AiCallNext;
import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.model.AiFlowMsgPushReq;
import com.guiji.robot.model.AiHangupReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;

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

    @Autowired
    FsAgentManager fsAgentManager;

    @Autowired
    DispatchLogService dispatchLogService;
    @Autowired
    RobotNextHelper robotNextHelper;

    /**
     * 申请新的ai资源
     */
    public AIResponse applyAi(AIInitRequest aiRequest) throws Exception {

        AiCallStartReq aiCallStartReq = new AiCallStartReq();
        aiCallStartReq.setPhoneNo(aiRequest.getPhoneNum());
        aiCallStartReq.setAiNo(aiRequest.getAiId());
        String tempId = aiRequest.getTempId();
        aiCallStartReq.setTemplateId(tempId);
        aiCallStartReq.setSeqId(String.valueOf(aiRequest.getUuid()));
        aiCallStartReq.setUserId(aiRequest.getUserId());

        dispatchLogService.startServiceRequestLog(aiRequest.getPlanUuid(),aiRequest.getPhoneNum(), com.guiji.dispatch.model.Constant.MODULAR_STATUS_START, "开始向机器人中心请求接口aiCallStart");
        Result.ReturnData returnData = RequestHelper.loopRequest(new RequestHelper.RequestApi() {
            @Override
            public Result.ReturnData execute() {
                return robotRemote.aiCallStart(aiCallStartReq);
            }

            @Override
            public void onErrorResult(Result.ReturnData result) {
                log.warn("调用机器人中心aiCallStart失败, 错误码为[{}]，错误信息[{}]", result.getCode(), result.getMsg());
            }

            @Override
            public boolean trueBreakOnCode(String code) {
                if(code.equals("00060001")){
                    return true;
                }
                return false;
            }
        }, 4, 1, 1, 60, true);
        dispatchLogService.endServiceRequestLog(aiRequest.getPlanUuid(),aiRequest.getPhoneNum(), returnData, "结束向机器人中心请求接口aiCallStart");

        if (returnData == null) {
            log.warn("请求ai资源失败");
            return null;
        }

        Result.ReturnData<AiCallNext> result = returnData;

        //申请资源失败，抛出异常
        Preconditions.checkState(result.success && result.getCode().equals(Constant.SUCCESS_COMMON), "failed robotRemote applyAi ");

        AiCallNext aiCallNext = result.getBody();
        String resp = aiCallNext.getSellbotJson();
        log.info(" aiCallStart result.getBody().getSellbotJson: " + resp);

        SellbotResponse sellbotResponse = CommonUtil.jsonToJavaBean(resp, SellbotResponse.class);
        Preconditions.checkState(sellbotResponse != null && sellbotResponse.isValid(), "invalid applyAi response");
        log.info("获取到的sellbot restore结果为: response[{}]", sellbotResponse);
        AIResponse aiResponse = new AIResponse();
        aiResponse.setResult(true);
        aiResponse.setMatched(true);
        aiResponse.setCallId(aiRequest.getUuid());
        aiResponse.setAccurateIntent(sellbotResponse.getAccurate_intent());
        aiResponse.setReason(sellbotResponse.getReason());

        String wavFilename = robotNextHelper.getWavFilename(sellbotResponse.getWav_filename(),tempId,aiRequest.getUuid());
        Preconditions.checkNotNull(wavFilename, "wavFilename is null error");
        aiResponse.setWavFile(wavFilename);
        aiResponse.setAiId(aiCallNext.getAiNo());
        aiResponse.setResponseTxt(sellbotResponse.getAnswer());
        Double wavDruation = fsAgentManager.getWavDruation(tempId, wavFilename,aiRequest.getUuid());
        Preconditions.checkNotNull(wavDruation, "wavDruation is null error");
        aiResponse.setWavDuration(wavDruation);
        return aiResponse;
    }


    /**
     * 发起ai请求
     *
     * @param aiRequest
     * @return
     * @throws Exception
     */
    public void sendAiRequest(AIRequest aiRequest) throws Exception {

        log.info("开始发起sellbot请求，request[{}]", aiRequest);
        try {
            CallOutPlan callPlan = callOutPlanService.findByCallId(new BigInteger(aiRequest.getUuid()));

            AiFlowMsgPushReq aiFlowMsgPushReq = new AiFlowMsgPushReq();
            aiFlowMsgPushReq.setAiNo(callPlan.getAiId());
            aiFlowMsgPushReq.setSentence(aiRequest.getSentence());
            aiFlowMsgPushReq.setSeqId(aiRequest.getUuid());
            aiFlowMsgPushReq.setTemplateId(callPlan.getTempId());
            aiFlowMsgPushReq.setTimestamp(new Date().getTime());
            aiFlowMsgPushReq.setUserId(String.valueOf(callPlan.getCustomerId()));
            dispatchLogService.startServiceRequestLog(aiRequest.getUuid(),callPlan.getPhoneNum(), com.guiji.dispatch.model.Constant.MODULAR_STATUS_START, "开始向机器人中心请求接口flowMsgPush");
            Result.ReturnData returnData = robotRemote.flowMsgPush(aiFlowMsgPushReq);
            dispatchLogService.endServiceRequestLog(aiRequest.getUuid(),callPlan.getPhoneNum(), returnData, "结束向机器人中心请求接口flowMsgPush");

        } catch (Exception ex) {
            log.warn("sellbot的flowMsgPush出现异常", ex);
            throw new Exception(ex.getMessage());
        }

    }

    @Override
    public void releaseAi(CallOutPlan callOutPlan) {
        AiHangupReq hangupReq = new AiHangupReq();
        hangupReq.setSeqId(callOutPlan.getPlanUuid());
        hangupReq.setAiNo(callOutPlan.getAiId());
        hangupReq.setPhoneNo(callOutPlan.getPhoneNum());
        hangupReq.setUserId(String.valueOf(callOutPlan.getCustomerId()));

        log.info("释放机器人资源，aiId[{}]", callOutPlan.getAiId());
        dispatchLogService.startServiceRequestLog(callOutPlan.getPlanUuid(),callOutPlan.getPhoneNum(), com.guiji.dispatch.model.Constant.MODULAR_STATUS_START, "开始向机器人中心请求挂断");
        Result.ReturnData returnData = null;
        try {
            returnData = RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    return robotRemote.aiHangup(hangupReq);
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    //TODO: 报警
                    log.warn("释放机器人资源出错, 错误码为[{}]，错误信息[{}]", result.getCode(), result.getMsg());
                }

                @Override
                public boolean trueBreakOnCode(String code) {
                    if(code.equals("00060001") || code.equals("00060006")){
                        return true;
                    }
                    return false;
                }
            }, 20, 1, 10, 180, true);
        } catch (Exception e) {
            log.warn("在释放机器人资源是出现异常, aiId:"+callOutPlan.getAiId(), e);
        }
        dispatchLogService.endServiceRequestLog(callOutPlan.getPlanUuid(),callOutPlan.getPhoneNum(), returnData, "结束向机器人中心请求挂断");

        log.info("------------------- releaseAi success aino:" + hangupReq.getAiNo());
    }


}
