package com.guiji.dispatch.impl;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.dispatch.bean.PlanUserIdLineRobotDto;
import com.guiji.dispatch.bean.UserLineBotenceVO;
import com.guiji.dispatch.bean.UserResourceDto;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.service.IGetPhonesInterface;
import com.guiji.dispatch.service.IPhonePlanQueueService;
import com.guiji.utils.DateUtil;
import com.guiji.utils.RedisUtil;
import io.swagger.models.auth.In;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ty on 2019/1/7.
 */
@Service
public class PhonePlanQueueServiceImpl implements IPhonePlanQueueService {
	static Logger logger = LoggerFactory.getLogger(PhonePlanQueueServiceImpl.class);
	private static final String REDIS_SYSTEM_MAX_PLAN = "REDIS_SYSTEM_MAX_PLAN";
	private static final String REDIS_PLAN_QUEUE_USER_LINE_ROBOT = "REDIS_PLAN_QUEUE_USER_LINE_ROBOT_";
	private static final String REDIS_USER_ROBOT_LINE_MAX_PLAN = "REDIS_USER_ROBOT_LINE_MAX_PLAN";
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private IGetPhonesInterface getPhonesInterface;
	@Autowired
	private DistributedLockHandler distributedLockHandler;

	@Override
	public void execute() throws Exception {
		while (true) {
			Lock lock = new Lock("planDistributeJobHandler.lock", "planDistributeJobHandler.lock");
			if (distributedLockHandler.isLockExist(lock)) { // 默认锁设置
				Thread.sleep(500);
				continue;
			}

			// 从redis获取系统最大并发数
			int systemMaxPlan = redisUtil.get(REDIS_SYSTEM_MAX_PLAN) == null ? 0
					: (int) redisUtil.get(REDIS_SYSTEM_MAX_PLAN);
			if (systemMaxPlan == 0) {
				logger.error("从redis获取系统最大并发数失败，获取的最大并发数为0");
			}
			String hour = String.valueOf(DateUtil.getCurrentHour());
			List<UserLineBotenceVO> userLineRobotList = (List<UserLineBotenceVO>)redisUtil.get(REDIS_USER_ROBOT_LINE_MAX_PLAN);
			if (userLineRobotList != null) {
				//根据用户、模板、线路组合插入拨打电话队列，如果队列长度小于最大并发数的2倍，则往队列中填充3倍最大并发数的计划
				for (UserLineBotenceVO dto : userLineRobotList) {
					String queue = REDIS_PLAN_QUEUE_USER_LINE_ROBOT+dto.getUserId()+"_"+dto.getLineId()+"_"+dto.getBotenceName();
					Lock queueLock = new Lock("dispatch.lock" + queue,"dispatch.lock" + queue);
					try {
						if (distributedLockHandler.tryLock(queueLock)) {
							long currentQueueSize = redisUtil.lGetListSize(queue);
							if (currentQueueSize < systemMaxPlan *2) {
								if (distributedLockHandler.isLockExist(lock)) { // 默认锁设置
									Thread.sleep(500);
									break;
								}
								List<DispatchPlan> dispatchPlanList = getPhonesInterface.getPhonesByParams(dto.getUserId(),dto.getLineId(),dto.getBotenceName(),hour,systemMaxPlan * 3);
								pushPlan2Queue(dispatchPlanList,queue);
							}
						}
					} finally {
						distributedLockHandler.releaseLock(queueLock); // 释放锁
					}
				}
			}
		}
	}

	@Override
	public boolean pushPlan2Queue(List<DispatchPlan> dispatchPlanList,String queue) {
		return redisUtil.leftPushAll(queue, dispatchPlanList);
	}

	@Override
	public boolean cleanQueue() {
		Set<String> queueKeys = redisUtil.getAllKeyMatch(REDIS_PLAN_QUEUE_USER_LINE_ROBOT);
		if (queueKeys != null) {
			for (String queueKey : queueKeys) {
				//1.获取redis锁，将拨打计划的redis锁住
				Lock queueLock = new Lock("dispatch.lock" + queueKey,"dispatch.lock" + queueKey);
				try {
					if (distributedLockHandler.tryLock(queueLock)) { // 默认锁设置
						//将redis拨打队列中的计划在数据库中status_sync状态回退到未进队列状态，并清空redis拨打队列
						List<String> planUuids = new ArrayList<String>();
						while (true) {
							DispatchPlan dispatchPlan = (DispatchPlan)redisUtil.lrightPop(queueKey);
							if(dispatchPlan == null) {
								break;
							} else {
								planUuids.add(dispatchPlan.getPlanUuid());
							}
						}
						if(planUuids.size()>0){
							getPhonesInterface.resetPhoneSyncStatus(planUuids);
						}
					}
				} catch (Exception e) {
					logger.info("PhonePlanQueueServiceImpl#cleanQueue", e);
					return false;
				} finally {
					distributedLockHandler.releaseLock(queueLock);
				}
			}
		}
		return false;
	}


	@Override
	public boolean cleanQueueByUserId(String userId) {
		Set<String> queueKeys = redisUtil.getAllKeyMatch(REDIS_PLAN_QUEUE_USER_LINE_ROBOT+userId);
		if (queueKeys != null) {
			for (String queueKey : queueKeys) {
				//1.获取redis锁，将拨打计划的redis锁住
				Lock queueLock = new Lock("dispatch.lock" + queueKey,"dispatch.lock" + queueKey);
				try {
					if (distributedLockHandler.tryLock(queueLock)) { // 默认锁设置
						//将redis拨打队列中的计划在数据库中status_sync状态回退到未进队列状态，并清空redis拨打队列
						List<String> planUuids = new ArrayList<String>();
						while (true) {
							DispatchPlan dispatchPlan = (DispatchPlan)redisUtil.lrightPop(queueKey);
							if(dispatchPlan == null) {
								break;
							} else {
								planUuids.add(dispatchPlan.getPlanUuid());
							}
						}
						if(planUuids.size()>0){
							getPhonesInterface.resetPhoneSyncStatus(planUuids);
						}
					}
				} catch (Exception e) {
					logger.info("PhonePlanQueueServiceImpl#cleanQueue", e);
					return false;
				} finally {
					distributedLockHandler.releaseLock(queueLock);
				}
			}
		}
		return false;
	}
}