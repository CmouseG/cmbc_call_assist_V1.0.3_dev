package com.guiji.calloutserver.manager.impl;

import com.google.common.base.Preconditions;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.enm.ECallDirection;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.helper.RequestHelper;
import com.guiji.calloutserver.manager.DispatchManager;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.service.DispatchLogService;
import com.guiji.component.result.Result;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.dispatch.model.DispatchPlan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/4 17:19
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class DispatchManagerImpl implements DispatchManager {
    @Autowired
    IDispatchPlanOut iDispatchPlanOutApi;

    @Autowired
    EurekaManager eurekaManager;

    @Autowired
    DispatchLogService dispatchLogService;

    /**
     * 拉取呼叫计划
     *
     * @param customerId
     * @param requestNum
     * @param lineId
     * @return
     */
    @Override
    public List<CallOutPlan> pullCallPlan(int customerId, Integer requestNum, Integer lineId) {
        List<CallOutPlan> callOutPlans = new ArrayList<>();
        try {
            Result.ReturnData disPatchResult = RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    Result.ReturnData returnData = iDispatchPlanOutApi.queryAvailableSchedules(customerId, requestNum, lineId);
                    log.info("------------- iDispatchPlanOutApi queryAvailableSchedules customerId[{}],requestNum[{}],lineId[{}],returnData[{}]", customerId, requestNum, lineId, returnData);
                    return returnData;
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    log.warn("获取调度中心呼叫计划出现异常[{}]", result);
                    //TODO: 报警
                }
            }, -1, 1, 3, 300, true);

            if(disPatchResult!=null && disPatchResult.getBody()!=null){
                List<DispatchPlan> dispatchPlans = (List<DispatchPlan>) disPatchResult.getBody();
                if (dispatchPlans != null && dispatchPlans.size() > 0) {
                    callOutPlans = toCallPlan(dispatchPlans);
                    dispatchLogService.endServiceRequestLog(callOutPlans.get(0).getCallId(),callOutPlans.get(0).getPhoneNum(),disPatchResult,"end dispacher queryAvailableSchedules");
                }
            }

        } catch (Exception ex) {
            log.warn("请求调度中心呼叫计划出现异常", ex);
            //TODO: 报警，请求调度中心数据异常
        }

        return callOutPlans;
    }

    /**
     * 将调度中心返回的数据，转为CalloutPlan
     *
     * @param dispatchPlans
     * @return
     */
    private List<CallOutPlan> toCallPlan(List<DispatchPlan> dispatchPlans) {
        Preconditions.checkNotNull(dispatchPlans, "将调度数据转为callPlan出现异常，调度数据为空");
        List<CallOutPlan> callOutPlans = new ArrayList<>(dispatchPlans.size());
        for (DispatchPlan dispatchPlan : dispatchPlans) {
            CallOutPlan callOutPlan = new CallOutPlan();
            callOutPlan.setCallState(ECallState.init.ordinal());
            callOutPlan.setCreateTime(new Date());
            callOutPlan.setCallDirection(ECallDirection.OUTBOUND.ordinal());
            callOutPlan.setCallId(dispatchPlan.getPlanUuid());
            callOutPlan.setPhoneNum(dispatchPlan.getPhone());
            callOutPlan.setCustomerId(dispatchPlan.getUserId().toString());
            callOutPlan.setLineId(dispatchPlan.getLine());
            callOutPlan.setServerid(eurekaManager.getInstanceId());
            callOutPlan.setHasTts(dispatchPlan.isTts());
            callOutPlan.setTempId(dispatchPlan.getRobot());

            callOutPlans.add(callOutPlan);
        }

        return callOutPlans;
    }

    /**
     *  回掉调度中心结果
     */
    @Override
    public void successSchedule(String callId, String phoneNo, String intent) {

        //调度中心
        Result.ReturnData returnData = null;
        dispatchLogService.startServiceRequestLog(callId,phoneNo, com.guiji.dispatch.model.Constant.MODULAR_STATUS_START, "start call dispatcher successSchedule");

        try
        {
            returnData = RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    return iDispatchPlanOutApi.successSchedule(callId);
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    //TODO: 报警
                    log.warn("调度中心回掉是否成功出错, 错误码为[{}]，错误信息[{}]", result.getCode(), result.getMsg());
                }
            }, -1, 1, 3, 120, true);
        } catch ( Exception e)
        {
            log.warn("调度中心回掉是否成功时出现异常", e);
        }

        log.info("======================successSchedule:callId[{}],phoneNo[{}],intent[{}]",callId,phoneNo,intent);
        dispatchLogService.endServiceRequestLog(callId,phoneNo, returnData, "end call dispatcher successSchedule");


    }
}
