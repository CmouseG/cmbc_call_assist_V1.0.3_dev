package com.guiji.robot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.robot.dao.UserAiCfgInfoMapper;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.dao.entity.UserAiCfgInfoExample;
import com.guiji.robot.service.IUserAiCfgService;

/** 
* @ClassName: UserAiCfgServiceImpl 
* @Description: 用户-机器人配置服务
* @date 2018年11月16日 下午2:21:53 
* @version V1.0  
*/
@Service
public class UserAiCfgServiceImpl implements IUserAiCfgService{
	@Autowired
	private UserAiCfgInfoMapper userAiCfgInfoMapper;
	
	/**
	 * 保存或者更新一条用户-机器人配置信息
	 * 同时记录历史
	 * @param userAiCfgInfo
	 * @return
	 */
	@Override
	public UserAiCfgInfo saveOrUpdate(UserAiCfgInfo userAiCfgInfo) {
		return null;
	}
	
	
	/**
	 * 根据用户编号查询用户-机器人配置信息列表
	 * @return
	 */
	@Override
	public List<UserAiCfgInfo> queryUserAiCfgListByUserId(String userId){
		if(userId != null) {
			UserAiCfgInfoExample example = new UserAiCfgInfoExample();
			example.createCriteria().andUserIdEqualTo(userId);
			return userAiCfgInfoMapper.selectByExample(example);
		}
		return null;
	}
	
	
	/**
	 * 查询用户下可以使用某个话术的机器人列表
	 * @param userId
	 * @param templateId
	 * @return
	 */
	@Override
	public List<UserAiCfgInfo> queryUserAiCfgListByUserIdAndTemplate(String userId,String templateId){
		return null;
	}
}
