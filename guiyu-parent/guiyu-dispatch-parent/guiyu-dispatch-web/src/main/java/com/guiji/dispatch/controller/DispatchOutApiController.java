package com.guiji.dispatch.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.guiji.dispatch.service.IResourcePoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.dispatch.model.DispatchPlan;
import com.guiji.dispatch.service.IDispatchPlanService;
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
		logger.info("返回可以拨打的任务给呼叫中心开始查询时间......."+System.currentTimeMillis());
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

//		if (list.size() > 0) {
//			list.get(list.size() - 1).setSuccess(dis.isSuccess());
//		}
		long end = System.currentTimeMillis();
		logger.info("返回可以拨打的任务给呼叫中心结果数量:" + list.size());
		logger.info("此次请求消费的时间为:" + (end - start));
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
		// if( redisUtil.get("robotId")!=null){
		// Object object = redisUtil.get("robotId");
		// String newStr = object + ","+RobotId;
		// boolean set = redisUtil.set("robotId", newStr);
		// result.body = set;
		// }else{
		// boolean set = redisUtil.set("robotId", RobotId);
		// result.body = set;
		// }
		return result;
	}

	@Override
	public ReturnData<Boolean> initResourcePool() {
		boolean result = resourcePoolService.initResourcePool();
		return new ReturnData<Boolean>(result);
	}

	@Override
	public ReturnData<Boolean> distributeByUser() {
		boolean result = resourcePoolService.distributeByUser();
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

}
