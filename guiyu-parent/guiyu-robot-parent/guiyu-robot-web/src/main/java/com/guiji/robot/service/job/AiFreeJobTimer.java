package com.guiji.robot.service.job;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.model.AiHangupReq;
import com.guiji.robot.service.IAiAbilityCenterService;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.impl.AiCacheService;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

/** 
* @ClassName: aiFreeJobTimer 
* @Description: 定时自动释放机器人
* @date 2019年1月9日 下午3:20:43 
* @version V1.0  
*/
@Component
@JobHandler(value="aiFreeJobTimer")
public class AiFreeJobTimer extends IJobHandler{
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
	 * 早9点-晚8点间，每10分钟检查下，如果某个机器人还是忙的状态，且持续时间超过10分钟，那么应该是资源处理出现了问题，比如呼叫中心调用后，需要调用释放接口，可能因为某些原因没有调用，导致机器人一直被占用
	 * 此处增加处理释放这些机器人
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		long beginTime = System.currentTimeMillis();
		XxlJobLogger.log("定时任务，检查一直被占用未释放的机器人...");
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
								aiHangupReq.setTemplateId(ai.getTemplateIds()); //模板
								//强制挂断电话
								iAiAbilityCenterService.aiHangup(aiHangupReq);
								XxlJobLogger.log("强制挂断电话,{}",ai);
							}
						}
					}
				}
			}
		}
		long endTime = System.currentTimeMillis();
		XxlJobLogger.log("定时任务，用时{}S,[检查一直被占用未释放的机器人]完成...",(endTime-beginTime)/1000);
		return SUCCESS;
	}
	
}
