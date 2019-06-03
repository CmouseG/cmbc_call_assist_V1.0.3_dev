package com.guiji.botsentence.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.controller.server.vo.*;
import com.guiji.botsentence.dao.*;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.service.IFileGenerateService;
import com.guiji.botsentence.service.ITtsService;
import com.guiji.botsentence.service.IVoliceService;
import com.guiji.botsentence.util.AudioConvertUtil;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.util.TtsUtil;
import com.guiji.botsentence.util.enums.TtsTaskParamEnum;
import com.guiji.botsentence.util.enums.TtsTaskTypeEnum;
import com.guiji.common.exception.CommonException;
import com.guiji.component.client.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

/**
 * 不适用addition表
 * @Description:
 * @author liyang  
 * @date 2018年9月2日  
 *
 */
@Service
public class FileGenerateServiceImpl implements IFileGenerateService {

	@javax.annotation.Resource
	private BotSentenceProcessMapper botSentenceProcessMapper;

	@javax.annotation.Resource
	private BotSentenceDomainMapper botSentenceDomainMapper;

	@javax.annotation.Resource
	private BotSentenceBranchMapper botSentenceBranchMapper;
	@javax.annotation.Resource
	private VoliceInfoMapper voliceInfoMapper;
	@javax.annotation.Resource
	private BotSentenceIntentMapper botSentenceIntentMapper;
	@javax.annotation.Resource
	private IVoliceService voliceService;
	@javax.annotation.Resource
	private BotSentenceLabelMapper botSentenceLabelMapper;
	@javax.annotation.Resource
	private BotSentenceAdditionMapper botSentenceAdditionMapper;

	@javax.annotation.Resource
	private BotSentenceTtsServiceImpl botSentenceTtsServiceImpl;
	
	@javax.annotation.Resource
	private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;
	
	@javax.annotation.Resource
	private BotSentenceTtsParamMapper botSentenceTtsParamMapper;
	
	@javax.annotation.Resource
	private BotSentenceTtsBackupMapper botSentenceTtsBackupMapper;
	
	@javax.annotation.Resource
	private BotSentenceProcessServiceImpl botSentenceProcessService;
	
	@javax.annotation.Resource
	private BotsentenceVariableServiceImpl botsentenceVariableService;

	@javax.annotation.Resource
	private ITtsService iTtsService;
	
	@Value("${template.dir}")
	private String tempDir;
	@Value("${export.dir}")
	private String exportDir;
	@Value("${local.wav.dir.pre}")
	private String localWavDirPre;
	@Value("${offline}")
	private boolean offline;
	@Value("${standard.head.dir}")
	private String standardHeadDir;
	@Value("${template.copyfiles.dir}")
	private String copyfilesDir;

	private static String FILE_SEPARATOR = System.getProperty("file.separator");

	private Logger logger = LoggerFactory.getLogger(FileGenerateServiceImpl.class);

	@Override
	public File fileGenerate(String processId, String dirName, String type, String userId) throws IOException {

		// 获取流程对象
		BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(processId);
		String templateId = botSentenceProcess.getTemplateId();

		if(templateId.contains("trade_")) {
			templateId = templateId.replaceAll("trade_", "");
		}

		// 拷贝一份模板文件出来
		String processDir = tempDir + FILE_SEPARATOR + dirName ;
		
		File processDirFile = new File(processDir);
		if(processDirFile.exists()) {
			logger.info("当前目录已存在，先清空该目录 " + processDir);
			try {
				FileUtils.deleteDirectory(processDirFile);
			} catch (IOException e) {
				logger.error("删除目录异常...", e);
				return null;
			}
		}
		
		String templateCfgsDir = processDir+ FILE_SEPARATOR + templateId;
		
		try {
			FileUtil.copyDir(copyfilesDir, templateCfgsDir);
		} catch (IOException e) {
			logger.error("copy file failed !! " + templateId, e);
			return null;
		}
		
		
		// domain的json文件所放的路径
		String domainDir = templateCfgsDir + FILE_SEPARATOR + "new_domain_cfg" + FILE_SEPARATOR;
		String selectDir = templateCfgsDir + FILE_SEPARATOR + "select_cfg" + FILE_SEPARATOR;
		String wavDir = processDir+ FILE_SEPARATOR + templateId.replaceAll("_en", "") +"_rec" + FILE_SEPARATOR;
		
		File wavfile = new File(wavDir);
		if(!wavfile.exists()) {
			wavfile.mkdirs();
		}

		// 获取录音文件列表
		VoliceInfoExample example = new VoliceInfoExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<VoliceInfo> voliceInfos = voliceInfoMapper.selectByExample(example);
		for(VoliceInfo temp : voliceInfos) {//把逗号全部替换成中文下逗号，为了后面生成json格式化
			String content = temp.getContent();
			content = content.replace(",", "，");
			temp.setContent(content);
		}
		
		Map<Long, String> voliceMap = new HashMap<Long, String>();
		
		Map<Integer, String> idsMap = new HashMap<>();
		Map<Long, Integer> voliceIdsMap = new HashMap<>();
		Map<Long, Integer> interruptMap = new HashMap<>();
		boolean needTts = false;
		
		int size = voliceInfos.size();
		for (int i = 1; i <= size; i++) {
			if(Constant.VOLICE_TYPE_INTERRUPT.equals(voliceInfos.get(i - 1).getType())) {
				interruptMap.put(voliceInfos.get(i - 1).getVoliceId(), 8000);
			}
			voliceIdsMap.put(voliceInfos.get(i - 1).getVoliceId(), i);
			idsMap.put(i, voliceInfos.get(i - 1).getContent());
			String content = voliceInfos.get(i - 1).getContent() + "*" + i;
			voliceMap.put(voliceInfos.get(i - 1).getVoliceId(), content);
			
			if(!needTts) {
				if(BotSentenceUtil.validateContainParam(voliceInfos.get(i - 1).getContent())) {
					needTts = true;
				}
			}
		}

		// 获取所有domain
		BotSentenceDomainExample botSentenceDomainExample = new BotSentenceDomainExample();
		botSentenceDomainExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceDomain> domains = botSentenceDomainMapper.selectByExample(botSentenceDomainExample);

		// 获取所有的branch
		BotSentenceBranchExample botSentenceBranchExample = new BotSentenceBranchExample();
		botSentenceBranchExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceBranch> botSentenceBranchs = botSentenceBranchMapper.selectByExample(botSentenceBranchExample);

		List<DomainVO> domainVOList = new ArrayList<>();
		List<String> agentKeywords = new ArrayList<>();
		BotSentenceOptions botSentenceOptions = botsentenceVariableService.getOptionsByProcessId(processId);
		// 对所有domain进行遍历
		for (BotSentenceDomain botSentenceDomain : domains) {

			String domainName = botSentenceDomain.getDomainName();
			DomainVO domainVO = new DomainVO();
			JSONObject jsonObject = new JSONObject(true);
			domainVOList.add(domainVO);
			//判断静音开关是否打开决定 是否要生成静音.json
			if("静音".equals(domainName) && null!= botSentenceOptions.getSilenceWaitStart() && !botSentenceOptions.getSilenceWaitStart()) {
				continue;
			}
			
			domainVO.setName(domainName);
			jsonObject.put("name", domainName);
			jsonObject.put("category", botSentenceDomain.getCategory());
			jsonObject.put("type", botSentenceDomain.getType());
						
			// 设置com_domain
			String comDomian = botSentenceDomain.getComDomain();
			if (!StringUtils.isBlank(comDomian)) {
				domainVO.setCom_domain(comDomian);
				jsonObject.put("com_domain", comDomian);
			}
			
			
			List<String> refuseResponse = new ArrayList<>();
			BranchRefuseVO refuseBranchNodeVO = new BranchRefuseVO();
			
			// 设置branch
			List<Map> branchShow = new ArrayList<Map>();
			List<String> allKeywords = new ArrayList<>();
			// 对所有的brach进行遍历
			for (BotSentenceBranch botSentenceBranch : botSentenceBranchs) {
				String branchName = botSentenceBranch.getBranchName();
				Map map = new HashMap();
				
				String branchDomain = botSentenceBranch.getDomain();
				String needAgent = botSentenceBranch.getNeedAgent();
				if(Constant.DOMAIN_TYPE_AGENT.equals(domainName)) {//处理转人工
					if(Constant.NEED_AGENT_YES.equals(needAgent)) {
						BranchSpecialQuestionVO BranchNodeVO = new BranchSpecialQuestionVO();
						BranchNodeVO.setNext(botSentenceBranch.getNext());
						List<String> keywords = getIntentKeys(botSentenceBranch.getIntents());
						BranchNodeVO.setKeys(keywords);
						BranchNodeVO.setEnd(botSentenceBranch.getEnd());
						String userAsk = botSentenceBranch.getUserAsk();
						if(StringUtils.isNotBlank(userAsk)) {
							userAsk = userAsk.replace(",", "，");
						}
						BranchNodeVO.setUser_ask(userAsk);
						BranchNodeVO.setResponse(getVolies(botSentenceBranch.getResponse(), voliceMap));
						map.put(branchName, BranchNodeVO);
						branchShow.add(map);
						allKeywords.addAll(keywords);
						agentKeywords.addAll(keywords);
					}
					if (Constant.DOMAIN_TYPE_AGENT.equals(botSentenceBranch.getDomain()) && branchName.equals("enter_branch")){
						domainVO.setEnter(getVolies(botSentenceBranch.getResponse(), voliceMap));
						jsonObject.put("enter", getVolies(botSentenceBranch.getResponse(), voliceMap));
					}else if(Constant.DOMAIN_TYPE_AGENT.equals(botSentenceBranch.getDomain()) && branchName.equals("failed_enter_branch")) {
						domainVO.setFailed_enter(getVolies(botSentenceBranch.getResponse(), voliceMap));
						jsonObject.put("failed_enter", getVolies(botSentenceBranch.getResponse(), voliceMap));
					}
				}else {
					if (branchDomain.equals(domainName)) {
	
						if (branchName.equals("enter_branch")){
							domainVO.setEnter(getVolies(botSentenceBranch.getResponse(), voliceMap));
							jsonObject.put("enter", getVolies(botSentenceBranch.getResponse(), voliceMap));
						}else if(branchName.equals("failed_enter_branch")) {
							domainVO.setFailed_enter(getVolies(botSentenceBranch.getResponse(), voliceMap));
							jsonObject.put("failed_enter", getVolies(botSentenceBranch.getResponse(), voliceMap));
						} else {
							if (branchName.equals("positive")) {
								BranchPositiveVO BranchNodeVO = new BranchPositiveVO();
								BranchNodeVO.setNext(botSentenceBranch.getNext());
								List<String> keywords = getIntentKeys(botSentenceBranch.getIntents());
								BranchNodeVO.setKeys(keywords);
								map.put(branchName, BranchNodeVO);
								allKeywords.addAll(keywords);
								branchShow.add(map);
							} else if (branchDomain.equals("一般问题")
									&& branchName.startsWith("special")
									&& !Constant.NEED_AGENT_YES.equals(needAgent)) {
								BranchSpecialQuestionVO BranchNodeVO = new BranchSpecialQuestionVO();
								BranchNodeVO.setNext(botSentenceBranch.getNext());
								List<String> keywords = getIntentKeys(botSentenceBranch.getIntents());
								BranchNodeVO.setKeys(keywords);
								BranchNodeVO.setEnd(botSentenceBranch.getEnd());
								if(org.apache.commons.lang.StringUtils.isNotBlank(botSentenceBranch.getRule()) && Constant.BRANCH_RULE_02.equals(botSentenceBranch.getRule())) {
									BranchNodeVO.setEnd_to_enter(true);
								}
								
								String userAsk = botSentenceBranch.getUserAsk();
								if(StringUtils.isNotBlank(userAsk)) {
									userAsk = userAsk.replace(",", "，");
								}
								BranchNodeVO.setUser_ask(userAsk);
								BranchNodeVO.setResponse(getVolies(botSentenceBranch.getResponse(), voliceMap));
								
								//设置一般问题的
								if("一般问题".equals(domainName) && null != botSentenceOptions.getSpecialLimitStart() && botSentenceOptions.getSpecialLimitStart()) {
									BranchNodeVO.setIs_special_limit_free(botSentenceOptions.getSpecialLimitStart());
								}
								
								map.put(branchName, BranchNodeVO);
								branchShow.add(map);
								allKeywords.addAll(keywords);
							}else if(branchName.startsWith("refuse_")){
								refuseResponse.addAll(getVolies(botSentenceBranch.getResponse(), voliceMap));
							} else {
								BranchNegativeVO BranchNodeVO = new BranchNegativeVO();
								BranchNodeVO.setNext(botSentenceBranch.getNext());
								List<String> keywords = getIntentKeys(botSentenceBranch.getIntents());
								BranchNodeVO.setKeys(keywords);
								BranchNodeVO.setEnd(botSentenceBranch.getEnd());
								BranchNodeVO.setResponse(getVolies(botSentenceBranch.getResponse(), voliceMap));
								map.put(branchName, BranchNodeVO);
								branchShow.add(map);
								allKeywords.addAll(keywords);
							}
						}
					}
				}

				/*//设置重复次数是否受限
				if(null != botSentenceOptions.getSpecialLimitStart() && botSentenceOptions.getSpecialLimitStart()) {
					//写入每个流程
					if(null != botSentenceDomain.getIsSpecialLimitFree() && botSentenceDomain.getIsSpecialLimitFree()) {
						jsonObject.put("is_special_limit_free", botSentenceDomain.getIsSpecialLimitFree());
					}
				}*/
				
			}
			//处理挽回话术(除结束之外的每个主domain都必须有)
			if((Constant.CATEGORY_TYPE_1.equals(botSentenceDomain.getCategory())
					&& !Constant.DOMAIN_TYPE_END.equals(botSentenceDomain.getType()))){
				BotSentenceBranchExample intentExample = new BotSentenceBranchExample();
				if(!Constant.DOMAIN_TYPE_AGENT.equals(botSentenceDomain.getType())) {
					intentExample.createCriteria()
							.andProcessIdEqualTo(processId)
							.andDomainEqualTo("拒绝")
							.andBranchNameEqualTo("negative");
					List<BotSentenceBranch> intentList = botSentenceBranchMapper.selectByExample(intentExample);
					if(null != intentList && intentList.size() > 0) {
						List<String> keywords = getIntentKeys(intentList.get(0).getIntents());
						
						String keywords_str = "";
						for(String temp : keywords) {
							keywords_str = keywords_str + "," + temp;
						}
						if(keywords_str.length() > 0) {
							keywords_str = keywords_str.substring(1, keywords_str.length());
						}
						keywords_str = "[" + keywords_str + "]";
						
						List<String> keywordsList = BotSentenceUtil.getKeywords(keywords_str);
						String singleKeywordStr = keywordsList.get(0);
						String complexywordStr = keywordsList.get(1);
						String [] single_array = singleKeywordStr.split(",");
						
						List<String> allKeyWords2 = new ArrayList<>();
						for(String temp : allKeywords) {
							String [] array = temp.split(",");
							if(null != array && array.length > 0) {
								for(int i = 0 ; i < array.length ; i++) {
									if(StringUtils.isNotBlank(array[i])) {
										allKeyWords2.add(array[i]);
									}
								}
							}
						}
						
						List<String> allRefuseKeyWords = new ArrayList<>();
						for(String temp : single_array) {
							String [] array = temp.split(",");
							if(null != array && array.length > 0) {
								for(int i = 0 ; i < array.length ; i++) {
									if(StringUtils.isNotBlank(array[i])) {
										allRefuseKeyWords.add(array[i]);
									}
								}
							}
						}
						
						for(String keyword : allKeyWords2) {
							if(allRefuseKeyWords.contains(keyword)){
								allRefuseKeyWords.remove(keyword);
							}
						}
						
						List<String> newRefuseKeyWords = new ArrayList<>();
						String newRefuseKeyWordsStr = "";
						
						for(String temp : allRefuseKeyWords) {
							newRefuseKeyWordsStr = newRefuseKeyWordsStr + "," + temp;
						}
						if(StringUtils.isNotBlank(complexywordStr)) {
							newRefuseKeyWordsStr = newRefuseKeyWordsStr + "," + complexywordStr;
						}
						
						if(newRefuseKeyWordsStr.length() > 0) {
							newRefuseKeyWordsStr = newRefuseKeyWordsStr.substring(1, newRefuseKeyWordsStr.length());
						}
						newRefuseKeyWords.add(newRefuseKeyWordsStr);
						
						Map map = new HashMap();
						refuseBranchNodeVO.setNext(domainName);
						refuseBranchNodeVO.setKeys(newRefuseKeyWords);
						refuseBranchNodeVO.setEnd("结束");
//						refuseBranchNodeVO.setIs_special_limit_free(true);
						refuseBranchNodeVO.setResponse(refuseResponse);
						map.put("refuse_" + domainName, refuseBranchNodeVO);
						branchShow.add(map);
					}
				}
			}
			
			domainVO.setBranch(branchShow);
			jsonObject.put("branch", branchShow);
			
			domainVO.setLimit(1);
			jsonObject.put("limit", 1);
			
			if("拒绝".equals(domainName)) {
				BotSentenceBranchExample example1 = new BotSentenceBranchExample();
				example1.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("拒绝").andBranchNameEqualTo("negative");
				List<BotSentenceBranch> list1 = botSentenceBranchMapper.selectByExample(example1);
				if(null != list1 && list1.size() > 0) {
					BotSentenceBranch branch = list1.get(0);
					if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
							&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
						String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
						domainVO.setLimit(respArray.length);
						jsonObject.put("limit", respArray.length);
					}
				}
			}
			
			//设置变量
			
			if(null != botSentenceOptions.getIgnoreButDomainsStart() && botSentenceOptions.getIgnoreButDomainsStart()) {
				if(StringUtils.isNotBlank(botSentenceDomain.getIgnoreButDomains())) {
					//写入每下流程
					//用户回复未超过4个字，且未匹配到关键词时
					//用户回复超过4个字，且未匹配到关键词
					//用户回复无声音时
					//设置以上三个变量同时放入到相应的ignore_but_domains
					List<String> ignore_but_domains = BotSentenceUtil.StringToList(botSentenceDomain.getIgnoreButDomains()); 
					//Arrays.asList(botSentenceDomain.getIgnoreButDomains().split(","));
					if(StringUtils.isNotBlank(botSentenceDomain.getNotMatchLess4To()) && !ignore_but_domains.contains(botSentenceDomain.getNotMatchLess4To())) {
						ignore_but_domains.add(botSentenceDomain.getNotMatchLess4To());
					}
					if(StringUtils.isNotBlank(botSentenceDomain.getNotMatchTo()) && !ignore_but_domains.contains(botSentenceDomain.getNotMatchTo())) {
						ignore_but_domains.add(botSentenceDomain.getNotMatchTo());
					}
					if(StringUtils.isNotBlank(botSentenceDomain.getNoWordsTo()) && !ignore_but_domains.contains(botSentenceDomain.getNoWordsTo())) {
						ignore_but_domains.add(botSentenceDomain.getNoWordsTo());
					}
					jsonObject.put("ignore_but_domains", ignore_but_domains);
				}
			}
			if(null != botSentenceOptions.getIgnoreButNegativeStart() && botSentenceOptions.getIgnoreButNegativeStart()) {
				if(null != botSentenceDomain.getIgnoreButNegative() && botSentenceDomain.getIgnoreButNegative()) {
					//写入每下流程
					jsonObject.put("ignore_but_negative", botSentenceDomain.getIgnoreButNegative());
				}
			}
			if(null != botSentenceOptions.getIgnoreUserSentenceStart() && botSentenceOptions.getIgnoreUserSentenceStart()) {
				if(null != botSentenceDomain.getIgnoreUserSentence() && botSentenceDomain.getIgnoreUserSentence()) {
					//定稿每个流程
					//domainVO.setIgnore_user_sentence(Arrays.asList(botSentenceDomain.getIgnoreUserSentence().split(",")));
					jsonObject.put("ignore_user_sentence", botSentenceDomain.getIgnoreUserSentence());
				}
			}
			if(null != botSentenceOptions.getUserDefineMatchOrder() && botSentenceOptions.getUserDefineMatchOrder()) {
				if(StringUtils.isNotBlank(botSentenceDomain.getMatchOrder())) {
					//写入每个流程
					//domainVO.setMatch_order(Arrays.asList(botSentenceDomain.getMatchOrder().split(",")));
					jsonObject.put("match_order", Arrays.asList(botSentenceDomain.getMatchOrder().split(",")));
				}
			}
			if(null != botSentenceOptions.getNotMatchLess4ToStart() && botSentenceOptions.getNotMatchLess4ToStart()) {
				if(StringUtils.isNotBlank(botSentenceDomain.getNotMatchLess4To())) {
					//写入每个流程
					//domainVO.setNot_match_less4_to(Arrays.asList(botSentenceDomain.getNotMatchLess4To().split(",")));
					jsonObject.put("not_match_less4_to", botSentenceDomain.getNotMatchLess4To());
				}
			}
			if(null != botSentenceOptions.getNotMatchToStart() && botSentenceOptions.getNotMatchToStart()) {
				if(StringUtils.isNotBlank(botSentenceDomain.getNotMatchTo())) {
					//写入每个流程
					//domainVO.setNot_match_to(Arrays.asList(botSentenceDomain.getNotMatchTo().split(",")));
					jsonObject.put("not_match_to", botSentenceDomain.getNotMatchTo());
				}
			}
			if(null != botSentenceOptions.getNoWordsToStart() && botSentenceOptions.getNoWordsToStart()) {
				if(StringUtils.isNotBlank(botSentenceDomain.getNoWordsTo())) {
					//写入每个流程
					//domainVO.setNo_words_to(Arrays.asList(botSentenceDomain.getNoWordsTo().split(",")));
					jsonObject.put("no_words_to", botSentenceDomain.getNoWordsTo());
				}
			}
			
			//设置优先级
			if(null != botSentenceOptions.getUserDefineMatchOrder() && botSentenceOptions.getUserDefineMatchOrder()) {//是否需要自定义排序
				List<String> matchOrderList = botsentenceVariableService.getUseMatchOrderList(processId, botSentenceDomain.getDomainName());
				if(null != matchOrderList && matchOrderList.size() > 0) {
					jsonObject.put("match_order", matchOrderList);
				}
			}
			
			
			
			//设置坐标
			jsonObject.put("position_x", botSentenceDomain.getPositionX());
			jsonObject.put("position_y", botSentenceDomain.getPositionY());

			String domainJson = JSON.toJSONString(jsonObject, SerializerFeature.SortField); 
			//jsonObject.toJSONString(jsonObject, SerializerFeature.SortField);
			domainJson = "{\"" + domainName + "\":"
					+ domainJson
					.replace("[{", "{")
					.replace("}},{", "},")
					.replace("}]", "}")
					.replace("\"branch\":[]", "\"branch\":{}")
					.replace("\\", "")
					.replace("[\"\"]", "[]")
					.replace("[\"\"", "[\"")
					.replace("\"\"]", "\"]")
					.replace("]\"]", "]]")
					.replace("[\"[\"", "[[\"")
					+ "}";
	
			//domainJson = BotSentenceUtil.javaToJson(domainVO, DomainVO.class);
			try {
				FileUtil.writeFile(domainDir + domainName + ".json", formatJson(domainJson));
			} catch (IOException e) {
				logger.error("write domain json file failed : " + domainDir + domainName + ".json", e);
				return null;
			}
		}

		// 生成select.json
		String selectJson = botsentenceVariableService.generateSelectJson(processId, agentKeywords);
		try {
			FileUtil.writeFile(selectDir + "select.json", formatJson(selectJson));
		} catch (IOException e) {
			logger.error("write select json file failed : " + selectDir + "select.json", e);
			return null;
		}
		
		
		//生成打分相关文件stat_cfg
		String statJson = botsentenceVariableService.generateStatJson(processId);
		String statPath = templateCfgsDir + FILE_SEPARATOR + "stat_cfg" + FILE_SEPARATOR + "stat.json";
		try {
			FileUtil.writeFile(statPath, statJson);
		} catch (IOException e) {
			logger.error("generate stat.json has exception:", e);
			return null;
		}
		
		
		//生成rule_拒绝关键词.json
		String juJueJson = botsentenceVariableService.generateJuJueJson(processId);
		String juJuePath = templateCfgsDir + FILE_SEPARATOR + "stat_cfg" + FILE_SEPARATOR + "rule_拒绝关键词.json";
		try {
			FileUtil.writeFile(juJuePath, formatJson(juJueJson));
		} catch (IOException e) {
			logger.error("generate rule_拒绝关键词.json has exception:", e);
			return null;
		}
		
		//生成rule_在忙关键词.json
		String zaiMangJson = botsentenceVariableService.generateZaiMangJson(processId);
		String zaiMangPath = templateCfgsDir + FILE_SEPARATOR + "stat_cfg" + FILE_SEPARATOR + "rule_在忙关键词.json";
		try {
			FileUtil.writeFile(zaiMangPath, formatJson(zaiMangJson));
		} catch (IOException e) {
			logger.error("generate rule_在忙关键词.json has exception:", e);
			return null;
		}

		//生成rule_投诉关键词.json
		String touSuJson = botsentenceVariableService.generateTouSuJson(processId);
		String touSuPath = templateCfgsDir + FILE_SEPARATOR + "stat_cfg" + FILE_SEPARATOR + "rule_投诉关键词.json";
		try {
			FileUtil.writeFile(touSuPath, formatJson(touSuJson));
		} catch (IOException e) {
			logger.error("generate rule_投诉关键词.json has exception:", e);
			return null;
		}
		
		//生成通用信息common.json
		String commonJson = botsentenceVariableService.generateCommonJson(processId, needTts);
		String commonPath = templateCfgsDir+ FILE_SEPARATOR + "common.json";
		try {
			FileUtil.writeFile(commonPath, commonJson);
		} catch (IOException e) {
			logger.error("generate common.json has exception:", e);
			return null;
		}

		// 下载音频文件
		RestTemplate restTemplate = new RestTemplate();
		for (int i = 1; i <= size; i++) {
			String wavURL = voliceInfos.get(i - 1).getVoliceUrl();
			try {
				if (StringUtils.isNotBlank(wavURL)) {
					if(interruptMap.containsKey(voliceInfos.get(i - 1).getVoliceId())) {
						generateWAV(restTemplate, wavURL, interruptMap.get(voliceInfos.get(i - 1).getVoliceId()).toString(), wavfile.getPath());
					}else {
						generateWAV(restTemplate, wavURL, Integer.toString(i), wavfile.getPath());
					}
				}
			} catch (IOException e) {
				logger.error("generate wav has exception:" + wavURL, e);
				return null;
			}
		}
		
		//生成录音文件文本相关文件s2w_cfg
		//创建目录
		File fileParent = new File(templateCfgsDir+ FILE_SEPARATOR + "s2w_cfg");
		if(!fileParent.exists()) {
			fileParent.mkdirs();
		}

		String s2w_ids_path = templateCfgsDir+ FILE_SEPARATOR + "s2w_cfg" + FILE_SEPARATOR + "s2w_ids.txt";
		String s2w_need_record_path = templateCfgsDir+ FILE_SEPARATOR + "s2w_cfg" + FILE_SEPARATOR + "s2w_need_record.txt";
		String s2w_new_path = templateCfgsDir+ FILE_SEPARATOR + "s2w_cfg" + FILE_SEPARATOR + "s2w_new.txt";

		String s2w_ids_txt= "";
		String s2w_need_record_txt= "";
		String s2w_new_txt= "";
		
		for(int key : idsMap.keySet()) {
			s2w_ids_txt = s2w_ids_txt + key + "\r\n";
			s2w_need_record_txt = s2w_need_record_txt + key + "<------------------->" + idsMap.get(key) + "\r\n";
			s2w_new_txt = s2w_new_txt + key + "<------------------->" + idsMap.get(key) + "\r\n";
		}
		
		
		try {
			FileUtil.writeFile(s2w_ids_path, s2w_ids_txt);
			FileUtil.writeFile(s2w_need_record_path, s2w_need_record_txt);
			FileUtil.writeFile(s2w_new_path, s2w_new_txt);
		}catch (IOException e) {
			logger.error("generate s2w_cfg文件 has exception:", e);
			return null;
		}

		// 生成一些附加的文件
		BotSentenceAddition botSentenceAddition = botSentenceAdditionMapper.selectByPrimaryKey(processId);

		//生成options.josn
		String optionJson = botsentenceVariableService.generateOptionJson(processId, voliceMap);
		String optionPath = templateCfgsDir+ FILE_SEPARATOR + "options.json";
		try {
			FileUtil.writeFile(optionPath, formatJson(optionJson));
		} catch (IOException e) {
			logger.error("generate options.json has exception:", e);
			return null;
		}
		
		//生成回访模板json字符串
		if(null != botSentenceOptions.getSurveyStart() && botSentenceOptions.getSurveyStart()) {
			String surveyJson = botsentenceVariableService.generateSurveyJson(processId);
			if(StringUtils.isNotBlank(surveyJson)) {
				logger.info("当前话术模板需要生成回访模板survey.json");
				String surveyPath = templateCfgsDir+ FILE_SEPARATOR + "survey.json";
				try {
					FileUtil.writeFile(surveyPath, surveyJson);
				} catch (IOException e) {
					logger.error("generate survey.json has exception:", e);
					return null;
				}
			}else {
				logger.info("当前话术模板不需要生成回访模板...");
			}
		}
		
		try {
			String simTxt = botSentenceAddition.getSimTxt();
			String simPath = templateCfgsDir + FILE_SEPARATOR + "sim_dict" + FILE_SEPARATOR + "sim.txt";
			simTxt = formatJson(simTxt).replace("\"", "");
			if(simTxt.startsWith("{")) {
				simTxt = simTxt.substring(1, simTxt.length());
			}
			if(simTxt.endsWith("}")) {
				simTxt = simTxt.substring(0, simTxt.length()-1);
			}
			FileUtil.writeFile(simPath, simTxt);
		} catch (UnsupportedEncodingException e1) {
			logger.error("generate sim.txt has UnsupportedEncodingException:", e1);
			return null;
		}
		 catch (IOException e) {
			logger.error("generate sim.txt has exception:", e);
			return null;
		}
		
		String stopWordsTxt = botSentenceAddition.getStopwordsTxt();
		String stopWordsPath = templateCfgsDir + FILE_SEPARATOR + "jieba_dict" + FILE_SEPARATOR + "stopwords.txt";
		try {
			FileUtil.writeFile(stopWordsPath, stopWordsTxt);
		} catch (IOException e) {
			logger.error("generate stopwords.txt has exception:", e);
			return null;
		}
		
		try {
			String userDictTxt =botSentenceAddition.getUserdictTxt();
			String userDictPath = templateCfgsDir + FILE_SEPARATOR + "jieba_dict" + FILE_SEPARATOR + "userdict.txt";
			FileUtil.writeFile(userDictPath, userDictTxt);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			logger.error("generate userdict.txt has UnsupportedEncodingException:", e1);
			return null;
		}
		 catch (IOException e) {
			logger.error("generate userdict.txt has exception:", e);
			return null;
		}
		
		String weightsTxt = botsentenceVariableService.generateWeightJson(processId);
		String weightsPath = templateCfgsDir + FILE_SEPARATOR + "weights_cfg" + FILE_SEPARATOR + "weights.txt";
		try {
			FileUtil.writeFile(weightsPath, weightsTxt);
		} catch (IOException e) {
			logger.error("generate weights.txt has exception:", e);
			return null;
		}
		
		
		//判断是否需要tts
		logger.info("判断是否需要TTS合成..." + needTts);
		if(needTts) {
			logger.info("当前话术需要TTS合成");
			//生成replace.json
			Map<String, Object> replaceMap = new LinkedHashMap<String, Object>();
			replaceMap.put("template_tts_flag", "true");
			replaceMap.put("tts_new", "true");
			replaceMap.put("tts_partial", "true");
			replaceMap.put("use_speaker_flag", botSentenceProcess.getSoundType());
			
			List<Integer> wavs = new ArrayList<>();
			
			List<VoliceInfo> ttsVoliceList = new ArrayList<>();
			
			for(VoliceInfo temp: voliceInfos) {
				//判断当前文案否需要TTS合成
				if(BotSentenceUtil.validateContainParam(temp.getContent())) {
					logger.info("当前文案需要TTS合成");
					logger.info(temp.getContent());
					wavs.add(voliceIdsMap.get(temp.getVoliceId()));
					ttsVoliceList.add(temp);
				}
			}
			
			replaceMap.put("num_sentence_merge_lst", wavs);
			
			replaceMap.put("old_num_sentence_merge_lst", wavs);
			
			//voliceIdsMap.get(key);
			Map<String, List<String>> voiceIndexToWavIndexListMap = Maps.newLinkedHashMap();
			Map<String, String> wavIndexToContentMap = Maps.newLinkedHashMap();

			Map<String, String> voiceIndexToReplaceContentMap = Maps.newLinkedHashMap();

			for(VoliceInfo temp : ttsVoliceList) {
				if(StringUtils.isBlank(temp.getContent())){
					continue;
				}
			    BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
				ttsTaskExample.createCriteria()
						.andProcessIdEqualTo(processId)
						.andBusiIdEqualTo(temp.getVoliceId().toString())
						.andBusiTypeEqualTo(TtsTaskTypeEnum.CALL_RECORD.getKey());
				ttsTaskExample.setOrderByClause("seq asc");
				List<BotSentenceTtsTask> taskList = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);

				//按照seq从小到大排列
				taskList.sort((task1, task2) -> TtsUtil.getIndexFromSeq(task1.getSeq()) - TtsUtil.getIndexFromSeq(task2.getSeq()));
				List<String> indexWavNameList = Lists.newArrayList();
				int voiceIndex = voliceIdsMap.get(temp.getVoliceId());
				StringBuilder replaceContentBuilder = new StringBuilder();
				for (BotSentenceTtsTask task : taskList) {

					int seqIndex = TtsUtil.getIndexFromSeq(task.getSeq());
					String wavName = voiceIndex + "_" + seqIndex;
					indexWavNameList.add(wavName);


					if(TtsTaskParamEnum.NOT_PARAM.getKey().equals(task.getIsParam())) {
						replaceContentBuilder.append(task.getContent());

						if(StringUtils.isNotBlank(task.getVoliceUrl())) {
							generateWAV(restTemplate, task.getVoliceUrl(), wavName, wavfile.getPath());
						}
					}else if(TtsTaskParamEnum.IS_PARAM.getKey().equals(task.getIsParam())) {
						replaceContentBuilder
								.append("$")
								.append(wavName);
						wavIndexToContentMap.put(wavName, task.getContent());
					}
					voiceIndexToReplaceContentMap.put(voiceIndex + "_replace_sentence", replaceContentBuilder.toString());
					task.setWavName(wavName);
					botSentenceTtsTaskMapper.updateByPrimaryKey(task);
				}
				voiceIndexToWavIndexListMap.put(voliceIdsMap.get(temp.getVoliceId()).toString(), indexWavNameList);
			}
			
			replaceMap.put("rec_tts_wav", voiceIndexToWavIndexListMap);
			replaceMap.put("tts_pos", wavIndexToContentMap);
			List<BotSentenceTtsParam> ttsParamList = iTtsService.getSortedParams(processId);
			if(!CollectionUtils.isEmpty(ttsParamList)){
				List<String> paramKeys = Lists.newArrayList();
				List<String> paramTypes = Lists.newArrayList();
				ttsParamList.forEach(ttsParam -> {
					paramKeys.add(ttsParam.getParamKey());
					paramTypes.add(ttsParam.getParamType());
				});
				replaceMap.put("replace_variables_flag", paramKeys);
				replaceMap.put("replace_variables_type", paramTypes);
			}
			
			//生成备用话术
			for(VoliceInfo temp : ttsVoliceList) {
				int index = voliceIdsMap.get(temp.getVoliceId());
				String key = index + "_backup";
				
				//查询当前文案的备用文案
				BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
				backupExample.createCriteria().andProcessIdEqualTo(processId).andVoliceIdEqualTo(temp.getVoliceId());
				List<BotSentenceTtsBackup> backupList = botSentenceTtsBackupMapper.selectByExample(backupExample);
				if(null != backupList && backupList.size() > 0) {
					String value = backupList.get(0).getContent();
					replaceMap.put(key, value);
					if(StringUtils.isNotBlank(backupList.get(0).getUrl())) {
						//生成备用话术音频文件
						generateWAV(restTemplate, backupList.get(0).getUrl(), key, wavfile.getPath());
						
						//为了校验通过，生成当前文案的录音 
						generateWAV(restTemplate, backupList.get(0).getUrl(), index+"", wavfile.getPath());
					}
					//更新备用话术的音频文件
					BotSentenceTtsBackup backup = backupList.get(0);
					backup.setWavName(key);
					botSentenceTtsBackupMapper.updateByPrimaryKey(backup);
				}
			}
			
			replaceMap.putAll(voiceIndexToReplaceContentMap);
			
			String replacePath = templateCfgsDir+ FILE_SEPARATOR + "replace.json";
			String replaceJson = JSON.toJSONString(replaceMap).replace("\"[", "[").replace("]\"", "]").replace("\\", "");
			try {
				FileUtil.writeFile(replacePath, formatJson(replaceJson));
			} catch (IOException e) {
				logger.error("write replace json file failed : " + selectDir + "replace.json", e);
				return null;
			}
		}else {
			logger.info("当前话术不需要TTS合成");
		}
		
		if(StringUtils.isNotBlank(type) && Constant.APPROVE_TYPE.equals(type)) {
			//更新录音的文件名
			Set<Long> keys = voliceIdsMap.keySet();
			for(Long voliceId : keys) {
				VoliceInfo volice = voliceService.getVoliceInfo(voliceId);
				volice.setWavName(voliceIdsMap.get(voliceId).toString());
				volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
				volice.setLstUpdateUser(userId);
				voliceInfoMapper.updateByPrimaryKey(volice);
			}
		}
		
		
		//将文件发给sellbot
		File dir=new File(processDir);
		try {
			FileUtil.copyDir(dir.getPath(), dir.getParent()+FILE_SEPARATOR+ "src" + FILE_SEPARATOR + dir.getName());
		} catch (IOException e) {
			logger.error("复制生成json源文件异常....", e);
			return null;
		}
		
		 //voliceService.uploadVoliceJsonZip(dir, dirName,processId,templateId);
		 return dir;
	}
	
	
	public boolean autoDeploy(File dir, String dirName, String processId, String templateId, String userId) {
		return voliceService.uploadVoliceJsonZip(dir, dirName,processId,templateId, userId);
	}
	
	/**
	 * 下载音频文件
	 */
	private void generateWAV(RestTemplate template, String url,String name,String dir) throws IOException {
		if(offline) {
			logger.info("下载录音: 当前是离线模式");
			if(url.contains("static")) {
				url = localWavDirPre + url;
			}
			
			File localFile = new File(url);
			File file = new File(dir + FILE_SEPARATOR + "temp_"+ name + ".wav");
			if(!localFile.exists()) {
				return;
			}
			FileUtil.copyFile(url, file.getPath());
			
			File wavFile = new File(dir + FILE_SEPARATOR + "temp2_" + name + ".wav");
			boolean flag = AudioConvertUtil.converWav(file.getPath(), wavFile.getPath());
			if(!flag) {
				throw new CommonException("转换wav失败"+file.getName());
			}
			
			if(file.delete()) {
				logger.info("删除临时文件"+file.getName()+"成功...");
			}
			
			
			//去掉音频信息头
			File finnalFile = new File(dir + FILE_SEPARATOR + name + ".wav");
			boolean flag2 = AudioConvertUtil.removeHeadInfo(wavFile.getPath(), finnalFile.getPath(), standardHeadDir);
			if(!flag2) {
				throw new CommonException("清空头信息失败"+wavFile.getName());
			}
			if(wavFile.delete()) {
				logger.info("删除临时文件"+wavFile.getName()+"成功...");
			}
		
		}else {
			logger.info("下载录音: 当前是在线模式");
			logger.info("downloading...." + url);
			String os = System.getProperties().getProperty("os.name").toLowerCase();
			logger.info("当前操作系统: " + os);
			if(os.startsWith("win")) {
				HttpHeaders headers = new HttpHeaders();
				HttpEntity<Resource> httpEntity = new HttpEntity<Resource>(headers);
				ResponseEntity<byte[]> response = template.exchange(url, HttpMethod.GET, httpEntity, byte[].class);
				logger.info(">>>>> return code:" + response.getStatusCodeValue());
				File file = new File(dir + FILE_SEPARATOR + name + ".wav");
				
				FileOutputStream fos1 = new FileOutputStream(file);
				fos1.write(response.getBody());
				fos1.flush();
				fos1.close();
			}else {

				HttpHeaders headers = new HttpHeaders();
				HttpEntity<Resource> httpEntity = new HttpEntity<Resource>(headers);
				ResponseEntity<byte[]> response = template.exchange(url, HttpMethod.GET, httpEntity, byte[].class);
				logger.info(">>>>> return code:" + response.getStatusCodeValue());
				File file = new File(dir + FILE_SEPARATOR + "temp_"+ name + ".wav");
				
				FileOutputStream fos1 = new FileOutputStream(file);
				fos1.write(response.getBody());
				fos1.flush();
				fos1.close();

				
				File wavFile = new File(dir + FILE_SEPARATOR + "temp2_" + name + ".wav");
				boolean flag = AudioConvertUtil.converWav(file.getPath(), wavFile.getPath());
				if(!flag) {
					throw new CommonException("转换wav失败"+file.getName());
				}
				
				if(file.delete()) {
					logger.info("删除临时文件"+file.getName()+"成功...");
				}
				
				
				//去掉音频信息头
				File finnalFile = new File(dir + FILE_SEPARATOR + name + ".wav");
				boolean flag2 = AudioConvertUtil.removeHeadInfo(wavFile.getPath(), finnalFile.getPath(), standardHeadDir);
				if(!flag2) {
					throw new CommonException("清空头信息失败"+wavFile.getName());
				}
				if(wavFile.delete()) {
					logger.info("删除临时文件"+wavFile.getName()+"成功...");
				}
			}
		}
	}
	

	/**
	 * 通过逗号连接的id，获取录音文件内容
	 */
	public List<String> getVolies(String ids, Map<Long, String> voliceMap) {

		List<String> enterList = new ArrayList<String>();

		if (!StringUtils.isBlank(ids)) {

			if (ids.contains("[")) {
				ids = ids.replace("[", "");
				ids = ids.replace("]", "").trim();
			}
			if (!StringUtils.isBlank(ids)) {
				String[] enterArr = ids.split(",");
				for (String voliceId : enterArr) {
					if (voliceMap.get(Long.parseLong(voliceId)) != null) {
						String response = voliceMap.get(Long.parseLong(voliceId));
						response = response.replace(",", "，");
						enterList.add(response);
					}
				}
			}
		}
		return enterList;
	}

	/**
	  *  根据意图的id，获取keys
	 */
	public List<String> getIntentKeys(String ids) {

		List<String> allKeywords = Lists.newArrayList();
		List<String> emptyKeywords = Lists.newArrayList();
		emptyKeywords.add("");
		if(StringUtils.isBlank(ids)){
			return emptyKeywords;
		}

		String[] intendIdArray = ids.split(",");
		List<Long> intendIds = Lists.newArrayList();

		for(String intendId : intendIdArray){
			intendIds.add(Long.parseLong(intendId));
		}

		if(CollectionUtils.isEmpty(intendIds)){
			return emptyKeywords;
		}

		BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
		intentExample.createCriteria().andIdIn(intendIds);
		List<BotSentenceIntent> intents = botSentenceIntentMapper.selectByExampleWithBLOBs(intentExample);

		if(CollectionUtils.isEmpty(intents)){
			return emptyKeywords;
		}

		List<String> keywordsList = Lists.newArrayList();
		intents.forEach(intent -> {
			keywordsList.addAll(JSON.parseArray(intent.getKeywords(), String.class));
		});

		if(CollectionUtils.isEmpty(keywordsList)){
			allKeywords.add("");
			return allKeywords;
		}

		List<String> keywordsReusltList = Lists.newArrayList();
		keywordsList.forEach(keyword -> {
			if(keyword.contains("[")){
				keywordsReusltList.add(keyword);
			}else {
				keywordsReusltList.add("\"" + keyword +"\"");
			}
		});

		allKeywords.add(String.join(",", keywordsReusltList));
		return allKeywords;
	}
	
	public static void main(String[] args) {
		List<Integer> indexList = new ArrayList<>();
		indexList.add(1);
		indexList.add(2);
		indexList.add(11);
		indexList.add(21);
		indexList.add(6);
		Collections.sort(indexList);
		for(int index : indexList) {
			System.out.println(index);
		}
	}
	private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
	
	 /***
     * 替换指定文件中的指定内容
     * @param filepath  文件路径
     * @param sourceStr 文件需要替换的内容
     * @param targetStr 替换后的内容
     * @return 替换成功返回true，否则返回false
     */
    public static boolean replaceFileStr(String filepath,String sourceStr,String targetStr){
        try {
        	if(StringUtils.isBlank(targetStr)) {
        		return true;
        	}
            FileReader fis = new FileReader(filepath);  // 创建文件输入流
            BufferedReader br = new BufferedReader(fis);
            char[] data = new char[1024];               // 创建缓冲字符数组
            int rn = 0;
            StringBuilder sb=new StringBuilder();       // 创建字符串构建器
            //fis.read(data)：将字符读入数组。在某个输入可用、发生 I/O 错误或者已到达流的末尾前，此方法一直阻塞。读取的字符数，如果已到达流的末尾，则返回 -1
            while ((rn = fis.read(data)) > 0) {         // 读取文件内容到字符串构建器
                String str=String.valueOf(data,0,rn);//把数组转换成字符串
                //System.out.println(str);
                sb.append(str);
            }
            fis.close();// 关闭输入流
            // 从构建器中生成字符串，并替换搜索文本
            String str = sb.toString().replace(sourceStr, targetStr);
            FileWriter fout = new FileWriter(filepath);// 创建文件输出流
            fout.write(str.toCharArray());// 把替换完成的字符串写入文件内
            fout.close();// 关闭输出流

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } 

    }
    
    private String replateTemplateOption(String json, String templateId, String templateName) {
    	logger.info("option数据: " + json);
    	json = json.trim();
    	String [] array= json.split("\"tempname\"");
		String left = array[0];  
		String right = "";
		if(array.length > 1) {
			right = array[1].substring(array[1].indexOf("\""), array[1].length());
			//获取第一个和第二个"之间的内容，并替换
			logger.info("");
			right.indexOf("\"", 0);
			right.indexOf("\"", 1);
			right = right.replace(right.substring(right.indexOf("\"", 0), right.indexOf("\"", 1) + 1), "");
		}else {
			left = left.substring(0, left.length()-1) + ",";
			right = "}";
		}
		
		String templateIdStr = "\"tempname\"" + ":\"" + templateId + "\"";
		json = left + templateIdStr + right;
		array = json.split("\"trade\"");
		String left2 = array[0];
		
		String right2 = "";
		if(array.length > 1) {
			right2 = array[1].substring(array[1].indexOf("\""), array[1].length());
			right2 = right2.replace(right2.substring(right2.indexOf("\"", 0), right2.indexOf("\"", 1) + 1), "");
			
		}else {
			left2 = left2.substring(0, left2.length()-1) + ",";
			right2 = "}";
		}
		String templateNameStr = "\"trade\": \"" + templateName + "\"";
		return left2 + templateNameStr + right2;
		
    }
    
    /**
     * 对json字符串格式化输出
     * @param jsonStr
     * @return
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        //jsonStr = jsonStr.replace(",", "，");
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                	/*sb.append(current);
	        		if (last == '"' || last == ']' || last == '}') {
	                     sb.append('\n');
	                     addIndentBlank(sb, indent);
	                 }*/
                	sb.append(current);
	                sb.append('\n');
	                addIndentBlank(sb, indent);
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }
    
    

    /**
     * 添加space
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
    
    public static boolean isChinese(char a) {
    	int v = (int)a;
    	return (v >=19968 && v <= 171941);
    }
	
}
