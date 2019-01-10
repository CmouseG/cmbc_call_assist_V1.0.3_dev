package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.service.CallOutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Calendar;
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

    public void add(CallOutPlan callPlan){
        callOutPlanMapper.insert(callPlan);
    }

    @Override
    public CallOutPlan findByCallId(BigInteger callId) {
        CallOutPlan callOutPlan = callOutPlanMapper.selectByPrimaryKey(callId);
        return callOutPlan;
    }

    @Override
    public CallOutPlan findByPlanUuid(String planUuid) {
        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria()
                .andPlanUuidEqualTo(planUuid);
        List<CallOutPlan> list = callOutPlanMapper.selectByExample(example);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void update(CallOutPlan callplan) {
        callOutPlanMapper.updateByPrimaryKeySelective(callplan);
    }


    @Override
    public int getNotEndCallCount() {
        //只统计当前1个小时内未打完的电话
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -1);

        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria()
                .andCallStateLessThan(ECallState.hangup_ok.ordinal())
                .andCreateTimeGreaterThan(c.getTime());//是否要在该字段加索引，或者分区
        return callOutPlanMapper.countByExample(example);
    }
}
