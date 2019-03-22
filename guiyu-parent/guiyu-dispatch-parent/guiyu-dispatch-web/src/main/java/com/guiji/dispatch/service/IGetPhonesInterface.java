package com.guiji.dispatch.service;

import java.util.List;

import com.guiji.dispatch.bean.PlanUserIdLineRobotDto;
import com.guiji.dispatch.dao.entity.DispatchPlan;

public interface IGetPhonesInterface {
	/**
	 * 根据用户名和线路查询任务
	 * 
	 * @param userId
	 * @param lineId
	 * @param limit
	 * @return List<DispatchPlan>
	 */
	public List<DispatchPlan> getPhonesByParams(Integer userId,  String robot, String callHour,
			Integer limit);

	/**
	 * @param 根据uuid修改同步状态
	 * @return boolean
	 */
	public boolean resetPhoneSyncStatus(List<Long> planuuidIds);

	public List<PlanUserIdLineRobotDto> selectPlanGroupByUserIdRobot(String callHour);


	public List<Integer> getUsersByParams(Integer statusPlan, Integer statusSync, String flag);

	List<DispatchPlan> getUsersByParamsByUserId(Integer userId, Integer limit, Integer statusPlan, Integer statusSync,
			String flag);

}
