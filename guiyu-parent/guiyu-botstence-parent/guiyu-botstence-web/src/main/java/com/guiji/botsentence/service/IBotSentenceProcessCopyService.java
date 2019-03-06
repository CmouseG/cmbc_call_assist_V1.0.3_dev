package com.guiji.botsentence.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestHeader;

import com.guiji.botsentence.dao.entity.BotSentenceShareAuth;
import com.guiji.botsentence.vo.AvaliableOrgVO;
import com.guiji.botsentence.vo.BotSentenceShareVO;
import com.guiji.component.client.config.JsonParam;

public interface IBotSentenceProcessCopyService {

	public String copy(String processId, String orgCode, String userId, String tempalteName);
	
	public List<BotSentenceShareAuth> queryBotstenceMarket(String userId, int pageSize, int pageNo, String templateName, String nickName, String orderType);
	
	public int countBotstenceMarket(String userId, String templateName, String nickName);
	
	public void saveBotStenceShare(BotSentenceShareVO share);
	
	public void saveBotStenceSharHistory(BotSentenceShareVO share);
	
	public List<AvaliableOrgVO> queryAvaliableOrgList(String processId);
	
	public void cancelShare(String processId, String userId);
}
