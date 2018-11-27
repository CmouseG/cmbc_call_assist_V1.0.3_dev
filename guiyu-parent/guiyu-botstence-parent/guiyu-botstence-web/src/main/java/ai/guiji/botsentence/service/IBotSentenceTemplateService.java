package ai.guiji.botsentence.service;

import java.util.List;

import ai.guiji.botsentence.dao.entity.BotSentenceTemplate;
import ai.guiji.botsentence.vo.BotSentenceIndustryVO;

/**
 * 
* @ClassName: IBotSentenceTemplateService
* @Description: 已生效话术模板服务类
* @author: 张朋
* @date 2018年8月15日 下午15:51:02 
* @version V1.0
 */
public interface IBotSentenceTemplateService {

	public List<BotSentenceIndustryVO> queryIndustryTemplate(Long userId);
	
	public List<BotSentenceTemplate> queryIndustryTemplateOld(Long userId);
	
	public List<BotSentenceTemplate> queryMyTemplate(int pageSize, int pageNo);
	
	public int countMyTemplate();
	
	public BotSentenceTemplate getBotSentenceTemplate(String templateId, String version);
	
	public BotSentenceTemplate getBotSentenceTemplate(String processId);
	
	public boolean validateHasTempalte(Long userId);
	
}
