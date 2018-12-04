package com.guiji.robot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.model.UserAiCfgDetailVO;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.StrUtils;
import ai.guiji.botsentence.api.IBotSentenceProcess;
import ai.guiji.botsentence.api.entity.BotSentenceProcess;
import ai.guiji.botsentence.api.entity.ServerResult;

/** 
* @ClassName: ControllerUtil 
* @Description: controller工具服务
* @date 2018年12月4日 上午10:34:04 
* @version V1.0  
*/
@Service
public class ControllerUtil {
	@Autowired
	IBotSentenceProcess iBotSentenceProcess;
	
	/**
	 * 将用户配置转为前端显示VO
	 * @param userAiCfgList
	 * @return
	 */
	public List<UserAiCfgDetailVO> changeUserAiCfg2VO(List<UserAiCfgInfo> userAiCfgList){
		if(ListUtil.isNotEmpty(userAiCfgList)) {
			List<UserAiCfgDetailVO> rtnList = new ArrayList<UserAiCfgDetailVO>();
			Map<String,String> templateMap = new HashMap<String,String>();
			for(UserAiCfgInfo userAiCfg : userAiCfgList) {
				UserAiCfgDetailVO vo = new UserAiCfgDetailVO();
				String templateIds = userAiCfg.getTemplateIds();	//话术模板
				if(StrUtils.isNotEmpty(templateIds)) {
					String[] templateArray = templateIds.split(",");	//多模板逗号分隔
					String templateNames = "";
					for(String template : templateArray) {
						if(templateMap.get(template) != null) {
							//缓存中有，直接取
							templateNames = templateNames + "," +templateMap.get(template);
						}else {
							//缓存中没有，重新查询
							ServerResult<List<BotSentenceProcess>> templateData = iBotSentenceProcess.getTemplateById(template);
							if(templateData != null && ListUtil.isNotEmpty(templateData.getData())) {
								BotSentenceProcess botSentenceProcess = templateData.getData().get(0);
								if(botSentenceProcess != null) {
									templateMap.put(template, botSentenceProcess.getTemplateName());
									templateNames = templateNames + "," +botSentenceProcess.getTemplateName();
								}
							}
						}
					}
					if(StrUtils.isNotEmpty(templateNames)) {
						templateNames = templateNames.substring(1);	//去掉第一个逗号
					}
					//设置模板名称
					vo.setTemplateNames(templateNames);
				}
				BeanUtil.copyProperties(userAiCfg, vo);
				rtnList.add(vo);
			}
			return rtnList;
		}
		return null;
	}
}
