package com.guiji.botsentence.controller;

import com.google.common.collect.Lists;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.controller.server.vo.BotSentenceTemplateTradeVO;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.BotSentenceShareAuthMapper;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.service.IBotSentenceProcessService;
import com.guiji.botsentence.util.IndustryUtil;
import com.guiji.botsentence.vo.*;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.client.util.BeanUtil;
import com.guiji.component.client.util.DateUtil;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.model.Page;
import com.guiji.component.result.ServerResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	@Resource
	private IBotSentenceProcessService botSentenceProcessService;
	
	@Resource
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Resource
	private BotSentenceShareAuthMapper botSentenceShareAuthMapper;
	
	@Resource
	private IndustryUtil industryUtil;
	
	
	/**
	 * 根据条件查询话术流程列表
	 * @param pageSize
	 * @param pageNo
	 * @param templateName
	 * @param accountNo
	 */
	@RequestMapping(value="queryBotSentenceProcessListByPage")
	public ServerResult<Page<BotSentenceProcessVO>> queryBotSentenceProcessListByPage(@JsonParam int pageSize, @JsonParam int pageNo, @JsonParam String templateName, @JsonParam String accountNo, 
			@RequestHeader String userId, @RequestHeader String orgCode, @RequestHeader Integer authLevel, @JsonParam String state) {
		Page<BotSentenceProcessVO> page = new Page<BotSentenceProcessVO>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		List<BotSentenceProcess> list = botSentenceProcessService.queryBotSentenceProcessList(pageSize, pageNo, templateName, accountNo, userId, state, authLevel, orgCode);
		
		int totalNum = botSentenceProcessService.countBotSentenceProcess(templateName, accountNo, userId, state, authLevel, orgCode);
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
				}else if(Constant.ERROR.equals(temp.getState())) {
					vo.setStateName("部署失败");
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
				
				//设置行业显示三级
				String industryId = vo.getIndustryId();
				if(StringUtils.isNotBlank(industryId) && industryId.length() == 6) {
					String level_1 = industryId.substring(0, 2);
					String level_2 = industryId.substring(0, 4);
					String level_3 = industryId;
					Map<String, String> map = IndustryUtil.map;
					vo.setIndustry(map.get(level_1) + "/" + map.get(level_2) + "/" + map.get(level_3));
					//vo.setIndustryId(level_1 + "," + level_2 + "," + level_3);
				}
				
				//设置是否分享
				BotSentenceShareAuthExample example = new BotSentenceShareAuthExample();
				example.createCriteria().andProcessIdEqualTo(temp.getProcessId());
				List<BotSentenceShareAuth> shareList =  botSentenceShareAuthMapper.selectByExample(example);
				if(null != shareList && shareList.size() > 0) {
					if(null != shareList.get(0).getShared()) {
						vo.setShared(shareList.get(0).getShared());
					}else {
						vo.setShared(false);
					}
				}else {
					vo.setShared(false);
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
	 * @param accountNo
	 */
	@RequestMapping(value="createBotSentenceProcess")
	@Jurisdiction("botsentence_maker_addTemplate")
	public ServerResult<String> createBotSentenceProcess(@JsonParam BotSentenceProcessVO paramVO, 
			@RequestHeader String userId, @RequestHeader String orgCode, @RequestHeader String authLevel) {
		if(null != paramVO && StringUtils.isNotBlank(paramVO.getProcessId()) && 
				StringUtils.isNotBlank(paramVO.getTemplateName())) {
			paramVO.setFlag("00");
			String new_processId = botSentenceProcessService.createBotSentenceTemplate(paramVO, userId);
			return ServerResult.createBySuccess(new_processId);
		}
		return ServerResult.createByErrorMessage("创建模板失败!");
	}
	
	
	/**
	 * 修改话术
	 * @param accountNo
	 */
	@RequestMapping(value="updateBotSentenceProcess")
	public ServerResult<String> updateBotSentenceProcess(@JsonParam String accountNo, @JsonParam String processId, @RequestHeader String userId) {
		if(StringUtils.isNotBlank(accountNo) && StringUtils.isNotBlank(processId)) {
			BotSentenceProcessVO paramVO = new BotSentenceProcessVO();
			paramVO.setAccountNo(accountNo);
			paramVO.setProcessId(processId);
			paramVO.setFlag("01");
			String new_processId = botSentenceProcessService.createBotSentenceTemplate(paramVO, userId);
			return ServerResult.createBySuccess(new_processId);
		}
		return ServerResult.createByErrorMessage("修改模板失败!");
	}
	
	/**
	 * 话术流程提交审核
	 * @param processId
	 */
	@RequestMapping(value="submit")
	public ServerResult submit(@JsonParam String processId, @RequestHeader String userId) {
		botSentenceProcessService.submit(processId, userId);
		return ServerResult.createBySuccess();
	}
	
	
	/**
	 * 修改话术名称
	 * @param processId
	 */
	@RequestMapping(value="updateTemplateName")
	@Jurisdiction("botsentence_maker_setName")
	public ServerResult updateTemplateName(@JsonParam String processId, @JsonParam String templateName, @JsonParam String industry, @RequestHeader String userId) {
		botSentenceProcessService.updateBotSentenceTemplate(processId, templateName, industry, userId);
		return ServerResult.createBySuccess();
	}
	
	/**
	 * 删除话术
	 * @param processId
	 */
	@RequestMapping(value="deleteTemplate")
	@Jurisdiction("botsentence_maker_delete")
	public ServerResult deleteTemplate(@JsonParam String processId, @RequestHeader String userId) {
		botSentenceProcessService.delete(processId, userId);
		return ServerResult.createBySuccess();
	}
	
	
	/**
	 * 查询通用对话列表信息
	 */
	@RequestMapping(value="queryCommonDialog")
	public ServerResult<List<CommonDialogVO>> queryCommonDialog(@JsonParam String processId) {
		//weChatAppletService.generateQrCode("test1_14721_en");
		
		List<CommonDialogVO> list = botSentenceProcessService.queryCommonDialog(processId);
		return ServerResult.createBySuccess(list);
	}
	
	/**
	 * 更新通用对话话术信息
	 */
	@RequestMapping(value="updateCommonDialog")
	public ServerResult updateCommonDialog(@JsonParam CommonDialogVO commonDialog, @RequestHeader String userId) {
		botSentenceProcessService.updateCommonDialog(commonDialog, userId);
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
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="saveRefuseBranch")
	public ServerResult<FlowInfoVO> saveRefuseBranch(@JsonParam String processId, @JsonParam String domainName, @JsonParam List<String> voliceIdList, @RequestHeader String userId) {
		botSentenceProcessService.saveRefuseBranch(processId, domainName, voliceIdList, userId);
		FlowInfoVO flow = botSentenceProcessService.initFlowInfo(processId);
		return ServerResult.createBySuccess(flow);
	}
	
	
	/**
	 * 删除domain挽回话术
	 * @param domain
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
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="deleteDomain")
	public ServerResult<FlowInfoVO> deleteDomain(@JsonParam String processId, @JsonParam String domainId, @RequestHeader String userId) {
		botSentenceProcessService.deleteDomain(processId, domainId, userId);
		FlowInfoVO flow = botSentenceProcessService.initFlowInfo(processId);
		return ServerResult.createBySuccess(flow);
	}
	
	
	/**
	 * 删除branch
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="deleteBranch")
	public ServerResult<FlowInfoVO> deleteBranch(@JsonParam String processId, @JsonParam String branchId , @RequestHeader String userId) {
		botSentenceProcessService.deleteBranch(processId, branchId, userId);
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
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="saveNode")
	public ServerResult<FlowInfoVO> saveNode(@JsonParam FlowNode node, @RequestHeader String userId){
		botSentenceProcessService.saveNode(node, userId);
		FlowInfoVO flow = botSentenceProcessService.initFlowInfo(node.getProcessId());
		return ServerResult.createBySuccess(flow);
	}
	
	/**
	 * 保存连线信息
	 * @param processId
	 * @param domainId
	 * @return
	 */
	@RequestMapping(value="saveEdge")
	public ServerResult<FlowInfoVO> saveEdge(@JsonParam FlowEdge edge, @RequestHeader String userId){
		botSentenceProcessService.saveEdge(edge.getProcessId(), edge, userId);
		FlowInfoVO flow = botSentenceProcessService.initFlowInfo(edge.getProcessId());
		return ServerResult.createBySuccess(flow);
	}
	
	
	/**
	 * 保存流程信息
	 * @param flow
	 * @return
	 */
	@RequestMapping(value="saveFlow")
	public ServerResult<FlowInfoVO> saveFlow(@JsonParam FlowInfoVO flow, @RequestHeader String userId){
		botSentenceProcessService.saveFlow(flow, userId);
		flow = botSentenceProcessService.initFlowInfo(flow.getProcessId());
		return ServerResult.createBySuccess(flow);
	}
	
	
	
	/**
	 * 查询TTS合成录音的状态
	 * @param flow
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
	public ServerResult saveSoundType(@JsonParam String processId, @JsonParam String soundType, @RequestHeader String userId){
		botSentenceProcessService.saveSoundType(processId, soundType, userId);
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
	
	/**
	 * 生成TTS合成录音
	 * @param flow
	 * @return
	 */
	@RequestMapping(value="generateTTS")
	@Async
	public ServerResult<FlowInfoVO> generateTTS(@JsonParam GenerateTTSVO param, @JsonParam String processId, @RequestHeader String userId, @JsonParam String model){
		//@JsonParam GenerateTTSVO param, 
		botSentenceProcessService.generateTTS(param.getList(), processId, userId, model);
		return ServerResult.createBySuccess();
	}
	
	/**
	 * 根据流程号获取主流程domain
	 * @param processId
	 * @return
	 */
	@RequestMapping(value="getDomainList")
	public ServerResult<List<BotSentenceDomain>> getDomainList(@JsonParam String processId) {
		List<BotSentenceDomain> list = botSentenceProcessService.getDomainList(processId);
		return ServerResult.createBySuccess(list);
	}
	
	/**
	 * 根据流程号获取所有domain
	 * @param processId
	 * @return
	 */
	@RequestMapping(value="getAllDomainList")
	public ServerResult<List<BotSentenceDomain>> getAllDomainList(@JsonParam String processId, @RequestHeader String userId) {
		List<BotSentenceDomain> list = botSentenceProcessService.getAllDomainList(processId);
		return ServerResult.createBySuccess(list);
	}
	
	@RequestMapping(value="queryIndustryListByAccountNo")
	public ServerResult<List<BotSentenceTemplateTradeVO>> queryIndustryListByAccountNo(@RequestHeader String userId, @RequestHeader String orgCode) {
		List<BotSentenceTemplateTradeVO> list = botSentenceProcessService.queryTemplateTreeByOrgCode(orgCode, "template");
		
		/*if(null != list && list.size() > 0) {
			for(BotSentenceTemplateTradeVO trade : list) {
				if(null != trade.getChildren()) {
					for(BotSentenceTemplateTradeVO trade1 : trade.getChildren()) {
						if(null != trade1.getChildren()) {
							List<BotSentenceTemplateTradeVO> trade2List = trade1.getChildren();
							//for(BotSentenceTemplateTradeVO trade2 : trade2List) {
							for(int i = trade2List.size()-1 ; i >= 0 ; i--) {
								//查询第3级行业下属的模板
								BotSentenceTemplateTradeVO trade2 = trade2List.get(i);
								List<BotSentenceTemplate> templateList = botSentenceTemplateService.queryTemplateByIndustry(trade2.getValue());
								if(null != templateList && templateList.size() > 0) {
									List<BotSentenceTemplateTradeVO> children = new ArrayList<>();
									for(BotSentenceTemplate template : templateList) {
										BotSentenceTemplateTradeVO vo = new BotSentenceTemplateTradeVO();
										vo.setLabel(template.getTemplateName());
										vo.setValue(template.getProcessId());
										children.add(vo);
									}
									trade2.setChildren(children);
								}else {
									//过滤第3级行业
									trade2List.remove(i);
								}
							}
						}
					}
				}
			}
		}*/
		
		
		return ServerResult.createBySuccess(list);
	}
	
	
	
	
	@RequestMapping(value="queryIndustryListByOrgCode")
	public ServerResult<List<BotSentenceTemplateTradeVO>> queryIndustryListByOrgCode(@JsonParam String orgCode) {
		List<BotSentenceTemplateTradeVO> list = botSentenceProcessService.queryTemplateTreeByOrgCode(orgCode,"template");
		/*
		if(null != list && list.size() > 0) {
			for(BotSentenceTemplateTradeVO trade : list) {
				if(null != trade.getChildren()) {
					for(BotSentenceTemplateTradeVO trade1 : trade.getChildren()) {
						if(null != trade1.getChildren()) {
							List<BotSentenceTemplateTradeVO> trade2List = trade1.getChildren();
							//for(BotSentenceTemplateTradeVO trade2 : trade2List) {
							for(int i = trade2List.size()-1 ; i >= 0 ; i--) {
								//查询第3级行业下属的模板
								BotSentenceTemplateTradeVO trade2 = trade2List.get(i);
								List<BotSentenceTemplate> templateList = botSentenceTemplateService.queryTemplateByIndustry(trade2.getValue());
								if(null != templateList && templateList.size() > 0) {
									List<BotSentenceTemplateTradeVO> children = new ArrayList<>();
									for(BotSentenceTemplate template : templateList) {
										BotSentenceTemplateTradeVO vo = new BotSentenceTemplateTradeVO();
										vo.setLabel(template.getTemplateName());
										vo.setValue(template.getTemplateId());
										children.add(vo);
									}
									trade2.setChildren(children);
								}else {
									//过滤第3级行业
									trade2List.remove(i);
								}
							}
						}
					}
				}
			}
		}*/
		
		
		return ServerResult.createBySuccess(list);
	}
	
	
	
	@RequestMapping(value="queryAllIndustryList")
	public ServerResult<List<BotSentenceTemplateTradeVO>> queryAllIndustryList() {
		List<BotSentenceTemplateTradeVO> list = botSentenceProcessService.queryAllIndustryList();
		return ServerResult.createBySuccess(list);
	}
	
	@RequestMapping(value="getTemplateBySelf")
	public ServerResult<List<BotSentenceProcess>> getTemplateBySelf(@RequestHeader("userId") String accountNo){
		List<BotSentenceProcess> result=botSentenceProcessService.getTemplateBySelf(accountNo);
		return ServerResult.createBySuccess(result);
	}
	
	@RequestMapping(value="getTemplateById")
	public ServerResult<List<BotSentenceProcess>> getTemplateById(String templateId){
		BotSentenceProcess process = botSentenceProcessService.getBotsentenceProcessByTemplateId(templateId);
		List<BotSentenceProcess> result = new ArrayList<>();
		result.add(process);
		//List<BotSentenceProcess> result=botSentenceProcessService.getTemplateById(templateId);
		return ServerResult.createBySuccess(result);
	}

	@RequestMapping(value="getTemplateByIds")
	public ServerResult<List<BotSentenceProcess>> getTemplateByIds(@RequestParam("templateIds") List<String> templateIds){
		if(CollectionUtils.isEmpty(templateIds)){
			return ServerResult.createBySuccess(Lists.newArrayList());
		}

		BotSentenceProcessExample processExample = new BotSentenceProcessExample();
		processExample.createCriteria().andTemplateIdIn(templateIds);

		return ServerResult.createBySuccess(botSentenceProcessMapper.selectByExample(processExample));
	}

	@RequestMapping(value="saveIndustry")
	@Jurisdiction("botsentence_mytemplate_save")
	public ServerResult saveTrade(@JsonParam String industryName, @JsonParam String industryId, @RequestHeader("userId") String userId){
		botSentenceProcessService.saveTrade(industryName, industryId, userId);
		industryUtil.initIndustry();
		return ServerResult.createBySuccess();
	}
	
}
