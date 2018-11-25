package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.service.CallOutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void add(CallOutPlan callPlan){
        callOutPlanMapper.insert(callPlan);
    }

    @Override
    public CallOutPlan findByCallId(String callId) {
        CallOutPlan callOutPlan = callOutPlanMapper.selectByPrimaryKey(callId);
        return callOutPlan;
    }

    @Override
    public void update(CallOutPlan callplan) {
        callOutPlanMapper.updateByPrimaryKeySelective(callplan);
    }
}
