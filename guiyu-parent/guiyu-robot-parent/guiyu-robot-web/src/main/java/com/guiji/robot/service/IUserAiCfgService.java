package com.guiji.robot.service;

import java.util.List;

import com.guiji.common.model.Page;
import com.guiji.robot.dao.entity.UserAiCfgBaseInfo;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.service.vo.UserAiCfgQueryCondition;

/** 
* @ClassName: IUserAiCfgService 
* @Description: 用户-机器人配置服务
* @date 2018年11月15日 下午8:24:20 
* @version V1.0  
*/
public interface IUserAiCfgService {
	
	/**
	 * 机器人数量总控配置
	 * @param userAiCfgBaseInfo
	 * @return
	 */
	public UserAiCfgBaseInfo putupUserCfgBase(UserAiCfgBaseInfo userAiCfgBaseInfo);
	
	
	/**
	 * 查询用户机器人配置基本信息
	 * @param userId
	 * @return
	 */
	public UserAiCfgBaseInfo queryUserAiCfgBaseInfoByUserId(String userId);
	
	/**
	 * 分页查询 用户机器人配置基本信息
	 * @param pageNo
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public Page<UserAiCfgBaseInfo> queryUserAiCfgBaseInfoFroPageByUserId(int pageNo, int pageSize,String userId);
	
	
	/**
	 * 根据用户编号查询用户-机器人配置信息列表
	 * @return
	 */
	List<UserAiCfgInfo> queryUserAiCfgListByUserId(String userId);
	
	
	/**
	 * 分页查询 用户机器人配置详情
	 * @param pageNo
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public Page<UserAiCfgInfo> queryCustAccountForPage(int pageNo, int pageSize,UserAiCfgQueryCondition condition);
	
	/**
	 * 根据用户编号查询用户符合话术模板的配置列表
	 * @param userId
	 * @param templateId
	 * @return
	 */
	public List<UserAiCfgInfo> queryUserAiCfgListByUserId(String userId,String templateId);
	
	
	/**
	 * 查询用户下可以使用某个话术的机器人列表
	 * @param userId
	 * @param templateId
	 * @return
	 */
	List<UserAiCfgInfo> queryUserAiCfgListByUserIdAndTemplate(String userId,String templateId);
	
	
	/**
	 * 用户资源变更服务
	 * @param userAiCfgInfo
	 * @return
	 */
	UserAiCfgInfo userAiCfgChange(UserAiCfgBaseInfo userBaseInfo,UserAiCfgInfo userAiCfgInfo);
	
	/**
	 * 删除用户一条资源配置信息
	 * @param id
	 */
	void delUserCfg(String userId,String id);
}
