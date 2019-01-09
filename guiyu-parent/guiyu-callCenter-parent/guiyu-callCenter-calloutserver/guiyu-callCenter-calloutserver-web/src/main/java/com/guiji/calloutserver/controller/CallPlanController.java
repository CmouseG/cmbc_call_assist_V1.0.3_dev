package com.guiji.calloutserver.controller;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.api.ICallPlan;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.enm.ECallDirection;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.entity.DispatchPlan;
import com.guiji.calloutserver.eventbus.handler.CallPlanDispatchHandler;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.calloutserver.service.CallOutPlanService;
import com.guiji.component.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
public class CallPlanController implements ICallPlan {

    @Autowired
    EurekaManager eurekaManager;
    @Autowired
    FsAgentManager fsAgentManager;
    @Autowired
    CallPlanDispatchHandler callPlanDispatchHandler;
    @Autowired
    CallOutPlanService callOutPlanService;
    @Override
    public Result.ReturnData startMakeCall(@RequestBody List<DispatchPlan> dispatchPlans) {
        log.info("------start startMakeCall dispatchPlans size[{}]",dispatchPlans.size());

        List<CallOutPlan> callOutPlans = toCallPlan(dispatchPlans);

        for(CallOutPlan callOutPlan:callOutPlans){
            callPlanDispatchHandler.readyToMakeCall(callOutPlan);
        }
        log.info(">>>>>>>end startMakeCall dispatchPlans");
        return Result.ok();
    }

    @Override
    public Result.ReturnData<Boolean> isCallEnd(@RequestParam(value = "planUuid", required = true) String planUuid) {
        //需要在planUuid字段上加索引
        CallOutPlan callOutPlan = callOutPlanService.findByPlanUuid(planUuid);
        if(callOutPlan!=null){
            if(callOutPlan.getCallState() > ECallState.agent_answer.ordinal()){
                return Result.ok(true);
            }else{
                return Result.ok(false);
            }
        }
        return Result.error(Constant.ERROR_UUID_NOTFIND);
    }

    /**
     * 将调度中心推过来的数据，转为CalloutPlan
     */
    private List<CallOutPlan> toCallPlan(List<DispatchPlan> dispatchPlans) {
        List<CallOutPlan> callOutPlans = new ArrayList<>(dispatchPlans.size());
        for (DispatchPlan dispatchPlan : dispatchPlans) {
            CallOutPlan callOutPlan = new CallOutPlan();
            callOutPlan.setCallState(ECallState.init.ordinal());
            callOutPlan.setCreateTime(new Date());
            callOutPlan.setCallDirection(ECallDirection.OUTBOUND.ordinal());
            callOutPlan.setPlanUuid(dispatchPlan.getPlanUuid());
            callOutPlan.setPhoneNum(dispatchPlan.getPhone());
            callOutPlan.setCustomerId(dispatchPlan.getUserId());
            callOutPlan.setLineId(dispatchPlan.getLine());
            callOutPlan.setServerid(eurekaManager.getInstanceId());
            callOutPlan.setHasTts(dispatchPlan.isTts());
            callOutPlan.setTempId(dispatchPlan.getTempId());
            callOutPlan.setOrgCode(dispatchPlan.getOrgCode());
            callOutPlan.setBatchId(dispatchPlan.getBatchId());

            callOutPlans.add(callOutPlan);
        }

        return callOutPlans;
    }

    @Override
    public Result.ReturnData<Map<String, Boolean>> isTempOk(@RequestBody List<String> tempIdList) {
        log.info("收到isTempOk请求 tempIdList[{}]", tempIdList);
        Map<String,Boolean> map = new HashMap();
        for(String temId:tempIdList){
            map.put(temId,isTempOk(temId));
        }
        return Result.ok(map);
    }

    @Override
    public Result.ReturnData<Integer> getNotEndCallCount() {
        //需要在状态字段上加索引
        int num = callOutPlanService.getNotEndCallCount();
        return Result.ok(num);
    }

    public boolean isTempOk(String tempId) {
       try{
            Result.ReturnData<Boolean> result =  fsAgentManager.istempexist(tempId);
            if(!result.getBody()){
                log.warn("启动呼叫计划失败，模板不存在[{}]", tempId);
                return false;
            }else{
                log.info("模板检查通过，返回结果为[{}]", result.getBody());
            }
        }catch (Exception e){
            log.warn("启动呼叫计划失败，出现异常,模板不存在[{}]", tempId);
            return false;
        }

        log.info("开始获取模板录音时长[{}]", tempId);
        try {
            Map<String, Double> map = fsAgentManager.getwavlength(tempId);
            if(map==null || map.size()==0){
                log.warn("启动呼叫计划失败，录音不存在，下载录音文件时长失败[{}]", tempId);
                return false;
            }else{
                log.info("模板录音获取成功，获取的数量为[{}]", map.size());
            }
        }catch (Exception e){
            log.warn("启动呼叫计划失败，下载录音文件时长失败[{}]", tempId);
            return false;
        }

        return true;
    }
}