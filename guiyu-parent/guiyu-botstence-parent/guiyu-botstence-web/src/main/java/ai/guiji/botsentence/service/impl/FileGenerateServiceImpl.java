package ai.guiji.botsentence.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import ai.guiji.botsentence.constant.Constant;
import ai.guiji.botsentence.controller.server.vo.BranchNegativeVO;
import ai.guiji.botsentence.controller.server.vo.BranchPositiveVO;
import ai.guiji.botsentence.controller.server.vo.BranchRefuseVO;
import ai.guiji.botsentence.controller.server.vo.BranchSpecialQuestionVO;
import ai.guiji.botsentence.controller.server.vo.DomainVO;
import ai.guiji.botsentence.dao.BotSentenceAdditionMapper;
import ai.guiji.botsentence.dao.BotSentenceBranchMapper;
import ai.guiji.botsentence.dao.BotSentenceDomainMapper;
import ai.guiji.botsentence.dao.BotSentenceIntentMapper;
import ai.guiji.botsentence.dao.BotSentenceLabelMapper;
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsBackupMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsParamMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsTaskMapper;
import ai.guiji.botsentence.dao.VoliceInfoMapper;
import ai.guiji.botsentence.dao.entity.BotSentenceAddition;
import ai.guiji.botsentence.dao.entity.BotSentenceBranch;
import ai.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import ai.guiji.botsentence.dao.entity.BotSentenceDomain;
import ai.guiji.botsentence.dao.entity.BotSentenceDomainExample;
import ai.guiji.botsentence.dao.entity.BotSentenceIntent;
import ai.guiji.botsentence.dao.entity.BotSentenceIntentExample;
import ai.guiji.botsentence.dao.entity.BotSentenceLabel;
import ai.guiji.botsentence.dao.entity.BotSentenceLabelExample;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsBackup;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsBackupExample;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsParam;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsParamExample;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsTask;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsTaskExample;
import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.entity.VoliceInfoExample;
import ai.guiji.botsentence.service.IFileGenerateService;
import ai.guiji.botsentence.service.IVoliceService;
import ai.guiji.botsentence.util.BotSentenceUtil;
import ai.guiji.component.client.util.FileUtil;
import ai.guiji.component.client.util.IOUtil;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * 不适用addition表
 * @Description:
 * @author liyang  
 * @date 2018年9月2日  
 *
 */
@Service
public class FileGenerateServiceImpl implements IFileGenerateService {

	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;

	@Autowired
	private BotSentenceDomainMapper botSentenceDomainMapper;

	@Autowired
	private BotSentenceBranchMapper botSentenceBranchMapper;
	@Autowired
	private VoliceInfoMapper voliceInfoMapper;
	@Autowired
	private BotSentenceIntentMapper botSentenceIntentMapper;
	@Autowired
	private IVoliceService voliceService;
	@Autowired
	private BotSentenceLabelMapper botSentenceLabelMapper;
	@Autowired
	private BotSentenceAdditionMapper botSentenceAdditionMapper;

	@Autowired
	private BotSentenceTtsServiceImpl botSentenceTtsServiceImpl;
	
	@Autowired
	private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;
	
	@Autowired
	private BotSentenceTtsParamMapper botSentenceTtsParamMapper;
	
	@Autowired
	private BotSentenceTtsBackupMapper botSentenceTtsBackupMapper;
	
	@Value("${template.dir}")
	private String tempDir;
	@Value("${export.dir}")
	private String exportDir;

	private static String FILE_SEPARATOR = System.getProperty("file.separator");

	private Logger logger = LoggerFactory.getLogger(FileGenerateServiceImpl.class);

	@Override
	public File fileGenerate(String processId, String dirName) throws IOException {

		// 获取流程对象
		BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(processId);
		//BotSentenceTemplate template = botSentenceTemplateMapper.selectByPrimaryKey(botSentenceProcess.getOldProcessId());
		String templateId = botSentenceProcess.getTemplateId();
		
		//templateId = templateId.split("_")[0];
		
		/*if(templateId.contains("_en")) {
			templateId = templateId.replaceAll("_en", "");
		}*/
		if(templateId.contains("trade_")) {
			templateId = templateId.replaceAll("trade_", "");
		}
		
		//tempDir = tempDir + FILE_SEPARATOR + botSentenceProcess.getAccountNo();
		
		// 模板文件路径
		/***************String tempPath = null;
		if (FileUtil.getFilePath(tempDir, templateId) != null) {
			tempPath = FileUtil.getFilePath(tempDir, templateId);
		} else {
			tempPath = FileUtil.getFilePath2Deep(tempDir, templateId);
		}
		if (tempPath == null) {
			logger.error("can not find template file!! " + templateId);
			return false;
		}*/

		// 拷贝一份模板文件出来
		//String dirName = DateUtil.getCurrentTime2() + "-" + templateId;
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
		
		//String templateCfgsDir = processDir+ FILE_SEPARATOR + "cfgs" + FILE_SEPARATOR + templateId;
		String templateCfgsDir = processDir+ FILE_SEPARATOR + templateId;
		
		try {
			FileUtil.copyDir(tempDir+FILE_SEPARATOR+"copyfiles", templateCfgsDir);
		} catch (IOException e) {
			logger.error("copy file failed !! " + templateId, e);
			return null;
		}
		
		
		// domain的json文件所放的路径
		String domainDir = templateCfgsDir + FILE_SEPARATOR + "new_domain_cfg" + FILE_SEPARATOR;
		String selectDir = templateCfgsDir + FILE_SEPARATOR + "select_cfg" + FILE_SEPARATOR;
		//String wavDir = processDir+ FILE_SEPARATOR + "wav" + FILE_SEPARATOR + templateId +"_rec" + FILE_SEPARATOR;
		String wavDir = processDir+ FILE_SEPARATOR + templateId.replaceAll("_en", "") +"_rec" + FILE_SEPARATOR;
		
		File wavfile = new File(wavDir);
		if(!wavfile.exists()) {
			wavfile.mkdirs();
		}
		
		String statDir = templateCfgsDir + FILE_SEPARATOR + "stat_cfg" + FILE_SEPARATOR;
		
		// 获取录音文件列表
		VoliceInfoExample example = new VoliceInfoExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<VoliceInfo> voliceInfos = voliceInfoMapper.selectByExample(example);

		Map<Long, String> voliceMap = new HashMap<Long, String>();
		
		Map<Integer, String> idsMap = new HashMap<>();
		Map<Long, Integer> voliceIdsMap = new HashMap<>();
		
		int size = voliceInfos.size();
		for (int i = 1; i <= size; i++) {
			voliceIdsMap.put(voliceInfos.get(i - 1).getVoliceId(), i);
			idsMap.put(i, voliceInfos.get(i - 1).getContent());
			String content = voliceInfos.get(i - 1).getContent() + "*" + i;
			voliceMap.put(voliceInfos.get(i - 1).getVoliceId(), content);
		}

		// 获取所有domain
		BotSentenceDomainExample botSentenceDomainExample = new BotSentenceDomainExample();
		botSentenceDomainExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceDomain> domains = botSentenceDomainMapper.selectByExample(botSentenceDomainExample);

		// 获取所有的branch
		BotSentenceBranchExample botSentenceBranchExample = new BotSentenceBranchExample();
		botSentenceBranchExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceBranch> botSentenceBranchs = botSentenceBranchMapper.selectByExample(botSentenceBranchExample);

		// 对所有domain进行遍历
		for (BotSentenceDomain botSentenceDomain : domains) {

			String domainName = botSentenceDomain.getDomainName();
			DomainVO domainVO = new DomainVO();

			List<String> refuseResponse = new ArrayList<>();
			BranchRefuseVO refuseBranchNodeVO = new BranchRefuseVO();
			
			// 设置branch
			List<Map> branchShow = new ArrayList<Map>();
			List<String> allKeywords = new ArrayList<>();
			// 对所有的brach进行遍历
			for (BotSentenceBranch botSentenceBranch : botSentenceBranchs) {

				String branchDomain = botSentenceBranch.getDomain();
				if (branchDomain.equals(domainName)) {

					String branchName = botSentenceBranch.getBranchName();
					Map map = new HashMap();

					if (branchName.equals("enter_branch")){
						domainVO.setEnter(getVolies(botSentenceBranch.getResponse(), voliceMap));
					}else if(branchName.equals("failed_enter_branch")) {
						domainVO.setFailed_enter(getVolies(botSentenceBranch.getResponse(), voliceMap));
					} else {
						if (branchName.equals("positive")) {
							BranchPositiveVO BranchNodeVO = new BranchPositiveVO();
							BranchNodeVO.setNext(botSentenceBranch.getNext());
							List<String> keywords = getIntentKeys(botSentenceBranch.getIntents());
							BranchNodeVO.setKeys(keywords);
							map.put(branchName, BranchNodeVO);
							allKeywords.addAll(keywords);
							branchShow.add(map);
						} else if (branchDomain.equals("一般问题") && branchName.startsWith("special")) {
							BranchSpecialQuestionVO BranchNodeVO = new BranchSpecialQuestionVO();
							BranchNodeVO.setNext(botSentenceBranch.getNext());
							List<String> keywords = getIntentKeys(botSentenceBranch.getIntents());
							BranchNodeVO.setKeys(keywords);
							BranchNodeVO.setEnd(botSentenceBranch.getEnd());
							BranchNodeVO.setUser_ask(botSentenceBranch.getUserAsk());
							BranchNodeVO.setResponse(getVolies(botSentenceBranch.getResponse(), voliceMap));
							map.put(branchName, BranchNodeVO);
							branchShow.add(map);
							allKeywords.addAll(keywords);
						} else if(branchName.startsWith("refuse_")){
							
							refuseResponse.addAll(getVolies(botSentenceBranch.getResponse(), voliceMap));
							/*refuseBranchNodeVO.setNext(botSentenceBranch.getNext());
							refuseBranchNodeVO.setKeys(getIntentKeys(botSentenceBranch.getIntents()));
							refuseBranchNodeVO.setEnd(botSentenceBranch.getEnd());
							refuseBranchNodeVO.setIs_special_limit_free(true);
							map.put(branchName, refuseBranchNodeVO);
							branchShow.add(map);*/
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
			
			
			//处理挽回话术(除结束之外的每个主domain都必须有)
			if((Constant.CATEGORY_TYPE_1.equals(botSentenceDomain.getCategory()) && !Constant.DOMAIN_TYPE_END.equals(botSentenceDomain.getType()))
					|| "解释开场白".equals(botSentenceDomain.getDomainName())){
				BotSentenceBranchExample intentExample = new BotSentenceBranchExample();
				
				intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("拒绝").andBranchNameEqualTo("negative");
				List<BotSentenceBranch> intentList = botSentenceBranchMapper.selectByExample(intentExample);
				List<String> keywords = getIntentKeys(intentList.get(0).getIntents());
				
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
				for(String temp : keywords) {
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
				for(String temp : allRefuseKeyWords) {
					temp = temp.replace("\"", "");
					newRefuseKeyWords.add(temp);
				}
				
				Map map = new HashMap();
				refuseBranchNodeVO.setNext(domainName);
				refuseBranchNodeVO.setKeys(newRefuseKeyWords);
				refuseBranchNodeVO.setEnd("结束");
				refuseBranchNodeVO.setIs_special_limit_free(true);
				refuseBranchNodeVO.setResponse(refuseResponse);
				map.put("refuse_" + domainName, refuseBranchNodeVO);
				branchShow.add(map);
			}
			
			domainVO.setBranch(branchShow);

			domainVO.setLimit(1);
			// 设置com_domain
			String comDomian = botSentenceDomain.getComDomain();
			if (!StringUtils.isBlank(comDomian)) {
				domainVO.setCom_domain(comDomian);
			}
			// 设置ignore_but_domains
			boolean ignore_user_sentence = false;
			boolean ignore_but_negative = false;
			boolean ignore = false;
			List<String> ignoreList = new ArrayList<String>();
			
			String ignoreButDomains = botSentenceDomain.getIgnoreButDomains();
			if (!StringUtils.isBlank(ignoreButDomains)) {
				String[] butDomainsArr = ignoreButDomains.replace("\"", "").replace("[", "").replace("]", "").split(",");
				List<String> butDomainList = new ArrayList<String>();
				for(String butDomain:butDomainsArr) {
					if(butDomain.startsWith("ignore_")) {
						ignore= true;
						String ignoreDomain = butDomain.replace("ignore_", "");
						if(ignoreDomain.length()>0) {
							ignoreList.add(ignoreDomain);
						}
						
					}else if(butDomain.equals("user_sentence_ignored")) {
						ignore_user_sentence =true;
					}else if(butDomain.equals("user_sentence_but_negative_ignored")) {
						ignore_but_negative = true;
					}else {
						butDomainList.add(butDomain);
					}
				}
				domainVO.setIgnore_but_domains(butDomainList);
			}
			
			String jsonstr = JSON.toJSONString(domainVO);
			JSONObject jsonObject = JSON.parseObject(jsonstr);
			
			if(ignore_user_sentence) {
				jsonObject.put("ignore_user_sentence", true);
			}
			if(ignore_but_negative) {
				jsonObject.put("ignore_but_negative", true);
			}
			if(ignore) {
				jsonObject.put("ignore", ignoreList);
			}

			if (domainName.equals("拒绝")) {
				jsonObject.remove("com_domain");
			} else {
				jsonObject.remove("limit");
				if (domainName.equals("投诉")) {
					jsonObject.remove("ignore_but_domains");
				} else if (domainName.equals("不清楚") || domainName.equals("号码过滤") || domainName.equals("在忙")
						|| domainName.equals("未匹配响应") || domainName.equals("结束") || domainName.equals("结束_在忙")
						|| domainName.equals("结束_未匹配")) {
					jsonObject.remove("com_domain");
					jsonObject.remove("ignore_but_domains");
				} else if (domainName.equals("不知道") || domainName.equals("强制结束")) {
					jsonObject.remove("com_domain");
					jsonObject.remove("ignore_but_domains");
					jsonObject.remove("branch");
				} else if (domainName.equals("等待") || domainName.equals("出错")) {
					jsonObject.remove("com_domain");
					jsonObject.remove("ignore_but_domains");
					jsonObject.remove("branch");
					jsonObject.remove("failed_enter");
				}
			}

			String domainJson = "{\"" + domainName + "\":" + jsonObject.toJSONString().replace("[{", "{")
					.replace("}},{", "},").replace("}]", "}").replace("\"branch\":[]", "\"branch\":{}").replace("\\", "")
					.replace("[\"\"]", "[]").replace("[\"\"", "[\"").replace("\"\"]", "\"]").replace("]\"]", "]]").replace("[\"[\"", "[[\"")+ "}";
			try {
				FileUtil.writeFile(domainDir + domainName + ".json", formatJson(domainJson));
			} catch (IOException e) {
				logger.error("write domain json file failed : " + domainDir + domainName + ".json", e);
				return null;
			}
		}

		// 生成select.json
		Map<String, String> selectMap = new HashMap<String, String>();
		BotSentenceIntentExample exampleIntent = new BotSentenceIntentExample();
		exampleIntent.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceIntent> listIntent = botSentenceIntentMapper.selectByExampleWithBLOBs(exampleIntent);
		for (BotSentenceIntent botSentenceIntent : listIntent) {
			if (botSentenceIntent.getForSelect() == 1) {
				String keys = botSentenceIntent.getKeywords();
				String[] arr = botSentenceIntent.getName().split("_");
				String domain = arr[2];
				selectMap.put(domain, keys);
			}
		}
		String selectJson = JSON.toJSONString(selectMap).replace("\"[", "[").replace("]\"", "]").replace("\\", "");
		try {
			FileUtil.writeFile(selectDir + "select.json", formatJson(selectJson));
		} catch (IOException e) {
			logger.error("write select json file failed : " + selectDir + "select.json", e);
			return null;
		}
		
		
		//生成打分相关文件stat_cfg
		//获取A/B/C/D对应的关键字
		BotSentenceLabelExample labelExample = new BotSentenceLabelExample();
		List<String> labelNameList = new ArrayList<>();
		labelNameList.add("A");
		labelNameList.add("B");
		labelNameList.add("C");
		labelNameList.add("D");
		labelNameList.add("E");
		
		Map<String, Object> statsMap = new HashMap<>();
		
		labelExample.createCriteria().andProcessIdEqualTo(processId).andLabelNameIn(labelNameList);
		List<BotSentenceLabel> labelList = botSentenceLabelMapper.selectByExample(labelExample);
		if(null != labelList && labelList.size() > 0) {
			for(BotSentenceLabel label : labelList) {
				Map<String, Object> labelMap = new HashMap<>();
				labelMap.put("keywords", label.getKeywords());
				statsMap.put(label.getLabelName(), labelMap);
				
				if("A".equals(label.getLabelName())) {
					//生成rule_A关键词.json
					String keywords = label.getKeywords();
					if(StringUtils.isNotBlank(label.getDisplayKeywords())) {
						keywords = label.getDisplayKeywords();
					}
					replaceFileStr(statDir+"rule_A关键词.json", "keywords_param", keywords);
					//stat.json
					replaceFileStr(statDir+"stat.json", "A_score_down", label.getScoreLow().toString());
				}else if("B".equals(label.getLabelName())) {
					//生成rule_B关键词.json
					String keywords = label.getKeywords();
					if(StringUtils.isNotBlank(label.getDisplayKeywords())) {
						keywords = label.getDisplayKeywords();
					}
					replaceFileStr(statDir+"rule_B关键词.json", "keywords_param", keywords);
					replaceFileStr(statDir+"stat.json", "B_score_up", label.getScoreUp().toString());
					replaceFileStr(statDir+"stat.json", "B_score_down", label.getScoreLow().toString());
				}else if("C".equals(label.getLabelName())) {
					//生成rule_C关键词.json
					String keywords = label.getKeywords();
					if(StringUtils.isNotBlank(label.getDisplayKeywords())) {
						keywords = label.getDisplayKeywords();
					}
					replaceFileStr(statDir+"rule_C关键词.json", "keywords_param", keywords);
					replaceFileStr(statDir+"stat.json", "C_score_up", label.getScoreUp().toString());
					replaceFileStr(statDir+"stat.json", "C_score_down", label.getScoreLow().toString());
				}else if("D".equals(label.getLabelName())) {
					//生成rule_D关键词.json
					String keywords = label.getKeywords();
					if(StringUtils.isNotBlank(label.getDisplayKeywords())) {
						keywords = label.getDisplayKeywords();
					}
					replaceFileStr(statDir+"rule_D关键词.json", "keywords_param", keywords);
					replaceFileStr(statDir+"stat.json", "D_score_up", label.getScoreUp().toString());
				}else if("E".equals(label.getLabelName())) {
					//生成rule_D关键词.json
					String keywords = label.getKeywords();
					if(StringUtils.isNotBlank(label.getDisplayKeywords())) {
						keywords = label.getDisplayKeywords();
					}
					replaceFileStr(statDir+"rule_E关键词.json", "keywords_param", keywords);
					replaceFileStr(statDir+"stat.json", "E_score", label.getScoreLow().toString());
				}
			}
			
            String stats2Json = JSON.toJSONString(statsMap).replace("\"[", "[").replace("]\"", "]").replace("\\", "");
    		try {
    			FileUtil.writeFile(statDir + "stat2.json", formatJson(stats2Json));
    		} catch (IOException e) {
    			logger.error("write select json file failed : " + selectDir + "stats2.json", e);
    			return null;
    		}
		}
		
		

		// 下载音频文件
		RestTemplate restTemplate = new RestTemplate();
		for (int i = 1; i <= size; i++) {
			String wavURL = voliceInfos.get(i - 1).getVoliceUrl();
			try {
				if (StringUtils.isNotBlank(wavURL)) {
					generateWAV(restTemplate, wavURL, Integer.toString(i), wavfile.getPath());
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
		List<Integer> ids = new ArrayList<>();
		
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
		String optionJson = botSentenceAddition.getOptionsJson();
		
		//替换optionjson里面的模板名称和编号
		optionJson = replateTemplateOption(optionJson, templateId, botSentenceProcess.getIndustry());
		
		String optionPath = templateCfgsDir+ FILE_SEPARATOR + "options.json";
		try {
			FileUtil.writeFile(optionPath, formatJson(optionJson));
		} catch (IOException e) {
			logger.error("generate options.json has exception:", e);
			return null;
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
		
		/*String temlateJson = botSentenceAddition.getTemplateJson();
		if(StringUtils.isNotBlank(temlateJson)) {
			String temlatePath = templateCfgsDir + FILE_SEPARATOR + "template" + FILE_SEPARATOR + "template.json";
			try {
				FileUtil.writeFile(temlatePath, temlateJson);
			} catch (IOException e) {
				logger.error("generate stopwords.txt has exception:", e);
				return null;
			}
		}*/
		
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
		
		String weightsTxt = botSentenceAddition.getWeightsTxt();
		String weightsPath = templateCfgsDir + FILE_SEPARATOR + "weights_cfg" + FILE_SEPARATOR + "weights.txt";
		try {
			FileUtil.writeFile(weightsPath, weightsTxt);
		} catch (IOException e) {
			logger.error("generate weights.txt has exception:", e);
			return null;
		}
		
		
		logger.info("判断是否需要TTS");
		BotSentenceTtsTaskExample ttsParamExample = new BotSentenceTtsTaskExample();
		ttsParamExample.createCriteria().andProcessIdEqualTo(processId);
		int num = botSentenceTtsTaskMapper.countByExample(ttsParamExample);
		if(num > 0) {
			logger.info("当前话术需要TTS");
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
			Map<String, List<String>> map1 = new LinkedHashMap<>();
			Map<String, String> tts_pos_map1 = new LinkedHashMap<>();
			Map<String, String> tts_pos_map2 = new LinkedHashMap<>();
			
			List<String> allParamList  = new ArrayList<>();
			
			for(VoliceInfo temp : ttsVoliceList) {
				
				if(StringUtils.isNotBlank(temp.getContent())) {
				   List<String> wavNameList = new ArrayList<>();
				   
				   BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
					ttsTaskExample.createCriteria().andProcessIdEqualTo(processId)
							.andBusiIdEqualTo(temp.getVoliceId().toString()).andBusiTypeEqualTo(Constant.TTS_BUSI_TYPE_01);
					ttsTaskExample.setOrderByClause(" seq");
					List<BotSentenceTtsTask> taskList = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);
					if (null != taskList && taskList.size() > 0) {
						for (BotSentenceTtsTask task : taskList) {
							String seq = task.getSeq();
							//使用序号替换voliceId
							int index = voliceIdsMap.get(new Long(task.getBusiId()));
							String wavName = index + "_" + seq.split("_")[1];
							wavNameList.add(wavName);
							
							if(Constant.IS_PARAM_FALSE.equals(task.getIsParam())) {
								if(StringUtils.isNotBlank(task.getVoliceUrl())) {
									generateWAV(restTemplate, task.getVoliceUrl(), wavName, wavfile.getPath());
								}
							}
							
							if(Constant.IS_PARAM_TRUE.equals(task.getIsParam())) {
								tts_pos_map1.put(wavName, task.getContent());
								allParamList.add(task.getContent());
							}
						}
					}
				   
					map1.put(voliceIdsMap.get(temp.getVoliceId()).toString(), wavNameList);
				}
			}
			
			replaceMap.put("rec_tts_wav", map1);
			
			
			//生成tts_pos
			replaceMap.put("tts_pos", tts_pos_map1);
			
			
			//生成replace_variables_flag
			Collections.sort(allParamList);
			replaceMap.put("replace_variables_flag", allParamList);
			
			//生成replace_variables_type
			List<String> replace_variables_type_list = new ArrayList<>();
			for(String param : allParamList) {
				BotSentenceTtsParamExample ttsParamexample = new BotSentenceTtsParamExample();
				ttsParamexample.createCriteria().andProcessIdEqualTo(processId).andParamKeyEqualTo(param);
				List<BotSentenceTtsParam> ttsParamList = botSentenceTtsParamMapper.selectByExample(ttsParamexample);
				if(null != ttsParamList && ttsParamList.size() > 0) {
					if(StringUtils.isNotBlank(ttsParamList.get(0).getParamType())) {
						replace_variables_type_list.add(ttsParamList.get(0).getParamType());
					}else {
						replace_variables_type_list.add("normal");
					}
					
				}else {
					//throw new CommonException("变量"+param+"没有设置类型");
					replace_variables_type_list.add("normal");
				}
			}
			replaceMap.put("replace_variables_type", replace_variables_type_list);
			
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
				}
			}
			
			
			//生成完整话术
			for(VoliceInfo temp : ttsVoliceList) {
				int index = voliceIdsMap.get(temp.getVoliceId());
				String content = temp.getContent();
				
				 String regEx = "\\$[0-9]{4}";
			    // 编译正则表达式
			    Pattern pattern = Pattern.compile(regEx);
			    Matcher matcher = pattern.matcher(content);
			    List<String> paramList = new ArrayList<>();
			    // 字符串是否与正则表达式相匹配
			    while(matcher.find()) {
			    	 String match = matcher.group();
			    	 paramList.add(match);
			    }
			    
			    for(String str : paramList) {
			    	if(tts_pos_map2.containsKey(str)) {
			    		content = content.replace(str, "$"+tts_pos_map2.get(str));
			    	}
			    }
				
				replaceMap.put(index + "_replace_sentence", content);
			}
			
			
			String replacePath = templateCfgsDir+ FILE_SEPARATOR + "replace.json";
			String replaceJson = JSON.toJSONString(replaceMap).replace("\"[", "[").replace("]\"", "]").replace("\\", "");
			try {
				FileUtil.writeFile(replacePath, formatJson(replaceJson));
			} catch (IOException e) {
				logger.error("write replace json file failed : " + selectDir + "replace.json", e);
				return null;
			}
		}else{
			logger.info("当前话术不需要TTS");
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
	
	
	public boolean autoDeploy(File dir, String dirName, String processId, String templateId) {
		return voliceService.uploadVoliceJsonZip(dir, dirName,processId,templateId);
	}
	

	private void writeFile(InputStream in,File file){
		OutputStream out=null;
		try {
			out=new FileOutputStream(file);
			byte[] b=new byte[1024];
			int index=-1;
			while((index=in.read(b))!=-1){
				out.write(b, 0, index);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			IOUtil.close(out);
			IOUtil.close(in);
		}
	}
	
	/**
	 * 下载音频文件
	 */
	public void generateWAV(RestTemplate template, String url,String name,String dir) throws IOException {
		logger.info("downloading...." + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Resource> httpEntity = new HttpEntity<Resource>(headers);
		ResponseEntity<byte[]> response = template.exchange(url, HttpMethod.GET, httpEntity, byte[].class);
		logger.info(">>>>> return code:" + response.getStatusCodeValue());
		File file = new File(dir + FILE_SEPARATOR + name + ".wav");
		
		FileOutputStream fos1 = new FileOutputStream(file);
		fos1.write(response.getBody());
		fos1.flush();
		fos1.close();

		
//		File wavFile = new File(dir + FILE_SEPARATOR + "temp2_" + name + ".wav");
//		boolean flag = AudioConvertUtil.converWav(file.getPath(), wavFile.getPath());
//		if(!flag) {
//			throw new CommonException("转换wav失败"+file.getName());
//		}
//		
//		if(file.delete()) {
//			logger.info("删除临时文件"+file.getName()+"成功...");
//		}
//		
//		
//		//去掉音频信息头
//		File finnalFile = new File(dir + FILE_SEPARATOR + name + ".wav");
//		boolean flag2 = AudioConvertUtil.removeHeadInfo(wavFile.getPath(), finnalFile.getPath());
//		if(!flag2) {
//			throw new CommonException("清空头信息失败"+wavFile.getName());
//		}
//		if(wavFile.delete()) {
//			logger.info("删除临时文件"+wavFile.getName()+"成功...");
//		}
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
						enterList.add(voliceMap.get(Long.parseLong(voliceId)));
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

		List<String> enterList = new ArrayList<String>();
		String res = "";

		if (!StringUtils.isBlank(ids)) {

			if (ids.contains("[")) {
				ids = ids.replace("[", "");
				ids = ids.replace("]", "").trim();
			}
			if (!StringUtils.isBlank(ids)) {
				String[] enterArr = ids.split(",");
				for (String id : enterArr) {
					BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(Long.parseLong(id));
					if (intent != null) {
						String keys = intent.getKeywords();
						if(keys.startsWith("[")) {
							keys = keys.substring(1, keys.length()-1);
//							enterList.add(keys);
						}
						res = res + keys +"," ;
					}
				}
			}
		}
		
		if(res.endsWith(",")){
			res=res.substring(0, res.length()-1);
		}
		enterList.add(res);
		return enterList;
	}
	
	public static void main(String[] args) {
	//	String keys = "{\"一般问题\":{\"branch\":{\"special_20\":{\"next\":\"一般问题\",\"keys\":[\"没信用卡\",\"没用信用卡\",[\"不会做*!\",\"信用卡\"]],\"response\":[\"没有关系的，如果您想申请信用卡，我们可以帮您介绍各大银行的专员为您免费办理信用卡的。*18\"],\"user_ask\":\"没有信用卡，没用信用卡\",\"end\":\"邀约\"},\"special_10\":{\"next\":\"一般问题\",\"keys\":[\"机器安全\",\"资金安全\",\"使用安全\",\"刷卡安全\",\"保证\",[\"机器*!\",\"安全\"],[\"破手机*!\",\"安全\"],[\"手机*!\",\"安全*!\"]],\"response\":[\"我们的产品是有央行颁发支付牌照的，在中国人民银行官网可以查询到的，机器有银联标识，可以放心使用，资金绝对是安全的。*29\"],\"user_ask\":\"机器安全怎么样？手机安全如何？\",\"end\":\"邀约\"},\"special_21\":{\"next\":\"一般问题\",\"keys\":[\"邮寄\",\"邮费\",[\"邮费*!\",\"多少钱\"],[\"邮费*!\",\"怎么算\"]],\"response\":[\"我们是顺丰发货的，一般运费在12到18元。您在签收时只需要将运费付给快递小哥就好了。*19\"],\"user_ask\":\"邮费多少？邮费怎么算\",\"end\":\"邀约\"},\"special_11\":{\"next\":\"一般问题\",\"keys\":[\"拿款\",\"取钱\",\"怎么放款\"],\"response\":[\"机器给您以后，您自己注册的，钱直接到您注册绑定的储蓄卡里面的。*11\"],\"user_ask\":\"我怎么拿款？怎么放款？\",\"end\":\"邀约\"},\"special_22\":{\"next\":\"一般问题\",\"keys\":[\"为什么要告诉你\",\"为什么找你们\",\"优势\",\"手刷的机器\",\"有什么用\",[\"为什么*!\",\"找你们\",\"跟你说\"],\"机器多大\",\"机器功能\",\"易携带\",[\"机器\",\"易携带*!\"],[\"机器\",\"优势*!\"]],\"response\":[\"我们的是最新版的友刷POS机，可以支持刷卡，插卡，闪付，云闪付，二维码扫码等支付方式。机器开启自动蓝牙连接手机APP，使用非常方便；而且机器小巧方便携带。*20\"],\"user_ask\":\"为什么要选你们，机器功能有哪些\",\"end\":\"邀约\"},\"special_23\":{\"next\":\"邀约_成功\",\"keys\":[\"代理商\",\"加盟条件\",\"怎么加盟\",\"怎么代理\",[\"代理商*!\",\"需要什么条件\",\"需要提供什么材料\",\"需要提供什么东西\"],[\"代理商*!\",\"要求\"]],\"response\":[],\"user_ask\":\"怎么加盟是什么？需要提供什么材料吗？\",\"end\":\"邀约_成功\"},\"special_18\":{\"next\":\"一般问题\",\"keys\":[\"费率多少\",\"刷卡费率\",\"费率\",\"手续费\",[\"刷卡*!\",\"收费\"],[\"刷卡*!\",\"价格\",\"多贵\"],[\"刷卡*!\",\"手续费\"]],\"response\":[\"是这样的，我们机器的刷卡费率最低是0.55，意思就是您刷一万元，只收取55元费用，云闪付，二维码费率只要0.38，这是目前市场上最低的费率了。您要是方便的话我现在给您发条短信，里面有我们产品的链接，您直接在链接里面填写收货地址，我们会有专人给您邮寄过去。*15\"],\"user_ask\":\"刷卡费率多少？手续费多少\",\"end\":\"邀约\"},\"special_19\":{\"next\":\"一般问题\",\"keys\":[\"乱收费\",\"加急费\",\"其它收费\",\"多久拿款\",[\"加急费*!\",\"多少钱\",\"大概多少钱\"],\"加急\",\"到账时间\",[\"到账时间*!\",\"多长时间\"],[\"加急*!\",\"收费\",\"多少钱\",\"大概多少钱\"],[\"取钱\",\"多长时间*!\"]],\"response\":[\"是这样的，您可以自行选择秒到还是次日到帐，秒到的话每笔仅收取3元的加急费，次日到账是不收取加急费的。*16\"],\"user_ask\":\"会不会乱收费？加急费有多少？\",\"end\":\"邀约\"},\"positive\":{\"next\":\"恢复\",\"keys\":[\"你说\",\"我叫\",\"知道\",\"介绍下\",\"好的\",\"不错\",\"介绍\",\"可以的\",\"感兴趣\",\"挺好\",\"有的\",\"是的\",\"行的\",\"说得具体点\"]},\"special_13\":{\"next\":\"一般问题\",\"keys\":[\"什么品牌\",\"机器品牌\",\"破手机\",[\"破手机\",\"什么品牌*!\"],[\"机器*!\",\"你叫什么\"],[\"机器\",\"什么品牌*!\"]],\"response\":[\"我们的机器品牌叫友刷，是卡友支付平台的，有央行颁发的支付牌照。*12\"],\"user_ask\":\"机器都有什么品牌？有什么品牌？\",\"end\":\"邀约\"},\"special_6\":{\"next\":\"一般问题\",\"keys\":[\"做了几年\",\"多长时间\",\"太不专业\",\"直接介绍\",\"直接说\",[\"就要*!\",\"给我介绍\"],[\"就要*!\",\"跟你说\"],[\"不能*!\",\"给我介绍\"],[\"为什么*!\",\"给我介绍\",\"业务经理\"]],\"response\":[\"老板，我刚做不久，一年多一点，以后还要多多像您学习呢。*33\"],\"user_ask\":\"你太不专业了，你做了几年了？\",\"end\":\"邀约\"},\"special_14\":{\"next\":\"一般问题\",\"keys\":[\"怎么办理\",\"怎么参加\",\"领取方式\",\"哪里领取\",\"机器怎么给我\",[\"破手机\",\"怎么给我*!\"],[\"机器\",\"邮寄*!\"]],\"response\":[\"稍后我会给您发一条短信，里面有我们产品的链接，您直接在链接里面下单，我们会有专人给您邮寄过去。或者您也可以直接来我们公司直接领取。*13\"],\"user_ask\":\"怎么参加？机器邮寄给我吗\",\"end\":\"邀约\"},\"special_7\":{\"next\":\"一般问题\",\"keys\":[\"机器人\",\"录音\"],\"response\":[\"这个不重要吧，给您打电话也是想要了解一下您是否需要POS机，有需要的话我们这里可以随时提供最优质的服务的。您看需要了解一下吗？*34\"],\"user_ask\":\"你这个是录音吗，你是机器人\",\"end\":\"邀约\"},\"special_15\":{\"next\":\"一般问题\",\"keys\":[\"免费\",\"多少费用\",\"收费方式\",\"付款\",\"价格\",\"价格多少\",\"大概多少钱\",\"费用问题\",\"大概收多少\",\"多贵\",[\"机器\",\"免费*!\"],[\"机器\",\"多少钱*!\"],[\"机器\",\"收费*!\"],[\"机器\",\"价格*!\"]],\"response\":[\"我们的机器价值150元，是免费送您的，邮费是到付的，大概12-18元。你只需要付一下邮费就可以了。*10\"],\"user_ask\":\"大概多少钱？价格多少？\",\"end\":\"邀约\"},\"special_8\":{\"next\":\"一般问题\",\"keys\":[\"从哪知道信息\",\"我电话\",\"我资料\",\"怎么拿到\",\"哪里弄得\",[\"哪里弄得*!\",\"我电话\"],[\"怎么拿到*!\",\"我资料\"],[\"哪位*!\",\"我电话\",\"我资料\"],[\"哪个\",\"我电话*!\"],[\"哪个\",\"我资料*!\"],[\"我资料*!\",\"哪里\"]],\"response\":[\"是这样的，我们这边是随机拨号的，如果打扰到您，请您见谅。*35\"],\"user_ask\":\"从哪知道信息？怎么拿到我资料的？\",\"end\":\"邀约\"},\"special_16\":{\"next\":\"一般问题\",\"keys\":[\"活动内容\",\"怎么返现\"],\"response\":[\"是这样的，公司现在做活动，机器您拿到后只要刷满150元即可开通，90天内，累计刷满10万元立返159元，累计刷满50万元再返259元，累计刷满100万元再返359元。返现是累计相加的。直接返到您绑定的银行卡里。*14\"],\"user_ask\":\"活动内容是什么？怎么返现？\",\"end\":\"邀约\"},\"special_9\":{\"next\":\"一般问题\",\"keys\":[\"发我手机上\",\"找不到微信\",\"加下微信\",\"发微信给我\",\"怎么联系\",\"你的联系方式\",\"号码多少\",\"发送\",\"发资料\",[\"发送*!\",\"我电话\"],[\"发送*!\",\"手机\"],[\"发资料*!\",\"手机\"],[\"发送*!\",\"公司地址\"]],\"response\":[\"好的，稍后我给您发条短信，里面有我们产品的链接和产品简介，您直接在链接里面下单，我们会有专人给您邮寄过去，您只需要承担邮费12-18元即可。*36\"],\"user_ask\":\"发我手机上，怎么联系你\",\"end\":\"邀约\"},\"special_2\":{\"next\":\"一般问题\",\"keys\":[\"开车怎么去\",\"怎么走\",\"哪边\",\"地址\",\"在哪\",\"公司位置\",\"在什么旁边\",\"什么位置\",[\"介绍公司*!\",\"公司位置\",\"什么位置\",\"地址\"]],\"response\":[\"我们公司在无锡市滨湖区梁清路与孙蒋路交口处华邸国际大厦A座2503室，在地铁2号线大王基站向南200米，您要是方便可以来我们公司现场领取，我们很欢迎您的。*17\"],\"user_ask\":\"公司位置在哪里？公司在什么位置\",\"end\":\"邀约\"},\"special_3\":{\"next\":\"一般问题\",\"keys\":[\"骗人\",\"靠谱\",\"合法\",\"跑路\",\"皮包公司\",\"诈骗电话\",\"信任你们\",\"泄露\",\"不放心\",[\"不会是\",\"诈骗电话\"],[\"泄露*!\",\"我信息\"],[\"骗人*!\",\"怎么办\"]],\"response\":[\"我们是卡友支付平台，亚洲第一的POS机硬件提供商，有支付牌照，这个您是可以放心的,您也可以在网上查询。您要是不放心可以来我们公司现场办理。*30\"],\"user_ask\":\"你们是骗人的吧？骗人怎么办？\",\"end\":\"邀约\"},\"negative\":{\"next\":\"结束\",\"keys\":[],\"response\":[],\"end\":\"结束\"},\"special_4\":{\"next\":\"一般问题\",\"keys\":[\"哪位\",\"哪个\",\"你叫什么\"],\"response\":[\"我是中禹集团的客户代表，您叫我娜娜就可以了。*31\"],\"user_ask\":\"你叫什么？你是哪位啊\",\"end\":\"邀约\"},\"special_5\":{\"next\":\"一般问题\",\"keys\":[\"年龄\"],\"response\":[\"老板，我是标准的90后*32\"],\"user_ask\":\"你多大了\",\"end\":\"邀约\"},\"special_1\":{\"next\":\"一般问题\",\"keys\":[\"哪里\",\"你哪的\",\"机构\",\"什么事\",\"介绍公司\",\"你是哪里\",\"哪个单位\",\"什么企业\",\"什么机构\",\"什么集团\",\"什么公司\",\"什么平台\",[\"平台*!\",\"你叫什么*!\"],[\"哪个\",\"公司*!\"],[\"公司*!\",\"介绍下*!\"],[\"公司*!\",\"你叫什么*!\"],\"没听过你们公司\"],\"response\":[\"我们是中禹集团无锡直营分公司，我们的产品叫友刷，您可以上网搜索一下，口碑和质量都是有保证的。*28\"],\"user_ask\":\"你们是什么公司啊？介绍下你们公司\",\"end\":\"邀约\"}}}}";
		//System.out.println(keys);
		/*String [] array= keys.split("\"tempname\": \"");
		String left = array[0];
		String right = array[1].substring(array[1].indexOf("\","), array[1].length());
		String templateId = "   \"tempname\": \"" + "张朋";
		System.out.println(left + templateId + right);
		keys = left + templateId + right;
		array = keys.split("\"trade\": \"");
		String left2 = array[0];
		String right2 = array[1].substring(array[1].indexOf("\","), array[1].length());
		
		String templateName = "  \"trade\": \"" + "测试模板" ;
		
		System.out.println(left2 + templateName + right2);*/
		//String right2 = array[1].split("");
		String keys = "那打扰您了af121,祝您生活愉快，再见！*48";
		//keys = keys.replace(",", "，");
		System.out.println(formatJson(keys));
		
		
		//生成replace.json
		Map<String, Object> replaceMap = new LinkedHashMap<String, Object>();
		replaceMap.put("template_tts_flag", true);
		replaceMap.put("tts_new", true);
		replaceMap.put("tts_partial", true);
		replaceMap.put("use_speaker_flag", "5");
		
		Integer[] wavs = new Integer[5];
		wavs[0] = 1;
		wavs[1] = 2;
		wavs[2] = 9;
		wavs[3] = 12;
		wavs[4] = 25;
		replaceMap.put("num_sentence_merge_lst", wavs);
		
		
		replaceMap.put("old_num_sentence_merge_lst", wavs);
		
		Map<String, String[]> map1 = new HashMap<>();
		String[] map1array1 = new String[2];
		map1array1[0] = "1_1";
		map1array1[1] = "1_2";
		map1.put("1", map1array1);
		replaceMap.put("rec_tts_wav", map1);
		
		
		Map<String, String> tts_pos_map1 = new HashMap<>();
		tts_pos_map1.put("1_2", "请问是$0000吗");
		tts_pos_map1.put("2_2", "请您立即处理欠款$1111");
		tts_pos_map1.put("21_2", "$1111");
		replaceMap.put("tts_pos", tts_pos_map1);
		
		
		List<String> replace_variables_flag_list = new ArrayList<>();
		replace_variables_flag_list.add("$0000");
		replace_variables_flag_list.add("$1111");
		replace_variables_flag_list.add("$3333");
		replaceMap.put("replace_variables_flag", replace_variables_flag_list);
		
		List<String> replace_variables_type_list = new ArrayList<>();
		replace_variables_type_list.add("name");
		replace_variables_type_list.add("money");
		replace_variables_type_list.add("normal");
		replaceMap.put("replace_variables_type", replace_variables_type_list);
		
		
		replaceMap.put("1_backup", "您好，请问是是本人吗");
		replaceMap.put("2_backup", "这边是360借条贷后管理部，您的贷款已逾期，为了不影响您的信用,请您立即处理欠款");
		
		replaceMap.put("1_replace_sentence", "您好，$1_2");
		replaceMap.put("2_replace_sentence", "这边是360借条贷后管理部，您的贷款已逾期，为了不影响您的信用,请您立即处理欠款$2_2");
		
		String selectJson = JSON.toJSONString(replaceMap).replace("\"[", "[").replace("]\"", "]").replace("\\", "");
		System.out.println(selectJson);
		
		//replaceFileStr("D:\\git-doc\\04-应用开发\\02-详细设计\\template\\代运营\\xzdyy\\cfgs\\xzdyy\\stat_cfg\\rule_A关键词.json", "keywords_param", "[张三]");
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
                	 sb.append(current);
                	 
	        		// if (last != '\\') {
	        		if (last == '"' || last == ']' || last == '}') {
	                     sb.append('\n');
	                     addIndentBlank(sb, indent);
	                 }
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
