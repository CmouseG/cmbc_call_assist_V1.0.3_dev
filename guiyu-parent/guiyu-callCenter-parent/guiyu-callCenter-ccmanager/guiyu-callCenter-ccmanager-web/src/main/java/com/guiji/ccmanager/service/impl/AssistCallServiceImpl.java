package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.ccmanager.service.AssistCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class AssistCallServiceImpl implements AssistCallService {


    @Autowired
    CallOutPlanMapper callOutPlanMapper;

    @Override
    public CallOutPlan getCallOutplan(BigInteger callId, Integer orgId) {

        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria().andCallIdEqualTo(callId).andOrgIdEqualTo(orgId);
        List<CallOutPlan> list = callOutPlanMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
