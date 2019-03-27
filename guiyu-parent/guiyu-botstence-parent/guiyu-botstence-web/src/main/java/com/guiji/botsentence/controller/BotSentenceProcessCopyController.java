package com.guiji.botsentence.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.Removal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceShareAuth;
import com.guiji.botsentence.service.IBotSentenceProcessCopyService;
import com.guiji.botsentence.util.IndustryUtil;
import com.guiji.botsentence.vo.AvaliableOrgVO;
import com.guiji.botsentence.vo.BotSentenceProcessVO;
import com.guiji.botsentence.vo.BotSentenceShareVO;
import com.guiji.common.exception.CommonException;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.client.util.BeanUtil;
import com.guiji.component.client.util.DateUtil;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.model.Page;
import com.guiji.component.result.ServerResult;

@RestController
@RequestMapping(value="botSentenceProcessCopy")
public class BotSentenceProcessCopyController {

	@Autowired
	private IBotSentenceProcessCopyService botSentenceProcessCopyService;
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@RequestMapping(value="copy")
	@Jurisdiction("botsentence_maker_market_get")
	public ServerResult<String> copy(@JsonParam String processId, @JsonParam String orgCode, @RequestHeader String userId, 
			@JsonParam String tempalteName) {
		String newProcessId = botSentenceProcessCopyService.copy(processId, orgCode, userId, tempalteName);
		return ServerResult.createBySuccess(newProcessId);
	}
	
	@RequestMapping(value="share")
	@Jurisdiction("botsentence_maker_toShare")
	public ServerResult saveBotStenceShare(@JsonParam BotSentenceShareVO share, @RequestHeader String userId) {
		share.setUserId(userId);
		botSentenceProcessCopyService.saveBotStenceShare(share);
		return ServerResult.createBySuccess();
	}
	
	@RequestMapping(value="queryBotstenceMarket")
	public ServerResult<Page<BotSentenceProcessVO>> queryBotstenceMarket(@RequestHeader String userId, @JsonParam int pageSize, @JsonParam int pageNo, 
			@JsonParam String templateName, @JsonParam String nickName, @JsonParam String orderType) {
		Page<BotSentenceProcessVO> page = new Page<BotSentenceProcessVO>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		List<BotSentenceShareAuth> list = botSentenceProcessCopyService.queryBotstenceMarket(userId, pageSize, pageNo, templateName, nickName, orderType);
		

		int totalNum = botSentenceProcessCopyService.countBotstenceMarket(userId, templateName, nickName);
		if(null != list) {
			
			List<BotSentenceProcessVO> results = new ArrayList<>();
			
			for(BotSentenceShareAuth temp : list) {
				BotSentenceProcessVO vo = new BotSentenceProcessVO();
				
				BeanUtil.copyProperties(temp, vo);
				
				/*if(null != temp.getCrtTime()) {
					vo.setCrtTimeStr(DateUtil.dateToString(temp.getCrtTime(), DateUtil.ymdhms));
				}*/
				
				BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(vo.getProcessId());
				
				if(null != process.getLstUpdateTime()) {
					vo.setCrtTimeStr(DateUtil.dateToString(process.getLstUpdateTime(), DateUtil.ymdhms));
				}else {
					vo.setCrtTimeStr(DateUtil.dateToString(process.getCrtTime(), DateUtil.ymdhms));
				}
				
				
				vo.setShareCount(temp.getShareCount());
				//设置行业显示三级
				String industryId = process.getIndustryId();
				if(StringUtils.isNotBlank(industryId) && industryId.length() == 6) {
					String level_1 = industryId.substring(0, 2);
					String level_2 = industryId.substring(0, 4);
					String level_3 = industryId;
					Map<String, String> map = IndustryUtil.map;
					vo.setIndustry(map.get(level_1) + "/" + map.get(level_2) + "/" + map.get(level_3));
					//vo.setIndustryId(level_1 + "," + level_2 + "," + level_3);
				}
				
				results.add(vo);
			}
			
			page.setRecords(results);
			page.setTotal(totalNum);
		}
		return ServerResult.createBySuccess(page);
		
	}
	
	@RequestMapping(value="queryAvaliableOrgList")
	public ServerResult<List<AvaliableOrgVO>> queryAvaliableOrgList(@JsonParam String processId) {
		if(StringUtils.isBlank(processId)) {
			throw new CommonException("话术流程编号为空!");
		}
		List<AvaliableOrgVO> list = botSentenceProcessCopyService.queryAvaliableOrgList(processId);
		return ServerResult.createBySuccess(list);
	}
	
	@RequestMapping(value="cancelShare")
	@Jurisdiction("botsentence_maker_cancelShare")
	public ServerResult cancelShare(@JsonParam String processId, @RequestHeader String userId) {
		botSentenceProcessCopyService.cancelShare(processId, userId);
		return ServerResult.createBySuccess();
	}
}
