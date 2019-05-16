package com.guiji.dispatch.service;

import java.util.List;

import com.guiji.dispatch.bean.PlanUserIdLineRobotDto;
import com.guiji.dispatch.dao.entity.DispatchPlan;

public interface IGetPhonesInterface {
	/**
	 * 根据用户名和线路查询任务
	 * 
	 * @param userId
	 * @param limit
	 * @return List<DispatchPlan>
	 */
	public List<DispatchPlan> getPhonesByParams(Integer userId,  String robot, String callHour,
			Integer limit);

	/**
	 * 根据uuid修改同步状态
	 * @param
	 * @return boolean
	 */
	public boolean resetPhoneSyncStatus(List<Long> planuuidIds);

	public List<PlanUserIdLineRobotDto> selectPlanGroupByUserIdRobot(String callHour);

	//获取等于当前日期的拨打用户列表
	public List<Integer> getUsersByParams(Integer statusPlan, Integer statusSync, String flag, List<Integer> allOrgId);

	//获取大于等于当前日期的拨打用户列表
	public List<Integer> getFutureUsersByParams(Integer statusPlan, Integer statusSync, String flag, List<Integer> allOrgId);

	//获取等于当前日期的拨打任务列表
	List<DispatchPlan> getUsersByParamsByUserId(Integer userId, Integer limit, Integer statusPlan, Integer statusSync,
			String flag);

	//获取大于等于当前日期的拨打任务列表
	List<DispatchPlan> getFuturePlanByUserId(Integer userId, Integer limit, Integer statusPlan, Integer statusSync, String flag);


}
