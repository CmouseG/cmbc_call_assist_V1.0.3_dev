package com.guiji.dispatch.impl;

import com.google.common.primitives.Bytes;
import com.guiji.dispatch.api.IDispatchPlanService;
import com.guiji.dispatch.dao.PlanMapper;
import com.guiji.dispatch.dao.entity.Plan;
import com.guiji.dispatch.model.CommonResponse;
import com.guiji.dispatch.model.Schedule;
import com.guiji.dispatch.model.ScheduleList;
import com.guiji.dispatch.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DispatchPlanServiceImpl implements IDispatchPlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatchPlanServiceImpl.class);
    private SqlSession session = MybatisUtil.getSqlSession();
    private PlanMapper mapper = session.getMapper(PlanMapper.class);

    @Override
    public CommonResponse addSchedule(Schedule schedule) throws Exception {
        LOGGER.info("addSchedule,request is {}", schedule);
        if (CollectionUtils.isEmpty(schedule.getPhones())) {
            LOGGER.error("phone is empty");
            final CommonResponse response = new CommonResponse("00001001", "failed");
            return response;
        }
        final List<Plan> plans = new ArrayList<>();
        for (final String phone : schedule.getPhones()) {
            final Plan plan = new Plan();
            plan.setUserId(schedule.getUserId());
            plan.setPlanUuid(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
            plan.setAttach(schedule.getAttach());
            plan.setCallAgent(schedule.getCallAgent());
            plan.setCallData(schedule.getCallDate());
            plan.setCallHour(schedule.getCallHour());
            plan.setClean(schedule.getClean());
            plan.setPhone(phone);
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
            plans.add(plan);
        }

        final CommonResponse response = new CommonResponse("00001000", "success");
        try {
            mapper.insert(plans);
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
    public CommonResponse queryAvailableSchedules(final Schedule schedule) throws Exception {
        LOGGER.info("queryAvailableSchedules,schedule is {}", schedule);
        if (StringUtils.isEmpty(schedule.getUserId()) || StringUtils.isEmpty(schedule.getTaskNum())) {
            LOGGER.error("userId or taskNum is empty");
            final CommonResponse response = new CommonResponse("00001001", "failed");
            return response;
        }

        final CommonResponse response = new CommonResponse("00001000", "success");
        try {
            final List<Plan> plans = mapper.selectByUserId(Integer.valueOf(schedule.getUserId()), schedule.getTaskNum());
            response.setContent(plans);
            session.commit();
        } catch (final Exception e) {
            LOGGER.error("exception is ", e);
            response.setRespCode("00001001");
            response.setRespMsg("failed");
        }
        return response;
    }

    @Override
    public CommonResponse queryExecuteResult(String planUuid) throws Exception {
        return null;
    }

    @Override
    public CommonResponse updatePlanBatch(final ScheduleList scheduleList) throws Exception {
        LOGGER.info("updatePlanBatch,scheduleList is {}", scheduleList);
        if (StringUtils.isEmpty(scheduleList.getPlanUuid())) {
            LOGGER.error("scheduleList is empty");
            final CommonResponse response = new CommonResponse("00001001", "failed");
            return response;
        }
        final List<Plan> plans = new ArrayList<>();
        for (final String planUuid : scheduleList.getPlanUuid()) {
            final Plan plan = new Plan();
            plan.setPlanUuid(planUuid);
            if (!StringUtils.isEmpty(scheduleList.getStatusPlan())) {
                plan.setStatusPlan(new Byte(scheduleList.getStatusPlan()));
            }
            if (!StringUtils.isEmpty(scheduleList.getStatusSync())) {
                plan.setStatusSync(new Byte(scheduleList.getStatusSync()));
            }
            plan.setResult(scheduleList.getResult());

            plans.add(plan);
        }

        final CommonResponse response = new CommonResponse("00001000", "success");
        try {
            mapper.updateByPrimaryKey(plans);
            session.commit();
        } catch (final Exception e) {
            LOGGER.error("exception is ", e);
            response.setRespCode("00001001");
            response.setRespMsg("failed");
        }
        return response;
    }
}
