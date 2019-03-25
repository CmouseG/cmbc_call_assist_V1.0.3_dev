package com.guiji.dispatch.pushcallcenter;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.component.result.Result;
import com.guiji.dispatch.bean.PlanUserIdLineRobotDto;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.line.IDispatchBatchLineService;
import com.guiji.dispatch.service.IGetPhonesInterface;
import com.guiji.dispatch.util.Constant;
import com.guiji.dispatch.util.ResHandler;
import com.guiji.utils.IdGengerator.IdUtils;
import com.guiji.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IGetPhonesInterfaceImpl implements IGetPhonesInterface {

	private static final Logger logger = LoggerFactory.getLogger(PushPhonesHandlerImpl.class);

	@Autowired
	private DispatchPlanMapper dispatchMapper;

	@Autowired
	private IDispatchBatchLineService linesService;

	@Autowired
	private RedisUtil redisUtils;

	@Autowired
	private IOrg orgService;

	@Autowired
	private IAuth auth;

	/**
	 * 根据用户 线路 模板 时间 查询任务信息
	 */
	@Override
	public List<DispatchPlan> getPhonesByParams(Integer userId, String robot, String callHour, Integer limit) {
		List<DispatchPlan> planList = new ArrayList<DispatchPlan>();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		DispatchPlan dis = new DispatchPlan();
		dis.setUserId(userId);
		dis.setCallHour(callHour);
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		dis.setStatusSync(Constant.STATUS_SYNC_0);
		dis.setFlag(Constant.IS_FLAG_2);
		dis.setRobot(robot);
		dis.setLimitStart(0);
		dis.setLimitEnd(limit);

		List<Integer> orgIds = new ArrayList<>();
		orgIds.add(ResHandler.getResObj(auth.getOrgByUserId(Long.valueOf(userId))).getId().intValue());
		List<DispatchPlan> selectByCallHour = dispatchMapper.selectByCallHour(dis, orgIds);
		if(null != selectByCallHour) {
			// 如果当前用户已经欠费的话，那么就删除
			List<String> userIdList = (List<String>) redisUtils.get("USER_BILLING_DATA");
			if (userIdList != null) {
				for (int j = 0; j < selectByCallHour.size(); j++) {
					if (!userIdList.contains(String.valueOf(selectByCallHour.get(j).getUserId()))) {
//					logger.info("getPhonesByParams>>>>>>>>>>>>>>>>>>>当前用户处于欠费" + selectByCallHour.get(j).getUserId());
						planList.add(selectByCallHour.get(j));
					}
				}
			}

			List<Long> ids = new ArrayList<>();
			for (DispatchPlan plan : planList) {
				ids.add(plan.getPlanUuidLong());
			}
			if (ids.size() > 0) {
				dispatchMapper.updateDispatchPlanListByStatusSYNC(ids, Constant.STATUS_SYNC_1, orgIds);
			}

			Map<Integer, List<DispatchBatchLine>> tmpMap = new HashMap<>();
			// 查询出每个任务下对应的线路
			for (DispatchPlan bean : planList) {

				List<DispatchBatchLine> linesVO = null;
				if(!tmpMap.containsKey(bean.getBatchId()))
				{
					linesVO = linesService.queryListByBatchId(bean.getBatchId());
					if(linesVO != null)
					{
						tmpMap.put(bean.getBatchId(), linesVO);
					}
				}

				if(tmpMap.containsKey(bean.getBatchId()))
				{
					bean.setLines(tmpMap.get(bean.getBatchId()));
				}
			}
		}
		return planList;
	}

	/**
	 * 根据用户 ，线路 ，机器人 拨打时间 分组查询 mod by xujin
	 */
	@Override
	public List<PlanUserIdLineRobotDto> selectPlanGroupByUserIdRobot(String callHour) {
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
		// 去掉line分组
		List<DispatchPlan> selectPlanGroupByUserIdLineRobot = dispatchMapper.selectPlanGroupByUserIdLineRobot(dis, getAllOrgIds());
		// 查询欠费用户
		List<String> userIdList = (List<String>) redisUtils.get("USER_BILLING_DATA");
		boolean hasArrearage = null != userIdList?true:false;
		for (DispatchPlan result : selectPlanGroupByUserIdLineRobot) {
			PlanUserIdLineRobotDto dto = new PlanUserIdLineRobotDto();
			dto.setUserId(result.getUserId());
			dto.setRobot(result.getRobot());
			if(hasArrearage){
				//只加入不欠费用户， 如果当前用户已经欠费的话，那么就删除
				if(!userIdList.contains(String.valueOf(result.getUserId()))) {
					planUserIdLineRobotDtos.add(dto);
				}else{
					logger.info("selectPlanGroupByUserIdRobot>>>>>>>>>>>>>>>>>>>当前用户处于欠费" + result.getUserId());
				}
			}else{
				planUserIdLineRobotDtos.add(dto);
			}
		}

		if(userIdList!=null){
			for (int j = 0; j < planUserIdLineRobotDtos.size(); j++) {
				if (userIdList.contains(String.valueOf(planUserIdLineRobotDtos.get(j).getUserId()))) {
					logger.info("selectPlanGroupByUserIdRobot>>>>>>>>>>>>>>>>>>>当前用户处于欠费" + planUserIdLineRobotDtos.get(j).getUserId());
					planUserIdLineRobotDtos.remove(j);
				}
			}
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

		List<DispatchPlan> selectByCallHour4UserId = dispatchMapper.selectByCallHour4UserId(dis, getAllOrgIds());
		for (DispatchPlan dto : selectByCallHour4UserId) {
			userIds.add(dto.getUserId());
		}
		return userIds;
	}

	/**
	 * 恢复任务中心同步状态
	 */
	@Override
	public boolean resetPhoneSyncStatus(List<Long> planuuidIds) {

		List<Integer> orgIds = new ArrayList<>();
		for (Long str : planuuidIds) {

			Integer orgId = IdUtils.doParse(str).getOrgId();
			if(!orgIds.contains(orgId))
			{
				orgIds.add(orgId);
			}
		}
		int result = dispatchMapper.updateDispatchPlanListByStatusSYNC(planuuidIds, Constant.STATUS_SYNC_0, orgIds);
		return result > 0 ? true : false;
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

		List<Integer> orgIds = new ArrayList<>();
		orgIds.add(ResHandler.getResObj(auth.getOrgByUserId(Long.valueOf(userId))).getId().intValue());
		return dispatchMapper.selectByCallHour(dis,  orgIds);
	}


	private List<Integer> getAllOrgIds()
	{
		Result.ReturnData<List<Integer>> resp = orgService.getAllOrgId();
		List<Integer> result = null;
		if (resp != null && resp.getBody() != null) {
			result = resp.getBody();
		}

		if(result == null)
		{
			result = new ArrayList<>();
		}

		return  result;
	}


}
