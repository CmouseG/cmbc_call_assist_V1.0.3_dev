package com.guiji.robot.service;

import java.util.List;

import com.guiji.robot.model.AiCallApplyReq;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.service.vo.AiPoolInfo;

/** 
* @ClassName: IAiResourceManagerService 
* @Description: 机器人资源管理
* @date 2018年11月16日 上午9:22:08 
* @version V1.0  
*/
public interface IAiResourceManagerService {
	
	/**
	 * 从AI资源池中获取所有机器人
	 * @return
	 */
	public List<AiInuseCache> queryAiPoolList();
	
	/**
	 * 重新加载sellbot资源
	 */
	public void reloadSellbotResource();
	
	/**
	 * 查询机器人资源池概况
	 * @return
	 */
	AiPoolInfo queryAiPoolInfo();
	
	/**
	 * 从机器人资源池中分配一个机器人给本次申请
	 * @param aiCallApplyReq
	 * @return
	 */
	AiInuseCache aiAssign(AiCallApplyReq aiCallApplyReq);
	
	/**
	 * 机器人资源释放还回进程资源池
	 * 1、将用户inuse缓存清理掉
	 * 2、将机器人资源池中的机器人状态置为闲
	 * @param aiInuse
	 */
	void aiRelease(AiInuseCache aiInuse);
	
	
	/**
	 * 机器人资源释放还回进程资源池
	 * @param aiList
	 */
	void aiBatchRtnProcess(List<AiInuseCache> aiList);
	

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
	 * 查询用户在休息的AI列表（空闲/暂停不可以用）
	 * @param userId
	 * @return
	 */
	List<AiInuseCache> queryUserSleepUseAiList(String userId);
	
	
	/**
	 * 查询用户某个机器人
	 * @param userId 用户id
	 * @param aiNo 机器人编号
	 * @return
	 */
	public AiInuseCache queryUserAi(String userId,String aiNo);
	
}
