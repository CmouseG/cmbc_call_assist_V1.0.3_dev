package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.service.CallOutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/1 15:31
 * @Project：guiyu-parent
 * @Description:
 */
@Service
public class CallOutPlanServiceImpl implements CallOutPlanService {
    @Autowired
    CallOutPlanMapper callOutPlanMapper;

    public void save(CallOutPlan callPlan){
        callOutPlanMapper.insert(callPlan);
    }

    @Override
    public void save(List<CallOutPlan> totalCallPlans) {
        for(CallOutPlan callOutPlan: totalCallPlans){
            callOutPlanMapper.insert(callOutPlan);
        }
    }

    @Override
    public CallOutPlan findByCallId(String callId) {
        CallOutPlan callOutPlan = callOutPlanMapper.selectByPrimaryKey(callId);
        return callOutPlan;
    }
}
