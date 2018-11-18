package ai.guiji.botsentence.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import ai.guiji.botsentence.controller.server.vo.BranchNegativeVO;
import ai.guiji.botsentence.controller.server.vo.BranchPositiveVO;
import ai.guiji.botsentence.controller.server.vo.BranchRefuseVO;
import ai.guiji.botsentence.controller.server.vo.BranchSpecialQuestionVO;
import ai.guiji.botsentence.controller.server.vo.DomainVO;
import ai.guiji.botsentence.dao.BotSentenceAdditionMapper;
import ai.guiji.botsentence.dao.BotSentenceBranchMapper;
import ai.guiji.botsentence.dao.BotSentenceDomainMapper;
import ai.guiji.botsentence.dao.BotSentenceIntentMapper;
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.VoliceInfoMapper;
import ai.guiji.botsentence.dao.entity.BotSentenceAddition;
import ai.guiji.botsentence.dao.entity.BotSentenceBranch;
import ai.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import ai.guiji.botsentence.dao.entity.BotSentenceDomain;
import ai.guiji.botsentence.dao.entity.BotSentenceDomainExample;
import ai.guiji.botsentence.dao.entity.BotSentenceIntent;
import ai.guiji.botsentence.dao.entity.BotSentenceIntentExample;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.entity.VoliceInfoExample;
import ai.guiji.botsentence.service.IFileGenerateService;
import ai.guiji.botsentence.service.IVoliceService;
import ai.guiji.component.client.util.FileUtil;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * 使用addition表，在表里存储txt等信息
 * @Description:
 * @author liyang  
 * @date 2018年9月2日  
 *
 */
//@Service
public class FileGenerateServiceImpl2 implements IFileGenerateService {

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
	private BotSentenceAdditionMapper botSentenceAdditionMapper;

	@Value("${template.dir}")
	private String tempDir;

	private static String FILE_SEPARATOR = System.getProperty("file.separator");

	private Logger logger = LoggerFactory.getLogger(FileGenerateServiceImpl2.class);

/*	@Override
	public Boolean fileGenerate(String processId) {
		File dir=new File("D:\\template\\test");
		return voliceService.uploadVoliceJsonZip(dir, "aaa","");
	}*/
//	@Override
	public Boolean fileGenerate(String processId) {

		// 获取流程对象
		BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(processId);
		String templateId = botSentenceProcess.getTemplateId();

		// 拷贝文件，创建文件夹
		String dirName = templateId + System.currentTimeMillis();
		String processDir = tempDir + FILE_SEPARATOR + dirName ;
		String templateCfgsDir = processDir+ FILE_SEPARATOR + "cfgs" + FILE_SEPARATOR + templateId;
		
		try {
			FileUtil.copyDir(tempDir+FILE_SEPARATOR+"copyfiles", templateCfgsDir);
		} catch (IOException e) {
			logger.error("copy file failed !! " + templateId);
			return false;
		}
		
		// domain的json文件所放的路径
		String domainDir = templateCfgsDir + FILE_SEPARATOR + "new_domain_cfg" + FILE_SEPARATOR;
		String selectDir = templateCfgsDir + FILE_SEPARATOR + "select_cfg" + FILE_SEPARATOR;
		String wavDir = processDir+ FILE_SEPARATOR + "wav" + FILE_SEPARATOR + templateId +"_rec";

		// 获取录音文件列表
		VoliceInfoExample example = new VoliceInfoExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<VoliceInfo> voliceInfos = voliceInfoMapper.selectByExample(example);

		Map<Long, String> voliceMap = new HashMap<Long, String>();
		int size = voliceInfos.size();
		for (int i = 1; i <= size; i++) {
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

			// 设置branch
			List<Map> branchShow = new ArrayList<Map>();

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
							BranchNodeVO.setKeys(getIntentKeys(botSentenceBranch.getIntents()));
							map.put(branchName, BranchNodeVO);
							branchShow.add(map);
						} else if (branchDomain.equals("一般问题") && branchName.startsWith("special")) {
							BranchSpecialQuestionVO BranchNodeVO = new BranchSpecialQuestionVO();
							BranchNodeVO.setNext(botSentenceBranch.getNext());
							BranchNodeVO.setKeys(getIntentKeys(botSentenceBranch.getIntents()));
							BranchNodeVO.setEnd(botSentenceBranch.getEnd());
							BranchNodeVO.setUser_ask(botSentenceBranch.getUserAsk());
							BranchNodeVO.setResponse(getVolies(botSentenceBranch.getResponse(), voliceMap));
							map.put(branchName, BranchNodeVO);
							branchShow.add(map);
						} else if(branchName.startsWith("refuse_")){
							BranchRefuseVO BranchNodeVO = new BranchRefuseVO();
							BranchNodeVO.setNext(botSentenceBranch.getNext());
							BranchNodeVO.setKeys(getIntentKeys(botSentenceBranch.getIntents()));
							BranchNodeVO.setEnd(botSentenceBranch.getEnd());
							BranchNodeVO.setResponse(getVolies(botSentenceBranch.getResponse(), voliceMap));
							BranchNodeVO.setIs_special_limit_free(true);
							map.put(branchName, BranchNodeVO);
							branchShow.add(map);
						} else {
							BranchNegativeVO BranchNodeVO = new BranchNegativeVO();
							BranchNodeVO.setNext(botSentenceBranch.getNext());
							BranchNodeVO.setKeys(getIntentKeys(botSentenceBranch.getIntents()));
							BranchNodeVO.setEnd(botSentenceBranch.getEnd());
							BranchNodeVO.setResponse(getVolies(botSentenceBranch.getResponse(), voliceMap));
							map.put(branchName, BranchNodeVO);
							branchShow.add(map);
						}
					}
				}
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
				FileUtil.writeFile(domainDir + domainName + ".json", domainJson);
			} catch (IOException e) {
				logger.error("write domain json file failed : " + domainDir + domainName + ".json");
				return false;
			}
		}

		// 生成select.json
		Map<String, String> selectMap = new HashMap<String, String>();
		BotSentenceIntentExample exampleIntent = new BotSentenceIntentExample();
		exampleIntent.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceIntent> listIntent = botSentenceIntentMapper.selectByExampleWithBLOBs(exampleIntent);
		for (BotSentenceIntent botSentenceIntent : listIntent) {
			if (botSentenceIntent.getForSelect() == 1 && botSentenceIntent.getName().contains("enter_branch")) {
				String keys = botSentenceIntent.getKeywords();
				String[] arr = botSentenceIntent.getName().split("_");
				String domain = arr[2];
				selectMap.put(domain, keys);
			}
		}
		String selectJson = JSON.toJSONString(selectMap).replace("\"[", "[").replace("]\"", "]").replace("\\", "");
		try {
			FileUtil.writeFile(selectDir + "select.json", selectJson);
		} catch (IOException e) {
			logger.error("write select json file failed : " + selectDir + "select.json");
			return false;
		}

		
		//生成stat.json
		
		
		
		
		// 下载音频文件
		RestTemplate restTemplate = new RestTemplate();
		for (int i = 1; i <= size; i++) {
			String wavURL = voliceInfos.get(i - 1).getVoliceUrl();
			try {
				if (StringUtils.isNotBlank(wavURL)) {
					generateWAV(restTemplate, wavURL, Integer.toString(i), wavDir);
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("generate wav has exception:" + e);
				return false;
			}
		}
		
		// 生成一些附加的文件
		BotSentenceAddition botSentenceAddition = botSentenceAdditionMapper.selectByPrimaryKey(processId);
		String optionJson = botSentenceAddition.getOptionsJson();
		String optionPath = templateCfgsDir+ FILE_SEPARATOR + "options.json";
		try {
			FileUtil.writeFile(optionPath, optionJson);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("generate options.json has exception:");
			return false;
		}
		
		try {
			String simTxt = new String(botSentenceAddition.getSimTxt().getBytes("iso8859-1"),"utf-8");
			String simPath = templateCfgsDir + FILE_SEPARATOR + "sim_dict" + FILE_SEPARATOR + "sim.txt";
			FileUtil.writeFile(simPath, simTxt);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			logger.error("generate sim.txt has UnsupportedEncodingException:"+e1);
			return false;
		}
		 catch (IOException e) {
			logger.error("generate sim.txt has exception:"+e);
			return false;
		}
		
		String stopWordsTxt = botSentenceAddition.getStopwordsTxt();
		String stopWordsPath = templateCfgsDir + FILE_SEPARATOR + "jieba_dict" + FILE_SEPARATOR + "stopwords.txt";
		try {
			FileUtil.writeFile(stopWordsPath, stopWordsTxt);
		} catch (IOException e) {
			logger.error("generate stopwords.txt has exception:");
			return false;
		}
		
		String temlateJson = botSentenceAddition.getTemplateJson();
		String temlatePath = templateCfgsDir + FILE_SEPARATOR + "template" + FILE_SEPARATOR + "template.json";
		try {
			FileUtil.writeFile(temlatePath, temlateJson);
		} catch (IOException e) {
			logger.error("generate stopwords.txt has exception:");
			return false;
		}
		
		try {
			String userDictTxt = new String(botSentenceAddition.getUserdictTxt().getBytes("iso8859-1"),"utf-8");
			String userDictPath = templateCfgsDir + FILE_SEPARATOR + "jieba_dict" + FILE_SEPARATOR + "userdict.txt";
			FileUtil.writeFile(userDictPath, userDictTxt);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			logger.error("generate userdict.txt has UnsupportedEncodingException:"+e1);
			return false;
		}
		 catch (IOException e) {
			logger.error("generate userdict.txt has exception:"+e);
			return false;
		}
		
		String weightsTxt = botSentenceAddition.getWeightsTxt();
		String weightsPath = templateCfgsDir + FILE_SEPARATOR + "weights_cfg" + FILE_SEPARATOR + "weights.txt";
		try {
			FileUtil.writeFile(weightsPath, weightsTxt);
		} catch (IOException e) {
			logger.error("generate weights.txt has exception:");
			return false;
		}	
		
		return true;
//		将文件发给sellbot
		//File dir=new File(processDir);
		//return voliceService.uploadVoliceJsonZip(dir, dirName,processId);

	}
	
	
	/**
	 * 下载音频文件
	 */
	public void generateWAV(RestTemplate template, String url,String name,String dir) throws IOException {

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Resource> httpEntity = new HttpEntity<Resource>(headers);
		ResponseEntity<byte[]> response = template.exchange(url, HttpMethod.GET, httpEntity, byte[].class);
		File file = new File(dir+ FILE_SEPARATOR + name + ".wav");
		File fileDir = new File(dir);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(response.getBody());
		fos.flush();
		fos.close();

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
		File dir=new File("D:\\template\\test");
       // new VoliceServiceImpl().uploadVoliceJsonZip(dir, "","");
	}


	@Override
	public File fileGenerate(String processId, String dirName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
