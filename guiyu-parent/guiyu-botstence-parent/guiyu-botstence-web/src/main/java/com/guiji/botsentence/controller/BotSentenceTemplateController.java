package com.guiji.botsentence.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.botsentence.controller.server.vo.BotSentenceTemplateTradeVO;
import com.guiji.botsentence.dao.entity.BotSentenceTemplate;
import com.guiji.botsentence.service.IBotSentenceProcessService;
import com.guiji.botsentence.service.IBotSentenceTemplateService;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.vo.BotSentenceIndustryVO;
import com.guiji.botsentence.vo.BotSentenceProcessVO;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.client.util.BeanUtil;
import com.guiji.component.client.util.DateUtil;
import com.guiji.component.model.Page;
import com.guiji.component.result.ServerResult;

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
	
	@Autowired
	private IBotSentenceProcessService botSentenceProcessService;
	
	@RequestMapping(value="queryIndustryTemplate")
	public ServerResult<List<BotSentenceIndustryVO>> queryIndustryTemplate(@RequestHeader String userId){
		List<BotSentenceIndustryVO> list = botSentenceTemplateService.queryIndustryTemplate(userId);
		return ServerResult.createBySuccess(list);
	}
	
	
	@RequestMapping(value="queryIndustryTemplateOld")
	public ServerResult<List<BotSentenceTemplate>> queryIndustryTemplateOld(@RequestHeader String userId){
		List<BotSentenceTemplate> list = botSentenceTemplateService.queryIndustryTemplateOld(userId);
		return ServerResult.createBySuccess(list);
	}
	
	@RequestMapping(value="validateHasTempalte")
	public ServerResult validateHasTempalte(@RequestHeader String userId){
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
	
	
	@RequestMapping(value="queryTemplateByAccountNo")
	public ServerResult<List<BotSentenceTemplateTradeVO>> queryTemplateByAccountNo(@JsonParam String accountNo) {
		if(StringUtils.isNotBlank(accountNo)) {
			List<BotSentenceTemplateTradeVO> list = botSentenceProcessService.queryIndustryListByAccountNo(accountNo, accountNo);
			/*for(BotSentenceTemplateTradeVO trade : list) {
				if(3 == trade.getLevel()) {
					List<BotSentenceTemplate> templateList = botSentenceTemplateService.queryTemplateByIndustry(trade.getValue());
					trade.setProcessList(templateList);
				}
			}*/
			return ServerResult.createBySuccess(list);
		}else {
			return ServerResult.createByErrorMessage("查询账号不能为空!");
		}
	}
	
	
	@RequestMapping(value="queryTemplateByIndustry")
	public ServerResult<List<BotSentenceTemplate>> queryTemplateByIndustry(@JsonParam String industryId) {
		if(StringUtils.isNotBlank(industryId)) {
			List<BotSentenceTemplate> templateList = botSentenceTemplateService.queryTemplateByIndustry(industryId);
			return ServerResult.createBySuccess(templateList);
		}else {
			return ServerResult.createByErrorMessage("查询账号不能为空!");
		}
	}
	
}
