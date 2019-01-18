package com.guiji.dispatch.pushcallcenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.bean.PlanUserIdLineRobotDto;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.service.IGetPhonesInterface;
import com.guiji.dispatch.util.Constant;

@Service
public class IGetPhonesInterfaceImpl implements IGetPhonesInterface {

	private static final Logger logger = LoggerFactory.getLogger(PushPhonesHandlerImpl.class);

	@Autowired
	private DispatchPlanMapper dispatchMapper;

	/**
	 * 根据用户 线路 模板 时间 查询任务信息
	 */
	@Override
	public List<DispatchPlan> getPhonesByParams(Integer userId, Integer lineId, String robot, String callHour,
			Integer limit) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		DispatchPlan dis = new DispatchPlan();
		dis.setUserId(userId);
		dis.setLine(lineId);
		dis.setCallHour(callHour);
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		dis.setStatusSync(Constant.STATUS_SYNC_0);
		dis.setFlag(Constant.IS_FLAG_2);
		dis.setRobot(robot);
		dis.setLimitStart(0);
		dis.setLimitEnd(limit);
		List<DispatchPlan> selectByCallHour = dispatchMapper.selectByCallHour(dis);
		List<String> ids = new ArrayList<>();
		for (DispatchPlan plan : selectByCallHour) {
			ids.add(plan.getPlanUuid());
		}
		if (ids.size() > 0) {
			dispatchMapper.updateDispatchPlanListByStatusSYNC(ids, Constant.STATUS_SYNC_1);
		}
		return selectByCallHour;
	}

	/**
	 * 根据用户 ，线路 ，机器人 拨打时间 分组查询
	 */
	@Override
	public List<PlanUserIdLineRobotDto> selectPlanGroupByUserIdLineRobot(String callHour) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		DispatchPlan dis = new DispatchPlan();
		dis.setCallHour(callHour);
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		// dis.setStatusSync(Constant.STATUS_SYNC_0);
		dis.setFlag(Constant.IS_FLAG_2);
		List<PlanUserIdLineRobotDto> planUserIdLineRobotDtos = new ArrayList<>();
		List<DispatchPlan> selectPlanGroupByUserIdLineRobot = dispatchMapper.selectPlanGroupByUserIdLineRobot(dis);
		for (DispatchPlan result : selectPlanGroupByUserIdLineRobot) {
			PlanUserIdLineRobotDto dto = new PlanUserIdLineRobotDto();
			dto.setUserId(result.getUserId());
			dto.setLineId(result.getLine());
			dto.setRobot(result.getRobot());
			planUserIdLineRobotDtos.add(dto);
		}
		return planUserIdLineRobotDtos;
	}

	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------

	@Override
	public List<Integer> getUsersByParams(Integer statusPlan, Integer statusSync, String flag) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		DispatchPlan dis = new DispatchPlan();
		dis.setCallHour(String.valueOf(hour));
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(statusPlan);
		dis.setStatusSync(statusSync);
		dis.setFlag(flag);
		// groupby
		List<Integer> userIds = new ArrayList<>();

		List<DispatchPlan> selectByCallHour4UserId = dispatchMapper.selectByCallHour4UserId(dis);
		for (DispatchPlan dto : selectByCallHour4UserId) {
			userIds.add(dto.getUserId());
		}
		return userIds;
	}

	public List<Integer> getUsersByParams(Integer userId, Integer limit, Integer statusPlan, Integer statusSync,
			String flag) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		DispatchPlan dis = new DispatchPlan();
		dis.setCallHour(String.valueOf(hour));
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(statusPlan);
		dis.setStatusSync(statusSync);
		dis.setFlag(flag);
		dis.setUserId(userId);
		dis.setLimitStart(0);
		dis.setLimitEnd(limit);
		// groupby
		List<Integer> userIds = new ArrayList<>();

		List<DispatchPlan> selectByCallHour4UserId = dispatchMapper.selectByCallHour4UserId(dis);
		for (DispatchPlan dto : selectByCallHour4UserId) {
			userIds.add(dto.getUserId());
		}
		return userIds;
	}

	/**
	 * 恢复任务中心同步状态
	 */
	@Override
	public boolean resetPhoneSyncStatus(List<String> planuuidIds) {
		int result = dispatchMapper.updateDispatchPlanListByStatusSYNC(planuuidIds, Constant.STATUS_SYNC_0);
		return result > 0 ? true : false;
	}

	/**
	 * 找出当前可以拨打的号码用户
	 * 
	 * @param callHour
	 * @return userids
	 */
	@Override
	public List<Integer> getUserIdsByCallHour(String callHour) {
		DispatchPlan dis = new DispatchPlan();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setCallHour(callHour);
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		dis.setStatusSync(Constant.STATUS_SYNC_0);
		dis.setFlag(Constant.IS_FLAG_2);
		List<Integer> userIds = new ArrayList<>();
		List<DispatchPlan> selectByCallHour4UserId = dispatchMapper.selectByCallHour4UserId(dis);
		for (DispatchPlan dto : selectByCallHour4UserId) {
			userIds.add(dto.getUserId());
		}
		// logger.info("getUserIdsByCallHour..."+userIds.size());
		return userIds;
	}

	/**
	 * 根据拨打时间 用户id 查询出线路
	 */
	@Override
	public List<Integer> getPhonesByCallHourAndUserId(String callhour, Integer userId) {
		DispatchPlan dis = new DispatchPlan();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setCallHour(callhour);
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		dis.setStatusSync(Constant.STATUS_SYNC_0);
		dis.setFlag(Constant.IS_FLAG_2);
		List<Integer> lineIds = new ArrayList<>();
		List<DispatchPlan> selectByCallHour4UserId = dispatchMapper.selectByCallHour4LineId(dis);
		for (DispatchPlan dto : selectByCallHour4UserId) {
			lineIds.add(dto.getLine());
		}
		return lineIds;
	}

	@Override
	public List<DispatchPlan> getUsersByParamsByUserId(Integer userId, Integer limit, Integer statusPlan,
			Integer statusSync, String flag) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		DispatchPlan dis = new DispatchPlan();
		dis.setCallHour(String.valueOf(hour));
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(statusPlan);
		dis.setStatusSync(statusSync);
		dis.setFlag(flag);
		dis.setUserId(userId);
		dis.setLimitStart(0);
		dis.setLimitEnd(limit);

		return dispatchMapper.selectByCallHour(dis);
	}

	@Override
	public Integer getCountByUserId(Integer userId, Integer statusPlan, Integer statusSync, String flag) {
		DispatchPlan dis = new DispatchPlan();
		dis.setUserId(userId);
		dis.setStatusPlan(statusPlan);
		dis.setFlag(flag);
		dis.setStatusSync(statusSync);
		return dispatchMapper.getCountByUserId(dis);
	}

}