package ai.guiji.botsentence.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysUser;

import ai.guiji.botsentence.constant.Constant;
import ai.guiji.botsentence.controller.server.vo.ImportBranchVO;
import ai.guiji.botsentence.controller.server.vo.ImportDomainVO;
import ai.guiji.botsentence.controller.server.vo.ImportLabelVO;
import ai.guiji.botsentence.dao.BotSentenceAdditionMapper;
import ai.guiji.botsentence.dao.BotSentenceBranchMapper;
import ai.guiji.botsentence.dao.BotSentenceDomainMapper;
import ai.guiji.botsentence.dao.BotSentenceIndustryMapper;
import ai.guiji.botsentence.dao.BotSentenceIntentMapper;
import ai.guiji.botsentence.dao.BotSentenceLabelMapper;
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.BotSentenceTemplateMapper;
import ai.guiji.botsentence.dao.VoliceInfoMapper;
import ai.guiji.botsentence.dao.entity.BotSentenceAddition;
import ai.guiji.botsentence.dao.entity.BotSentenceAdditionExample;
import ai.guiji.botsentence.dao.entity.BotSentenceBranch;
import ai.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import ai.guiji.botsentence.dao.entity.BotSentenceDomain;
import ai.guiji.botsentence.dao.entity.BotSentenceDomainExample;
import ai.guiji.botsentence.dao.entity.BotSentenceIndustry;
import ai.guiji.botsentence.dao.entity.BotSentenceIndustryExample;
import ai.guiji.botsentence.dao.entity.BotSentenceIntent;
import ai.guiji.botsentence.dao.entity.BotSentenceIntentExample;
import ai.guiji.botsentence.dao.entity.BotSentenceLabel;
import ai.guiji.botsentence.dao.entity.BotSentenceLabelExample;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import ai.guiji.botsentence.dao.entity.BotSentenceTemplate;
import ai.guiji.botsentence.dao.entity.BotSentenceTemplateExample;
import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.entity.VoliceInfoExample;
import ai.guiji.botsentence.dao.ext.ImportProcessMapper;
import ai.guiji.botsentence.service.IImportProcessService;
import ai.guiji.botsentence.vo.BotSentenceProcessVO;
import ai.guiji.component.client.util.FileUtil;
import ai.guiji.component.client.util.FtpUploadUtil;
import ai.guiji.component.client.util.Pinyin4jUtil;
import ai.guiji.component.client.util.QiuniuUploadUtil;
import ai.guiji.component.client.util.SystemUtil;
import ai.guiji.component.exception.CommonException;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Service
public class ImportProcessServiceImpl implements IImportProcessService {

	@Autowired
	private FtpUploadUtil ftpUploadUtil;
	@Value("${sftp.path}")
	private String ftpPath;

	@Autowired
	private ImportProcessMapper importProcessMapper;

	@Autowired
	private VoliceInfoMapper voliceInfoMapper;
	@Autowired
	private BotSentenceBranchMapper botSentenceBranchMapper;
	@Autowired
	private BotSentenceIntentMapper botSentenceIntentMapper;
	@Autowired
	private BotSentenceLabelMapper botSentenceLabelMapper;
	@Autowired
	private BotSentenceDomainMapper botSentenceDomainMapper;
	@Autowired
	private BotSentenceTemplateMapper botSentenceTemplateMapper;
	@Autowired
	private BotSentenceAdditionMapper botSentenceAdditionMapper;
	@Autowired
	private QiuniuUploadUtil qiuniuUploadUtil;

	@Autowired
	private BotSentenceIndustryMapper botSentenceIndustryMapper;

	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;

	@Autowired
	private VoliceServiceImpl voliceServiceImpl;

	@Autowired
	private IAuth iAuth;

	private Logger logger = LoggerFactory.getLogger(ImportProcessServiceImpl.class);

	public static void main(String[] args) {

	}

	@Override
	@Transactional
	public void importProcess(File templateFile, BotSentenceProcessVO paramVO,Long userId) {
		handleData(templateFile, paramVO,userId);
	}

	public boolean handleData(File templateFile, BotSentenceProcessVO paramVO,Long userId) {
		String templatId=paramVO.getTemplateId();
		ReturnData<SysUser> data=iAuth.getUserById(userId);
		String userName=data.getBody().getUsername();
		String orgCode=paramVO.getOrgCode();
		String orgName=paramVO.getOrgName();
		if(org.springframework.util.StringUtils.isEmpty(paramVO.getOrgCode())){
			orgCode=data.getBody().getOrgCode();
			orgName=data.getBody().getOrgName();
		}
		if(StringUtils.isBlank(userId.toString())) {
			logger.error("用户信息为空");
			return false;
		}

		Date date = new Date();
		logger.info("------>>start import data,file:" + templateFile.getName());
		List<File> listFile = new ArrayList<File>();
		FileUtil.getAllFilePaths(templateFile, listFile);

		//String templateName="";
		String template_id = "";
		String trade = "";
		String des = "";
		String template_name = "";
		String options_json = "";
		//获取模板名称
		for (File file : listFile) {
			if(file.getName().equals("options.json")) {
				try {
					options_json = FileUtil.readToString(file);
					JSONObject json = JSONObject.parseObject(options_json);
					template_id = json.getString("tempname") + "_en";
					trade = json.getString("trade");
					des = json.getString("des");
					template_name = json.getString("dianame");//模板名称

				} catch (IOException e) {
					logger.error("加载options.json文件异常", e);
					return false;
				}
			}
		}

		logger.info("话术模板编号: " + template_id);
		logger.info("行业: " + trade);
		logger.info("话术模板描述: " + des);
		logger.info("话术模板名称: " + template_name);

		if(StringUtils.isBlank(template_name)) {
			template_name = "未命名";
		}

		if(StringUtils.isBlank(trade)) {
			trade = "未知行业";
		}

		String processId = "";
		

		if("02".equals(paramVO.getTemplateType())) {//导入个人流程

			if(StringUtils.isNotBlank(templatId)) {
				logger.info("更新话术模板");
				logger.info("模板编号:" + template_id);
				BotSentenceProcessExample example = new BotSentenceProcessExample();
				example.createCriteria().andTemplateIdEqualTo(templatId);
				List<BotSentenceProcess> list = botSentenceProcessMapper.selectByExample(example);
				if(null != list && list.size() > 0 || !templatId.equals(template_id)) {
					logger.info("导入更新个人话术模板");
					processId = list.get(0).getProcessId();
					//先删除当前话术有关的数据
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

					//删除label
					BotSentenceLabelExample deleteLabelExample = new BotSentenceLabelExample();
					deleteLabelExample.createCriteria().andProcessIdEqualTo(processId);
					botSentenceLabelMapper.deleteByExample(deleteLabelExample);

					//删除addition
					BotSentenceAdditionExample deleteAdditionExample = new BotSentenceAdditionExample();
					deleteAdditionExample.createCriteria().andProcessIdEqualTo(processId);
					botSentenceAdditionMapper.deleteByExample(deleteAdditionExample);


				}else {
					throw new CommonException("当前模板不存在!");
				}

			}else {
				// 插入process
				processId = importProcessMapper.getProcessId();
				BotSentenceProcess botSentenceProcess = new BotSentenceProcess();
				botSentenceProcess.setProcessId(processId);
				botSentenceProcess.setState(Constant.APPROVE_MAEKING);
				botSentenceProcess.setTemplateType("02");

				//生成模板编号
				Pinyin4jUtil util= new Pinyin4jUtil();
				String pingyin = "";
				try {
					pingyin = util.toPinYinLowercase(trade);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					logger.error("生成首字母异常...", e);
					pingyin = SystemUtil.getSysJournalNo(5, false);
				}

				template_id = pingyin + "_" + SystemUtil.getSysJournalNo(5, true) + "_en";

				botSentenceProcess.setTemplateId(template_id);//模板编号
				botSentenceProcess.setTemplateName(template_name);//模板名称
				botSentenceProcess.setCrtTime(date);//创建时间
				botSentenceProcess.setIndustry(trade);//行业
				botSentenceProcess.setVersion("0");//默认版本为0
				botSentenceProcess.setAccountNo(userId.toString());//设置账号
				botSentenceProcess.setOrgCode(orgCode);
				botSentenceProcess.setUserName(userName);
				botSentenceProcess.setOrgName(orgName);
				botSentenceProcess.setCrtUser(userId.toString());
				botSentenceProcessMapper.insertSelective(botSentenceProcess);

				processId =botSentenceProcess.getProcessId(); 
			}

		}else {//默认为导入话术模板
			//判断当前模板是否已存在，如果存在则更新
			BotSentenceTemplateExample queryExample = new BotSentenceTemplateExample();
			queryExample.createCriteria().andTemplateIdEqualTo(template_id);
			List<BotSentenceTemplate> list = botSentenceTemplateMapper.selectByExample(queryExample);

			//判断当前行业是否存在，如果存在，则不处理，如果不存在，则新增一条行业信息
			BotSentenceIndustryExample industryExample = new BotSentenceIndustryExample();
			industryExample.createCriteria().andIndustryNameEqualTo(trade);
			List<BotSentenceIndustry> tradeList = botSentenceIndustryMapper.selectByExample(industryExample);
			int tradeId = 0;
			if(null != tradeList && tradeList.size() > 0) {
				tradeId = tradeList.get(0).getIndustryId();
			}else {
				BotSentenceIndustry botSentenceIndustry = new BotSentenceIndustry();
				botSentenceIndustry.setIndustryName(trade);
				botSentenceIndustry.setCrtTime(new Date(System.currentTimeMillis()));
				botSentenceIndustry.setCrtUser(userId.toString());
				botSentenceIndustryMapper.insert(botSentenceIndustry);
				tradeId = botSentenceIndustry.getIndustryId();
			}

			BotSentenceTemplate template = null;

			if(null != list && list.size() > 0) {
				logger.info("当前话术模板已存在，自动覆盖，保证话术模板流程编号不变....");
				template = list.get(0);
				template.setLstUpdateTime(new Date(System.currentTimeMillis()));
				template.setLstUpdateUser(userId.toString());
				template.setState("04");
				template.setTemplateType("01");
				template.setTemplateId(template_id);//模板编号
				template.setIndustryId(tradeId);//行业
				template.setIndustryName(trade);
				if(StringUtils.isNotBlank(template_name)) {
					template.setTemplateName(template_name);
				}else {
					template.setTemplateName(trade);
				}
				processId =template.getProcessId();
				botSentenceTemplateMapper.updateByPrimaryKey(template);


				//先删除当前话术有关的数据
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

				//删除label
				BotSentenceLabelExample deleteLabelExample = new BotSentenceLabelExample();
				deleteLabelExample.createCriteria().andProcessIdEqualTo(processId);
				botSentenceLabelMapper.deleteByExample(deleteLabelExample);

				//删除addition
				BotSentenceAdditionExample deleteAdditionExample = new BotSentenceAdditionExample();
				deleteAdditionExample.createCriteria().andProcessIdEqualTo(processId);
				botSentenceAdditionMapper.deleteByExample(deleteAdditionExample);



			}else {
				logger.info("当前话术不存在，创建新的话术模板...");
				template = new BotSentenceTemplate();
				template.setCrtTime(date);//创建时间
				template.setLstUpdateTime(new Date(System.currentTimeMillis()));
				template.setCrtUser(userId.toString());
				template.setState("04");
				template.setTemplateType("01");
				template.setTemplateId(template_id);//模板编号
				template.setIndustryId(tradeId);//行业
				template.setIndustryName(trade);
				if(StringUtils.isNotBlank(template_name)) {
					template.setTemplateName(template_name);
				}else {
					template.setTemplateName(trade);
				}

				template.setAccountNo(userId.toString());//设置账号
				template.setVersion("0");//默认版本为0
				botSentenceTemplateMapper.insertSelective(template);
				processId =template.getProcessId();
			}

		}


		// txt等文件的一些附加信息
		String sim_txt = "";
		String template_json = "";
		String weights_txt = "";
		String stopwords_txt = "";
		String userdict_txt = "";

		// 存储mav的url
		Map<String, String> mavMap = new HashMap<String, String>();
		// <*后的id,volice_id>
		Map<String, Long> voliceMap = new HashMap<String, Long>();

		for (File file : listFile) {
			if (file.getName().endsWith("_rec")) {
//				if("02".equals(paramVO.getTemplateType())) {//导入本地模板不需要导入录音文件
//					logger.info("导入本地模板，不需要导入录音文件>..");
//				}else {
					File[] mavFiles = file.listFiles();
					for (File wavFile : mavFiles) {
						String wavName = wavFile.getName();
						if (wavName.endsWith(".wav")) {
							try {
								String uploadurl = qiuniuUploadUtil.upload(new FileInputStream(wavFile), processId + "_" + wavName);
								mavMap.put(wavName.replace(".wav", ""), uploadurl);
							} catch (FileNotFoundException e) {
								logger.error("上传录音文件"+wavName+"异常...", e);
								continue;
							}
						}
					}
//				}


			} else if (file.getName().equals("select.json")) {// 处理select.json，插入到intent表
				try {
					String selectContent = FileUtil.readToString(file);
					JSONObject selectJSONObject = JSON.parseObject(selectContent);
					for (Entry<String, Object> entry : selectJSONObject.entrySet()) {
						String domainName = entry.getKey();
						JSONArray jSONArray = (JSONArray) entry.getValue();
						String keys = jSONArray.toString();
						BotSentenceIntent botSentenceIntent = new BotSentenceIntent();
						keys = keys.replace("\n", "");//替换换行符
						botSentenceIntent.setKeywords(keys);
						botSentenceIntent.setProcessId(processId);
						botSentenceIntent.setDomainName(domainName);
						botSentenceIntent.setName("process_import_" + domainName + "_enter_branch");
						botSentenceIntent.setForSelect(1);
						botSentenceIntent.setTemplateId(template_id);
						botSentenceIntent.setCrtTime(date);
						botSentenceIntent.setCrtUser("systemImport");
						botSentenceIntentMapper.insertSelective(botSentenceIntent);
					}
				} catch (IOException e) {
					logger.error("read select.json IOException:" + e);
					return false;
				}
			} else if (file.getName().equals("stat.json")) {// 处理stat.json，插入到label表
				try {
					String statContent = FileUtil.readToString(file);
					JSONObject statJSONObject = JSON.parseObject(statContent);
					for (Entry<String, Object> entry : statJSONObject.entrySet()) {
						String labelName = entry.getKey();
						if (labelName.equals("A") || labelName.equals("B") || labelName.equals("C")
								|| labelName.equals("D") || labelName.equals("E")) {
							JSONObject jSONObject = (JSONObject) entry.getValue();
							ImportLabelVO importLabelVO = JSON.parseObject(jSONObject.toJSONString(),
									ImportLabelVO.class);

							BotSentenceLabel botSentenceLabel = new BotSentenceLabel();
							botSentenceLabel.setBusyCount(importLabelVO.getBusy_count());
							botSentenceLabel.setConversationCount(importLabelVO.getConversation_count());
							botSentenceLabel.setDenyCount(importLabelVO.getDeny_count());
							botSentenceLabel.setKeywords(importLabelVO.getKeywords());
							botSentenceLabel.setLabelName(labelName);
							botSentenceLabel.setProcessId(processId);
							botSentenceLabel.setSpecialCount(importLabelVO.getSpecial_count());
							botSentenceLabel.setUsedTimeS(importLabelVO.getUsed_time_s());

							if (labelName.equals("A")) {
								botSentenceLabel.setScoreLow(50d);
								botSentenceLabel.setScoreUp(100d);
							} else if (labelName.equals("B")) {
								botSentenceLabel.setScoreLow(30d);
								botSentenceLabel.setScoreUp(49d);
							} else if (labelName.equals("C")) {
								botSentenceLabel.setScoreLow(10d);
								botSentenceLabel.setScoreUp(29d);
							} else if (labelName.equals("D")) {
								botSentenceLabel.setScoreLow(0d);
								botSentenceLabel.setScoreUp(9d);
							} else if (labelName.equals("E")) {
								botSentenceLabel.setScoreLow(0d);
								botSentenceLabel.setScoreUp(0d);
							}
							botSentenceLabel.setCrtTime(date);
							botSentenceLabel.setCrtUser("systemImport");
							botSentenceLabelMapper.insertSelective(botSentenceLabel);
						}

					}
				} catch (IOException e) {
					logger.error("read stat.json IOException:" + e);
					return false;
				}
			} else if (file.getName().equals("template.json")) {
				try {
					template_json = FileUtil.readToString(file);
				} catch (IOException e) {
					logger.error("read template.json IOException:" + e);
					return false;
				}
			} else if (file.getName().equals("sim.txt")) {
				try {
					sim_txt = FileUtil.readToString(file);
				} catch (IOException e) {
					logger.error("read sim.txt IOException:" + e);
					return false;
				}
			} else if (file.getName().equals("weights.txt")) {
				try {
					weights_txt = FileUtil.readToString(file);
				} catch (IOException e) {
					logger.error("read weights.txt IOException:" + e);
					return false;
				}
			} else if (file.getName().equals("options.json")) {
				try {
					options_json = FileUtil.readToString(file);
				} catch (IOException e) {
					logger.error("read options.json IOException:" + e);
					return false;
				}
			} else if (file.getName().equals("stopwords.txt")) {
				try {
					stopwords_txt = FileUtil.readToString(file);
				} catch (IOException e) {
					logger.error("read stopwords.txt IOException:" + e);
					return false;
				}
			} else if (file.getName().equals("userdict.txt")) {
				try {
					userdict_txt = FileUtil.readToString(file);
				} catch (IOException e) {
					logger.error("read userdict.txt IOException:" + e);
					return false;
				}
			}
		}
		// 插入addition的一些附加信息
		BotSentenceAddition botSentenceAddition = new BotSentenceAddition();
		botSentenceAddition.setProcessId(processId);
		botSentenceAddition.setOptionsJson(options_json);
		botSentenceAddition.setSimTxt(sim_txt);
		botSentenceAddition.setStopwordsTxt(stopwords_txt);
		botSentenceAddition.setTemplateJson(template_json);
		botSentenceAddition.setUserdictTxt(userdict_txt);
		botSentenceAddition.setWeightsTxt(weights_txt);
		botSentenceAdditionMapper.insertSelective(botSentenceAddition);

		// 处理domain
		List<BotSentenceDomain> domainList = new ArrayList<BotSentenceDomain>();
		List<BotSentenceBranch> branchList = new ArrayList<BotSentenceBranch>();

		Map<String, ImportDomainVO> domainMap = new HashMap<>();
		List<String> validateBranchList = new ArrayList<>();

		List<String> mainDomainList = new ArrayList<>();
		List<String> mainBranchList = new ArrayList<>();

		for (File file : listFile) {
			String fileName = file.getName();
			if (fileName.equals("new_domain_cfg")) {
				File[] domainFiles = file.listFiles();
				for (File domainFile : domainFiles) {
					String domainFileName = domainFile.getName();
					logger.info(domainFileName);
					if (domainFileName.endsWith(".json")) {
						try {
							String domainName = domainFileName.replace(".json", "");
							String domainJson = FileUtil.readToString(domainFile);
							JSONObject jSONObjectDomainDetail = (JSONObject) JSON.parseObject(domainJson).get(domainName);
							ImportDomainVO importDomainVO = JSONObject.parseObject(jSONObjectDomainDetail.toJSONString(),
									ImportDomainVO.class);

							//校验是否存在指向一般问题的domain
							if(!"一般问题".equals(domainName) && "一般问题".equals(importDomainVO.getCom_domain())) {
								throw new CommonException("导入模板失败,流程"+domainName+"指向了一般问题!");
							}

							String branchStr = importDomainVO.getBranch();
							JSONObject objects = JSON.parseObject(branchStr);
							if (objects != null && !objects.isEmpty()) {
								for (Entry<String, Object> entry : objects.entrySet()) {
									String branchName = entry.getKey();
									JSONObject branchJSONObject = (JSONObject) entry.getValue();
									ImportBranchVO importBranchVO = JSON.parseObject(branchJSONObject.toJSONString(), ImportBranchVO.class);
									if(!"negative".equals(branchName) && !"一般问题".equals(domainName) && !"投诉".equals(domainName) 
											&& StringUtils.isNotBlank(importDomainVO.getCom_domain())) {

										logger.info(domainName+importBranchVO.getNext());

										if(validateBranchList.contains(domainName+importBranchVO.getNext())) {
											throw new CommonException("导入模板失败,流程"+domainName+"存在多条分支同时指向"+importBranchVO.getNext());	
										}
										validateBranchList.add(domainName+importBranchVO.getNext());

									}
									//把属于流程的保存下来，剔除掉negative
									if(!"negative".equals(branchName) && !mainDomainList.contains(importBranchVO.getNext())) {
										mainDomainList.add(importBranchVO.getNext());
									}

									if(!"negative".equals(branchName) && !mainBranchList.contains(domainName + "," + branchName)) {
										mainBranchList.add(domainName + "," + branchName);
									}

									//校验是否存在指向一般问题的domain
									if( !"一般问题".equals(domainName) && !"投诉".equals(domainName) && "一般问题".equals(importBranchVO.getNext()) && StringUtils.isNotBlank(importDomainVO.getCom_domain())) {
										throw new CommonException("导入模板失败,流程"+domainName+"指向了一般问题!");
									}
								}
							}

							//把属于流程的保存下来
							if(!"解释开场白".equals(domainName) && !mainDomainList.contains(domainName)) {
								mainDomainList.add(domainName);
							}

							domainMap.put(domainName, importDomainVO);
						} catch (IOException e) {
							logger.error("read new_domain_cfg: json file IOException:" + e);
							return false;
						}
					}
				}
			}
		}


		//遍历domainmap
		for(String key : domainMap.keySet()) {
			Map<String, String> responseMap = new HashMap<String, String>();
			processDomains(domainMap.get(key), key,
					processId, voliceMap, date,domainList,branchList,responseMap,template_id, mavMap, mainDomainList, mainBranchList,userId);
		}

		List<String> mainDomainNameList = new ArrayList<>();
		BotSentenceDomain startDomain = null;
		BotSentenceDomain startExplainDomain = null;

		for(BotSentenceDomain temp : domainList) {
			if("1".equals(temp.getCategory())) {
				if("开场白".equals(temp.getDomainName())) {
					startDomain = temp;
					temp.setType("start");
				}else if("结束".equals(temp.getDomainName())) {
					temp.setType("end");
				}else {
					temp.setType("process");
				}
				if(!mainDomainNameList.contains(temp.getDomainName())) {
					mainDomainNameList.add(temp.getDomainName());
				}
			}else {
				temp.setCategory("3");
			}

			if("解释开场白".equals(temp.getDomainName())) {
				startExplainDomain = temp;
				temp.setType("start_explain");
				temp.setCategory("3");
			}

			temp.setTemplateId(template_id);
		}

		//如果开场白下一节点是解释开场白，则修改开场白指向解释开场白的下级节点
		boolean flag = false;
		if(null != startDomain && null != startExplainDomain) {
			if("解释开场白".equals(startDomain.getComDomain())) {
				flag = true;
				startDomain.setComDomain(startExplainDomain.getComDomain());
			}
		}


		logger.info("保存domain开始...");

		//保存domain
		for(BotSentenceDomain botSentenceDomain:domainList) {
			botSentenceDomainMapper.insertSelective(botSentenceDomain);
			//initparent(botSentenceDomain.getProcessId());//初始化parent字段
		}
		logger.info("保存domain结束...");

		logger.info("开始初始化坐标...");
		//初始化数据
		initPositive(domainList);
		logger.info("初始化坐标结束...");


		//branch设置resp，然后插入库中
		for(BotSentenceBranch botSentenceBranch:branchList) {
			String resps = botSentenceBranch.getResponse();
			String response = "[";
			if(StringUtils.isNotBlank(resps)) {
				String[] ids = resps.split(",");
				for(String id : ids) {
					response = response +voliceMap.get(id) + ",";
				}
			}
			if(response.endsWith(",")) {
				response=response.substring(0, response.length()-1);
			}
			botSentenceBranch.setResponse(response+"]");
			botSentenceBranch.setTemplateId(template_id);

			//设置branch的line
			if (mainDomainNameList.contains(botSentenceBranch.getNext()) && mainDomainNameList.contains(botSentenceBranch.getDomain())){
				if("positive".equals(botSentenceBranch.getBranchName())) {

					botSentenceBranch.setLineName("未拒绝");
					botSentenceBranch.setType(Constant.BRANCH_TYPE_POSITIVE);

					botSentenceBranch.setIsShow("1");
				}else if(botSentenceBranch.getBranchName().startsWith("special")) {
					botSentenceBranch.setLineName("分支");
					botSentenceBranch.setIsShow("1");
					botSentenceBranch.setType(Constant.BRANCH_TYPE_NORMAL);
				}
			}
			if("解释开场白".equals(botSentenceBranch.getNext())) {
				botSentenceBranch.setLineName(null);
				botSentenceBranch.setIsShow(null);
				botSentenceBranch.setType(null);
			}

			if(flag) {
				if("positive".equals(botSentenceBranch.getBranchName()) && "解释开场白".equals(botSentenceBranch.getDomain())) {
					botSentenceBranch.setDomain("开场白");
				}
			}


			botSentenceBranchMapper.insertSelective(botSentenceBranch);
		}

		logger.info("------>>end import data,processId:" + processId);
		return true;

	}

	public void setLevel(List<BotSentenceBranch> branchList,String domainName,Map<String,Integer> mapLevel) {

		for(BotSentenceBranch botSentenceBranch:branchList) {
			String branchName = botSentenceBranch.getBranchName();
			if(!branchName.equals("negative") && !branchName.equals("enter_branch") && !branchName.equals("failed_enter_branch")) {
				if(botSentenceBranch.getDomain().equals(domainName)) {
					int levelUp = mapLevel.get(domainName);
					String next = botSentenceBranch.getNext();
					if(mapLevel.get(next)!=null) {
						if(mapLevel.get(next)<1+levelUp) {
							mapLevel.put(next, 1+levelUp);
						}
					}else {
						mapLevel.put(next, 1+levelUp);
					}
					setLevel(branchList,next,mapLevel);
				}
			}
		}

	}


	public void processDomains(ImportDomainVO importDomainVO, String domainName, String processId, Map<String, Long> voliceMap,
			Date date,List<BotSentenceDomain> domainList,List<BotSentenceBranch> branchList, Map<String, String> responseMap, String templateId, Map<String, String> mavMap
			,List<String> mainDomainList, List<String> mainBranchList,Long userId) {

		/*JSONObject jSONObjectDomainDetail = (JSONObject) JSON.parseObject(domainJson).get(domainName);

		ImportDomainVO importDomainVO = JSONObject.parseObject(jSONObjectDomainDetail.toJSONString(),
				ImportDomainVO.class);*/

		// 插入enter_branch
		String enter = importDomainVO.getEnter();
		if (StringUtils.isNotBlank(enter)) {
			String response = "";
			if (enter.startsWith("[")) {
				enter = enter.replace("[", "").replace("]", "");
			}
			if (StringUtils.isNotBlank(enter)) {
				String[] enters = enter.split("\",\"");
				for (String enterResp : enters) {
					if (StringUtils.isNotBlank(enterResp)) {
						responseMap.put(getId(enterResp), getRespContent(enterResp));
						response = response+getId(enterResp)+",";
					}
				}
				BotSentenceBranch botSentenceBranch = new BotSentenceBranch();
				botSentenceBranch.setBranchName("enter_branch");
				botSentenceBranch.setDomain(domainName);
				botSentenceBranch.setProcessId(processId);
				botSentenceBranch.setTemplateId(templateId);
				botSentenceBranch.setResponse(response);
				botSentenceBranch.setCrtTime(date);
				botSentenceBranch.setCrtUser(userId.toString());
				branchList.add(botSentenceBranch);
			}
		}
		// 插入failed_enter_branch
		String failEnter = importDomainVO.getFailed_enter();
		if (StringUtils.isNotBlank(failEnter)) {
			String responseFail = "";
			if (failEnter.startsWith("[")) {
				failEnter = failEnter.replace("[", "").replace("]", "");
			}
			if (StringUtils.isNotBlank(failEnter)) {
				String[] entersfail = failEnter.split("\",\"");
				for (String enterRespFail : entersfail) {
					if (StringUtils.isNotBlank(enterRespFail)) {
						responseMap.put(getId(enterRespFail), getRespContent(enterRespFail));
						responseFail = responseFail+getId(enterRespFail)+",";
					}
				}
				BotSentenceBranch botSentenceBranchFail = new BotSentenceBranch();
				botSentenceBranchFail.setBranchName("failed_enter_branch");
				botSentenceBranchFail.setDomain(domainName);
				botSentenceBranchFail.setProcessId(processId);
				botSentenceBranchFail.setResponse(responseFail);
				botSentenceBranchFail.setCrtTime(date);
				botSentenceBranchFail.setCrtUser(userId.toString());
				branchList.add(botSentenceBranchFail);
			}
		}

		// 插入其他branch
		String branchStr = importDomainVO.getBranch();
		JSONObject objects = JSON.parseObject(branchStr);
		if (objects != null && !objects.isEmpty()) {
			for (Entry<String, Object> entry : objects.entrySet()) {
				String branchName = entry.getKey();

				JSONObject branchJSONObject = (JSONObject) entry.getValue();
				ImportBranchVO importBranchVO = JSON.parseObject(branchJSONObject.toJSONString(), ImportBranchVO.class);

				BotSentenceBranch branchMost = new BotSentenceBranch();
				branchMost.setBranchName(branchName);
				branchMost.setDomain(domainName);
				branchMost.setProcessId(processId);

				String responseMost = "";
				List<String> respList = importBranchVO.getResponse();
				if (respList != null) {
					for (String resp : respList) {
						if(resp.startsWith("[")) {
							JSONArray jSONArray = JSON.parseArray(resp);
							for(int i=0;i<jSONArray.size();i++){
								String respInner = jSONArray.getString(i);
								responseMap.put(getId(respInner), getRespContent(respInner));
								responseMost = responseMost+getId(respInner)+",";
							}
						}else {
							responseMap.put(getId(resp), getRespContent(resp));
							responseMost = responseMost+getId(resp)+",";
						}
						//						responseMost += voliceMap.get(getId(resp)) + ",";
					}
					branchMost.setResponse(responseMost);
				}

				branchMost.setUserAsk(importBranchVO.getUser_ask());
				branchMost.setEnd(importBranchVO.getEnd());
				branchMost.setNext(importBranchVO.getNext());

				// 插入intent表
				String keys = importBranchVO.getKeys();
				if (StringUtils.isNotBlank(keys) && !keys.equals("[]")) {
					BotSentenceIntent botSentenceIntent = new BotSentenceIntent();
					keys = keys.replace("\n", "");//替换换行符
					botSentenceIntent.setKeywords(keys);
					botSentenceIntent.setProcessId(processId);
					botSentenceIntent.setTemplateId(templateId);
					botSentenceIntent.setName("process_import_" + domainName + "_" + branchName);
					botSentenceIntent.setForSelect(0);
					botSentenceIntent.setDomainName(domainName);
					botSentenceIntent.setCrtTime(date);
					botSentenceIntent.setCrtUser(userId.toString());
					botSentenceIntentMapper.insertSelective(botSentenceIntent);
					long intents = botSentenceIntent.getId();
					branchMost.setIntents(String.valueOf(intents));
				}

				branchMost.setCrtTime(date);
				branchMost.setCrtUser(userId.toString());
				branchList.add(branchMost);
			}
		}

		//处理挽回文案
		for(int j = branchList.size()-1 ; j >= 0 ; j--) {
			BotSentenceBranch temp = branchList.get(j);
			if(temp.getBranchName().startsWith("refuse_")) {
				branchList.remove(temp);//删除当前
			}

			/*if(temp.getBranchName().startsWith("refuse_") && temp.getResponse().indexOf(",") > 0) {
				branchList.remove(temp);//删除当前
				String[] array = temp.getResponse().split(",");
				for(int i = 0 ; i < array.length ; i++) {
					if(StringUtils.isNotBlank(array[i].trim())) {
						BotSentenceBranch branch = new BotSentenceBranch();
						branch.setResponse(array[i]);
						branch.setRespname(domainName + "-" + "挽回" + (i+1));
						branch.setBranchName("refuse_" + domainName);
						branch.setCrtTime(new Date(System.currentTimeMillis()));
						branch.setCrtUser(userId);
						branch.setDomain(domainName);
						branch.setIsShow("0");
						branch.setNext(domainName);
						branch.setEnd("结束");
						branch.setTemplateId(templateId);
						branch.setProcessId(processId);
						branch.setIntents(temp.getIntents());
						branchList.add(branch);
					}
				}
			}*/
		}


		// domain插入
		BotSentenceDomain botSentenceDomain = new BotSentenceDomain();
		botSentenceDomain.setComDomain(importDomainVO.getCom_domain());
		botSentenceDomain.setIgnoreButDomains(importDomainVO.getIgnore_but_domains());
		botSentenceDomain.setProcessId(processId);
		botSentenceDomain.setDomainName(domainName);
		botSentenceDomain.setCrtTime(date);
		botSentenceDomain.setCrtUser(userId.toString());
		if(mainDomainList.contains(domainName) && StringUtils.isNotBlank(importDomainVO.getCom_domain())) {
			botSentenceDomain.setCategory("1");
		}
		if("结束".equals(domainName)) {
			botSentenceDomain.setCategory("1");
		}
		if("投诉".equals(domainName)) {
			botSentenceDomain.setCategory(null);
		}
		/*if (!domainName.equals("一般问题") && !domainName.equals("在忙") && !domainName.equals("号码过滤")
				&& !domainName.equals("结束_在忙") && !domainName.equals("结束_未匹配") && !domainName.equals("出错")
				&& !domainName.equals("投诉") && !domainName.equals("未匹配响应") && !domainName.equals("拒绝")
				&& !domainName.equals("强制结束") && !domainName.equals("不清楚") && !domainName.equals("不知道")
				&& !domainName.equals("用户不清楚") && !domainName.equals("自由介绍") && !domainName.equals("解释开场白")
				&& !domainName.equals("邀约_成功") && !domainName.equals("等待")) {
			botSentenceDomain.setCategory("1");
			botSentenceDomain.setIsMainFlow("01");//设置为主流程
		}else {
			botSentenceDomain.setCategory("3");
		}*/


		domainList.add(botSentenceDomain);

		// 插入volice录音信息
		for(Entry<String, String> entry : responseMap.entrySet()) {
			String id = entry.getKey();
			String content = entry.getValue();
			VoliceInfo voliceInfo = new VoliceInfo();
			voliceInfo.setContent(content.replace("\n", "").trim());
			voliceInfo.setProcessId(processId);
			voliceInfo.setDomainName(domainName);
			voliceInfo.setTemplateId(templateId);
			voliceInfo.setVoliceUrl(mavMap.get(id));
			voliceInfo.setCrtTime(date);
			voliceInfo.setCrtUser(userId.toString());
			//voliceInfoMapper.insertSelective(voliceInfo);
			voliceServiceImpl.saveVoliceInfo(voliceInfo,userId);
			voliceMap.put(id, voliceInfo.getVoliceId());
		}
	}

	/**
	 * 获取说话内容*后面的编号
	 * 
	 * @param reponse
	 * @return
	 */
	public String getId(String reponse) {
		String[] recordArr = reponse.split("\\*");
		String id = recordArr[recordArr.length - 1];
		if (id.endsWith("\"")) {
			return id.replace("\"", "");
		}
		return id;
	}

	/**
	 * 获取说话内容*前面的内容
	 * 
	 * @param
	 * @return
	 */
	public String getRespContent(String reponse) {

		String[] recordArr = reponse.split("\\*");
		String respContent = "";
		for(int i=0;i<recordArr.length-1;i++) {
			respContent = respContent + recordArr[i];
		}

		if (respContent.endsWith("\"")) {
			respContent = respContent.substring(0, respContent.length()-1);
		}

		if (respContent.startsWith("\"")) {
			respContent = respContent.substring(1, respContent.length());
		}

		return respContent;
	}


	private void initparent(String processId) {
		//查询开场白节点
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1");
		List<BotSentenceDomain> list2 = botSentenceDomainMapper.selectByExample(example);

		for(int i = 0 ; i < list2.size() ; i++) {
			BotSentenceDomain domain = list2.get(i);
			if(StringUtils.isNotBlank(domain.getComDomain())) {
				//查询当前domain的下级节点，则设置下级节点的parent为当前节点
				BotSentenceDomainExample example3 = new BotSentenceDomainExample();
				example3.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andDomainNameEqualTo(domain.getComDomain());
				List<BotSentenceDomain> list3 = botSentenceDomainMapper.selectByExample(example3);
				if(null != list3 && list3.size() > 0) {
					for(BotSentenceDomain temp : list3) {
						temp.setParent(domain.getDomainName());
						temp.setParentId(domain.getDomainId());
						botSentenceDomainMapper.updateByPrimaryKey(temp);
					}
				}
			}
			if("start".equals(domain.getType())) {
				domain.setParent("root");
				domain.setParentId(domain.getDomainId());
				domain.setPositionX(300);
				domain.setPositionY(100);
				botSentenceDomainMapper.updateByPrimaryKey(domain);
			}
		}



	}


	//设置x和y坐标
	private void initPositive(List<BotSentenceDomain> domainList) {
		BotSentenceDomain domain = null;
		String processId = null;
		for(BotSentenceDomain temp : domainList) {
			if("start".equals(temp.getType())) {
				processId = temp.getProcessId();
				domain = temp;
				domain.setIsMainFlow("01");
				domain.setPositionX(350);
				domain.setPositionY(100);
				botSentenceDomainMapper.updateByPrimaryKey(domain);
			}
		}

		Map<String, BotSentenceDomain> map = new HashMap<>();

		List<String> mainDomainList = new ArrayList<>();

		int y = 100;
		int num = 0;
		while(StringUtils.isNotBlank(domain.getComDomain())) {
			if(num > 100) {
				logger.info("初始化坐标发生死循环，循环次数超过100>>>>>");
				throw new CommonException("初始化坐标发生死循环，循环次数超过100>>>>>");
			}
			num++;
			y = y + 200;
			BotSentenceDomainExample positionExample = new BotSentenceDomainExample();
			positionExample.createCriteria().andProcessIdEqualTo(domain.getProcessId()).andCategoryEqualTo("1").andDomainNameEqualTo(domain.getComDomain());
			List<BotSentenceDomain> positionList = botSentenceDomainMapper.selectByExample(positionExample);
			if(null != positionList && positionList.size() > 0) {
				domain = positionList.get(0);
				domain.setPositionX(350);
				domain.setPositionY(y);
				domain.setIsMainFlow("01");
				botSentenceDomainMapper.updateByPrimaryKey(domain);
				map.put(domain.getDomainName(), domain);

				mainDomainList.add(domain.getDomainName());
			}
		}

		//根据条件查询

		for(String temp : mainDomainList) {
			BotSentenceDomainExample positionExample = new BotSentenceDomainExample();
			positionExample.createCriteria().andProcessIdEqualTo(domain.getProcessId()).andCategoryEqualTo("1").andComDomainEqualTo(temp)
			.andPositionXIsNull().andPositionYIsNull();
			List<BotSentenceDomain> positionList = botSentenceDomainMapper.selectByExample(positionExample);
			if(null != positionList && positionList.size() > 0) {
				BotSentenceDomain upDomain = map.get(temp);
				BotSentenceDomain tempDomain = positionList.get(0);
				tempDomain.setPositionX(upDomain.getPositionX() + 350);
				tempDomain.setPositionY(upDomain.getPositionY() - 200);
				botSentenceDomainMapper.updateByPrimaryKey(tempDomain);
			}
		}


		BotSentenceDomainExample positionExample = new BotSentenceDomainExample();
		positionExample.createCriteria().andProcessIdEqualTo(domain.getProcessId()).andCategoryEqualTo("1")
		.andPositionXIsNull().andPositionYIsNull();
		List<BotSentenceDomain> positionList = botSentenceDomainMapper.selectByExample(positionExample);
		if(null != positionList && positionList.size() > 0) {
			for(BotSentenceDomain temp : positionList) {
				int temp_x = 800;
				int temp_y = 800;
				BotSentenceDomainExample comPositionExample = new BotSentenceDomainExample();
				comPositionExample.createCriteria().andProcessIdEqualTo(domain.getProcessId()).andCategoryEqualTo("1")
				.andPositionXIsNotNull().andPositionYIsNotNull().andComDomainEqualTo(temp.getComDomain());
				List<BotSentenceDomain> comPositionList = botSentenceDomainMapper.selectByExample(comPositionExample);
				if(null != comPositionList && comPositionList.size() > 0) {
					for(BotSentenceDomain temp2 : comPositionList) {
						temp_x = temp2.getPositionX() + 450;
						temp_y = temp2.getPositionY();
						if("01".equals(temp2.getIsMainFlow())){
							break;
						}
					}
				}

				temp.setPositionX(temp_x);
				temp.setPositionY(temp_y);
				botSentenceDomainMapper.updateByPrimaryKey(temp);
			}

		}

	}

	private void updateMainBranchName(String processId) {
		BotSentenceDomainExample positionExample = new BotSentenceDomainExample();
		positionExample.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andIsMainFlowEqualTo("01");
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(positionExample);
		for(BotSentenceDomain temp : domainList) {
			BotSentenceBranchExample example = new BotSentenceBranchExample();
			example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(temp.getDomainName()).andBranchNameEqualTo("positive");
			List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(example);
			if(null != branchList && branchList.size() > 0) {
				BotSentenceBranch branch = branchList.get(0);
				branch.setLineName("未拒绝");
				botSentenceBranchMapper.updateByPrimaryKey(branch);
			}
		}
	}


	private String setMainFlow(String domainName, List<BotSentenceDomain> domainList, BotSentenceDomain startExplainDomain) {

		for(int i = 0 ; i < domainList.size() ; i++) {
			BotSentenceDomain temp = domainList.get(i);

			if(domainName.equals(temp.getDomainName())) {

				if("解释开场白".equals(temp.getComDomain())) {//如果当前domain的下一节点为解释开场白，则修改当前节点指向下一节点，跳过解释开场白节点
					temp.setComDomain(startExplainDomain.getComDomain());
				}


				temp.setCategory("1");
				temp.setIsMainFlow("01");
				logger.info("当前domain: " + temp.getDomainName() + "为主流程...");
				return temp.getComDomain();
			}
		}
		return null;
	}

	private boolean validateTempateName(String templateName, String userId) {
		BotSentenceTemplateExample example = new BotSentenceTemplateExample();
		example.createCriteria().andTemplateTypeEqualTo("01").andTemplateNameEqualTo(templateName);
		int num = botSentenceTemplateMapper.countByExample(example);
		if(num > 0) {
			return false;
		}

		BotSentenceTemplateExample example1 = new BotSentenceTemplateExample();
		example1.createCriteria().andTemplateTypeEqualTo("02").andTemplateNameEqualTo(templateName).andAccountNoEqualTo(userId);
		int num1 = botSentenceTemplateMapper.countByExample(example1);
		if(num1 > 0) {
			return false;
		}


		return true;
	}


}
