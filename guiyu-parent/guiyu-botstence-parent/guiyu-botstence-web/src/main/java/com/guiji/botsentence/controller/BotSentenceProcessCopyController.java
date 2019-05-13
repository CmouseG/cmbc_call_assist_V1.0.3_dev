package com.guiji.botsentence.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.guiji.auth.api.IAuth;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.BotSentenceShareAuthMapper;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import com.guiji.botsentence.dao.entity.BotSentenceShareAuth;
import com.guiji.botsentence.dao.entity.BotSentenceShareAuthExample;
import com.guiji.botsentence.service.IBotSentenceProcessCopyService;
import com.guiji.botsentence.service.ITradeService;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value="botSentenceProcessCopy")
public class BotSentenceProcessCopyController {

	@Autowired
	private IBotSentenceProcessCopyService botSentenceProcessCopyService;
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;

	@Resource
	private BotSentenceShareAuthMapper botSentenceShareAuthMapper;

	@Resource
	private IAuth iAuth;

	@Resource
	private ITradeService iTradeService;
	
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
		Page<BotSentenceProcessVO> page = new Page<>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);

		String orgCode = iAuth.getOrgByUserId(Long.valueOf(userId)).getBody().getCode();

		BotSentenceShareAuthExample shareAuthExample = new BotSentenceShareAuthExample();
		shareAuthExample.setLimitStart((pageNo-1)*pageSize);
		shareAuthExample.setLimitEnd(pageSize);
	 	if("1".equals(orderType)){
			shareAuthExample.setOrderByClause("share_count desc");
		}else {
			shareAuthExample.setOrderByClause("crt_time desc");
		}

		BotSentenceShareAuthExample.Criteria commonCriteria = shareAuthExample.or();
	 	commonCriteria.andTypeEqualTo("00").andSharedEqualTo(true);
		BotSentenceShareAuthExample.Criteria authCriteria = shareAuthExample.or();
		authCriteria.andTypeEqualTo("01").andSharedEqualTo(true)
				.andAvailableOrgLike("%" + orgCode +",%");;
		if(StringUtils.isNotBlank(templateName)) {
			commonCriteria.andTemplateNameLike("%"+templateName+"%");
			authCriteria.andTemplateNameLike("%"+templateName+"%");
		}
		if(StringUtils.isNotBlank(nickName)) {
			commonCriteria.andNickNameLike("%"+nickName+"%");
			authCriteria.andNickNameLike("%"+templateName+"%");
		}

		int totalNum = botSentenceShareAuthMapper.countByExample(shareAuthExample);
		page.setTotal(totalNum);
		if(totalNum == 0){
			return ServerResult.createBySuccess(page);
		}

		List<BotSentenceShareAuth> shareAuthList = botSentenceShareAuthMapper.selectByExample(shareAuthExample);

		Set<String> processIds = Sets.newHashSet();
		shareAuthList.forEach(shareAuth -> processIds.add(shareAuth.getProcessId()));

		BotSentenceProcessExample processExample = new BotSentenceProcessExample();
		processExample.createCriteria().andProcessIdIn(Lists.newArrayList(processIds));
		List<BotSentenceProcess> processes = botSentenceProcessMapper.selectByExample(processExample);

		Set<String> industryIds = Sets.newHashSet();
		Map<String, String> processIdToIndustryIdMap = Maps.newHashMap();
		processes.forEach(process -> {
			industryIds.add(process.getIndustryId());
			processIdToIndustryIdMap.put(process.getProcessId(), process.getIndustryId());
		});

		Map<String,String> industryIdToFullNameMap = iTradeService.getIndustryIdToFullNameMap(industryIds);

		List<BotSentenceProcessVO> processVOS = Lists.newArrayList();
		shareAuthList.forEach(shareAuth -> {
			BotSentenceProcessVO processVO = new BotSentenceProcessVO();

			BeanUtil.copyProperties(shareAuth, processVO);
			processVO.setShareCount(shareAuth.getShareCount());


			if(null != shareAuth.getLstUpdateTime()) {
				processVO.setCrtTimeStr(DateUtil.dateToString(shareAuth.getLstUpdateTime(), DateUtil.ymdhms));
			}else {
				processVO.setCrtTimeStr(DateUtil.dateToString(shareAuth.getCrtTime(), DateUtil.ymdhms));
			}

			String industryId = processIdToIndustryIdMap.get(shareAuth.getProcessId());
			processVO.setIndustry(industryIdToFullNameMap.get(industryId));

			processVOS.add(processVO);
		});

		page.setRecords(processVOS);

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
