package com.guiji.calloutserver.eventbus.handler;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.AsyncEventBus;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.eventbus.event.CallResourceReadyEvent;
import com.guiji.calloutserver.helper.RequestHelper;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.calloutserver.service.CallOutPlanService;
//import com.guiji.calloutserver.service.DispatchLogService;
import com.guiji.component.result.Result;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.AiCallApplyReq;
import com.guiji.robot.model.AiCallNext;
import com.guiji.robot.model.TtsVoiceReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/2 14:56
 * @Project：guiyu-parent
 * @Description: 用于检查呼叫相关的资源是否齐备，如模板、tts、sellbot等
 */
@Slf4j
@Component
public class CallResourceChecker {
    @Autowired
    AsyncEventBus asyncEventBus;
    @Autowired
    FsAgentManager fsAgentManager;
    @Autowired
    IRobotRemote robotRemote;
    @Autowired
    CallOutPlanService callOutPlanService;
//    @Autowired
//    DispatchLogService dispatchLogService;

    /**
     * 检查呼叫依赖的各项资源是否齐备，只有完全齐备，才允许进行后面的呼叫
     * @param callOutPlan
     */
    public void checkCallResources(CallOutPlan callOutPlan){
        //启动多个线程，用于检测各个资源的状态
        checkTemp(callOutPlan.getTempId());
//        checkTts(callOutPlan);
        checkSellbot(callOutPlan);

        asyncEventBus.post(new CallResourceReadyEvent(callOutPlan));
        log.info("---------------------CallResourceReadyEvent post "+callOutPlan.getPhoneNum());
    }

    /**
     * 检查sellbot资源是否就位
     * @param callOutPlan
     */
    public void checkSellbot(CallOutPlan callOutPlan) {
        log.info("检查sellbot资源，callOutPlan[{}]", callOutPlan);

        AiCallApplyReq aiCallApplyReq = new AiCallApplyReq();
        aiCallApplyReq.setPhoneNo(callOutPlan.getPhoneNum());
        aiCallApplyReq.setSeqId(callOutPlan.getCallId().toString()+ Constant.UUID_SEPARATE+callOutPlan.getOrgId());
        aiCallApplyReq.setTemplateId(callOutPlan.getTempId());
        aiCallApplyReq.setUserId(String.valueOf(callOutPlan.getCustomerId()));
        aiCallApplyReq.setDisSeqId(callOutPlan.getPlanUuid());

        log.info("发起的ai请求为[{}]", aiCallApplyReq);

        Result.ReturnData<AiCallNext> returnData = null;
        final String[] msg = {"没有机器人资源"};
//        dispatchLogService.startServiceRequestLog(callOutPlan.getPlanUuid(),callOutPlan.getPhoneNum(), com.guiji.dispatch.model.Constant.MODULAR_STATUS_START, "开始向机器人中心请求接口aiCallApply");
        try {
            returnData = RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    return robotRemote.aiCallApply(aiCallApplyReq);
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    //TODO: 报警
                    msg[0] = result.getMsg();
                    log.warn("申请机器人资源失败, 错误码为[{}]，错误信息[{}]", result.getCode(), result.getMsg());
                }
                @Override
                public boolean trueBreakOnCode(String code) {
                    if(code.equals("00060001")){
                        return true;
                    }
                    return false;
                }
            }, 5, 1, 5,180,true);
        } catch (Exception e) {
            log.warn("在初始化fsline时出现异常", e);
        }
//        dispatchLogService.endServiceRequestLog(callOutPlan.getPlanUuid(),callOutPlan.getPhoneNum(), returnData, "结束向机器人中心请求接口aiCallApply");
        Preconditions.checkNotNull(returnData, msg[0]);

        log.info("sellbot资源检查通过，返回结果[{}]", returnData.getBody());
        String aiNo = returnData.getBody().getAiNo();
        callOutPlan.setAiId(aiNo);
        callOutPlanService.update(callOutPlan);
    }

    /**
     * 检查tts是否就位
     * @param callOutPlan
     */
    private void checkTts(CallOutPlan callOutPlan) {
        //TODO:tts资源检查
    }

    /**
     * 检查模板是否就位
     */
    public void checkTemp(String tempId) {
        fsAgentManager.istempexist(tempId);
        fsAgentManager.getwavlength(tempId);
    }
}
