package com.guiji.service;

import com.guiji.entity.CallPlan;
import com.guiji.entity.ECallDirection;
import com.guiji.web.request.UpdateLabelRequest;
import com.guiji.web.response.QueryQueueCalls;
import com.guiji.web.response.QueryRecordInDetail;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 15:13
 * @Project：ccserver
 * @Description:
 */
public interface CallPlanService {
    void update(CallPlan callPlan);

    CallPlan findByCallId(String recordId, ECallDirection callDirection, Integer orgId);

//    QueryQueueCalls queueCalls(String queueId, ECallDirection callDirection);

//    void updateLabel(UpdateLabelRequest request, ECallDirection callDirection);

//    QueryRecordInDetail getRealCallInfo(String mobile, ECallDirection callDirection);

    CallPlan findByUuidOrAgentChannelUuid(String uuid, ECallDirection callDirection);

    /**
     * 查看坐席正在服务的呼入计划
     * @param agentId
     * @return
     */
    CallPlan findByAgentId(String agentId);
}
