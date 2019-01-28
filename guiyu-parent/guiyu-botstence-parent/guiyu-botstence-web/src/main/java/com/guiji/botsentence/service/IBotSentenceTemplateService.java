package com.guiji.botsentence.service;

import java.util.List;

import com.guiji.botsentence.dao.entity.BotSentenceTemplate;
import com.guiji.botsentence.vo.BotSentenceIndustryVO;

/**
 * 
* @ClassName: IBotSentenceTemplateService
* @Description: 已生效话术模板服务类
* @author: 张朋
* @date 2018年8月15日 下午15:51:02 
* @version V1.0
 */
public interface IBotSentenceTemplateService {

	public List<BotSentenceIndustryVO> queryIndustryTemplate(String userId);
	
	public List<BotSentenceTemplate> queryIndustryTemplateOld(String userId);
	
	public List<BotSentenceTemplate> queryMyTemplate(int pageSize, int pageNo);
	
	public int countMyTemplate();
	
	public BotSentenceTemplate getBotSentenceTemplate(String templateId, String version);
	
	public BotSentenceTemplate getBotSentenceTemplate(String processId);
	
	public boolean validateHasTempalte(String userId);
	
	public List<BotSentenceTemplate> queryTemplateByIndustry(String industryId);
	
}
