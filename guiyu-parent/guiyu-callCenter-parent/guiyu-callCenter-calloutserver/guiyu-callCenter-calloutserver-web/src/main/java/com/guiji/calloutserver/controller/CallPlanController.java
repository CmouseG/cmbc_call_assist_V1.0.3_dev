package com.guiji.calloutserver.controller;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.api.ICallPlan;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.enm.ECallDirection;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.entity.CallEndIntent;
import com.guiji.calloutserver.entity.DispatchPlan;
import com.guiji.calloutserver.eventbus.handler.CallPlanDispatchHandler;
import com.guiji.calloutserver.manager.CallingCountManager;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.calloutserver.service.CallOutPlanService;
import com.guiji.calloutserver.service.TempReadyService;
import com.guiji.component.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    CallPlanDispatchHandler callPlanDispatchHandler;
    @Autowired
    CallOutPlanService callOutPlanService;
    @Value("${callCount.max}")
    String callCountMax;
    @Autowired
    TempReadyService tempReadyService;
    @Autowired
    CallingCountManager callingCountManager;

    @Override
    public Result.ReturnData startMakeCall(@RequestBody DispatchPlan dispatchPlan) {

        log.info("------start startMakeCall dispatchPlan[{}]",dispatchPlan);//注释掉

        if(callingCountManager.getCallCount()<Integer.valueOf(callCountMax)){
            if(tempReadyService.isTempOk(dispatchPlan.getTempId())){
                CallOutPlan callOutPlan = toCallPlan(dispatchPlan);

                callPlanDispatchHandler.readyToMakeCall(callOutPlan);

                log.info(">>>>>>>end startMakeCall dispatchPlan,,ok");//注释掉
                return Result.ok();
            }else{
                log.info(">>>>>>>end startMakeCall,temp not ok dispatchPlan[{}]",dispatchPlan);
                return Result.error(Constant.ERROR_TEMP_NOT_AVAILABLE);
            }
        }else{
            log.info(">>>>>>>end startMakeCall,call count too big[{}]",dispatchPlan);
            return Result.error(Constant.ERROR_CALLCOUNT_OUTLIMIT);
        }
    }

    @Override
    public Result.ReturnData<CallEndIntent> isCallEnd(@RequestParam(value = "planUuid", required = true) String planUuid) {
        //需要在planUuid字段上加索引
        CallOutPlan callOutPlan = callOutPlanService.findByPlanUuid(planUuid);
        if(callOutPlan!=null){
            CallEndIntent callEndIntent = new CallEndIntent();
            if(callOutPlan.getCallState() > ECallState.agent_answer.ordinal()){
                callEndIntent.setEnd(true);
            }else{
                callEndIntent.setEnd(false);
            }
            if(callOutPlan.getAccurateIntent()!=null){
                callEndIntent.setIntent(callOutPlan.getAccurateIntent());
            }
            return Result.ok(callEndIntent);

        }
        return Result.error(Constant.ERROR_UUID_NOTFIND);
    }

    /**
     * 将调度中心推过来的数据，转为CalloutPlan
     */
    private CallOutPlan toCallPlan(DispatchPlan dispatchPlan) {

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

        return callOutPlan;
    }

/*    @Override
    public Result.ReturnData<Map<String, Boolean>> isTempOk(@RequestBody List<String> tempIdList) {
        log.info("收到isTempOk请求 tempIdList[{}]", tempIdList);
        Map<String,Boolean> map = new HashMap();
        for(String temId:tempIdList){
            map.put(temId,isTempOk(temId));
        }
        return Result.ok(map);
    }*/

    @Override
    public Result.ReturnData<Integer> getNotEndCallCount() {
        //需要在状态字段上加索引
        int num = callOutPlanService.getNotEndCallCount();
        return Result.ok(num);
    }


}