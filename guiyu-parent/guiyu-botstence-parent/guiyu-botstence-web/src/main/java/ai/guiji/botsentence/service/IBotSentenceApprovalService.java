package ai.guiji.botsentence.service;

import java.util.List;

import ai.guiji.botsentence.dao.entity.BotSentenceDomain;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.vo.DomainVO;
import ai.guiji.component.client.config.JsonParam;


public interface IBotSentenceApprovalService {

	/**
	 * 根据条件查询话术模板列表
	 * @param state
	 */
	public List<BotSentenceProcess> getListApprovaling(int pageSize, int pageNo, String templateName, String accountNo, String orgCode);

	public int countApprovaling(String templateName, String orgCode);

	
	/**
	 * 审批通过
	 * @param processId
	 */
	public void passApproval(String processId, List<DomainVO> selectedList,Long userId);

	public void notPassApproval(String processId,Long userId);

	public List queryComProcess(String processId);
	
	public List queryComProcess2(String processId);
	
	public void publishSentence(String processId,Long userId);

}
