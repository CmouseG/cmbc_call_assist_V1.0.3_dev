package com.guiji.dispatch.impl;

import com.guiji.dispatch.api.IJssPlanService;
import com.guiji.dispatch.dao.PlanMapper;
import com.guiji.dispatch.entity.Plan;
import com.guiji.dispatch.model.CommonResponse;
import com.guiji.dispatch.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class JssPlanServiceImpl implements IJssPlanService {

    @Autowired
    private PlanMapper mapper;

    @Override
    public CommonResponse addSchedule(Schedule schedule) throws Exception {
        final Plan plan = new Plan();
        plan.setPlanUuid(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        plan.setAttach(schedule.getAttach());
        final CommonResponse response = new CommonResponse("00001000", "success");
        try {
            mapper.insert(plan);
        } catch (final Exception e) {
            response.setRespCode("00001001");
            response.setRespMsg("failed");
        }
        return response;
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
