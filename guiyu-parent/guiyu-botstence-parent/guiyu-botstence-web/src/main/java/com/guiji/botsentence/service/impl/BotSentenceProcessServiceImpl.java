package com.guiji.botsentence.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.controller.server.vo.BotSentenceTemplateTradeVO;
import com.guiji.botsentence.dao.BotSentenceAdditionMapper;
import com.guiji.botsentence.dao.BotSentenceBranchMapper;
import com.guiji.botsentence.dao.BotSentenceDomainMapper;
import com.guiji.botsentence.dao.BotSentenceGradeRuleMapper;
import com.guiji.botsentence.dao.BotSentenceIntentMapper;
import com.guiji.botsentence.dao.BotSentenceOptionsMapper;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.BotSentenceShareAuthMapper;
import com.guiji.botsentence.dao.BotSentenceSurveyIntentMapper;
import com.guiji.botsentence.dao.BotSentenceSurveyMapper;
import com.guiji.botsentence.dao.BotSentenceTemplateMapper;
import com.guiji.botsentence.dao.BotSentenceTradeMapper;
import com.guiji.botsentence.dao.BotSentenceTtsBackupMapper;
import com.guiji.botsentence.dao.BotSentenceTtsTaskMapper;
import com.guiji.botsentence.dao.entity.BotSentenceAddition;
import com.guiji.botsentence.dao.entity.BotSentenceBranch;
import com.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import com.guiji.botsentence.dao.entity.BotSentenceDomain;
import com.guiji.botsentence.dao.entity.BotSentenceDomainExample;
import com.guiji.botsentence.dao.entity.BotSentenceGradeRuleExample;
import com.guiji.botsentence.dao.entity.BotSentenceIntent;
import com.guiji.botsentence.dao.entity.BotSentenceIntentExample;
import com.guiji.botsentence.dao.entity.BotSentenceOptions;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import com.guiji.botsentence.dao.entity.BotSentenceSurveyExample;
import com.guiji.botsentence.dao.entity.BotSentenceSurveyIntentExample;
import com.guiji.botsentence.dao.entity.BotSentenceTemplate;
import com.guiji.botsentence.dao.entity.BotSentenceTemplateExample;
import com.guiji.botsentence.dao.entity.BotSentenceTrade;
import com.guiji.botsentence.dao.entity.BotSentenceTradeExample;
import com.guiji.botsentence.dao.entity.BotSentenceTtsBackup;
import com.guiji.botsentence.dao.entity.BotSentenceTtsBackupExample;
import com.guiji.botsentence.dao.entity.BotSentenceTtsTask;
import com.guiji.botsentence.dao.entity.BotSentenceTtsTaskExample;
import com.guiji.botsentence.dao.entity.BusinessAnswerTaskExt;
import com.guiji.botsentence.dao.entity.VoliceInfo;
import com.guiji.botsentence.dao.entity.VoliceInfoExample;
import com.guiji.botsentence.dao.entity.VoliceInfoExt;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample.Criteria;
import com.guiji.botsentence.dao.entity.BotSentenceShareAuthExample;
import com.guiji.botsentence.dao.entity.ext.IntentVO;
import com.guiji.botsentence.dao.entity.ext.VoliceInfoVO;
import com.guiji.botsentence.dao.ext.BotSentenceBranchExtMapper;
import com.guiji.botsentence.dao.ext.BotSentenceDomainExtMapper;
import com.guiji.botsentence.dao.ext.BotSentenceIntentExtMapper;
import com.guiji.botsentence.dao.ext.BotSentenceProcessExtMapper;
import com.guiji.botsentence.dao.ext.VoliceInfoExtMapper;
import com.guiji.botsentence.service.BotSentenceKeyWordsService;
import com.guiji.botsentence.service.IBotSentenceProcessService;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.vo.BotSentenceIntentVO;
import com.guiji.botsentence.vo.BotSentenceProcessVO;
import com.guiji.botsentence.vo.CommonDialogVO;
import com.guiji.botsentence.vo.DomainVO;
import com.guiji.botsentence.vo.FlowEdge;
import com.guiji.botsentence.vo.FlowInfoVO;
import com.guiji.botsentence.vo.FlowNode;
import com.guiji.botsentence.vo.LevelVO;
import com.guiji.botsentence.vo.RefuseBranchVO;
import com.guiji.component.client.util.BeanUtil;
import com.guiji.component.client.util.Pinyin4jUtil;
import com.guiji.component.client.util.SystemUtil;
import com.guiji.common.exception.CommonException;
import com.guiji.component.result.ServerResult;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.netflix.discovery.converters.Auto;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Service
public class BotSentenceProcessServiceImpl implements IBotSentenceProcessService {

	private Logger logger = LoggerFactory.getLogger(BotSentenceProcessServiceImpl.class);
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Autowired
	private BotSentenceDomainMapper botSentenceDomainMapper;
	
	@Autowired
	private BotSentenceBranchMapper botSentenceBranchMapper;
	
	@Autowired
	private BotSentenceIntentMapper botSentenceIntentMapper;
	
	@Autowired
	private BotSentenceBranchExtMapper botSentenceBranchExtMapper;
	
	@Autowired
	private BotSentenceDomainExtMapper botSentenceDomainExtMapper;
	
	@Autowired
	private BotSentenceIntentExtMapper botSentenceIntentExtMapper;
	
	@Autowired
	private com.guiji.botsentence.dao.VoliceInfoMapper voliceInfoMapper;
	
	@Autowired
	private VoliceInfoExtMapper voliceInfoExtMapper;
	
	@Autowired
	private BotSentenceTemplateMapper botSentenceTemplateMapper;
	
	@Autowired
	private BotSentenceAdditionMapper botSentenceAdditionMapper;
	
	@Autowired
	private BusinessAnswerTaskServiceImpl businessAnswerTaskService;
	
	@Autowired
	private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;
	
	@Autowired
	private BotSentenceTtsServiceImpl botSentenceTtsService;
	
	@Autowired
	private BotSentenceTtsBackupMapper botSentenceTtsBackupMapper;
	
	@Autowired
	private VoliceServiceImpl voliceServiceImpl;

	@Autowired
	private BotSentenceTradeMapper botSentenceTradeMapper;
	
	@Autowired
	private BotSentenceKeyWordsService botSentenceKeyWordsService;
	
	@Autowired
	private BotSentenceKeyWordsValidateServiceImpl botSentenceKeyWordsValidateService;
	
	@Autowired
	private BotSentenceGradeRuleMapper botSentenceGradeRuleMapper;
	
	@Autowired
	private BotsentenceVariableServiceImpl botsentenceVariableService;
	
	@Autowired
	private BotSentenceOptionsMapper botSentenceOptionsMapper;
	
	@Autowired
	private BotSentenceSurveyMapper botSentenceSurveyMapper;
	
	@Autowired
	private BotSentenceShareAuthMapper botSentenceShareAuthMapper;
	
	
	@Autowired
	private IAuth iAuth;
	
	@Autowired
	private IOrg orgService;
	
	@Autowired
	private BotSentenceSurveyIntentMapper botSentenceSurveyIntentMapper;
	
	private static final String domain_prefix = "分支";
	private static final String branch_prefix = "special_";
	private static final String line_prefix = "未命名";
	
	@Value("${all_tts}")
	private boolean all_tts;
	
	@Value("${offline}")
	private boolean offline;
	
	@Override
	public List<BotSentenceProcess> queryBotSentenceProcessList(int pageSize, int pageNo, String templateName, String accountNo, String userId, String state) {
		
		ReturnData<SysUser> data=iAuth.getUserById(new Long(userId));
		String orgCode=data.getBody().getOrgCode();
		
		BotSentenceProcessExample example = new BotSentenceProcessExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(templateName)) {
			criteria.andTemplateNameLike("%" + templateName + "%");
		}
		
		if(StringUtils.isNotBlank(state)) {
			criteria.andStateEqualTo(state);
		}
		
		criteria.andOrgCodeLike(orgCode+"%");
		
		criteria.andStateNotEqualTo("99");
		
		//计算分页
		int limitStart = (pageNo-1)*pageSize;
		int limitEnd = pageSize;
		example.setLimitStart(limitStart);
		example.setLimitEnd(limitEnd);
		example.setOrderByClause(" crt_time desc");
		
		return botSentenceProcessMapper.selectByExample(example);
	}

	@Override
	public int countBotSentenceProcess(String templateName, String accountNo, String userId, String state) {

		ReturnData<SysUser> data=iAuth.getUserById(new Long(userId));
		String orgCode=data.getBody().getOrgCode();
		BotSentenceProcessExample example = new BotSentenceProcessExample();
		Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(templateName)) {
			criteria.andTemplateNameLike("%" + templateName + "%");
		}
		
		if(StringUtils.isNotBlank(state)) {
			criteria.andStateEqualTo(state);
		}
		
		criteria.andOrgCodeLike(orgCode+"%");
		//criteria.andAccountNoEqualTo(String.valueOf(userId));
		
		criteria.andStateNotEqualTo("99");
		return botSentenceProcessMapper.countByExample(example);
	
	}


	@Override
	@Transactional
	public String createBotSentenceTemplate(BotSentenceProcessVO paramVO, String userId) {
		ReturnData<SysUser> data=iAuth.getUserById(new Long(userId));
		String userName=data.getBody().getUsername();
		String orgCode=paramVO.getOrgCode();
		String orgName=paramVO.getOrgName();
		if(org.springframework.util.StringUtils.isEmpty(paramVO.getOrgCode())){
			orgCode=data.getBody().getOrgCode();
			orgName=data.getBody().getOrgName();
		}
		
		
		long time1 = System.currentTimeMillis();
		logger.info("============" + time1);
		if(null == paramVO || StringUtils.isBlank(paramVO.getProcessId())) {
			throw new CommonException("创建模板失败，请求参数为空!");
		}
		logger.info("是否全TTS: " + all_tts);
		logger.info("是否离线版本: " + offline);
		
		//查询已生效的话术模板
		//BotSentenceProcess botSentenceTemplate = botSentenceProcessMapper.selectByPrimaryKey(paramVO.getProcessId());
		BotSentenceTemplate botSentenceTemplate = botSentenceTemplateMapper.selectByPrimaryKey(paramVO.getProcessId());
		if(null == botSentenceTemplate) {
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
				
				//设置agent
				/*if(StringUtils.isNotBlank(vo.getIgnoreButDomains())) {
					String ignoreButDomains = vo.getIgnoreButDomains().substring(0, vo.getIgnoreButDomains().length()-1);
					ignoreButDomains = ignoreButDomains + ",\"agent\"]";
					vo.setIgnoreButDomains(ignoreButDomains);
				}*/
				
				/*if("1".equals(vo.getCategory())) {
					vo.setIsMainFlow("01");
				}*/
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
				
				//保存意图信息
				/*String intents = temp.getIntents();
				logger.info("当前意图信息为: " + intents);
				if(StringUtils.isNotBlank(intents)) {
					String[] intentsArray = intents.split(",");
					for(int i = 0 ; i < intentsArray.length ; i++) {
						if(intentIdList.contains(new Long(intentsArray[i].trim()))) {
							continue;
						}
						
						intentIdList.add(new Long(intentsArray[i].trim()));
					}
				}*/
				
				
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
		
		
		//保存意向标签数据
		//modify by zhangpeng 20190112 新意向不需要
		/*BotSentenceLabelExample labelExample = new BotSentenceLabelExample();
		labelExample.createCriteria().andProcessIdEqualTo(paramVO.getProcessId());
		List<BotSentenceLabel> labelList = botSentenceLabelMapper.selectByExample(labelExample);
		if(null != labelList && labelList.size() > 0) {
			for(BotSentenceLabel label : labelList) {
				label.setLabelId(null);
				label.setProcessId(process.getProcessId());
				if(StringUtils.isBlank(label.getKeywords())) {
					label.setKeywords("[]");
				}else {
					label.setKeywords(label.getKeywords());
				}
				label.setCrtUser(userId);
				label.setCrtTime(new Date(System.currentTimeMillis()));
				label.setLstUpdateTime(null);
				label.setLstUpdateUser(null);
			}
			botSentenceLabelExtMapper.batchInsert(labelList);
			logger.info("保存意向标签信息成功");
		}*/
		
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
		
		//生成二维码
		/*logger.info("生成当前话术的二维码...");
		String qrCodeUrl = weChatAppletService.generateQrCode(process.getTemplateId());
		process.setQrCode(qrCodeUrl);
		logger.info("生成二维码成功..." + qrCodeUrl);
		botSentenceProcessMapper.updateByPrimaryKey(process);*/
		
		
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
				//BotSentenceSurvey survey = new BotSentenceSurvey();
				//survey.setDomain(domain.getDomainName());
				//surveyList.add(survey);
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
		
		//校验所有结束节点只有指向，没有分支  为了可以增加无头卡片
		/*if(null != list && list.size() > 0) {
			for(String domainName : endDomainList) {
				BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
				branchExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andNextEqualTo(domainName);
				int num = botSentenceBranchMapper.countByExample(branchExample);
				if(num < 1) {
					throw new CommonException("节点["+domainName+"]未连线!");
				}
			}
		}*/
		
		
		
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
		
		//校验所有节点是否都有其它节点指向它（开始节点除外） 为了可以增加无头卡片
		/*for(BotSentenceDomain domain : list) {
			String domainName = domain.getDomainName();
			BotSentenceBranchExample notStartBranchExample = new BotSentenceBranchExample();
			notStartBranchExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andNextEqualTo(domainName);
			List<BotSentenceBranch> notStartBranchList = botSentenceBranchMapper.selectByExample(notStartBranchExample);
			if((!Constant.DOMAIN_TYPE_START.equals(domain.getType())) && (null == notStartBranchList || notStartBranchList.size() < 1)) {
				throw new CommonException("节点[" + domainName + "]连线不完整!");
			}
		}*/
		
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
		
		
		
		/*BotSentenceDomainExample domainExample2 = new BotSentenceDomainExample();
		domainExample2.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andDomainNameNotIn(domainList);
		List<String> domainList2 = new ArrayList<>();
		List<BotSentenceDomain> list2 = botSentenceDomainMapper.selectByExample(domainExample2);
		if(null != list && list.size() > 0) {
			for(BotSentenceDomain domain : list2) {
				if(!"end".equals(domain.getType())) {
					domainList2.add(domain.getDomainName());
				}
			}
		}
		
		if(domainList2.size() > 0) {
			throw new CommonException("提交失败,节点"+domainList2.toString()+"应该是结束类型!");
		}*/
		
		
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
		refuseExample.createCriteria().andProcessIdEqualTo(processId).andTypeEqualTo(Constant.VOLICE_TYPE_REFUSE);
		List<VoliceInfo> refuseVoliceList = voliceInfoMapper.selectByExample(refuseExample);
		if(null != refuseVoliceList && refuseVoliceList.size() > 0) {
			for(VoliceInfo volice : refuseVoliceList) {
				if(StringUtils.isBlank(volice.getVoliceUrl())) {
					throw new CommonException("存在未上传录音的挽回话术文案!");
				}
			}
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
		
		//校验打断新规文案为必输
		BotSentenceOptions option = botsentenceVariableService.getOptionsByProcessId(processId);
		if(null != option) {
			if(null != option.getInterruptionConfigStart() && option.getInterruptionConfigStart()) {
				if(StringUtils.isBlank(option.getVoice())) {
					throw new CommonException("请设置打断规则文案!");
				}
			}
		}
		
		process.setState("01");//审核中
		process.setLstUpdateTime(new Date(System.currentTimeMillis()));
		process.setLstUpdateUser(userId);
		botSentenceProcessMapper.updateByPrimaryKey(process);
		logger.info("提交审核成功...");
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
	public void delete(String processId, String userId) {
		if(StringUtils.isBlank(processId)) {
			throw new CommonException("修改话术失败，请求参数为空!");
		}
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		if(null != process && ("00".equals(process.getState()) || "02".equals(process.getState()) || "03".equals(process.getState())) && !"00".equals(process.getTemplateType())) {
			logger.info("删除话术流程信息...");
			/*int a = botSentenceProcessMapper.deleteByPrimaryKey(processId);
			logger.info("删除话术流程信息数量: " + a);
			
			logger.info("删除话术domain信息...");
			BotSentenceDomainExample domainExample = new BotSentenceDomainExample();
			domainExample.createCriteria().andProcessIdEqualTo(processId);
			int b = botSentenceDomainMapper.deleteByExample(domainExample);
			logger.info("删除话术domain信息数量: " + b);
			
			logger.info("删除话术branch信息...");
			BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
			branchExample.createCriteria().andProcessIdEqualTo(processId);
			int c = botSentenceBranchMapper.deleteByExample(branchExample);
			logger.info("删除话术branch信息数量: " + c);
			
			logger.info("删除话术intent信息...");
			BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
			intentExample.createCriteria().andProcessIdEqualTo(processId);
			int d = botSentenceIntentMapper.deleteByExample(intentExample);
			logger.info("删除话术intent信息数量: " + d);*/
			
			process.setState("99");//状态修改为作废
			process.setLstUpdateTime(new Date(System.currentTimeMillis()));
			process.setLstUpdateUser(userId);
			botSentenceProcessMapper.updateByPrimaryKey(process);
			
			//删除分享
			BotSentenceShareAuthExample example = new BotSentenceShareAuthExample();
			example.createCriteria().andProcessIdEqualTo(processId);
			botSentenceShareAuthMapper.deleteByExample(example);
			logger.info("删除话术数据成功...");
		}else {
			throw new CommonException("当前状态不是制作中，不允许删除!");
		}
		 
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
			vo.setLuoji("重复3次后走邀约失败");//逻辑
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
			vo.setLuoji("没有话术时，被拒绝走失败结束");
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
						vo.setLuoji("第" + index + "次未识别时回复的话术");//逻辑
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
				vo.setLuoji("触发\"" + branch.getDomain() +"\"流程");//逻辑
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
				vo.setLuoji("触发\"" + branch.getDomain() +"\"流程");//逻辑
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
				vo.setLuoji("触发\"" + branch.getDomain() +"\"流程");//逻辑
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
		
		
		//在忙，话术为domain为结束_在忙的enter，取resp，关键词为domain为结束_在忙的enter，取intents
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
			vo.setLuoji("命中投诉关键词");//逻辑
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
		
		//在忙，话术为domain为结束_在忙的enter，取resp，关键词为domain为结束_在忙的enter，取intents
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
			vo.setLuoji("命中号码过滤关键词");//逻辑
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

	public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
		/*String currentName1 = "分支";
		String currentName = "分支10";
		int currentNum = new Integer(currentName.substring(currentName1.length(), currentName.length())) + 1;
		System.out.println(currentNum);*/
		String []keys = new String[3];
		String a="一";
		String b="bb";
		String c="cc";
		keys[0] = a;
		keys[1] = b;
		keys[2] = c;
				
		//aa = DigestUtils.md5Hex("192.168.1.208:8888");
		System.out.println(JSONObject.toJSONString(keys));
			
		String aa = "0102";
		System.out.println(aa.substring(aa.length() -2, aa.length()));
		
		
//		
//		System.out.println(System.currentTimeMillis());
//		System.out.println(System.currentTimeMillis());
//		System.out.println(System.currentTimeMillis());
//		Pinyin4jUtil util= new Pinyin4jUtil();
//		String p = util.toPinYinLowercase("shadfsafd");
//		System.out.println(p);
		
		/*String resp = "[111,22,33,44,55,";
		resp = resp.substring(0, resp.length() -1) + "]";
		
		
		
		String bb = "新增分支_123";
		System.out.println(bb.indexOf("_"));
		System.out.println(bb.substring(bb.indexOf("_")+1, bb.length()));
		System.out.println(bb.substring(bb.length()-1, bb.length()));
		*/
		/*
		String aa = "[\"公众号\",\"联系电话\",[\"优惠\",\"发我手机上*!\"],[\"发我手机上\",\"资料*!\"],[\"发我手机上\",\"等一会*!\"],\"公司简介\",[\"发送\",\"看看*!\"],[\"公司*!\",\"发送\"],[\"手机号*!\",\"重复一下\"],[\"发个\",\"发点\",\"有什么\",\"资料*!\"],\"有资料\",\"发我手机上\",\"怎么联系\",\"你的联系方式\",\"手机号\",\"号码多少\",\"加下微信\",\"发送\",\"再说\",\"再联系\",\"有需要再联系\"]";
		aa = aa.substring(1,aa.length()-1);
		
		String yuliu = "";
		List<String> yulius = new ArrayList<>();
		
		while(aa.contains("[")) {
			System.out.println(aa);	
			int start = aa.indexOf("[");
			int end = aa.indexOf("]");
			yulius.add(aa.substring(start,end+1));
			//System.out.println(aa.substring(start,end+1));
			aa = aa.substring(0, start) + aa.substring(end+2, aa.length());
		}
		
		String yuliuStr = "";
		for(String temp : yulius) {
			yuliuStr = yuliuStr + temp + ",";
		}
		yuliuStr = yuliuStr.substring(0, yuliuStr.length()-1);
		
		System.out.println(yuliuStr);
		System.out.println(aa);*/
		
		
		
		
		/*String []keyArray = aa.split(",");
		for(int i = 0 ; i < keyArray.length ; i++) {
			System.out.println(keyArray[i]);
		}*/
		
	}

	/*@Override
	@Transactional
	public void updateCommonDialog(String voliceId, String content, String keywords, String intentId, String branchId) {
		if(StringUtils.isBlank(voliceId) || StringUtils.isBlank(content)) {
			throw new CommonException("更新失败，请求数据为空!");
		}
		
		String userId = UserUtil.getUserId();
		VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(voliceId));
		volice.setContent(content.replace("\n", "").trim());
		volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
		volice.setLstUpdateUser(userId);
		if(!"【新增】".equals(volice.getFlag())) {
			volice.setFlag("【修改】");
		}
		voliceServiceImpl.saveVoliceInfo(volice);
		
		updateProcessState(volice.getProcessId());
		
	}*/
	
	
	@Override
	@Transactional
	public void updateCommonDialog(CommonDialogVO commonDialog, String userId) {


		String content = commonDialog.getContent();
		long voliceId = commonDialog.getVoliceId();
		String branchId = commonDialog.getBranchId();
		
		BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);
		
		//与除主流程之外的所有关键字去重
		List<Long> intentIds = new ArrayList<>();
		
		//获取所有关键词库对应关键词集合
		
		//不需要校验关键词的域
		List<String> notValidateList = new ArrayList<>();
		notValidateList.add("失败邀约");
		notValidateList.add("未识别");
		notValidateList.add("出错");
		notValidateList.add("失败结束");
		notValidateList.add("强制结束");
		notValidateList.add("结束_未匹配");
		
		
		if(!notValidateList.contains(commonDialog.getYujin())) {
			if(null != commonDialog.getIntentList() && commonDialog.getIntentList().size() > 0) {
				for(BotSentenceIntentVO temp : commonDialog.getIntentList()) {
					if(StringUtils.isNotBlank(commonDialog.getIntentDomain())) {
						temp.setIntentDomain(commonDialog.getIntentDomain());
					}else {
						temp.setIntentDomain(commonDialog.getDomain());
					}
					if(null != temp.getId()) {
						intentIds.add(new Long(temp.getId()));
					}
				}
				//botSentenceKeyWordsValidateService.validateBusinessAskKeywords(commonDialog.getIntentList(), commonDialog.getProcessId(), intentIds);
				botSentenceKeyWordsValidateService.validateBusinessAskKeywords2(commonDialog.getIntentList(), commonDialog.getProcessId(), intentIds);
			}
		}
		
		//没有文案的域
		if((!"号码过滤".equals(commonDialog.getDomain())) && (!"投诉".equals(commonDialog.getDomain())) 
				&& !"拒绝".equals(commonDialog.getDomain()) && !"用户不清楚".equals(commonDialog.getDomain())) {

			if(null == commonDialog || StringUtils.isBlank(commonDialog.getContent()) || StringUtils.isBlank(commonDialog.getBranchId())) {
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
					voliceInfoMapper.deleteByPrimaryKey(new Long(deleteId));
				}
				resp = "["+BotSentenceUtil.listToString(respList)+"]";
				branch.setResponse("["+BotSentenceUtil.listToString(respList)+"]");
			}else {
				if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
						&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
					String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
					
					for(int i = 0 ; i < respArray.length ; i++) {
						voliceInfoMapper.deleteByPrimaryKey(new Long(respArray[i]));
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
		
		
		//更新branch的意图
		if(!notValidateList.contains(commonDialog.getYujin())) {
			if(null != branch) {
				if(null != commonDialog.getIntentList() && commonDialog.getIntentList().size() > 0) {
					//新增意图
					String intentIds2 = botSentenceKeyWordsService.saveIntent(branch.getDomain(), branch.getProcessId(), branch.getTemplateId(), commonDialog.getIntentList(), "00", branch, userId);
					branch.setIntents(intentIds2);
				}else {
					branch.setIntents(null);
				}
			}
		}
		
		branch.setLstUpdateTime(new Date(System.currentTimeMillis()));
		branch.setLstUpdateUser(userId);
		botSentenceBranchMapper.updateByPrimaryKey(branch);
		
		//如果当前状态为审批通过、已上线，则把状态修改为“制作中”
		this.updateProcessState(commonDialog.getProcessId(), userId);
	}
	

	/*@Override
	public ProcessInfo initProcessInfo(String processId) {
		if(StringUtils.isNotBlank(processId)) {
			ProcessInfo processInfo = new ProcessInfo();
			processInfo.setProcessId(processId);
			
			BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
			
			List<LevelVO> results = new ArrayList<>();
			//第一步：先查询name为开场白的domain作为顶层数据
			BotSentenceDomainExample domaninExample = new BotSentenceDomainExample();
			domaninExample.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1");
			domaninExample.setOrderByClause(" level desc");
			List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domaninExample);
			if(null != domainList && domainList.size() > 0) {
				BotSentenceDomain end = domainList.get(0);
				int maxLevel = end.getLevel();
				List<String> domainNameList = new ArrayList<>();
				
				//获取解释开场白
				VoliceInfo startExplainVolice = getStartExplain(processId);
				
				
				for(int i = 1 ; i <= maxLevel ; i++) {
					LevelVO level = new LevelVO();
					level.setId(i);
					List<DomainVO> domins = new ArrayList<>();
					BotSentenceDomainExample example = new BotSentenceDomainExample();
					
					if(domainNameList.size()>0 && !"systemImport".equals(process.getCrtUser())) {
						example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andLevelEqualTo(i).andDomainNameIn(domainNameList);
						String orderStr = "";
						for(int m = 0 ; m < domainNameList.size() ; m++) {
							if(m == domainNameList.size() -1) {
								orderStr = orderStr + "'" + domainNameList.get(m) + "'";
							}else {
								orderStr = orderStr + "'" +domainNameList.get(m) + "',";
							}
						}
						
						example.setOrderByClause(" field(domain_name, " + orderStr + ")");
					}else {
						example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andLevelEqualTo(i);
					}
					
					List<BotSentenceDomain> levelList = botSentenceDomainMapper.selectByExample(example);
					
					domainNameList = new ArrayList<>();
					
					if(null != levelList && levelList.size() > 0) {
						for(BotSentenceDomain temp : levelList) {
							DomainVO vo = new DomainVO();
							vo.setLevel(i+"");
							vo.setType(temp.getType());//节点类型
							vo.setName(temp.getDomainName());//设置domain名称
							vo.setDomainId(temp.getDomainId());//
							//获取当前domain的主分支
							BotSentenceBranch mainBranch = getMainBranch(processId, temp.getDomainName());
							if(null != mainBranch) {
								vo.setMainFlowId(mainBranch.getBranchId());
								vo.setMainFlow(mainBranch.getBranchId());
							}
							
							//如果当前节点为场白，则把解释开场白作为开场白的的一个属性
							if(null != startExplainVolice && "start".equals(temp.getType())) {
								vo.setStartExplainText(startExplainVolice.getContent());
								vo.setStartExplainUrl(startExplainVolice.getVoliceUrl());
								
								//如果当前节点是开始节点，则查询解释开场白的意图信息
								//获取解释开场白的branch分支的positive
								BotSentenceBranch branch = getPositiveBranch(processId, "解释开场白");
								if(null != branch && StringUtils.isNotBlank(branch.getIntents())) {
									String intentId = branch.getIntents().split(",")[0];
									BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intentId));
									vo.setStartExplainKeywords(BotSentenceUtil.generateShowKeywords(intent.getKeywords()));
								}
							}
							
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
									vo.setText(volice.getContent());//设置文案内容
									vo.setUrl(volice.getVoliceUrl());//设置录音URL
								}
							}
							
							
							//查询当前domain的连线branch
							BotSentenceBranchExample lineExample = new BotSentenceBranchExample();
							lineExample.createCriteria().andDomainEqualTo(temp.getDomainName()).andProcessIdEqualTo(processId).andIsShowEqualTo("1");
							lineExample.setOrderByClause(" crt_time");
							List<BotSentenceBranch> lineBranchList = botSentenceBranchMapper.selectByExample(lineExample);
							if(null != lineBranchList && lineBranchList.size() > 0) {
								List<NextVO> nextList = new ArrayList<NextVO>();
								
								for(BotSentenceBranch lineBranch : lineBranchList) {
									NextVO next = new NextVO();
									next.setName(lineBranch.getLineName());
									next.setShow(lineBranch.getIsShow());
									
									next.setTo(lineBranch.getNext());
									next.setBranchId(lineBranch.getBranchId());
									
									String intents = lineBranch.getIntents();
									if(StringUtils.isNotBlank(intents)) {
										String intent = intents.split(",")[0];
										BotSentenceIntent botSentenceIntent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intent));
										
										String keyword = botSentenceIntent.getKeywords();
										
										if(StringUtils.isNotBlank(keyword)) {

											List<String> keyword_list = BotSentenceUtil.getKeywords(keyword);
											if(null != keyword_list && keyword_list.size() > 0) {
												next.setText(keyword_list.get(0).replace("\"", ""));
											}
										}
									}
									nextList.add(next);
									if(!domainNameList.contains(lineBranch.getNext())) {
										domainNameList.add(lineBranch.getNext());
									}
									
								}
								vo.setNext(nextList);
							}
							
							//获取当前domain的挽回话术列表
							List<RefuseBranchVO> refuses = new ArrayList<>();
							BotSentenceBranchExample refuseBranchExample = new BotSentenceBranchExample();
							refuseBranchExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(temp.getDomainName()).andBranchNameEqualTo("refuse_" + temp.getDomainName());
							List<BotSentenceBranch> refuseBranchs = botSentenceBranchMapper.selectByExample(refuseBranchExample);
							if(null != refuseBranchs && refuseBranchs.size() > 0) {
								String resp = refuseBranchs.get(0).getResponse();
								
								if(StringUtils.isNotBlank(resp) && !"[]".equals(resp.trim()) && resp.trim().startsWith("[") && resp.trim().endsWith("]")) {
									String[] respArray = resp.substring(1,resp.length()-1).split(",");
									for(int m = 0 ; m < respArray.length ; m++) {
										long voliceId_new = new Long(respArray[m]);
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
							vo.setRefuses(refuses);
							
							
							domins.add(vo);
						}
						level.setArr(domins);
					}
					results.add(level);
				}
				processInfo.setLevelList(results);
			}
			
			return processInfo;
		}
		
		
		return null;
	}*/


	public boolean hasChild(String domainName, String processId) {
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		
		example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andParentEqualTo(domainName);
		
		int num = botSentenceDomainMapper.countByExample(example);
		if(num > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public int getParentXnum(String domainName, List<LevelVO> levelList, String processId) {
		//获取parent
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		
		example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andDomainNameEqualTo(domainName);
		
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(example);
		BotSentenceDomain parent = domainList.get(0);
		
		for(int i = 0 ; i < levelList.size() ; i++) {
			List<DomainVO> list = levelList.get(i).getArr();
			for(int j = 0 ; j < list.size() ; j++) {
				if(parent.getParent().equals(list.get(j).getName())) {
					return list.get(j).getxNum();
				}
			}
		}
		return 0;
	}
	
	public int getMaxChildLength(String processId, String domainName) {
		
		List<String> domainNameList = new ArrayList<>();
		List<String> allDomainNameList = new ArrayList<>();
		
		domainNameList.add(domainName);
		allDomainNameList.add(domainName);
		
		while(null != domainNameList && domainNameList.size() > 0) {//如果上级有值，则继续遍历
			BotSentenceDomainExample example = new BotSentenceDomainExample();
			
			example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andParentIn(domainNameList);
			
			List<BotSentenceDomain> levelList = botSentenceDomainMapper.selectByExample(example);
			
			domainNameList = new ArrayList<>();
			
			if(null != levelList && levelList.size() > 0) {
				
				for(BotSentenceDomain temp : levelList) {
					//vo.setxNum(xnum);
					
					if(!domainNameList.contains(temp.getDomainName())) {
						allDomainNameList.add(temp.getDomainName());
						domainNameList.add(temp.getDomainName());
					}
				}
			}
			
		}
		
		int branchNum = 0;
		
		for(int i = 0 ; i < allDomainNameList.size() ; i++) {
			BotSentenceDomainExample example = new BotSentenceDomainExample();
			example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andParentEqualTo(allDomainNameList.get(i));
			int num = botSentenceDomainMapper.countByExample(example);
			if(num > 1) {
				branchNum = branchNum + num - 1;
			}
		}
		return branchNum;
	}
	
	
	private int getNextDomainNum(int level ,List<LevelVO> results) {
		int count = 0;
		LevelVO last = results.get(0);
		for(LevelVO temp : results) {
			if(level == temp.getId()) {
				
			}
		}
		
		return count;
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
		
		
		/*if(Constant.DOMAIN_TYPE_AGENT.equals(blankDomain.getType())){//如果当前节点是转人工，则新增一个转人工意图
			BotSentenceIntent intent = new BotSentenceIntent();
			intent.setCrtTime(date);
			intent.setCrtUser(userId);
			intent.setProcessId(blankDomain.getProcessId());
			intent.setKeywords("[]");
			intent.setIndustry(process.getIndustry());
			intent.setDomainName(Constant.DOMAIN_TYPE_AGENT);
			String key_str = process.getTemplateId().split("_")[0];
			intent.setTemplateId(key_str);
			intent.setForSelect(1);
			intent.setName("trade_" + key_str + "_" + Constant.DOMAIN_TYPE_AGENT + "_" + Constant.DOMAIN_TYPE_AGENT);
			botSentenceIntentMapper.insert(intent);
		}*/
		
		//如果当前状态为审批通过、已上线，则把状态修改为“制作中”
		updateProcessState(blankDomain.getProcessId(), userId);
		
		return domain;
	}
	
	/**
	 * 更新一个domain卡片
	 * @param node
	 */
	@Transactional
	private BotSentenceDomain updateNode(FlowNode blankDomain, String domainId, String userId) {

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
		
		
		if(StringUtils.isNotBlank(domainId) && domainId.equals(blankDomain.getId())) {//如果修改的是当前domain才需要更新以下数据，否则只要更新上面的坐标即可
			//第三步：更新branch表的domain
			BotSentenceBranchExample branchExample1 = new BotSentenceBranchExample();
			branchExample1.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andDomainEqualTo(oldDomainName);
			List<BotSentenceBranch> branchList1 = botSentenceBranchMapper.selectByExample(branchExample1);
			if(null != branchList1 && branchList1.size() > 0) {
				for(BotSentenceBranch temp : branchList1) {
					temp.setDomain(newDomainName);
					temp.setLstUpdateTime(new Date(System.currentTimeMillis()));
					temp.setLstUpdateUser(userId);
					botSentenceBranchMapper.updateByPrimaryKey(temp);
				}
			}
			
			//第四步：更新branch表的next
			BotSentenceBranchExample branchExample2 = new BotSentenceBranchExample();
			branchExample2.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andNextEqualTo(oldDomainName);
			List<BotSentenceBranch> branchList2 = botSentenceBranchMapper.selectByExample(branchExample2);
			if(null != branchList2 && branchList2.size() > 0) {
				for(BotSentenceBranch temp : branchList2) {
					temp.setNext(blankDomain.getLabel());
					temp.setLstUpdateTime(new Date(System.currentTimeMillis()));
					temp.setLstUpdateUser(userId);
					botSentenceBranchMapper.updateByPrimaryKey(temp);
				}
			}
			
			
			//第五步：更新branch表的end
			BotSentenceBranchExample branchExample3 = new BotSentenceBranchExample();
			branchExample3.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andEndEqualTo(oldDomainName);
			List<BotSentenceBranch> branchList3 = botSentenceBranchMapper.selectByExample(branchExample3);
			if(null != branchList3 && branchList3.size() > 0) {
				for(BotSentenceBranch temp : branchList3) {
					temp.setEnd(blankDomain.getLabel());
					temp.setLstUpdateTime(new Date(System.currentTimeMillis()));
					temp.setLstUpdateUser(userId);
					botSentenceBranchMapper.updateByPrimaryKey(temp);
				}
			}
			
			
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
			
			
			/*if(Constant.DOMAIN_TYPE_START.equals(blankDomain.getType())) {
				//获取解释开场白分支
				BotSentenceBranch branch = getStartExplainBranch(blankDomain.getProcessId());
				if(null != blankDomain.getStartExplainIntentList()	&& blankDomain.getStartExplainIntentList().size() > 0) {
					Map<String, String> keywords = getSameBranchKeywords(blankDomain.getProcessId(), blankDomain.getLabel(), branch.getBranchId(), null);
					String message = "";
					//获取所有关键词库对应关键词集合
					String allKeywords = "";
					for(BotSentenceIntentVO temp : blankDomain.getStartExplainIntentList()) {
						if(org.apache.commons.lang.StringUtils.isNotBlank(temp.getKeywords())) {
							String replaceKeyWords = temp.getKeywords().replaceAll("，", ",");
							replaceKeyWords = replaceKeyWords.replace("\n", "");
							
							allKeywords = allKeywords + replaceKeyWords + ",";
						}
					}
					
					if(StringUtils.isNotBlank(allKeywords)) {
						String []keywords_array = allKeywords.split(",");
						
						//校验关键字是否重复
						for(int j = 0 ; j < keywords_array.length ; j++) {
							if(keywords.containsKey(keywords_array[j])) {
								String repeat = "【" + keywords_array[j] + "】 与 【" + keywords.get(keywords_array[j]) + "】的关键字重复了";
								message = message + repeat + "<br/>";
							}
						}
					}
					
					String message2 = botSentenceKeyWordsValidateService.validateIntentKeywords(blankDomain.getStartExplainIntentList());
					message = message+message2;
					
					if(StringUtils.isNotBlank(message)) {
						throw new CommonException(message);
					}
					
					String intentIds2 = botSentenceKeyWordsService.saveIntent(domain.getDomainName(), domain.getProcessId(), domain.getTemplateId(), blankDomain.getStartExplainIntentList(), "01", branch, userId);
					if(org.apache.commons.lang.StringUtils.isNotBlank(intentIds2)) {
						BotSentenceBranch explainBranch = getStartExplainBranch(domain.getProcessId());
						explainBranch.setIntents(intentIds2);
						explainBranch.setLstUpdateTime(new Date(System.currentTimeMillis()));
						explainBranch.setLstUpdateUser(userId);
						botSentenceBranchMapper.updateByPrimaryKey(explainBranch);
					}
				}
			}*/
		}
		
		//如果当前状态为审批通过、已上线，则把状态修改为“制作中”
		this.updateProcessState(domain.getProcessId(), userId);
		
		return domain;
	}
	
	/**
	 * 更新开场白信息
	 * @param blankDomain
	 * @return
	 */
	@Transactional
	private void saveStartExplain(FlowNode blankDomain,String domainId, String userId) {
		//如果当前节点为开场白，则需要同时更新解释开场白信息
		if(Constant.DOMAIN_TYPE_START.equals(blankDomain.getType()) && StringUtils.isNotBlank(blankDomain.getStartExplainText())) {
			
			VoliceInfo volice = getStartExplain(blankDomain.getProcessId());
			//判断解释开场白的文案是否有变化，如果有，则设置volice-flag【修改】
			if(StringUtils.isNotBlank(volice.getContent()) && !volice.getContent().equals(blankDomain.getStartExplainText()) && !"【新增】".equals(volice.getFlag())) {
				volice.setFlag("【修改】");
			}
			
			volice.setContent(blankDomain.getStartExplainText().replace("\n", "").trim());
			volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
			volice.setLstUpdateUser(userId);
			
			voliceServiceImpl.saveVoliceInfo(volice, userId);
			
			
			if(StringUtils.isNotBlank(domainId) && domainId.equals(blankDomain.getId())) {//只有保存开场白卡片才需要校验关键字是否重复

				//获取同级分支的关键字列表
				Map<String, String> keywords = new HashMap<>(); 
				
				BotSentenceBranch explainBranch = getStartExplainBranch(blankDomain.getProcessId());
						
				BotSentenceBranchExample branchexample = new BotSentenceBranchExample();
				branchexample.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andIsShowEqualTo("1").andDomainEqualTo(explainBranch.getDomain()).andBranchIdNotEqualTo(explainBranch.getBranchId());
				List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchexample);
				if(null != branchList && branchList.size() > 0) {
					for(BotSentenceBranch branch : branchList) {
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
											keywords.put(str, branch.getLineName());
										}
									}
								}
								
							}
						}
					}
				}
				
				
				if(null != blankDomain.getStartExplainIntentList()	&& blankDomain.getStartExplainIntentList().size() > 0) {
					String message = "";
					//获取所有关键词库对应关键词集合
					String allKeywords = "";
					for(BotSentenceIntentVO temp : blankDomain.getStartExplainIntentList()) {
						if(org.apache.commons.lang.StringUtils.isNotBlank(temp.getKeywords())) {
							String replaceKeyWords = temp.getKeywords().replaceAll("，", ",");
							replaceKeyWords = replaceKeyWords.replace("\n", "");
							
							allKeywords = allKeywords + replaceKeyWords + ",";
						}
					}
					
					if(StringUtils.isNotBlank(allKeywords)) {
						String []keywords_array = allKeywords.split(",");
						
						//校验关键字是否重复
						for(int j = 0 ; j < keywords_array.length ; j++) {
							if(keywords.containsKey(keywords_array[j])) {
								String repeat = "【" + keywords_array[j] + "】 与 【" + keywords.get(keywords_array[j]) + "】的关键字重复了";
								message = message + repeat + "<br/>";
							}
						}
					}
					
					String message2 = botSentenceKeyWordsValidateService.validateIntentKeywords(blankDomain.getStartExplainIntentList());
					
					
					if(StringUtils.isNotBlank(message+message2)) {
						throw new CommonException(message);
					}
					BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(blankDomain.getProcessId());
					String intentIds2 = botSentenceKeyWordsService.saveIntent(explainBranch.getDomain(), blankDomain.getProcessId(), process.getTemplateId(), blankDomain.getStartExplainIntentList(), "01", explainBranch, userId);
					if(org.apache.commons.lang.StringUtils.isNotBlank(intentIds2)) {
						explainBranch.setIntents(intentIds2);
						explainBranch.setLstUpdateTime(new Date(System.currentTimeMillis()));
						explainBranch.setLstUpdateUser(userId);
						botSentenceBranchMapper.updateByPrimaryKey(explainBranch);
					}
				}
			}
				
		}
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
		String message = "";
		
		if(Constant.BRANCH_TYPE_NORMAL.equals(edge.getType())) {
			//获取流程图中当前同级分支的关键字
			Map<String, String> keywords = new HashMap<>();
			
			BotSentenceBranchExample example = new BotSentenceBranchExample();
			example.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(source.getDomainName());
			List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(example);
			
			/*if(Constant.DOMAIN_TYPE_START.equals(source.getType())) {
				BotSentenceBranchExample startExample = new BotSentenceBranchExample();
				startExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(source.getDomainName()).andNextEqualTo("解释开场白");
				List<BotSentenceBranch> startList = botSentenceBranchMapper.selectByExample(startExample);
				list.addAll(startList);
			}*/
			
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
			
			//校验关键字是否重复
			String []keywords_array = new String[] {};
			
			//获取所有关键词库对应关键词集合
			if(null != edge.getIntentList() && edge.getIntentList().size() > 0) {
				String allKeywords = "";
				for(BotSentenceIntentVO temp : edge.getIntentList()) {
					if(org.apache.commons.lang.StringUtils.isNotBlank(temp.getKeywords())) {
						String replaceKeyWords = temp.getKeywords().replaceAll("，", ",");
						replaceKeyWords = replaceKeyWords.replace("\n", "");
						
						allKeywords = allKeywords + replaceKeyWords + ",";
					}
				}
				if(org.apache.commons.lang.StringUtils.isNotBlank(allKeywords)) {
					allKeywords = allKeywords.substring(0, allKeywords.length()-1);
					keywords_array=allKeywords.split(",");
				}
			}
			
			for(int j = 0 ; j < keywords_array.length ; j++) {
				if(keywords.containsKey(keywords_array[j])) {
					String repeat = "【" + keywords_array[j] + "】 与 【" + keywords.get(keywords_array[j]) + "】的关键字重复了";
					message = message + repeat + "<br/>";
				}
			}
			
			String message2 = botSentenceKeyWordsValidateService.validateIntentKeywords(edge.getIntentList());
			message = message + message2;
			
			if(StringUtils.isNotBlank(message)) {
				throw new CommonException(message);
			}
			
			if(null != edge.getIntentList() && edge.getIntentList().size() > 0) {
				String intents = botSentenceKeyWordsService.saveIntent(source.getDomainName(), process.getProcessId(), process.getTemplateId(), edge.getIntentList(), "01", null, userId);
				if(org.apache.commons.lang.StringUtils.isNotBlank(intents)) {
					newBranch.setIntents(intents);
				}
			}
		}else if(Constant.BRANCH_TYPE_POSITIVE.equals(edge.getType())){
			//判断当前卡片向下是否已存在未拒绝的分支，如果存在，则不允许新增
			BotSentenceBranchExample example = new BotSentenceBranchExample();
			example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(source.getDomainName()).andTypeEqualTo(Constant.BRANCH_TYPE_POSITIVE);
			int num = botSentenceBranchMapper.countByExample(example);
			if(num > 0) {
				throw new CommonException("当前节点【" + source.getDomainName() + "】已存在'未拒绝'的分支");
			}
			branchName = "positive";
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

			
			//获取同级分支的关键字列表
			Map<String, String> keywords = new HashMap<>(); 
					
			BotSentenceBranchExample branchexample = new BotSentenceBranchExample();
			branchexample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(botSentenceBranch.getDomain()).andBranchIdNotEqualTo(botSentenceBranch.getBranchId());
			List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(branchexample);
			
			/*if(Constant.DOMAIN_TYPE_START.equals(new_sourceDomain.getType())) {
				BotSentenceBranchExample startExample = new BotSentenceBranchExample();
				startExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(new_sourceDomain.getDomainName()).andNextEqualTo("解释开场白");
				List<BotSentenceBranch> startList = botSentenceBranchMapper.selectByExample(startExample);
				list.addAll(startList);
			}*/
			
			
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
			
			
			if(Constant.BRANCH_TYPE_NORMAL.equals(edge.getType())) {//如果是一般类型
				String message = "";
				//如果原来有意图，则更新意图内容
				/*if(StringUtils.isNotBlank(botSentenceBranch.getIntents())) {
					//更新意图表的关键词
					String intent = botSentenceBranch.getIntents();
					if(StringUtils.isNotBlank(intent)) {
						BotSentenceIntent botSentenceIntent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intent));
						
						List<Long> intentIds = new ArrayList<>();
						intentIds.add(new Long(intent));
						
						String new_key_words = "[";
						if(StringUtils.isNotBlank(edge.getKeyWords())) {
							String replaceKeyWords = edge.getKeyWords().replaceAll("，", ",");
							String []keywords_array = replaceKeyWords.split(",");
							
							//校验关键字是否重复
							for(int j = 0 ; j < keywords_array.length ; j++) {
								if(keywords.containsKey(keywords_array[j])) {
									String repeat = "【" + keywords_array[j] + "】 与 【" + keywords.get(keywords_array[j]) + "】的关键字重复了";
									message = message + repeat + "<br/>";
								}
								
								new_key_words = new_key_words + "\"" + keywords_array[j] + "\"" + ",";
							}
							new_key_words = new_key_words.substring(0, new_key_words.length() -1);
						}
						
						if(StringUtils.isNotBlank(message)) {
							throw new CommonException(message);
						}
						
						new_key_words = new_key_words+"]";
						new_key_words = new_key_words.replace("\n", "");//替换换行符
						botSentenceIntent.setKeywords(new_key_words);
						botSentenceIntent.setLstUpdateTime(new Date(System.currentTimeMillis()));
						botSentenceIntent.setLstUpdateUser(UserUtil.getUserId());
						botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(botSentenceIntent);
					}
				}else {//如果原来没有意图，则需要新增一条意图信息
					BotSentenceIntent intent = new BotSentenceIntent();
					intent.setCrtTime(new Date(System.currentTimeMillis()));
					intent.setCrtUser(UserUtil.getUserId());
					intent.setProcessId(processId);
					intent.setKeywords(edge.getKeyWords());
					String key_str = old_sourcedomain.getTemplateId().split("_")[0];
					intent.setTemplateId(key_str);
					intent.setForSelect(0);
					intent.setName("trade_" + key_str + "_" + botSentenceBranch.getBranchName() + "_" + botSentenceBranch.getBranchName());
					String new_key_words = "[";
					if(StringUtils.isNotBlank(edge.getKeyWords())) {
						String replaceKeyWords = edge.getKeyWords().replaceAll("，", ",");
						replaceKeyWords = replaceKeyWords.replace("\n", "");//替换换行符
						
						String []keywords_array = replaceKeyWords.split(",");
						for(int j = 0 ; j < keywords_array.length ; j++) {
							//校验关键字是否重复
							if(keywords.containsKey(keywords_array[j])) {
								String repeat = "【" + keywords_array[j] + "】 与 【" + keywords.get(keywords_array[j]) + "】的关键字重复了";
								message = message + repeat + "<br/>";
							}
							
							new_key_words = new_key_words + "\"" + keywords_array[j] + "\"" + ",";
						}
						
						if(StringUtils.isNotBlank(message)) {
							throw new CommonException(message);
						}
						
						new_key_words = new_key_words.substring(0, new_key_words.length() -1);
					}
					
					new_key_words = new_key_words+"]";
					intent.setKeywords(new_key_words);
					intent.setId(null);
					intent.setDomainName(botSentenceBranch.getBranchName());
					
					botSentenceIntentMapper.insert(intent);
					
					botSentenceBranch.setIntents(intent.getId().toString());//保存意图ID
				
				}*/
				
				//获取所有关键词库对应关键词集合
				if(null != edge.getIntentList() && edge.getIntentList().size() > 0) {
					String allKeywords = "";
					for(BotSentenceIntentVO temp : edge.getIntentList()) {
						if(org.apache.commons.lang.StringUtils.isNotBlank(temp.getKeywords())) {
							String replaceKeyWords = temp.getKeywords().replaceAll("，", ",");
							replaceKeyWords = replaceKeyWords.replace("\n", "");
							
							allKeywords = allKeywords + replaceKeyWords + ",";
						}
					}
					
					if(StringUtils.isNotBlank(allKeywords)) {
						String []keywords_array = allKeywords.split(",");
						
						//校验关键字是否重复
						for(int j = 0 ; j < keywords_array.length ; j++) {
							if(keywords.containsKey(keywords_array[j])) {
								String repeat = "【" + keywords_array[j] + "】 与 【" + keywords.get(keywords_array[j]) + "】的关键字重复了";
								message = message + repeat + "<br/>";
							}
						}
					}
					
					String message2 = botSentenceKeyWordsValidateService.validateIntentKeywords(edge.getIntentList());
					message = message + message2;
					
					if(StringUtils.isNotBlank(message)) {
						throw new CommonException(message);
					}
					
					String intentIds2 = botSentenceKeyWordsService.saveIntent(new_sourceDomain.getDomainName(), processId, botSentenceBranch.getTemplateId(), edge.getIntentList(), "01", botSentenceBranch, userId);
					if(org.apache.commons.lang.StringUtils.isNotBlank(intentIds2)) {
						botSentenceBranch.setIntents(intentIds2);
					}
				}
				
				//如果原来是未拒绝，则需要更改branchname名称
				if(Constant.BRANCH_TYPE_POSITIVE.equals(botSentenceBranch.getType())) {
					//更新Branchname
					String branchName = getNewBranchName(processId);
					botSentenceBranch.setBranchName(branchName);
				}
				botSentenceBranch.setEnd(botSentenceBranch.getNext());
			}else if(Constant.BRANCH_TYPE_POSITIVE.equals(edge.getType())) {//如果是未拒绝
				//判断当前卡片向下是否已存在未拒绝的分支，如果存在，则不允许新增
				BotSentenceBranchExample example = new BotSentenceBranchExample();
				example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(botSentenceBranch.getDomain()).andTypeEqualTo(Constant.BRANCH_TYPE_POSITIVE)
				.andBranchIdNotEqualTo(branchId);
				int num = botSentenceBranchMapper.countByExample(example);
				if(num > 0) {
					throw new CommonException("当前节点【" + botSentenceBranch.getDomain() + "】已存在'未拒绝'的分支");
				}
				
				//如果原来是一般类型，则需要删除意图信息
				if(StringUtils.isNotBlank(botSentenceBranch.getIntents())) {
					String []intents = botSentenceBranch.getIntents().split(",");
					for(int i = 0 ; i < intents.length ; i++) {
						botSentenceIntentMapper.deleteByPrimaryKey(new Long(intents[i]));
					}
				}
				//更新Branchname
				botSentenceBranch.setBranchName("positive");
				botSentenceBranch.setIntents(null);
				botSentenceBranch.setEnd(null);
			}
			
		}
		botSentenceBranch.setType(edge.getType());
		botSentenceBranchMapper.updateByPrimaryKey(botSentenceBranch);
		
		//如果当前状态为审批通过、已上线，则把状态修改为“制作中”
		this.updateProcessState(processId, userId);
	}
	
	
	/**
	 * 获取当前domain的主分支
	 * @param processId
	 * @param domain
	 * @return
	 */
	private BotSentenceBranch getMainBranch(String processId, String domain) {
		BotSentenceBranchExample mainExample = new BotSentenceBranchExample();
		mainExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain).andBranchNameEqualTo("positive");
		List<BotSentenceBranch> mainBranchList = botSentenceBranchMapper.selectByExample(mainExample);
		if(null != mainBranchList && mainBranchList.size() > 0) {
			return mainBranchList.get(0);
		}
		return null;
	}
	
	private VoliceInfo getStartExplain(String processId) {
		//获取解释开场白
		BotSentenceBranchExample example = new BotSentenceBranchExample();
		example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("解释开场白").andBranchNameEqualTo("enter_branch");
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(example);
		if(null != branchList && branchList.size() > 0) {
			BotSentenceBranch branch = branchList.get(0);
			//获取当前branch对应的话术内容
			String resp = branch.getResponse();
			if(StringUtils.isNotBlank(resp) && !"[]".equals(resp)) {
				String[] respArray = resp.substring(1,resp.length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				return volice;
			}
		}
		return null;
	}
	
	
	public BotSentenceBranch getStartExplainBranch(String processId) {
		//获取解释开场白
		BotSentenceBranchExample startExample = new BotSentenceBranchExample();
		startExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("开场白").andNextEqualTo("解释开场白");
		List<BotSentenceBranch> startBranchList = botSentenceBranchMapper.selectByExample(startExample);
		
		if(null != startBranchList && startBranchList.size() > 0) {
			BotSentenceBranch branch = startBranchList.get(0);
			return branch;
		}
		return null;
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
	 * 生成新的lineName
	 * @param processId
	 * @return
	 */
	private String getNewLineName(String processId) {
		String lineName = line_prefix + "1";
		BotSentenceBranchExample queryExample = new BotSentenceBranchExample();
		queryExample.createCriteria().andProcessIdEqualTo(processId).andLineNameLike(line_prefix + "%");
		List<BotSentenceBranch> queryList = botSentenceBranchMapper.selectByExample(queryExample);
		if(null != queryList && queryList.size() > 0) {
			List<Integer> list = new ArrayList<>();
			for(BotSentenceBranch temp : queryList) {
				String currentName = temp.getLineName();
				int currentNum = new Integer(currentName.substring(line_prefix.length(), currentName.length()));
				list.add(currentNum);
			}
			
			int maxLineNum = Collections.max(list) + 1;
			lineName = line_prefix + maxLineNum;
		}
		return lineName;
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
			
			
			
			/*String resp = "[";
			String respname = "[";
			for(String temp : voliceIdList) {
				if(StringUtils.isBlank(temp)) {
					continue;
				}
				resp = resp + temp + ",";
				
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(temp));
				respname = respname + volice.getName() + ",";
				
			}
			resp = "[" + resp.substring(0, resp.length() -1) + "]";
			respname = "[" + respname.substring(0, respname.length() -1) + "]";
			
			String userId = UserUtil.getUserId();
			BotSentenceBranchExample example = new BotSentenceBranchExample();
			example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domainName).andBranchNameEqualTo("refuse_" + domainName);
			List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(example);
			if(null != list && list.size() > 0) {
				branch = list.get(0);
				branch.setResponse(resp);
				branch.setRespname(respname);
				branch.setLstUpdateTime(new Date(System.currentTimeMillis()));
				branch.setLstUpdateUser(userId);
				botSentenceBranchMapper.updateByPrimaryKey(branch);
			}else {
				branch.setResponse(resp);
				branch.setRespname(respname);
				branch.setBranchName("refuse_" + domainName);
				branch.setCrtTime(new Date(System.currentTimeMillis()));
				branch.setCrtUser(userId);
				branch.setDomain(domainName);
				branch.setIsShow("0");
				branch.setNext(domainName);
				branch.setEnd("结束");
				branch.setTemplateId(process.getTemplateId());
				branch.setProcessId(processId);
				
				//获取意图信息：来源：domin为拒绝的enter分支的intent
				BotSentenceBranchExample intentExample = new BotSentenceBranchExample();
				intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("拒绝").andBranchNameEqualTo("enter_branch");
				List<BotSentenceBranch> intentList = botSentenceBranchMapper.selectByExample(intentExample);
				if(null != intentList && intentList.size() > 0) {
					branch.setIntents(intentList.get(0).getIntents());
				}
				
				botSentenceBranchMapper.insert(branch);
			}*/
			
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
		
		
		/*List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			BotSentenceBranch refuseBranch = list.get(0);
			String resp = refuseBranch.getResponse();
			if(StringUtils.isNotBlank(resp) && !"[]".equals(resp.trim()) && resp.trim().startsWith("[") && resp.trim().endsWith("]")) {
				String[] respArray = resp.substring(1,resp.length()-1).split(",");
				String resp_new = "[";
				int index = -1;
				
				if(respArray.length ==1) {
					resp_new = "[]";
				}else {
					for(int i = 0 ; i < respArray.length ; i++) {
						if(voliceId.equals(respArray[i])) {
							index = i;
						}else {
							resp_new = resp_new + respArray[i] + ",";
						}
					}
					resp_new = resp_new.substring(0, resp_new.length() -1)  + "]";
				}
				
				
				//更新resp和respname值
				String respname = refuseBranch.getRespname();
				String respname_new = "[";
				if(StringUtils.isNotBlank(respname) && !"[]".equals(respname.trim()) && respname.trim().startsWith("[") && respname.trim().endsWith("]")) {
					String[] respnameArray = respname.substring(1,respname.length()-1).split(",");
					
					if(respnameArray.length == 1) {
						respname_new = "[]";
					}else {
						for(int i = 0 ; i < respnameArray.length ; i++) {
							if(i != index) {
								respname_new = respname_new + respnameArray[i] + ",";
							}
						}
					}
				}
				respname_new = respname_new.substring(0, respname_new.length() -1)  + "]";
				
				refuseBranch.setResponse(resp_new);
				refuseBranch.setRespname(respname_new);
				botSentenceBranchMapper.updateByPrimaryKey(refuseBranch);
		
			}
		}*/
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
			branchExample2.createCriteria().andProcessIdEqualTo(processId).andNextIn(domainNames);
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
					
					voliceInfoMapper.deleteByPrimaryKey(volice.getVoliceId());
					
					//删除TTS任务信息
					BotSentenceTtsTaskExample ttsExample = new BotSentenceTtsTaskExample();
					ttsExample.createCriteria().andProcessIdEqualTo(processId).andBusiIdEqualTo(volice.getVoliceId().toString());
					botSentenceTtsTaskMapper.deleteByExample(ttsExample);
					logger.info("删除TTS任务信息");
					
					//删除备用话术信息
					BotSentenceTtsBackupExample backExample = new BotSentenceTtsBackupExample();
					backExample.createCriteria().andProcessIdEqualTo(processId).andVoliceIdEqualTo(volice.getVoliceId());
					botSentenceTtsBackupMapper.deleteByExample(backExample);
					logger.info("删除备用话术信息");
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
		
		this.updateProcessState(processId, userId);
	}

	@Override
	@Transactional
	public void deleteBranch(String processId, String branchId, String userId) {
		//删除branch本身
		//BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);
		botSentenceBranchMapper.deleteByPrimaryKey(branchId);
		//String userId = UserUtil.getUserId();
		//如果当前branch为positive，则更新domain表的com_domain为空
		/*if("positive".equals(branch.getBranchName())) {
			BotSentenceDomainExample updateDomainExample = new BotSentenceDomainExample();
			updateDomainExample.createCriteria().andProcessIdEqualTo(processId).andComDomainEqualTo(branch.getDomain());
			List<BotSentenceDomain> domains = botSentenceDomainMapper.selectByExample(updateDomainExample);
			if(null != domains && domains.size() > 0) {
				BotSentenceDomain domain = domains.get(0);
				domain.setComDomain(null);
				domain.setLstUpdateTime(new Date(System.currentTimeMillis()));
				domain.setLstUpdateUser(userId);
				botSentenceDomainMapper.updateByPrimaryKey(domain);
			}
		}*/
		
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

	/*@Override
	@Transactional
	public String deleteDomainSimple(String domainId) {
		//获取当前domain向下的所有domain列表，并逐个删除
		List<BotSentenceBranch> list = new ArrayList<>(); 
		List<String> domainIdList = new ArrayList<>();
				
		BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(domainId);
		String processId = domain.getProcessId();
		
		//添加当前domain本身
		domainIdList.add(domain.getDomainName());
		
		if(StringUtils.isBlank(domain.getComDomain())) {
			deleteOneDomain(processId, domainId);
		}else {
			//判断是否有其它domain与当前domain指向同一个下级节点，如果有，则只删除当前domain本身，如果没有，则删除向下所有节点
			BotSentenceDomainExample existExample = new BotSentenceDomainExample();
			existExample.createCriteria().andProcessIdEqualTo(processId).andComDomainEqualTo(domain.getComDomain()).andCategoryEqualTo("1");
			int num = botSentenceDomainMapper.countByExample(existExample);
			if(num > 1) {
				deleteOneDomain(processId, domainId);
			}else {
				//先查询当前domain所有指向的next节点
				BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
				branchExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain.getDomainName());
				list = botSentenceBranchMapper.selectByExample(branchExample);

				while(null != list && list.size() > 0) {
					
					if(null != list && list.size() > 0) {
						List<String> domainNameList = new ArrayList<>();
						
						for(BotSentenceBranch branch : list) {
							if(StringUtils.isNotBlank(branch.getNext())) {
								
								if(!domainIdList.contains(branch.getNext())) {
									domainIdList.add(branch.getNext());
									domainNameList.add(branch.getNext());
								}
							}
						}
						if(domainNameList.size()>0) {
							BotSentenceBranchExample branchExample2 = new BotSentenceBranchExample();
							branchExample2.createCriteria().andProcessIdEqualTo(processId).andDomainIn(domainNameList);
							list = botSentenceBranchMapper.selectByExample(branchExample2);
						}else {
							break;
						}
					}
				}
				
				BotSentenceDomainExample domainExample = new BotSentenceDomainExample();
				domainExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameIn(domainIdList).andCategoryEqualTo("1");
				List<BotSentenceDomain> domain_list = botSentenceDomainMapper.selectByExample(domainExample);
				if(null != domain_list && domain_list.size() > 0) {
					for(BotSentenceDomain temp : domain_list) {
						deleteOneDomain(processId, temp.getDomainId());
					}
				}
			}
		}
			
		
		
		return processId;
	}*/
	
	
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
		
		//删除原来comdomain指向当前domain的数据
		/*BotSentenceDomainExample domaninExample = new BotSentenceDomainExample();
		domaninExample.createCriteria().andProcessIdEqualTo(processId).andComDomainEqualTo(domain.getDomainName());
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domaninExample);
		if(null != domainList && domainList.size() > 0) {
			for(BotSentenceDomain temp : domainList) {
				temp.setComDomain(null);
				temp.setLstUpdateTime(new Date(System.currentTimeMillis()));
				temp.setLstUpdateUser(UserUtil.getUserId());
				botSentenceDomainMapper.updateByPrimaryKey(temp);
			}
		}*/
		
		
		
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
			//VoliceInfo startExplainVolice = getStartExplain(processId);
			for(BotSentenceDomain temp : levelList) {
				
				FlowNode node = new FlowNode();
				node.setId(temp.getDomainId());
				node.setLabel(temp.getDomainName());
				node.setX(temp.getPositionX());
				node.setY(temp.getPositionY());
				
				node.setType(temp.getType());//节点类型
				node.setLabel(temp.getDomainName());//设置domain名称
				//如果当前节点为场白，则把解释开场白作为开场白的的一个属性
				/*if(null != startExplainVolice && Constant.DOMAIN_TYPE_START.equals(temp.getType())) {
					node.setStartExplainText(startExplainVolice.getContent());
					node.setStartExplainUrl(startExplainVolice.getVoliceUrl());
					
					//如果当前节点是开始节点，则查询解释开场白的意图信息
					BotSentenceBranch branch = getStartExplainBranch(processId);
					
					//查询关键词库列表
					List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(branch.getBranchId());
					node.setStartExplainIntentList(intentList);
					
					//设置解释开场白的挽回列表add by 2018-09-29
					node.setStartExplainDomainName("解释开场白");
					
					List<RefuseBranchVO> refuses = new ArrayList<>();
					BotSentenceBranchExample refuseBranchExample = new BotSentenceBranchExample();
					refuseBranchExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("解释开场白").andBranchNameEqualTo("refuse_解释开场白");
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
					node.setStartExplainRefuses(refuses);
				}*/
				
				
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
						/*String intents = lineBranch.getIntents();
						if(StringUtils.isNotBlank(intents)) {
							String intent = intents.split(",")[0];
							BotSentenceIntent botSentenceIntent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intent));
							
							String keyword = botSentenceIntent.getKeywords();
							
							if(StringUtils.isNotBlank(keyword)) {

								List<String> keyword_list = BotSentenceUtil.getKeywords(keyword);
								if(null != keyword_list && keyword_list.size() > 0) {
									//edge.setText(keyword_list.get(0).replace("\"", ""));
									edge.setKeyWords(keyword_list.get(0).replace("\"", ""));
								}
							}
						}*/
						
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
		
		
		//4.非开场白节点，其它节点都应该有其它节点指向它
		/*if(null != flow.getNodes() && flow.getNodes().size() > 0) {
			//处理卡片信息
			for(FlowNode node : flow.getNodes()) {
				if(!targets.contains(node.getId()) && !Constant.DOMAIN_TYPE_START.equals(node.getType())) {
					throw new CommonException("节点[" + node.getLabel() + "]连线不完整!");
				}
			}
		}*/
		
		
		
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
			int count1 = voliceInfoMapper.countByExample(voliceExample);
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
			
			int total = count1 + unfinish_tts_num + unfinisn_back_num;
			
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
					if(org.apache.commons.lang.StringUtils.isNotBlank(keywords)) {
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
	@Transactional
	public void generateTTS(List<VoliceInfoExt> list2, String processId, String userId) {
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
					ttsExample.createCriteria().andProcessIdEqualTo(processId).andBusiIdEqualTo(temp.getVoliceId().toString()).andIsParamEqualTo(Constant.IS_PARAM_FALSE);
					List<BotSentenceTtsTask> tasklist = botSentenceTtsTaskMapper.selectByExample(ttsExample);
					if(null != tasklist && tasklist.size() > 0) {
						for(BotSentenceTtsTask ttsTask : tasklist) {
							botSentenceTtsService.saveAndSentTTS(ttsTask, processId, true, userId);
						}
					}
				}else {
					BotSentenceTtsTask ttsTask = new BotSentenceTtsTask();
					ttsTask.setContent(temp.getContent());
					ttsTask.setProcessId(processId);
					ttsTask.setBusiId(temp.getVoliceId().toString());
					ttsTask.setBusiType("03");
					
					botSentenceTtsService.saveAndSentTTS(ttsTask, processId, isNeedTts, userId);
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
				botSentenceTtsService.saveAndSentTTS(ttsTask, processId, false, userId);
			}
		}
	}
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
		
		
		/*voliceInfoExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameIn(domains).andDomainNameNotIn(ignoreDomainList);
		List<VoliceInfo> list = voliceInfoMapper.selectByExample(voliceInfoExample);
		for(int i = 0 ; i < list.size() ; i++) {
			VoliceInfo volice = list.get(i);
			if(Constant.VOLICE_TYPE_REFUSE.equals(volice.getType())) {
				continue;
			}
			
			CommonDialogVO vo = new CommonDialogVO();
			vo.setHuashu(volice.getContent());//话术
			vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
			vo.setYujin(volice.getDomainName());//语境
			vo.setLuoji("触发\"" + volice.getDomainName() +"\"流程");//逻辑
			vo.setTitle(volice.getDomainName());
			//vo.setBranchId(branch.getBranchId());
			vo.setVoliceId(volice.getVoliceId());
			vo.setTemplateId(volice.getTemplateId());
			vo.setProcessId(processId);
			//vo.setBranchName(branch.getBranchName());
			vo.setVoliceName(volice.getName());
			vo.setFlag(volice.getFlag());
			resultList.add(vo);
		}*/
		
		
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
		List<BotSentenceTemplateTradeVO> results = new ArrayList<>();
		
		List<String> industryIdList = new ArrayList<>();
		List<String> parentIndustryIdList = new ArrayList<>();
		
		//ReturnData<SysUser> data=iAuth.getUserById(new Long(userId));
		ReturnData<SysOrganization> org = iAuth.getOrgByUserId(new Long(userId));
		String orgCode=org.getBody().getCode();
		ReturnData<List<String>> returnData= orgService.getIndustryByOrgCode(orgCode);
		industryIdList = returnData.getBody();
		/*UserAccountTradeRelationExample example1 = new UserAccountTradeRelationExample();
		example1.createCriteria().andAccountNoEqualTo(accountNo);
		List<UserAccountTradeRelation> relationList = userAccountTradeRelationMapper.selectByExample(example1);*/
		if(null != industryIdList && industryIdList.size() > 0) {
			//过滤掉二级行业下没有模板的数据
			for(int i = industryIdList.size()-1 ; i >=0 ; i--) {
				BotSentenceTemplateExample example = new BotSentenceTemplateExample();
				example.createCriteria().andIndustryIdLike(industryIdList.get(i) + "%");
				int num = botSentenceTemplateMapper.countByExample(example);
				if(num == 0) {
					industryIdList.remove(i);
				}
			}
		}
		
		for(String industryId : industryIdList) {
			//获取上级行业
			BotSentenceTrade trade = getBotSentenceTrade(industryId);
			if(null != trade && !parentIndustryIdList.contains(trade.getParentId())) {
				parentIndustryIdList.add(trade.getParentId());
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
					if(industryIdList.contains(trade.getIndustryId())) {
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
				}
				parentVo.setChildren(tradeVOList);
			}
			results.add(parentVo);
		}
		return results;
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
		
		List<String> industryIdList = new ArrayList<>();
		List<String> parentIndustryIdList = new ArrayList<>();
		
		ReturnData<List<String>> returnData= orgService.getIndustryByOrgCode(orgCode);
		industryIdList = returnData.getBody();
		if(null != industryIdList && industryIdList.size() > 0) {
			//过滤掉二级行业下没有模板的数据
			for(int i = industryIdList.size()-1 ; i >=0 ; i--) {
				BotSentenceTemplateExample example = new BotSentenceTemplateExample();
				example.createCriteria().andIndustryIdLike(industryIdList.get(i) + "%");
				int num = botSentenceTemplateMapper.countByExample(example);
				if(num == 0) {
					industryIdList.remove(i);
				}
			}
		}
		
		for(String industryId : industryIdList) {
			//获取上级行业
			BotSentenceTrade trade = getBotSentenceTrade(industryId);
			if(null != trade && !parentIndustryIdList.contains(trade.getParentId())) {
				parentIndustryIdList.add(trade.getParentId());
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
					if(industryIdList.contains(trade.getIndustryId())) {
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
				}
				parentVo.setChildren(tradeVOList);
			}
			results.add(parentVo);
		}
		return results;
	}
	
	
	@Autowired
	private BotSentenceProcessExtMapper botSentenceProcessExtMapper;
	
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
	
	public void test() {
		
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
}
