package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.manager.CallingCountManager;
import com.guiji.calloutserver.manager.DispatchManager;
import com.guiji.calloutserver.service.CallStateService;
import com.guiji.component.result.Result;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.AiHangupReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    @Autowired
    IRobotRemote robotRemote;
    @Autowired
    DispatchManager dispatchService;
    @Autowired
    CallingCountManager callingCountManager;

    @Async
    @Override
    synchronized public void updateCallState(Boolean isDelay, String serverid) {

        log.info("---开始，将状态没有回调的通话记录isDelay[{}]，serverid[{}]修改状态,回调---", isDelay, serverid);
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        criteria.andCallStateLessThanOrEqualTo(ECallState.agent_answer.ordinal());
        criteria.andServeridEqualTo(serverid);

        Calendar c = Calendar.getInstance();
        Date endTime = null;
        Date startTime = null;
        if(isDelay){
            c.add(Calendar.MINUTE, -10);
            endTime = c.getTime();
            c.add(Calendar.MINUTE, -20);
            startTime = c.getTime();
        }else{//启动的时候，认为服务停掉的72个小时内的数据都要检查一下
            c.add(Calendar.MINUTE, -5);
            endTime = c.getTime();
            c.add(Calendar.HOUR, -72);
            startTime = c.getTime();
        }

        criteria.andCreateTimeGreaterThan(startTime);
        criteria.andCreateTimeLessThan(endTime);
        log.info("------>>>startTime[{}],endTime[{}]", startTime, endTime);

        List<CallOutPlan> list = callOutPlanMapper.selectByExample(example);

        CallOutPlan updateCallPlan = new CallOutPlan();
        updateCallPlan.setCallState(ECallState.hangup_fail.ordinal());

        updateCallPlan.setAccurateIntent("W");
        updateCallPlan.setReason("系统通信异常");

        //回调调度中心和机器人中心
        log.info("updateCallState 查询的list大小:" + list.size());
        if (list != null && list.size() > 0) {

            for (CallOutPlan callOutPlan : list) {
                try {
                    AiHangupReq hangupReq = new AiHangupReq();
                    hangupReq.setSeqId(String.valueOf(callOutPlan.getCallId()));
                    hangupReq.setAiNo(callOutPlan.getAiId());
                    hangupReq.setPhoneNo(callOutPlan.getPhoneNum());
                    hangupReq.setUserId(String.valueOf(callOutPlan.getCustomerId()));
                    Result.ReturnData result = robotRemote.aiHangup(hangupReq);
                    log.info("---->>回调机器人中心释放资源，返回结果result[{}],callId[{}]",result,callOutPlan.getCallId());
                } catch (Exception e) {
                    log.error("调用robotRemote aiHangup 出现异常:" + e);
                }

                try {
                    dispatchService.successSchedule(callOutPlan.getPlanUuid(),callOutPlan.getPhoneNum(),callOutPlan.getAccurateIntent(), callOutPlan.getCustomerId(), callOutPlan.getLineId(),callOutPlan.getTempId());
                    log.info("---->>回调dispatcher，返回结果,callId[{}]",callOutPlan.getCallId());
                } catch (Exception e) {
                    log.error("调用调度中心 successSchedule 出现异常:" + e);
                }
                //计数减一
                callingCountManager.removeOneCall();
            }
            //修改calloutplan的状态，放到后面，防止刚修改完状态，就接到启动计划
            callOutPlanMapper.updateCallStateIntentReason(updateCallPlan, example);

        }

        log.info("---结束，将状态没有回调的通话记录isDelay[{}]，serverid[{}]修改状态,回调---");
    }
}
