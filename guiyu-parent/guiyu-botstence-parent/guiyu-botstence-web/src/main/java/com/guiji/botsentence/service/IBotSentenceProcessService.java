package com.guiji.botsentence.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.botsentence.controller.server.vo.BotSentenceTemplateIndustryVO;
import com.guiji.botsentence.controller.server.vo.BotSentenceTemplateTradeVO;
import com.guiji.botsentence.dao.entity.BotSentenceDomain;
import com.guiji.botsentence.dao.entity.BotSentenceIntent;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import com.guiji.botsentence.dao.entity.VoliceInfoExt;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample.Criteria;
import com.guiji.botsentence.vo.BlankBranch;
import com.guiji.botsentence.vo.BlankDomain;
import com.guiji.botsentence.vo.BotSentenceProcessVO;
import com.guiji.botsentence.vo.CommonDialogVO;
import com.guiji.botsentence.vo.FlowEdge;
import com.guiji.botsentence.vo.FlowInfoVO;
import com.guiji.botsentence.vo.FlowNode;
import com.guiji.botsentence.vo.ProcessInfo;
import com.guiji.botsentence.vo.RefuseBranchVO;
import com.guiji.botsentence.vo.SaveNodeVO;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.result.ServerResult;

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
	 * @param state
	 */
	public List<BotSentenceProcess> queryBotSentenceProcessList(int pageSize, int pageNo, String templateName, String accountNo, String userId);

	public int countBotSentenceProcess(String templateName, String accountNo, String userId);

	/**
	 * 根据现有的话术模板创建一套新模板
	 * @param paramVO
	 */
	public String createBotSentenceTemplate(BotSentenceProcessVO paramVO, String userId);
	
	
	/**
	 * 修改话术模板
	 * @param processId
	 */
	public String updateBotSentenceTemplate(String processId, String templateName, String industry, String userId);
	
	/**
	 * 提交话术审核
	 * @param processId
	 */
	public void submit(String processId, String userId);
	
	
	/**
	 * 删除话术
	 * @param processId
	 */
	public void delete(String processId, String userId);
	
	
	
	public List queryCommonDialog(String processId);
	
	public List queryCommonDialogSimple(String processId);
	
	public void updateCommonDialog(CommonDialogVO commonDialog, String userId);
	
	public void saveRefuseBranch(String processId, String domainName, List<String> voliceIdList, String userId);
	
	public void deleteRefuseBranch(String processId, String domainName, String voliceId);
	
	public void deleteDomain(String processId, String domainId, String userId);
	
	public String deleteDomainSimple(String domainId);
	
	public void deleteBranch(String processId, String branchId, String userId);
	
	public void updateProcessState(String processId, String userId);
	
	public String getEndDomainName(String processId, String domainId);
	
	public FlowInfoVO initFlowInfo(String processId);
	
	public BotSentenceDomain saveNode(FlowNode node, String userId);
	
	public void saveEdge(String processId, FlowEdge edge, String userId);
	
	public void saveFlow(FlowInfoVO flow, String userId);
	
	public boolean queryTTSStatus(List<VoliceInfoExt> list, String processId);
	
	public void saveSoundType(String processId, String soundType, String userId);
	
	public BotSentenceProcess queryBotsentenceProcessInfo(String processId);
	
	public BotSentenceIntent queryKeywordsListByBranchId(String branchId);
	
	public List<BotSentenceProcess> queryBotSentenceProcessListByAccountNo(String accountNo);	
	
	public void generateTTS(List<VoliceInfoExt> list, String processId, String userId);
	
	public List<BotSentenceDomain> getAllDomainList(String processId);
	
	public List<BotSentenceDomain> getDomainList(String processId);
	
	public List<BotSentenceTemplateTradeVO> queryIndustryListByAccountNo(String accountNo, String userId);
	
	public List<BotSentenceTemplateTradeVO> queryIndustryListByOrgCode(String orgCode);
	
	public List<BotSentenceTemplateTradeVO> queryAllIndustryList();
	
	List<BotSentenceProcess> getTemplateByOrgCode(String orgCode);
	
	public List<BotSentenceProcess> getTemplateBySelf(String accountNo);
	
	public List<BotSentenceProcess> getTemplateById(String templateId);
	
	public List<Object> getAvailableTemplateBySelf(String accountNo);
	
	public BotSentenceProcess getBotsentenceProcessByTemplateId(String templateId);
	
	public void generateTTSCallback(String id, String url);
	
	public void saveTrade(String industryName, String industryId, String userId);
	
	public int countTemplateByOrgCode(String orgCode);
	
	public List<BotSentenceTemplateTradeVO> queryTradeListByTradeIdList(List<String> tradeIdList);
}
