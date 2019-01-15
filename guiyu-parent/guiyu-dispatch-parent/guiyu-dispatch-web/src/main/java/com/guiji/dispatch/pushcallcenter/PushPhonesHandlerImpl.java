package com.guiji.dispatch.pushcallcenter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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
import com.guiji.dispatch.bean.UserResourceDto;
import com.guiji.dispatch.dao.DispatchPlanMapper;
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
		Integer currentCount1 = (Integer) redisUtil.get("REDIS_CURRENTLY_COUNT");
		Integer sysMaxPlan1 = (Integer) redisUtil.get("REDIS_SYSTEM_MAX_PLAN");
		logger.info("----------currentCount1----------" + currentCount1);
		logger.info("----------sysMaxPlan1----------" + sysMaxPlan1);
		while (true) {
			// 当前推送记录
			Integer currentCount = (Integer) redisUtil.get("REDIS_CURRENTLY_COUNT");
			// 最大并发
			Integer sysMaxPlan = (Integer) redisUtil.get("REDIS_SYSTEM_MAX_PLAN");
			if (currentCount < sysMaxPlan) {
				Lock lock = new Lock("redisPlanQueueLock", "redisPlanQueueLock");
				if (distributedLockHandler.isLockExist(lock)) {
					logger.info("redis锁了  redisPlanQueueLock");
					continue;
				}
				DispatchPlan object = (DispatchPlan) redisUtil.lrightPop("REDIS_PLAN_QUEUE");
				if (object == null) {
					continue;
				}
				// 用户id
				Integer userId = object.getUserId();
				List<UserResourceDto> max = (List<UserResourceDto>) redisUtil.get("REDIS_USER_MAX_ROBOT");
				if (max != null) {
					Integer redisUserIdCount = (Integer) redisUtil.get("REDIS_USERID_CURRENTLY_COUNT_" + userId);
					if (redisUserIdCount == null) {
						redisUserIdCount = 0;
					}
					for (UserResourceDto dto : max) {
						if (userId.equals(Integer.valueOf(dto.getUserId()))) {
							// 如果当前用户正在拨打数量大于改用户配置的的机器人数量。
							if (redisUserIdCount >= dto.getCount()) {
								logger.info("用户:{}机器人数量{}已到达上线", userId, dto.getCount());
								// 还原状态
								updateStatusSync(object.getPlanUuid());
							} else {
								// 记录推送记录
								insertPush(object);
								List<com.guiji.calloutserver.entity.DispatchPlan> list = new ArrayList<>();
								com.guiji.calloutserver.entity.DispatchPlan callBean = new com.guiji.calloutserver.entity.DispatchPlan();
								try {
									BeanUtils.copyProperties(callBean, object);
									callBean.setTempId(object.getRobot());
									list.add(callBean);
								} catch (IllegalAccessException e) {
									updateStatusSync(object.getPlanUuid());
									logger.error("error", e);
									continue;
								} catch (InvocationTargetException e) {
									updateStatusSync(object.getPlanUuid());
									logger.error("error", e);
									continue;
								}
								logger.info("通知呼叫中心开始打电话:" + callBean.getPlanUuid() + "-----" + callBean.getPhone());
								ReturnData startMakeCall = callPlanCenter.startMakeCall(callBean);
								if (!startMakeCall.success) {
									updateStatusSync(object.getPlanUuid());
									logger.info("启动呼叫中心任务失败");
									continue;
								}
								// redis修改变量
								Integer addup = (Integer) redisUtil.get("REDIS_CURRENTLY_COUNT");
								addup = addup + 1;
								redisUtil.set("REDIS_CURRENTLY_COUNT", addup);

								// 拿到对应用户的redis
								Integer userIdCount = (Integer) redisUtil
										.get("REDIS_USERID_CURRENTLY_COUNT_" + callBean.getUserId());
								if (userIdCount == null) {
									userIdCount = 0;
								}
								userIdCount = userIdCount + 1;
								redisUtil.set("REDIS_USERID_CURRENTLY_COUNT_" + callBean.getUserId(), userIdCount);

							}
						}
					}
				}
			}
		}
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
		record.setCallbackStatus(Constant.NOCALLBACK);
		rabbitTemplate.convertAndSend("dispatch.PushPhonesRecords", JsonUtils.bean2Json(record));
	}

}
