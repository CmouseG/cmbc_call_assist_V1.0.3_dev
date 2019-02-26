package com.guiji.botsentence.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.botsentence.dao.BotSentenceBranchMapper;
import com.guiji.botsentence.dao.BotSentenceGradeDetailMapper;
import com.guiji.botsentence.dao.BotSentenceGradeMapper;
import com.guiji.botsentence.dao.BotSentenceGradeRuleMapper;
import com.guiji.botsentence.dao.entity.BotSentenceBranch;
import com.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import com.guiji.botsentence.dao.entity.BotSentenceGrade;
import com.guiji.botsentence.dao.entity.BotSentenceGradeDetail;
import com.guiji.botsentence.dao.entity.BotSentenceGradeDetailExample;
import com.guiji.botsentence.dao.entity.BotSentenceGradeExample;
import com.guiji.botsentence.dao.entity.BotSentenceGradeRule;
import com.guiji.botsentence.dao.entity.BotSentenceGradeRuleExample;
import com.guiji.botsentence.dao.ext.BotSentenceGradeRuleExtMapper;
import com.guiji.botsentence.service.IBotSentenceGradeService;
import com.guiji.botsentence.vo.GradeEvaluateVO;
import com.guiji.common.exception.CommonException;

/**
 * 意向服务
 * @author 张朋
 *
 */
@Service
public class BotSentenceGradeServiceImpl implements IBotSentenceGradeService {

	private Logger logger = LoggerFactory.getLogger(BotSentenceGradeServiceImpl.class);
	
	@Autowired
	private BotSentenceGradeMapper botSentenceGradeMapper;
	
	@Autowired
	private BotSentenceGradeDetailMapper botSentenceGradeDetailMapper;
	
	@Autowired
	private BotSentenceGradeRuleMapper botSentenceGradeRuleMapper;
	
	@Autowired
	private BotSentenceGradeRuleExtMapper botSentenceGradeRuleExtMapper;
	
	@Autowired
	private BotSentenceProcessServiceImpl botSentenceProcessService;
	
	@Autowired
	private BotSentenceBranchMapper botSentenceBranchMapper;
	
	@Override
	public void saveBotSentenceGrade(BotSentenceGrade botSentenceGrade, String userId) {
		if(StringUtils.isBlank(botSentenceGrade.getProcessId())) {
			throw new CommonException("话术流程编号为空");
		}
		BotSentenceGrade exist = this.getBotSentenceGrade(botSentenceGrade.getProcessId());
		exist.setInitStat("D");
		exist.setStatOrder(botSentenceGrade.getStatOrder());
		exist.setCrtTime(new Date(System.currentTimeMillis()));
		exist.setCrtUser(userId);
		botSentenceGradeMapper.insert(botSentenceGrade);
		
		
		//如果当前状态为审批通过、已上线，则把状态修改为“制作中”
		botSentenceProcessService.updateProcessState(botSentenceGrade.getProcessId(), userId);
	}

	@Override
	public void saveBotSentenceGradeDetailList(List<BotSentenceGradeDetail> list, String userId) {
		if(null == list || list.size() < 1) {
			throw new CommonException("保存意向列表为空");
		}
		for(BotSentenceGradeDetail detail : list) {
			if(null == detail.getId()) {
				detail.setCrtTime(new Date(System.currentTimeMillis()));
				detail.setCrtUser(userId);
				botSentenceGradeDetailMapper.insert(detail);
			}else {
				detail.setLstUpdateTime(new Date(System.currentTimeMillis()));
				detail.setLstUpdateUser(userId);
				botSentenceGradeDetailMapper.updateByPrimaryKey(detail);
			}
		}
		
		//如果当前状态为审批通过、已上线，则把状态修改为“制作中”
		//botSentenceProcessService.updateProcessState(botSentenceGrade.getProcessId());
	}

	@Override
	public List<BotSentenceGradeDetail> queryBotSentenceGradeDetailList(String processId) {
		BotSentenceGradeDetailExample example = new BotSentenceGradeDetailExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		return botSentenceGradeDetailMapper.selectByExample(example);
	}

	@Override
	public BotSentenceGrade getBotSentenceGrade(String processId) {
		BotSentenceGradeExample example = new BotSentenceGradeExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceGrade> list = botSentenceGradeMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public String generateEvaluate(BotSentenceGradeRule detail) {

		String evaluate = "";
		if(StringUtils.isNotBlank(detail.getType())) {
			if("=".equals(detail.getValue2())) {
				detail.setValue2("==");
			}
			
			
			if("01".equals(detail.getType())) {
				if(StringUtils.isNotBlank(detail.getValue1()) && StringUtils.isNotBlank(detail.getValue2())) {
					if("在忙".equals(detail.getValue2()) || "投诉".equals(detail.getValue2())) {
						evaluate = detail.getValue2() + "关键词_命中数 > 0 ";
					}else {
						evaluate = "'" + detail.getValue2() + "'" + " in 经过域";
					}
				}
			}else if("02".equals(detail.getType())) {
				if(StringUtils.isNotBlank(evaluate)) {
					//evaluate = evaluate + " and " + "('拒绝' in 经过域 and 拒绝['进入次数']" +  detail.getValue2() + detail.getValue3() + ")";
					evaluate = evaluate + " and " + "(拒绝关键词_命中数" +  detail.getValue2() + detail.getValue3() + ")";
				}else {
					evaluate = "拒绝关键词_命中数" +  detail.getValue2() + detail.getValue3();
				}
			}else if("03".equals(detail.getType())) {//主流程有效回答计数
				if(StringUtils.isNotBlank(evaluate)) {
					evaluate = evaluate + " and " + "主流程有效回答计数" +  detail.getValue2() + detail.getValue3();
				}else {
					evaluate = "主流程有效回答计数" +  detail.getValue2() + detail.getValue3();
				}
			}else if("04".equals(detail.getType())) {//接通时长
				if(StringUtils.isNotBlank(evaluate)) {
					evaluate = evaluate + " and " + "接通时长" +  detail.getValue2() + detail.getValue3();
				}else {
					evaluate = "接通时长" + detail.getValue2() + detail.getValue3();
				}
			}else if("05".equals(detail.getType())) {//触发业务问答次数
				if(StringUtils.isNotBlank(evaluate)) {
					evaluate = evaluate + " and ('一般问题' in 经过域 and 一般问题['进入次数']" + detail.getValue2() + detail.getValue3() + ")"; 
				}else {
					evaluate = "'一般问题' in 经过域 and 一般问题['进入次数']" + detail.getValue2() + detail.getValue3(); 
				}
			}else if("06".equals(detail.getType())) {//触发业务问答次数
				if(StringUtils.isNotBlank(evaluate)) {
					evaluate = evaluate + " and ('一般问题' in 经过域 and " + "'" + detail.getValue2() + ".responses'" + " in 一般问题['进入分支'])"; 
				}else {
					evaluate = "'一般问题' in 经过域 and " + "'" + detail.getValue2() + ".responses'" + " in 一般问题['进入分支']";
				}
			}
		}
		
		return evaluate;
	
	}

	@Override
	public String generateGradeEvaluate(String processId, String intentName) {
		GradeEvaluateVO responseVO = new GradeEvaluateVO();
		List<String> ruleNoList = botSentenceGradeRuleExtMapper.queryDistinctRuleNoByProcessIdAndIntentName(processId, intentName);
		String result = "";
		String evaluateShow = "";
		if(null != ruleNoList && ruleNoList.size() > 0) {
			for(int m = 0 ; m < ruleNoList.size() ; m++) {
				String ruleNo = ruleNoList.get(m);
				BotSentenceGradeRuleExample example = new BotSentenceGradeRuleExample();;
				example.createCriteria().andProcessIdEqualTo(processId).andRuleNoEqualTo(ruleNo).andIntentNameEqualTo(intentName);
				example.setOrderByClause(" show_seq");
				List<BotSentenceGradeRule> list = botSentenceGradeRuleMapper.selectByExample(example);
				if(null != list && list.size() > 0) {
					String ruleEvaluate = "";
					if(list.size() == 1) {
						ruleEvaluate = generateEvaluate(list.get(0));
					}else {
						for(int i = 0 ; i < list.size() ; i++) {
							BotSentenceGradeRule detail = list.get(i);
							if(i == list.size()-1) {
								ruleEvaluate = ruleEvaluate + "(" + generateEvaluate(detail) + ")";
							}else {
								ruleEvaluate = ruleEvaluate + "(" + generateEvaluate(detail) + ")" + " and ";
							}
						}
					}
					if(m == 0) {
						result = "(" + ruleEvaluate + ")";
					}else {
						result = result + " or " + "(" + ruleEvaluate + ")";
					}
				}
			}
		}
		return result;
	}

	@Override
	public String getIntentNameByRuleNo(String processId, String ruleNo) {
		BotSentenceGradeRuleExample example = new BotSentenceGradeRuleExample();;
		example.createCriteria().andProcessIdEqualTo(processId).andRuleNoEqualTo(ruleNo);
		List<BotSentenceGradeRule> list = botSentenceGradeRuleMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0).getIntentName();
		}
		return null;
	}
	
	
	public String generateGradeShowEvaluate(String processId, String intentName) {
		List<String> ruleNoList = botSentenceGradeRuleExtMapper.queryDistinctRuleNoByProcessIdAndIntentName(processId, intentName);
		String result = "";
		if(null != ruleNoList && ruleNoList.size() > 0) {
			for(int m = 0 ; m < ruleNoList.size() ; m++) {
				String ruleNo = ruleNoList.get(m);
				BotSentenceGradeRuleExample example = new BotSentenceGradeRuleExample();;
				example.createCriteria().andProcessIdEqualTo(processId).andRuleNoEqualTo(ruleNo).andIntentNameEqualTo(intentName);
				example.setOrderByClause(" show_seq");
				List<BotSentenceGradeRule> list = botSentenceGradeRuleMapper.selectByExample(example);
				if(null != list && list.size() > 0) {
					String ruleEvaluate = "";
					if(list.size() == 1) {
						ruleEvaluate = concatEvaluate(processId, list);
					}else {
						//for(int i = 0 ; i < list.size() ; i++) {
							//if(i == list.size()-1) {
								ruleEvaluate = ruleEvaluate + "(" + concatEvaluate(processId, list) + ")";
							//}else {
								//ruleEvaluate = ruleEvaluate + "(" + concatEvaluate(processId, list) + ")" + " 且 ";
							//}
						//}
					}
					if(m == 0) {
						result = "(" + ruleEvaluate + ")";
					}else {
						result = result + " 或 " + "(" + ruleEvaluate + ")";
					}
				}
			}
		}
		return result;
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

}
