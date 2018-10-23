package com.guiji.dispatch.impl;

import com.guiji.dispatch.api.IJssPlanService;
import com.guiji.dispatch.model.CommonResponse;
import com.guiji.dispatch.model.Schedule;
import org.springframework.stereotype.Service;

@Service
public class JssPlanServiceImpl implements IJssPlanService {
    @Override
    public CommonResponse addSchedule(Schedule schedule) throws Exception {
        return null;
    }

    @Override
    public CommonResponse querySchedules(String userId) throws Exception {
        return null;
    }

    @Override
    public CommonResponse pauseSchedule(String planUuid) throws Exception {
        return null;
    }

    @Override
    public CommonResponse resumeSchedule(String planUuid) throws Exception {
        return null;
    }

    @Override
    public CommonResponse stopSchedule(String planUuid) throws Exception {
        return null;
    }

    @Override
    public CommonResponse queryAvailableSchedule(String userId, String taskNum) throws Exception {
        return null;
    }

    @Override
    public CommonResponse queryExecuteResult(String planUuid) throws Exception {
        return null;
    }
}
