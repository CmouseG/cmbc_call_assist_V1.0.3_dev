package com.guiji.robot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.model.AiHangupReq;
import com.guiji.robot.model.CheckAiReady;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.vo.AiBaseInfo;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.service.vo.AiResourceApply;
import com.guiji.utils.DateUtil;
import com.guiji.utils.RedisUtil;
import com.guiji.utils.SystemUtil;

import lombok.Synchronized;

/** 
* @ClassName: AiResourceManagerServiceImpl 
* @Description: 机器人资源管理
* @date 2018年11月16日 下午2:19:33 
* @version V1.0  
*/
@Service
public class AiResourceManagerServiceImpl implements IAiResourceManagerService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	RedisUtil redisUtil;
	
	/**
	 * 机器人资源分配
	 * 增加同步锁-简单点可以先加代码锁，后续改为分布锁，以防止再申请资源时资源冲突
	 * @param checkAiReady
	 * @return
	 */
	@Override
	@Synchronized
	public List<AiInuseCache> aiAssign(AiResourceApply checkAiReady){
		if(checkAiReady != null) {
			//TODO 调用进程管理服务申请机器人资源
			List<AiInuseCache> list = new ArrayList<AiInuseCache>();
			AiInuseCache aiInuse = new AiInuseCache();
			aiInuse.setAiNo(SystemUtil.getBusiSerialNo("AI")); //机器人临时编号
			aiInuse.setAiStatus(RobotConstants.AI_STATUS_F); //新申请机器人默认空闲状态
			aiInuse.setUserId(checkAiReady.getUserId());
			aiInuse.setTemplateId(checkAiReady.getTemplateId());
			aiInuse.setInitDate(DateUtil.getCurrentymd()); //分配日期
			aiInuse.setInitTime(DateUtil.getCurrentTime()); //分配时间
//			aiInuse.setIp(ip);
//			aiInuse.setPort(port);
			list.add(aiInuse);
			return list;
		}
		return null;
	}
	
	
	/**
	 * 机器人资源释放还回进程资源池
	 * @param aiBaseInfo
	 */
	@Override
	public void aiRelease(AiBaseInfo aiBaseInfo) {

	}
	
	
	/**
	 * 机器人资源释放还回进程资源池
	 * @param aiList
	 */
	@Override
	public void aiBatchRelease(List<AiBaseInfo> aiList) {

	}
	

	/**
	 * 设置机器人忙-正在打电话
	 * @param aiCallStartReq
	 */
	@Override
	public void aiBusy(AiCallStartReq aiCallStartReq) {

	}
	
	
	/**
	 * 设置机器人空闲
	 * @param aiHangupReq
	 */
	@Override
	public void aiFree(AiHangupReq aiHangupReq) {

	}
	

	/**
	 * 查询用户已分配的AI列表
	 * @param userId
	 * @return
	 */
	@Override
	public List<AiInuseCache> queryUserInUseAiList(Long userId){
		return null;
	}
	
	
	/**
	 * 查询用户正在忙的AI列表
	 * @param userId
	 * @return
	 */
	@Override
	public List<AiInuseCache> queryUserBusyUseAiList(Long userId){
		return null;
	}
	
}
