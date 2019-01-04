package com.guiji.robot.service.job;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.model.AiHangupReq;
import com.guiji.robot.service.IAiAbilityCenterService;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.impl.AiCacheService;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;

/** 
* @ClassName: AiResourceJobTimer 
* @Description: AI资源定时任务作业
* @date 2018年11月23日 上午11:34:59 
* @version V1.0  
*/
@Component
public class AiResourceJobTimer {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final int BUSY_TIMEOUT =  10*60*1000;	//忙状态超时时间10分钟
	
	@Autowired
	AiCacheService aiCacheService; 
	@Autowired
	IAiResourceManagerService iAiResourceManagerService;
	@Autowired
	DistributedLockHandler distributedLockHandler;
	@Autowired
	IAiAbilityCenterService iAiAbilityCenterService;
	
	
	/**
	 * 每天晚上释放分配给用户的机器人
	 */
    @Scheduled(cron="0 30 20,23 * * ?")
    public void aiResourRel(){
    	Lock lock = new Lock("LOCK_ROBOT_AI_RELEASE_JOB", "LOCK_ROBOT_AI_RELEASE_JOB");
    	if (distributedLockHandler.tryLock(lock,0L)) { // 默认锁设置,超时时间设置为0ms，要么获取锁，那么获取不到，不重试
    		try {
				long beginTime = System.currentTimeMillis();
				logger.info("定时任务，准备发起[释放全量已分配机器人]开始...");
				//查询所有用户已分配的机器人列表
				Map<String,List<AiInuseCache>> allUserAiInUserMap = aiCacheService.queryAllAiInUse();
				if(allUserAiInUserMap != null && !allUserAiInUserMap.isEmpty()) {
					for(Map.Entry<String, List<AiInuseCache>> allUserAiInuseEntry : allUserAiInUserMap.entrySet()) {
						String userId = allUserAiInuseEntry.getKey();	//用户ID
						List<AiInuseCache> aiList = allUserAiInuseEntry.getValue();	//用户已分配的机器人
						logger.info("开始释放用户{}[{}]个机器人...",userId,aiList==null?0:aiList.size());
						//释放机器人资源
						iAiResourceManagerService.aiBatchRelease(aiList);
						logger.info("释放用户{}[{}]个机器人...完成",userId,aiList==null?0:aiList.size());
					}
				}
				long endTime = System.currentTimeMillis();
				logger.info("定时任务，用时{}S,[释放全量已分配机器人]完成...",(endTime-beginTime)/1000);
			} catch (Exception e) {
				logger.error("定时任务[晚间释放机器人]发生异常",e);
				throw e;
			}finally {
				distributedLockHandler.releaseLock(lock);	//释放锁
			}
    	}else {
    		logger.warn("定时任务[晚间释放机器人]未能获取锁！！！");
    	}
    }
	
	
	
	/**
	 * 早9点-晚8点间，每10分钟检查下，如果某个机器人还是忙的状态，且持续时间超过10分钟，那么应该是资源处理出现了问题，比如呼叫中心调用后，需要调用释放接口，可能因为某些原因没有调用，导致机器人一直被占用
	 * 此处增加处理释放这些机器人
	 */
	@Scheduled(cron="0 0/10 9-20 * * ?")
    public void aiFree(){
    	Lock lock = new Lock("LOCK_ROBOT_AI_FREE_JOB", "LOCK_ROBOT_AI_FREE_JOB");
    	if (distributedLockHandler.tryLock(lock,0L)) { // 默认锁设置,超时时间设置为0ms，要么获取锁，那么获取不到，不重试
    		try {
				long beginTime = System.currentTimeMillis();
				logger.info("定时任务，检查一直被占用未释放的机器人...");
				//查询所有用户已分配的机器人列表
				Map<String,List<AiInuseCache>> allUserAiInUserMap = aiCacheService.queryAllAiInUse();
				if(allUserAiInUserMap != null && !allUserAiInUserMap.isEmpty()) {
					for(Map.Entry<String, List<AiInuseCache>> allUserAiInuseEntry : allUserAiInUserMap.entrySet()) {
						String userId = allUserAiInuseEntry.getKey();	//用户ID
						List<AiInuseCache> aiList = allUserAiInuseEntry.getValue();	//用户已分配的机器人
						if(ListUtil.isNotEmpty(aiList)) {
							for(AiInuseCache ai : aiList) {
								if(RobotConstants.AI_STATUS_B.equals(ai.getAiStatus())) {
									//如果现在是忙的状态
									//这通电话开始时间
									String callTimeStr = ai.getCallingTime();
									Date callTime = DateUtil.parseDate(callTimeStr);
									if(System.currentTimeMillis()-callTime.getTime() > BUSY_TIMEOUT){
										//如果这通电话忙的状态超过了10分钟没有更新，那么将该机器人设置为挂断
										AiHangupReq aiHangupReq = new AiHangupReq();
										BeanUtil.copyProperties(ai, aiHangupReq); //属性拷贝
										aiHangupReq.setPhoneNo(ai.getCallingPhone()); //正在拨打的手机号
										//强制挂断电话
										iAiAbilityCenterService.aiHangup(aiHangupReq);
										logger.info("强制挂断电话,{}",ai);
									}
								}
							}
						}
					}
				}
				long endTime = System.currentTimeMillis();
				logger.info("定时任务，用时{}S,[检查一直被占用未释放的机器人]完成...",(endTime-beginTime)/1000);
			} catch (BeansException e) {
				logger.error("定时任务[检查一直被占用未释放的机器人]发生异常",e);
				throw e;
			}finally {
				distributedLockHandler.releaseLock(lock);	//释放锁
			}
    	}else {
    		logger.warn("定时任务[检查一直被占用未释放的机器人]未能获取锁！！！");
    	}
    }
	
}
