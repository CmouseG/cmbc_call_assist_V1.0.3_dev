package com.guiji.dispatch.job;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guiji.ccmanager.api.ICallManagerOut;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.utils.RedisUtil;

@Component
// @RestController
public class TimeTask {

	private static final Logger logger = LoggerFactory.getLogger(TimeTask.class);

	@Autowired
	private IDispatchPlanService dispatchPlanService;
	@Autowired
	private ICallManagerOut callManagerOutApi;
	// @Autowired
	// private IRobotRemote robotRemote;

	@Autowired
	private RedisUtil redisUtil;

	@Scheduled(cron = "0 0/1 * * * ?")
	public void selectPhonesByDate() {
		List<DispatchPlan> list = dispatchPlanService.selectPhoneByDate();
		logger.info("startcallplan..");
		// 分组排序
		Map<String, List<DispatchPlan>> collect = list.stream().collect(Collectors.groupingBy(d -> fetchGroupKey(d)));
		for (Entry<String, List<DispatchPlan>> entry : collect.entrySet()) {
			// 判断机器人是否准备就绪
			logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			checkRedisAndDate(entry.getKey());
		}
	}

	// 每天凌晨1点执行一次
	@Scheduled(cron = "0 0 1 * * ?")
	public void replayPhone() {
		boolean result = dispatchPlanService.updateReplayDate();
		logger.info("当前凌晨一点执行日期刷新操作了！" + result);
	}

	/**
	 * 判断redis是否在当前key
	 * 
	 * @param key
	 */
	private void checkRedisAndDate(String key) {
		Object object = redisUtil.get(key);
		logger.info("checkRedisAndDate key result:" + object);
		if (object != null && object != "") {
			logger.info("当前推送已经推送过：在失效时间内，不重复推送:" + key);
		} else {
			String[] split = key.split("-");
			ReturnData<Boolean> startcallplan = callManagerOutApi.startCallPlan(split[0], split[1], split[2]);
			logger.info("启动客户呼叫计划结果" + startcallplan.success);
			logger.info("启动客户呼叫计划结果详情 " + startcallplan.msg);
			if (startcallplan.success) {
				// 5分钟失效时间
				redisUtil.set(key, new Date(), 300);
			}
		}
	}

	/**
	 * 分组排序字段
	 * 
	 * @param detail
	 * @return
	 */
	private static String fetchGroupKey(DispatchPlan detail) {
		return String.valueOf(detail.getUserId() + "-" + detail.getRobot() + "-" + detail.getLine());
	}
}
