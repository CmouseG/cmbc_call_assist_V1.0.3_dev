package com.guiji.dispatch.pushcallcenter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.calloutserver.api.ICallPlan;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.component.result.Result.ReturnData;
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
	private ICallPlan callPlanCenter;
	@Autowired
	private DistributedLockHandler distributedLockHandler;


	@Override
	public void pushHandler() {
		logger.info("*********************pushHandler**********************");
		logger.info("*********************pushHandler**********************");
		logger.info("*********************pushHandler**********************");
		logger.info("*********************pushHandler**********************");
		logger.info("*********************pushHandler**********************");

		while (true) {
			//当前推送记录
			Integer currentCount = (Integer) redisUtil.get("REDIS_CURRENTLY_COUNT");
			//最大并发
			Integer sysMaxPlan = (Integer) redisUtil.get("REDIS_SYSTEM_MAX_PLAN");
			if (currentCount < sysMaxPlan) {
				Lock lock = new Lock("redisPlanQueueLock", "redisPlanQueueLock");
				if (distributedLockHandler.isLockExist(lock)) {
					break;
				}
				DispatchPlan object = (DispatchPlan) redisUtil.lrightPop("REDIS_PLAN_QUEUE");
				if (object != null) {
					// 记录推送记录
					insertPush(object);
					List<com.guiji.calloutserver.entity.DispatchPlan> list = new ArrayList<>();
					com.guiji.calloutserver.entity.DispatchPlan callBean = new com.guiji.calloutserver.entity.DispatchPlan();
					try {
						BeanUtils.copyProperties(callBean, object);
						callBean.setTempId(object.getRobot());
						list.add(callBean);
					} catch (IllegalAccessException e) {
						logger.error("error", e);
						break;
					} catch (InvocationTargetException e) {
						logger.error("error", e);
						break;
					}
					logger.info("通知呼叫中心开始打电话:" + callBean.getPlanUuid() + "-----" + callBean.getPhone());
					ReturnData startMakeCall = callPlanCenter.startMakeCall(callBean);
					if (!startMakeCall.success) {
						logger.info("启动呼叫中心任务失败");
						break;
					}
					// redis修改变量
					Integer addup = (Integer) redisUtil.get("REDIS_CURRENTLY_COUNT");
					addup = addup + 1;
					redisUtil.set("REDIS_CURRENTLY_COUNT", addup);
				}
			}
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
		record.setCallbackStatus(Constant.NOCALLBACK);
		rabbitTemplate.convertAndSend("dispatch.PushPhonesRecords", JsonUtils.bean2Json(record));
	}

}
