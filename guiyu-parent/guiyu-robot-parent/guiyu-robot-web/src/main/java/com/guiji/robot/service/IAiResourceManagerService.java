package com.guiji.robot.service;

import java.util.List;

import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.model.AiHangupReq;
import com.guiji.robot.model.CheckAiReady;
import com.guiji.robot.service.vo.AiBaseInfo;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.service.vo.AiResourceApply;

/** 
* @ClassName: IAiResourceManagerService 
* @Description: 机器人资源管理
* @date 2018年11月16日 上午9:22:08 
* @version V1.0  
*/
public interface IAiResourceManagerService {
	
	/**
	 * 机器人资源分配
	 * @param checkAiReady
	 * @return
	 */
	List<AiInuseCache> aiAssign(AiResourceApply aiResourceApply);
	
	
	/**
	 * 机器人资源释放还回进程资源池
	 * @param aiBaseInfo
	 */
	void aiRelease(AiBaseInfo aiBaseInfo);
	
	
	/**
	 * 机器人资源释放还回进程资源池
	 * @param aiList
	 */
	void aiBatchRelease(List<AiBaseInfo> aiList);
	

	/**
	 * 设置机器人忙-正在打电话
	 * @param aiCallStartReq
	 */
	void aiBusy(AiCallStartReq aiCallStartReq);
	
	
	/**
	 * 设置机器人空闲
	 * @param aiHangupReq
	 */
	void aiFree(AiHangupReq aiHangupReq);
	

	/**
	 * 查询用户已分配的AI列表
	 * @param userId
	 * @return
	 */
	List<AiInuseCache> queryUserInUseAiList(Long userId);
	
	
	/**
	 * 查询用户正在忙的AI列表
	 * @param userId
	 * @return
	 */
	List<AiInuseCache> queryUserBusyUseAiList(Long userId);
	
}
