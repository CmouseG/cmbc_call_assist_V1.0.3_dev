package com.guiji.botsentence.dao.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BotSentenceGradeRuleExtMapper {

	List<String> queryDistinctProcessId(@Param("processId") String processId);
	
	List<String> queryDistinctIntentNameProcessId(@Param("processId") String processId);
	
	List<String> queryDistinctRuleNoByProcessIdAndIntentName(@Param("processId") String processId, @Param("intentName") String intentName);
	
	String queryMaxRuleNo(@Param("processId") String processId);
	
	void updateIntentNameByRemark(@Param("processId") String processId, @Param("intentName") String intentName, @Param("remark") String remark);
}