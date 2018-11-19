package com.guiji.robot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.model.AiHangupReq;
import com.guiji.robot.model.CheckAiReady;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.vo.AiBaseInfo;
import com.guiji.robot.service.vo.AiInuseCache;

/** 
* @ClassName: AiResourceManagerServiceImpl 
* @Description: 机器人资源管理
* @date 2018年11月16日 下午2:19:33 
* @version V1.0  
*/
@Service
public class AiResourceManagerServiceImpl implements IAiResourceManagerService{

	
	/**
	 * 机器人资源分配
	 * 增加同步锁-简单点可以先加代码锁，后续改为分布锁，以防止再申请资源时资源冲突
	 * @param checkAiReady
	 * @return
	 */
	@Override
	public List<AiInuseCache> aiAssign(CheckAiReady checkAiReady){
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
