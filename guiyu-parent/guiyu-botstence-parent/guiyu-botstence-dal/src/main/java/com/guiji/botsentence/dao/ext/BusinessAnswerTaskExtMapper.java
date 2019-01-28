package com.guiji.botsentence.dao.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BusinessAnswerTaskExt;

public interface BusinessAnswerTaskExtMapper {
	
	public List<BusinessAnswerTaskExt> queryBusinessAnswerTaskExtById(@Param("processId")String processId);
	
	public int queryBusinessAnswerTaskExtCount(String processId);
	
	public String queryKeywordsByIntentId(String intentId);
	
	public Map<String,String> queryVoliceUrlByIntentId(String intentId);
	
	public int getLastbranchNo(String processId);
}
