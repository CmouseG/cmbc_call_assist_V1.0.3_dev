package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.calloutserver.service.CallOutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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
        System.out.print(callOutPlan);
        return callOutPlan;
    }

//    @Override
//    public CallOutPlan findByPlanUuid(String planUuid) {
//        CallOutPlanExample example = new CallOutPlanExample();
//        example.createCriteria().andPlanUuidEqualTo(planUuid);
//        List<CallOutPlan> list = callOutPlanMapper.selectByExample(example);
//        return list.get(0);
//    }

    @Override
    public void update(CallOutPlan callplan) {
        callOutPlanMapper.updateByPrimaryKeySelective(callplan);
    }
}
