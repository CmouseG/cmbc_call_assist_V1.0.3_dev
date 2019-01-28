package com.guiji.service;

import com.guiji.callcenter.dao.entity.CallInPlan;
import com.guiji.web.request.UpdateLabelRequest;
import com.guiji.web.response.QueryQueueCalls;
import com.guiji.web.response.QueryRecordInDetail;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 15:13
 * @Project：ccserver
 * @Description:
 */
public interface CallInPlanService {
    void update(CallInPlan callPlan);

    CallInPlan queueCallsfindByRecordId(String recordId);

    QueryQueueCalls queueCalls(String queueId);

    void updateLabel(UpdateLabelRequest request);

    QueryRecordInDetail queryCallrecord(String callrecordId);

    QueryRecordInDetail getRealCallInfo(String mobile);

    CallInPlan findByCallId(String recordId);

    CallInPlan findByUuidOrAgentChannelUuid(String uuid);

    /**
     * 获取座席正在服务的callplan
     * @param agentId
     * @return
     */
    CallInPlan findByAgentId(String agentId);
}
