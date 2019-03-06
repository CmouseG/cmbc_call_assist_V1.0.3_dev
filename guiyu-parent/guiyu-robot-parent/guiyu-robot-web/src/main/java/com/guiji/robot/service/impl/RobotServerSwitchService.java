package com.guiji.robot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.service.ISellbotService;
import com.guiji.robot.service.vo.HsReplace;
import com.guiji.robot.util.DataLocalCacheUtil;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: RobotServerSwitchService 
* @Description: 机器人服务选择
* @date 2019年3月3日 下午6:14:12 
* @version V1.0  
*/
@Slf4j
@Service
public class RobotServerSwitchService {
	@Autowired
	SellbotServiceImpl sellbotServiceImpl;
	@Autowired
	SellbotMockServiceImpl sellbotMockServiceImpl;
	@Autowired
	FlyDragonServiceImpl flyDragonServiceImpl;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;
	//需要为sellbot mock的话术模板
	@Value("#{'${template.mock:}'.split(',')}")
	private List<String> mockTemplateList;
	private List<Integer> age;

	
	/**
	 * 根据话术模板获取机器人服务
	 * @param templateId
	 * @return
	 */
	public ISellbotService getRobotServerInstance(String templateId) {
		if(StrUtils.isNotEmpty(templateId)) {
			//获取模板
			HsReplace hsReplace = dataLocalCacheUtil.queryTemplate(templateId);
			if(mockTemplateList!=null&&mockTemplateList.contains(templateId)) {
				log.info("话术模板：{}使用挡板模式...",templateId);
				return sellbotMockServiceImpl;
			}else if(RobotConstants.HS_VERSION_FL==hsReplace.getVersion()) {
				//返回飞龙机器人
				return flyDragonServiceImpl;
			}else {
				//默认sellbot机器人
				return sellbotServiceImpl;
			}
		}
		//默认sellbot服务
		return sellbotServiceImpl;
	}
}
