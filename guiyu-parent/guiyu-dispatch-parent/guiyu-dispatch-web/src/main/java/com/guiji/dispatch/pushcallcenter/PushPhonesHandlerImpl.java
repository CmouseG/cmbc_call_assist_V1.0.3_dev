package com.guiji.dispatch.pushcallcenter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.guiji.dispatch.bean.UserLineBotenceVO;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.atomic.RedisAtomicDouble;
import org.springframework.stereotype.Service;

import com.guiji.calloutserver.api.ICallPlan;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchLines;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.PushRecords;
import com.guiji.dispatch.util.Constant;
import com.guiji.utils.DateUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;

/**
 * 推送号码
 * 
 * @author Administrator
 *
 */
@Service
public class PushPhonesHandlerImpl implements IPushPhonesHandler {

	private static final Logger logger = LoggerFactory.getLogger(PushPhonesHandlerImpl.class);

	private static final String REDIS_USER_ROBOT_LINE_MAX_PLAN = "REDIS_USER_ROBOT_LINE_MAX_PLAN";

	private static final String REDIS_PLAN_QUEUE_USER_LINE_ROBOT = "REDIS_PLAN_QUEUE_USER_LINE_ROBOT_";

	private static final String REDIS_CALL_QUEUE_USER_LINE_ROBOT_COUNT = "REDIS_CALL_QUEUE_USER_LINE_ROBOT_COUNT";

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Autowired
	private DispatchPlanMapper disMapper;

	@Autowired
	private ICallPlan callPlanCenter;
	@Autowired
	private DistributedLockHandler distributedLockHandler;

	@Override
	public void pushHandler() {

		Lock pushHandlerLock = new Lock("pushHandler", "pushHandler");
		while (true) {
			try {
				Lock redisPlanQueueLock = new Lock("redisPlanQueueLock", "redisPlanQueueLock");
				if (distributedLockHandler.isLockExist(redisPlanQueueLock)) {
					logger.info("redis锁了  redisPlanQueueLock");
					Thread.sleep(500);
					continue;
				}
				List<UserLineBotenceVO> userLineRobotList = (List<UserLineBotenceVO>) redisUtil
						.get(REDIS_USER_ROBOT_LINE_MAX_PLAN);
				if (userLineRobotList != null) {
					// 根据用户、模板、线路组合插入拨打电话队列，如果队列长度小于最大并发数的2倍，则往队列中填充3倍最大并发数的计划
					for (UserLineBotenceVO dto : userLineRobotList) {
						String queue = REDIS_PLAN_QUEUE_USER_LINE_ROBOT + dto.getUserId() + "_" + dto.getBotenceName();
						String queueCount = REDIS_CALL_QUEUE_USER_LINE_ROBOT_COUNT + dto.getUserId() + "_"
								+ dto.getBotenceName();
						Lock queueLock = new Lock("dispatch.callphone.lock" + queue, "dispatch.callphone.lock" + queue);
						try {
							if (distributedLockHandler.tryLock(queueLock)) {
								Integer redisUserIdCount = (Integer) redisUtil.get(queueCount);
								if (redisUserIdCount == null) {
									redisUserIdCount = 0;
								}

								Integer callMax = dto.getMaxRobotCount();

								if (callMax <= redisUserIdCount) {
									continue;
								}

								Object obj = (Object) redisUtil.lrightPop(queue);
								if (obj == null || !(obj instanceof DispatchPlan)) {
									continue;
								}
								DispatchPlan dispatchRedis = (DispatchPlan) obj;

								com.guiji.calloutserver.entity.DispatchPlan callBean = new com.guiji.calloutserver.entity.DispatchPlan();
								try {
									BeanUtils.copyProperties(callBean, dispatchRedis);
									callBean.setTempId(dispatchRedis.getRobot());
									callBean.setAgentGroupId(dispatchRedis.getCallAgent());
									List<Integer> lines = new ArrayList<>();
									for (DispatchLines line : dispatchRedis.getLines()) {
										lines.add(line.getLineId());
									}
									callBean.setLineList(lines);
								} catch (IllegalAccessException e) {
									updateStatusSync(dispatchRedis.getPlanUuid());
									logger.info("---------BeanUtils.copyProperties转换失败-----------", e);
									continue;
								} catch (InvocationTargetException e) {
									updateStatusSync(dispatchRedis.getPlanUuid());
									logger.info("---------BeanUtils.copyProperties转换失败-----------", e);
									continue;
								}

								List<String> userIdList = (List<String>) redisUtil.get("USER_BILLING_DATA");
								if(userIdList!=null){
									if (userIdList.contains(String.valueOf(callBean.getUserId()))) {
										logger.info("startMakeCall>>>>>>>>>>>>>>>>>>>当前用户处于欠费" + callBean.getUserId());
										continue;
									}
								}
								// 增加推送次数
								addVariable(callBean, queueCount);
								logger.info("通知呼叫中心开始打电话:" + callBean.getPlanUuid() + "-----" + callBean.getPhone()
										+ "---------" + callBean.getLineList());
								ReturnData startMakeCall = callPlanCenter.startMakeCall(callBean);
								// 记录推送记录
								insertPush(dispatchRedis);
								if (!startMakeCall.success) {
									updateStatusSync(dispatchRedis.getPlanUuid());
									logger.info("启动呼叫中心任务失败");
									// 减少推送次数
									cutVariable(callBean, queueCount);
									continue;
								}
							}
						} finally {
							distributedLockHandler.releaseLock(queueLock); // 释放锁
						}
					}
				}
			} catch (Exception e) {
				logger.info("pushHandler代码异常了", e);
			} finally {
				distributedLockHandler.releaseLock(pushHandlerLock); // 释放锁
			}
		}
	}

	private void cutVariable(com.guiji.calloutserver.entity.DispatchPlan callBean, String queueName) {
		Integer currentCount = (Integer) redisUtil.get(queueName);
		if (currentCount == null) {
			currentCount = 0;
		}
		if (currentCount > 0) {
			// currentCount = currentCount - 1;
			// redisUtil.set(queueName, currentCount);
			// 递减1
			redisUtil.decr(queueName, 1);
		}
	}

	private void addVariable(com.guiji.calloutserver.entity.DispatchPlan callBean, String queueName) {
		// Integer currentCount = (Integer) redisUtil.get(queueName);
		// if (currentCount == null) {
		// currentCount = 0;
		// }
		// currentCount = currentCount + 1;
		// redisUtil.set(queueName, currentCount);
		redisUtil.incr(queueName, 1);
	}

	public void updateStatusSync(String planUUID) {
		List<String> list = new ArrayList<>();
		list.add(planUUID);
		if (list.size() > 0) {
			disMapper.updateDispatchPlanListByStatusSYNC(list, Constant.STATUS_SYNC_0);
		}
	}

	public void insertPush(DispatchPlan dispatchPlan) {
		PushRecords record = new PushRecords();
		try {
			record.setCreateTime(DateUtil.getCurrent4Time());
		} catch (Exception e) {
			logger.error("error", e);
		}
		record.setPhone(dispatchPlan.getPhone());
		record.setPlanuuid(dispatchPlan.getPlanUuid());
		record.setUserId(dispatchPlan.getUserId());
		record.setLine(dispatchPlan.getLine());
		record.setRobot(dispatchPlan.getRobot());
		record.setCallbackStatus(Constant.NOCALLBACK);
		rabbitTemplate.convertAndSend("dispatch.PushPhonesRecords", JsonUtils.bean2Json(record));
	}

}
