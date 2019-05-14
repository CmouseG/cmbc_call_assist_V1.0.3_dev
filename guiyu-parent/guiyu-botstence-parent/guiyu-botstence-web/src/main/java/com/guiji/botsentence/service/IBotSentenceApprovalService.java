package com.guiji.botsentence.service;

import java.util.List;

import com.guiji.botsentence.dao.entity.BotSentenceDomain;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.vo.DomainVO;
import com.guiji.component.client.config.JsonParam;


public interface IBotSentenceApprovalService {

	/**
	 * 根据条件查询话术模板列表
	 * @param state
	 */
	public List<BotSentenceProcess> getListApprovaling(int pageSize, int pageNo, String templateName, String accountNo);

	public int countApprovaling(String templateName);

	
	/**
	 * 审批通过
	 * @param processId
	 */
	public void passApproval(String processId, List<DomainVO> selectedList, String userId);

	void passAudit(String processId, String userId);

	void resetComDomain(String processId, String userId);

	public void notPassApproval(String processId, String userId);

	public List queryComProcess(String processId);
	
	public List queryComProcess2(String processId);

	public void deployTestSellbotByScp(String processId, String userId);
	
	public void deployTestSellbotByAgent(String processId);
	
	public void publishSentence(String processId,String userId);
	
	public void saveDeploy(List<String> list, String jobId, String processId, String templateId, String userId);
}
