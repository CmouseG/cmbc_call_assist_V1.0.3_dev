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
public class IPushPhonesHandlerImpl implements IPushPhonesHandler {

	private static final Logger logger = LoggerFactory.getLogger(IPushPhonesHandlerImpl.class);

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

		Integer sysMaxPlan = (Integer) redisUtil.get("REDIS_SYSTEM_MAX_PLAN");
		if (sysMaxPlan <= 0) {
			logger.info("当前并发数为0");
			return;
		}
		// 查询呼叫中心当前正在拨打的数量，如果正在拨打中数量小于当并发相减循环取出当前任务
		ReturnData<Integer> notEndCallCount = callPlanCenter.getNotEndCallCount();
		if (notEndCallCount.success) {
			Integer callingNums = notEndCallCount.getBody();
			logger.info("--------------当前呼叫中心正在拨打号码有----------------：" + callingNums);
			logger.info("--------------当前并发数---------------" + sysMaxPlan);
			System.out.println();
			if (callingNums < sysMaxPlan) {
				int times = sysMaxPlan - callingNums;
				for (int i = 0; i < times; i++) {
					Lock lock = new Lock("redisPlanQueueLock", "redisPlanQueueLock");
		            if (distributedLockHandler.isLockExist(lock)) {
		            	continue;
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
						} catch (InvocationTargetException e) {
							logger.error("error", e);
						}
						logger.info("通知呼叫中心开始打电话:" + callBean.getPlanUuid() + "-----" + callBean.getPhone());
						callPlanCenter.startMakeCall(callBean);
					}
				}
			}
		} else {
			logger.info("-----------调用呼叫中心查询并发接口失败---------");
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
