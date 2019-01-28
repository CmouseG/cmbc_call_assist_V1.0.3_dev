package com.guiji.service.impl;

import com.guiji.callcenter.dao.entity.CallInPlan;
import com.guiji.service.CallInPlanService;
import com.guiji.web.request.UpdateLabelRequest;
import com.guiji.web.response.QueryQueueCalls;
import com.guiji.web.response.QueryRecordInDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/16 19:47
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class CallInPlanServiceImpl implements CallInPlanService {
    @Override
    public void update(CallInPlan callPlan) {

    }

    @Override
    public CallInPlan queueCallsfindByRecordId(String recordId) {
        return null;
    }

    @Override
    public QueryQueueCalls queueCalls(String queueId) {
        return null;
    }

    @Override
    public void updateLabel(UpdateLabelRequest request) {

    }

    @Override
    public QueryRecordInDetail queryCallrecord(String callrecordId) {
        return null;
    }

    @Override
    public QueryRecordInDetail getRealCallInfo(String mobile) {
        return null;
    }

    @Override
    public CallInPlan findByCallId(String recordId) {
        return null;
    }

    @Override
    public CallInPlan findByUuidOrAgentChannelUuid(String uuid) {
        return null;
    }

    @Override
    public CallInPlan findByAgentId(String agentId) {
        return null;
    }
}
