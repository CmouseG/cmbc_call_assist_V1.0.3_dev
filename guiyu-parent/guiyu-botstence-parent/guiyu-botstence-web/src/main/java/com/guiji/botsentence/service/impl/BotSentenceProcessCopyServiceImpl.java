package com.guiji.botsentence.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.*;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.dao.entity.BotSentenceShareAuthExample.Criteria;
import com.guiji.botsentence.dao.entity.ext.IntentVO;
import com.guiji.botsentence.dao.entity.ext.VoliceInfoVO;
import com.guiji.botsentence.dao.ext.BotSentenceBranchExtMapper;
import com.guiji.botsentence.dao.ext.BotSentenceDomainExtMapper;
import com.guiji.botsentence.dao.ext.BotSentenceShareAuthExtMapper;
import com.guiji.botsentence.service.IBotSentenceProcessCopyService;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.vo.AvaliableOrgVO;
import com.guiji.botsentence.vo.BotSentenceShareVO;
import com.guiji.common.exception.CommonException;
import com.guiji.component.client.util.BeanUtil;
import com.guiji.component.client.util.Pinyin4jUtil;
import com.guiji.component.client.util.SystemUtil;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 话术市场复制、分享功能相关服务类
 * @author zhangpeng
 * @date 2019-03-02 10:10
 */
@Service
public class BotSentenceProcessCopyServiceImpl implements IBotSentenceProcessCopyService {

	private Logger logger = LoggerFactory.getLogger(BotSentenceProcessServiceImpl.class);
	
	@Resource
	private IOrg orgService;
	
	@Resource
	private IAuth iAuth;
	
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
	private com.guiji.botsentence.dao.VoliceInfoMapper voliceInfoMapper;
	
	@Resource
	private BotSentenceAdditionMapper botSentenceAdditionMapper;
	
	@Resource
	private BotSentenceProcessServiceImpl botSentenceProcessServiceImpl;
	
	@Resource
	private BotSentenceGradeMapper botSentenceGradeMapper;
	
	@Resource
	private BotSentenceGradeRuleMapper botSentenceGradeRuleMapper;
	
	@Resource
	private BotSentenceOptionsMapper botSentenceOptionsMapper;
	
	@Resource
	private BotSentenceOptionsLevelMapper botSentenceOptionsLevelMapper;
	
	@Resource
	private BotSentenceSurveyMapper botSentenceSurveyMapper;
	
	@Resource
	private BotSentenceSurveyIntentMapper botSentenceSurveyIntentMapper;
	
	@Resource
	private BotSentenceTtsBackupMapper botSentenceTtsBackupMapper;
	
	@Resource
	private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;
	
	@Resource
	private BotSentenceTtsParamMapper botSentenceTtsParamMapper;
	
	@Resource
	private VoliceServiceImpl voliceServiceImpl;
	
	@Resource
	private BotSentenceShareAuthMapper botSentenceShareAuthMapper;
	
	@Resource
	private BotSentenceShareHistoryMapper botSentenceShareHistoryMapper;
	
	@Resource
	private BotSentenceShareAuthExtMapper botSentenceShareAuthExtMapper;
	
	@Override
	@Transactional
	public String copy(String processId, String orgCode, String userId, String tempalteName) {
		logger.info("当前用户id: " + userId);
		ReturnData<SysUser> data=iAuth.getUserById(new Long(userId));
		orgCode=data.getBody().getOrgCode();
		String orgName = data.getBody().getOrgName();
		
		logger.info("当前机构code: " + orgCode);
		
		ReturnData<SysUser> userData = iAuth.getUserById(new Long(userId));
		
		long time1 = System.currentTimeMillis();
		logger.info("============" + time1);
		if(StringUtils.isBlank(tempalteName) || StringUtils.isBlank(processId)) {
			throw new CommonException("创建模板失败，请求参数为空!");
		}
		
		//查询已生效的话术模板
		BotSentenceProcess botSentenceTemplate = botSentenceProcessMapper.selectByPrimaryKey(processId);
		if(null == botSentenceTemplate) {
			throw new CommonException("复制模板失败， 该基础模板不存在!");
		}
		
		//保存话术流程信息
		BotSentenceProcess process = new BotSentenceProcess();
		
		logger.info("创建新话术模板");
		//生成模板编号
		Pinyin4jUtil util= new Pinyin4jUtil();
		String pingyin = "";
		try {
			pingyin = util.toPinYinLowercase(tempalteName);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			logger.error("生成首字母异常...", e);
			pingyin = SystemUtil.getSysJournalNo(5, false);
		}
		
		String templateId = pingyin + "_" + SystemUtil.getSysJournalNo(5, true) + "_en";
		process.setOrgCode(orgCode);
		process.setOrgName(orgName);
		process.setUserName(userData.getBody().getUsername());
		process.setState("00");//制作中
		process.setVersion("0");//初始版本为0
		process.setAccountNo(userId);//所属账号
		process.setIndustry(botSentenceTemplate.getIndustry());//所属行业
		process.setIndustryId(botSentenceTemplate.getIndustryId());
		process.setTemplateId(templateId);//模板编号
		process.setTemplateName(tempalteName);
		process.setSoundType(botSentenceTemplate.getSoundType());
		process.setOldProcessId(processId);//设置依赖的流程编号
		process.setTemplateType("01");//自定义模板
		logger.info("生成的模板编号为: " + templateId);
		process.setCrtTime(new Date(System.currentTimeMillis()));
		process.setCrtUser(userId);
		
		botSentenceProcessMapper.insert(process);
		logger.info("保存话术流程信息成功...");
		logger.info("新生成的话术流程编号是: " + process.getProcessId());
		
		long time2 = System.currentTimeMillis();
		logger.info("======保存话术流程信息耗时======" + (time2-time1));
		
		//保存domain信息
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(example);
		
		//暂时先过滤掉这几个域-
		List<String> ignoreDomainList = new ArrayList<>();
		
		List<BotSentenceDomain> domainList_new = new ArrayList<>();
		if(null != domainList && domainList.size() > 0) {
			for(BotSentenceDomain temp : domainList) {
				
				if(ignoreDomainList.contains(temp.getDomainName())) {
					continue;
				}
				
				BotSentenceDomain vo = new BotSentenceDomain();
				BeanUtil.copyProperties(temp, vo);
				//vo.setComDomain(null);//在审批时才需要设置默认主流程
				vo.setTemplateId(process.getTemplateId());
				vo.setCrtTime(new Date(System.currentTimeMillis()));
				vo.setCrtUser(userId);
				vo.setProcessId(process.getProcessId());
				domainList_new.add(vo);
			}
			botSentenceDomainExtMapper.batchInsert(domainList_new);
			logger.info("共保domain信息: " + domainList_new.size());
		}
		
		logger.info("保存domain信息成功...");
		
		long time3 = System.currentTimeMillis();
		logger.info("======保存domain信息耗时======" + (time3-time2));
		
		//保存volice信息
		VoliceInfoExample voliceExample = new VoliceInfoExample();
		voliceExample.createCriteria().andProcessIdEqualTo(processId);
		List<VoliceInfo> voliceTempList = voliceInfoMapper.selectByExample(voliceExample);
		List<VoliceInfoVO> voliceList = new ArrayList<>();
		
		for(VoliceInfo voliceTemp : voliceTempList) {
			VoliceInfoVO voliceInfoVO = new VoliceInfoVO();
			voliceTemp.setCrtTime(new Date(System.currentTimeMillis()));
			voliceTemp.setCrtUser(userId);
			voliceTemp.setProcessId(process.getProcessId());
			BeanUtil.copyProperties(voliceTemp, voliceInfoVO);
			voliceInfoVO.setOldVoliceId(voliceTemp.getVoliceId());
			voliceInfoVO.setTimes(voliceTemp.getTimes());
			voliceInfoVO.setTemplateId(process.getTemplateId());
			voliceList.add(voliceInfoVO);
		}
		
		//保存brach信息
		BotSentenceBranchExample botSentenceBranchExample = new BotSentenceBranchExample();
		botSentenceBranchExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(botSentenceBranchExample);
		List<BotSentenceBranch> branchList_new = new ArrayList<>();
		List<Long> respIdList = new ArrayList<>();
		
		Map<Long, Long> voliceMap = new HashMap<>();
		
		if(null != branchList && branchList.size() > 0) {
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
				
				vo.setCrtTime(new Date(System.currentTimeMillis()));
				vo.setCrtUser(userId);
				vo.setProcessId(process.getProcessId());
				vo.setTemplateId(process.getTemplateId());
				vo.setBranchId(null);
				branchList_new.add(vo);
			}
			
			BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
			intentExample.createCriteria().andProcessIdEqualTo(processId);
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
				intentsList.add(intentVO);
			}
			
			//批量保存意图数据
			intentsList = botSentenceProcessServiceImpl.batchSaveIntent(intentsList);
			
			//批量保存录音数据
			voliceList = botSentenceProcessServiceImpl.batchSaveVoliceInfo(voliceList);
			
			for(VoliceInfoVO vo : voliceList) {
				voliceMap.put(vo.getOldVoliceId(), vo.getVoliceId());
			}
			Map<String, String> intentIdMap = new HashMap<>();
			for(IntentVO vo : intentsList) {
				intentIdMap.put(vo.getOldIntentId()+"", vo.getId().toString());
			}
			
			
			for(BotSentenceBranch branch : branchList_new) {
				//设置branch的intents数据
				String oldIntent = branch.getIntents();
				String newIntent = "";
				List<String> newIntentList = new ArrayList<>();
				List<String> newIntentList2 = new ArrayList<>();
				List<String> oldIntentIdList = new ArrayList<>();
				//newIntentList.add(oldIntent);
				if(StringUtils.isNotBlank(oldIntent)) {
					oldIntentIdList = BotSentenceUtil.StringToList(oldIntent);
					for(String oldIntentId : oldIntentIdList) {
						if(intentIdMap.containsKey(oldIntentId)) {
							newIntentList.add(intentIdMap.get(oldIntentId));
						}
					}
					newIntent = BotSentenceUtil.listToString(newIntentList);
				}else {
					newIntent = null;
				}
				branch.setIntents(newIntent);
				
				//设置branch的resp数据
				String oldResp = branch.getResponse();
				String newResp = "";
				List<String> newRespList = new ArrayList<>();
				List<String> newRespList2 = new ArrayList<>();
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
		BotSentenceGradeExample gradeExample = new BotSentenceGradeExample();
		gradeExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceGrade> gradeList = botSentenceGradeMapper.selectByExample(gradeExample);
		if(null != gradeList && gradeList.size() > 0) {
			BotSentenceGrade newGrade = gradeList.get(0);
			newGrade.setId(null);
			newGrade.setProcessId(process.getProcessId());
			newGrade.setTemplateId(process.getTemplateId());
			newGrade.setCrtTime(new Date(System.currentTimeMillis()));
			newGrade.setCrtUser(userId);
			botSentenceGradeMapper.insert(newGrade);
			
			//查询意向规则
			BotSentenceGradeRuleExample gradeRuleExample = new BotSentenceGradeRuleExample();
			gradeRuleExample.createCriteria().andProcessIdEqualTo(processId);
			List<BotSentenceGradeRule> gradeRuleList = botSentenceGradeRuleMapper.selectByExample(gradeRuleExample);
			if(null != gradeRuleList && gradeRuleList.size() > 0) {
				for(BotSentenceGradeRule rule : gradeRuleList) {
					rule.setId(null);
					rule.setProcessId(process.getProcessId());
					rule.setTemplateId(process.getTemplateId());
					rule.setCrtTime(new Date(System.currentTimeMillis()));
					rule.setCrtUser(userId);
					botSentenceGradeRuleMapper.insert(rule);
				}
			}
			logger.info("保存意向标签信息成功");
		}
		
		
		//保存变量相关数据
		BotSentenceOptionsExample optionsExample = new BotSentenceOptionsExample();
		optionsExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceOptions> optionsList = botSentenceOptionsMapper.selectByExample(optionsExample);
		if(null != optionsList && optionsList.size() > 0) {
			BotSentenceOptions newOption = optionsList.get(0);
			newOption.setOptionsId(null);
			newOption.setProcessId(process.getProcessId());
			newOption.setTemplateId(process.getTemplateId());
			newOption.setCrtTime(new Date(System.currentTimeMillis()));
			newOption.setCrtUser(userId);
			botSentenceOptionsMapper.insert(newOption);
		}
		
		//保存变量优先级数据
		BotSentenceOptionsLevelExample optionsLevelExample = new BotSentenceOptionsLevelExample();
		optionsLevelExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceOptionsLevel> optionsLevelList = botSentenceOptionsLevelMapper.selectByExample(optionsLevelExample);
		if(null != optionsLevelList && optionsLevelList.size() > 0) {
			for(BotSentenceOptionsLevel newOptionsLevel : optionsLevelList) {
				newOptionsLevel.setOptionsLevelId(null);
				newOptionsLevel.setProcessId(process.getProcessId());
				newOptionsLevel.setTemplateId(process.getTemplateId());
				newOptionsLevel.setCrtTime(new Date(System.currentTimeMillis()));
				newOptionsLevel.setCrtUser(userId);
				botSentenceOptionsLevelMapper.insert(newOptionsLevel);
			}
		}
		
		//保存变量回访模板数据
		BotSentenceSurveyExample surveyExample = new BotSentenceSurveyExample();
		surveyExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceSurvey> surveyList = botSentenceSurveyMapper.selectByExample(surveyExample);
		if(null != surveyList && surveyList.size() > 0) {
			for(BotSentenceSurvey newSurvey : surveyList) {
				newSurvey.setSurveyId(null);
				newSurvey.setProcessId(process.getProcessId());
				newSurvey.setTemplateId(process.getTemplateId());
				newSurvey.setCrtTime(new Date(System.currentTimeMillis()));
				newSurvey.setCrtUser(userId);
				botSentenceSurveyMapper.insert(newSurvey);
			}
		}
		
		BotSentenceSurveyIntentExample surveyIntentExample = new BotSentenceSurveyIntentExample();
		surveyIntentExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceSurveyIntent> surveyIntentList = botSentenceSurveyIntentMapper.selectByExample(surveyIntentExample);
		if(null != surveyIntentList && surveyIntentList.size() > 0) {
			for(BotSentenceSurveyIntent newSurveyIntent : surveyIntentList) {
				newSurveyIntent.setSurveyIntentId(null);
				newSurveyIntent.setProcessId(process.getProcessId());
				newSurveyIntent.setTemplateId(process.getTemplateId());
				newSurveyIntent.setCrtTime(new Date(System.currentTimeMillis()));
				newSurveyIntent.setCrtUser(userId);
				botSentenceSurveyIntentMapper.insert(newSurveyIntent);
			}
		}
		logger.info("保存字段相关数据完成...");
		
		
		//保存TTS相关数据
		BotSentenceTtsBackupExample ttsBackupExample = new BotSentenceTtsBackupExample();
		ttsBackupExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceTtsBackup> ttsBackupList = botSentenceTtsBackupMapper.selectByExample(ttsBackupExample);
		if(null != ttsBackupList && ttsBackupList.size() > 0) {
			for(BotSentenceTtsBackup newTtsBackup : ttsBackupList) {
				newTtsBackup.setBackupId(null);
				newTtsBackup.setProcessId(process.getProcessId());
				newTtsBackup.setTemplateId(process.getTemplateId());
				Long newVoliceId = voliceMap.get(newTtsBackup.getVoliceId());
				if(null != newVoliceId) {
					newTtsBackup.setVoliceId(newVoliceId);
				}else {
					newTtsBackup.setVoliceId(null);
				}
				newTtsBackup.setCrtTime(new Date(System.currentTimeMillis()));
				newTtsBackup.setCrtUser(userId);
				botSentenceTtsBackupMapper.insert(newTtsBackup);
			}
		}
		
		BotSentenceTtsParamExample ttsParamExample = new BotSentenceTtsParamExample();
		ttsParamExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceTtsParam> ttsParamList = botSentenceTtsParamMapper.selectByExample(ttsParamExample);
		if(null != ttsParamList && ttsParamList.size() > 0) {
			for(BotSentenceTtsParam newTtsParam : ttsParamList) {
				newTtsParam.setParamId(null);
				newTtsParam.setProcessId(process.getProcessId());
				newTtsParam.setTemplateId(process.getTemplateId());
				newTtsParam.setCrtTime(new Date(System.currentTimeMillis()));
				newTtsParam.setCrtUser(userId);
				botSentenceTtsParamMapper.insert(newTtsParam);
			}
		}
		
		BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
		ttsTaskExample.createCriteria().andProcessIdEqualTo(processId).andBusiTypeEqualTo(Constant.TTS_BUSI_TYPE_01);
		List<BotSentenceTtsTask> ttsTaskList = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);
		if(null != ttsTaskList && ttsTaskList.size() > 0) {
			for(BotSentenceTtsTask newTtsTask : ttsTaskList) {
				newTtsTask.setId(null);
				newTtsTask.setProcessId(process.getProcessId());
				Long newBusiId = voliceMap.get(new Long(newTtsTask.getBusiId()));
				if(null != newBusiId) {
					newTtsTask.setBusiId(newBusiId.toString());
				}else {
					newTtsTask.setBusiId(null);
				}
				String oldSeq = newTtsTask.getSeq();
				if(StringUtils.isNotBlank(oldSeq) && oldSeq.contains("_")) {
					String [] array = oldSeq.split("_");
					String voliceId = array[0];
					Long newVoliceId = voliceMap.get(new Long(voliceId));
					String newSeq = newVoliceId + "_" + array[1];
					newTtsTask.setSeq(newSeq);
				}else {
					newTtsTask.setSeq(null);
				}
				newTtsTask.setCrtTime(new Date(System.currentTimeMillis()));
				newTtsTask.setCrtUser(userId);
				botSentenceTtsTaskMapper.insert(newTtsTask);
			}
		}
		logger.info("tts相关数据保存完成...");
		
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
		
		logger.info("创建新的话术模板成功,流程编号: " + process.getProcessId());
		
		long time10 = System.currentTimeMillis();
		logger.info("======复制整个话术共计耗时======" + (time10-time1));

		
		BotSentenceShareAuthExample shareExample = new BotSentenceShareAuthExample();
		shareExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceShareAuth> list = botSentenceShareAuthMapper.selectByExample(shareExample);
		if(null != list && list.size() > 0) {
			BotSentenceShareAuth sharer = list.get(0);
			//判断当前用户是否有过使用该模板，如果没有，则新增热度+1
			BotSentenceShareHistoryExample historyExample = new BotSentenceShareHistoryExample();
			historyExample.createCriteria().andProcessIdEqualTo(processId).andCrtUserEqualTo(userId);
			int num = botSentenceShareHistoryMapper.countByExample(historyExample);
			if(num == 0) {
				if(null != sharer.getShareCount()) {
					sharer.setShareCount(sharer.getShareCount() + 1);
				}else {
					sharer.setShareCount(1);
				}
			}
			botSentenceShareAuthMapper.updateByPrimaryKey(sharer);
			
			BotSentenceShareVO share = new BotSentenceShareVO();
			share.setProcessId(processId);
			share.setTemplateId(sharer.getTemplateId());
			share.setTemplateName(sharer.getTemplateName());
			share.setUserName(sharer.getNickName());
			share.setUserId(userId);
			saveBotStenceSharHistory(share);
		}
		logger.info("保存分享使用记录完成...");
		
		return process.getProcessId();
	}

	@Override
	public List<BotSentenceShareAuth> queryBotstenceMarket(String userId, int pageSize, int pageNo, String templateName, String nickName, String orderType) {
		//获取机构
		//ReturnData<SysUser> data=iAuth.getUserById(new Long(userId));
		ReturnData<SysOrganization> data = iAuth.getOrgByUserId(new Long(userId));
		String orgCode=data.getBody().getCode();

		BotSentenceShareAuthExample example = new BotSentenceShareAuthExample();
		Criteria criteria = example.createCriteria().andTypeEqualTo("00").andSharedEqualTo(true);
		if(StringUtils.isNotBlank(templateName)) {
			criteria.andTemplateNameLike("%"+templateName+"%");
		}
		if(StringUtils.isNotBlank(nickName)) {
			criteria.andNickNameLike("%"+nickName+"%");
		}
		
		//String orgCode = "1.%";
		Criteria criteria1 = example.createCriteria().andTypeEqualTo("01").andSharedEqualTo(true);;
		criteria1.andAvailableOrgLike(orgCode + ",%");
		if(StringUtils.isNotBlank(templateName)) {
			criteria1.andTemplateNameLike("%"+templateName+"%");
		}
		if(StringUtils.isNotBlank(nickName)) {
			criteria1.andNickNameLike("%"+nickName+"%");
		}
		
		example.or(criteria1);
		
		if(StringUtils.isBlank(orderType)) {//默认按照时间排序
			example.setOrderByClause(" crt_time desc");
		}else if("0".equals(orderType)){
			example.setOrderByClause(" crt_time desc");
		}else if("1".equals(orderType)){
			example.setOrderByClause(" share_count desc");
		}
		
		
		//计算分页
		int limitStart = (pageNo-1)*pageSize;
		int limitEnd = pageSize;
		example.setLimitStart(limitStart);
		example.setLimitEnd(limitEnd);
		
		//List<BotSentenceShareAuth> list = botSentenceShareAuthExtMapper.queryBotSentenceShareMarket(orgCode, templateName, nickName);
		List<BotSentenceShareAuth> list = botSentenceShareAuthMapper.selectByExample(example);
		return list;
	}

	@Override
	@Transactional
	public void saveBotStenceShare(BotSentenceShareVO share) {
		if(StringUtils.isBlank(share.getType()) || StringUtils.isBlank(share.getTemplateName()) 
				|| StringUtils.isBlank(share.getProcessId()) || StringUtils.isBlank(share.getUserName())) {
			throw new CommonException("分享失败，请求参数不完整!");
		}
		if(Constant.SHARE_TYPE_01.equals(share.getType())) {
			if(null == share.getOrgCodeList()) {
				throw new CommonException("分享失败，机构列表为空!");
			}
		}
		BotSentenceShareAuthExample example = new BotSentenceShareAuthExample();
		example.createCriteria().andProcessIdEqualTo(share.getProcessId());
		List<BotSentenceShareAuth> list = botSentenceShareAuthMapper.selectByExample(example);
		
		BotSentenceShareAuth botSentenceShare = new BotSentenceShareAuth();
		
		if(null != list && list.size() > 0) {
			botSentenceShare = list.get(0);
			botSentenceShare.setLstUpdateUser(share.getUserId());
			botSentenceShare.setLstUpdateTime(new Date(System.currentTimeMillis()));
		}else {
			botSentenceShare.setShareCount(0);
			botSentenceShare.setCrtUser(share.getUserId());
			botSentenceShare.setCrtTime(new Date(System.currentTimeMillis()));
		}
		botSentenceShare.setNickName(share.getUserName());
		botSentenceShare.setProcessId(share.getProcessId());
		botSentenceShare.setTemplateId(share.getTemplateId());
		botSentenceShare.setTemplateName(share.getTemplateName());
		botSentenceShare.setType(share.getType());
		botSentenceShare.setShared(true);
		if(Constant.SHARE_TYPE_01.equals(share.getType())) {//特定用户，即指定的机构列表
			List<String> orgCodeList = share.getOrgCodeList();
			/*String avaliableOrg = "";
			for(String temp : orgCodeList) {
				avaliableOrg = avaliableOrg + "," + temp + ",";
			}*/
			botSentenceShare.setAvailableOrg(BotSentenceUtil.listToString(orgCodeList) + ",");
		}else {
			botSentenceShare.setAvailableOrg(null);
		}
		
		if(null != list && list.size() > 0) {
			botSentenceShareAuthMapper.updateByPrimaryKey(botSentenceShare);
		}else {
			botSentenceShareAuthMapper.insert(botSentenceShare);
		}
	}

	@Override
	public void saveBotStenceSharHistory(BotSentenceShareVO share) {
		BotSentenceShareHistory history = new BotSentenceShareHistory();
		history.setNickName(share.getUserName());
		history.setProcessId(share.getProcessId());
		history.setTemplateId(share.getTemplateId());
		history.setTemplateName(share.getTemplateName());
		history.setCrtTime(new Date(System.currentTimeMillis()));
		history.setCrtUser(share.getUserId());
		botSentenceShareHistoryMapper.insert(history);
	}

	@Override
	public int countBotstenceMarket(String userId, String templateName, String nickName) {

		//获取机构
		ReturnData<SysOrganization> data = iAuth.getOrgByUserId(new Long(userId));
		String orgCode=data.getBody().getCode();
		BotSentenceShareAuthExample example = new BotSentenceShareAuthExample();
		Criteria criteria = example.createCriteria().andTypeEqualTo("00").andSharedEqualTo(true);;
		if(StringUtils.isNotBlank(templateName)) {
			criteria.andTemplateNameLike("%"+templateName+"%");
		}
		if(StringUtils.isNotBlank(nickName)) {
			criteria.andNickNameLike("%"+nickName+"%");
		}
		//String orgCode = "1.%";
		Criteria criteria1 = example.createCriteria().andTypeEqualTo("01").andSharedEqualTo(true);;
		criteria1.andAvailableOrgLike(orgCode + ",%");
		if(StringUtils.isNotBlank(templateName)) {
			criteria.andTemplateNameLike("%"+templateName+"%");
		}
		if(StringUtils.isNotBlank(nickName)) {
			criteria.andNickNameLike("%"+nickName+"%");
		}
		example.or(criteria1);
		int num = botSentenceShareAuthMapper.countByExample(example);
		return num;
	}

	/**
	 * 查询话术已经分享的企业用户
	 */
	@Override
	public List<AvaliableOrgVO> queryAvaliableOrgList(String processId) {
		List<AvaliableOrgVO> resultList = new ArrayList<>();
		
		BotSentenceShareAuthExample example = new BotSentenceShareAuthExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceShareAuth> list = botSentenceShareAuthMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			BotSentenceShareAuth share = list.get(0);
			String avaliableOrg = share.getAvailableOrg();
			if(StringUtils.isNotBlank(avaliableOrg)) {
				String [] array = avaliableOrg.split(",");
				for(String orgCode : array) {
					AvaliableOrgVO vo = new AvaliableOrgVO();
					ReturnData<SysOrganization> result = orgService.getOrgByCode(orgCode);
					if(null != result && null != result.getBody()) {
						vo.setOrgCode(result.getBody().getCode());
						vo.setOrgName(result.getBody().getName());
						resultList.add(vo);
					}
				}
			}
		}
		return resultList;
	}

	@Override
	public void cancelShare(String processId, String userId) {
		BotSentenceShareAuthExample example = new BotSentenceShareAuthExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceShareAuth> list = botSentenceShareAuthMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			BotSentenceShareAuth share = list.get(0);
			share.setShared(false);
			share.setLstUpdateTime(new Date(System.currentTimeMillis()));
			share.setLstUpdateUser(userId);
			botSentenceShareAuthMapper.updateByPrimaryKey(share);
		}
	}
	
}
