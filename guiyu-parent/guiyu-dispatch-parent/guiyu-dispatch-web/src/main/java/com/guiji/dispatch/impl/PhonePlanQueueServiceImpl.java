package com.guiji.dispatch.impl;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.dispatch.bean.UserLineBotenceVO;
import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.enums.SyncStatusEnum;
import com.guiji.dispatch.line.IDispatchBatchLineService;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.service.IGetPhonesInterface;
import com.guiji.dispatch.service.IPhonePlanQueueService;
import com.guiji.utils.DateUtil;
import com.guiji.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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

	@Autowired
	private IDispatchPlanService planService;

	@Override
	public void execute() throws Exception {
		while (true) {
			try {
				// 从redis获取系统最大并发数版本号
				String systemMaxPlanVer = redisUtil.get(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN_VER) == null ? ""
						: (String) redisUtil.get(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN_VER);

				Lock lock = new Lock("planDistributeJobHandler.lock", "planDistributeJobHandler.lock");
				if (distributedLockHandler.isLockExist(lock)) { // 默认锁设置
					Thread.sleep(100);
					continue;
				}

				// 从redis获取系统最大并发数
				int systemMaxPlan = redisUtil.get(REDIS_SYSTEM_MAX_PLAN) == null ? 0
						: (int) redisUtil.get(REDIS_SYSTEM_MAX_PLAN);
				if (systemMaxPlan == 0) {
					logger.error("从redis获取系统最大并发数失败，获取的最大并发数为0");
					continue;
				}
				String hour = String.valueOf(DateUtil.getCurrentHour());
				List<UserLineBotenceVO> userLineRobotList = (List<UserLineBotenceVO>)redisUtil.get(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN);
				List<String> oldIdBotenceList = getUserBotenceList(userLineRobotList);

				if (userLineRobotList != null) {
					//根据用户、模板、线路组合插入拨打电话队列，如果队列长度小于最大并发数的2倍，则往队列中填充3倍最大并发数的计划
					LoopUser: for (UserLineBotenceVO dto : userLineRobotList) {
						if (isVerChanged(systemMaxPlanVer)) {
							if (!isNewAlotContains((List<UserLineBotenceVO>) redisUtil.get(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN), dto.getUserId() + "_" + dto.getBotenceName())) {

								continue;
							}
						}

						//mod by xujin
//						String queue = REDIS_PLAN_QUEUE_USER_LINE_ROBOT+dto.getUserId()+"_"+dto.getLineId()+"_"+dto.getBotenceName();
						String queue = RedisConstant.RedisConstantKey.REDIS_PLAN_QUEUE_USER_LINE_ROBOT+dto.getUserId()+"_"+dto.getBotenceName();
						Lock queueLock = new Lock("dispatch.lock" + queue,"dispatch.lock" + queue);
						if (distributedLockHandler.tryLock(queueLock, 1000L))
						{
							try {
								long currentQueueSize = redisUtil.lGetListSize(queue);//拨打队列长度
								Integer userMaxRobotCount = dto.getMaxRobotCount();		//分配用户、话术的最大机器人数
								if (currentQueueSize < userMaxRobotCount * 100) {//当拨打队列数据量小于100倍机器人数量，则从数据库中获取下批数据
									// mod by xujin
									//从数据库获取需要放入拨打队列数据，以分配机器人数200倍的数量获取
									List<DispatchPlan> dispatchPlanList = getPhonesInterface.getPhonesByParams(dto.getUserId(),dto.getBotenceName(),hour,userMaxRobotCount * 200);
									int len = 0;
									if(null != dispatchPlanList && dispatchPlanList.size()>0){
										len = dispatchPlanList.size();
										logger.info("当前查询到的数据:"+len);
										//进去队列之前，根据优line优先级进行排序
										LoopPlan: for(DispatchPlan plan: dispatchPlanList){
											try {
												//进去队列之前，根据优line优先级进行排序
												DispatchPlan sortPlan = lineService.sortLine(plan);
												if (isVerChanged(systemMaxPlanVer)) {
													if (!isNewAlotContains((List<UserLineBotenceVO>) redisUtil.get(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN), dto.getUserId() + "_" + dto.getBotenceName())) {
														/*
														this.pushPlan2Queue(dispatchPlanList, queue);
														this.cleanQueueByQueueName(queue);
														break LoopUser;
														*/
														//将该计划任务的推送拨打队列状态重新变更为"未推送"
														planService.updPlanStatusSyncById(plan.getPlanUuidLong(), SyncStatusEnum.NO_SYNC.getStatus());
													}
												}
												this.pushPlanQueue(sortPlan, queue);
											}catch(Exception ex){
												logger.error("plan line排序异常或者推入redis队列异常", ex);
											}
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


	private boolean isNewAlotContains(List<UserLineBotenceVO> newList, String oldIdBotenceQueueName)
	{
		boolean containsFlg = false;
		if(newList != null && !newList.isEmpty() )
		{
			for (UserLineBotenceVO dto:newList) {
				if(StringUtils.equals(dto.getUserId() +"_"+ dto.getBotenceName(), oldIdBotenceQueueName))
				{
					containsFlg = true;
					break;
				}
			}
		}

		return containsFlg;
	}

	private List<String> getUserBotenceList(List<UserLineBotenceVO> list)
	{
		List<String> tmpList = new ArrayList<>();
		if(list == null || list.isEmpty())
		{
			return tmpList;
		}
		for (UserLineBotenceVO dto:list) {
			tmpList.add(dto.getUserId() +"_"+ dto.getBotenceName());
		}

		return tmpList;
	}

	private boolean isVerChanged(String oldVer)
	{
		// 从redis获取系统最大并发数
		String systemMaxPlanVerCurrent = (String) redisUtil.get(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN_VER);
		if(!StringUtils.equals(systemMaxPlanVerCurrent, oldVer))
		{
			return true;
		}

		return false;
	}


	@Async("asyncSuccPhoneExecutor")
	public boolean pushPlanQueue(DispatchPlan plan, String queue){
	//	logger.info("推入要拨打电话数据KEY:{},{}", queue, plan);
		boolean result = redisUtil.leftPush(queue, plan);
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
				cleanQueueByQueueName(queueKey);
			}
		}
		return false;
	}


	@Override
	public boolean cleanQueueByUserId(String userId) {
		Set<String> queueKeys = redisUtil.getAllKeyMatch(RedisConstant.RedisConstantKey.REDIS_PLAN_QUEUE_USER_LINE_ROBOT+userId);
		if (queueKeys != null) {
			for (String queueKey : queueKeys) {
				cleanQueueByQueueName(queueKey);
			}
		}
		return false;
	}

	@Async("cleanQueueExecutor")
	@Override
	public boolean cleanQueueByQueueName(String queueName) {

		//1.获取redis锁，将拨打计划的redis锁住
		Lock queueLock = new Lock("dispatch.lock" + queueName, "dispatch.lock" + queueName);
		if (distributedLockHandler.tryLock(queueLock)) { // 默认锁设置
			try {

				//将redis拨打队列中的计划在数据库中status_sync状态回退到未进队列状态，并清空redis拨打队列
				List<Long> planUuids = new ArrayList<Long>();
				while (true) {
					DispatchPlan dispatchPlan = (DispatchPlan) redisUtil.lrightPop(queueName);
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
			} finally {
				distributedLockHandler.releaseLock(queueLock);
			}
		}
		return false;
	}
}
