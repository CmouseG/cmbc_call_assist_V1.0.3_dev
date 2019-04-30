package com.guiji.service.impl;

import com.guiji.callcenter.dao.AgentMapper;
import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.TierMapper;
import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.config.FsBotConfig;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.entity.CallState;
import com.guiji.fs.FsManager;
import com.guiji.service.AuthService;
import com.guiji.service.CallOutPlanService;
import com.guiji.service.PhoneService;
import com.guiji.util.DateUtil;
import com.guiji.web.request.UpdateLabelRequest;
import com.guiji.web.response.QueryQueueCalls;
import com.guiji.web.response.QueryRecordInDetail;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CallOutPlanServiceImpl implements CallOutPlanService {
    @Autowired
    CallOutPlanMapper callOutPlanMapper;
    @Autowired
    AgentMapper agentMapper;
    @Autowired
    TierMapper tierMapper;
    @Autowired
    FsManager fsManager;
    @Autowired
    PhoneService phoneService;
    @Autowired
    FsBotConfig fsBotConfig;
    @Autowired
    IDispatchPlanOut iDispatchPlanOut;
    @Autowired
    AuthService authService;

    @Override
    public void insert(CallOutPlan callOutPlan){
        callOutPlanMapper.insert(callOutPlan);
    }

    @Override
    public void update(CallOutPlan callPlan){
        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria().andOrgIdEqualTo(callPlan.getOrgId())
                .andCallIdEqualTo(callPlan.getCallId());
        callOutPlanMapper.updateByExampleSelective(callPlan,example);
    }

    @Override
    public CallOutPlan findByCallId(String callId,Integer orgId) {
        CallOutPlanExample callOutPlanExample = new CallOutPlanExample();
        callOutPlanExample.createCriteria()
                .andCallIdEqualTo(new BigInteger(callId))
                .andOrgIdEqualTo(orgId);
        List<CallOutPlan> list =callOutPlanMapper.selectByExample(callOutPlanExample);
        return list.get(0);
    }

    @Override
    public QueryQueueCalls queueCalls(String queueId,Agent agent,Integer orgId,Integer authLevel) {

        List<Integer> orgIds = authService.getOrgIdsByAuthlevel(authLevel,orgId);
        Date date = DateUtil.initDateByDay();
        CallOutPlanExample callOutPlanExample = new CallOutPlanExample();
        callOutPlanExample.createCriteria().andAgentGroupIdEqualTo(queueId)
                .andAgentAnswerTimeGreaterThan(date)
                .andOrgIdIn(orgIds);
        Integer answeredCount=callOutPlanMapper.countByExample(callOutPlanExample);
        QueryQueueCalls queryQueueCalls = new QueryQueueCalls();
        if(answeredCount>0){
            queryQueueCalls.setAnsweredCount(answeredCount);
            queryQueueCalls.setWaitCount(fsManager.getWaitCount(queueId));
            queryQueueCalls.setQueueId(queueId);
        }
        if(agent!=null){
            CallOutPlanExample callOutPlanExample1 = new CallOutPlanExample();
            callOutPlanExample1.createCriteria().andAgentIdEqualTo(agent.getUserId()+"")
                    .andAgentAnswerTimeGreaterThan(date)
                    .andOrgIdIn(orgIds);
            Integer agentAnsweredCount = callOutPlanMapper.countByExample(callOutPlanExample1);
            queryQueueCalls.setAgentAnsweredCount(agentAnsweredCount);
        }
        return queryQueueCalls;
    }

    @Override
    public void updateLabel(UpdateLabelRequest request,Integer orgId,Integer authLevel) {
        List<Integer> orgIds = authService.getOrgIdsByAuthlevel(authLevel,orgId);
        CallOutPlanExample callOutPlanExample = new CallOutPlanExample();
        callOutPlanExample.createCriteria().andPlanUuidEqualTo(request.getCallRecordId())
                        .andOrgIdIn(orgIds);
        CallOutPlan callOutPlan = new CallOutPlan();
        callOutPlan.setAccurateIntent(request.getLabel());
        callOutPlanMapper.updateByExampleSelective(callOutPlan,callOutPlanExample);
        try {
            log.info("前端修改意向標簽的時候將標簽同步到Dispatch");
            //同步修改的意向标签到Dispatch
            iDispatchPlanOut.updateLabelByUUID(request.getCallRecordId(), request.getLabel());
        } catch (Exception e){
            log.info("同步修改的意向标签到Dispatch失败");
        }
    }

    @Override
    public QueryRecordInDetail queryCallrecord(String callrecordId) {
        CallOutPlanExample callOutPlanExample = new CallOutPlanExample();
        callOutPlanExample.createCriteria().andPlanUuidEqualTo(callrecordId);
        List<CallOutPlan> list =callOutPlanMapper.selectByExample(callOutPlanExample);
        CallOutPlan callOutPlan =list.get(0);
        QueryRecordInDetail record= new QueryRecordInDetail();
        record.setAnswerTime(callOutPlan.getAnswerTime()+"");
        record.setBillSec(callOutPlan.getBillSec());
        record.setPhone(callOutPlan.getPhoneNum());
        record.setLabel(callOutPlan.getAccurateIntent());
        record.setArea(phoneService.findLocationByPhone(callOutPlan.getPhoneNum()));
        return record;
    }

    @Override
    public QueryRecordInDetail getRealCallInfo(String mobile,Integer authLevel,Integer orgId) {
        log.info("获取号码[{}]的实时呼叫信息", mobile);
        List<Integer> orgIds = authService.getOrgIdsByAuthlevel(authLevel,orgId);
        CallOutPlanExample callOutPlanExample = new CallOutPlanExample();
        callOutPlanExample.createCriteria().andPhoneNumEqualTo(mobile).
                andCallStateBetween(CallState.answer.ordinal(), CallState.agent_answer.ordinal()).
                andOrgIdIn(orgIds);
        List<CallOutPlan> list =callOutPlanMapper.selectByExample(callOutPlanExample);
        CallOutPlan callOutPlan =list.get(0);
        Preconditions.checkNotNull(callOutPlan, "用户没有实时通话信息,号码:" + mobile);
        QueryRecordInDetail queryRecordInDetail = new QueryRecordInDetail();
        queryRecordInDetail.setPhone(callOutPlan.getPhoneNum());
        queryRecordInDetail.setLabel(callOutPlan.getAccurateIntent());
        queryRecordInDetail.setArea(phoneService.findLocationByPhone(callOutPlan.getPhoneNum()));
        queryRecordInDetail.setBillSec(callOutPlan.getBillSec());

        String answerTime = DateUtil.toString(callOutPlan.getAnswerTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC);
        queryRecordInDetail.setAnswerTime(answerTime);

        queryRecordInDetail.setCallrecordId(callOutPlan.getPlanUuid());
        return queryRecordInDetail;
    }

    @Override
    public CallOutPlan findByUuidOrAgentChannelUuid(String uuid) {
        CallOutPlanExample callOutPlanExample = new CallOutPlanExample();
        callOutPlanExample.or().andPlanUuidEqualTo(uuid).andAgentChannelUuidEqualTo(uuid);
        List<CallOutPlan> callOutPlanList = callOutPlanMapper.selectByExample(callOutPlanExample);
        if(callOutPlanList == null || callOutPlanList.size() == 0){
            log.info("当前不存在uuid或agentchanneluuid为[{}]的calloutplan", uuid);
            return null;
        }

        return callOutPlanList.get(0);
    }

    @Override
    public CallOutPlan findByAgentId(String agentId) {
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        criteria.andCallStateEqualTo(CallState.agent_answer.ordinal());
        criteria.andAgentIdEqualTo(agentId);

        List<CallOutPlan> callOutPlans = callOutPlanMapper.selectByExample(example);
        if(callOutPlans!=null && callOutPlans.size()>0){
            return callOutPlans.get(0);
        }else{
            return null;
        }
    }

}
