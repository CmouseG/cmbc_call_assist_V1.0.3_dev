package com.guiji.dispatch.controller;

import java.util.ArrayList;
import java.util.List;

import com.guiji.dispatch.enums.SysDelEnum;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.dispatch.dao.DispatchLinesMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchLines;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.line.ILinesService;
import com.guiji.dispatch.model.DispatchPlan;
import com.guiji.dispatch.model.PlanCountVO;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.service.IResourcePoolService;
import com.guiji.dispatch.util.Constant;
import com.guiji.utils.RedisUtil;

@RestController
public class DispatchOutApiController implements IDispatchPlanOut {

	static Logger logger = LoggerFactory.getLogger(DispatchOutApiController.class);

	@Autowired
	private IDispatchPlanService dispatchPlanService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private IResourcePoolService resourcePoolService;

	@Autowired
	private DispatchPlanMapper mapper;
	@Autowired
	private ILinesService lineService;
	@Autowired
	private DispatchPlanMapper dispatchMapper;
	/**
	 * 完成
	 *
	 * @param planUuid
	 *            任务id
	 * @return 响应报文
	 * @throws Exception
	 *             异常
	 */
	@Override
	@GetMapping(value = "out/successSchedule")
	public ReturnData<Boolean> successSchedule(String planUuid, String label) {
		boolean result = dispatchPlanService.successSchedule(planUuid, label);
		ReturnData<Boolean> res = new ReturnData<>();
		res.body = result;
		return res;
	}

	/**
	 * 返回可以拨打的任务给呼叫中心
	 *
	 * @param schedule
	 *            请求参数
	 * @return 响应报文
	 */
	@Override
	@GetMapping(value = "out/queryAvailableSchedules")
	public ReturnData<List<DispatchPlan>> queryAvailableSchedules(Integer userId, int requestCount, int lineId) {
		long start = System.currentTimeMillis();
		com.guiji.dispatch.dao.entity.DispatchPlan dis = new com.guiji.dispatch.dao.entity.DispatchPlan();

		List<com.guiji.dispatch.dao.entity.DispatchPlan> queryAvailableSchedules = dispatchPlanService
				.queryAvailableSchedules(userId, requestCount, lineId, dis, true);
		List<DispatchPlan> list = new ArrayList<>();
		try {
			for (com.guiji.dispatch.dao.entity.DispatchPlan plan : queryAvailableSchedules) {
				DispatchPlan bean = new DispatchPlan();
				BeanUtils.copyProperties(plan, bean);
				list.add(bean);
			}
		} catch (Exception e) {
			logger.error("error", e);
		}

		// if (list.size() > 0) {
		// list.get(list.size() - 1).setSuccess(dis.isSuccess());
		// }
		long end = System.currentTimeMillis();
		logger.info("返回可以拨打的任务给呼叫中心结果数量:" + list.size());
		if (list.size() > 0) {
			logger.info("返回可以拨打的任务给呼叫中心结果号码:" + list.get(0).getPhone());
			logger.info("返回可以拨打的任务给呼叫中心结果uuid:" + list.get(0).getPlanUuid());
		}
		return Result.ok(list);
	}

	@Override
	public ReturnData<Boolean> receiveRobotId(String RobotId) {
		logger.info("receiveRobotId 接受到了。");
		ReturnData<Boolean> result = new ReturnData<>();
		boolean res = redisUtil.set(RobotId, RobotId);
		result.body = res;
		return result;
	}

	@Override
	public ReturnData<Boolean> initResourcePool() {
		boolean result = resourcePoolService.initResourcePool();
		return new ReturnData<Boolean>(result);
	}

	@Override
	public ReturnData<Boolean> successSchedule4TempId(String tempId) {
		logger.info("successSchedule4TempId  完成模板通知升级:" + tempId);
		ReturnData<Boolean> result = new ReturnData<>();
		redisUtil.del(tempId);
		result.body = true;
		return result;
	}

	@Override
	public ReturnData<PlanCountVO> getPlanCountByUserId(String orgCode) {
		PlanCountVO planCountByUserId = dispatchPlanService.getPlanCountByUserId(orgCode);
		ReturnData<PlanCountVO> result = new ReturnData<>();
		result.body = planCountByUserId;
		return result;
	}

	@Override
	public ReturnData<Boolean> opertationStopPlanByUserId(String orgCode, String type) {
		boolean stopPlanByorgCode = dispatchPlanService.stopPlanByorgCode(orgCode, type);
		ReturnData<Boolean> result = new ReturnData<>();
		result.body = stopPlanByorgCode;
		return result;
	}

	@Override
	public ReturnData<Boolean> updateLabelByUUID(String planuuid, String label) {
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(planuuid);
		com.guiji.dispatch.dao.entity.DispatchPlan dis = new com.guiji.dispatch.dao.entity.DispatchPlan();
		dis.setResult(label);
		int count = mapper.updateByExampleSelective(dis, ex);
		ReturnData<Boolean> result = new ReturnData<>();
		result.body = count > 0 ? true : false;
		return result;
	}

	@Override
	public ReturnData<Boolean> lineIsUsedByUserId(Integer lineId, Integer userId) {
		ReturnData<Boolean> res = new ReturnData<>();
		// 查询当前任务中心
		DispatchPlanExample planEx = new DispatchPlanExample();
		planEx.createCriteria().andUserIdEqualTo(userId)
				.andStatusPlanEqualTo(Integer.valueOf(com.guiji.dispatch.model.Constant.STATUSPLAN_PLANING))
				.andIsDelEqualTo(SysDelEnum.NORMAL.getState());
		int count = dispatchMapper.countByExample(planEx);

		int limitNum = 5000;
		int times = (count%limitNum==0)?count/limitNum:(count/limitNum+1);
		for(int i=0; i<times; i++){
			planEx.setLimitStart(i*limitNum);
			planEx.setLimitEnd(i*limitNum + limitNum);
			planEx.setOrderByClause("gmt_create DESC");
			List<com.guiji.dispatch.dao.entity.DispatchPlan> selectByExample2 = dispatchMapper.selectByExample(planEx);
			for (com.guiji.dispatch.dao.entity.DispatchPlan dis : selectByExample2) {
				Integer result = lineService.countLineId(dis.getPlanUuid());
				if (result > 0) {
					res.body = true;
					return res;
				}
			}
		}
		res.body = false;
		return res;
	}

	@Override
	public ReturnData<Boolean> lineIsUsed(Integer lineId, List<String> userIdList) {
		ReturnData<Boolean> res = new ReturnData<>();
		DispatchPlanExample planEx = new DispatchPlanExample();
		planEx.createCriteria().andStatusPlanEqualTo(Integer.valueOf(com.guiji.dispatch.model.Constant.STATUSPLAN_PLANING))
				.andIsDelEqualTo(SysDelEnum.NORMAL.getState());
		int count = dispatchMapper.countByExample(planEx);
		List<com.guiji.dispatch.dao.entity.DispatchPlan> selectByExample2 = dispatchMapper.selectByExample(planEx);
		int limitNum = 5000;
		int times = (count%limitNum==0)?count/limitNum:(count/limitNum+1);
		for(int i=0; i<times; i++) {
			planEx.setLimitStart(i * limitNum);
			planEx.setLimitEnd(i * limitNum + limitNum);
			planEx.setOrderByClause("gmt_create DESC");
			for(com.guiji.dispatch.dao.entity.DispatchPlan dis : selectByExample2){
				Integer result = lineService.countLineIdAndUUid(dis.getPlanUuid(),lineId);
				if(result>0){
					res.body = true;
					return res;
				}
			}
		}
		res.body = false;
		return res;
	}

	/**
	 * 查询任务计划
	 * @param planUuid
	 * @return
	 */
	@Override
	public ReturnData<DispatchPlan> queryDispatchPlanById(String planUuid) {
		com.guiji.dispatch.dao.entity.DispatchPlan  plan = !StringUtils.isEmpty(planUuid)?
				dispatchPlanService.queryDispatchPlanById(planUuid):null;
		DispatchPlan dispatchPlan = null;
		if(null != plan){
			dispatchPlan = new DispatchPlan();
			BeanUtils.copyProperties(plan, dispatchPlan, DispatchPlan.class);
		}
		return new ReturnData<DispatchPlan>(dispatchPlan);
	}

	/**
	 * 查询任务计划备注
	 * @param planUuid
	 * @return
	 */
	@Override
	@ApiOperation(value="查询任务计划备注", notes="查询任务计划备注")
	@RequestMapping(value = "/dispatch/queryPlanRemarkById", method = {RequestMethod.GET})
	public ReturnData<String> queryPlanRemarkById(String planUuid) {
		String planAttach = !StringUtils.isEmpty(planUuid)?
				dispatchPlanService.queryPlanRemarkById(planUuid):null;
		return new ReturnData<String>(planAttach);
	}


}
