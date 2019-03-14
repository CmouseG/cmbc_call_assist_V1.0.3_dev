package com.guiji.botsentence.dao.ext;

import java.util.List;

public interface BotSentenceTradeExtMapper {
	
	public List<String> queryParentIdListByTemplateIdList(List<String> templateIdList);
    
}