package com.guiji.robot.service;

import java.util.List;

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
	 * @param AaiInuseCache
	 */
	void aiRelease(AiInuseCache aiInuse);
	
	
	/**
	 * 机器人资源释放还回进程资源池
	 * @param aiList
	 */
	void aiBatchRelease(List<AiInuseCache> aiList);
	

	/**
	 * 设置机器人忙-正在打电话
	 * @param aiInuseCache
	 */
	void aiBusy(AiInuseCache aiInuseCache);
	
	
	/**
	 * 设置机器人空闲
	 * @param aiInuseCache
	 */
	void aiFree(AiInuseCache aiInuseCache);
	
	
	/**
	 * 设置机器人暂停不可用
	 * @param aiInuseCache
	 */
	void aiPause(AiInuseCache aiInuseCache);
	

	/**
	 * 查询用户已分配的AI列表
	 * @param userId
	 * @return
	 */
	List<AiInuseCache> queryUserInUseAiList(String userId);
	
	
	/**
	 * 查询用户正在忙的AI列表
	 * @param userId
	 * @return
	 */
	List<AiInuseCache> queryUserBusyUseAiList(String userId);
	
	
	/**
	 * 查询用户某个机器人
	 * @param userId 用户id
	 * @param aiNo 机器人编号
	 * @return
	 */
	public AiInuseCache queryUserAi(String userId,String aiNo);
	
}
