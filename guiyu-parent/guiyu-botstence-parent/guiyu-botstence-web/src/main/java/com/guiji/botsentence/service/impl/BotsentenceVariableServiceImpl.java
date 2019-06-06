package com.guiji.botsentence.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.controller.server.vo.DomainParamVO;
import com.guiji.botsentence.controller.server.vo.OptionsVO;
import com.guiji.botsentence.dao.*;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.dao.ext.BotSentenceDomainExtMapper;
import com.guiji.botsentence.json.Options_interruption_config;
import com.guiji.botsentence.json.SurveyJSON;
import com.guiji.botsentence.json.Survey_info;
import com.guiji.botsentence.service.IBotsentenceVariableService;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.vo.BotSentenceSurveyVO;
import com.guiji.common.exception.CommonException;
import com.guiji.component.client.util.BeanUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 变量设置相关服务类
 * @author Administrator
 *
 */
@Service
public class BotsentenceVariableServiceImpl implements IBotsentenceVariableService {

	private Logger logger = LoggerFactory.getLogger(BotsentenceVariableServiceImpl.class);
	
	@Resource
	private BotSentenceOptionsMapper botSentenceOptionsMapper;
	
	@Resource
	private BotSentenceProcessServiceImpl botSentenceProcessService;
	
	@Resource
	private BotSentenceOptionsLevelMapper botSentenceOptionsLevelMapper;
	
	@Resource
	private BotSentenceSurveyMapper botSentenceSurveyMapper;
	
	@Resource
	private BotSentenceSurveyIntentMapper botSentenceSurveyIntentMapper;
	
	@Resource
	private VoliceServiceImpl voliceService;
	
	@Resource
	private BotSentenceDomainMapper botSentenceDomainMapper;
	
	@Resource
	private BotSentenceBranchMapper botSentenceBranchMapper;
	
	@Resource
	private VoliceInfoMapper voliceInfoMapper;
	
	@Resource
	private BotSentenceDomainExtMapper botSentenceDomainExtMapper;
	
	@Resource
	private BotSentenceIntentMapper botSentenceIntentMapper;
	
	@Resource
	private BotSentenceGradeServiceImpl botSentenceGradeService;

	@Resource
	private FileGenerateServiceImpl fileGenerateService;
	
	@Override
	@Transactional
	public void saveOptions(OptionsVO vo, String userId) {
		if(StringUtils.isBlank(vo.getProcessId())) {
			throw new CommonException("话术流程编号为空");
		}
		BotSentenceOptions options = this.getOptionsByProcessId(vo.getProcessId());
		
		BotSentenceProcess process = botSentenceProcessService.queryBotsentenceProcessInfo(vo.getProcessId());
		if(null == options) {
			logger.info("新增options");
			options = new BotSentenceOptions();
			options.setProcessId(vo.getProcessId());
			options.setTempname(process.getTemplateName());
			options.setTemplateId(process.getTemplateId());
			options.setCheckSim(true);
			options.setCurDomainPrior(true);
			options.setUseNotMatchLogic(true);
			options.setNotMatchSolution("solution_two");
			options.setTrade(process.getIndustry());
			options.setCrtTime(new Date(System.currentTimeMillis()));
			options.setCrtUser(userId);
			botSentenceOptionsMapper.insert(options);
		}

		BotSentenceOptions newOptions = new BotSentenceOptions();
		BeanUtil.copyProperties(vo, newOptions);
		newOptions.setOptionsId(options.getOptionsId());
		
		List<BotSentenceDomain> domainList = botSentenceProcessService.getDomainList(vo.getProcessId());
		
		Map<String, BotSentenceDomain> map = new HashMap<>();
		for(BotSentenceDomain domain : domainList) {
			map.put(domain.getDomainName(), domain);
		}

        //保存 domain 里的 ignore_but_domain
        if(!CollectionUtils.isEmpty(vo.getIgnoreButDomains())){
            vo.getIgnoreButDomains().forEach(domainParam -> {
                BotSentenceDomain domain = map.get(domainParam.getDomain());
                if(CollectionUtils.isEmpty(domainParam.getToList())){
                    domain.setIgnoreButDomains(null);
                }else {
                    domain.setIgnoreButDomains(BotSentenceUtil.listToString(domainParam.getToList()));
                }
                domain.setLstUpdateTime(new Date());
                domain.setLstUpdateUser(userId);
                botSentenceDomainMapper.updateByPrimaryKey(domain);
            });
        }

		//保存ignore_but_negative
		if(Constant.VARIABLE_FLAG_09.equals(vo.getFlag())) {
			List<BotSentenceDomain> allDomainList = botSentenceProcessService.getAllDomainList(vo.getProcessId());
			Map<String, BotSentenceDomain> allMap = new HashMap<>();
			for(BotSentenceDomain domain : allDomainList) {
				allMap.put(domain.getDomainName(), domain);
			}
			botSentenceDomainExtMapper.batchUpdateIgnoreButNegative(vo.getProcessId());
			if(null != vo.getIgnoreButNegatives() && vo.getIgnoreButNegatives().size() > 0) {
				for(String domainName : vo.getIgnoreButNegatives()) {
					BotSentenceDomain domain = allMap.get(domainName);
					domain.setIgnoreButNegative(true);
					domain.setLstUpdateTime(new Date(System.currentTimeMillis()));
					domain.setLstUpdateUser(userId);
					botSentenceDomainMapper.updateByPrimaryKey(domain);
				}
			}
		}
		
		
		//保存ignore_user_sentence
		if(Constant.VARIABLE_FLAG_08.equals(vo.getFlag())) {
			List<BotSentenceDomain> allDomainList = botSentenceProcessService.getAllDomainList(vo.getProcessId());
			Map<String, BotSentenceDomain> allMap = new HashMap<>();
			for(BotSentenceDomain domain : allDomainList) {
				allMap.put(domain.getDomainName(), domain);
			}
			botSentenceDomainExtMapper.batchUpdateIgnoreButSentence(vo.getProcessId());
			
			if(null != vo.getIgnoreUserSentences() && vo.getIgnoreUserSentences().size() > 0) {
				for(String domainName : vo.getIgnoreUserSentences()) {
					BotSentenceDomain domain = allMap.get(domainName);
					domain.setIgnoreUserSentence(true);
					domain.setLstUpdateTime(new Date(System.currentTimeMillis()));
					domain.setLstUpdateUser(userId);
					botSentenceDomainMapper.updateByPrimaryKey(domain);
				}
			}
		}

        //保存 domain 里的 match_orders
        if(!CollectionUtils.isEmpty(vo.getMatchOrders())){
            vo.getMatchOrders().forEach(domainParam -> {
                BotSentenceDomain domain = map.get(domainParam.getDomain());
                if(CollectionUtils.isEmpty(domainParam.getToList())){
                    domain.setMatchOrder(null);
                }else {
                    String matchOrders = BotSentenceUtil.listToString(domainParam.getToList());
                    matchOrders = matchOrders.replaceAll("肯定", "positive");
                    domain.setMatchOrder(matchOrders);
                }
                domain.setLstUpdateTime(new Date(System.currentTimeMillis()));
                domain.setLstUpdateUser(userId);
                botSentenceDomainMapper.updateByPrimaryKey(domain);
            });
        }

		if(null != vo.getNotMatchLess4Tos() && vo.getNotMatchLess4Tos().size() > 0) {
			for(DomainParamVO domainParam : vo.getNotMatchLess4Tos()) {
				if(null != domainParam.getToList() && domainParam.getToList().size() > 0) {
					BotSentenceDomain domain = map.get(domainParam.getDomain());
					domain.setNotMatchLess4To(BotSentenceUtil.listToString(domainParam.getToList()));
					domain.setLstUpdateTime(new Date(System.currentTimeMillis()));
					domain.setLstUpdateUser(userId);
					botSentenceDomainMapper.updateByPrimaryKey(domain);
				}
			}
		}
		
		if(null != vo.getNoWordsTos() && vo.getNoWordsTos().size() > 0) {
			for(DomainParamVO domainParam : vo.getNoWordsTos()) {
				if(null != domainParam.getToList() && domainParam.getToList().size() > 0) {
					BotSentenceDomain domain = map.get(domainParam.getDomain());
					domain.setNoWordsTo(BotSentenceUtil.listToString(domainParam.getToList()));
					domain.setLstUpdateTime(new Date(System.currentTimeMillis()));
					domain.setLstUpdateUser(userId);
					botSentenceDomainMapper.updateByPrimaryKey(domain);
				}
			}
		}
		
		if(null != vo.getNotMatchTos() && vo.getNotMatchTos().size() > 0) {
			for(DomainParamVO domainParam : vo.getNotMatchTos()) {
				if(null != domainParam.getToList() && domainParam.getToList().size() > 0) {
					BotSentenceDomain domain = map.get(domainParam.getDomain());
					domain.setNotMatchTo(BotSentenceUtil.listToString(domainParam.getToList()));
					domain.setLstUpdateTime(new Date(System.currentTimeMillis()));
					domain.setLstUpdateUser(userId);
					botSentenceDomainMapper.updateByPrimaryKey(domain);
				}
			}
		}
		
		//保存打断新规录音信息
		if(Constant.VARIABLE_FLAG_04.equals(vo.getFlag())) {
			if(StringUtils.isNotBlank(vo.getVoliceContent())) {//有文案
				if(StringUtils.isNotBlank(options.getVoice())) {//如果之前有保存过，则直接更新文案
					VoliceInfo voliceInfo = voliceService.getVoliceInfo(new Long(options.getVoice()));
					if(!voliceInfo.getContent().equals(vo.getVoliceContent())) {
						voliceInfo.setContent(vo.getVoliceContent());
						voliceInfo.setType(Constant.VOLICE_TYPE_INTERRUPT);
						voliceInfo.setLstUpdateUser(userId);
						voliceInfo.setLstUpdateTime(new Date(System.currentTimeMillis()));
						voliceService.saveVoliceInfo(voliceInfo, userId);
						newOptions.setVoice(voliceInfo.getVoliceId().toString());
					}
				}else {//如果之前没有保存过，则新增一条文案录音信息
					//插入音频信息
					VoliceInfo voliceInfo=new VoliceInfo();
					voliceInfo.setContent(vo.getVoliceContent().replace("\n", "").trim());
					voliceInfo.setProcessId(vo.getProcessId());
					voliceInfo.setTemplateId(options.getTemplateId());
					voliceInfo.setType(Constant.VOLICE_TYPE_INTERRUPT);
					//voliceInfo.setDomainName(DOMAIN);
					voliceInfo.setCrtTime(new Date(System.currentTimeMillis()));
					voliceInfo.setCrtUser(userId);
					voliceInfo.setFlag("【新增】");
					//voliceInfoMapper.insertSelective(voliceInfo);
					voliceService.saveVoliceInfo(voliceInfo, userId);
					
					newOptions.setVoice(voliceInfo.getVoliceId().toString());
				}
			}else {
				//判断之前是不是有保存过打断文案,如果有，则需要删除
				if(StringUtils.isNotBlank(options.getVoice())) {
					BotSentenceOptions exist = botSentenceOptionsMapper.selectByPrimaryKey(options.getOptionsId());
					exist.setVoice(null);
					botSentenceOptionsMapper.updateByPrimaryKey(exist);
					voliceService.deleteVolice(process.getProcessId(), options.getVoice());
				}
			}
		}
		
		//保存静音文案
		if(null != vo.getSilenceVoliceList() && vo.getSilenceVoliceList().size() > 0) {
			
			BotSentenceDomain domain = botSentenceProcessService.getDomain(vo.getProcessId(), "静音");
			if(null == domain) {
				//新增一条静音domain
				domain = new BotSentenceDomain();
				domain.setCategory(Constant.CATEGORY_TYPE_3);//非主流程
				domain.setProcessId(process.getProcessId());//话术流程编号
				domain.setTemplateId(process.getTemplateId());//话术模板编号
				domain.setDomainName("静音");//名称
				domain.setCrtTime(new Date(System.currentTimeMillis()));//创建时间
				domain.setCrtUser(userId);//创建人
				botSentenceDomainMapper.insert(domain);
			}
			
			
			List<VoliceInfo> list = vo.getSilenceVoliceList();
			
			//保存录音文案信息
			for(VoliceInfo temp : list) {
				if(null == temp.getVoliceId()) {
					//新增volice
					temp.setType(Constant.VOLICE_TYPE_NORMAL);//话术流程domain录音
					temp.setDomainName("静音");
					if(StringUtils.isNotBlank(temp.getContent())) {
						temp.setContent(temp.getContent().replace("\n", "").trim());//录音内容
					}
					
					temp.setProcessId(process.getProcessId());
					temp.setTemplateId(process.getTemplateId());
					temp.setFlag("【新增】");
					voliceService.saveVoliceInfo(temp, userId);
				}else {
					//更新volice
					VoliceInfo volice = voliceService.getVoliceInfo(temp.getVoliceId());
					if(!temp.getContent().equals(volice.getContent())) {
						volice.setContent(temp.getContent());
						volice.setVoliceUrl(null);
						volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
						volice.setLstUpdateUser(userId);
						voliceService.saveVoliceInfo(volice, userId);
					}
				}
			}
			
			VoliceInfoExample voliceExample = new VoliceInfoExample();
			voliceExample.createCriteria().andProcessIdEqualTo(vo.getProcessId()).andDomainNameEqualTo("静音");
			List<VoliceInfo> voliceList = voliceInfoMapper.selectByExample(voliceExample);
			List<String> resps = new ArrayList<>();
			if(null != voliceList && voliceList.size() > 0) {
				for(VoliceInfo temp : voliceList) {
					resps.add(temp.getVoliceId().toString());
				}
			}
			
			
			
			//保存branch信息--positive
			BotSentenceBranch positiveBranch = botSentenceProcessService.getPositiveBranch(vo.getProcessId(), "静音");
			if(null != positiveBranch) {
				positiveBranch.setResponse("[" + BotSentenceUtil.listToString(resps) + "]");
				positiveBranch.setLstUpdateTime(new Date(System.currentTimeMillis()));
				positiveBranch.setLstUpdateUser(userId);
				botSentenceBranchMapper.updateByPrimaryKey(positiveBranch);
			}else {
				positiveBranch = new BotSentenceBranch();
				positiveBranch.setDomain("静音");
				positiveBranch.setBranchName("positive");
				positiveBranch.setNext("恢复");
				positiveBranch.setTemplateId(process.getTemplateId());
				positiveBranch.setCrtTime(new Date(System.currentTimeMillis()));
				positiveBranch.setCrtUser(userId);
				positiveBranch.setProcessId(process.getProcessId());
				if(resps.size() > 0) {
					positiveBranch.setResponse("[" + BotSentenceUtil.listToString(resps) + "]");
				}
				botSentenceBranchMapper.insert(positiveBranch);
			}
			
			
			//保存branch信息--enter_branch
			BotSentenceBranch enterBranch = botSentenceProcessService.getEnterBranch(vo.getProcessId(), "静音");
			if(null != enterBranch) {
				enterBranch.setResponse("[" + BotSentenceUtil.listToString(resps) + "]");
				enterBranch.setLstUpdateTime(new Date(System.currentTimeMillis()));
				enterBranch.setLstUpdateUser(userId);
				botSentenceBranchMapper.updateByPrimaryKey(enterBranch);
			}else {
				enterBranch = new BotSentenceBranch();
				enterBranch.setDomain("静音");
				enterBranch.setBranchName("enter_branch");
				enterBranch.setTemplateId(process.getTemplateId());
				enterBranch.setCrtTime(new Date(System.currentTimeMillis()));
				enterBranch.setCrtUser(userId);
				enterBranch.setProcessId(process.getProcessId());
				if(resps.size() > 0) {
					enterBranch.setResponse("[" + BotSentenceUtil.listToString(resps) + "]");
				}
				botSentenceBranchMapper.insert(enterBranch);
			}
			
			
			//保存branch信息--special
			BotSentenceBranch specialBranch = botSentenceProcessService.getSpecialBranch(vo.getProcessId(), "静音");
			if(null != specialBranch) {
				specialBranch.setResponse("[" + BotSentenceUtil.listToString(resps) + "]");
				specialBranch.setLstUpdateTime(new Date(System.currentTimeMillis()));
				specialBranch.setLstUpdateUser(userId);
				botSentenceBranchMapper.updateByPrimaryKey(specialBranch);
			}else {
				specialBranch = new BotSentenceBranch();
				specialBranch.setDomain("静音");
				specialBranch.setBranchName("special");
				specialBranch.setTemplateId(process.getTemplateId());
				specialBranch.setCrtTime(new Date(System.currentTimeMillis()));
				specialBranch.setCrtUser(userId);
				specialBranch.setProcessId(process.getProcessId());
				if(resps.size() > 0) {
					specialBranch.setResponse("[" + BotSentenceUtil.listToString(resps) + "]");
				}
				botSentenceBranchMapper.insert(specialBranch);
			}
		}
		
		//保存重复次数是否受限
		/*if(Constant.VARIABLE_FLAG_14.equals(vo.getFlag())) {
			botSentenceDomainExtMapper.batchUpdateIsSpecialLimitFree(vo.getProcessId());
			if(null != vo.getIsSpecialLimitFreeList() && vo.getIsSpecialLimitFreeList().size() > 0) {
				for(String domainName : vo.getIsSpecialLimitFreeList()) {
					BotSentenceDomain domain = map.get(domainName);
					domain.setIsSpecialLimitFree(true);
					domain.setLstUpdateTime(new Date(System.currentTimeMillis()));
					domain.setLstUpdateUser(userId);
					botSentenceDomainMapper.updateByPrimaryKey(domain);
				}
			}
		}*/
		
		
		
		//如果开启静音转空，那静音次数也自动开启
		if(null != vo.getSilenceAsEmpty() && vo.getSilenceAsEmpty()) {
			newOptions.setSilenceWaitStart(true);
		}
		
		newOptions.setLstUpdateTime(new Date(System.currentTimeMillis()));
		newOptions.setLstUpdateUser(userId);
		botSentenceOptionsMapper.updateByPrimaryKeySelective(newOptions);
		
		//如果当前状态为审批通过、已上线，则把状态修改为“制作中”
		botSentenceProcessService.updateProcessState(vo.getProcessId(), userId);
		
	}

	@Override
	public OptionsVO queryOptionsByProcessId(String processId) {
		OptionsVO result = null;
		BotSentenceOptionsExample example = new BotSentenceOptionsExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceOptions> list = botSentenceOptionsMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			result = new OptionsVO();
			BeanUtil.copyProperties(list.get(0), result);
			if(StringUtils.isNotBlank(result.getVoice())) {
				VoliceInfo volice = voliceService.getVoliceInfo(new Long(result.getVoice()));
				result.setVoliceContent(volice.getContent());
			}
		}
		
		//查询静音文案
		VoliceInfoExample voliceExample = new VoliceInfoExample();
		voliceExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameEqualTo("静音");
		List<VoliceInfo> voliceList = voliceInfoMapper.selectByExample(voliceExample);
		if(null != voliceList && voliceList.size() > 0) {
			result.setSilenceVoliceList(voliceList);
		}
		
		//查询ignore_but_negative
		BotSentenceDomainExample domainExample1 = new BotSentenceDomainExample();
		domainExample1.createCriteria().andProcessIdEqualTo(processId).andIgnoreButNegativeEqualTo(true);
		domainExample1.setOrderByClause(" position_y, position_x");
		List<BotSentenceDomain> domainList1 = botSentenceDomainMapper.selectByExample(domainExample1);
		List<String> domainNameList1 = new ArrayList<>();
		if(null != domainList1 && domainList1.size() > 0) {
			for(BotSentenceDomain domain : domainList1) {
				domainNameList1.add(domain.getDomainName());
			}
		}
		result.setIgnoreButNegatives(domainNameList1);
		
		//查询ignore_user_sentence
		BotSentenceDomainExample domainExample2 = new BotSentenceDomainExample();
		domainExample2.createCriteria().andProcessIdEqualTo(processId).andIgnoreUserSentenceEqualTo(true);
		domainExample2.setOrderByClause(" position_y, position_x");
		List<BotSentenceDomain> domainList2 = botSentenceDomainMapper.selectByExample(domainExample2);
		List<String> domainNameList2 = new ArrayList<>();
		if(null != domainList2 && domainList2.size() > 0) {
			for(BotSentenceDomain domain : domainList2) {
				domainNameList2.add(domain.getDomainName());
			}
		}
		result.setIgnoreUserSentences(domainNameList2);
		
		//查询ignore_user_sentence
		BotSentenceDomainExample domainExample3 = new BotSentenceDomainExample();
		domainExample3.createCriteria().andProcessIdEqualTo(processId).andIgnoreUserSentenceEqualTo(true);
		domainExample3.setOrderByClause(" position_y, position_x");
		List<BotSentenceDomain> domainList3 = botSentenceDomainMapper.selectByExample(domainExample3);
		List<String> domainNameList3 = new ArrayList<>();
		if(null != domainList3 && domainList3.size() > 0) {
			for(BotSentenceDomain domain : domainList3) {
				domainNameList3.add(domain.getDomainName());
			}
		}
		result.setIgnoreButNegatives(domainNameList1);
		
		//查询is_special_limit_free
		BotSentenceDomainExample domainExample4 = new BotSentenceDomainExample();
		domainExample4.createCriteria().andProcessIdEqualTo(processId).andIsSpecialLimitFreeEqualTo(true);
		domainExample4.setOrderByClause(" position_y, position_x");
		List<BotSentenceDomain> domainList4 = botSentenceDomainMapper.selectByExample(domainExample4);
		List<String> domainNameList4 = new ArrayList<>();
		if(null != domainList4 && domainList4.size() > 0) {
			for(BotSentenceDomain domain : domainList4) {
				domainNameList4.add(domain.getDomainName());
			}
		}
		result.setIsSpecialLimitFreeList(domainNameList4);
		
		return result;
	}
	
	
	public BotSentenceOptions getOptionsByProcessId(String processId) {
		BotSentenceOptionsExample example = new BotSentenceOptionsExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceOptions> list = botSentenceOptionsMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public void saveStart(String processId, String flag, boolean type, String userId) {

		BotSentenceOptions options = this.getOptionsByProcessId(processId);
		
		if(null == options) {
			logger.info("新增options");
			options = new BotSentenceOptions();
			options.setProcessId(processId);
			BotSentenceProcess process = botSentenceProcessService.queryBotsentenceProcessInfo(processId);
			options.setTempname(process.getTemplateName());
			options.setTemplateId(process.getTemplateId());
			options.setCheckSim(true);
			options.setCurDomainPrior(true);
			options.setUseNotMatchLogic(true);
			options.setNotMatchSolution("solution_two");
			options.setTrade(process.getIndustry());
			options.setCrtTime(new Date(System.currentTimeMillis()));
			options.setCrtUser(userId);
			botSentenceOptionsMapper.insert(options);
		}
		
		//根据flag标志分别保存不同变量字段
		logger.info("当前保存标志: " + flag);
		
		BotSentenceOptions newOptions = new BotSentenceOptions();
		newOptions.setOptionsId(options.getOptionsId());
		
		if(Constant.VARIABLE_FLAG_01.equals(flag)) {
			newOptions.setSilenceWaitStart(type);
		}else if(Constant.VARIABLE_FLAG_02.equals(flag)) {
			newOptions.setSilenceAsEmpty(type);
		}else if(Constant.VARIABLE_FLAG_03.equals(flag)) {
			newOptions.setNonInterruptableStart(type);
		}else if(Constant.VARIABLE_FLAG_04.equals(flag)) {
			newOptions.setInterruptionConfigStart(type);
		}else if(Constant.VARIABLE_FLAG_05.equals(flag)) {
			newOptions.setInterruptableDomainStart(type);
		}else if(Constant.VARIABLE_FLAG_06.equals(flag)) {
			newOptions.setGlobalInterruptableDomainsStart(type);
		}else if(Constant.VARIABLE_FLAG_07.equals(flag)) {
			newOptions.setIgnoreButDomainsStart(type);			
		}else if(Constant.VARIABLE_FLAG_08.equals(flag)) {
			newOptions.setIgnoreUserSentenceStart(type);
		}else if(Constant.VARIABLE_FLAG_09.equals(flag)) {
			newOptions.setIgnoreButNegativeStart(type);
		}else if(Constant.VARIABLE_FLAG_10.equals(flag)) {
			newOptions.setUserDefineMatchOrder(type);
		}else if(Constant.VARIABLE_FLAG_11.equals(flag)) {
			newOptions.setNotMatchLess4ToStart(type);
		}else if(Constant.VARIABLE_FLAG_12.equals(flag)) {
			newOptions.setNotMatchToStart(type);
		}else if(Constant.VARIABLE_FLAG_13.equals(flag)) {
			newOptions.setNoWordsToStart(type);
		}else if(Constant.VARIABLE_FLAG_14.equals(flag)) {
			newOptions.setSpecialLimitStart(type);
		}else {
			throw new CommonException("未识别的标志");
		}
		
		newOptions.setLstUpdateTime(new Date(System.currentTimeMillis()));
		newOptions.setLstUpdateUser(userId);
		botSentenceOptionsMapper.updateByPrimaryKeySelective(newOptions);
		
	
	}


	@Override
	public List<BotSentenceOptionsLevel> queryUserDefineMatchOrder(String processId) {
		BotSentenceOptionsLevelExample example = new BotSentenceOptionsLevelExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		return botSentenceOptionsLevelMapper.selectByExample(example);
	}

	/**
	 * 保存回访规则列表，主要保存类型
	 */
	@Override
	@Transactional
	public void saveSurvey(String processId, List<BotSentenceSurvey> list, String userId) {
		if(null != list && list.size() > 0) {
			for(BotSentenceSurvey survey : list) {
				/*if(StringUtils.isNotBlank(survey.getDomain())) {
					throw new CommonException("请求参数为空");
				}
				logger.info("当前domain: " + requestSurvey.getDomain());*/
				//BotSentenceSurvey survey = this.getSurveyByDomain(processId, requestSurvey.getDomain(), requestSurvey.getIntentName());
				if(StringUtils.isBlank(survey.getSurveyId())) {
					logger.info("新增回访规则");
					//survey = new BotSentenceSurvey();
					//BeanUtil.copyProperties(requestSurvey, survey);
					survey.setProcessId(processId);
					survey.setCrtTime(new Date(System.currentTimeMillis()));
					survey.setCrtUser(userId);
					botSentenceSurveyMapper.insert(survey);
					
				}else {
					BotSentenceSurvey exist = botSentenceSurveyMapper.selectByPrimaryKey(survey.getSurveyId());
					exist.setType(survey.getType());
					logger.info("更新回访规则");
					//BeanUtil.copyProperties(requestSurvey, survey);
					exist.setLstUpdateTime(new Date(System.currentTimeMillis()));
					exist.setLstUpdateUser(userId);
					botSentenceSurveyMapper.updateByPrimaryKey(exist);
				}
			}
		}
		
		botSentenceProcessService.updateProcessState(processId, userId);
	}

	@Override
	@Transactional
	public void saveSurveyIntent(BotSentenceSurveyIntent surveyIntent, String userId) {
		if(null != surveyIntent && StringUtils.isNotBlank(surveyIntent.getProcessId()) && StringUtils.isNotBlank(surveyIntent.getDomain())) {
			if(StringUtils.isBlank(surveyIntent.getSurveyIntentId())) {
				logger.info("新增回访意图");
				
				BotSentenceSurveyIntent exist = getSurveyIntentByName(surveyIntent.getProcessId(), surveyIntent.getDomain(), surveyIntent.getIntentName());
				if(null != exist) {
					throw new CommonException("当前意图名称已存在!");
				}
				surveyIntent.setType(Constant.SURVEY_INTENT_TYPE_DEFINE);
				surveyIntent.setCrtTime(new Date(System.currentTimeMillis()));
				surveyIntent.setCrtUser(userId);
				botSentenceSurveyIntentMapper.insert(surveyIntent);
			}else {
				logger.info("更新回访意图");
				surveyIntent.setLstUpdateTime(new Date(System.currentTimeMillis()));
				surveyIntent.setLstUpdateUser(userId);
				botSentenceSurveyIntentMapper.updateByPrimaryKeySelective(surveyIntent);
			}
		}else {
			throw new CommonException("请求参数为空!");
		}
		
		botSentenceProcessService.updateProcessState(surveyIntent.getProcessId(), userId);
	}

	@Override
	public BotSentenceSurveyIntent getSurveyIntentByName(String processId, String domain, String intentName) {
		BotSentenceSurveyIntentExample example = new BotSentenceSurveyIntentExample();
		example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain).andIntentNameEqualTo(intentName);
		List<BotSentenceSurveyIntent> list = botSentenceSurveyIntentMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	

	public BotSentenceSurvey getSurveyByDomain(String processId, String domain) {
		BotSentenceSurveyExample example = new BotSentenceSurveyExample();
		example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain);
		List<BotSentenceSurvey> list = botSentenceSurveyMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<BotSentenceSurveyVO> querySurveyByProcessId(String processId) {
		List<BotSentenceSurveyVO> result = new ArrayList<>();
		
		List<BotSentenceDomain> domainList = botSentenceProcessService.getDomainList(processId);
		for(BotSentenceDomain domain : domainList) {
			BotSentenceSurveyExample example = new BotSentenceSurveyExample();
			example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain.getDomainName());
			List<BotSentenceSurvey> surveyList = botSentenceSurveyMapper.selectByExample(example);
			
			BotSentenceSurveyVO vo = new BotSentenceSurveyVO();
			
			if(null != surveyList && surveyList.size() > 0) {
				BotSentenceSurvey survey = surveyList.get(0);
				vo.setType(survey.getType());
				vo.setSurveyId(survey.getSurveyId());
			}
			vo.setProcessId(processId);
			vo.setDomain(domain.getDomainName());
			
			BotSentenceSurveyIntentExample intentExample = new BotSentenceSurveyIntentExample();
			intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain.getDomainName());
			List<BotSentenceSurveyIntent> list = botSentenceSurveyIntentMapper.selectByExample(intentExample);
			
			vo.setSurveyIntentList(list);
			
			result.add(vo);
		}
		
		return result;
	}


	/**
	 * 根据话术流程编号生成options.json字符串
	 * @param processId
	 * @return
	 */
	public String generateOptionJson(String processId, Map<Long, String> voliceMap) {
		BotSentenceProcess botSentenceProcess = botSentenceProcessService.queryBotsentenceProcessInfo(processId); 
		logger.info("生成基础options.json内容");
		/*OptionsJSON option = new OptionsJSON();
		option.setCheck_sim(true);
		option.setCur_domain_prior(true);
		option.setUse_endfiles_list(true);
		option.setUse_not_match_logic(true);
		option.setNot_match_solution("solution_two");
		option.setTrade(botSentenceProcess.getIndustry());
		option.setTempname(botSentenceProcess.getTemplateId());
		option.setDes(botSentenceProcess.getTemplateName());
		option.setDianame(botSentenceProcess.getTemplateName());*/
		
		//JSONObject jsonObject = (JSONObject) JSON.toJSON(option);
		
		BotSentenceOptions botSentenceOptions = this.getOptionsByProcessId(processId);
		JSONObject jsonObject = new JSONObject(true);
		jsonObject.put("check_sim", botSentenceOptions.getCheckSim());
		jsonObject.put("cur_domain_prior", botSentenceOptions.getCurDomainPrior());
		jsonObject.put("use_endfiles_list", botSentenceOptions.getUseEndfilesList());
		jsonObject.put("use_not_match_logic", botSentenceOptions.getUseNotMatchLogic());
		jsonObject.put("not_match_solution", botSentenceOptions.getNotMatchSolution());
		jsonObject.put("trade", botSentenceProcess.getIndustry());
		jsonObject.put("tempname", botSentenceProcess.getTemplateId());
		jsonObject.put("des", botSentenceProcess.getTemplateName());
		jsonObject.put("dianame", botSentenceProcess.getTemplateName());
		
		//获取三级行业名称
		/*String level1 = botSentenceProcess.getIndustry().substring(0, 2);
		String level2 = botSentenceProcess.getIndustry().substring(0, 4);
		String level3 = botSentenceProcess.getIndustry().substring(0, 6);
		BotSentenceTrade trade1 = botSentenceProcessService.getBotSentenceTrade(level1);
		if(null != trade1) {
			jsonObject.put("一级行业", trade1.getIndustryName());
		}
		BotSentenceTrade trade2 = botSentenceProcessService.getBotSentenceTrade(level2);
		if(null != trade2) {
			jsonObject.put("二级行业", trade2.getIndustryName());
		}
		BotSentenceTrade trade3 = botSentenceProcessService.getBotSentenceTrade(level3);
		if(null != trade3) {
			jsonObject.put("三级行业", trade3.getIndustryName());
		}*/
		
		logger.info("根据变量设置添加到options.json");
		//查询变量设置信息
		
		if(null != botSentenceOptions) {
			if(null != botSentenceOptions.getNonInterruptableStart() && botSentenceOptions.getNonInterruptableStart()) {
				if(StringUtils.isNotBlank(botSentenceOptions.getNonInterruptable())) {
					//option.setNon_interruptable(botSentenceOptions.getNonInterruptable());
					jsonObject.put("non_interruptable", botSentenceOptions.getNonInterruptable().replaceAll(",", " "));
				}
			}
			if(null != botSentenceOptions.getSilenceWaitStart() && botSentenceOptions.getSilenceWaitStart()) {
				if(null != botSentenceOptions.getSilenceWaitSecs()) {
					//option.setSilence_wait_secs(botSentenceOptions.getSilenceWaitSecs());
					jsonObject.put("silence_wait_secs",  botSentenceOptions.getSilenceWaitSecs());
				}
				if(null != botSentenceOptions.getSilenceWaitTime()) {
					//option.setSilence_wait_time(botSentenceOptions.getSilenceWaitTime());
					jsonObject.put("silence_wait_time", botSentenceOptions.getSilenceWaitTime());
				}
			}
			if(null != botSentenceOptions.getSilenceAsEmpty() && botSentenceOptions.getSilenceAsEmpty()) {
				//option.setSilence_as_empty(botSentenceOptions.getSilenceAsEmpty());
				jsonObject.put("silence_as_empty", botSentenceOptions.getSilenceAsEmpty());
			}
			if(null != botSentenceOptions.getSpecialLimitStart() && botSentenceOptions.getSpecialLimitStart()) {
				if(null != botSentenceOptions.getSpecialLimit()) {
					//option.setSpecial_limit(botSentenceOptions.getSpecialLimit());
					jsonObject.put("special_limit", botSentenceOptions.getSpecialLimit());
				}
			}
			if(null != botSentenceOptions.getUserDefineMatchOrder() && botSentenceOptions.getUserDefineMatchOrder()) {//是否需要自定义排序
				//option.setUser_define_match_order(botSentenceOptions.getUserDefineMatchOrder());
				jsonObject.put("user_define_match_order", botSentenceOptions.getUserDefineMatchOrder());
			}
			if(null != botSentenceOptions.getInterruptableDomainStart() && botSentenceOptions.getInterruptableDomainStart()) {//被解释开场白打断
				List<String> interruptable_domainList = new ArrayList<>();
				interruptable_domainList.add("解释开场白");
				jsonObject.put("interruptable_domain", interruptable_domainList);
			}
			if(null != botSentenceOptions.getGlobalInterruptableDomainsStart() && botSentenceOptions.getGlobalInterruptableDomainsStart()) {
				if(StringUtils.isNotBlank(botSentenceOptions.getGlobalInterruptableDomains())) {
					//option.setGlobal_interruptable_domains(stringToList(botSentenceOptions.getGlobalInterruptableDomains()));
					jsonObject.put("global_interruptable_domains", Arrays.asList(botSentenceOptions.getGlobalInterruptableDomains().split(",")));
				}
			}
			if(null != botSentenceOptions.getInterruptionConfigStart() && botSentenceOptions.getInterruptionConfigStart()) {//打断新规
				Options_interruption_config config = new Options_interruption_config();
				if(null != botSentenceOptions.getInterruptMinInterval() && null != botSentenceOptions.getInterruptWordsNum() &&
						botSentenceOptions.getInterruptMinInterval() > 0 && botSentenceOptions.getInterruptWordsNum() > 0) {
					config.setInterrupt_min_interval(botSentenceOptions.getInterruptMinInterval());
					config.setInterrupt_words_num(botSentenceOptions.getInterruptWordsNum());
					if(StringUtils.isNotBlank(botSentenceOptions.getVoice())) {
						VoliceInfo volice = voliceService.getVoliceInfo(new Long(botSentenceOptions.getVoice()));
						config.setVoice(volice.getContent() + "*8000");
					}
					jsonObject.put("interruption_config", config);
				}
				//option.setInterruption_config(config);
			}
		}
		//String optionJson = JSON.toJSONString(option);
		String optionJson = jsonObject.toJSONString();
		return optionJson;
	}
	
	
	/**
	 * 根据话术流程编号生成survey.json字符串
	 * @param processId
	 * @return
	 * @throws Exception 
	 */
	public String generateSurveyJson(String processId) {
		List<SurveyJSON> result = new ArrayList<>();
		BotSentenceSurveyExample example = new BotSentenceSurveyExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceSurvey> list = botSentenceSurveyMapper.selectByExample(example);
		
		String json = null;
		
		if(null != list && list.size() > 0) {
			for(BotSentenceSurvey survey : list) {
				if(StringUtils.isBlank(survey.getType())) {
					continue;
				}
				
				SurveyJSON surveyJson = new SurveyJSON();
				surveyJson.setDomain(survey.getDomain());
				Survey_info info = new Survey_info();
				
				//获取enter_branch
				BotSentenceBranch enterBranch = botSentenceProcessService.getEnterBranch(processId, survey.getDomain());
				String resp = enterBranch.getResponse();
				if(StringUtils.isNotBlank(resp) && !"[]".equals(resp.trim()) && resp.trim().startsWith("[") && resp.trim().endsWith("]")) {
					String[] respArray = resp.substring(1,resp.length()-1).split(",");
					for(int i = 0 ; i < respArray.length ; i++) {
						resp = respArray[0];
					}
				}
				//根据resp获取volice的文案
				VoliceInfo volice = voliceService.getVoliceInfo(new Long(resp));
				info.setQuestion(volice.getContent());
				info.setType(survey.getType());
				
				Map<String, Map<String, Object>> answer = new HashMap<>();
				
				
				if("INTENT".equals(survey.getType()) || "KEY".equals(survey.getType())) {
					//查询回访模板的意图列表
					BotSentenceSurveyIntentExample intentExample = new BotSentenceSurveyIntentExample();
					intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(survey.getDomain());
					List<BotSentenceSurveyIntent> intentList = botSentenceSurveyIntentMapper.selectByExample(intentExample);
					if(null != intentList && intentList.size() > 0) {
						for(BotSentenceSurveyIntent surveyIntent : intentList) {
							if(StringUtils.isNotBlank(surveyIntent.getIntentKeys())) {
								Map<String, Object> keysMap = new HashMap<>();
								List<String> keys = stringToList(surveyIntent.getIntentKeys());
								keysMap.put("keys", keys);
								answer.put(surveyIntent.getIntentName(), keysMap);
							}
						}
					}
					info.setAnswer(answer);
					info.setDefault_to_format("不清楚");
				}
				
				surveyJson.setInfo(info);
				
				result.add(surveyJson);
			}
			
			try {
				json = BotSentenceUtil.formatJavaToJson(result, ArrayList.class);
			} catch (Exception e) {
				logger.error("转换回访模板survey.json异常...", e);
				throw new CommonException("转换回访模板survey.json异常...");
			}
		}
		return json;
	}
	
	
	private List<String> stringToList(String keysStr){
		List<String> keys = new ArrayList<>();
		String [] array = keysStr.split(",");
		keys = Arrays.asList(array);
		return keys;
	}
	
	public List<String> getUseMatchOrderList(String processId, String domain){
		List<String> list = new ArrayList<>();
		BotSentenceOptionsLevelExample example = new BotSentenceOptionsLevelExample();
		example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain);
		List<BotSentenceOptionsLevel> levelList = botSentenceOptionsLevelMapper.selectByExample(example);
		if(null != levelList && levelList.size() > 0) {
			BotSentenceOptionsLevel level = levelList.get(0);
			if(StringUtils.isNotBlank(level.getLevel1())) {
				list.add(level.getLevel1());
			}else {
				return list;
			}
			if(StringUtils.isNotBlank(level.getLevel2())) {
				list.add(level.getLevel2());
			}else {
				return list;
			}
			if(StringUtils.isNotBlank(level.getLevel3())) {
				list.add(level.getLevel3());
			}else {
				return list;
			}
			if(StringUtils.isNotBlank(level.getLevel4())) {
				list.add(level.getLevel4());
			}else {
				return list;
			}
			if(StringUtils.isNotBlank(level.getLevel5())) {
				list.add(level.getLevel5());
			}else {
				return list;
			}
			if(StringUtils.isNotBlank(level.getLevel6())) {
				list.add(level.getLevel6());
			}else {
				return list;
			}
			if(StringUtils.isNotBlank(level.getLevel7())) {
				list.add(level.getLevel7());
			}else {
				return list;
			}
		}
		
		return list;
	}

	@Override
	public List<DomainParamVO> queryDomainParamList(String processId, String paramType) {
		List<DomainParamVO> result = new ArrayList<>();
		List<BotSentenceDomain> list = botSentenceProcessService.getDomainList(processId);
		for(BotSentenceDomain domain : list) {
			DomainParamVO vo = new DomainParamVO();
			vo.setDomain(domain.getDomainName());
			List<String> toList = new ArrayList<>();
			
			if(Constant.VARIABLE_FLAG_07.equals(paramType)) {//ignoreButDomainsStart
				if(StringUtils.isNotBlank(domain.getIgnoreButDomains())) {
					toList = Arrays.asList(domain.getIgnoreButDomains().split(","));
				}
			}else if(Constant.VARIABLE_FLAG_11.equals(paramType)) {//notMatchLess4ToStart
				if(StringUtils.isNotBlank(domain.getNotMatchLess4To())) {
					toList = Arrays.asList(domain.getNotMatchLess4To().split(","));
				}
			}else if(Constant.VARIABLE_FLAG_12.equals(paramType)) {//notMatchToStart
				if(StringUtils.isNotBlank(domain.getNotMatchTo())) {
					toList = Arrays.asList(domain.getNotMatchTo().split(","));
				}
			}else if(Constant.VARIABLE_FLAG_13.equals(paramType)) {//noWordsToStart
				if(StringUtils.isNotBlank(domain.getNoWordsTo())) {
					toList = Arrays.asList(domain.getNoWordsTo().split(","));
				}
			}/*else if(Constant.VARIABLE_FLAG_09.equals(paramType)) {//ignoreButNegativeStart
				if(null != domain.getIgnoreButNegative()) {
					toList = Arrays.asList(domain.getIgnoreButNegative().split(","));
				}
			}else if(Constant.VARIABLE_FLAG_08.equals(paramType)) {//ignoreUserSentenceStart
				if(StringUtils.isNotBlank(domain.getIgnoreUserSentence())) {
					toList = Arrays.asList(domain.getIgnoreUserSentence().split(","));
				}
			}*/else if(Constant.VARIABLE_FLAG_10.equals(paramType)) {//userDefineMatchOrder
				if(StringUtils.isNotBlank(domain.getMatchOrder())) {
					toList = Arrays.asList(domain.getMatchOrder().split(","));
				}
			}
			
			
			vo.setToList(toList);
			result.add(vo);
		}
		
		return result;
	}

	@Transactional
	@Override
	public void deleteSilenceVolice(String processId, String voliceId, String userId) {
		
		voliceService.deleteVolice(processId, voliceId);
		
		BotSentenceBranch positiveBranch = botSentenceProcessService.getPositiveBranch(processId, "静音");
		String resp = positiveBranch.getResponse();
		if(StringUtils.isNotBlank(resp) && resp.length() > 2) {
			resp = resp.substring(1, resp.length() - 1);
			List<String> list = new ArrayList<>(BotSentenceUtil.StringToList(resp));
			for(int i = 0 ; i < list.size() ; i++) {
				if(voliceId.equals(list.get(i))) {
					list.remove(i);
				}
			}
			resp = BotSentenceUtil.listToString(list);
			resp = "[" + resp + "]";
		}
		positiveBranch.setResponse(resp);
		positiveBranch.setLstUpdateTime(new Date(System.currentTimeMillis()));
		positiveBranch.setLstUpdateUser(userId);
		botSentenceBranchMapper.updateByPrimaryKey(positiveBranch);
		
		BotSentenceBranch enterBranch = botSentenceProcessService.getEnterBranch(processId, "静音");
		enterBranch.setResponse(resp);
		enterBranch.setLstUpdateTime(new Date(System.currentTimeMillis()));
		enterBranch.setLstUpdateUser(userId);
		botSentenceBranchMapper.updateByPrimaryKey(enterBranch);
		
		BotSentenceBranch specialBranch = botSentenceProcessService.getSpecialBranch(processId, "静音");
		specialBranch.setResponse(resp);
		specialBranch.setLstUpdateTime(new Date(System.currentTimeMillis()));
		specialBranch.setLstUpdateUser(userId);
		botSentenceBranchMapper.updateByPrimaryKey(specialBranch);
	}

	@Override
	public void deleteSurveyIntent(String processId, String surveyIntentId) {
		if(StringUtils.isNotBlank(surveyIntentId)) {
			botSentenceSurveyIntentMapper.deleteByPrimaryKey(surveyIntentId);
		}
	}
	
	public void initDomainSurveyIntent(String processId, String domainName, String templateId, String userId) {
		BotSentenceSurvey survey = new BotSentenceSurvey();
		survey.setDomain(domainName);
		survey.setProcessId(processId);
		survey.setTemplateId(templateId);
		survey.setCrtTime(new Date(System.currentTimeMillis()));
		survey.setCrtUser(userId);
		botSentenceSurveyMapper.insert(survey);
		
		BotSentenceSurveyIntent surveyIntent1 = new BotSentenceSurveyIntent();
		surveyIntent1.setProcessId(processId);
		surveyIntent1.setTemplateId(templateId);
		surveyIntent1.setDomain(domainName);
		surveyIntent1.setIntentName("不清楚");
		surveyIntent1.setType(Constant.SURVEY_INTENT_TYPE_DEFAULT);
		surveyIntent1.setCrtTime(new Date(System.currentTimeMillis()));
		surveyIntent1.setCrtUser(userId);
		botSentenceSurveyIntentMapper.insert(surveyIntent1);
		
		
		BotSentenceSurveyIntent surveyIntent2 = new BotSentenceSurveyIntent();
		surveyIntent2.setProcessId(processId);
		surveyIntent2.setTemplateId(templateId);
		surveyIntent2.setDomain(domainName);
		surveyIntent2.setIntentName("可以");
		surveyIntent2.setIntentKeys("可以，有，是的，对的，对，好");
		surveyIntent2.setType(Constant.SURVEY_INTENT_TYPE_DEFAULT);
		surveyIntent2.setCrtTime(new Date(System.currentTimeMillis()));
		surveyIntent2.setCrtUser(userId);
		botSentenceSurveyIntentMapper.insert(surveyIntent2);
		
		BotSentenceSurveyIntent surveyIntent3 = new BotSentenceSurveyIntent();
		surveyIntent3.setProcessId(processId);
		surveyIntent3.setTemplateId(templateId);
		surveyIntent3.setDomain(domainName);
		surveyIntent3.setIntentName("不可以");
		surveyIntent3.setIntentKeys("不可以，没有，没，没完成，不知道，不清楚，忘记，忘了");
		surveyIntent3.setType(Constant.SURVEY_INTENT_TYPE_DEFAULT);
		surveyIntent3.setCrtTime(new Date(System.currentTimeMillis()));
		surveyIntent3.setCrtUser(userId);
		botSentenceSurveyIntentMapper.insert(surveyIntent3);
	}
	
	public void deleteSurveyIntentByDomain(String processId, String domainName) {
		BotSentenceSurveyExample example1 = new BotSentenceSurveyExample();
		example1.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domainName);
		botSentenceSurveyMapper.deleteByExample(example1);
		
		BotSentenceSurveyIntentExample example = new BotSentenceSurveyIntentExample();
		example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domainName);
		botSentenceSurveyIntentMapper.deleteByExample(example);
	}

	@Override
	public String generateWeightJson(String processId) {
		BotSentenceBranchExample example = new BotSentenceBranchExample();
		example.createCriteria().andProcessIdEqualTo(processId).andWeightIsNotNull().andDomainEqualTo("一般问题");
		List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(example);
		
		String weight = "keyword,weight\n";
		if(null != list && list.size() > 0) {
			for(BotSentenceBranch branch : list) {
				String intents = branch.getIntents();
				if(StringUtils.isNotBlank(intents)) {
					String [] array = intents.split(",");
					for(String intentId : array) {
						BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intentId));
						if(null != intent) {
							List<String> keywordList = BotSentenceUtil.getKeywords(intent.getKeywords());
							if(null != keywordList && keywordList.size() > 0) {
								String keywords = keywordList.get(0).replace("\"", "");
								String [] keywordArray = keywords.split(",");
								for(String keyword : keywordArray) {
									weight = weight + keyword + "," + branch.getWeight() + "\n";
								}
							}
						}
					}
				}
			}
		}
		return weight;
	}

	@Override
	public String generateStatJson(String processId) {
		LinkedHashMap<String, Object> statMap = new LinkedHashMap<>();
		statMap.put("init_stat", "D");
		BotSentenceGrade grade = botSentenceGradeService.getBotSentenceGrade(processId);
		if(null != grade && StringUtils.isNotBlank(grade.getStatOrder())) {
			List<String> intentNameList = BotSentenceUtil.StringToList(grade.getStatOrder());
			for(String intentName : intentNameList) {
				Map<String, String> map = new LinkedHashMap<>();
				String evaluate = botSentenceGradeService.generateGradeEvaluate(processId, intentName);
				String show = botSentenceGradeService.generateGradeShowEvaluate(processId, intentName);
				//if(StringUtils.isNotBlank(evaluate)) {
				map.put("evaluate", evaluate);
				map.put("show", show);
				statMap.put(intentName, map);
				//}
			}
			
			statMap.put("show", "");
			statMap.put("max_conversation_count", 12);
			statMap.put("stat_order", BotSentenceUtil.StringToList(grade.getStatOrder()));
			if(StringUtils.isNotBlank(grade.getInitStat())) {
				statMap.put("init_stat", grade.getInitStat());
			}
		}
		
		/*List<String> ruleNoList = botSentenceGradeRuleExtMapper.queryDistinctProcessId(processId);
		if(null != ruleNoList && ruleNoList.size() > 0) {
			String intentName = botSentenceGradeService.getIntentNameByRuleNo(processId, ruleNoList.get(0));
			for(String ruleNo : ruleNoList) {
				Map<String, String> map = new HashMap<>();
				String evaluate = botSentenceGradeService.generateGradeEvaluate(processId, ruleNo);
				map.put("evaluate", evaluate);
				statMap.put(intentName, map);
			}
		}*/
		
		statMap.put("type", "general");
		
		String jsonString = null;
		try {
			jsonString = BotSentenceUtil.formatJavaToJson(statMap, LinkedHashMap.class);
		} catch (Exception e) {
			logger.error("转换意向标签stat.json异常...", e);
			throw new CommonException("转换意向标签stat.json异常...");
		}
		return jsonString;
	}

	/**
	 * 根据话术流程编号生成相应的select.json字符串
	 */
	@Override
	public String generateSelectJson(String processId, List<String> agentKeywordList) {
		//以下几个域的negative分支
		List<String> list = new ArrayList<>();
		list.add("在忙");
		list.add("投诉");
		list.add("拒绝");
		list.add("用户不清楚");
		List<String> intentIdList = new ArrayList<>();
		BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
		branchExample.createCriteria().andProcessIdEqualTo(processId).andDomainIn(list).andBranchNameEqualTo("negative");
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchExample);
		if(null != branchList && branchList.size() > 0) {
			for(BotSentenceBranch branch : branchList) {
				String intents = branch.getIntents();
				if(StringUtils.isNotBlank(intents)) {
					String [] array = intents.split(",");
					for(String intentId : array) {
						intentIdList.add(intentId);
					}
				}
			}
		}
		
		
		//号码过滤取special
		BotSentenceBranchExample branchExample2 = new BotSentenceBranchExample();
		branchExample2.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("号码过滤").andBranchNameEqualTo("special");
		List<BotSentenceBranch> branchList2 = botSentenceBranchMapper.selectByExample(branchExample2);
		if(null != branchList2 && branchList2.size() > 0) {
			for(BotSentenceBranch branch : branchList2) {
				String intents = branch.getIntents();
				if(StringUtils.isNotBlank(intents)) {
					String [] array = intents.split(",");
					for(String intentId : array) {
						intentIdList.add(intentId);
					}
				}
			}
		}
		
		
		//一般问题分支
		BotSentenceBranchExample branchExample3 = new BotSentenceBranchExample();
		branchExample3.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("一般问题").andBranchNameLike("special%");
		List<BotSentenceBranch> branchList3 = botSentenceBranchMapper.selectByExample(branchExample3);
		if(null != branchList3 && branchList3.size() > 0) {
			for(BotSentenceBranch branch : branchList3) {
				String intents = branch.getIntents();
				if(StringUtils.isNotBlank(intents)) {
					String [] array = intents.split(",");
					for(String intentId : array) {
						intentIdList.add(intentId);
					}
				}
			}
		}
		
		Map<String, String> selectMap = new HashMap<String, String>();
		
		for (String intentId : intentIdList) {
			String keys = "";
			
			BotSentenceIntent botSentenceIntent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intentId));
			String domainName = botSentenceIntent.getDomainName();
			
			keys = botSentenceIntent.getKeywords();
			
			if(StringUtils.isNotBlank(keys) && keys.length() > 2) {
				List<String> keywordsList = JSON.parseArray(keys,  String.class);

				List<String> keywordsReusltList = Lists.newArrayList();

				keywordsList.forEach(keyword -> {
					if(keyword.contains("[")){
						keywordsReusltList.add(keyword);
					}else {
						keywordsReusltList.add("\"" + keyword +"\"");
					}
				});

				String keywordJson = String.join(",", keywordsReusltList);

				if(selectMap.containsKey(domainName)) {
					String existKeywords = selectMap.get(domainName);
					keywordJson = existKeywords + "," + keywordJson;
				}
				selectMap.put(domainName, keywordJson);
			}
		}
		
		//设置转人工关键词
		String agentKeywords = "";
		if(null != agentKeywordList && agentKeywordList.size() > 0 ) {
			for(int i = 0 ; i < agentKeywordList.size() ; i++) {
				if(i == 0) {
					agentKeywords = agentKeywordList.get(i);
				}else {
					agentKeywords = agentKeywords + "," + agentKeywordList.get(i);
				}
			}
			selectMap.put(Constant.DOMAIN_TYPE_AGENT, agentKeywords);
		}
		
		Set<String> set = selectMap.keySet();
		for(String str : set) {
			String keywords = selectMap.get(str);
			keywords = "[" + keywords + "]";
			selectMap.put(str, keywords);
		}
		
		String jsonString = null;
		try {
			jsonString = JSON.toJSONString(selectMap).replace("\"[", "[").replace("]\"", "]").replace("\\", "");
			return jsonString;
		} catch (Exception e) {
			logger.error("转换select.json异常...", e);
			throw new CommonException("转换select.json异常...");
		}
	}
	
	//生成rule_在忙.json
	@Override
	public String generateZaiMangJson(String processId) {
		Map<String, Map<String, Object>> refuseMap = new LinkedHashMap<>();
		Map<String, Object> refuseMapDeail = new LinkedHashMap<>();
		List<String> refuseKeywords = new ArrayList<>();
		//获取在忙的关键词
		BotSentenceBranchExample example0 = new BotSentenceBranchExample();
		example0.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("在忙").andBranchNameEqualTo("negative");
		List<BotSentenceBranch> list0 = botSentenceBranchMapper.selectByExample(example0);
		if(null != list0 && list0.size() > 0) {
			BotSentenceBranch branch0 = list0.get(0);
			refuseKeywords = fileGenerateService.getIntentKeys(branch0.getIntents());
		}
		
		refuseMap.put("rule_在忙关键词", refuseMapDeail);
		refuseMapDeail.put("event_type", "ET_kw_update_round");
		refuseMapDeail.put("event_eval", "");
		refuseMapDeail.put("keywords", refuseKeywords);
		refuseMapDeail.put("help", "在忙");
		
		String refuseJson = JSON.toJSONString(refuseMap, SerializerFeature.SortField); 
		refuseJson = refuseJson.replace("[{", "{")
		.replace("}},{", "},").replace("}]", "}").replace("\"branch\":[]", "\"branch\":{}").replace("\\", "")
		.replace("[\"\"]", "[]").replace("[\"\"", "[\"").replace("\"\"]", "\"]").replace("]\"]", "]]").replace("[\"[\"", "[[\"");
		return refuseJson;
	}

	//生成rule_投诉.json
	@Override
	public String generateTouSuJson(String processId) {

		Map<String, Map<String, Object>> refuseMap = new LinkedHashMap<>();
		Map<String, Object> refuseMapDeail = new LinkedHashMap<>();
		List<String> refuseKeywords = new ArrayList<>();
		
		BotSentenceBranchExample example7 = new BotSentenceBranchExample();
		example7.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("投诉").andBranchNameEqualTo("negative");
		List<BotSentenceBranch> list7 = botSentenceBranchMapper.selectByExample(example7);
		if(null != list7 && list7.size() > 0) {
			BotSentenceBranch branch = list7.get(0);
			//查询关键词库列表
			refuseKeywords = fileGenerateService.getIntentKeys(branch.getIntents());
		}
		
		refuseMap.put("rule_投诉关键词", refuseMapDeail);
		refuseMapDeail.put("event_type", "ET_kw_update_round");
		refuseMapDeail.put("event_eval", "");
		refuseMapDeail.put("keywords", refuseKeywords);
		refuseMapDeail.put("help", "投诉");
		
		String refuseJson = JSON.toJSONString(refuseMap, SerializerFeature.SortField); 
		refuseJson = refuseJson.replace("[{", "{")
		.replace("}},{", "},").replace("}]", "}").replace("\"branch\":[]", "\"branch\":{}").replace("\\", "")
		.replace("[\"\"]", "[]").replace("[\"\"", "[\"").replace("\"\"]", "\"]").replace("]\"]", "]]").replace("[\"[\"", "[[\"");
		return refuseJson;
	}

	@Override
	public String generateJuJueJson(String processId) {
		Map<String, Map<String, Object>> refuseMap = new LinkedHashMap<>();
		Map<String, Object> refuseMapDeail = new LinkedHashMap<>();
		List<String> refuseKeywords = new ArrayList<>();
		
		BotSentenceBranchExample example1 = new BotSentenceBranchExample();
		example1.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("拒绝").andBranchNameEqualTo("negative");
		List<BotSentenceBranch> list1 = botSentenceBranchMapper.selectByExample(example1);
		if(null != list1 && list1.size() > 0) {
			BotSentenceBranch branch = list1.get(0);
			refuseKeywords = fileGenerateService.getIntentKeys(branch.getIntents());
		}
		refuseMap.put("rule_拒绝关键词", refuseMapDeail);
		refuseMapDeail.put("event_type", "ET_kw_update_round");
		refuseMapDeail.put("event_eval", "");
		refuseMapDeail.put("keywords", refuseKeywords);
		refuseMapDeail.put("help", "拒绝");
		
		String refuseJson = JSON.toJSONString(refuseMap, SerializerFeature.SortField); 
		refuseJson = refuseJson.replace("[{", "{")
		.replace("}},{", "},").replace("}]", "}").replace("\"branch\":[]", "\"branch\":{}").replace("\\", "")
		.replace("[\"\"]", "[]").replace("[\"\"", "[\"").replace("\"\"]", "\"]").replace("]\"]", "]]").replace("[\"[\"", "[[\"");
	
		return refuseJson;
	}

	@Override
	public String generateCommonJson(String processId, boolean needTts) {
		LinkedHashMap<String, Object> commonMap = new LinkedHashMap<>();
		
		BotSentenceProcess process = botSentenceProcessService.queryBotsentenceProcessInfo(processId);
		
		commonMap.put("templateName", process.getTemplateName());
		commonMap.put("templateId", process.getTemplateId());
		commonMap.put("trade", process.getIndustry());
		commonMap.put("tts", false);
		commonMap.put("agent", false);
		
		//判断是否需要TTS
		commonMap.put("tts", needTts);
		//判断是否需要转人工
		BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
		branchExample.createCriteria().andProcessIdEqualTo(processId).andNeedAgentEqualTo(Constant.NEED_AGENT_YES);
		int agentNum = botSentenceBranchMapper.countByExample(branchExample);
		if(agentNum > 0) {
			commonMap.put("agent", true);
		}
		
		String jsonString = null;
		try {
			jsonString = BotSentenceUtil.formatJavaToJson(commonMap, LinkedHashMap.class);
		} catch (Exception e) {
			logger.error("转换通用信息common.json异常...", e);
			throw new CommonException("转换通用信息common.json异常...");
		}
		return jsonString;
	}
}
