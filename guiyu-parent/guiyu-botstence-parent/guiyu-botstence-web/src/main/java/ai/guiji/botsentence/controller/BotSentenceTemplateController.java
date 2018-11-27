package ai.guiji.botsentence.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.guiji.botsentence.controller.server.vo.BotSentenceTemplateIndustryVO;
import ai.guiji.botsentence.dao.entity.BotSentenceTemplate;
import ai.guiji.botsentence.service.IBotSentenceTemplateService;
import ai.guiji.botsentence.vo.BotSentenceIndustryVO;
import ai.guiji.botsentence.vo.BotSentenceProcessVO;
import ai.guiji.component.client.config.JsonParam;
import ai.guiji.component.client.util.BeanUtil;
import ai.guiji.component.client.util.DateUtil;
import ai.guiji.component.model.Page;
import ai.guiji.component.model.ServerResult;

/**
 * 
* @ClassName: BotSentenceTemplateController
* @Description: 已生效话术模板前后台处理逻辑类
* @author: 张朋
* @date 2018年8月15日 下午14:36:02 
* @version V1.0
 */
@RestController
@RequestMapping(value="botSentenceTemplate")
public class BotSentenceTemplateController {

	@Autowired
	private IBotSentenceTemplateService botSentenceTemplateService;
	
	@RequestMapping(value="queryIndustryTemplate")
	public ServerResult<List<BotSentenceIndustryVO>> queryIndustryTemplate(@RequestHeader Long userId){
		List<BotSentenceIndustryVO> list = botSentenceTemplateService.queryIndustryTemplate(userId);
		return ServerResult.createBySuccess(list);
	}
	
	
	@RequestMapping(value="queryIndustryTemplateOld")
	public ServerResult<List<BotSentenceTemplate>> queryIndustryTemplateOld(@RequestHeader Long userId){
		List<BotSentenceTemplate> list = botSentenceTemplateService.queryIndustryTemplateOld(userId);
		return ServerResult.createBySuccess(list);
	}
	
	@RequestMapping(value="validateHasTempalte")
	public ServerResult validateHasTempalte(@RequestHeader Long userId){
		boolean flag = botSentenceTemplateService.validateHasTempalte(userId);
		if(flag) {
			return ServerResult.createBySuccess();
		}else {
			return ServerResult.createByErrorMessage("该账号没有创建话术模板权限，请联系客服人员!");
		}
	}
	
	@RequestMapping(value="queryMyTemplate")
	public ServerResult<Page<BotSentenceProcessVO>> queryMyTemplate(@JsonParam int pageSize, @JsonParam int pageNo){
		Page<BotSentenceProcessVO> page = new Page<BotSentenceProcessVO>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		List<BotSentenceTemplate> list = botSentenceTemplateService.queryMyTemplate(pageSize, pageNo);
		List<BotSentenceProcessVO> results = new ArrayList<>();
		
		if(null != list && list.size() > 0) {
			for(BotSentenceTemplate temp : list) {
				BotSentenceProcessVO vo = new BotSentenceProcessVO();
				BeanUtil.copyProperties(temp, vo);
				if(null != temp.getCrtTime()) {
					vo.setCrtTimeStr(DateUtil.dateToString(temp.getCrtTime(), DateUtil.ymdhms));
					vo.setIndustry(temp.getIndustryName());
				}
				results.add(vo);
			}
		}
		
		
		int totalNum = botSentenceTemplateService.countMyTemplate();
		
		page.setRecords(results);
		page.setTotal(totalNum);
		
		return ServerResult.createBySuccess(page);
	}
	
}
