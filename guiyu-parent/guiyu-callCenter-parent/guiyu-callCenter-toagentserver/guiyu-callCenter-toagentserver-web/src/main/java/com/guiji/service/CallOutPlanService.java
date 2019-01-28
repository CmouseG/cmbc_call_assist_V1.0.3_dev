package com.guiji.service;

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

    CallOutPlan findByCallId(String recordId);

    QueryQueueCalls queueCalls(String queueId);

    void updateLabel(UpdateLabelRequest request);

    QueryRecordInDetail queryCallrecord(String callrecordId);

    QueryRecordInDetail getRealCallInfo(String mobile);

    CallOutPlan findByUuidOrAgentChannelUuid(String uuid);

    /**
     * 获取座席正在服务的callplan
     * @param agentId
     * @return
     */
    CallOutPlan findByAgentId(String agentId);
}
