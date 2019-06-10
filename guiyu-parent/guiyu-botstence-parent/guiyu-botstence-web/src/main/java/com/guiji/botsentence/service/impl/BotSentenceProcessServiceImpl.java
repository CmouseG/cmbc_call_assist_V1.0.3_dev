package com.guiji.botsentence.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.constant.SpeechMqConstant;
import com.guiji.botsentence.controller.server.vo.BotSentenceTemplateTradeVO;
import com.guiji.botsentence.dao.*;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample.Criteria;
import com.guiji.botsentence.dao.entity.ext.IntentVO;
import com.guiji.botsentence.dao.entity.ext.VoliceInfoVO;
import com.guiji.botsentence.dao.ext.*;
import com.guiji.botsentence.message.SpeechAuditMessage;
import com.guiji.botsentence.service.BotSentenceKeyWordsService;
import com.guiji.botsentence.service.IBotSentenceProcessService;
import com.guiji.botsentence.service.IBotSentenceTemplateService;
import com.guiji.botsentence.service.IKeywordsVerifyService;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.util.enums.*;
import com.guiji.botsentence.vo.*;
import com.guiji.common.exception.CommonException;
import com.guiji.component.client.util.BeanUtil;
import com.guiji.component.client.util.Pinyin4jUtil;
import com.guiji.component.client.util.SystemUtil;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.guiyu.message.component.FanoutSender;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BotSentenceProcessServiceImpl implements IBotSentenceProcessService {

	private Logger logger = LoggerFactory.getLogger(BotSentenceProcessServiceImpl.class);

	@Resource
	private FanoutSender fanoutSender;

	@Resource
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Resource
	private BotSentenceDomainMapper botSentenceDomainMapper;
	
	@Resource
	private BotSentenceBranchMapper botSentenceBranchMapper;
	
	@Resource
	private BotSentenceIntentMapper botSentenceIntentMapper;
	
	@Resource
	private BotSentenceBranchExtMapper botSentenceBranchExtMapper;
	
	@Resource
	private BotSentenceDomainExtMapper botSentenceDomainExtMapper;
	
	@Resource
	private BotSentenceIntentExtMapper botSentenceIntentExtMapper;
	
	@Resource
	private com.guiji.botsentence.dao.VoliceInfoMapper voliceInfoMapper;
	
	@Resource
	private VoliceInfoExtMapper voliceInfoExtMapper;
	
	@Resource
	private BotSentenceTemplateMapper botSentenceTemplateMapper;
	
	@Resource
	private BotSentenceAdditionMapper botSentenceAdditionMapper;
	
	@Resource
	private BusinessAnswerTaskServiceImpl businessAnswerTaskService;
	
	@Resource
	private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;

	@Resource
	private BotSentenceTtsParamMapper botSentenceTtsParamMapper;
	
	@Resource
	private BotSentenceTtsServiceImpl botSentenceTtsService;
	
	@Resource
	private BotSentenceTtsBackupMapper botSentenceTtsBackupMapper;
	
	@Resource
	private VoliceServiceImpl voliceServiceImpl;

	@Resource
	private BotSentenceTradeMapper botSentenceTradeMapper;
	
	@Resource
	private BotSentenceKeyWordsService botSentenceKeyWordsService;

	@Resource
	private BotSentenceProcessExtMapper botSentenceProcessExtMapper;

	@Resource
	private BotSentenceGradeMapper botSentenceGradeMapper;
	
	@Resource
	private BotSentenceGradeRuleMapper botSentenceGradeRuleMapper;
	
	@Resource
	private BotsentenceVariableServiceImpl botsentenceVariableService;
	
	@Resource
	private BotSentenceOptionsMapper botSentenceOptionsMapper;
	
	@Resource
	private BotSentenceSurveyMapper botSentenceSurveyMapper;
	
	@Resource
	private BotSentenceShareAuthMapper botSentenceShareAuthMapper;
	
	@Resource
	private IBotSentenceTemplateService botSentenceTemplateService;
	
	@Resource
	private BotSentenceGradeRuleExtMapper botSentenceGradeRuleExtMapper;

	@Resource
	private BotSentenceKeywordAuditMapper botSentenceKeywordAuditMapper;
	
	@Resource
	private IAuth iAuth;
	
	@Resource
	private IOrg orgService;
	
	@Resource
	private BotSentenceSurveyIntentMapper botSentenceSurveyIntentMapper;

	@Resource
	private BotAvailableTemplateMapper botAvailableTemplateMapper;

	@Resource
	private IKeywordsVerifyService iKeywordsVerifyService;
	
	private static final String domain_prefix = "分支";
	private static final String branch_prefix = "special_";
	private static final String line_prefix = "未命名";
	
	@Value("${all_tts}")
	private boolean all_tts;

	@Override
	public List<BotSentenceProcess> queryBotSentenceProcessList(int pageSize, int pageNo, String templateName, String accountNo, String userId, String state, int authLevel, String orgCode) {
		BotSentenceProcessExample example = new BotSentenceProcessExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(templateName)) {
			criteria.andTemplateNameLike("%" + templateName + "%");
		}
		
		if(StringUtils.isNotBlank(state)) {
			criteria.andStateEqualTo(state);
		}
		
		criteria.andStateNotEqualTo("99");
		
		if(1 == authLevel) {//查询本人
			criteria.andCrtUserEqualTo(userId);
		}else if(2 == authLevel) {//查询本组织
			criteria.andOrgCodeEqualTo(orgCode);
		}else if(3 == authLevel) {//查询本组织及以下
			criteria.andOrgCodeLike(orgCode+"%");
		}
		
		//计算分页
		int limitStart = (pageNo-1)*pageSize;
		int limitEnd = pageSize;
		example.setLimitStart(limitStart);
		example.setLimitEnd(limitEnd);
		example.setOrderByClause(" crt_time desc");
		
		return botSentenceProcessMapper.selectByExample(example);
	}

	@Override
	public int countBotSentenceProcess(String templateName, String accountNo, String userId, String state, int authLevel, String orgCode) {
		BotSentenceProcessExample example = new BotSentenceProcessExample();
		Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(templateName)) {
			criteria.andTemplateNameLike("%" + templateName + "%");
		}
		
		if(StringUtils.isNotBlank(state)) {
			criteria.andStateEqualTo(state);
		}
		
		criteria.andStateNotEqualTo("99");
		
		if(1 == authLevel) {//查询本人
			criteria.andCrtUserEqualTo(userId);
		}else if(2 == authLevel) {//查询本组织
			criteria.andOrgCodeEqualTo(orgCode);
		}else if(3 == authLevel) {//查询本组织及以下
			criteria.andOrgCodeLike(orgCode+"%");
		}
		
		return botSentenceProcessMapper.countByExample(example);
	
	}


	@Override
	@Transactional
	public String createBotSentenceTemplate(BotSentenceProcessVO paramVO, String userId) {
		ReturnData<SysUser> data=iAuth.getUserById(new Long(userId));
		String userName=data.getBody().getUsername();
		String orgCode=data.getBody().getOrgCode();
		String orgName=data.getBody().getOrgName();
		
		if(StringUtils.isNotBlank(paramVO.getOrgCode())) {
			orgCode = paramVO.getOrgCode();
			orgName = paramVO.getOrgName();
		}
		
		
		long time1 = System.currentTimeMillis();
		logger.info("============" + time1);
		if(null == paramVO || StringUtils.isBlank(paramVO.getProcessId())) {
			throw new CommonException("创建模板失败，请求参数为空!");
		}
		logger.info("是否全TTS: " + all_tts);

		//查询已生效的话术模板
		//BotSentenceProcess botSentenceTemplate = botSentenceProcessMapper.selectByPrimaryKey(paramVO.getProcessId());
		BotSentenceTemplateExample templateExample = new BotSentenceTemplateExample();
		templateExample.createCriteria().andTemplateIdEqualTo(paramVO.getProcessId());
		List<BotSentenceTemplate> templateList = botSentenceTemplateMapper.selectByExample(templateExample);
		
		BotSentenceTemplate botSentenceTemplate = null;
		if(null != templateList && templateList.size() > 0) {
			botSentenceTemplate = templateList.get(0);
			paramVO.setProcessId(botSentenceTemplate.getProcessId());
		}else {
			throw new CommonException("创建模板失败，行业模板不存在!");
		}
		
		//保存话术流程信息
		BotSentenceProcess process = new BotSentenceProcess();
		
		if("00".equals(paramVO.getFlag())) {//创建新话术
			logger.info("创建新话术模板");
			//生成模板编号
			Pinyin4jUtil util= new Pinyin4jUtil();
			String pingyin = "";
			try {
				pingyin = util.toPinYinLowercase(paramVO.getTemplateName());
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				logger.error("生成首字母异常...", e);
				pingyin = SystemUtil.getSysJournalNo(5, false);
			}
			
			String templateId = pingyin + "_" + SystemUtil.getSysJournalNo(5, true) + "_en";
			process.setOrgCode(orgCode);
			process.setOrgName(orgName);
			process.setUserName(userName);
			process.setState("00");//制作中
			process.setVersion("0");//初始版本为0
			process.setAccountNo(userId);//所属账号
			process.setIndustry(botSentenceTemplate.getIndustryName());//所属行业
			process.setIndustryId(botSentenceTemplate.getIndustryId());
			process.setTemplateId(templateId);//模板编号
			process.setTemplateName(paramVO.getTemplateName());
			process.setOldProcessId(paramVO.getProcessId());//设置依赖的流程编号
			process.setTemplateType("01");//自定义模板
			logger.info("生成的模板编号为: " + templateId);
		}else if("01".equals(paramVO.getFlag())) {//修改话术
			logger.info("修改话术模板");
			
			//判断当前是否存在制作中的话术
			BotSentenceProcessExample example = new BotSentenceProcessExample();
			example.createCriteria().andTemplateIdEqualTo(botSentenceTemplate.getTemplateId()).andStateEqualTo("00");
			List<BotSentenceProcess> list = botSentenceProcessMapper.selectByExample(example);
			if(null != list && list.size() > 0) {
				throw new CommonException("当前话术已存在一条制作中的记录!");
			}
			
			BeanUtil.copyProperties(botSentenceTemplate, process);
			process.setLstUpdateTime(new Date(System.currentTimeMillis()));
			process.setLstUpdateUser(userId);
			process.setState("00");//制作中
			Integer newVersion = new Integer(botSentenceTemplate.getVersion())+1;
			process.setVersion(newVersion.toString());
			process.setProcessId(null);
			process.setOldProcessId(paramVO.getProcessId());
			
			//更新旧的信息为废弃状态
			BotSentenceProcess oldProcess = botSentenceProcessMapper.selectByPrimaryKey(paramVO.getProcessId());
			oldProcess.setState("99");//废弃状态
			oldProcess.setLstUpdateTime(new Date(System.currentTimeMillis()));
			oldProcess.setLstUpdateUser(userId);
			botSentenceProcessMapper.updateByPrimaryKey(oldProcess);
			logger.info("更新旧的话术流程为废弃状态: " + paramVO.getProcessId());
		}
		process.setCrtTime(new Date(System.currentTimeMillis()));
		process.setCrtUser(userId);
		
		botSentenceProcessMapper.insert(process);
		logger.info("保存话术流程信息成功...");
		logger.info("新生成的话术流程编号是: " + process.getProcessId());
		
		long time2 = System.currentTimeMillis();
		logger.info("======保存话术流程信息耗时======" + (time2-time1));
		
		//保存domain信息
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		example.createCriteria().andProcessIdEqualTo(paramVO.getProcessId());
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(example);
		
		//暂时先过滤掉这几个域-
		List<String> ignoreDomainList = new ArrayList<>();
	/*	ignoreDomainList.add("不清楚");
		ignoreDomainList.add("不知道");
		ignoreDomainList.add("等待");
		ignoreDomainList.add("用户不清楚");
		ignoreDomainList.add("自由介绍");*/
		
		List<BotSentenceDomain> domainList_new = new ArrayList<>();
		if(null != domainList && domainList.size() > 0) {
			for(BotSentenceDomain temp : domainList) {
				
				if(ignoreDomainList.contains(temp.getDomainName())) {
					continue;
				}
				
				BotSentenceDomain vo = new BotSentenceDomain();
				BeanUtil.copyProperties(temp, vo);
				vo.setComDomain(null);//在审批时才需要设置默认主流程
				vo.setTemplateId(process.getTemplateId());
				vo.setCrtTime(new Date(System.currentTimeMillis()));
				vo.setCrtUser(userId);
				vo.setProcessId(process.getProcessId());
				domainList_new.add(vo);
				//botSentenceDomainMapper.insert(vo);
			}
			botSentenceDomainExtMapper.batchInsert(domainList_new);
			logger.info("共保domain信息: " + domainList_new.size());
		}
		
		logger.info("保存domain信息成功...");
		
		long time3 = System.currentTimeMillis();
		logger.info("======保存domain信息耗时======" + (time3-time2));
		
		//保存brach信息
		BotSentenceBranchExample botSentenceBranchExample = new BotSentenceBranchExample();
		botSentenceBranchExample.createCriteria().andProcessIdEqualTo(paramVO.getProcessId());
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(botSentenceBranchExample);
		List<BotSentenceBranch> branchList_new = new ArrayList<>();
		//Map<String, Integer> map = new HashMap<>();
		List<Long> respIdList = new ArrayList<>();
		List<Long> intentIdList = new ArrayList<>();
		
		if(null != branchList && branchList.size() > 0) {
			
			List<VoliceInfoVO> voliceList = new ArrayList<>();
			List<IntentVO> intentsList = new ArrayList<>();
			
			for(BotSentenceBranch temp : branchList) {
				BotSentenceBranch vo = new BotSentenceBranch();
				BeanUtil.copyProperties(temp, vo);
				//保存录音信息
				String resp = temp.getResponse();
				String resp_new = "";
				logger.info("当前录音信息为: " + resp);
				if(StringUtils.isNotBlank(resp) && !"[]".equals(resp.trim()) && resp.trim().startsWith("[") && resp.trim().endsWith("]")) {
					String[] respArray = resp.substring(1,resp.length()-1).split(",");
					for(int i = 0 ; i < respArray.length ; i++) {
						if(respIdList.contains(new Long(respArray[i]))) {
							continue;
						}
						respIdList.add(new Long(respArray[i]));
					}
					resp_new="[" + resp_new + "]";
				}else {
					resp_new = "[]";
				}
				
				//如果是一般问题，则初始化权重为50
				if("一般问题".equals(temp.getDomain())) {
					vo.setWeight("50");
				}
				
				vo.setCrtTime(new Date(System.currentTimeMillis()));
				vo.setCrtUser(userId);
				vo.setProcessId(process.getProcessId());
				vo.setTemplateId(process.getTemplateId());
				vo.setBranchId(null);
				branchList_new.add(vo);
			}
			
			
			
			BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
			//intentExample.createCriteria().andIdIn(intentIdList);
			intentExample.createCriteria().andProcessIdEqualTo(paramVO.getProcessId());
			List<BotSentenceIntent> intentTempList = botSentenceIntentMapper.selectByExampleWithBLOBs(intentExample);
			
			//复制一条意图信息
			for(BotSentenceIntent intentTemp : intentTempList) {
				IntentVO intentVO = new IntentVO();
				BeanUtil.copyProperties(intentTemp, intentVO);
				intentVO.setCrtTime(new Date(System.currentTimeMillis()));
				intentVO.setCrtUser(userId);
				intentVO.setProcessId(process.getProcessId());
				intentVO.setTemplateId(process.getTemplateId());
				intentVO.setId(null);
				intentVO.setOldIntentId(intentTemp.getId());
				intentVO.setName(Constant.CUSTOMER_DEFINE_KEYWORD);//自定义
				//intentVO.setName(intentVO.getName().replace(intentVO.getName().split("_")[1], process.getTemplateId().split("_")[0]));
				intentsList.add(intentVO);
			}
			
			
			VoliceInfoExample voliceExample = new VoliceInfoExample();
			voliceExample.createCriteria().andVoliceIdIn(respIdList);
			List<VoliceInfo> voliceTempList = voliceInfoMapper.selectByExample(voliceExample);
			
			for(VoliceInfo voliceTemp : voliceTempList) {
				VoliceInfoVO voliceInfoVO = new VoliceInfoVO();
				//VoliceInfo volicdInfo = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[i]));
				
				voliceTemp.setCrtTime(new Date(System.currentTimeMillis()));
				voliceTemp.setCrtUser(userId);
				voliceTemp.setProcessId(process.getProcessId());
				voliceTemp.setType(Constant.VOLICE_TYPE_NORMAL);
				BeanUtil.copyProperties(voliceTemp, voliceInfoVO);
				voliceInfoVO.setOldVoliceId(voliceTemp.getVoliceId());
				voliceInfoVO.setTimes(voliceTemp.getTimes());
				voliceInfoVO.setTemplateId(process.getTemplateId());
				
				if(all_tts) {
					logger.info("全部TTS合成模式");
					voliceInfoVO.setVoliceUrl(null);
				}
				voliceList.add(voliceInfoVO);
			}
			
			
			//批量保存意图数据
			intentsList = batchSaveIntent(intentsList);
			
			//批量保存录音数据
			voliceList = batchSaveVoliceInfo(voliceList);
			
			for(BotSentenceBranch branch : branchList_new) {
				
				//设置branch的intents数据
				String oldIntent = branch.getIntents();
				String newIntent = "";
				List<String> newIntentList = new ArrayList<>();
				List<String> newIntentList2 = new ArrayList<>();
				newIntentList.add(oldIntent);
				if(StringUtils.isNotBlank(oldIntent)) {
					String[] intentArray = oldIntent.split(",");
					for(int i = 0 ; i < intentArray.length ; i++) {
						for(IntentVO vo : intentsList) {
							if(newIntentList.contains((vo.getOldIntentId()) + "")) {
								if(!newIntentList2.contains(vo.getId().toString())) {
									newIntentList2.add(vo.getId().toString());
								}
							}
						}
					}
					
					if(null != newIntentList2 && newIntentList2.size() > 0) {
						for(int i = 0 ; i < newIntentList2.size() ; i++) {
							if(i == newIntentList2.size() - 1) {//最后一条
								newIntent = newIntent + newIntentList2.get(i);
							}else {
								newIntent = newIntent + newIntentList2.get(i) + ",";
							}
						}
					}else {
						newIntent = null;
					}
					
				}else {
					newIntent = null;
				}
				branch.setIntents(newIntent);
				
				
				
				//设置branch的resp数据
				String oldResp = branch.getResponse();
				String newResp = "";
				List<String> newRespList = new ArrayList<>();
				List<String> newRespList2 = new ArrayList<>();
				//newRespList.add(oldResp);
				if(StringUtils.isNotBlank(oldResp) && !"[]".equals(oldResp.trim()) && oldResp.trim().startsWith("[") && oldResp.trim().endsWith("]")) {
					String[] respArray = oldResp.substring(1,oldResp.length()-1).split(",");
					
					for(int i = 0 ; i < respArray.length ; i++) {
						newRespList.add(respArray[i]);
					}
					
					for(int i = 0 ; i < respArray.length ; i++) {
						for(VoliceInfoVO vo : voliceList) {
							if(newRespList.contains((vo.getOldVoliceId()) + "")) {
								if(!newRespList2.contains(vo.getVoliceId().toString())) {
									newRespList2.add(vo.getVoliceId().toString());
								}
								//newResp = newResp.replaceAll((vo.getOldVoliceId()) + "", vo.getVoliceId()+"");
							}
						}
					}
					
					if(null !=newRespList2 && newRespList2.size() > 0) {
						newResp = "[";
						for(int i = 0 ; i < newRespList2.size() ; i++) {
							if(i == newRespList2.size() - 1) {//最后一条
								newResp = newResp + newRespList2.get(i) + "]";
							}else {
								newResp = newResp + newRespList2.get(i) + ",";
							}
						}
					}else {
						newResp = "[]";
					}
					
					
					
					
				}else {
					newResp = "[]";
				}
				
				branch.setResponse(newResp);
				
			}
			
			
			
			botSentenceBranchExtMapper.batchInsert(branchList_new);
			logger.info("共保存brach信息: " + branchList_new.size());
		}
		logger.info("保存brach信息成功");
		
		long time4 = System.currentTimeMillis();
		logger.info("======保存brach信息耗时======" + (time4-time3));
		
		//保存附件相关信息
		//botSentenceTemplate
		BotSentenceAddition addition = botSentenceAdditionMapper.selectByPrimaryKey(botSentenceTemplate.getProcessId());
		if(null != addition) {
			BotSentenceAddition temp = new BotSentenceAddition();
			BeanUtil.copyProperties(addition, temp);
			temp.setProcessId(process.getProcessId());
			temp.setSimTxt("");
			botSentenceAdditionMapper.insert(temp);
		}
		
		
		//创建流程时，默认创建一条挽回话术池--取domain为拒绝的negative数据
		BotSentenceBranchExample refuseExample = new BotSentenceBranchExample();
		refuseExample.createCriteria().andProcessIdEqualTo(process.getProcessId()).andDomainEqualTo("拒绝").andBranchNameEqualTo("negative");
		List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(refuseExample);
		if(null != list && list.size() > 0) {
			BotSentenceBranch refuse = list.get(0);
			if(StringUtils.isNotBlank(refuse.getResponse()) && !"[]".equals(refuse.getResponse())) {
				String[] respArray = refuse.getResponse().substring(1,refuse.getResponse().length()-1).split(",");
				VoliceInfo oldVolice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				VoliceInfo volice = new VoliceInfo();
				volice.setType(Constant.VOLICE_TYPE_REFUSE);//挽回话术
				volice.setContent(oldVolice.getContent().trim());
				volice.setProcessId(process.getProcessId());
				volice.setTemplateId(process.getTemplateId());
				volice.setName("默认挽回话术");
				volice.setDomainName("默认挽回话术");
				volice.setTimes(oldVolice.getTimes());
				if(all_tts) {
					logger.info("全部TTS合成模式");
					volice.setVoliceUrl(null);
				}else {
					volice.setVoliceUrl(oldVolice.getVoliceUrl());
				}
				
				voliceServiceImpl.saveVoliceInfo(volice, userId);
			}
		}
		logger.info("保存默认一条挽回话术池成功...");

		//初始化options数据
		logger.info("新增options");
		BotSentenceOptions options = new BotSentenceOptions();
		options.setProcessId(process.getProcessId());
		options.setTempname(process.getTemplateName());
		options.setTemplateId(process.getTemplateId());
		options.setCheckSim(true);
		options.setCurDomainPrior(true);
		options.setUseNotMatchLogic(true);
		options.setNotMatchSolution("solution_two");
		options.setTrade(process.getIndustry());
		
		options.setSilenceWaitSecs(4);
		options.setSilenceWaitTime(2);
		options.setInterruptWordsNum(5);
		options.setInterruptMinInterval(3);
		
		options.setCrtTime(new Date(System.currentTimeMillis()));
		options.setCrtUser(userId);
		botSentenceOptionsMapper.insert(options);
	
		//初始化回访数据
		for(BotSentenceDomain domain : domainList_new) {
			if(Constant.CATEGORY_TYPE_1.equals(domain.getCategory())) {
				botsentenceVariableService.initDomainSurveyIntent(process.getProcessId(), domain.getDomainName(), process.getTemplateId(), userId);
			}
		}
		
		logger.info("创建新的话术模板成功,流程编号: " + process.getProcessId());
		
		return process.getProcessId();
	}
	
	
	public List<VoliceInfoVO> batchSaveVoliceInfo(List<VoliceInfoVO> list){
		
		if(null != list && list.size() > 0) {
			voliceInfoExtMapper.batchInsert(list);
			return list;
		}
		return null;
	}
	
	
	public List<IntentVO> batchSaveIntent(List<IntentVO> list){
		if(null != list && list.size() > 0) {
			botSentenceIntentExtMapper.batchInsert(list);
			return list;
		}
		return null;
	}

	@Override
	@Transactional
	public void submit(String processId, String userId) {
		
		if(StringUtils.isBlank(processId)) {
			throw new CommonException("话术流程编号为为空!");
		}
		logger.info("提交话术流程审核, 当前话术流程编号: " + processId);
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		if(null == process) {
			throw new CommonException("当前话术流程不存在!");
		}
		
		if(Constant.APPROVE_NOTPASS.equals(process.getState()) || Constant.APPROVE_ONLINE.equals(process.getState())) {
			throw new CommonException("话术未作任何修改,不允许重复提交");
		}
		
		if(!"00".equals(process.getState())) {
			throw new CommonException("话术流程状态不是制作中，不允许提交审核!");
		}
		
		//判断有没有维护意向
		BotSentenceGradeRuleExample ruleExample = new BotSentenceGradeRuleExample();
		ruleExample.createCriteria().andProcessIdEqualTo(processId);
		int exist = botSentenceGradeRuleMapper.countByExample(ruleExample);
		if(exist < 1) {
			throw new CommonException("请先维护意向标签信息!");
		}
		
		//判断当前话术流程是否所有最后一个分支都是结束节点
		BotSentenceDomainExample domainExample = new BotSentenceDomainExample();
		domainExample.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1");
		List<String> domainList = new ArrayList<>();
		
		List<String> endDomainList = new ArrayList<>();
		
		List<BotSentenceDomain> list = botSentenceDomainMapper.selectByExample(domainExample);
		if(null != list && list.size() > 0) {
			for(BotSentenceDomain domain : list) {
				domainList.add(domain.getDomainName());
				//判断是否所有节点都有连线(end节点除外)
				BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
				branchExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(domain.getDomainName());
				int num = botSentenceBranchMapper.countByExample(branchExample);
				if(num < 1 && !Constant.DOMAIN_TYPE_END.equals(domain.getType()) && !Constant.DOMAIN_TYPE_AGENT.equals(domain.getType())) {
					throw new CommonException("节点["+domain.getDomainName()+"]未连线,或修改为结束节点!");
				}
				if(Constant.DOMAIN_TYPE_END.equals(domain.getType()) || Constant.DOMAIN_TYPE_AGENT.equals(domain.getType())) {
					endDomainList.add(domain.getDomainName());
				}
				
			}
		}
		
		//校验是否所有连线都已有连接
		BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
		branchExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainIn(domainList);
		List<BotSentenceBranch> branchValidateList = botSentenceBranchMapper.selectByExample(branchExample);
		for(BotSentenceBranch branch : branchValidateList) {
			if(StringUtils.isBlank(branch.getNext())) {
				throw new CommonException("连线["+branch.getLineName()+"]未连接节点,或删除该连线!");
			}
			
			if(Constant.BRANCH_TYPE_NORMAL.equals(branch.getType())) {//如果是一般类型的分支，关键字不能为空
				String intent = branch.getIntents();
				if(StringUtils.isBlank(intent)) {
					throw new CommonException("连线["+branch.getLineName()+"]关键词库为空!");
				}
			}
			
		}
		
		for(String domainName : domainList) {
			BotSentenceBranch branch = getEnterBranch(processId, domainName);
			if(StringUtils.isBlank(branch.getResponse())) {
				throw new CommonException("节点[" + domainName + "]文案不完整!");
			}
			
			String response = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",")[0];
			VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(response));
			if(StringUtils.isBlank(volice.getContent())) {
				throw new CommonException("节点[" + domainName + "]文案不完整!");
			}
		}
		//暂时先过滤掉这几个域-
		List<String> ignoreDomainList = new ArrayList<>();
		ignoreDomainList.add("不清楚");
		ignoreDomainList.add("不知道");
		ignoreDomainList.add("等待");
		ignoreDomainList.add("用户不清楚");
		ignoreDomainList.add("自由介绍");
		
		boolean needTts = false;
		
		//判断当前话术流程是否全部都已上传了录音信息
		BotSentenceBranchExample example = new BotSentenceBranchExample();
		example.createCriteria().andProcessIdEqualTo(processId).andResponseIsNotNull().andResponseNotEqualTo("[]").andDomainNotIn(ignoreDomainList);
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(example);
		for(BotSentenceBranch temp : branchList) {
			String [] array = BotSentenceUtil.getResponses(temp.getResponse());
			if(null != array && array.length > 0) {
				for(int i = 0 ; i < array.length ; i++) {
					VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(array[i]));
					//如果当前录音是TTS合成，则不需要校验
					boolean tts = botSentenceTtsService.validateContainParam(volice.getContent());
					if(!tts) {
						if(null == volice || StringUtils.isBlank(volice.getVoliceUrl())) {
							throw new CommonException("存在未上传录音的文案!");
						}
					}
					
					if(!needTts) {
						if(tts) {
							needTts = true;
						}
					}
					
				}
			}
		}
		
		//判断挽回话术池是否上传录音
		VoliceInfoExample refuseExample = new VoliceInfoExample();
		refuseExample.createCriteria()
				.andProcessIdEqualTo(processId)
				.andTypeEqualTo(Constant.VOLICE_TYPE_REFUSE);
		List<VoliceInfo> refuseVoliceList = voliceInfoMapper.selectByExample(refuseExample);
		if(!CollectionUtils.isEmpty(refuseVoliceList)){
			refuseVoliceList.forEach(voliceInfo -> {
				if(!voliceInfo.getNeedTts() && StringUtils.isBlank(voliceInfo.getVoliceUrl())){
					throw new CommonException("存在未上传录音的挽回话术文案!");
				}
			});
		}

		//校验TTS录音是否已上传
		BotSentenceTtsTaskExample ttsExample = new BotSentenceTtsTaskExample();
		ttsExample.createCriteria().andProcessIdEqualTo(processId).andIsParamEqualTo(Constant.IS_PARAM_FALSE);
		List<BotSentenceTtsTask> ttsList =  botSentenceTtsTaskMapper.selectByExample(ttsExample);
		
		if(needTts) {
			for(BotSentenceTtsTask task : ttsList) {
				if(StringUtils.isBlank(task.getVoliceUrl())) {
					throw new CommonException("存在未上传录音的tts文案!");
				}
				
				String voliceId = task.getBusiId();
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(voliceId));
				//校验是否编辑备用话术
				if(StringUtils.isNotBlank(volice.getContent())) {
					String content = volice.getContent();
					if(BotSentenceUtil.validateContainParam(content)) {
						//根据voliceId查询用话术
						BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
						backupExample.createCriteria().andProcessIdEqualTo(processId).andVoliceIdEqualTo(volice.getVoliceId());
						List<BotSentenceTtsBackup> backupList = botSentenceTtsBackupMapper.selectByExample(backupExample);
						if(null != backupList && backupList.size() > 0) {
							if(StringUtils.isBlank(backupList.get(0).getContent())) {
								throw new CommonException("备用话术未维护!");
							}
						}else {
							throw new CommonException("备用话术未维护!");
						}
						
					}
				}
			}
		}
		
		//校验通用对话录音是否已上传
		BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
		backupExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceTtsBackup> backupList = botSentenceTtsBackupMapper.selectByExample(backupExample);
		if(null != backupList && backupList.size() > 0) {
			for(BotSentenceTtsBackup backup : backupList) {
				if(null != backup.getContent() && StringUtils.isNotBlank(backup.getContent().trim()) && StringUtils.isBlank(backup.getUrl())) {
					throw new CommonException("存在未上传录音的备用话术!");
				}
			}
		}
		
		
		//校验是否保存录音师
		if(needTts && StringUtils.isBlank(process.getSoundType())) {
			throw new CommonException("当前话术需要TTS，请选择配音师!");
		}
		
		//如果有打断新规文案，则校验录音必须上传
		BotSentenceOptions option = botsentenceVariableService.getOptionsByProcessId(processId);
		if(null != option) {
			if(null != option.getInterruptionConfigStart() && option.getInterruptionConfigStart()) {
				if(StringUtils.isNotBlank(option.getVoice())) {
					VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(option.getVoice()));
					if(StringUtils.isBlank(volice.getVoliceUrl())) {
						throw new CommonException("打断文案未上传录音!");
					}
				}
			}
		}
		
		process.setState("01");//审核中
		process.setLstUpdateTime(new Date(System.currentTimeMillis()));
		process.setLstUpdateUser(userId);
		botSentenceProcessMapper.updateByPrimaryKey(process);
		logger.info("提交审核成功...");

		String speechAuditMessageJson = JSON.toJSONString(new SpeechAuditMessage(processId, userId));
		fanoutSender.send(SpeechMqConstant.SPEECH_AUDIT_EXCHANGE, speechAuditMessageJson);
		logger.info("发送关键词审核消息：{}", speechAuditMessageJson);
	}

	/**
	 * 修改话术模板
	 */
	@Override
	public String updateBotSentenceTemplate(String processId, String templateName, String industry, String userId) {
		if(StringUtils.isBlank(templateName) || StringUtils.isBlank(processId)) {
			throw new CommonException("修改话术失败，请求参数为空!");
		}
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		process.setTemplateName(templateName);
		if(StringUtils.isNotBlank(industry)) {
			process.setIndustry(industry);
		}
		process.setLstUpdateTime(new Date(System.currentTimeMillis()));
		process.setLstUpdateUser(userId);
		botSentenceProcessMapper.updateByPrimaryKey(process);
		
		updateProcessState(process.getProcessId(), userId);
		
		return null;
	}

	/**
	 * 删除一条制作中的话术
	 */
	@Override
	@Transactional
	public void delete(String processId, String userId) {
		if(StringUtils.isBlank(processId)) {
			throw new CommonException("修改话术失败，请求参数为空!");
		}
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		if(null == process){
			throw new CommonException("话术不存在!");
		}

		SpeechAuditStatusEnum auditStatus = SpeechAuditStatusEnum.getEnumByKey(process.getState());
		if(null == auditStatus){
			throw new CommonException("当前话术状态不合法!");
		}
		if(auditStatus != SpeechAuditStatusEnum.MAKING
			&& auditStatus != SpeechAuditStatusEnum.NOT_PASSED
			&& auditStatus != SpeechAuditStatusEnum.DEPLOY_FAILED){

			throw new CommonException("当前话术状态为:" + auditStatus.getDesc() + "，不允许删除！");
		}
		botSentenceProcessMapper.deleteByPrimaryKey(processId);

		BotAvailableTemplateExample availableTemplateExample = new BotAvailableTemplateExample();
		availableTemplateExample.createCriteria().andTemplateIdEqualTo(process.getTemplateId());
		botAvailableTemplateMapper.deleteByExample(availableTemplateExample);

		BotSentenceGradeExample gradeExample = new BotSentenceGradeExample();
		gradeExample.createCriteria().andProcessIdEqualTo(processId);
		botSentenceGradeMapper.deleteByExample(gradeExample);

		BotSentenceGradeRuleExample gradeRuleExample = new BotSentenceGradeRuleExample();
		gradeRuleExample.createCriteria().andProcessIdEqualTo(processId);
		botSentenceGradeRuleMapper.deleteByExample(gradeRuleExample);

		BotSentenceKeywordAuditExample auditExample = new BotSentenceKeywordAuditExample();
		auditExample.createCriteria().andProcessIdEqualTo(processId);
		botSentenceKeywordAuditMapper.deleteByExample(auditExample);

		BotSentenceOptionsExample optionsExample = new BotSentenceOptionsExample();
		optionsExample.createCriteria().andProcessIdEqualTo(processId);
		botSentenceOptionsMapper.deleteByExample(optionsExample);

		//删除domain
		BotSentenceDomainExample deleteDomainExample = new BotSentenceDomainExample();
		deleteDomainExample.createCriteria().andProcessIdEqualTo(processId);
		botSentenceDomainMapper.deleteByExample(deleteDomainExample);

		//删除branch
		BotSentenceBranchExample deleteBranchExample = new BotSentenceBranchExample();
		deleteBranchExample.createCriteria().andProcessIdEqualTo(processId);
		botSentenceBranchMapper.deleteByExample(deleteBranchExample);

		//删除intent
		BotSentenceIntentExample deleteIntentExample = new BotSentenceIntentExample();
		deleteIntentExample.createCriteria().andProcessIdEqualTo(processId);
		botSentenceIntentMapper.deleteByExample(deleteIntentExample);

		//删除volice
		VoliceInfoExample deleteVoliceExample = new VoliceInfoExample();
		deleteVoliceExample.createCriteria().andProcessIdEqualTo(processId);
		voliceInfoMapper.deleteByExample(deleteVoliceExample);

		//删除addition
		BotSentenceAdditionExample deleteAdditionExample = new BotSentenceAdditionExample();
		deleteAdditionExample.createCriteria().andProcessIdEqualTo(processId);
		botSentenceAdditionMapper.deleteByExample(deleteAdditionExample);

		//删除tts task
		BotSentenceTtsTaskExample ttsExample = new BotSentenceTtsTaskExample();
		ttsExample.createCriteria().andProcessIdEqualTo(processId);
		botSentenceTtsTaskMapper.deleteByExample(ttsExample);

		BotSentenceTtsParamExample ttsParamExample = new BotSentenceTtsParamExample();
		ttsParamExample.createCriteria().andProcessIdEqualTo(processId);
		botSentenceTtsParamMapper.deleteByExample(ttsParamExample);

		//删除tts 备用话术
		BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
		backupExample.createCriteria().andProcessIdEqualTo(processId);
		botSentenceTtsBackupMapper.deleteByExample(backupExample);

		//删除分享
		BotSentenceShareAuthExample example = new BotSentenceShareAuthExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		botSentenceShareAuthMapper.deleteByExample(example);
		logger.info("删除话术数据成功...");
	}

	
	@Override
	public List<CommonDialogVO> queryCommonDialog(String processId) {

		List<CommonDialogVO> branchList = new ArrayList<>();
		List<CommonDialogVO> resultList = new ArrayList<>();
		List<Long> voliceIdList = new ArrayList<>();
		
		//重复
		BotSentenceBranchExample example = new BotSentenceBranchExample();
		example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("用户不清楚").andBranchNameEqualTo("negative");
		List<BotSentenceBranch> repeatList = botSentenceBranchMapper.selectByExample(example);
		if(null != repeatList && repeatList.size() > 0) {
			BotSentenceBranch branch = repeatList.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			//查询关键词库列表
			List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
			vo.setHuashu("重复上一句");//话术
			vo.setLuoji("用户表示没听清时的回复，回复机器人的上一句话术");//逻辑
			vo.setYujin(branch.getDomain());//语境
			vo.setTitle(branch.getDomain());
			vo.setBranchId(branch.getBranchId());
			vo.setTemplateId(branch.getTemplateId());
			vo.setProcessId(processId);
			vo.setBranchName(branch.getBranchName());
			vo.setIntentList(intentList);
			vo.setDomain(branch.getDomain());
			vo.setIntentDomain(branch.getDomain());
			branchList.add(vo);
		}
		
		
		//挽回（关键词：拒绝domain，name为negative的intent
		logger.info("查询挽回话术...");
		BotSentenceBranchExample example1 = new BotSentenceBranchExample();
		example1.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("拒绝").andBranchNameEqualTo("negative");
		List<BotSentenceBranch> list1 = botSentenceBranchMapper.selectByExample(example1);
		if(null != list1 && list1.size() > 0) {
			BotSentenceBranch branch = list1.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			List<VoliceInfo> refuseList = new ArrayList<>();
			
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				//String huashu = "";
				for(int i = 0 ; i < respArray.length ; i++) {
					VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[i]));
					if(null != volice && i == 0) {
						vo.setHuashu(volice.getContent());//话术
						vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
						vo.setVoliceId(volice.getVoliceId());
						vo.setFlag(volice.getFlag());
						voliceIdList.add(volice.getVoliceId());
					}
					//huashu = huashu + volice.getContent() + "\n";
					refuseList.add(volice);
				}
				//vo.setHuashu(huashu);//话术
			}
			vo.setRefuseList(refuseList);
			//查询关键词库列表
			List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
			//vo.setYujin("全局挽回");//语境
			vo.setYujin(branch.getDomain());//语境
			vo.setLuoji("机器人回复业务问答和通用对话时被用户拒绝，则机器人回复该话术");
			vo.setBranchId(branch.getBranchId());
			vo.setTemplateId(branch.getTemplateId());
			vo.setProcessId(processId);
			vo.setBranchName(branch.getBranchName());
			//vo.setTitle("全局挽回");
			vo.setTitle(branch.getDomain());
			vo.setIntentList(intentList);
			vo.setDomain(branch.getDomain());
			vo.setIntentDomain(branch.getDomain());
			branchList.add(vo);
		}
		
		//失败邀约放到全局语境里来
		BotSentenceBranchExample example5 = new BotSentenceBranchExample();
		example5.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("邀约").andBranchNameEqualTo("failed_enter_branch");
		List<BotSentenceBranch> list5 = botSentenceBranchMapper.selectByExample(example5);
		if(null != list5 && list5.size() > 0) {
			BotSentenceBranch branch = list5.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			//查询关键词库列表
			List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
			
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				vo.setHuashu(volice.getContent());//话术
				vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
				vo.setYujin("失败邀约");//语境
				vo.setTitle("失败邀约");
				vo.setLuoji("机器人连续重复回复3次同样的话术后，走失败邀约");
				//vo.setYujin(branch.getDomain());//语境
				//vo.setTitle(branch.getDomain());
				vo.setBranchId(branch.getBranchId());
				vo.setVoliceId(volice.getVoliceId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setVoliceName(volice.getName());
				vo.setFlag(volice.getFlag());
				vo.setIntentList(intentList);
				vo.setDomain(branch.getDomain());
				vo.setIntentDomain(branch.getDomain());
				voliceIdList.add(volice.getVoliceId());
				branchList.add(vo);
			}
		}
		
		
		
		
		//未识别 （domain未为匹配响应的branch，special
		BotSentenceBranchExample example3 = new BotSentenceBranchExample();
		example3.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("未匹配响应").andBranchNameEqualTo("special");
		List<BotSentenceBranch> list3 = botSentenceBranchMapper.selectByExample(example3);
		int index = 1;
		if(null != list3 && list3.size() > 0) {
			for(BotSentenceBranch branch : list3) {
				
				if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
						&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
					String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
					
					//查询关键词库列表
					List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
					
					for(int i = 0 ; i < respArray.length ; i++) {
						CommonDialogVO vo = new CommonDialogVO();
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[i]));
						
						vo.setHuashu(volice.getContent());//话术
						vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
						//vo.setLuoji("未识别" + index);//逻辑
						vo.setLuoji("机器人第" + index + "次未识别出用户的回复则走该话术");//逻辑
						vo.setYujin("未识别");//语境
						vo.setTitle("未识别" + index);
						
						//vo.setLuoji(branch.getDomain());//逻辑
						//vo.setYujin(branch.getDomain());//语境
						//vo.setTitle(branch.getDomain() );
						
						vo.setBranchId(branch.getBranchId());
						vo.setVoliceId(volice.getVoliceId());
						vo.setTemplateId(branch.getTemplateId());
						vo.setProcessId(processId);
						vo.setBranchName(branch.getBranchName());
						vo.setVoliceName(volice.getName());
						vo.setFlag(volice.getFlag());
						vo.setIntentList(intentList);
						vo.setIntentDomain(branch.getDomain());
						vo.setDomain(branch.getDomain());
						voliceIdList.add(volice.getVoliceId());
						branchList.add(vo);
						index++;
					}
				}
				
			}
		}
		
		
		//出错，话术domain为”出错“的enter
		BotSentenceBranch chucuoBranch = getEnterBranch(processId, "出错");
		if(null != chucuoBranch) {
			BotSentenceBranch branch = chucuoBranch;
			CommonDialogVO vo = new CommonDialogVO();
			//查询关键词库列表
			List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
			
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				vo.setHuashu(volice.getContent());//话术
				vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
				//vo.setLuoji("触发\"出错\"流程");//逻辑
				//vo.setYujin("其他");//语境
				//vo.setTitle("其他1");
				vo.setLuoji("如果没有邀约流程，且模板中有逻辑指向邀约（失败邀约），则用出错的话术代替之");//逻辑
				vo.setYujin(branch.getDomain());//语境
				vo.setTitle(branch.getDomain());
				vo.setBranchId(branch.getBranchId());
				vo.setVoliceId(volice.getVoliceId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setVoliceName(volice.getName());
				vo.setFlag(volice.getFlag());
				vo.setIntentList(intentList);
				vo.setDomain(branch.getDomain());
				vo.setIntentDomain(branch.getDomain());
				voliceIdList.add(volice.getVoliceId());
				branchList.add(vo);
			}
		}
		
		

		//失败结束放到全局语境里来
		BotSentenceBranchExample example6 = new BotSentenceBranchExample();
		example6.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("结束").andBranchNameEqualTo("failed_enter_branch");
		List<BotSentenceBranch> list6 = botSentenceBranchMapper.selectByExample(example6);
		if(null != list6 && list6.size() > 0) {
			BotSentenceBranch branch = list6.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			//查询关键词库列表
			List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
			
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				vo.setHuashu(volice.getContent());//话术
				vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
				vo.setYujin("失败结束");//语境
				vo.setTitle("失败结束");
				vo.setLuoji("用户投诉、号码过滤、及连续被拒绝的情况，机器人走该话术");
				//vo.setYujin(branch.getDomain());//语境
				//vo.setTitle(branch.getDomain());
				vo.setBranchId(branch.getBranchId());
				vo.setVoliceId(volice.getVoliceId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setVoliceName(volice.getName());
				vo.setFlag(volice.getFlag());
				vo.setIntentList(intentList);
				vo.setDomain(branch.getDomain());
				vo.setIntentDomain(branch.getDomain());
				voliceIdList.add(volice.getVoliceId());
				branchList.add(vo);
			}
		}
		
		
		//在忙，话术为domain为结束_在忙的enter，取resp，关键词为domain为结束_在忙的enter，取intents
		BotSentenceBranchExample example4 = new BotSentenceBranchExample();
		example4.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("结束_在忙").andBranchNameEqualTo("enter_branch");
		List<BotSentenceBranch> list4 = botSentenceBranchMapper.selectByExample(example4);
		if(null != list4 && list4.size() > 0) {
			BotSentenceBranch branch = list4.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			List<BotSentenceIntentVO> intentList = null;
			//查询关键词库列表
			//获取在忙的关键词
			BotSentenceBranchExample example0 = new BotSentenceBranchExample();
			example0.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("在忙").andBranchNameEqualTo("negative");
			List<BotSentenceBranch> list0 = botSentenceBranchMapper.selectByExample(example0);
			if(null != list0 && list0.size() > 0) {
				BotSentenceBranch branch0 = list0.get(0);
				//查询关键词库列表
				intentList = botSentenceKeyWordsService.getIntent(branch0.getBranchId());
				vo.setIntentDomain(branch0.getDomain());
			}
			
			//List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
			
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				vo.setHuashu(volice.getContent());//话术
				vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
				vo.setLuoji("命中在忙关键词,走在忙结束,机器人主动挂断");//逻辑
				//vo.setYujin("在忙");//语境
				//vo.setTitle("在忙");
				vo.setLuoji("命中在忙关键词，走在忙结束,机器人主动挂断");
				vo.setYujin(branch.getDomain());//语境
				vo.setTitle(branch.getDomain());
				vo.setBranchId(branch.getBranchId());
				vo.setVoliceId(volice.getVoliceId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setVoliceName(volice.getName());
				vo.setFlag(volice.getFlag());
				vo.setIntentList(intentList);
				vo.setDomain(branch.getDomain());
				voliceIdList.add(volice.getVoliceId());
				branchList.add(vo);
			}
		}
		
		
		//强制结束，话术domain为”强制结束“的enter
		BotSentenceBranch endBranch = getEnterBranch(processId, "强制结束");
		if(null != endBranch) {
			BotSentenceBranch branch = endBranch;
			CommonDialogVO vo = new CommonDialogVO();
			//查询关键词库列表
			List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
			
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				vo.setHuashu(volice.getContent());//话术
				vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
				//vo.setLuoji("触发\"强制结束\"流程");//逻辑
				//vo.setYujin("其他");//语境
				//vo.setTitle("其他2");
				vo.setLuoji("通话轮数超过12轮，机器人走该话术");//逻辑
				vo.setYujin(branch.getDomain());//语境
				vo.setTitle(branch.getDomain());
				vo.setBranchId(branch.getBranchId());
				vo.setVoliceId(volice.getVoliceId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setVoliceName(volice.getName());
				vo.setFlag(volice.getFlag());
				vo.setIntentList(intentList);
				vo.setDomain(branch.getDomain());
				vo.setIntentDomain(branch.getDomain());
				voliceIdList.add(volice.getVoliceId());
				branchList.add(vo);
			}
		}
		
		//结束_未匹配，话术domain为”结束_未匹配“的enter
		BotSentenceBranch endWithoutMatchBranch = getEnterBranch(processId, "结束_未匹配");
		if(null != endWithoutMatchBranch) {
			BotSentenceBranch branch = endWithoutMatchBranch;
			CommonDialogVO vo = new CommonDialogVO();
			//查询关键词库列表
			List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
			
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				vo.setHuashu(volice.getContent());//话术
				vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
				//vo.setLuoji("触发\"结束_未匹配\"流程");//逻辑
				//vo.setYujin("其他");//语境
				//vo.setTitle("其他3");
				vo.setLuoji("①机器人第3次未识别出用户的回复则走该话术；②机器人走失败邀约后，机器人第1次未识别出用户的回复则走该话术");//逻辑
				vo.setYujin(branch.getDomain());//语境
				vo.setTitle(branch.getDomain());
				vo.setBranchId(branch.getBranchId());
				vo.setVoliceId(volice.getVoliceId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setVoliceName(volice.getName());
				vo.setFlag(volice.getFlag());
				vo.setIntentList(intentList);
				vo.setDomain(branch.getDomain());
				vo.setIntentDomain(branch.getDomain());
				voliceIdList.add(volice.getVoliceId());
				branchList.add(vo);
			}
		}
		
		
		//投诉，话术为domain为结束_在忙的enter，取resp，关键词为domain为结束_在忙的enter，取intents
		BotSentenceBranchExample example7 = new BotSentenceBranchExample();
		example7.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("投诉").andBranchNameEqualTo("negative");
		List<BotSentenceBranch> list7 = botSentenceBranchMapper.selectByExample(example7);
		if(null != list7 && list7.size() > 0) {
			BotSentenceBranch branch = list7.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			//查询关键词库列表
			List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
				
			vo.setHuashu("投诉默认触发结束_失败");//话术
			//vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
			vo.setLuoji("触发投诉关键词时，机器人走该话术，投诉关键词位于【编辑——自定义】中");//逻辑
			vo.setYujin(branch.getDomain());//语境
			vo.setTitle(branch.getDomain());
			vo.setBranchId(branch.getBranchId());
			//vo.setVoliceId(volice.getVoliceId());
			vo.setTemplateId(branch.getTemplateId());
			vo.setProcessId(processId);
			vo.setBranchName(branch.getBranchName());
			//vo.setVoliceName(volice.getName());
			//vo.setFlag(volice.getFlag());
			vo.setIntentList(intentList);
			vo.setDomain(branch.getDomain());
			vo.setIntentDomain(branch.getDomain());
			//voliceIdList.add(volice.getVoliceId());
			branchList.add(vo);
		}
		
		//号码过滤，话术为domain为结束_在忙的enter，取resp，关键词为domain为结束_在忙的enter，取intents
		BotSentenceBranchExample example8 = new BotSentenceBranchExample();
		example8.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("号码过滤").andBranchNameEqualTo("special");
		List<BotSentenceBranch> list8 = botSentenceBranchMapper.selectByExample(example8);
		if(null != list8 && list8.size() > 0) {
			BotSentenceBranch branch = list8.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			//查询关键词库列表
			List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
				
			vo.setHuashu("号码过滤默认触发结束_失败");//话术
			//vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
			vo.setLuoji("触发号码过滤关键词时，机器人走该话术，号码过滤关键词位于【编辑——自定义】中");//逻辑
			vo.setYujin(branch.getDomain());//语境
			vo.setTitle(branch.getDomain());
			vo.setBranchId(branch.getBranchId());
			//vo.setVoliceId(volice.getVoliceId());
			vo.setTemplateId(branch.getTemplateId());
			vo.setProcessId(processId);
			vo.setBranchName(branch.getBranchName());
			//vo.setVoliceName(volice.getName());
			//vo.setFlag(volice.getFlag());
			vo.setIntentList(intentList);
			vo.setDomain(branch.getDomain());
			vo.setIntentDomain(branch.getDomain());
			//voliceIdList.add(volice.getVoliceId());
			branchList.add(vo);
		}
		
		return branchList;
	}

	@Override
	@Transactional
	public void updateCommonDialog(CommonDialogVO commonDialog, String userId) {

		String content = commonDialog.getContent();
		long voliceId = commonDialog.getVoliceId();
		String branchId = commonDialog.getBranchId();
		
		BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);

		if(null == branch){
			throw new CommonException("更新失败，为找到相应的通用对话!");
		}

		//不需要校验关键词的域
		List<String> notValidateList = new ArrayList<>();
		notValidateList.add("失败邀约");
		notValidateList.add("未识别");
		notValidateList.add("出错");
		notValidateList.add("失败结束");
		notValidateList.add("强制结束");
		notValidateList.add("结束_未匹配");

		if(!notValidateList.contains(commonDialog.getYujin())) {
			String intendIdsStr = null;
			if(!CollectionUtils.isEmpty(commonDialog.getIntentList())){
				iKeywordsVerifyService.verifyCommonDialogBranch(commonDialog.getIntentList(), commonDialog.getProcessId(), commonDialog.getBranchId());
				intendIdsStr = botSentenceKeyWordsService.saveIntent(branch.getDomain(), branch.getProcessId(), branch.getTemplateId(), commonDialog.getIntentList(), "00", branch, userId);
			}
			branch.setIntents(intendIdsStr);

			DomainNameEnum domainNameEnum = DomainNameEnum.getDomainByName(branch.getDomain());
			if(DomainNameEnum.END_BUSY == domainNameEnum){

				branch.setIntents(null);
				//获取在忙的关键词
				BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
				branchExample.createCriteria()
						.andProcessIdEqualTo(branch.getProcessId())
						.andDomainEqualTo(DomainNameEnum.BUSY.getKey())
						.andBranchNameEqualTo(BranchNameEnum.negative.name());
				List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchExample);
				if(!CollectionUtils.isEmpty(branchList)){
					BotSentenceBranch busyBranch = branchList.get(0);
					busyBranch.setIntents(intendIdsStr);
					botSentenceBranchMapper.updateByPrimaryKey(busyBranch);
				}
			}
		}
		
		//没有文案的域
		if((!"号码过滤".equals(commonDialog.getDomain())) && (!"投诉".equals(commonDialog.getDomain())) 
				&& !"拒绝".equals(commonDialog.getDomain()) && !"用户不清楚".equals(commonDialog.getDomain())) {

			if(StringUtils.isBlank(commonDialog.getContent()) || StringUtils.isBlank(commonDialog.getBranchId())) {
				throw new CommonException("更新失败，请求数据为空!");
			}
			
			if(0 == commonDialog.getVoliceId()) {
				throw new CommonException("更新失败，请求数据为空!");
			}
			
			VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(voliceId));
			volice.setContent(content.replace("\n", "").trim());
			volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
			volice.setLstUpdateUser(userId);
			if(!"【新增】".equals(volice.getFlag())) {
				volice.setFlag("【修改】");
			}
			voliceServiceImpl.saveVoliceInfo(volice, userId);
		}
		
		
		if("拒绝".equals(commonDialog.getDomain())) {
			List<String> oldRespList = new ArrayList<>();
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				
				for(int i = 0 ; i < respArray.length ; i++) {
					oldRespList.add(respArray[i]);
				}
			}
			
			String resp = "[]";
			if(null != commonDialog.getRefuseList() && commonDialog.getRefuseList().size() > 0) {
				for(VoliceInfo volice : commonDialog.getRefuseList()) {
					if(StringUtils.isBlank(volice.getContent())) {
						throw new CommonException("更新失败，拒绝文案为空!");
					}
				}
				List<String> respList = new ArrayList<>();
				//维护一条或多条文案
				for(VoliceInfo volice : commonDialog.getRefuseList()) {
					//插入音频信息
					if(null != volice.getVoliceId() && volice.getVoliceId() > 0) {
						if(!"【新增】".equals(volice.getFlag())){
							volice.setFlag("【修改】");
						}
						if(oldRespList.contains(volice.getVoliceId().toString())) {
							oldRespList.remove(volice.getVoliceId().toString());
						}
					}else {
						volice.setFlag("【新增】");
					}
					volice.setDomainName(commonDialog.getDomain());
					volice.setType("00");
					volice.setTemplateId(branch.getTemplateId());
					volice.setProcessId(commonDialog.getProcessId());
					volice.setContent(volice.getContent().replace("\n", "").trim());
					voliceServiceImpl.saveVoliceInfo(volice, userId);
					respList.add(volice.getVoliceId().toString());
				}
				
				//删除被删掉的文案volice
				for(String deleteId : oldRespList) {
					voliceServiceImpl.deleteVolice(branch.getProcessId(), deleteId);
				}
				resp = "["+ BotSentenceUtil.listToString(respList)+"]";
				branch.setResponse("["+ BotSentenceUtil.listToString(respList)+"]");
			}else {
				if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
						&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
					String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
					
					for(int i = 0 ; i < respArray.length ; i++) {
						voliceServiceImpl.deleteVolice(branch.getProcessId(), respArray[i]);
					}
				}
				//拒绝不需要文案，则设置拒绝的branch为空
				resp = "[]";
				branch.setResponse("[]");
			}
			
			//更新拒绝域的enter_branch和failed_enter_branch
			BotSentenceBranch enter_branch = this.getEnterBranch(branch.getProcessId(), branch.getDomain());
			enter_branch.setResponse(resp);
			botSentenceBranchMapper.updateByPrimaryKey(enter_branch);
			
			BotSentenceBranch failed_enter_branch = this.getFailEnterBranch(branch.getProcessId(), branch.getDomain());
			failed_enter_branch.setResponse(resp);
			botSentenceBranchMapper.updateByPrimaryKey(failed_enter_branch);
		}
		
		branch.setLstUpdateTime(new Date(System.currentTimeMillis()));
		branch.setLstUpdateUser(userId);
		botSentenceBranchMapper.updateByPrimaryKey(branch);
		
		//如果当前状态为审批通过、已上线，则把状态修改为“制作中”
		this.updateProcessState(commonDialog.getProcessId(), userId);
	}


	@Transactional
	@Override
	public BotSentenceDomain saveNode(FlowNode blankDomain, String userId) {
		
		if(null == blankDomain){
			throw new CommonException("保存失败，请求参数不完整!");
		}
		if(StringUtils.isBlank(blankDomain.getProcessId())) {
			throw new CommonException("保存失败，话术流程编号为空!");
		}
		
		/*if(StringUtils.isBlank(blankDomain.getDomainName())) {
			throw new CommonException("保存失败，节点名称为空!");
		}*/
		
		if(StringUtils.isBlank(blankDomain.getLabel())) {
			throw new CommonException("保存失败，节点名称为空!");
		}
		
		logger.info("新增domain卡片");
		
		Date date = new Date(System.currentTimeMillis());
		
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(blankDomain.getProcessId());
		
		String domainName = "";
		
		//先判断当前domain人名字是否重复
		BotSentenceDomainExample valicateExample = new BotSentenceDomainExample();
		valicateExample.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andDomainNameEqualTo(blankDomain.getLabel());
		int num = botSentenceDomainMapper.countByExample(valicateExample);
		if(num > 0) {
			throw new CommonException("当前名称名已存在,请输入新的节点名称");
		}
		domainName = blankDomain.getLabel();
		
		if(Constant.DOMAIN_TYPE_END.equals(blankDomain.getType())) {
			if(!domainName.startsWith("结束")) {
				throw new CommonException("节点" + domainName + "的名称必须以结束_开头");
			}
		}
		
		
		//新增一个domain
		BotSentenceDomain domain = new BotSentenceDomain();
		domain.setCategory(Constant.CATEGORY_TYPE_1);//主流程
		domain.setProcessId(process.getProcessId());//话术流程编号
		domain.setTemplateId(process.getTemplateId());//话术模板编号
		domain.setDomainName(domainName);//名称
		domain.setType(blankDomain.getType());
		domain.setPositionX(blankDomain.getX());
		domain.setPositionY(blankDomain.getY());
		domain.setCrtTime(date);//创建时间
		domain.setCrtUser(userId);//创建人
		botSentenceDomainMapper.insert(domain);
		
		//第五步：保存录音信息
		VoliceInfo volice = new VoliceInfo();
		volice.setType(Constant.VOLICE_TYPE_NORMAL);//话术流程domain录音
		volice.setDomainName(domainName);
		if(StringUtils.isNotBlank(blankDomain.getContent())) {
			volice.setContent(blankDomain.getContent().replace("\n", "").trim());//录音内容
		}
		
		volice.setProcessId(blankDomain.getProcessId());
		volice.setTemplateId(process.getTemplateId());
		volice.setFlag("【新增】");
		//voliceInfoMapper.insert(volice);
		voliceServiceImpl.saveVoliceInfo(volice, userId);
		
		
		//保存branch信息--enter_branch
		BotSentenceBranch enterBranch = new BotSentenceBranch();
		enterBranch.setDomain(domainName);
		enterBranch.setBranchName("enter_branch");
		enterBranch.setTemplateId(process.getTemplateId());
		enterBranch.setCrtTime(date);
		enterBranch.setCrtUser(userId);
		enterBranch.setProcessId(blankDomain.getProcessId());
		enterBranch.setResponse("["+volice.getVoliceId()+"]");
		botSentenceBranchMapper.insert(enterBranch);
		
		//保存branch信息--failed_enter_branch
		BotSentenceBranch failedEnterBranch = new BotSentenceBranch();
		BeanUtil.copyProperties(enterBranch, failedEnterBranch);
		failedEnterBranch.setBranchId(null);
		failedEnterBranch.setBranchName("failed_enter_branch");
		botSentenceBranchMapper.insert(failedEnterBranch);
		
		//保存negative信息
		BotSentenceBranch negativeBranch = new BotSentenceBranch();
		negativeBranch.setDomain(domainName);
		negativeBranch.setBranchName("negative");
		negativeBranch.setCrtTime(date);
		negativeBranch.setCrtUser(userId);
		negativeBranch.setProcessId(blankDomain.getProcessId());
		negativeBranch.setTemplateId(process.getTemplateId());
		negativeBranch.setResponse("[]");
		negativeBranch.setNext("拒绝");
		negativeBranch.setEnd("拒绝");
		botSentenceBranchMapper.insert(negativeBranch);
		
		//初始化回访数据
		botsentenceVariableService.initDomainSurveyIntent(process.getProcessId(), domain.getDomainName(), process.getTemplateId(), userId);

		updateProcessState(blankDomain.getProcessId(), userId);
		return domain;
	}
	
	/**
	 * 更新一个domain卡片
	 */
	@Transactional
	public BotSentenceDomain updateNode(FlowNode blankDomain, String domainId, String userId) {

		//第一步，更新domain信息的名称
		BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(blankDomain.getId());
		String oldDomainName = domain.getDomainName();
		String newDomainName = blankDomain.getLabel();
		//先判断当前domain人名字是否重复
		if(!domain.getDomainName().trim().equals(blankDomain.getLabel().trim())) {
			BotSentenceDomainExample valicateExample = new BotSentenceDomainExample();
			valicateExample.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andDomainNameEqualTo(blankDomain.getLabel());
			List<BotSentenceDomain> validateList = botSentenceDomainMapper.selectByExample(valicateExample);
			if(null != validateList && validateList.size() > 0) {
				throw new CommonException("当前名称名已存在,请输入新的节点名称");
			}
		}
		
		domain.setType(blankDomain.getType());
		domain.setDomainName(blankDomain.getLabel());
		domain.setPositionY(blankDomain.getY());
		domain.setPositionX(blankDomain.getX());
		domain.setLstUpdateTime(new Date(System.currentTimeMillis()));
		domain.setLstUpdateUser(userId);
		botSentenceDomainMapper.updateByPrimaryKey(domain);
		
		this.updateValiableDomainName(domain.getProcessId(), oldDomainName, newDomainName, "update");

		if(StringUtils.isNotBlank(domainId) && domainId.equals(blankDomain.getId())) {//如果修改的是当前domain才需要更新以下数据，否则只要更新上面的坐标即可
			//第三步：更新所有domain为oldDomain的branch
			BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
			branchExample.createCriteria()
					.andProcessIdEqualTo(blankDomain.getProcessId())
					.andDomainEqualTo(oldDomainName);
			List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchExample);
			branchList.forEach(branch -> {
				branch.setDomain(newDomainName);
				branch.setLstUpdateTime(new Date());
				branch.setLstUpdateUser(userId);

				if(branch.getBranchName().contains("refuse_")){
					branch.setBranchName(branch.getBranchName().replaceAll(oldDomainName, newDomainName));
					branch.setRespname(branch.getRespname().replaceAll(oldDomainName, newDomainName));
				}

				botSentenceBranchMapper.updateByPrimaryKey(branch);
			});

			//第四步：更新所有next为oldDomain的branch
			BotSentenceBranchExample nextBranchExample = new BotSentenceBranchExample();
			nextBranchExample.createCriteria()
					.andProcessIdEqualTo(blankDomain.getProcessId())
					.andNextEqualTo(oldDomainName);
			List<BotSentenceBranch> nextBranchList = botSentenceBranchMapper.selectByExample(nextBranchExample);
			nextBranchList.forEach(branch -> {
				branch.setNext(newDomainName);
				branch.setLstUpdateTime(new Date());
				branch.setLstUpdateUser(userId);
				botSentenceBranchMapper.updateByPrimaryKey(branch);
			});

			//第五步：更新所有end为oldDomain的branch
			BotSentenceBranchExample endBranchExample = new BotSentenceBranchExample();
			endBranchExample.createCriteria()
					.andProcessIdEqualTo(blankDomain.getProcessId())
					.andEndEqualTo(oldDomainName);
			List<BotSentenceBranch> endBranchList = botSentenceBranchMapper.selectByExample(endBranchExample);
			endBranchList.forEach(branch -> {
				branch.setEnd(newDomainName);
				branch.setLstUpdateTime(new Date());
				branch.setLstUpdateUser(userId);
				botSentenceBranchMapper.updateByPrimaryKey(branch);
			});

			//更新volice表的content字段（文案）
			if(StringUtils.isNotBlank(blankDomain.getContent())) {
				BotSentenceBranch enterBranch = this.getEnterBranch(domain.getProcessId(), newDomainName);
				String resp = enterBranch.getResponse();
				if(StringUtils.isNotBlank(resp) && !"[]".equals(resp.trim()) && resp.trim().startsWith("[") && resp.trim().endsWith("]")) {
					String[] respArray = resp.substring(1,resp.length()-1).split(",");
					if(respArray.length > 0) {
						long voliceId = new Long(respArray[0]);
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(voliceId);
						
						if(StringUtils.isNotBlank(volice.getContent()) && !volice.getContent().equals(blankDomain.getContent()) && !"【新增】".equals(volice.getFlag())) {
							volice.setFlag("【修改】");
						}
						
						if(StringUtils.isNotBlank(blankDomain.getContent())) {
							volice.setContent(blankDomain.getContent().replace("\n", "").trim());
						}else {
							volice.setContent(blankDomain.getContent());
						}
						
						volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
						volice.setLstUpdateUser(userId);
						volice.setDomainName(blankDomain.getLabel());
						
						voliceServiceImpl.saveVoliceInfo(volice, userId);
					}
				}
			}
			//更新意图表的domain名称和关键字
			BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
			intentExample.createCriteria().andProcessIdEqualTo(domain.getProcessId()).andDomainNameEqualTo(oldDomainName);
			List<BotSentenceIntent> intentList = botSentenceIntentMapper.selectByExample(intentExample);
			if(null != intentList && intentList.size() > 0) {
				for(BotSentenceIntent intent : intentList) {
					intent.setDomainName(newDomainName);
					intent.setLstUpdateTime(new Date(System.currentTimeMillis()));
					intent.setLstUpdateUser(userId);
					botSentenceIntentMapper.updateByPrimaryKey(intent);
				}
			}
			
			
			VoliceInfoExample voliceExample = new VoliceInfoExample();
			voliceExample.createCriteria().andProcessIdEqualTo(domain.getProcessId()).andDomainNameEqualTo(oldDomainName);
			List<VoliceInfo> voliceList = voliceInfoMapper.selectByExample(voliceExample);
			if(null != voliceList && voliceList.size() > 0) {
				for(VoliceInfo volice : voliceList) {
					volice.setDomainName(newDomainName);
					volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
					volice.setLstUpdateUser(userId);
					voliceInfoMapper.updateByPrimaryKey(volice);
				}
			}
		}
		
		//如果当前状态为审批通过、已上线，则把状态修改为“制作中”
		this.updateProcessState(domain.getProcessId(), userId);
		
		return domain;
	}

	/**
	 * 保存线条
	 */
	@Transactional
	@Override
	public void saveEdge(String processId, FlowEdge edge, String userId) {
		if(null == edge ) {
			throw new CommonException("保存失败，请求参数不完整!");
		}

		if(StringUtils.isBlank(edge.getLabel())) {
			throw new CommonException("保存失败，线条名称为空!");
		}

		if(StringUtils.isBlank(edge.getType())) {
			throw new CommonException("保存失败，分支类型为空!");
		}

		if(StringUtils.isBlank(edge.getSource()) || StringUtils.isBlank(edge.getTarget())) {
			throw new CommonException("保存失败，线条[" + edge.getLabel() + "]未连接!");
		}

		Date date = new Date(System.currentTimeMillis());
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);

		BotSentenceDomain source = botSentenceDomainMapper.selectByPrimaryKey(edge.getSource());
		BotSentenceDomain target = botSentenceDomainMapper.selectByPrimaryKey(edge.getTarget());

		logger.info("新增连线数据...");
		//在当前节点新增一个Branch
		BotSentenceBranch newBranch = new BotSentenceBranch();

		//获取新增分支的名称，统一以branch_开头，后面接数字
		String branchName = getNewBranchName(processId);
		BranchTypeEnum branchType = BranchTypeEnum.getTypeByKey(edge.getType());

		//check not reject branch
		if(BranchTypeEnum.NOT_REJECT == branchType){
			//判断当前卡片向下是否已存在未拒绝的分支，如果存在，则不允许新增
			BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
			branchExample.createCriteria()
					.andProcessIdEqualTo(processId)
					.andDomainEqualTo(source.getDomainName())
					.andTypeEqualTo(BranchTypeEnum.NOT_REJECT.getKey());
			if(botSentenceBranchMapper.countByExample(branchExample) > 0) {
				throw new CommonException("当前节点【" + source.getDomainName() + "】已存在'未拒绝'的分支");
			}
			branchName = "positive";
		}

		List<BotSentenceIntentVO> intentVOS = edge.getIntentList();
		if(!CollectionUtils.isEmpty(intentVOS)){
			iKeywordsVerifyService.verifyMainProcessBranch(edge.getIntentList(), processId, source.getDomainName(), null);
			String intentIds = botSentenceKeyWordsService.saveIntent(source.getDomainName(), process.getProcessId(), process.getTemplateId(), edge.getIntentList(), CategoryEnum.MAIN_PROCESS.getTypeKey(), null, userId);
			newBranch.setIntents(intentIds);
		}

		newBranch.setDomain(source.getDomainName());
		newBranch.setCrtTime(date);
		newBranch.setCrtUser(userId);
		newBranch.setProcessId(processId);
		newBranch.setTemplateId(process.getTemplateId());
		newBranch.setResponse("[]");
		newBranch.setLineName(edge.getLabel());
		newBranch.setIsShow("1");//1-显示
		newBranch.setNext(target.getDomainName());
		newBranch.setBranchName(branchName);
		newBranch.setType(edge.getType());
		newBranch.setEnd(target.getDomainName());

		botSentenceBranchMapper.insert(newBranch);

		updateProcessState(processId, userId);
	}


	/**
	 * 更新连接线
	 */
	@Transactional
	public void updateEdge(String processId, FlowEdge edge, BotSentenceBranch botSentenceBranch, Map<String, String> map, String branchId, String userId) {
		logger.info("更新连线数据");
		//获取原先该连线的source和target值，如有更改，则需要更新；如无更改，则不用处理
		BotSentenceDomain old_sourcedomain = getDomain(processId, botSentenceBranch.getDomain());
		BotSentenceDomain old_targetdomain = getDomain(processId, botSentenceBranch.getNext());

		BotSentenceDomain new_sourceDomain = botSentenceDomainMapper.selectByPrimaryKey(map.get(edge.getSource()));
		BotSentenceDomain new_targetDomain = botSentenceDomainMapper.selectByPrimaryKey(map.get(edge.getTarget()));

		//判断如果线的指和原来完全一致，则不需要处理
		if(old_sourcedomain.getDomainId().equals(new_sourceDomain.getDomainId()) &&
				old_targetdomain.getDomainId().equals(new_targetDomain.getDomainId())) {
			logger.info(botSentenceBranch.getBranchId() + "当前连接线不需要处理...");
		}else {
			//如果是source有变动，则需要修改原先连接线的domain为新的domain值
			if(!old_sourcedomain.getDomainId().equals(new_sourceDomain.getDomainId())) {
				botSentenceBranch.setDomain(new_sourceDomain.getDomainName());
				botSentenceBranch.setNext(new_targetDomain.getDomainName());
				logger.info("更新连线由"+ new_sourceDomain.getDomainName() + "---》 " + new_targetDomain.getDomainName());
			}
		}

		botSentenceBranch.setLineName(edge.getLabel());//更新线的名称
		botSentenceBranch.setLstUpdateTime(new Date(System.currentTimeMillis()));
		botSentenceBranch.setLstUpdateUser(userId);


		processId = botSentenceBranch.getProcessId();

		if(StringUtils.isNotBlank(branchId) && branchId.equals(botSentenceBranch.getBranchId())) {//如果编辑的是当前连线，才需要更新意图关键字，其它情况只需要更新连线即可

			BranchTypeEnum branchType = BranchTypeEnum.getTypeByKey(edge.getType());
			//check not reject branch
			if(BranchTypeEnum.NOT_REJECT == branchType){
				//判断当前卡片向下是否已存在未拒绝的分支，如果存在，则不允许新增
				BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
				branchExample.createCriteria()
						.andProcessIdEqualTo(processId)
						.andDomainEqualTo(botSentenceBranch.getDomain())
						.andTypeEqualTo(BranchTypeEnum.NOT_REJECT.getKey())
						.andBranchIdNotEqualTo(branchId);
				if(botSentenceBranchMapper.countByExample(branchExample) > 0) {
					throw new CommonException("当前节点【" + botSentenceBranch.getDomain() + "】已存在'未拒绝'的分支");
				}
				botSentenceBranch.setBranchName(BranchNameEnum.positive.name());
			}else if(BranchTypeEnum.NORMAL == branchType){
				BranchTypeEnum originType = BranchTypeEnum.getTypeByKey(botSentenceBranch.getType());
				if(BranchTypeEnum.NOT_REJECT == originType){
					botSentenceBranch.setBranchName(getNewBranchName(processId));
				}
				botSentenceBranch.setEnd(botSentenceBranch.getNext());
			}else {
				throw new CommonException("不合法的分支类型");
			}

			List<BotSentenceIntentVO> intentVOS = edge.getIntentList();
			if(!CollectionUtils.isEmpty(intentVOS)){
				iKeywordsVerifyService.verifyMainProcessBranch(intentVOS, processId, botSentenceBranch.getDomain(), branchId);
				String intentIds = botSentenceKeyWordsService.saveIntent(new_sourceDomain.getDomainName(), processId, botSentenceBranch.getTemplateId(), intentVOS, CategoryEnum.MAIN_PROCESS.getTypeKey(), botSentenceBranch, userId);
				botSentenceBranch.setIntents(intentIds);
			}
		}
		botSentenceBranch.setType(edge.getType());
		botSentenceBranchMapper.updateByPrimaryKey(botSentenceBranch);

		//如果当前状态为审批通过、已上线，则把状态修改为“制作中”
		this.updateProcessState(processId, userId);
	}



	/**
	 * 获取当前domain的enter_branch分支
	 * @param processId
	 * @param domain
	 * @return
	 */
	public BotSentenceBranch getEnterBranch(String processId, String domain) {
		BotSentenceBranchExample mainExample = new BotSentenceBranchExample();
		mainExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain).andBranchNameEqualTo("enter_branch");
		List<BotSentenceBranch> mainBranchList = botSentenceBranchMapper.selectByExample(mainExample);
		if(null != mainBranchList && mainBranchList.size() > 0) {
			return mainBranchList.get(0);
		}
		return null;
	}


	/**
	 * 获取当前domain的fail_enter_branch分支
	 * @param processId
	 * @param domain
	 * @return
	 */
	public BotSentenceBranch getFailEnterBranch(String processId, String domain) {
		BotSentenceBranchExample mainExample = new BotSentenceBranchExample();
		mainExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain).andBranchNameEqualTo("failed_enter_branch");
		List<BotSentenceBranch> mainBranchList = botSentenceBranchMapper.selectByExample(mainExample);
		if(null != mainBranchList && mainBranchList.size() > 0) {
			return mainBranchList.get(0);
		}
		return null;
	}


	/**
	 * 获取当前domain的positive分支
	 * @param processId
	 * @param domain
	 * @return
	 */
	public BotSentenceBranch getPositiveBranch(String processId, String domain) {
		BotSentenceBranchExample mainExample = new BotSentenceBranchExample();
		mainExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain).andBranchNameEqualTo("positive");
		List<BotSentenceBranch> mainBranchList = botSentenceBranchMapper.selectByExample(mainExample);
		if(null != mainBranchList && mainBranchList.size() > 0) {
			return mainBranchList.get(0);
		}
		return null;
	}

	/**
	 * 生成新的branchName
	 * @param processId
	 * @return
	 */
	private String getNewBranchName(String processId) {
		String branchName = branch_prefix + "1";
		List<String> queryList = botSentenceBranchExtMapper.querySpecialBranchoList(processId);

		if(null != queryList && queryList.size() > 0) {
			List<Integer> list = new ArrayList<>();
			for(String temp : queryList) {
				String currentName = temp;
				int currentNum = new Integer(currentName.substring(branch_prefix.length(), currentName.length()));
				list.add(currentNum);
			}

			int maxNameNum = Collections.max(list) + 1;
			branchName = branch_prefix + maxNameNum;
		}
		return branchName;
	}

	/**
	 * 生成新的domain名称
	 * @param processId
	 * @return
	 */
	private String getNewDomainName(String processId, boolean isMainFlow) {
		String blankDomainName = domain_prefix + "1";
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		example.createCriteria().andProcessIdEqualTo(processId).andDomainNameLike(domain_prefix + "%");
		List<BotSentenceDomain> queryList = botSentenceDomainMapper.selectByExample(example);
		if(null != queryList && queryList.size() > 0) {
			List<Integer> list = new ArrayList<>();
			for(BotSentenceDomain temp : queryList) {
				String currentName = temp.getDomainName();
				int currentNum = new Integer(currentName.substring(domain_prefix.length(), currentName.length()));
				list.add(currentNum);
			}
			int maxNameNum = Collections.max(list) + 1;
			blankDomainName = domain_prefix + maxNameNum;
		}
		return blankDomainName;
	}


	/**
	 * 保存挽回话术，最多三个
	 */
	@Override
	@Transactional
	public void saveRefuseBranch(String processId, String domainName, List<String> voliceIdList, String userId) {
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);

		if(StringUtils.isBlank(processId) || StringUtils.isBlank(domainName)) {
			throw new CommonException("保存挽回话术失败，请求参数不完整!");
		}

		if(null != voliceIdList && voliceIdList.size() > 0) {
			if(voliceIdList.size() > 3) {
				throw new CommonException("挽回话术超过3条!");
			}


			//先把原先挽回话术删除
			BotSentenceBranchExample deleteBranch = new BotSentenceBranchExample();
			deleteBranch.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domainName).andBranchNameLike("refuse_%");
			botSentenceBranchMapper.deleteByExample(deleteBranch);

			//新增挽回话术
			int index = 1;
			for(String temp : voliceIdList) {
				if(StringUtils.isBlank(temp)) {
					continue;
				}
				BotSentenceBranch branch = new BotSentenceBranch();
				branch.setResponse("[" + temp + "]");
				branch.setRespname(domainName + "-" + "挽回" + index);
				branch.setBranchName("refuse_" + domainName);
				branch.setCrtTime(new Date(System.currentTimeMillis()));
				branch.setCrtUser(userId);
				branch.setDomain(domainName);
				branch.setIsShow("0");
				branch.setNext(domainName);
				branch.setEnd("结束");
				branch.setTemplateId(process.getTemplateId());
				branch.setProcessId(processId);

				//获取意图信息：来源：domin为拒绝的negative分支的intent
				BotSentenceBranchExample intentExample = new BotSentenceBranchExample();
				intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("拒绝").andBranchNameEqualTo("negative");
				List<BotSentenceBranch> intentList = botSentenceBranchMapper.selectByExample(intentExample);
				if(null != intentList && intentList.size() > 0) {
					branch.setIntents(intentList.get(0).getIntents());
				}

				botSentenceBranchMapper.insert(branch);
				index++;
			}

		}else {
			throw new CommonException("挽回话术列表为空!");
		}

		updateProcessState(processId, userId);

	}

	@Override
	@Transactional
	public void deleteRefuseBranch(String processId, String domainName, String voliceId) {
		BotSentenceBranchExample example = new BotSentenceBranchExample();
		//example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domainName).andBranchNameEqualTo("refuse_" + domainName).andResponseLike("%"+voliceId+"%");
		example.createCriteria().andProcessIdEqualTo(processId).andBranchNameLike("refuse_%").andResponseLike("%"+voliceId+"%");
		botSentenceBranchMapper.deleteByExample(example);
	}

	@Override
	@Transactional
	public void deleteDomain(String processId, String domainId, String userId) {
		List<String> domainNames = new ArrayList<>();

		//删除domain本身
		BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(domainId);
		domainNames.add(domain.getDomainName());

		if(null != domainNames && domainNames.size() > 0) {
			//删除原来其它domain指向当前节点的next数据
			BotSentenceBranchExample branchExample2 = new BotSentenceBranchExample();
			branchExample2.createCriteria().andProcessIdEqualTo(processId).andNextIn(domainNames).andIsShowEqualTo("1");
			botSentenceBranchMapper.deleteByExample(branchExample2);

			//删除录音信息
			VoliceInfoExample voliceExample = new VoliceInfoExample();
			voliceExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameIn(domainNames);

			List<VoliceInfo> voliceList = voliceInfoMapper.selectByExample(voliceExample);
			if(null != voliceList && voliceList.size() > 0) {
				for(VoliceInfo volice : voliceList) {
					boolean flag = false;
					//判断是否有其它域在使用当前方案，如果有，则不删除
					BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
					branchExample.createCriteria().andProcessIdEqualTo(processId).andDomainNotIn(domainNames);
					List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(branchExample);
					if(null != list && list.size() > 0) {
						for(BotSentenceBranch branch : list) {
							String respStr = branch.getResponse();
							if(StringUtils.isNotBlank(respStr) && !"[]".equals(respStr.trim()) && respStr.trim().startsWith("[") && respStr.trim().endsWith("]")) {
								String[] respArray = respStr.substring(1,respStr.length()-1).split(",");
								for(String resp : respArray) {
									if(StringUtils.isNotBlank(resp) && resp.equals(volice.getVoliceId().toString())) {
										flag = true;
										break;
									}
								}
								if(flag) {
									break;
								}
							}
						}
					}

					if(flag) {
						continue;
					}

					voliceServiceImpl.deleteVolice(processId, volice.getVoliceId().toString());
				}
			}



			//删除branch信息
			BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
			branchExample.createCriteria().andProcessIdEqualTo(processId).andDomainIn(domainNames);
			botSentenceBranchMapper.deleteByExample(branchExample);

			//删除意图信息
			BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
			intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameIn(domainNames);
			botSentenceIntentMapper.deleteByExample(intentExample);


			//处理转人工
			//判断是否已存在agent的节点
			if(StringUtils.isNotBlank(domain.getType()) && Constant.DOMAIN_TYPE_AGENT.equals(domain.getType())) {
				BotSentenceIntentExample agentExample = new BotSentenceIntentExample();
				intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameEqualTo(Constant.DOMAIN_TYPE_AGENT);
				List<BotSentenceIntent> list = botSentenceIntentMapper.selectByExample(agentExample);
				if(null != list && list.size() > 0) {
					botSentenceIntentMapper.deleteByPrimaryKey(list.get(0).getId());
				}
			}

			//删除回访信息
			BotSentenceSurveyExample surveyExample = new BotSentenceSurveyExample();
			surveyExample.createCriteria().andProcessIdEqualTo(processId);
			botSentenceSurveyMapper.deleteByExample(surveyExample);

			BotSentenceSurveyIntentExample surveyIntentExample = new BotSentenceSurveyIntentExample();
			surveyIntentExample.createCriteria().andProcessIdEqualTo(processId);
			botSentenceSurveyIntentMapper.deleteByExample(surveyIntentExample);
		}
		botSentenceDomainMapper.deleteByPrimaryKey(domainId);

		//修改指向该域的为空
		botSentenceBranchExtMapper.updateEndWhenDeleteDomain(processId, domain.getDomainName());

		//处理变量及意向
		this.updateValiableDomainName(processId, domain.getDomainName(), null, "delete");
		
		this.updateProcessState(processId, userId);
	}

	@Override
	@Transactional
	public void deleteBranch(String processId, String branchId, String userId) {
		//删除branch本身
		//BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);
		botSentenceBranchMapper.deleteByPrimaryKey(branchId);

		this.updateProcessState(processId, userId);
	}


	public BotSentenceDomain getDomain(String processId, String domainName) {
		BotSentenceDomainExample domaninExample = new BotSentenceDomainExample();
		domaninExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameEqualTo(domainName);
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domaninExample);
		if(null != domainList && domainList.size() > 0) {
			return domainList.get(0);
		}
		return null;
	}


	public List<BotSentenceDomain> getChildDomainList(String domainId) {
		BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(domainId);

		BotSentenceDomainExample domaninExample = new BotSentenceDomainExample();
		domaninExample.createCriteria().andProcessIdEqualTo(domain.getProcessId()).andCategoryEqualTo("1").andIsMainFlowEqualTo("01");
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domaninExample);
		if(null != domainList && domainList.size() > 0) {
			return domainList;
		}
		return null;
	}

	@Override
	@Transactional
	public String deleteDomainSimple(String domainId) {
		//获取当前domain向下的所有domain列表，并逐个删除
		List<String> domainIdList = new ArrayList<>();

		BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(domainId);
		String processId = domain.getProcessId();

		//添加当前domain本身
		domainIdList.add(domain.getDomainId());

		//查询当前节点是否有下级节点
		BotSentenceDomainExample existChildExample = new BotSentenceDomainExample();
		existChildExample.createCriteria().andProcessIdEqualTo(processId).andParentEqualTo(domain.getDomainName());
		List<BotSentenceDomain> childList = botSentenceDomainMapper.selectByExample(existChildExample);
		if(null != childList && childList.size() > 0) {
			for(BotSentenceDomain temp : childList) {
				domainIdList.add(temp.getDomainId());//添加当前节点的直属下级节点
			}
		}


		if(null == childList || childList.size() == 0) {//当前节点没有下级节点，则删除当前节点本身
			deleteOneDomain(processId, domainId);
		}else {

			while(null != childList && childList.size() > 0) {

				List<String> domainNameList = new ArrayList<>();

				for(BotSentenceDomain temp : childList) {
					if(!domainIdList.contains(temp.getDomainId())) {
						domainIdList.add(temp.getDomainId());
					}

					if(!domainNameList.contains(temp.getDomainName())) {
						domainNameList.add(temp.getDomainName());
					}

				}
				if(domainNameList.size()>0) {
					BotSentenceDomainExample tempExample = new BotSentenceDomainExample();
					tempExample.createCriteria().andProcessIdEqualTo(processId).andParentIn(domainNameList);
					childList = botSentenceDomainMapper.selectByExample(tempExample);
				}else {
					break;
				}
			}

			for(String tempDomainId : domainIdList) {
				logger.info("删除节点: " + tempDomainId);
				deleteOneDomain(processId, tempDomainId);
			}

		}



		return processId;
	}


	private void deleteOneDomain(String processId, String domainId) {
		//删除domain本身
		BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(domainId);
		botSentenceDomainMapper.deleteByPrimaryKey(domainId);

		//删除branch信息
		BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
		branchExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain.getDomainName());
		botSentenceBranchMapper.deleteByExample(branchExample);

		//删除录音信息
		VoliceInfoExample voliceExample = new VoliceInfoExample();
		voliceExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameEqualTo(domain.getDomainName());
		voliceInfoMapper.deleteByExample(voliceExample);

		//删除意图信息
		BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
		intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameEqualTo(domain.getDomainName());
		botSentenceIntentMapper.deleteByExample(intentExample);

		//删除原来其它domain指向当前节点的next数据
		BotSentenceBranchExample branchExample2 = new BotSentenceBranchExample();
		branchExample2.createCriteria().andProcessIdEqualTo(processId).andNextEqualTo(domain.getDomainName());
		botSentenceBranchMapper.deleteByExample(branchExample2);

	}

	@Override
	public void updateProcessState(String processId, String userId) {
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		process.setTestState(Constant.TEST_STATE_UNDEPLOY);
		logger.info("当前话术流程状态: " + process.getState());
		if(Constant.APPROVE_PASS.equals(process.getState()) || Constant.APPROVE_NOTPASS.equals(process.getState()) || Constant.APPROVE_ONLINE.equals(process.getState())
				|| Constant.ERROR.equals(process.getState())) {
			process.setState(Constant.APPROVE_MAEKING);
		}
		process.setLstUpdateTime(new Date(System.currentTimeMillis()));
		process.setLstUpdateUser(userId);
		botSentenceProcessMapper.updateByPrimaryKey(process);
	}

	@Override
	public String getEndDomainName(String processId, String domainId) {
		List<Integer> seqList = new ArrayList<>();
		//查询当前节点是否有下级节点
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		example.createCriteria().andProcessIdEqualTo(processId).andDomainNameLike("%结束_%").andCategoryEqualTo("1");
		List<BotSentenceDomain> list = botSentenceDomainMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			for(BotSentenceDomain temp : list) {
				try {
					seqList.add(new Integer(temp.getDomainName().split("_")[1]));
				}catch(NumberFormatException e) {
					continue;
				}
			}
		}else {
			return "结束_1";
		}
		Integer seq = Collections.max(seqList) + 1;
		return "结束_"+seq;
	}

	@Override
	public FlowInfoVO initFlowInfo(String processId) {
		FlowInfoVO flow = new FlowInfoVO();

		List<FlowNode> nodeList = new ArrayList<>();

		List<FlowEdge> edgeList = new ArrayList<>();
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1");
		List<BotSentenceDomain> levelList = botSentenceDomainMapper.selectByExample(example);

		if(null != levelList && levelList.size() > 0) {
			//获取解释开场白
			for(BotSentenceDomain temp : levelList) {

				FlowNode node = new FlowNode();
				node.setId(temp.getDomainId());
				node.setLabel(temp.getDomainName());
				node.setX(temp.getPositionX());
				node.setY(temp.getPositionY());
				node.setType(temp.getType());//节点类型
				node.setLabel(temp.getDomainName());//设置domain名称

				//查询当前domain的enter话术branch
				BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
				branchExample.createCriteria().andDomainEqualTo(temp.getDomainName()).andProcessIdEqualTo(processId).andBranchNameEqualTo("enter_branch");
				List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchExample);
				if(null != branchList && branchList.size() > 0) {
					BotSentenceBranch branch = branchList.get(0);
					//获取当前branch对应的话术内容
					String resp = branch.getResponse();
					if(StringUtils.isNotBlank(resp) && !"[]".equals(resp)) {
						String[] respArray = resp.substring(1,resp.length()-1).split(",");
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
						node.setContent(volice.getContent());//设置文案内容
						node.setContentUrl(volice.getVoliceUrl());//设置录音URL
					}
				}

				//获取当前domain的挽回话术列表
				List<RefuseBranchVO> refuses = new ArrayList<>();
				BotSentenceBranchExample refuseBranchExample = new BotSentenceBranchExample();
				refuseBranchExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(temp.getDomainName()).andBranchNameEqualTo("refuse_" + temp.getDomainName());
				List<BotSentenceBranch> refuseBranchs = botSentenceBranchMapper.selectByExample(refuseBranchExample);
				if(null != refuseBranchs && refuseBranchs.size() > 0) {
					for(BotSentenceBranch refuseBranch : refuseBranchs) {
						String respStr = refuseBranch.getResponse();

						if(StringUtils.isNotBlank(respStr) && !"[]".equals(respStr.trim()) && respStr.trim().startsWith("[") && respStr.trim().endsWith("]")) {
							String resp = respStr.substring(1,respStr.length()-1);
							long voliceId_new = new Long(resp);
							VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(voliceId_new);
							if(null != volice) {
								RefuseBranchVO refuse = new RefuseBranchVO();
								refuse.setContent(volice.getContent());
								refuse.setVoliceId(volice.getVoliceId().toString());
								refuse.setName(volice.getName());
								refuses.add(refuse);
							}
						}
					}
				}
				node.setRefuses(refuses);
				node.setShape("add_node");
				node.setProcessId(processId);

				nodeList.add(node);

				//查询当前domain的连线branch
				BotSentenceBranchExample lineExample = new BotSentenceBranchExample();
				lineExample.createCriteria().andDomainEqualTo(temp.getDomainName()).andProcessIdEqualTo(processId).andIsShowEqualTo("1");
				lineExample.setOrderByClause(" crt_time");
				List<BotSentenceBranch> lineBranchList = botSentenceBranchMapper.selectByExample(lineExample);
				if(null != lineBranchList && lineBranchList.size() > 0) {

					for(BotSentenceBranch lineBranch : lineBranchList) {
						FlowEdge edge = new FlowEdge();

						//查询关键词库列表
						List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(lineBranch.getBranchId());
						edge.setIntentList(intentList);
						edge.setId(lineBranch.getBranchId());
						edge.setSource(temp.getDomainId());
						edge.setLabel(lineBranch.getLineName());
						edge.setProcessId(processId);
						edge.setType(lineBranch.getType());
						for(BotSentenceDomain parentDomain : levelList) {
							if(parentDomain.getDomainName().equals(lineBranch.getNext())) {
								edge.setTarget(parentDomain.getDomainId());
							}
						}
						edgeList.add(edge);
					}
				}
			}
		}

		flow.setNodes(nodeList);
		flow.setEdges(edgeList);
		flow.setProcessId(processId);

		return flow;
	}

	/**
	 * 保存完整流程信息,包括新建卡片，新建连线，坐标信息
	 */
	@Override
	@Transactional
	public void saveFlow(FlowInfoVO flow, String userId) {
		Map<String, String> map = new HashMap<>();

		//流程图校验
		//1.两个节点之间只能有一条线、自己不能连接自己
		List<String> sources = new ArrayList<>();
		List<String> targets = new ArrayList<>();

		if(null != flow.getEdges() && flow.getEdges().size() > 0) {
			List<String> list = new ArrayList<>();
			for(FlowEdge edge : flow.getEdges()) {
				if(list.contains(edge.getSource()+edge.getTarget()) || list.contains(edge.getTarget() + edge.getSource())) {
					throw new CommonException("分支[" + edge.getLabel() + "]连线重复，两个节点之间只能有一条连线!");
				}

				if(edge.getSource().equals(edge.getTarget())) {
					throw new CommonException("分支[" + edge.getLabel() + "]不能自己连接自己!");
				}

				sources.add(edge.getSource());
				targets.add(edge.getTarget());
				list.add(edge.getSource()+edge.getTarget());
			}
		}


		//3.结束节点不能有分支连线，其它节点不能指向开始节点
		if(null != flow.getNodes() && flow.getNodes().size() > 0) {
			for(FlowNode node : flow.getNodes()) {
				if(Constant.DOMAIN_TYPE_END.equals(node.getType()) && sources.contains(node.getId())) {
					throw new CommonException("节点[" + node.getLabel() + "]是结束节点，不能有分支连线!");
				}

				if(Constant.DOMAIN_TYPE_START.equals(node.getType()) && targets.contains(node.getId())) {
					throw new CommonException("节点[" + node.getLabel() + "]是开始节点，不能指向它!");
				}

				if(Constant.DOMAIN_TYPE_AGENT.equals(node.getType()) && sources.contains(node.getId())) {
					throw new CommonException("节点[" + node.getLabel() + "]是转人工节点，不能有分支连线!");
				}
			}
		}

		if(null != flow.getNodes() && flow.getNodes().size() > 0) {
			//处理卡片信息
			for(FlowNode node : flow.getNodes()) {
				String id = node.getId();
				//判断当前ID是否为前台自动生成，如是，则为新建卡片
				BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(id);
				if(null == domain) {//为新增节点
					//新建卡片
					//node.setDomainName(node.getLabel());
					node.setProcessId(flow.getProcessId());
					BotSentenceDomain newdomain = saveNode(node, userId);
					map.put(node.getId(), newdomain.getDomainId());

				}else {//更新节点
					//更新卡片坐标信息
					node.setProcessId(flow.getProcessId());
					domain = updateNode(node, flow.getDomainId(), userId);
					map.put(node.getId(), domain.getDomainId());
				}

				//保存/更新开场白信息
				//saveStartExplain(node, flow.getDomainId(), userId);

			}
		}

		if(null != flow.getEdges() && flow.getEdges().size() > 0) {
			//处理连接线信息
			for(FlowEdge edge : flow.getEdges()) {
				BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(edge.getId());
				if(null == branch) {//新建连线
					FlowEdge newEdge = new FlowEdge();
					BeanUtil.copyProperties(edge, newEdge);
					newEdge.setSource(map.get(edge.getSource()));
					newEdge.setTarget(map.get(edge.getTarget()));
					saveEdge(flow.getProcessId(), newEdge, userId);
				}else {//更新连线的指向
					updateEdge(flow.getProcessId(), edge, branch, map, flow.getBranchId(), userId);
				}
			}
		}
		updateProcessState(flow.getProcessId(), userId);
	}


	public Map<String, String> getAllMainFlowKeywords(String processId, List<Long> intentIds){
		Map<String, String> map = new HashMap<>();
		//List<String> list = new ArrayList<>();

		List<BusinessAnswerTaskExt> businessAnswerList = businessAnswerTaskService.queryBusinessAnswerListByPage(processId);

		BotSentenceBranchExample example = new BotSentenceBranchExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(example);
		if(null != branchList && branchList.size() > 0) {
			for(BotSentenceBranch branch : branchList) {
				if(StringUtils.isNotBlank(branch.getIntents())) {
					String [] array = branch.getIntents().split(",");
					for(int i = 0 ; i < array.length ; i++) {
						BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(new Long(array[i]));
						if(null != intentIds && intentIds.contains(intent.getId())) {//如果为当前需要过滤的意图，则跳过
							continue;
						}
						List<String> keywordList = BotSentenceUtil.getKeywords(intent.getKeywords());
						System.out.println(keywordList.get(0));
						String []keyword_array = keywordList.get(0).split(",");
						for(int j = 0 ; j < keyword_array.length ; j++) {
							if(StringUtils.isNotBlank(keyword_array[j])) {
								//list.add(keyword_array[j]);
								if(StringUtils.isNotBlank(branch.getLineName())) {
									map.put(keyword_array[j].replace("\"", ""), branch.getDomain() + "(" + branch.getLineName() + ")");
								}else {
									if("一般问题".equals(branch.getDomain())) {
										for(BusinessAnswerTaskExt businessAnswer: businessAnswerList) {
											if(branch.getBranchId().equals(businessAnswer.getBranchId())) {
												map.put(keyword_array[j].replace("\"", ""), branch.getDomain() + businessAnswer.getIndex());
											}
										}

									}else {
										map.put(keyword_array[j].replace("\"", ""), branch.getDomain());
									}

								}

							}
						}
					}
				}
			}
		}
		return map;
	}


	@Override
	public boolean queryTTSStatus(List<VoliceInfoExt> list, String processId) {
		boolean flag = false;

		if(null != list && list.size() > 0) {
			List<String> voliceIds = new ArrayList<>();
			for(VoliceInfoExt volice : list) {
				voliceIds.add(volice.getVoliceId());
			}

			//查询volice非TTS的录音URL为空的数据
			VoliceInfoExample voliceExample = new VoliceInfoExample();
			voliceExample.createCriteria().andProcessIdEqualTo(processId).andVoliceUrlIsNull().andNeedTtsEqualTo(false);
			Long count1 = voliceInfoMapper.countByExample(voliceExample);
			logger.info("一般录音未合成: " + count1);

			BotSentenceTtsTaskExample example = new BotSentenceTtsTaskExample();
			example.createCriteria().andProcessIdEqualTo(processId).andVoliceUrlIsNull().andBusiTypeEqualTo("01").andIsParamEqualTo("02");
			int unfinish_tts_num = botSentenceTtsTaskMapper.countByExample(example);
			logger.info("tts录音未合成: " + unfinish_tts_num);

			//查询备用话术录音URL为空的数据
			BotSentenceTtsBackupExample backExample = new BotSentenceTtsBackupExample();
			backExample.createCriteria().andProcessIdEqualTo(processId).andUrlIsNull();
			int unfinisn_back_num = botSentenceTtsBackupMapper.countByExample(backExample);
			logger.info("备用话术录音未合成: " + unfinisn_back_num);

			int total = count1.intValue() + unfinish_tts_num + unfinisn_back_num;

			logger.info("还剩下" + total + "个未合成...");
			if(total == 0) {
				flag = true;
			}
		}else {
			flag = true;
		}

		return flag;
	}

	@Override
	public void saveSoundType(String processId, String soundType, String userId) {
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		process.setSoundType(soundType);
		process.setState(Constant.APPROVE_MAEKING);
		process.setLstUpdateTime(new Date(System.currentTimeMillis()));
		process.setLstUpdateUser(userId);
		botSentenceProcessMapper.updateByPrimaryKey(process);

		//如果当前状态为审批通过、已上线，则把状态修改为“制作中”
		this.updateProcessState(processId, userId);
	}

	@Override
	public BotSentenceProcess queryBotsentenceProcessInfo(String processId) {
		return botSentenceProcessMapper.selectByPrimaryKey(processId);
	}


	/**
	 * 根据branchI获取意图信息
	 */
	@Override
	public BotSentenceIntent queryKeywordsListByBranchId(String branchId) {
		BotSentenceIntent result = new BotSentenceIntent();
		BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);

		if("结束_在忙".equals(branch.getDomain())) {
			//查询在忙domain作为关键字
			BotSentenceBranchExample example1 = new BotSentenceBranchExample();
			example1.createCriteria().andProcessIdEqualTo(branch.getProcessId()).andDomainEqualTo("在忙").andBranchNameEqualTo("negative");
			List<BotSentenceBranch> list1 = botSentenceBranchMapper.selectByExample(example1);
			if(null != list1 && list1.size() > 0) {
				branch = list1.get(0);
			}
		}


		if(null != branch && StringUtils.isNotBlank(branch.getIntents())) {
			String intentId=branch.getIntents().split(",")[0].trim();
			if(intentId!=null){
				BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intentId));
				if(null != intent) {
					result.setId(intent.getId());
					String keywords=intent.getKeywords();
					if(StringUtils.isNotBlank(keywords)) {
						List<String> keywordList = BotSentenceUtil.getKeywords(keywords);
						if(null != keywordList && keywordList.size() > 0) {
							result.setKeywords(keywordList.get(0).replace("\"", ""));
						}
					}
				}
			}
		}
		
		return result;
	}

	@Override
	public List<BotSentenceProcess> queryBotSentenceProcessListByAccountNo(String accountNo) {

		if(StringUtils.isBlank(accountNo)) {
			throw new CommonException("用户账号为空");
		}
		BotSentenceProcessExample example = new BotSentenceProcessExample();
		Criteria criteria = example.createCriteria();
		List<String> states = new ArrayList<>();
		states.add(Constant.APPROVE_MAEKING);
		//states.add(Constant.APPROVE_PASS);
		states.add(Constant.APPROVE_NOTPASS);
		states.add(Constant.APPROVE_ONLINE);
		criteria.andAccountNoEqualTo(accountNo).andStateIn(states);
		example.setOrderByClause(" crt_time desc");
		
		return botSentenceProcessMapper.selectByExample(example);
	
	}

	/**
	 * 生成TTS录音信息
	 */
	@Override
	//@Transactional
	public void generateTTS(List<VoliceInfoExt> list2, String processId, String userId, String model) {

		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		if(null == process){
			throw new CommonException("话术不存在！");
		}

		if(StringUtils.isBlank(process.getSoundType())){
			model = TtsModelEnum.MH.getKey();
		}else {
			model = process.getSoundType();
		}


		VoliceInfoExample example = new VoliceInfoExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<VoliceInfo> list = voliceInfoMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			for(VoliceInfo temp : list) {
				if(StringUtils.isBlank(temp.getContent())) {
					logger.info("当前文案内容为空，不需要TTS合成，跳过...");
					continue;
				}
				
				boolean isNeedTts = botSentenceTtsService.validateContainParam(temp.getContent());
				if(isNeedTts) {
					logger.info("当前文案需要TTS合成，需要拆分文案生成多个TTS任务");
					logger.info("更新当前录音的url为空");
					VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(temp.getVoliceId()));
					volice.setVoliceUrl(null);
					voliceInfoMapper.updateByPrimaryKey(volice);
					
					
					BotSentenceTtsTaskExample ttsExample = new BotSentenceTtsTaskExample();
					ttsExample.createCriteria()
							.andProcessIdEqualTo(processId)
							.andBusiIdEqualTo(temp.getVoliceId().toString())
							.andIsParamEqualTo(Constant.IS_PARAM_FALSE);
					List<BotSentenceTtsTask> tasklist = botSentenceTtsTaskMapper.selectByExample(ttsExample);
					if(null != tasklist && tasklist.size() > 0) {
						for(BotSentenceTtsTask ttsTask : tasklist) {
							botSentenceTtsService.saveAndSentTTS(ttsTask, processId, true, userId, model);
						}
					}
				}else {
					BotSentenceTtsTask ttsTask = new BotSentenceTtsTask();
					ttsTask.setContent(temp.getContent());
					ttsTask.setProcessId(processId);
					ttsTask.setBusiId(temp.getVoliceId().toString());
					ttsTask.setBusiType("03");
					
					botSentenceTtsService.saveAndSentTTS(ttsTask, processId, isNeedTts, userId, model);
				}
		}
		
		//备用文案TTS生成
		BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
		backupExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceTtsBackup> backupList = botSentenceTtsBackupMapper.selectByExample(backupExample);
		if(null != backupList && backupList.size() > 0) {
			for(BotSentenceTtsBackup backup : backupList) {
				BotSentenceTtsTask ttsTask = new BotSentenceTtsTask();
				
				BotSentenceTtsTaskExample example2 = new BotSentenceTtsTaskExample();
				example2.createCriteria().andProcessIdEqualTo(processId).andBusiIdEqualTo(backup.getBackupId());
				List<BotSentenceTtsTask> list3 = botSentenceTtsTaskMapper.selectByExample(example2);
				if(null != list3 && list3.size() > 0) {
					ttsTask = list3.get(0);
				}
				
				ttsTask.setBusiId(backup.getBackupId());
				ttsTask.setBusiType("02");
				ttsTask.setContent(backup.getContent());
				botSentenceTtsService.saveAndSentTTS(ttsTask, processId, false, userId, model);
			}
		}
	}
		//更新话术流程状态
		this.updateProcessState(processId, userId);
	}
	
	
	/**
	 * 根据流程号获取所有domain
	 * @param processId
	 * @return
	 */
	@Override
	public List<BotSentenceDomain> getAllDomainList(String processId) {
		BotSentenceDomainExample domaninExample = new BotSentenceDomainExample();
		domaninExample.createCriteria().andProcessIdEqualTo(processId);
		domaninExample.setOrderByClause(" category, position_y, position_x");
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domaninExample);
		return domainList;
	}
	
	@Override
	public List<CommonDialogVO> queryCommonDialogSimple(String processId) {
		List<CommonDialogVO> resultList = new ArrayList<>();
		
		List<CommonDialogVO> resultListUnRepeat = new ArrayList<>();
		
		//重复
		CommonDialogVO repeat = new CommonDialogVO();
		repeat.setHuashu("重复上一句");//话术
		repeat.setLuoji("重复3次后走邀约失败");//逻辑
		repeat.setYujin("重复");//语境
		repeat.setTitle("重复");
		resultList.add(repeat);
		
		
		//失败邀约放到全局语境里来
		BotSentenceBranchExample example5 = new BotSentenceBranchExample();
		example5.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("邀约").andBranchNameEqualTo("failed_enter_branch");
		List<BotSentenceBranch> list5 = botSentenceBranchMapper.selectByExample(example5);
		if(null != list5 && list5.size() > 0) {
			BotSentenceBranch branch = list5.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				if(null != respArray && respArray.length > 0) {
					for(String resp : respArray) {
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(resp));
						vo.setHuashu(volice.getContent());//话术
						vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
						vo.setYujin("失败邀约");//语境
						vo.setTitle("失败邀约");
						vo.setBranchId(branch.getBranchId());
						vo.setVoliceId(volice.getVoliceId());
						vo.setTemplateId(branch.getTemplateId());
						vo.setProcessId(processId);
						vo.setBranchName(branch.getBranchName());
						vo.setVoliceName(volice.getName());
						vo.setFlag(volice.getFlag());
						
						//查询关键词库列表
						List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
						vo.setIntentList(intentList);
						
						resultList.add(vo);
					}
				}
			}
		}
		
		//失败结束放到全局语境里来
		BotSentenceBranchExample example6 = new BotSentenceBranchExample();
		example6.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("结束").andBranchNameEqualTo("failed_enter_branch");
		List<BotSentenceBranch> list6 = botSentenceBranchMapper.selectByExample(example6);
		if(null != list6 && list6.size() > 0) {
			BotSentenceBranch branch = list6.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				if(null != respArray && respArray.length > 0) {
					for(String resp : respArray) {
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(resp));
						vo.setHuashu(volice.getContent());//话术
						vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
						vo.setYujin("失败结束");//语境
						vo.setTitle("失败结束");
						vo.setBranchId(branch.getBranchId());
						vo.setVoliceId(volice.getVoliceId());
						vo.setTemplateId(branch.getTemplateId());
						vo.setProcessId(processId);
						vo.setBranchName(branch.getBranchName());
						vo.setVoliceName(volice.getName());
						vo.setFlag(volice.getFlag());
						//查询关键词库列表
						List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
						vo.setIntentList(intentList);
						resultList.add(vo);
					}
				}
			}
		}
		
		List<String> domains = new ArrayList<>();
		
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("3");
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(example);
		if(null != domainList && domainList.size() > 0) {
			for(BotSentenceDomain domain : domainList) {
				domains.add(domain.getDomainName());
			}
		}
		
		VoliceInfoExample voliceInfoExample = new VoliceInfoExample();
		List<String> ignoreDomainList = new ArrayList<>();
		ignoreDomainList.add("不清楚");
		ignoreDomainList.add("不知道");
		ignoreDomainList.add("等待");
		ignoreDomainList.add("用户不清楚");
		ignoreDomainList.add("自由介绍");
		
		ignoreDomainList.add("一般问题");
		//ignoreDomainList.add("解释开场白");
		
		
		//查询其它
		BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
		branchExample.createCriteria().andProcessIdEqualTo(processId).andDomainIn(domains).andDomainNotIn(ignoreDomainList);
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchExample);
		if(null != branchList && branchList.size() > 0) {
			for(BotSentenceBranch branch : branchList) {
				//查询关键词库列表
				List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
				
				if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
						&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
					String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
					if(null != respArray && respArray.length > 0) {
						for(String resp : respArray) {
							VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(resp));

							if(Constant.VOLICE_TYPE_REFUSE.equals(volice.getType())) {
								continue;
							}
							
							CommonDialogVO vo = new CommonDialogVO();
							vo.setHuashu(volice.getContent());//话术
							vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
							vo.setYujin(volice.getDomainName());//语境
							vo.setLuoji("触发\"" + volice.getDomainName() +"\"流程");//逻辑
							vo.setTitle(volice.getDomainName());
							vo.setBranchId(branch.getBranchId());
							vo.setVoliceId(volice.getVoliceId());
							vo.setTemplateId(volice.getTemplateId());
							vo.setProcessId(processId);
							vo.setBranchName(branch.getBranchName());
							vo.setVoliceName(volice.getName());
							vo.setFlag(volice.getFlag());
							vo.setIntentList(intentList);
							resultList.add(vo);
						}
					}
				}
			}
		}
		//根据录音ID去重
		if(resultList.size() > 1) {
			for(int i = 0 ; i < resultList.size() ; i++) {
				boolean exist = false;
				for(CommonDialogVO vo : resultListUnRepeat) {
					if(vo.getVoliceId() == resultList.get(i).getVoliceId()) {
						exist = true;
						break;
					}
				}
				if(!exist) {
					resultListUnRepeat.add(resultList.get(i));
				}
			}
		}
		
		return resultListUnRepeat;
	}
	
	/**
	 * 根据流程号获取主流程domain
	 * @param processId
	 * @return
	 */
	@Override
	public List<BotSentenceDomain> getDomainList(String processId) {
		//List<BotSentenceDomain> result = new ArrayList<>();
		BotSentenceDomainExample domaninExample = new BotSentenceDomainExample();
		domaninExample.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo(Constant.CATEGORY_TYPE_1);
		domaninExample.setOrderByClause(" position_y, position_x");
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domaninExample);
		
		/*for(BotSentenceDomain domain: domainList) {
			result.add(domain);
			if(Constant.DOMAIN_TYPE_START.equals(domain.getType())) {
				BotSentenceDomain startExplainDomain = this.getDomain(processId, "解释开场白");
				if(null != startExplainDomain) {
					result.add(startExplainDomain);
				}
			}
		}*/
		
		return domainList;
	}
	
	/**
	 * 获取当前domain的special分支
	 * @param processId
	 * @param domain
	 * @return
	 */
	public BotSentenceBranch getSpecialBranch(String processId, String domain) {
		BotSentenceBranchExample mainExample = new BotSentenceBranchExample();
		mainExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain).andBranchNameEqualTo("special");
		List<BotSentenceBranch> mainBranchList = botSentenceBranchMapper.selectByExample(mainExample);
		if(null != mainBranchList && mainBranchList.size() > 0) {
			return mainBranchList.get(0);
		}
		return null;
	}

	/**
	 * 根据账号查询行业列表（树结构）
	 */
	@Override
	public List<BotSentenceTemplateTradeVO> queryIndustryListByAccountNo(String accountNo, String userId) {
		List<String> templateIdList = new ArrayList<>();
		ReturnData<SysOrganization> org = iAuth.getOrgByUserId(new Long(userId));
		String orgCode=org.getBody().getCode();
		ReturnData<List<String>> returnData= orgService.getIndustryByOrgCode(orgCode);
		templateIdList = returnData.getBody();
		if(null != templateIdList && templateIdList.size() > 0) {
			List<BotSentenceTemplateTradeVO> list = this.queryTradeListByTemplateIdList(templateIdList);
			return list;
		}
		return null;
	}
	
	/**
	 * 暂时先这样处理，后续优化
	 * @return
	 */
	@Override
	public List<BotSentenceTemplateTradeVO> queryAllIndustryList() {
		List<BotSentenceTemplateTradeVO> results = new ArrayList<>();
		BotSentenceTradeExample example = new BotSentenceTradeExample();
		example.createCriteria().andLevelEqualTo(1);
		List<BotSentenceTrade> industryList = botSentenceTradeMapper.selectByExample(example);
		if(null != industryList && industryList.size() > 0) {
			for(BotSentenceTrade industry : industryList) {
				BotSentenceTemplateTradeVO vo = new BotSentenceTemplateTradeVO();
				vo.setValue(industry.getIndustryId());
				vo.setLabel(industry.getIndustryName());
				vo.setLevel(industry.getLevel());
				List<BotSentenceTrade> childIndustryList1 = getChildIndustryList(industry.getIndustryId());
				if(null != childIndustryList1 && childIndustryList1.size() > 0) {
					List<BotSentenceTemplateTradeVO> childs1 = new ArrayList<>();
					for(BotSentenceTrade child1 : childIndustryList1) {
						BotSentenceTemplateTradeVO childVo = new BotSentenceTemplateTradeVO();
						childVo.setValue(child1.getIndustryId());
						childVo.setLabel(child1.getIndustryName());
						childVo.setLevel(child1.getLevel());
						childs1.add(childVo);
						
						List<BotSentenceTrade> childIndustryList2 = getChildIndustryList(child1.getIndustryId());
						if(null != childIndustryList2 && childIndustryList2.size() > 0) {
							List<BotSentenceTemplateTradeVO> childs2 = new ArrayList<>();
							for(BotSentenceTrade child2 : childIndustryList2) {
								BotSentenceTemplateTradeVO childVo2 = new BotSentenceTemplateTradeVO();
								childVo2.setValue(child2.getIndustryId());
								childVo2.setLabel(child2.getIndustryName());
								childVo2.setLevel(child2.getLevel());
								childs2.add(childVo2);
							}
							childVo.setChildren(childs2);
						}
					}
					vo.setChildren(childs1);
				}
				results.add(vo);
			}
		}
		return results;
		
	}
	
	
	private List<BotSentenceTrade> getChildIndustryList(String parentIndustryId) {
		BotSentenceTradeExample childExample = new BotSentenceTradeExample();
		childExample.createCriteria().andParentIdEqualTo(parentIndustryId);
		List<BotSentenceTrade> list = botSentenceTradeMapper.selectByExample(childExample);
		return list;
	}
	
	public BotSentenceTrade getBotSentenceTrade(String industryId) {
		BotSentenceTradeExample example = new BotSentenceTradeExample();
		example.createCriteria().andIndustryIdEqualTo(industryId);
		List<BotSentenceTrade> list = botSentenceTradeMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	
	private Map<String, String> getSameBranchKeywords(String processId, String domain, String branchId, String type) {
		BotSentenceBranchExample branchexample = new BotSentenceBranchExample();
		branchexample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(domain).andBranchIdNotEqualTo(branchId);
		List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(branchexample);
		Map<String, String> keywords = new HashMap<>(); 
		if(Constant.DOMAIN_TYPE_START.equals(type)) {
			BotSentenceBranchExample startExample = new BotSentenceBranchExample();
			startExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain).andNextEqualTo("解释开场白");
			List<BotSentenceBranch> startList = botSentenceBranchMapper.selectByExample(startExample);
			list.addAll(startList);
		}
		
		
		if(null != list && list.size() > 0) {
			for(BotSentenceBranch branch : list) {
				String intents = branch.getIntents();
				if(StringUtils.isNotBlank(intents)) {
					String [] array = intents.split(",");
					for(int i = 0 ; i < array.length ; i++) {
						BotSentenceIntent temp = botSentenceIntentMapper.selectByPrimaryKey(new Long(array[i]));
						if(null != temp) {
							String[] keywordList = BotSentenceUtil.getKeywords(temp.getKeywords()).get(0).replace("\"", "").split(",");
							if(null != keywordList && keywordList.length > 0) {
								for(String str : keywordList) {
									str = str.replace("\"", "");
									if(StringUtils.isNotBlank(branch.getLineName())) {
										keywords.put(str, branch.getLineName());
									}else {
										keywords.put(str, branch.getNext());
									}
								}
							}
						}
						
					}
				}
			}
		}
		return keywords;
	}
	
	@Override
	public List<BotSentenceProcess> getTemplateByOrgCode(String orgCode) {

		BotSentenceProcessExample example = new BotSentenceProcessExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrgCodeLike(orgCode+"%");
		criteria.andStateNotEqualTo("99");
		return botSentenceProcessMapper.selectByExample(example);
	}
	
	
	@Override
	public int countTemplateByOrgCode(String orgCode) {
		BotSentenceProcessExample example = new BotSentenceProcessExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrgCodeLike(orgCode+"%");
		criteria.andStateNotEqualTo("99");
		int num = botSentenceProcessMapper.countByExample(example);
		return num;
	}

	@Override
	public List<BotSentenceTemplateTradeVO> queryIndustryListByOrgCode(String orgCode) {

		List<BotSentenceTemplateTradeVO> results = new ArrayList<>();
		
		List<String> templateIdList = new ArrayList<>();
		List<String> parentIndustryIdList = new ArrayList<>();
		
		ReturnData<List<String>> returnData= orgService.getIndustryByOrgCode(orgCode);
		templateIdList = returnData.getBody();
		
		for(String templateId : templateIdList) {
			//根据模板获取行业
			BotSentenceTemplate template = botSentenceTemplateService.getBotSentenceTemplateByTemplateId(templateId);
			if(null != template) {
				//获取一级行业
				parentIndustryIdList.add(template.getIndustryId().substring(0, 2));
			}
		}
		
		
		for(String industryId : parentIndustryIdList) {
			//获取上级行业
			BotSentenceTrade parentTrade = getBotSentenceTrade(industryId);
			
			BotSentenceTemplateTradeVO parentVo = new BotSentenceTemplateTradeVO();
			parentVo.setValue(parentTrade.getIndustryId());
			parentVo.setLabel(parentTrade.getIndustryName());
			parentVo.setLevel(parentTrade.getLevel());
			//获取下级行业
			List<BotSentenceTrade> trades = getChildIndustryList(parentTrade.getIndustryId());
			if(null != trades && trades.size() > 0) {
				List<BotSentenceTemplateTradeVO> tradeVOList = new ArrayList<>();
				for(BotSentenceTrade trade : trades) {
					BotSentenceTemplateTradeVO tradeVO = new BotSentenceTemplateTradeVO();
					tradeVO.setValue(trade.getIndustryId());
					tradeVO.setLabel(trade.getIndustryName());
					tradeVO.setLevel(trade.getLevel());
					List<BotSentenceTrade> childIndustryList = getChildIndustryList(trade.getIndustryId());
					if(null != childIndustryList && childIndustryList.size() > 0) {
						List<BotSentenceTemplateTradeVO> childVOList = new ArrayList<>();
						for(BotSentenceTrade childTrade : childIndustryList) {
							BotSentenceTemplateTradeVO childVo = new BotSentenceTemplateTradeVO();
							childVo.setValue(childTrade.getIndustryId());
							childVo.setLabel(childTrade.getIndustryName());
							childVo.setLevel(childTrade.getLevel());
							childVOList.add(childVo);
						}
						tradeVO.setChildren(childVOList);
					}
					tradeVOList.add(tradeVO);
				}
				parentVo.setChildren(tradeVOList);
			}
			results.add(parentVo);
		}
		return results;
	}
	
	

	
	@Override
	public List<BotSentenceProcess> getTemplateBySelf(String accountNo) {
		System.out.println();
		return botSentenceProcessExtMapper.getTemplateBySelf(accountNo);
	}
	
	@Override
	public List<BotSentenceProcess> getTemplateById(String templateId) {
		return botSentenceProcessExtMapper.getTemplateById(templateId);
	}
	
	
	public List<Object> getAvailableTemplateBySelf(String accountNo) {
		return botSentenceProcessExtMapper.getAvailableTemplateBySelf(accountNo);
	}

	@Override
	public BotSentenceProcess getBotsentenceProcessByTemplateId(String templateId) {
		BotSentenceProcessExample example = new BotSentenceProcessExample();
		example.createCriteria().andTemplateIdEqualTo(templateId);
		List<BotSentenceProcess> list = botSentenceProcessMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	

	@Override
	public void generateTTSCallback(String id, String url) {
		logger.info("接收TTS合成回调参数: " + id);
		logger.info("接收TTS合成回调参数: " + url);
		if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(url)) {
			BotSentenceTtsTask ttsTask = botSentenceTtsTaskMapper.selectByPrimaryKey(new Long(id));
			if(null != ttsTask) {
				ttsTask.setVoliceUrl(url);
				ttsTask.setStatus(Constant.TTS_FINISH);
				botSentenceTtsTaskMapper.updateByPrimaryKey(ttsTask);
				
				if("01".equals(ttsTask.getBusiType()) || "03".equals(ttsTask.getBusiType())) {
					//更新volice url
					VoliceInfo volice = voliceServiceImpl.getVoliceInfo(new Long(ttsTask.getBusiId()));
					if(!url.equals(volice.getVoliceUrl())) {
						//判断是否合成录音，如果是合成录音，则不更新URL
						if(botSentenceTtsService.validateContainParam(volice.getContent())){
							logger.info("当前录音需要TTS合成，不需要更新录音 URL");
							volice.setVoliceUrl(null);
							volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
							volice.setNeedTts(true);
							volice.setLstUpdateUser("tts");
						}else {
							volice.setVoliceUrl(url);
							volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
							volice.setLstUpdateUser("tts");
							volice.setNeedTts(false);
						}
						voliceInfoMapper.updateByPrimaryKey(volice);
						//voliceInfoMapper.updateByPrimaryKeySelective(volice);
						logger.info("更新TTS合成URL成功...");
					}
				}else if("02".equals(ttsTask.getBusiType())) {
					//更新backup url
					BotSentenceTtsBackup backup = botSentenceTtsBackupMapper.selectByPrimaryKey(ttsTask.getBusiId());
					if(!url.equals(backup.getUrl())) {
						backup.setUrl(url);
						backup.setLstUpdateTime(new Date(System.currentTimeMillis()));
						backup.setLstUpdateUser("tts");
						botSentenceTtsBackupMapper.updateByPrimaryKeySelective(backup);
						logger.info("更新备用文案TTS合成URL成功...");
					}
				}
				
			}
		}
	}
	
	
	public Map<String, String> getAllSelectKeywords(String processId, List<Long> intentIds){
		Map<String, String> map = new HashMap<>();
		List<String> list = new ArrayList<>();
		list.add("在忙");
		list.add("投诉");
		list.add("拒绝");
		list.add("用户不清楚");
		List<BotSentenceBranch> allBranchList = new ArrayList<>();
		
		BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
		branchExample.createCriteria().andProcessIdEqualTo(processId).andDomainIn(list).andBranchNameEqualTo("negative");
		List<BotSentenceBranch> branchList1 = botSentenceBranchMapper.selectByExample(branchExample);
		if(null != branchList1 && branchList1.size() > 0) {
			allBranchList.addAll(branchList1);
		}
		
		//号码过滤取special
		BotSentenceBranchExample branchExample2 = new BotSentenceBranchExample();
		branchExample2.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("号码过滤").andBranchNameEqualTo("special");
		List<BotSentenceBranch> branchList2 = botSentenceBranchMapper.selectByExample(branchExample2);
		if(null != branchList2 && branchList2.size() > 0) {
			allBranchList.addAll(branchList2);
		}
		
		//一般问题分支
		BotSentenceBranchExample branchExample3 = new BotSentenceBranchExample();
		branchExample3.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("一般问题");
		List<BotSentenceBranch> branchList3 = botSentenceBranchMapper.selectByExample(branchExample3);
		if(null != branchList3 && branchList3.size() > 0) {
			allBranchList.addAll(branchList3);
		}
		
		List<BusinessAnswerTaskExt> businessAnswerList = businessAnswerTaskService.queryBusinessAnswerListByPage(processId);
		
		if(null != allBranchList && allBranchList.size() > 0) {
			for(BotSentenceBranch branch : allBranchList) {
				if(StringUtils.isNotBlank(branch.getIntents())) {
					String [] array = branch.getIntents().split(",");
					for(int i = 0 ; i < array.length ; i++) {
						BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(new Long(array[i]));
						if(null != intentIds && intentIds.contains(new Long(array[i]))) {//如果为当前需要过滤的意图，则跳过
							continue;
						}
						
						List<String> keywordList = BotSentenceUtil.getKeywords(intent.getKeywords());
						System.out.println(keywordList.get(0));
						String []keyword_array = keywordList.get(0).split(",");
						for(int j = 0 ; j < keyword_array.length ; j++) {
							if(StringUtils.isNotBlank(keyword_array[j])) {
								//list.add(keyword_array[j]);
								if(StringUtils.isNotBlank(branch.getLineName())) {
									map.put(keyword_array[j].replace("\"", ""), branch.getDomain() + "(" + branch.getLineName() + ")");
								}else {
									if("一般问题".equals(branch.getDomain())) {
										for(BusinessAnswerTaskExt businessAnswer: businessAnswerList) {
											if(branch.getBranchId().equals(businessAnswer.getBranchId())) {
												map.put(keyword_array[j].replace("\"", ""), branch.getDomain() + businessAnswer.getIndex());
											}
										}
										
									}else {
										map.put(keyword_array[j].replace("\"", ""), branch.getDomain());
									}
									
								}
								
							}
						}
					}
				}
			}
		}
		
		return map;
	}

	@Override
	public void saveTrade(String industryName, String industryId, String userId) {
		
			
		BotSentenceTrade newTrade = new BotSentenceTrade();
		if(StringUtils.isNotBlank(industryId)) {
			//校验当前行业是否已存在
			BotSentenceTradeExample exist = new BotSentenceTradeExample();
			exist.createCriteria().andParentIdEqualTo(industryId).andIndustryNameEqualTo(industryName);
			int count = botSentenceTradeMapper.countByExample(exist);
			if(count > 0) {
				throw new CommonException("当前行业已存在!");
			}
			
			BotSentenceTradeExample example = new BotSentenceTradeExample();
			example.createCriteria().andIndustryIdEqualTo(industryId);
			List<BotSentenceTrade> list = botSentenceTradeMapper.selectByExample(example);
			BotSentenceTrade trade = list.get(0);
			
			//获取行业编号
			BotSentenceTradeExample example1 = new BotSentenceTradeExample();
			example1.createCriteria().andParentIdEqualTo(industryId);
			example1.setOrderByClause(" industry_id desc");
			List<BotSentenceTrade> list2 = botSentenceTradeMapper.selectByExample(example1);
			String newIndustryId = null;
			if(null != list2 && list2.size() > 0) {
				BotSentenceTrade max = list2.get(0);
				newIndustryId = max.getIndustryId().substring(max.getIndustryId().length() -2, max.getIndustryId().length());
				
				int newIndustry = new Integer(newIndustryId) + 1;
				if(newIndustry > 9) {
					newIndustryId = industryId + newIndustry;
					//newTrade.setIndustryId(industryId + newIndustry) ;
				}else {
					newIndustryId = industryId + "0" + newIndustry;
					//newTrade.setIndustryId(industryId + "0" + newIndustry) ;
				}
				
			}else {
				newIndustryId = industryId + "01";
			}
			
			newTrade.setIndustryId(newIndustryId);
			
			newTrade.setIndustryName(industryName);
			newTrade.setParentId(trade.getIndustryId());
			newTrade.setParentName(trade.getIndustryName());
			newTrade.setLevel(trade.getLevel() + 1);
			newTrade.setCrtTime(new Date(System.currentTimeMillis()));
			newTrade.setCrtUser(userId);
			botSentenceTradeMapper.insert(newTrade);
		}else {
			BotSentenceTradeExample example1 = new BotSentenceTradeExample();
			example1.createCriteria().andLevelEqualTo(1);
			example1.setOrderByClause(" industry_id desc");
			List<BotSentenceTrade> list2 = botSentenceTradeMapper.selectByExample(example1);
			BotSentenceTrade max = list2.get(0);
			int newIndustry = new Integer(max.getIndustryId()) + 1;
			if(newIndustry > 9) {
				newTrade.setIndustryId(newIndustry + "") ;
			}else {
				newTrade.setIndustryId("0" + newIndustry) ;
			}
			
			newTrade.setIndustryName(industryName);
			newTrade.setLevel(1);
			newTrade.setCrtTime(new Date(System.currentTimeMillis()));
			newTrade.setCrtUser(userId);
			botSentenceTradeMapper.insert(newTrade);
		}
			
	}

	@Override
	public List<BotSentenceTemplateTradeVO> queryTradeListByTradeIdList(List<String> tradeIdList) {

		List<BotSentenceTemplateTradeVO> results = new ArrayList<>();
		BotSentenceTradeExample example = new BotSentenceTradeExample();
		example.createCriteria().andLevelEqualTo(1);
		List<BotSentenceTrade> industryList = botSentenceTradeMapper.selectByExample(example);
		if(null != industryList && industryList.size() > 0) {
			for(BotSentenceTrade industry : industryList) {
				boolean flag = false;
				BotSentenceTemplateTradeVO vo = new BotSentenceTemplateTradeVO();
				vo.setValue(industry.getIndustryId());
				vo.setLabel(industry.getIndustryName());
				vo.setLevel(industry.getLevel());
				List<BotSentenceTrade> childIndustryList1 = getChildIndustryList(industry.getIndustryId());
				if(null != childIndustryList1 && childIndustryList1.size() > 0) {
					List<BotSentenceTemplateTradeVO> childs1 = new ArrayList<>();
					for(BotSentenceTrade child1 : childIndustryList1) {
						if(null != tradeIdList && tradeIdList.size() > 0 && !tradeIdList.contains(child1.getIndustryId())) {
							continue;
						}
						flag = true;
						BotSentenceTemplateTradeVO childVo = new BotSentenceTemplateTradeVO();
						childVo.setValue(child1.getIndustryId());
						childVo.setLabel(child1.getIndustryName());
						childVo.setLevel(child1.getLevel());
						childs1.add(childVo);
						
						List<BotSentenceTrade> childIndustryList2 = getChildIndustryList(child1.getIndustryId());
						if(null != childIndustryList2 && childIndustryList2.size() > 0) {
							List<BotSentenceTemplateTradeVO> childs2 = new ArrayList<>();
							for(BotSentenceTrade child2 : childIndustryList2) {
								BotSentenceTemplateTradeVO childVo2 = new BotSentenceTemplateTradeVO();
								childVo2.setValue(child2.getIndustryId());
								childVo2.setLabel(child2.getIndustryName());
								childVo2.setLevel(child2.getLevel());
								childs2.add(childVo2);
							}
							childVo.setChildren(childs2);
						}
					}
					
					vo.setChildren(childs1);
				}
				if(flag) {
					results.add(vo);
				}
				
			}
		}
		return results;
	}

	@Override
	public List<BotSentenceTemplateTradeVO> queryTradeListByTemplateIdList(List<String> templateIdList) {
		List<BotSentenceTemplateTradeVO> results = new ArrayList<>();
		
		List<String> parentIndustryIdList = new ArrayList<>();
		List<String> industryIdList2 = new ArrayList<>();
		List<String> industryIdList3 = new ArrayList<>();
		
		if(null == templateIdList || templateIdList.size() == 0) {
			return null;
		}
		for(String templateId : templateIdList) {
			//根据模板获取行业
			BotSentenceTemplate template = botSentenceTemplateService.getBotSentenceTemplateByTemplateId(templateId);
			if(null != template) {
				//获取一级行业
				if(!parentIndustryIdList.contains(template.getIndustryId().substring(0, 2))) {
					parentIndustryIdList.add(template.getIndustryId().substring(0, 2));
				}
				//获取二级行业
				if(!industryIdList2.contains(template.getIndustryId().substring(0, 4))) {
					industryIdList2.add(template.getIndustryId().substring(0, 4));
				}
				//获取三级行业
				if(!industryIdList3.contains(template.getIndustryId())) {
					industryIdList3.add(template.getIndustryId());
				}
			}
		}
		
		
		for(String industryId : parentIndustryIdList) {
			//获取上级行业
			BotSentenceTrade parentTrade = getBotSentenceTrade(industryId);
			
			BotSentenceTemplateTradeVO parentVo = new BotSentenceTemplateTradeVO();
			parentVo.setValue(parentTrade.getIndustryId());
			parentVo.setLabel(parentTrade.getIndustryName());
			parentVo.setLevel(parentTrade.getLevel());
			//获取下级行业
			List<BotSentenceTrade> trades = getChildIndustryList(parentTrade.getIndustryId());
			if(null != trades && trades.size() > 0) {
				List<BotSentenceTemplateTradeVO> tradeVOList = new ArrayList<>();
				for(BotSentenceTrade trade : trades) {
					if(!industryIdList2.contains(trade.getIndustryId())) {
						continue;
					}
					BotSentenceTemplateTradeVO tradeVO = new BotSentenceTemplateTradeVO();
					tradeVO.setValue(trade.getIndustryId());
					tradeVO.setLabel(trade.getIndustryName());
					tradeVO.setLevel(trade.getLevel());
					List<BotSentenceTrade> childIndustryList = getChildIndustryList(trade.getIndustryId());
					if(null != childIndustryList && childIndustryList.size() > 0) {
						List<BotSentenceTemplateTradeVO> childVOList = new ArrayList<>();
						for(BotSentenceTrade childTrade : childIndustryList) {
							if(!industryIdList3.contains(childTrade.getIndustryId())) {
								continue;
							}
							BotSentenceTemplateTradeVO childVo = new BotSentenceTemplateTradeVO();
							childVo.setValue(childTrade.getIndustryId());
							childVo.setLabel(childTrade.getIndustryName());
							childVo.setLevel(childTrade.getLevel());
							childVOList.add(childVo);
							//查询模板
							List<BotSentenceTemplate> templateList = botSentenceTemplateService.queryTemplateByIndustry(childTrade.getIndustryId());
							if(null != templateList && templateList.size() > 0) {
								List<BotSentenceTemplateTradeVO> templateVOList = new ArrayList<>();
								for(BotSentenceTemplate temp : templateList) {
									if(templateIdList.contains(temp.getTemplateId())) {
										BotSentenceTemplate tempalte = botSentenceTemplateService.getBotSentenceTemplateByTemplateId(temp.getTemplateId());
										BotSentenceTemplateTradeVO templateVo = new BotSentenceTemplateTradeVO();
										templateVo.setValue(tempalte.getTemplateId());
										templateVo.setLabel(tempalte.getTemplateName());
										templateVOList.add(templateVo);
									}
								}
								childVo.setChildren(templateVOList);
							}
						}
						tradeVO.setChildren(childVOList);
					}
					tradeVOList.add(tradeVO);
				}
				parentVo.setChildren(tradeVOList);
			}
			results.add(parentVo);
		}
		return results;
	
	}

	@Override
	public void updateValiableDomainName(String processId, String domainName, String newDomainName, String type) {
		BotSentenceOptions options = botsentenceVariableService.getOptionsByProcessId(processId);
		if("delete".equals(type)) {
			//无法被打断的流程
			if(StringUtils.isNotBlank(options.getNonInterruptable())) {
				List<String> list = BotSentenceUtil.StringToList(options.getNonInterruptable());
				if(list.contains(domainName)) {
					list.remove(domainName);
					options.setNonInterruptable(BotSentenceUtil.listToString(list));
					botSentenceOptionsMapper.updateByPrimaryKey(options);
				}
			}
			
			//用户回复未超过4个字，且未匹配到关键词时，回复某个主流程
			botSentenceDomainExtMapper.deleteNotMatchLess4ToByDomain(processId, domainName);
			
			//用户回复超过4个字，且未匹配到关键词时，将用户的回复配置为
			botSentenceDomainExtMapper.deleteNotMatchToByDomain(processId, domainName);
			
			//用户回复无声音时，将用户的回复配置为
			botSentenceDomainExtMapper.deleteNotWordsToByDomain(processId, domainName);
			
			//删除相应的意向
			BotSentenceGradeRuleExample ruleExample = new BotSentenceGradeRuleExample();
			ruleExample.createCriteria().andProcessIdEqualTo(processId).andTypeEqualTo("01").andValue2EqualTo(domainName);
			botSentenceGradeRuleMapper.deleteByExample(ruleExample);
		}else if("update".equals(type)) {
			//更新意向
			botSentenceGradeRuleExtMapper.updateValue2ByDomain(newDomainName, processId, domainName);
			
			//用户回复未超过4个字，且未匹配到关键词时，回复某个主流程
			botSentenceDomainExtMapper.updateNotMatchLess4ToByDomain(processId, domainName, newDomainName);
			
			//用户回复超过4个字，且未匹配到关键词时，将用户的回复配置为
			botSentenceDomainExtMapper.updateNotMatchToByDomain(processId, domainName, newDomainName);
			
			//用户回复无声音时，将用户的回复配置为
			botSentenceDomainExtMapper.updateNotWordsToByDomain(processId, domainName, newDomainName);
		}
		
	}
	
	@Override
	public List<BotSentenceTemplateTradeVO> queryTemplateTreeByOrgCode(String orgCode, String queryType) {
		logger.info("根据机构查询行业模板树结构...");
		List<BotSentenceTemplateTradeVO> results = new ArrayList<>();
		
		List<String> templateIdList = new ArrayList<>();
		List<String> parentIndustryIdList = new ArrayList<>();
		List<String> industryIdList2 = new ArrayList<>();
		List<String> industryIdList3 = new ArrayList<>();
		
		ReturnData<List<String>> returnData= orgService.getIndustryByOrgCode(orgCode);
		templateIdList = returnData.getBody();
		if(null == templateIdList || templateIdList.size() == 0) {
			logger.info("根据机构"+orgCode+"查询模板编号列表为空!");
			return null;
		}
		logger.info("模板列表: " + templateIdList.toString());
		for(String templateId : templateIdList) {
			logger.info("当前模板编号: " + templateId);
			//根据模板获取行业
			BotSentenceTemplate template = botSentenceTemplateService.getBotSentenceTemplateByTemplateId(templateId);
			if(null != template) {
				logger.info("当前模板["+templateId+"]存在..");
				//获取一级行业
				if(!parentIndustryIdList.contains(template.getIndustryId().substring(0, 2))) {
					parentIndustryIdList.add(template.getIndustryId().substring(0, 2));
				}
				//获取二级行业
				if(!industryIdList2.contains(template.getIndustryId().substring(0, 4))) {
					industryIdList2.add(template.getIndustryId().substring(0, 4));
				}
				//获取三级行业
				if(!industryIdList3.contains(template.getIndustryId())) {
					industryIdList3.add(template.getIndustryId());
				}
			}else {
				logger.info("当前模板["+templateId+"]不存在..");
			}
		}
		
		logger.info("一级行业列表: " + parentIndustryIdList.toString());
		for(String industryId : parentIndustryIdList) {
			//获取上级行业
			BotSentenceTrade parentTrade = getBotSentenceTrade(industryId);
			
			BotSentenceTemplateTradeVO parentVo = new BotSentenceTemplateTradeVO();
			parentVo.setValue(parentTrade.getIndustryId());
			parentVo.setLabel(parentTrade.getIndustryName());
			parentVo.setLevel(parentTrade.getLevel());
			//获取下级行业
			List<BotSentenceTrade> trades = getChildIndustryList(parentTrade.getIndustryId());
			if(null != trades && trades.size() > 0) {
				List<BotSentenceTemplateTradeVO> tradeVOList = new ArrayList<>();
				for(BotSentenceTrade trade : trades) {
					if(!industryIdList2.contains(trade.getIndustryId())) {
						continue;
					}
					BotSentenceTemplateTradeVO tradeVO = new BotSentenceTemplateTradeVO();
					tradeVO.setValue(trade.getIndustryId());
					tradeVO.setLabel(trade.getIndustryName());
					tradeVO.setLevel(trade.getLevel());
					List<BotSentenceTrade> childIndustryList = getChildIndustryList(trade.getIndustryId());
					if(null != childIndustryList && childIndustryList.size() > 0) {
						List<BotSentenceTemplateTradeVO> childVOList = new ArrayList<>();
						for(BotSentenceTrade childTrade : childIndustryList) {
							if(!industryIdList3.contains(childTrade.getIndustryId())) {
								continue;
							}
							BotSentenceTemplateTradeVO childVo = new BotSentenceTemplateTradeVO();
							childVo.setValue(childTrade.getIndustryId());
							childVo.setLabel(childTrade.getIndustryName());
							childVo.setLevel(childTrade.getLevel());
							childVOList.add(childVo);
							if("template".equals(queryType)) {
								//查询模板
								List<BotSentenceTemplate> templateList = botSentenceTemplateService.queryTemplateByIndustry(childTrade.getIndustryId());
								if(null != templateList && templateList.size() > 0) {
									List<BotSentenceTemplateTradeVO> templateVOList = new ArrayList<>();
									for(BotSentenceTemplate temp : templateList) {
										if(templateIdList.contains(temp.getTemplateId())) {
											BotSentenceTemplate tempalte = botSentenceTemplateService.getBotSentenceTemplateByTemplateId(temp.getTemplateId());
											BotSentenceTemplateTradeVO templateVo = new BotSentenceTemplateTradeVO();
											templateVo.setValue(tempalte.getTemplateId());
											templateVo.setLabel(tempalte.getTemplateName());
											templateVOList.add(templateVo);
										}
									}
									childVo.setChildren(templateVOList);
								}
							}
						}
						tradeVO.setChildren(childVOList);
					}
					tradeVOList.add(tradeVO);
				}
				parentVo.setChildren(tradeVOList);
			}
			results.add(parentVo);
		}
		return results;
	}
}
