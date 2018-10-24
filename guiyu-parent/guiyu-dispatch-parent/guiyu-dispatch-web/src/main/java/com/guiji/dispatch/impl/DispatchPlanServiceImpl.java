package com.guiji.dispatch.impl;

import com.guiji.dispatch.api.IDispatchPlanService;
import com.guiji.dispatch.dao.PlanMapper;
import com.guiji.dispatch.dao.entity.Plan;
import com.guiji.dispatch.dao.model.CommonResponse;
import com.guiji.dispatch.dao.model.Schedule;
import com.guiji.dispatch.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class DispatchPlanServiceImpl implements IDispatchPlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatchPlanServiceImpl.class);
    private SqlSession session = MybatisUtil.getSqlSession();
    private PlanMapper mapper = session.getMapper(PlanMapper.class);

    @Override
    public CommonResponse addSchedule(Schedule schedule) throws Exception {
        LOGGER.info("addSchedule,request is {}", schedule);
        final Plan plan = new Plan();
        plan.setUserId(schedule.getUserId());
        plan.setPlanUuid(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
        plan.setAttach(schedule.getAttach());
        plan.setCallAgent(schedule.getCallAgent());
        plan.setCallData(schedule.getCallDate());
        plan.setCallHour(schedule.getCallHour());
        plan.setClean(schedule.getClean());
        plan.setPhone(schedule.getPhone());
        plan.setBatchId(schedule.getBatchId());
        final Date date = new Date();
        plan.setGmtCreate(date);
        plan.setGmtModified(date);
        plan.setParams(schedule.getParams());
        plan.setRecall(schedule.getRecall());
        plan.setRecallParams(schedule.getRecallParams());
        plan.setStatusPlan(schedule.getStatusPlan());
        plan.setStatusSync(schedule.getStatusSync());
        plan.setRobot(schedule.getRobot());
        final CommonResponse response = new CommonResponse("00001000", "success");
        try {
            mapper.insert(plan);
            session.commit();
        } catch (final Exception e) {
            LOGGER.error("exception is ", e);
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
