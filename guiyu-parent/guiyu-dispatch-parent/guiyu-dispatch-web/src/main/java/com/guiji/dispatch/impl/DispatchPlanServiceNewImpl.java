package com.guiji.dispatch.impl;

import java.util.List;

import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.api.IDispatchPlanService;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.model.CommonResponse;
import com.guiji.dispatch.model.Schedule;
import com.guiji.dispatch.model.ScheduleList;
@Service
public class DispatchPlanServiceNewImpl  implements IDispatchPlanService
{

	@Autowired
	private DispatchPlanMapper mapper;
	
	@Override
	public CommonResponse addSchedule(Schedule schedule) throws Exception {
		return null;
	}

	@Override
	public CommonResponse querySchedules(String Robot) throws Exception {
		
		DispatchPlanExample example = new DispatchPlanExample();
		example.createCriteria().andRobotEqualTo(Robot);
		List<DispatchPlan> selectByExample = mapper.selectByExample(example);
		System.out.println(selectByExample.size());
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
	public CommonResponse queryAvailableSchedules(Schedule schedule) throws Exception {
		return null;
	}

	@Override
	public CommonResponse queryExecuteResult(String planUuid) throws Exception {
		return null;
	}

	@Override
	public CommonResponse updatePlanBatch(ScheduleList scheduleList) throws Exception {
		return null;
	}

}
