package com.guiji.service.impl;

import com.guiji.callcenter.dao.entity.CallInPlan;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.entity.CallPlan;
import com.guiji.entity.ECallDirection;
import com.guiji.service.*;
import com.guiji.web.request.UpdateLabelRequest;
import com.guiji.web.response.QueryQueueCalls;
import com.guiji.web.response.QueryRecordInDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/16 17:32
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class CallPlanServiceImpl implements CallPlanService {
    @Autowired
    CallInPlanService callInPlanService;

    @Autowired
    CallInRecordService callInRecordService;

    @Autowired
    CallOutPlanService callOutPlanService;

    @Autowired
    CallOutRecordService callOutRecordService;

    @Override
    public void update(CallPlan callPlan) {
        log.debug("开始更新CallPlan[{}]", callPlan);
        if(callPlan.getCallDirection() == ECallDirection.OUTBOUND.ordinal()){
            CallOutPlan callOutPlan = new CallOutPlan();
            BeanUtils.copyProperties(callPlan, callOutPlan);
            callOutPlanService.update(callOutPlan);
        }else if(callPlan.getCallDirection() == ECallDirection.INBOUND.ordinal()){
            CallInPlan callInPlan = new CallInPlan();
            BeanUtils.copyProperties(callPlan, callInPlan);
            callInPlanService.update(callInPlan);
        }else{
            log.warn("保存callPlan失败，因非法的callDirection[{}]", callPlan.getCallDirection());
        }
    }

    @Override
    public CallPlan findByCallId(String callId, ECallDirection callDirection, Integer orgId) {
        log.debug("开始查找指定的通话记录，recordId[{}], callDirection[{}]", callId, callDirection);
        CallPlan callplan = new CallPlan();
        if(callDirection == ECallDirection.OUTBOUND){
            CallOutPlan callOutPlan = callOutPlanService.findByCallId(callId, orgId);
            BeanUtils.copyProperties(callOutPlan, callplan);
        }else if(callDirection == ECallDirection.INBOUND){
            CallInPlan callInPlan = callInPlanService.findByCallId(callId);
            BeanUtils.copyProperties(callInPlan, callplan);
        }else{
            log.warn("查询callPlan[{}]失败，因非法的callDirection[{}]", callId, callDirection);
            return null;
        }

        return callplan;
    }

/*    @Override
    public QueryQueueCalls queueCalls(String queueId, ECallDirection callDirection) {
        log.debug("开始queueCalls，queueId[{}], callDirection[{}]", queueId, callDirection);
        if(callDirection == ECallDirection.OUTBOUND){
            return callOutPlanService.queueCalls(queueId,null);
        }else if(callDirection == ECallDirection.INBOUND){
            return callInPlanService.queueCalls(queueId);
        }else{
            log.warn("开始queueCalls[{}]失败，因非法的callDirection[{}]", queueId, callDirection);
            return null;
        }
    }*/

/*    @Override
    public void updateLabel(UpdateLabelRequest request, ECallDirection callDirection) {
        log.debug("开始updateLabel，request[{}], callDirection[{}]", request, callDirection);
        if(callDirection == ECallDirection.OUTBOUND){
            callOutPlanService.updateLabel(request);
        }else if(callDirection == ECallDirection.INBOUND){
            callInPlanService.updateLabel(request);
        }else{
            log.warn("updateLabel失败，因非法的callDirection[{}]", callDirection);
        }
    }*/

/*    @Override
    public QueryRecordInDetail getRealCallInfo(String mobile, ECallDirection callDirection) {
        log.debug("开始getRealCallInfo，mobile[{}], callDirection[{}]", mobile, callDirection);
        if(callDirection == ECallDirection.OUTBOUND){
            return callOutPlanService.getRealCallInfo(mobile);
        }else if(callDirection == ECallDirection.INBOUND){
            return callInPlanService.getRealCallInfo(mobile);
        }else{
            log.warn("getRealCallInfo失败，因非法的callDirection[{}]", callDirection);
            return null;
        }
    }*/

    @Override
    public CallPlan findByUuidOrAgentChannelUuid(String uuid, ECallDirection callDirection) {
        log.debug("开始findByUuidOrAgentChannelUuid，uuid[{}]", uuid);

        CallPlan callPlan = new CallPlan();
        if(callDirection == ECallDirection.OUTBOUND){
            CallOutPlan callOutPlan = callOutPlanService.findByUuidOrAgentChannelUuid(uuid);
            if(callOutPlan!=null){
                BeanUtils.copyProperties(callOutPlan, callPlan);
                return callPlan;
            }
        }else if(callDirection == ECallDirection.INBOUND){
            CallInPlan callInPlan = callInPlanService.findByUuidOrAgentChannelUuid(uuid);
            if(callInPlan!=null){
                BeanUtils.copyProperties(callInPlan, callPlan);
                return callPlan;
            }
        }else{
            log.warn("findByUuidOrAgentChannelUuid失败，因非法的callDirection[{}]", callDirection);
            return null;
        }

        return null;
    }

    @Override
    public CallPlan findByAgentId(String agentId) {
        log.debug("findByAgentId，uuid[{}]", agentId);

        CallPlan callPlan = new CallPlan();
        CallOutPlan callOutPlan = callOutPlanService.findByAgentId(agentId);
        if(callOutPlan!=null){
            BeanUtils.copyProperties(callOutPlan, callPlan);
            return callPlan;
        }else{
            CallInPlan callInPlan = callInPlanService.findByAgentId(agentId);
            if(callInPlan!=null){
                BeanUtils.copyProperties(callInPlan, callPlan);
                return callPlan;
            }
        }

        return null;
    }
}
