package com.guiji.robot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.robot.dao.UserAiCfgInfoMapper;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.dao.entity.UserAiCfgInfoExample;
import com.guiji.robot.service.IUserAiCfgService;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: UserAiCfgServiceImpl 
* @Description: 用户-机器人配置服务
* @date 2018年11月16日 下午2:21:53 
* @version V1.0  
*/
@Service
public class UserAiCfgServiceImpl implements IUserAiCfgService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
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
	 * 根据用户编号查询用户符合话术模板的配置列表
	 * @return
	 */
	@Override
	public List<UserAiCfgInfo> queryUserAiCfgListByUserId(String userId,String templateId){
		if(StrUtils.isNotEmpty(userId) && StrUtils.isNotEmpty(templateId)) {
			//查询用户所有模板
			List<UserAiCfgInfo> list = this.queryUserAiCfgListByUserId(userId);
			List<UserAiCfgInfo> rtnList = new ArrayList<UserAiCfgInfo>();
			if(ListUtil.isNotEmpty(list)) {
				for(UserAiCfgInfo cfg : list) {
					if(StrUtils.isNotEmpty(cfg.getTemplateIds()) && cfg.getTemplateIds().contains(templateId)) {
						//如果该配置可以使用该话术模板，返回
						rtnList.add(cfg);
					}
				}
			}
			return rtnList;
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
