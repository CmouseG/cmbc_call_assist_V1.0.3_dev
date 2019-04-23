package com.guiji.dispatch.impl;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.dispatch.bean.UserLineBotenceVO;
import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.line.IDispatchBatchLineService;
import com.guiji.dispatch.service.IGetPhonesInterface;
import com.guiji.dispatch.service.IPhonePlanQueueService;
import com.guiji.utils.DateUtil;
import com.guiji.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ty on 2019/1/7.
 */
@Service
public class PhonePlanQueueServiceImpl implements IPhonePlanQueueService {
	static Logger logger = LoggerFactory.getLogger(PhonePlanQueueServiceImpl.class);
	private static final String REDIS_SYSTEM_MAX_PLAN = "REDIS_SYSTEM_MAX_PLAN";
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private IGetPhonesInterface getPhonesInterface;
	@Autowired
	private DistributedLockHandler distributedLockHandler;
	@Autowired
	private IDispatchBatchLineService lineService;

	@Override
	public void execute() throws Exception {
		while (true) {
			try {
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
				List<UserLineBotenceVO> userLineRobotList = (List<UserLineBotenceVO>)redisUtil.get(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN);
				if (userLineRobotList != null) {
					//根据用户、模板、线路组合插入拨打电话队列，如果队列长度小于最大并发数的2倍，则往队列中填充3倍最大并发数的计划
					for (UserLineBotenceVO dto : userLineRobotList) {
						//mod by xujin
//						String queue = REDIS_PLAN_QUEUE_USER_LINE_ROBOT+dto.getUserId()+"_"+dto.getLineId()+"_"+dto.getBotenceName();
						String queue = RedisConstant.RedisConstantKey.REDIS_PLAN_QUEUE_USER_LINE_ROBOT+dto.getUserId()+"_"+dto.getBotenceName();
						Lock queueLock = new Lock("dispatch.lock" + queue,"dispatch.lock" + queue);
						if (distributedLockHandler.tryLock(queueLock, 1000L))
						{
							try {

								long currentQueueSize = redisUtil.lGetListSize(queue);
								if (currentQueueSize < systemMaxPlan *2) {
									if (distributedLockHandler.isLockExist(lock)) { // 默认锁设置
										Thread.sleep(500);
										break;
									}
									// mod by xujin
									List<DispatchPlan> dispatchPlanList = getPhonesInterface.getPhonesByParams(dto.getUserId(),dto.getBotenceName(),hour,systemMaxPlan * 3);
									int len = 0;
									if(null != dispatchPlanList && dispatchPlanList.size()>0){
										len = dispatchPlanList.size();
										logger.info("当前查询到的数据:"+len);

										//进去队列之前，根据优line优先级进行排序
										List<DispatchPlan> bak = new ArrayList<>();
										bak.addAll(dispatchPlanList);
										for(DispatchPlan plan: dispatchPlanList){
											//进去队列之前，根据优line优先级进行排序
											DispatchPlan sortPlan = lineService.sortLine(plan);
											this.pushPlanQueue(sortPlan, queue);
										}

									/*
									//进去队列之前，根据优line优先级进行排序
									List<DispatchPlan> bak = new ArrayList<>();
									bak.addAll(dispatchPlanList);
									List<DispatchPlan> sortLine = lineService.sortLine(dispatchPlanList);
									logger.info("sortLine", sortLine);
									if(sortLine.size()>0){
										pushPlan2Queue(sortLine,queue);
									}else if (bak.size()>0){
										logger.info("当前排序走默认规则>>>>>>>>>>>>>>>>>>>>>>>>>>");
										pushPlan2Queue(dispatchPlanList,queue);
									}
									*/
									}
								}
							}
							catch (Exception e) {
								logger.info("PhonePlanQueueServiceImpl#execute:",e);
								e.printStackTrace();
							} finally{
								distributedLockHandler.releaseLock(queueLock); // 释放锁
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("PhonePlanQueueServiceImpl#execute:" , e);
				e.printStackTrace();
			}
		}
	}

	public boolean pushPlanQueue(DispatchPlan plan, String queue){
		logger.info("推入要拨打电话数据KEY:{},{}", queue, plan);
		boolean result = redisUtil.leftPush(queue, plan);
		logger.info("推入要拨打电话数据KEY:{},{}", queue, redisUtil.lGetListSize(queue));
		return result;
	}

	@Override
	public boolean pushPlan2Queue(List<DispatchPlan> dispatchPlanList,String queue) {
		logger.info("推入要拨打电话数据KEY:{},{}", queue, dispatchPlanList.size());
		boolean result = redisUtil.leftPushAll(queue, dispatchPlanList);

		logger.info("推入要拨打电话数据KEY:{},{}", queue, redisUtil.lGetListSize(queue));
		return result;
	}

	@Override
	public boolean cleanQueue() {
		Set<String> queueKeys = redisUtil.getAllKeyMatch(RedisConstant.RedisConstantKey.REDIS_PLAN_QUEUE_USER_LINE_ROBOT);
		if (queueKeys != null) {
			for (String queueKey : queueKeys) {
				//1.获取redis锁，将拨打计划的redis锁住
				Lock queueLock = new Lock("dispatch.lock" + queueKey, "dispatch.lock" + queueKey);
				if (distributedLockHandler.tryLock(queueLock)) { // 默认锁设置
					try {

						//将redis拨打队列中的计划在数据库中status_sync状态回退到未进队列状态，并清空redis拨打队列
						List<Long> planUuids = new ArrayList<Long>();
						while (true) {
							DispatchPlan dispatchPlan = (DispatchPlan) redisUtil.lrightPop(queueKey);
							if (dispatchPlan == null) {
								break;
							} else {
								planUuids.add(dispatchPlan.getPlanUuidLong());
							}
						}
						if (planUuids.size() > 0) {
							getPhonesInterface.resetPhoneSyncStatus(planUuids);
						}
					} catch (Exception e) {
						logger.info("PhonePlanQueueServiceImpl#cleanQueue", e);
						continue;
					} finally {
						distributedLockHandler.releaseLock(queueLock);
					}
				}
			}
		}
		return false;
	}


	@Override
	public boolean cleanQueueByUserId(String userId) {
		Set<String> queueKeys = redisUtil.getAllKeyMatch(RedisConstant.RedisConstantKey.REDIS_PLAN_QUEUE_USER_LINE_ROBOT+userId);
		if (queueKeys != null) {
			for (String queueKey : queueKeys) {
				//1.获取redis锁，将拨打计划的redis锁住
				Lock queueLock = new Lock("dispatch.lock" + queueKey, "dispatch.lock" + queueKey);
				if (distributedLockHandler.tryLock(queueLock)) { // 默认锁设置
					try {

						//将redis拨打队列中的计划在数据库中status_sync状态回退到未进队列状态，并清空redis拨打队列
						List<Long> planUuids = new ArrayList<Long>();
						while (true) {
							DispatchPlan dispatchPlan = (DispatchPlan) redisUtil.lrightPop(queueKey);
							if (dispatchPlan == null) {
								break;
							} else {
								planUuids.add(dispatchPlan.getPlanUuidLong());
							}
						}
						if (planUuids.size() > 0) {
							getPhonesInterface.resetPhoneSyncStatus(planUuids);
						}
					} catch (Exception e) {
						logger.info("PhonePlanQueueServiceImpl#cleanQueue", e);
						continue;
					} finally {
						distributedLockHandler.releaseLock(queueLock);
					}
				}
			}
		}
		return false;
	}
}
