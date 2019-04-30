package com.guiji.service;

import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.entity.CallPlan;
import com.guiji.web.request.UpdateLabelRequest;
import com.guiji.web.response.QueryQueueCalls;
import com.guiji.web.response.QueryRecordInDetail;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 15:13
 * @Project：ccserver
 * @Description:
 */
public interface CallOutPlanService {
    void insert(CallOutPlan callOutPlan);

    void update(CallOutPlan callPlan);

    CallOutPlan findByCallId(String recordId,Integer orgId);

    QueryQueueCalls queueCalls(String queueId,Agent agent,Integer orgId,Integer authLevel);

    void updateLabel(UpdateLabelRequest request,Integer orgId,Integer authLevel);

    QueryRecordInDetail queryCallrecord(String callrecordId);

    QueryRecordInDetail getRealCallInfo(String mobile,Integer authLevel,Integer orgId);

    CallOutPlan findByUuidOrAgentChannelUuid(String uuid);

    /**
     * 获取座席正在服务的callplan
     * @param agentId
     * @return
     */
    CallOutPlan findByAgentId(String agentId);
}
