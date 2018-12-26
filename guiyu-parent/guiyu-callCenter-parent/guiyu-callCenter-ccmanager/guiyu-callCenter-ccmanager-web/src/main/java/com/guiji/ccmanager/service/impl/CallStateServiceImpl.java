package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.service.CallStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/25 0025 14:59
 * @Description:
 */
@Service
@Slf4j
public class CallStateServiceImpl implements CallStateService {

    @Autowired
    CallOutPlanMapper callOutPlanMapper;

    @Async
    public void updateCallState(){

        log.info("---开始将10分钟之前，状态没有回调的通话记录，修改状态---");
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
//        criteria.andLineIdEqualTo(Integer.valueOf(lineId));
        criteria.andCallStateBetween(Constant.CALLSTATE_INIT,Constant.CALLSTATE_AGENT_ANSWER);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -10);
        criteria.andCreateTimeLessThan(c.getTime());

        CallOutPlan updateCallPlan = new CallOutPlan();
        updateCallPlan.setCallState(Constant.CALLSTATE_HANGUP_FAIL);
        updateCallPlan.setAccurateIntent("W");
        updateCallPlan.setReason("系统通信异常");
        callOutPlanMapper.updateByExampleSelective(updateCallPlan,example);
        log.info("---结束将10分钟之前，状态没有回调的通话记录，修改状态---");
    }
}
