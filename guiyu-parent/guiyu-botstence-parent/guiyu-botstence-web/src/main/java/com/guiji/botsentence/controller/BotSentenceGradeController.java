package com.guiji.botsentence.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mapstruct.BeforeMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.botsentence.dao.BotSentenceBranchMapper;
import com.guiji.botsentence.dao.BotSentenceGradeMapper;
import com.guiji.botsentence.dao.BotSentenceGradeRuleMapper;
import com.guiji.botsentence.dao.entity.BotSentenceBranch;
import com.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import com.guiji.botsentence.dao.entity.BotSentenceGrade;
import com.guiji.botsentence.dao.entity.BotSentenceGradeDetail;
import com.guiji.botsentence.dao.entity.BotSentenceGradeExample;
import com.guiji.botsentence.dao.entity.BotSentenceGradeRule;
import com.guiji.botsentence.dao.entity.BotSentenceGradeRuleExample;
import com.guiji.botsentence.dao.ext.BotSentenceGradeRuleExtMapper;
import com.guiji.botsentence.service.impl.BotSentenceGradeServiceImpl;
import com.guiji.botsentence.service.impl.BotSentenceProcessServiceImpl;
import com.guiji.botsentence.service.impl.BotsentenceVariableServiceImpl;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.vo.BotSentenceGradeVO;
import com.guiji.component.client.config.JsonParam;
import com.guiji.common.exception.CommonException;
import com.guiji.component.result.ServerResult;

@RestController
@RequestMapping(value="botSentenceGrade")
public class BotSentenceGradeController {

	@Autowired
	private BotSentenceGradeServiceImpl botSentenceGradeService;
	
	@Autowired
	private BotSentenceGradeRuleMapper botSentenceGradeRuleMapper;
	
	@Autowired
	private BotSentenceGradeMapper botSentenceGradeMapper;
	
	@Autowired
	private BotSentenceGradeRuleExtMapper botSentenceGradeRuleExtMapper;
	
	@Autowired
	private BotSentenceBranchMapper botSentenceBranchMapper;
	
	@Autowired
	private BotsentenceVariableServiceImpl botsentenceVariableService;
	
	@Autowired
	private BotSentenceProcessServiceImpl botSentenceProcessService;
	
	@RequestMapping(value="queryBotSentenceGradeDetailList")
	public List<BotSentenceGradeDetail> queryBotSentenceGradeDetailList(@JsonParam String processId) {
		List<BotSentenceGradeDetail> list = botSentenceGradeService.queryBotSentenceGradeDetailList(processId);
		
		String str = botSentenceGradeService.generateGradeEvaluate(processId, "AA");
		System.out.println(str);
		return null;
	}
	
	@RequestMapping(value="queryBotSentenceGradeRuleListByRuleNo")
	public ServerResult<List<BotSentenceGradeRule>> queryBotSentenceGradeRuleListByRuleNo(@JsonParam String processId, @JsonParam String ruleNo) {
		String str = botsentenceVariableService.generateSelectJson("20190111PRO00000463397", null);
		System.out.println(str);
		
		
		
		BotSentenceGradeRuleExample example = new BotSentenceGradeRuleExample();
		example.createCriteria().andProcessIdEqualTo(processId).andRuleNoEqualTo(ruleNo);
		example.setOrderByClause(" show_seq");
		List<BotSentenceGradeRule> list = botSentenceGradeRuleMapper.selectByExample(example);
		//String str = botSentenceGradeService.generateGradeEvaluate(processId, "AA");
		//System.out.println(str);
		return ServerResult.createBySuccess(list);
	}
	
	
	@RequestMapping(value="saveBotSentenceGradeRuleList")
	@Transactional
	public ServerResult saveBotSentenceGradeRuleList(@JsonParam List<JSONObject> list, @RequestHeader String userId) {
		if(null != list && list.size() > 0) {
			boolean isNew = false;
			String processId = list.get(0).getString("processId");
			String ruleNo = list.get(0).getString("ruleNo");
			String oldRuleNo = list.get(0).getString("ruleNo");
			String intentName = list.get(0).getString("intentName");
			
			if(StringUtils.isBlank(intentName)) {
				throw new CommonException("请设置意向等级标签[A-Z]!");
			}
			
			if(StringUtils.isBlank(ruleNo)) {
				isNew = true;
			}
			String maxRuleNo = botSentenceGradeRuleExtMapper.queryMaxRuleNo(processId);
			if(StringUtils.isNotBlank(maxRuleNo)) {
				ruleNo = (new Integer(maxRuleNo) + 1)+"";
			}else {
				ruleNo = "1";
			}
			
			String remark = list.get(0).getString("remark");
			//判断优先级是否有该意向，如果没有则新增
			BotSentenceGrade grade = botSentenceGradeService.getBotSentenceGrade(processId);
			if(null == grade) {
				grade = new BotSentenceGrade();
				grade.setCrtTime(new Date(System.currentTimeMillis()));
				grade.setCrtUser(userId);
				grade.setProcessId(processId);
				grade.setStatOrder(intentName);
				botSentenceGradeMapper.insert(grade);
			}else {
				/*String statOrder = grade.getStatOrder();
				if(StringUtils.isNotBlank(statOrder) && !BotSentenceUtil.StringToList(statOrder).contains(intentName)) {
					statOrder = statOrder + "," + intentName;
					grade.setStatOrder(statOrder);
					botSentenceGradeMapper.updateByPrimaryKey(grade); 
				}else {
					if(StringUtils.isBlank(statOrder)) {
						statOrder = intentName;
						grade.setStatOrder(statOrder);
						botSentenceGradeMapper.updateByPrimaryKey(grade); 
					}
				}*/
			}
			
			
			
			if(!isNew) {
				//获取原先的意向名
				BotSentenceGradeRuleExample example = new BotSentenceGradeRuleExample();
				example.createCriteria().andProcessIdEqualTo(processId).andRuleNoEqualTo(oldRuleNo);
				botSentenceGradeRuleMapper.deleteByExample(example);
			}
			
			int seq = 1;
			for(JSONObject json : list) {
				BotSentenceGradeRule rule = JSON.toJavaObject(json, BotSentenceGradeRule.class);
				rule.setShowSeq(seq);
				rule.setRuleNo(ruleNo);
				rule.setCrtTime(new Date(System.currentTimeMillis()));
				rule.setCrtUser(userId);
				botSentenceGradeRuleMapper.insert(rule);
				seq++;
			}
			updateBotSentenceGradeOrder(processId, userId);
			
			//更新所有的规则的备注字段
			botSentenceGradeRuleExtMapper.updateIntentNameByRemark(processId, intentName, remark);
			
			//更新话术流程状态
			botSentenceProcessService.updateProcessState(processId, userId);
		}
		
		return ServerResult.createBySuccess();
	}
	
	
	@RequestMapping(value="queryBotSentenceGradeRuleList")
	public ServerResult<List<BotSentenceGradeVO>> queryBotSentenceGradeRuleList(@JsonParam String processId) {
		
		List<BotSentenceGradeVO> results = new ArrayList<>();
		BotSentenceGrade grade = botSentenceGradeService.getBotSentenceGrade(processId);
		if(null != grade) {
			String statOrder = grade.getStatOrder();
			if(StringUtils.isNotBlank(statOrder)) {
				List<String> intentNames = BotSentenceUtil.StringToList(statOrder);
				for(String name : intentNames) {
					List<String> ruleNoList = botSentenceGradeRuleExtMapper.queryDistinctProcessId(processId);
					
					for(String ruleNo : ruleNoList) {
						BotSentenceGradeRuleExample example1 = new BotSentenceGradeRuleExample();
						example1.createCriteria().andProcessIdEqualTo(processId).andRuleNoEqualTo(ruleNo).andIntentNameEqualTo(name);
						example1.setOrderByClause(" show_seq");
						List<BotSentenceGradeRule> list = botSentenceGradeRuleMapper.selectByExample(example1);
						if(null != list && list.size() > 0) {
							String evaluate = concatEvaluate(processId, list);
							BotSentenceGradeVO vo = new BotSentenceGradeVO();
							vo.setCrtTime(list.get(0).getCrtTime());
							vo.setIntentName(name);
							vo.setRuleNo(ruleNo);
							vo.setRemark(list.get(0).getRemark());
							vo.setEvaluate(evaluate);
							results.add(vo);
						}
					}
				}
			}
		}
		
		
		
		return ServerResult.createBySuccess(results);
	}
	
	private String concatEvaluate(String processId, List<BotSentenceGradeRule> list) {
		String result = "";
		for(int i = 0 ; i < list.size() ; i++) {
			String temp;
			BotSentenceGradeRule rule = list.get(i);
			if("01".equals(rule.getType())) {
				temp = " 触发" + "'" + rule.getValue2() + "'";
			}else if("02".equals(rule.getType())) {
				temp = rule.getValue1() + "" + rule.getValue2() + "" + rule.getValue3() + "次";
			}else if("03".equals(rule.getType())) {
				temp = rule.getValue1() + "" + rule.getValue2() + "" + rule.getValue3() + "轮";
			}else if("04".equals(rule.getType())) {
				temp = rule.getValue1() + "" + rule.getValue2() + "" + rule.getValue3() + "秒";
			}else if("05".equals(rule.getType())) {
				temp = rule.getValue1() + "" + rule.getValue2() + "" + rule.getValue3() + "个";
			}else if("06".equals(rule.getType())) {
				BotSentenceBranchExample mainExample = new BotSentenceBranchExample();
				mainExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("一般问题").andBranchNameEqualTo(rule.getValue2());
				List<BotSentenceBranch> mainBranchList = botSentenceBranchMapper.selectByExample(mainExample);
				if(null != mainBranchList && mainBranchList.size() > 0) {
					temp = " 触发一般问题" + "'" + mainBranchList.get(0).getUserAsk() + "'";
				}else {
					temp = " 触发一般问题" + "'" + rule.getValue2() + "'";
				}
			}else {
				temp = rule.getValue1() + "" + rule.getValue2() + "" + rule.getValue3();
			}
			if(i == 0) {
				result = temp;
			}else {
				result = result + " 且 " + temp;
			}
		}
		return result;
	}
	
	@RequestMapping(value="deleteBotSentenceGrade")
	@Transactional
	public ServerResult deleteBotSentenceGrade(@JsonParam String processId, @JsonParam String intentName, @JsonParam String ruleNo, @RequestHeader String userId) {
		BotSentenceGradeRuleExample example = new BotSentenceGradeRuleExample();
		example.createCriteria().andProcessIdEqualTo(processId).andRuleNoEqualTo(ruleNo);
		botSentenceGradeRuleMapper.deleteByExample(example);
		
		//判断是否还存在当前意向的规则，如不存在，则删除相应的优先级
		BotSentenceGradeRuleExample exist = new BotSentenceGradeRuleExample();
		exist.createCriteria().andProcessIdEqualTo(processId).andIntentNameEqualTo(intentName);
		int num = botSentenceGradeRuleMapper.countByExample(exist);
		if(num < 1) {
			BotSentenceGrade grade = botSentenceGradeService.getBotSentenceGrade(processId);
			String statOrder = grade.getStatOrder();
			if(StringUtils.isNotBlank(statOrder)) {
				List<String> list = BotSentenceUtil.StringToList(statOrder);
				list.remove(intentName);
				grade.setStatOrder(BotSentenceUtil.listToString(list));
				botSentenceGradeMapper.updateByPrimaryKey(grade);
			}
		}
		updateBotSentenceGradeOrder(processId, userId);
		return ServerResult.createBySuccess();
	}
	
	@RequestMapping(value="saveBotSentenceGradeOrder")
	@Transactional
	public ServerResult saveBotSentenceGradeOrder(@JsonParam String processId, @JsonParam String orderStr, @RequestHeader String userId) {
		//判断是否还存在当前意向的规则，如不存在，则删除相应的优先级
		if(StringUtils.isBlank(orderStr)) {
			throw new CommonException("意向排序列表为空!");
		}
		BotSentenceGrade grade = botSentenceGradeService.getBotSentenceGrade(processId);
		grade.setStatOrder(orderStr);
		botSentenceGradeMapper.updateByPrimaryKey(grade);
		
		//更新话术流程状态
		botSentenceProcessService.updateProcessState(processId, userId);
		
		return ServerResult.createBySuccess();
	}
	
	
	@RequestMapping(value="queryBotSentenceGradeOrder")
	public ServerResult<List<String>> queryBotSentenceGradeOrder(@JsonParam String processId) {
		//判断是否还存在当前意向的规则，如不存在，则删除相应的优先级
		BotSentenceGrade grade = botSentenceGradeService.getBotSentenceGrade(processId);
		if(null != grade && StringUtils.isNotBlank(grade.getStatOrder())) {
			return ServerResult.createBySuccess(BotSentenceUtil.StringToList(grade.getStatOrder()));
		}
		return ServerResult.createBySuccess();
	}
	
	/**
	 * 更新意向优先级
	 * @param processId
	 */
	private void updateBotSentenceGradeOrder(String processId, String userId) {
		BotSentenceGrade grade = botSentenceGradeService.getBotSentenceGrade(processId);
		List<String> intentNames = botSentenceGradeRuleExtMapper.queryDistinctIntentNameProcessId(processId);
		
		if(null != intentNames && intentNames.size() > 0) {
			String orderStr = grade.getStatOrder();
			
			if(StringUtils.isNotBlank(orderStr)) {
				List<String> orderList = BotSentenceUtil.StringToList(orderStr);
				
				//先把意图加入到排序字符串
				for(String name : intentNames) {
					if(!orderList.contains(name)) {
						orderList.add(name);
					}
				}
				
				
				for(int i = orderList.size()-1 ; i >=0 ; i--) {
					if(!intentNames.contains(orderList.get(i))) {
						orderList.remove(i);
					}
				}
				grade.setStatOrder(BotSentenceUtil.listToString(orderList));
			}else {
				grade.setStatOrder(BotSentenceUtil.listToString(intentNames));
			}
		}else {
			grade.setStatOrder("");
		}
		
		botSentenceGradeMapper.updateByPrimaryKey(grade);
		
		//更新话术流程状态
		botSentenceProcessService.updateProcessState(processId, userId);
	}
	
}
