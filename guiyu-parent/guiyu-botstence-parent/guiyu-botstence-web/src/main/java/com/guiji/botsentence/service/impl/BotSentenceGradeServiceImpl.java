package com.guiji.botsentence.service.impl;

import com.guiji.botsentence.dao.BotSentenceBranchMapper;
import com.guiji.botsentence.dao.BotSentenceGradeDetailMapper;
import com.guiji.botsentence.dao.BotSentenceGradeMapper;
import com.guiji.botsentence.dao.BotSentenceGradeRuleMapper;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.dao.ext.BotSentenceGradeRuleExtMapper;
import com.guiji.botsentence.service.IBotSentenceGradeService;
import com.guiji.botsentence.util.enums.DomainNameEnum;
import com.guiji.botsentence.util.enums.GradeRuleTypeEnum;
import com.guiji.botsentence.vo.GradeEvaluateVO;
import com.guiji.common.exception.CommonException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.guiji.botsentence.util.enums.GradeRuleTypeEnum.EFFECTIVE_DIALOGUE_TIMES;
import static com.guiji.botsentence.util.enums.GradeRuleTypeEnum.USER_NO_SPEAK_TIMES;

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
					evaluate = evaluate + " and ('一般回答计数'" + detail.getValue2() + detail.getValue3() + ")"; 
				}else {
					evaluate = "'一般回答计数'" + detail.getValue2() + detail.getValue3(); 
				}
			}else if("06".equals(detail.getType())) {//触发某个业务问答
				if(StringUtils.isNotBlank(evaluate)) {
					evaluate = evaluate + " and ('一般问题' in 经过域 and " + "'" + detail.getValue2() + ".responses'" + " in 一般问题['进入分支'])"; 
				}else {
					evaluate = "'一般问题' in 经过域 and " + "'" + detail.getValue2() + ".responses'" + " in 一般问题['进入分支']";
				}
			}else if(USER_NO_SPEAK_TIMES.getKey().equals(detail.getType())) {
				String concatValues= USER_NO_SPEAK_TIMES.getEvaluate() +  detail.getValue2() + detail.getValue3();
				if(StringUtils.isNotBlank(evaluate)) {
					evaluate = evaluate + " and " + "(" + concatValues + ")";
				}else {
					evaluate = concatValues;
				}
			}else if(EFFECTIVE_DIALOGUE_TIMES.getKey().equals(detail.getType())) {
				String concatValues= EFFECTIVE_DIALOGUE_TIMES.getEvaluate() +  detail.getValue2() + detail.getValue3();
				if(StringUtils.isNotBlank(evaluate)) {
					evaluate = evaluate + " and " + "(" + concatValues + ")";
				}else {
					evaluate = concatValues;
				}
			}
		}
		
		return evaluate;
	
	}

	@Override
	public String generateGradeEvaluate(String processId, String intentName) {
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

	@Override
	public void saveGradeRuleList(List<BotSentenceGradeRule> gradeRules, String userId) {
		// TODO: 19-4-26 need complete
	}

    @Override
    public String concatRulesForShow(String processId, List<BotSentenceGradeRule> gradeRules) {
	    StringBuilder rulesBuilder = new StringBuilder();

	    gradeRules.forEach(rule -> {
			GradeRuleTypeEnum ruleType = GradeRuleTypeEnum.getTypeByKey(rule.getType());
			if(null == ruleType){
				throw new CommonException("未识别的意向标签类型");
			}
			StringBuilder ruleBuilder = new StringBuilder();
	    	switch (ruleType){
				case TRIGGER_MAIN_PROCESS:
					ruleBuilder
							.append("触发'")
							.append(rule.getValue2())
							.append("'");
					break;
				case REJECTED_TIMES:
					ruleBuilder
							.append(rule.getValue1())
							.append(rule.getValue2())
							.append(rule.getValue3())
							.append("次");
					break;
				case EFFECTIVE_MAIN_PROCESS_DIALOGUE_TIMES:
					ruleBuilder
							.append(rule.getValue1())
							.append(rule.getValue2())
							.append(rule.getValue3())
							.append("轮");
					break;
				case CALL_DURATION:
					ruleBuilder
							.append(rule.getValue1())
							.append(rule.getValue2())
							.append(rule.getValue3())
							.append("秒");
					break;
				case TRIGGER_BUSINESS_QA_TIMES:
					ruleBuilder
							.append(rule.getValue1())
							.append(rule.getValue2())
							.append(rule.getValue3())
							.append("个");
					break;
				case TRIGGER_ONE_BUSINESS_QA:
					BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
					branchExample.createCriteria()
							.andProcessIdEqualTo(processId)
							.andDomainEqualTo(DomainNameEnum.NORMAL_QUESTION.getKey())
							.andBranchNameEqualTo(rule.getValue2());
					List<BotSentenceBranch> branches = botSentenceBranchMapper.selectByExample(branchExample);
					if(CollectionUtils.isEmpty(branches)){
						throw new CommonException("对应的一般问题不存在");
					}
					ruleBuilder
							.append("触发一般问题'")
							.append(branches.get(0).getUserAsk())
							.append("'");
					break;
				case USER_NO_SPEAK_TIMES:
					ruleBuilder
							.append(rule.getValue1())
							.append(rule.getValue2())
							.append(rule.getValue3())
							.append("次");
					break;
				case EFFECTIVE_DIALOGUE_TIMES:
					ruleBuilder
							.append(rule.getValue1())
							.append(rule.getValue2())
							.append(rule.getValue3())
							.append("轮");
					break;
			}

			if(0 == rulesBuilder.length()){
				rulesBuilder.append(ruleBuilder.toString());
			}else {
				rulesBuilder
						.append(" 且 ")
						.append(ruleBuilder.toString());
			}
        });
        return rulesBuilder.toString();
    }

    public String generateGradeShowEvaluate(String processId, String intentName) {
		List<String> ruleNoList = botSentenceGradeRuleExtMapper.queryDistinctRuleNoByProcessIdAndIntentName(processId, intentName);
		if(CollectionUtils.isEmpty(ruleNoList)){
			return "";
		}

		StringBuilder showEvaluate = new StringBuilder();
		ruleNoList.forEach(ruleNo -> {
			BotSentenceGradeRuleExample gradeRuleExample = new BotSentenceGradeRuleExample();
			gradeRuleExample.createCriteria()
					.andProcessIdEqualTo(processId)
					.andRuleNoEqualTo(ruleNo)
					.andIntentNameEqualTo(intentName);
			gradeRuleExample.setOrderByClause("show_seq");
			List<BotSentenceGradeRule> gradeRules = botSentenceGradeRuleMapper.selectByExample(gradeRuleExample);
			String rulesForShow = concatRulesForShow(processId, gradeRules);

			if(StringUtils.isBlank(rulesForShow)){
				return;
			}

			if(gradeRules.size() > 1){
				rulesForShow = "(" + rulesForShow + ")";
			}

			if(0 == showEvaluate.length()){
				showEvaluate
						.append("(")
						.append(rulesForShow)
						.append(")");
			}else {
				showEvaluate
						.append(" 或 ")
						.append("(")
						.append(rulesForShow)
						.append(")");
			}
		});

		if(0 == showEvaluate.length()){
			return "";
		}else {
			return showEvaluate.toString();
		}
	}
}
