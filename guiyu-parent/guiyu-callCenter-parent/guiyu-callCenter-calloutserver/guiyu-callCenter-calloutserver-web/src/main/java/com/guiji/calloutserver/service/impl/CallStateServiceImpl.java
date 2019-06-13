package com.guiji.calloutserver.service.impl;

import com.guiji.auth.api.IOrg;
import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.constant.HangupDirectionEnum;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.manager.CallingCountManager;
import com.guiji.calloutserver.manager.DispatchManager;
import com.guiji.calloutserver.service.CallStateService;
import com.guiji.calloutserver.service.LineCountWService;
import com.guiji.component.result.Result;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.AiHangupReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    LineCountWService lineCountWService;
    @Autowired
    IOrg iorg;

    @Async
    @Override
    synchronized public void updateCallState() {

        log.info("---开始，将状态没有回调的通话记录,修改状态,回调---");
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        //转人工对话可能会比较长，不包括转人工的状态
        criteria.andCallStateLessThanOrEqualTo(ECallState.to_agent.ordinal())
                .andOrgIdIn(getAllOrgIds());

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -7);
        Date endTime  = c.getTime();

        criteria.andCreateTimeLessThan(endTime);
        log.info("------>>>endTime[{}]", endTime);

        List<CallOutPlan> list = callOutPlanMapper.selectByExample(example);

        CallOutPlan updateCallPlan = new CallOutPlan();
        updateCallPlan.setCallState(ECallState.hangup_fail.ordinal());

        updateCallPlan.setAccurateIntent("W");
        updateCallPlan.setReason("603");

        //回调调度中心和机器人中心
        log.info("updateCallState 查询的list大小:" + list.size());
        if (list != null && list.size() > 0) {

            for (CallOutPlan callOutPlan : list) {
                try {
                    AiHangupReq hangupReq = new AiHangupReq();
                    hangupReq.setSeqId(callOutPlan.getCallId().toString()+Constant.UUID_SEPARATE+callOutPlan.getOrgId());
                    hangupReq.setAiNo(callOutPlan.getAiId());
                    hangupReq.setPhoneNo(callOutPlan.getPhoneNum());
                    hangupReq.setUserId(String.valueOf(callOutPlan.getCustomerId()));
                    hangupReq.setTemplateId(callOutPlan.getTempId());

                    hangupReq.setTotalCallTime(callOutPlan.getBillSec());
                    if(callOutPlan.getHangupDirection()!=null){
                        if(callOutPlan.getHangupDirection()== HangupDirectionEnum.USER.ordinal()){
                            hangupReq.setHangUpType(1);
                        }else{
                            hangupReq.setHangUpType(2);
                        }
                    }else{
                        hangupReq.setHangUpType(0);
                    }
                    hangupReq.setHangUpHalfway(255);

                    Result.ReturnData result = robotRemote.aiHangup(hangupReq);
                    log.info("---->>回调机器人中心释放资源，返回结果result[{}],callId[{}]",result,callOutPlan.getCallId());
                } catch (Exception e) {
                    log.error("调用robotRemote aiHangup 出现异常:" + e);
                }

                try {
                    dispatchService.successSchedule(callOutPlan.getPlanUuid(),callOutPlan.getPhoneNum(),callOutPlan.getAccurateIntent()!=null? callOutPlan.getAccurateIntent():"W",
                            callOutPlan.getCustomerId(), callOutPlan.getLineId(),callOutPlan.getTempId(),true);
                    log.info("---->>回调dispatcher，返回结果,callId[{}]",callOutPlan.getCallId());
                } catch (Exception e) {
                    log.error("调用调度中心 successSchedule 出现异常:" + e);
                }
                //计数减一
                callingCountManager.removeOneCall();
                lineCountWService.addWCount(callOutPlan.getLineId(),callOutPlan.getOrgCode(),callOutPlan.getCustomerId());

                //修改calloutplan的状态，放到后面，防止刚修改完状态，就接到启动计划
                CallOutPlanExample exampleUpdate = new CallOutPlanExample();
                exampleUpdate.createCriteria().andCallIdEqualTo(callOutPlan.getCallId())
                        .andOrgIdEqualTo(callOutPlan.getOrgId());
                callOutPlanMapper.updateCallStateIntentReason(updateCallPlan, exampleUpdate);
            }

        }

        log.info("---结束，将状态没有回调的通话记录修改状态,回调---");
    }

    public List<Integer> getAllOrgIds()
    {
        Result.ReturnData<List<Integer>> resp = iorg.getAllOrgId();
        List<Integer> result = null;
        if (resp != null && resp.getBody() != null) {
            result = resp.getBody();
        }

        if(result == null)
        {
            result = new ArrayList<>();
        }

        return  result;
    }
}
