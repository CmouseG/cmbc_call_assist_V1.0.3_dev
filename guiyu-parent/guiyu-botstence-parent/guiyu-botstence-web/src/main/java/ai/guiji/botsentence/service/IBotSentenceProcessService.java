package ai.guiji.botsentence.service;

import java.util.List;

import ai.guiji.botsentence.dao.entity.BotSentenceDomain;
import ai.guiji.botsentence.dao.entity.BotSentenceIntent;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.VoliceInfoExt;
import ai.guiji.botsentence.vo.BlankBranch;
import ai.guiji.botsentence.vo.BlankDomain;
import ai.guiji.botsentence.vo.BotSentenceProcessVO;
import ai.guiji.botsentence.vo.FlowEdge;
import ai.guiji.botsentence.vo.FlowInfoVO;
import ai.guiji.botsentence.vo.FlowNode;
import ai.guiji.botsentence.vo.ProcessInfo;
import ai.guiji.botsentence.vo.RefuseBranchVO;
import ai.guiji.botsentence.vo.SaveNodeVO;
import ai.guiji.component.client.config.JsonParam;

/**
 * 
* @ClassName: IBotSentenceProcessService
* @Description: 话术流程相关服务类
* @author: 张朋
* @date 2018年8月14日 下午4:36:02 
* @version V1.0
 */
public interface IBotSentenceProcessService {

	/**
	 * 根据条件查询话术模板列表
	 * @param templateName
	 * @param accountNo
	 * @param
	 */
	public List<BotSentenceProcess> queryBotSentenceProcessList(int pageSize, int pageNo, String templateName, String accountNo,Long userId);

	public int countBotSentenceProcess(String templateName, String accountNo,Long userId);

	/**
	 * 根据现有的话术模板创建一套新模板
	 * @param paramVO
	 */
	public String createBotSentenceTemplate(BotSentenceProcessVO paramVO,Long userId);
	
	
	/**
	 * 修改话术模板
	 * @param processId
	 */
	public String updateBotSentenceTemplate(String processId, String templateName, String industry,Long userId);
	
	/**
	 * 提交话术审核
	 * @param processId
	 */
	public void submit(String processId,Long userId);
	
	
	/**
	 * 删除话术
	 * @param processId
	 */
	public void delete(String processId,Long userId);
	
	
	
	public List queryCommonDialog(String processId);
	
	public void updateCommonDialog(String voliceId, String content, String keywords, String intentId, String branchId,Long userId);
	
	public void saveRefuseBranch(String processId, String domainName, List<String> voliceIdList,Long userId);
	
	public void deleteRefuseBranch(String processId, String domainName, String voliceId);
	
	public void deleteDomain(String processId, String domainId);
	
	public String deleteDomainSimple(String domainId);
	
	public void deleteBranch(String processId, String branchId);
	
	public void updateProcessState(String processId,Long userId);
	
	public String getEndDomainName(String processId, String domainId);
	
	public FlowInfoVO initFlowInfo(String processId);
	
	public BotSentenceDomain saveNode(FlowNode node,Long userId);
	
	public void saveEdge(String processId, FlowEdge edge,Long userId);
	
	public void saveFlow(FlowInfoVO flow,Long userId);
	
	public boolean queryTTSStatus(List<VoliceInfoExt> list, String processId);
	
	public void saveSoundType(String processId, String soundType,Long userId);
	
	public BotSentenceProcess queryBotsentenceProcessInfo(String processId);
	
	public BotSentenceIntent queryKeywordsListByBranchId(String branchId);
}
