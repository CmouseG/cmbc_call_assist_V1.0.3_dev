package ai.guiji.botsentence.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.guiji.botsentence.constant.Constant;
import ai.guiji.botsentence.dao.entity.BotSentenceIntent;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.service.IBotSentenceProcessService;
import ai.guiji.botsentence.vo.BotSentenceProcessVO;
import ai.guiji.botsentence.vo.CommonDialogVO;
import ai.guiji.botsentence.vo.FlowEdge;
import ai.guiji.botsentence.vo.FlowInfoVO;
import ai.guiji.botsentence.vo.FlowNode;
import ai.guiji.botsentence.vo.GenerateTTSVO;
import ai.guiji.component.client.config.JsonParam;
import ai.guiji.component.client.util.BeanUtil;
import ai.guiji.component.client.util.DateUtil;
import ai.guiji.component.model.Page;
import ai.guiji.component.model.ServerResult;

/**
 * 
* @ClassName: BotSentenceProcessController
* @Description: 话术流程前后台处理逻辑类
* @author: 张朋
* @date 2018年8月15日 下午14:36:02 
* @version V1.0
 */
@RestController
@RequestMapping(value="botSentenceProcess")
public class BotSentenceProcessController {

	@Autowired
	private IBotSentenceProcessService botSentenceProcessService;
	
	
	/**
	 * 根据条件查询话术流程列表
	 * @param pageSize
	 * @param pageNo
	 * @param templateName
	 * @param accountNo
	 */
	@RequestMapping(value="queryBotSentenceProcessListByPage")
	public ServerResult<Page<BotSentenceProcessVO>> queryBotSentenceProcessListByPage(@JsonParam int pageSize, @JsonParam int pageNo, @JsonParam String templateName, @JsonParam String accountNo,@RequestHeader Long userId) {
		Page<BotSentenceProcessVO> page = new Page<BotSentenceProcessVO>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		List<BotSentenceProcess> list = botSentenceProcessService.queryBotSentenceProcessList(pageSize, pageNo, templateName, accountNo,userId);
		
		int totalNum = botSentenceProcessService.countBotSentenceProcess(templateName, accountNo,userId);
		if(null != list) {
			
			List<BotSentenceProcessVO> results = new ArrayList<>();
			
			for(BotSentenceProcess temp : list) {
				BotSentenceProcessVO vo = new BotSentenceProcessVO();
				BeanUtil.copyProperties(temp, vo);
				
				if(Constant.APPROVE_MAEKING.equals(temp.getState())) {
					vo.setStateName("制作中");
				}else if(Constant.APPROVE_CHECKING.equals(temp.getState())) {
					vo.setStateName("审核中");
				}else if(Constant.APPROVE_PASS.equals(temp.getState())) {
					vo.setStateName("审核通过");
				}else if(Constant.APPROVE_NOTPASS.equals(temp.getState())) {
					vo.setStateName("审核不通过");
				}else if(Constant.APPROVE_ONLINE.equals(temp.getState())) {
					vo.setStateName("已上线");
				}else if(Constant.DEPLOYING.equals(temp.getState())) {
					vo.setStateName("部署中");
				}
				
				
				
				if(null != temp.getCrtTime()) {
					vo.setCrtTimeStr(DateUtil.dateToString(temp.getCrtTime(), DateUtil.ymdhms));
				}
				if(null != temp.getLstUpdateTime()) {
					vo.setLstUpdateTimeStr(DateUtil.dateToString(temp.getLstUpdateTime(), DateUtil.ymdhms));
				}else {
					vo.setLstUpdateTimeStr(DateUtil.dateToString(temp.getCrtTime(), DateUtil.ymdhms));
				}
				if(null != temp.getApproveTime()) {
					vo.setApproveTimeStr(DateUtil.dateToString(temp.getApproveTime(), DateUtil.ymdhms));
				}
				
				results.add(vo);
			}
			
			page.setRecords(results);
			page.setTotal(totalNum);
		}
		return ServerResult.createBySuccess(page);
	}
	
	
	/**
	 * 根据选定模板创建一套新话术流程
	 * @param
	 */
	@RequestMapping(value="createBotSentenceProcess")
	public ServerResult<String> createBotSentenceProcess(@JsonParam BotSentenceProcessVO paramVO,@JsonParam Long userId) {
		if(null != paramVO && StringUtils.isNotBlank(paramVO.getProcessId()) && 
				StringUtils.isNotBlank(paramVO.getTemplateName())) {
			paramVO.setFlag("00");
			String new_processId = botSentenceProcessService.createBotSentenceTemplate(paramVO,userId);
			return ServerResult.createBySuccess(new_processId);
		}
		return ServerResult.createByErrorMessage("创建模板失败!");
	}
	
	
	/**
	 * 修改话术
	 * @param accountNo
	 */
	@RequestMapping(value="updateBotSentenceProcess")
	public ServerResult<String> updateBotSentenceProcess(@JsonParam String accountNo, @JsonParam String processId,@RequestHeader Long userId) {
		if(StringUtils.isNotBlank(accountNo) && StringUtils.isNotBlank(processId)) {
			BotSentenceProcessVO paramVO = new BotSentenceProcessVO();
			paramVO.setAccountNo(accountNo);
			paramVO.setProcessId(processId);
			paramVO.setFlag("01");
			String new_processId = botSentenceProcessService.createBotSentenceTemplate(paramVO,userId);
			return ServerResult.createBySuccess(new_processId);
		}
		return ServerResult.createByErrorMessage("修改模板失败!");
	}
	
	/**
	 * 话术流程提交审核
	 * @param processId
	 */
	@RequestMapping(value="submit")
	public ServerResult submit(@JsonParam String processId,@RequestHeader Long userId) {
		botSentenceProcessService.submit(processId,userId);
		return ServerResult.createBySuccess();
	}
	
	
	/**
	 * 修改话术名称
	 * @param processId
	 */
	@RequestMapping(value="updateTemplateName")
	public ServerResult updateTemplateName(@JsonParam String processId, @JsonParam String templateName, @JsonParam String industry,@RequestHeader Long userId) {
		botSentenceProcessService.updateBotSentenceTemplate(processId, templateName, industry,userId);
		return ServerResult.createBySuccess();
	}
	
	/**
	 * 删除话术
	 * @param processId
	 */
	@RequestMapping(value="deleteTemplate")
	public ServerResult deleteTemplate(@JsonParam String processId,@RequestHeader Long userId) {
		botSentenceProcessService.delete(processId,userId);
		return ServerResult.createBySuccess();
	}
	
	
	/**
	 * 查询通用对话列表信息
	 */
	@RequestMapping(value="queryCommonDialog")
	public ServerResult<List<CommonDialogVO>> queryCommonDialog(@JsonParam String processId) {
		List<CommonDialogVO> list = botSentenceProcessService.queryCommonDialog(processId);
		return ServerResult.createBySuccess(list);
	}
	
	/**
	 * 更新通用对话话术信息
	 */
	@RequestMapping(value="updateCommonDialog")
	public ServerResult updateCommonDialog(@JsonParam String voliceId, @JsonParam String content, @JsonParam String keywords, @JsonParam String intentId, @JsonParam String branchId,@RequestHeader Long userId) {
		botSentenceProcessService.updateCommonDialog(voliceId, content, keywords, intentId, branchId,userId);
		return ServerResult.createBySuccess();
	}
	
	/**
	 * 初始化话术流程图
	 */
	@RequestMapping(value="initFlowInfo")
	public ServerResult<FlowInfoVO> initFlowInfo(@JsonParam String processId) {
		FlowInfoVO flow = botSentenceProcessService.initFlowInfo(processId);
		return ServerResult.createBySuccess(flow);
	}
	
	
	/**
	 * 保存domain挽回话术
	 * @param
	 * @return
	 */
	@RequestMapping(value="saveRefuseBranch")
	public ServerResult<FlowInfoVO> saveRefuseBranch(@JsonParam String processId, @JsonParam String domainName, @JsonParam List<String> voliceIdList,@RequestHeader Long userId) {
		botSentenceProcessService.saveRefuseBranch(processId, domainName, voliceIdList,userId);
		FlowInfoVO flow = botSentenceProcessService.initFlowInfo(processId);
		return ServerResult.createBySuccess(flow);
	}
	
	
	/**
	 * 删除domain挽回话术
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteRefuseBranch")
	public ServerResult<FlowInfoVO> deleteRefuseBranch(@JsonParam String processId, @JsonParam String domainName, @JsonParam String voliceId) {
		botSentenceProcessService.deleteRefuseBranch(processId, domainName, voliceId);
		FlowInfoVO flow = botSentenceProcessService.initFlowInfo(processId);
		return ServerResult.createBySuccess(flow);
	}
	
	/**
	 * 删除domain
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteDomain")
	public ServerResult<FlowInfoVO> deleteDomain(@JsonParam String processId, @JsonParam String domainId) {
		botSentenceProcessService.deleteDomain(processId, domainId);
		FlowInfoVO flow = botSentenceProcessService.initFlowInfo(processId);
		return ServerResult.createBySuccess(flow);
	}
	
	
	/**
	 * 删除branch
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteBranch")
	public ServerResult<FlowInfoVO> deleteBranch(@JsonParam String processId, @JsonParam String branchId) {
		botSentenceProcessService.deleteBranch(processId, branchId);
		FlowInfoVO flow = botSentenceProcessService.initFlowInfo(processId);
		return ServerResult.createBySuccess(flow);
	}
	
	
	/**
	 * 获取结束节点的名称
	 * @param processId
	 * @param domainId
	 * @return
	 */
	@RequestMapping(value="getEndDomainName")
	public ServerResult<String> getEndDomainName(@JsonParam String processId, @JsonParam String domainId){
		String name = botSentenceProcessService.getEndDomainName(processId, domainId);
		return ServerResult.createBySuccess(name);
	}
	
	/**
	 * 保存卡片信息
	 * @param
	 * @return
	 */
	@RequestMapping(value="saveNode")
	public ServerResult<FlowInfoVO> saveNode(@JsonParam FlowNode node,@RequestHeader Long userId){
		botSentenceProcessService.saveNode(node,userId);
		FlowInfoVO flow = botSentenceProcessService.initFlowInfo(node.getProcessId());
		return ServerResult.createBySuccess(flow);
	}
	
	/**
	 * 保存连线信息
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value="saveEdge")
	public ServerResult<FlowInfoVO> saveEdge(@JsonParam FlowEdge edge,@RequestHeader Long userId){
		botSentenceProcessService.saveEdge(edge.getProcessId(), edge,userId);
		FlowInfoVO flow = botSentenceProcessService.initFlowInfo(edge.getProcessId());
		return ServerResult.createBySuccess(flow);
	}
	
	
	/**
	 * 保存流程信息
	 * @param flow
	 * @return
	 */
	@RequestMapping(value="saveFlow")
	public ServerResult<FlowInfoVO> saveFlow(@JsonParam FlowInfoVO flow,@RequestHeader Long userId){
		botSentenceProcessService.saveFlow(flow,userId);
		flow = botSentenceProcessService.initFlowInfo(flow.getProcessId());
		return ServerResult.createBySuccess(flow);
	}
	
	
	
	/**
	 * 查询TTS合成录音的状态
	 * @param
	 * @return
	 */
	@RequestMapping(value="queryTTSStatus")
	public ServerResult<Boolean> queryTTSStatus(@JsonParam GenerateTTSVO param, @JsonParam String processId){
		boolean flag = botSentenceProcessService.queryTTSStatus(param.getList(), processId);
		return ServerResult.createBySuccess(flag);
	}
	
	/**
	 * 选择录音师
	 * @param processId
	 * @param soundType
	 * @return
	 */
	@RequestMapping(value="saveSoundType")
	public ServerResult saveSoundType(@JsonParam String processId, @JsonParam String soundType,@RequestHeader Long userId){
		botSentenceProcessService.saveSoundType(processId, soundType,userId);
		return ServerResult.createBySuccess();
	}
	
	
	/**
	 * 查询话术模板基础信息
	 * @param processId
	 * @return
	 */
	@RequestMapping(value="queryBotsentenceProcessInfo")
	public ServerResult<BotSentenceProcess> queryBotsentenceProcessInfo(@JsonParam String processId){
		BotSentenceProcess process = botSentenceProcessService.queryBotsentenceProcessInfo(processId);
		return ServerResult.createBySuccess(process);
	}
	
	/**
	 * 查询某一个文案对应的关键词
	 * @param branchId
	 * @return
	 */
	@RequestMapping(value="queryIntentByBranchId")
	public ServerResult<BotSentenceIntent> queryIntentByBranchId(@JsonParam String branchId){
		BotSentenceIntent intent = botSentenceProcessService.queryKeywordsListByBranchId(branchId);
		return ServerResult.createBySuccess(intent);
	}
}
