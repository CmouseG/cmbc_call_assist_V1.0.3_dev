package com.guiji.botsentence.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.BotSentenceAdditionMapper;
import com.guiji.botsentence.dao.BotSentenceBranchMapper;
import com.guiji.botsentence.dao.BotSentenceDomainMapper;
import com.guiji.botsentence.dao.BotSentenceIndustryMapper;
import com.guiji.botsentence.dao.BotSentenceIntentMapper;
import com.guiji.botsentence.dao.BotSentenceKeywordTemplateMapper;
import com.guiji.botsentence.dao.BotSentenceLabelMapper;
import com.guiji.botsentence.dao.BotSentenceOptionsMapper;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.BotSentenceSurveyMapper;
import com.guiji.botsentence.dao.BotSentenceTemplateMapper;
import com.guiji.botsentence.dao.BotSentenceTradeMapper;
import com.guiji.botsentence.dao.VoliceInfoMapper;
import com.guiji.botsentence.dao.entity.BotSentenceBranch;
import com.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import com.guiji.botsentence.dao.entity.BotSentenceDomain;
import com.guiji.botsentence.dao.entity.BotSentenceDomainExample;
import com.guiji.botsentence.dao.entity.BotSentenceIntent;
import com.guiji.botsentence.dao.entity.BotSentenceIntentExample;
import com.guiji.botsentence.dao.entity.BotSentenceKeywordTemplate;
import com.guiji.botsentence.dao.entity.BotSentenceKeywordTemplateExample;
import com.guiji.botsentence.dao.entity.BotSentenceOptions;
import com.guiji.botsentence.dao.entity.BotSentenceOptionsExample;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import com.guiji.botsentence.dao.entity.BotSentenceSurveyExample;
import com.guiji.botsentence.dao.entity.BotSentenceTemplate;
import com.guiji.botsentence.dao.entity.BotSentenceTemplateExample;
import com.guiji.botsentence.dao.entity.BotSentenceTrade;
import com.guiji.botsentence.dao.entity.BotSentenceTradeExample;
import com.guiji.botsentence.dao.entity.VoliceInfo;
import com.guiji.botsentence.dao.entity.VoliceInfoExample;
import com.guiji.botsentence.service.impl.BotsentenceVariableServiceImpl;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.vo.InitValidateKeywordsVO;
import com.guiji.common.exception.CommonException;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.client.util.BeanUtil;
import com.guiji.component.client.util.NetFileDownUtil;
import com.guiji.user.dao.SysUserMapper;

@RestController
public class InitController {

	private org.slf4j.Logger  logger = LoggerFactory.getLogger(InitController.class);
	
	@Autowired
	BotSentenceTemplateMapper botSentenceTemplateMapper;
	
	@Autowired
	BotSentenceDomainMapper botSentenceDomainMapper;
	
	@Autowired
	BotSentenceBranchMapper botSentenceBranchMapper;
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Autowired
	private BotSentenceTradeMapper botSentenceTradeMapper;
	
	@Autowired
	private BotSentenceKeywordTemplateMapper botSentenceKeywordTemplateMapper;
	
	@Autowired
	private SysUserMapper userMapper;
	
	@Autowired
	private BotSentenceIndustryMapper botSentenceIndustryMapper;
	
	@Autowired
	private BotSentenceLabelMapper botSentenceLabelMapper;
	
	@Autowired
	private BotSentenceAdditionMapper botSentenceAdditionMapper;
	
	@Autowired
	private VoliceInfoMapper voliceInfoMapper;
	
	@Autowired
	private BotSentenceIntentMapper botSentenceIntentMapper;
	
	@Autowired
	private BotsentenceVariableServiceImpl botsentenceVariableService;
	
	@Autowired
	private BotSentenceOptionsMapper botSentenceOptionsMapper;
	
	@Autowired
	private BotSentenceSurveyMapper botSentenceSurveyMapper;
	
	@Value("${create.file.tmp}")
	private String tempPath;
	/*@RequestMapping(value="init/initLevel")
	public void initLevel() {
		
		BotSentenceTemplateExample example2 = new BotSentenceTemplateExample();
		//example2.createCriteria().andProcessIdEqualTo("3242");
		List<BotSentenceTemplate> list = botSentenceTemplateMapper.selectByExample(example2);
		for(BotSentenceTemplate template : list) {
			if("3425".equals(template.getProcessId())) {
				System.out.println();
			}
			
			BotSentenceDomainExample example = new BotSentenceDomainExample();
			example.createCriteria().andProcessIdEqualTo(template.getProcessId()).andCategoryEqualTo("1");
			
			List<BotSentenceDomain> list2 = botSentenceDomainMapper.selectByExample(example);
			Map<String, String> map = new HashMap<>();
			int maxLevel = 0;
			for(int i = 0 ; i < list2.size() ; i++) {
				if(!map.containsKey(list2.get(i).getComDomain())){
					maxLevel++;
					map.put(list2.get(i).getComDomain(), list2.get(i).getComDomain());
				}
			}
			
			//int maxLevel = botSentenceDomainMapper.countByExample(example);
			
			
			
			BotSentenceDomainExample maxLevelExample = new BotSentenceDomainExample();
			maxLevelExample.createCriteria().andProcessIdEqualTo(template.getProcessId()).andCategoryEqualTo("1").andComDomainIsNull();
			List<BotSentenceDomain> maxLevelList = botSentenceDomainMapper.selectByExample(maxLevelExample);
			if(null != maxLevelList && maxLevelList.size() > 0) {
				BotSentenceDomain temp = maxLevelList.get(0);
				temp.setLevel(maxLevel);
				botSentenceDomainMapper.updateByPrimaryKey(temp);
				
				List<BotSentenceDomain> selectlList = null;
						
				BotSentenceDomainExample selectExample = new BotSentenceDomainExample();
				selectExample.createCriteria().andProcessIdEqualTo(template.getProcessId()).andCategoryEqualTo("1").andComDomainEqualTo(temp.getDomainName());
				selectlList = botSentenceDomainMapper.selectByExample(selectExample);
				
				while(null != selectlList && selectlList.size() > 0) {
					maxLevel = maxLevel-1;
					
					List<String> domainNames = new ArrayList<>();
					
					for(int i = 0 ; i < selectlList.size() ; i++) {
						temp = selectlList.get(i);
						temp.setLevel(maxLevel);
						botSentenceDomainMapper.updateByPrimaryKey(temp);
						
						domainNames.add(temp.getDomainName());
					}
					
					selectExample = new BotSentenceDomainExample();
					selectExample.createCriteria().andProcessIdEqualTo(template.getProcessId()).andCategoryEqualTo("1").andComDomainIn(domainNames);
					selectlList = botSentenceDomainMapper.selectByExample(selectExample);
				}
				
			}
			
		}
	}*/
	
	
	
	@RequestMapping(value="init/initparent")
	@Transactional
	public void initparent() {
		
		BotSentenceTemplateExample example2 = new BotSentenceTemplateExample();
		//example2.createCriteria().andProcessIdEqualTo("3242");
		List<BotSentenceTemplate> list = botSentenceTemplateMapper.selectByExample(example2);
		for(BotSentenceTemplate template : list) {
//			if("2357".equals(template.getProcessId())) {
//				continue;
//			}
			
			//查询开场白节点
			BotSentenceDomainExample example = new BotSentenceDomainExample();
			example.createCriteria().andProcessIdEqualTo(template.getProcessId()).andCategoryEqualTo("1");
			List<BotSentenceDomain> list2 = botSentenceDomainMapper.selectByExample(example);
			
			for(int i = 0 ; i < list2.size() ; i++) {
				BotSentenceDomain domain = list2.get(i);
				if(StringUtils.isNotBlank(domain.getComDomain())) {
					//查询当前domain的下级节点，则设置下级节点的parent为当前节点
					BotSentenceDomainExample example3 = new BotSentenceDomainExample();
					example3.createCriteria().andProcessIdEqualTo(template.getProcessId()).andCategoryEqualTo("1").andDomainNameEqualTo(domain.getComDomain());
					List<BotSentenceDomain> list3 = botSentenceDomainMapper.selectByExample(example3);
					if(null != list3 && list3.size() > 0) {
						for(BotSentenceDomain temp : list3) {
							temp.setParent(domain.getDomainName());
							botSentenceDomainMapper.updateByPrimaryKey(temp);
						}
					}
				}
				if("start".equals(domain.getType())) {
					domain.setParent("root");
					botSentenceDomainMapper.updateByPrimaryKey(domain);
				}
			}
		}
	}
	
	
	
	//设置x和y坐标
	@RequestMapping(value="initPositive")
	@Transactional
	public void initPositive() {
		
		BotSentenceProcessExample processExample = new BotSentenceProcessExample();
		processExample.createCriteria().andLstUpdateUserEqualTo("nj11");
		List<BotSentenceProcess> processList = botSentenceProcessMapper.selectByExample(processExample);
		if(null != processList && processList.size() > 0) {
			for(BotSentenceProcess process : processList) {
				BotSentenceDomainExample domainExample = new BotSentenceDomainExample();
				domainExample.createCriteria().andProcessIdEqualTo(process.getProcessId());
				List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domainExample);
				if(null != domainList && domainList.size() > 0) {
					initDomain(domainList);
					initBranch(process.getProcessId(), domainList);
					//initLabel(process.getTemplateId(), process.getProcessId());
					//initAdditon(process.getTemplateId(), process.getProcessId());
					initRefuse(process.getProcessId(), process.getTemplateId());
				}
			}
		}
	}
	
	@Transactional
	public void initDomain(List<BotSentenceDomain> domainList) {

		BotSentenceDomain domain = null;
		for(BotSentenceDomain temp : domainList) {
			if("start".equals(temp.getType())) {
				domain = temp;
				domain.setIsMainFlow("01");
				domain.setCategory("1");
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
			logger.info("当前domain: " + domain.getDomainName() + "===" + domain.getProcessId());
			if(num > 50) {
				logger.info(domain.getComDomain() + "初始化坐标发生死循环，循环次数超过100>>>>>");
				throw new CommonException("初始化坐标发生死循环，循环次数超过50>>>>>");
			}
			num++;
			y = y + 200;
			BotSentenceDomainExample positionExample = new BotSentenceDomainExample();
			positionExample.createCriteria().andProcessIdEqualTo(domain.getProcessId()).andDomainNameEqualTo(domain.getComDomain());
			List<BotSentenceDomain> positionList = botSentenceDomainMapper.selectByExample(positionExample);
			if(null != positionList && positionList.size() > 0) {
				domain = positionList.get(0);
				domain.setPositionX(350);
				domain.setPositionY(y);
				domain.setCategory("1");
				domain.setIsMainFlow("01");
				botSentenceDomainMapper.updateByPrimaryKey(domain);
				map.put(domain.getDomainName(), domain);
				
				mainDomainList.add(domain.getDomainName());
			}else {
				break;
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
	
	@Transactional
	public void initBranch(String processId, List<BotSentenceDomain> domainList) {
		
		BotSentenceDomain startDomain = null;
		BotSentenceDomain startExplainDomain = null;
		
		List<String> mainDomainNameList = new ArrayList<>();
		
		for(BotSentenceDomain domain : domainList) {
			if("开场白".equals(domain.getDomainName())) {
				startDomain = domain;
			}
			if("解释开场白".equals(domain.getDomainName())) {
				startExplainDomain = domain;
			}
			
			if("1".equals(domain.getCategory())) {
				mainDomainNameList.add(domain.getDomainName());
			}
			
		}
		
		//如果开场白下一节点是解释开场白，则修改开场白指向解释开场白的下级节点
		boolean flag = false;
		if(null != startDomain && null != startExplainDomain) {
			if("解释开场白".equals(startDomain.getComDomain())) {
				flag = true;
				startDomain.setComDomain(startExplainDomain.getComDomain());
			}
		}
		

		BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
		branchExample.createCriteria().andProcessIdEqualTo(processId).andDomainIn(mainDomainNameList);
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchExample);
		
		for(BotSentenceBranch botSentenceBranch:branchList) {
			
			
			//设置branch的line
			if (mainDomainNameList.contains(botSentenceBranch.getNext()) && mainDomainNameList.contains(botSentenceBranch.getDomain())){
				if("positive".equals(botSentenceBranch.getBranchName())) {
					botSentenceBranch.setLineName("未拒绝");
					botSentenceBranch.setType(Constant.BRANCH_TYPE_POSITIVE);
					botSentenceBranch.setIsShow("1");
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
			botSentenceBranchMapper.updateByPrimaryKey(botSentenceBranch);
		}
		
	}
	
	
	
	@Transactional
	public void initRefuse(String processId, String template_id) {
		//创建流程时，默认创建一条挽回话术池--取domain为拒绝的negative数据
		BotSentenceBranchExample refuseExample = new BotSentenceBranchExample();
		refuseExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("拒绝").andBranchNameEqualTo("negative");
		List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(refuseExample);
		if(null != list && list.size() > 0) {
			BotSentenceBranch refuse = list.get(0);
			if(StringUtils.isNotBlank(refuse.getResponse()) && !"[]".equals(refuse.getResponse())) {
				String[] respArray = refuse.getResponse().substring(1,refuse.getResponse().length()-1).split(",");
				VoliceInfo oldVolice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				if(null != oldVolice) {
					VoliceInfo volice = new VoliceInfo();
					volice.setType(Constant.VOLICE_TYPE_REFUSE);//挽回话术
					volice.setContent(oldVolice.getContent());
					volice.setVoliceUrl(oldVolice.getVoliceUrl());
					volice.setCrtTime(new Date(System.currentTimeMillis()));
					volice.setCrtUser("import");
					volice.setProcessId(processId);
					volice.setTemplateId(template_id);
					volice.setName("默认挽回话术");
					voliceInfoMapper.insert(volice);
				}
			}
		}
		
		//创建当前domain对应的挽回话术列表
		BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
		branchExample.createCriteria().andProcessIdEqualTo(processId).andBranchNameLike("refuse_%");
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchExample);
		
		for(BotSentenceBranch botSentenceBranch:branchList) {
			String response = botSentenceBranch.getResponse();
			if(StringUtils.isNotBlank(response) && response.length() > 2) {
				String[] respArray = response.substring(1,response.length()-1).split(",");
				if(null != respArray && respArray.length > 0) {
					String respnameArray[] = null;
					if(StringUtils.isBlank(botSentenceBranch.getRespname()) || "[]".equals(botSentenceBranch.getRespname())) {
						respnameArray = new String[respArray.length];
						for(int i = 0 ; i < respArray.length ; i++) {
							respnameArray[i] = botSentenceBranch.getDomain() + "-挽回" + (i+1);
						}
						
					}else {
						respnameArray = botSentenceBranch.getRespname().substring(1,botSentenceBranch.getRespname().length()-1).split(",");
					}
					
					//新增挽回话术
					for(int i = 0 ; i < respArray.length ; i++) {
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[i]));
						//把当前挽回话术添加到挽回话术池
						/*VoliceInfo new_volice = new VoliceInfo();
						new_volice.setType(Constant.VOLICE_TYPE_REFUSE);//挽回话术
						new_volice.setContent(volice.getContent());
						new_volice.setVoliceUrl(volice.getVoliceUrl());
						new_volice.setCrtTime(new Date(System.currentTimeMillis()));
						new_volice.setCrtUser("import");
						new_volice.setProcessId(processId);
						new_volice.setTemplateId(template_id);
						new_volice.setName(respnameArray[i]);
						voliceInfoMapper.insert(new_volice);*/
						
						if(null != volice) {
							BotSentenceBranch new_branch = new BotSentenceBranch();
							BeanUtil.copyProperties(botSentenceBranch, new_branch);
							new_branch.setResponse("[" + respArray[i] +"]");
							new_branch.setRespname(null);
							new_branch.setCrtUser("import");
							botSentenceBranchMapper.insert(new_branch);
							
							//更新volice的name字段
							volice.setType(Constant.VOLICE_TYPE_REFUSE);//挽回话术池
							volice.setName(respnameArray[i].replace("\"", ""));
							voliceInfoMapper.updateByPrimaryKey(volice);
						}
					}
						
					
					//删除当前挽回话术
					botSentenceBranchMapper.deleteByPrimaryKey(botSentenceBranch.getBranchId());
				}
				
			}
			
		}
	}
	
	//初始化意图和录音信息
	@RequestMapping(value="initIntentAndVolice")
	@Transactional
	public void initIntentAndVolice() {
		
		BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
		branchExample.createCriteria().andSeqEqualTo(0L);
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchExample);
		
		if(null != branchList && branchList.size() > 0) {
			
			//设置意图
			BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
			intentExample.createCriteria().andOldIdIsNotNull();
			List<BotSentenceIntent> list = botSentenceIntentMapper.selectByExample(intentExample);
			Map<String,String> intentMap = new HashMap<>();
			if(null != list && list.size() > 0) {
				for(BotSentenceIntent intent : list) {
					intentMap.put(intent.getOldId().toString(), intent.getId().toString());
				}
			}
			
			//设置录音 
			VoliceInfoExample voliceInfoExample = new VoliceInfoExample();
			voliceInfoExample.createCriteria().andOldIdIsNotNull();
			List<VoliceInfo> voliceList = voliceInfoMapper.selectByExample(voliceInfoExample);
			Map<String,String> voliceMap = new HashMap<>();
			if(null != voliceList && voliceList.size() > 0) {
				for(VoliceInfo volice : voliceList) {
					voliceMap.put(volice.getOldId().toString(), volice.getVoliceId().toString());
				}
			}
			
			
			for(BotSentenceBranch branch : branchList) {
				//更新意图id
				String oldIntents = branch.getIntents();
				if(StringUtils.isNotBlank(oldIntents)) {
					String [] oldIntentArray = oldIntents.split(",");
					String newIntents = "";
					for(int i = 0 ; i < oldIntentArray.length ; i++) {
						if(null != intentMap.get(oldIntentArray[i])) {
							if(i == oldIntentArray.length -1) {
								newIntents = newIntents + intentMap.get(oldIntentArray[i]);
							}else {
								newIntents = newIntents + intentMap.get(oldIntentArray[i]) + ",";
							}
						}
					}
					
					branch.setIntents(newIntents);//更新为新的意图id
				}
				
				
				//更新录音id
				String oldResp = branch.getResponse();
				if(StringUtils.isNotBlank(oldResp) && !"[]".equals(oldResp) && oldResp.startsWith("[") && oldResp.endsWith("]")) {
					String[] oldRespArray = oldResp.substring(1,oldResp.length()-1).split(",");
					String newResp = "[";
					for(int i = 0 ; i < oldRespArray.length ; i++) {
						if(null != voliceMap.get(oldRespArray[i])) {
							if(i == oldRespArray.length -1) {
								newResp = newResp + voliceMap.get(oldRespArray[i]);
							}else {
								newResp = newResp + voliceMap.get(oldRespArray[i]) + ",";
							}
						}
					}
					newResp = newResp + "]";
					branch.setResponse(newResp);//更新为新的录音 id
				}
				branch.setSeq(1L);//表示已经处理过的数据
				botSentenceBranchMapper.updateByPrimaryKey(branch);
			}
		}
	}
	
	
	public static void main(String[] args) {
		String aa = MD5Encoder.encode("http://192.168.1.208:8888".getBytes());
		System.out.println(aa);
		
	}
	
	
	@Transactional
	@RequestMapping(value="initImportRefuse")
	public void initImportRefuse() {

		String [] array = {"xtyhysfhs_88717_en",
				"jhxc_19132_en",
				"dk_77483_en",
				"bdfjy_60137_en",
				"sdqc_79991_en",
				"wxcx_28989_en",
				"llsd_30836_en",
				"jx_71458_en",
				"sbhs_11810_en",
				//"nnlljy_56001_en",
				"yzlljy_27106_en",
				"kxdk_41121_en",
				"ydbxlskd_15444_en",
				"kzgj_50153_en",
				"bgy_78091_en",
				"1_30884_en",
				"ydbxlskd2_26570_en",
				"pxjy_63869_en",
				"jjjd_34969_en",
				"fsdwk_47457_en",
				"ybgj_66067_en",
				"fssswk_80837_en",
				"xyjhkjhs_72170_en",
				"jsjzs_75666_en",
				"cskj888_50013_en",
				"lyag_81548_en",
				"fssswk_86327_en",
				"haxqslc_53675_en",
				"qdyd_50597_en",
				"xzhj_96989_en",
				"bhtxszp_61553_en",
				"bhtjqrzs_44639_en",
				"ljrpalc_58973_en",
				"123_49587_en",
				"cyyd_26595_en",
				"brzcrjy_37428_en",
				"bhtjqrzs2_44404_en",
				"dklx_71314_en",
				"cjzq_23353_en",
				"msyxxyktg_66764_en",
				"smrhcgp_79109_en",
				"xldhs_12091_en",
				"ths_66629_en",
				"bybp1_26711_en",
				"zxmb1_61502_en",
				"tz_71035_en",
				"zqbgy_38422_en",
				"bybp2_53870_en",
				"lljy_72224_en",
				"lcrcfc2_89239_en",
				"rclcfc_49356_en",
				"fdy_17803_en",
				"glgc_32541_en",
				"glgc_90864_en"};
		
		
		//String [] array2 = {"nnlljy_56001_en"};
		
		for(int i = 0 ; i < array.length ; i++) {
			
			
			BotSentenceProcessExample processExample = new BotSentenceProcessExample();
			processExample.createCriteria().andTemplateIdEqualTo(array[i]);
			List<BotSentenceProcess> processList = botSentenceProcessMapper.selectByExample(processExample);
			String processId = processList.get(0).getProcessId();
			logger.info("当前模板名称: " + processList.get(0).getTemplateName());
			
			//创建流程时，默认创建一条挽回话术池--取domain为拒绝的negative数据
			BotSentenceBranchExample refuseExample2 = new BotSentenceBranchExample();
			refuseExample2.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("拒绝").andBranchNameEqualTo("negative");
			List<BotSentenceBranch> list2 = botSentenceBranchMapper.selectByExample(refuseExample2);
			if(null != list2 && list2.size() > 0) {
				BotSentenceBranch refuse = list2.get(0);
				if(StringUtils.isNotBlank(refuse.getResponse()) && !"[]".equals(refuse.getResponse())) {
					String[] respArray = refuse.getResponse().substring(1,refuse.getResponse().length()-1).split(",");
					VoliceInfo oldVolice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
					if(null != oldVolice) {
						VoliceInfo volice = new VoliceInfo();
						volice.setType(Constant.VOLICE_TYPE_REFUSE);//挽回话术
						volice.setContent(oldVolice.getContent());
						volice.setVoliceUrl(oldVolice.getVoliceUrl());
						volice.setCrtTime(new Date(System.currentTimeMillis()));
						volice.setCrtUser("import");
						volice.setProcessId(processId);
						volice.setTemplateId(array[i]);
						volice.setName("默认挽回话术");
						voliceInfoMapper.insert(volice);
					}
				}
			}
			
			
			BotSentenceBranchExample refuseExample = new BotSentenceBranchExample();
			refuseExample.createCriteria().andProcessIdEqualTo(processId).andBranchNameLike("refuse_%");
			List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(refuseExample);
			
			Map<String, Integer> map = new HashMap<>();
			
			if(null != list && list.size() > 0) {
				for(BotSentenceBranch refuse : list) {
					if(map.containsKey(refuse.getDomain())) {
						int num = map.get(refuse.getDomain()) + 1;
						map.put(refuse.getDomain(), num);
					}else {
						map.put(refuse.getDomain(), 1);
					}
					
					if(StringUtils.isNotBlank(refuse.getResponse()) && !"[]".equals(refuse.getResponse())) {
						String[] respArray = refuse.getResponse().substring(1,refuse.getResponse().length()-1).split(",");
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
						if(null != volice) {
							volice.setType(Constant.VOLICE_TYPE_REFUSE);//挽回话术
							
							volice.setName(refuse.getDomain() + "-挽回" + map.get(refuse.getDomain()));
							voliceInfoMapper.updateByPrimaryKey(volice);
						}
					}
				}
				
			}
			
		}
	}
	
	
	@RequestMapping(value="initMediaDuration")
	public void initMediaDuration(@JsonParam String processId, @JsonParam String type) throws IOException {
		int num = 0 ;
		if("all".equals(type)) {
			VoliceInfoExample example = new VoliceInfoExample();
			example.createCriteria().andVoliceUrlIsNotNull().andTimesIsNull();
			example.setOffset(0L);
			example.setLimit(5000);
			List<VoliceInfo>  list = voliceInfoMapper.selectByExample(example);
			File dir = new File(tempPath);
			if(null != list && list.size() > 0) {
				for(VoliceInfo temp : list) {
					File file = null;
					try {
						java.util.Random random = new java.util.Random();
						long aa = random.nextLong();
						file = File.createTempFile(aa + "-" + String.valueOf(System.currentTimeMillis()), "wav", dir);
						NetFileDownUtil util = new NetFileDownUtil(temp.getVoliceUrl(), file);
						util.downfile();
						int times = BotSentenceUtil.getVideoTime(file.getPath());
						temp.setTimes(times);
						voliceInfoMapper.updateByPrimaryKey(temp);
						file.delete();
						logger.info(temp.getVoliceId() + "  更新录音时长成功: " + times);
					}catch(Exception e) {
						logger.error("录音计算异常: " + temp.getVoliceId());
					}finally {
						if(null != file) {
							file.delete();
						}
					}
					num++;
					logger.info("当前数量: "+num);
				}
			}
		}else {
			if(StringUtils.isNotBlank(processId)) {
				
				VoliceInfoExample example = new VoliceInfoExample();
				example.createCriteria().andProcessIdEqualTo(processId);
				List<VoliceInfo>  list = voliceInfoMapper.selectByExample(example);
				File dir = new File(tempPath);
				if(null != list && list.size() > 0) {
					for(VoliceInfo temp : list) {
						File file = null;
						try {
							java.util.Random random = new java.util.Random();
							long aa = random.nextLong();
							file = File.createTempFile(aa + "-" + String.valueOf(System.currentTimeMillis()), "wav", dir);
							NetFileDownUtil util = new NetFileDownUtil(temp.getVoliceUrl(), file);
							util.downfile();
							int times = BotSentenceUtil.getVideoTime(file.getPath());
							temp.setTimes(times);
							voliceInfoMapper.updateByPrimaryKey(temp);
							file.delete();
							logger.info(temp.getVoliceId() + "  更新录音时长成功: " + times);
						}catch(Exception e) {
							logger.error("录音计算异常: " + temp.getVoliceId());
						}finally {
							if(null != file) {
								file.delete();
							}
						}
					}
				}
			
			
		}
		}
	}
	
	@RequestMapping(value="validateRepeatKeyword")
	public void validateRepeatKeyword() {
		List<String> list = new ArrayList<>();
		List<String> messageList = new ArrayList<>();
		Map<String, List<String>> map = new LinkedHashMap<>();
		//查询所有三级行业
		BotSentenceTradeExample tradeExample = new BotSentenceTradeExample();
		tradeExample.createCriteria().andLevelEqualTo(3);
		List<BotSentenceTrade> tradeList = botSentenceTradeMapper.selectByExample(tradeExample);
		
		
		BotSentenceTrade current = new BotSentenceTrade();
		current.setIndustryId("currency");
		tradeList.add(current);
		
		for(BotSentenceTrade trade : tradeList) {
			//根据行业查询关键词
			BotSentenceKeywordTemplateExample keywordTempalte = new BotSentenceKeywordTemplateExample();
			keywordTempalte.createCriteria().andIndustryIdEqualTo(trade.getIndustryId());
			List<BotSentenceKeywordTemplate> templateList = botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(keywordTempalte);
			
			String message = "";
			
			Map<String, InitValidateKeywordsVO> numMap = new HashMap<>();
			
			
	        if(templateList!=null && templateList.size() > 0){
	        	for(BotSentenceKeywordTemplate temp : templateList) {
	        		String tempKeywords = temp.getKeywords();
	        		if(StringUtils.isNotBlank(tempKeywords)) {
	        			tempKeywords = tempKeywords.substring(1, tempKeywords.length() - 1);
		        		tempKeywords = BotSentenceUtil.generateShowKeywords(tempKeywords);
		        		List<String> industryKeywords = BotSentenceUtil.StringToList(tempKeywords);
		        		for(String keyword : industryKeywords) {
		        			InitValidateKeywordsVO vo =  null;
		        			
		        			if(numMap.containsKey(keyword)) {
		        				vo =  numMap.get(keyword);
		        				int num = vo.getNum();
		        				vo.setNum(num+1);
		        				List<String> intentNameList = vo.getIntentNameList();
		        				intentNameList.add(temp.getIntentName());
		        				vo.setIntentNameList(intentNameList);
		        				vo.setId(temp.getId());
		        			}else {
		        				vo = new InitValidateKeywordsVO();
		        				vo.setNum(1);
		        				vo.setParendTradeName(trade.getParentName());
		        				vo.setTradeName(temp.getIndustryName());
		        				List<String> intentNameList = new ArrayList<>();
		        				intentNameList.add(temp.getIntentName());
		        				vo.setIntentNameList(intentNameList);
		        				vo.setId(temp.getId());
		        			}
		        			
		        			numMap.put(keyword, vo);
		        		}
	        		}
	        	}
	        }
	        Set<String> set = numMap.keySet();
			for(String str : set) {
				InitValidateKeywordsVO vo = numMap.get(str);
				if(vo.getNum() > 1) {
					message = "关键词: [" + str + "] " + vo.getParendTradeName() + "--" + vo.getTradeName() + "--" + BotSentenceUtil.listToString(vo.getIntentNameList());
					messageList.add(message);
					
					//删除多余的关键词
					//查询相应的行业下的发生重复关键词的意图
					//BotSentenceKeywordTemplate temp = botSentenceKeywordTemplateMapper.selectByPrimaryKey(vo.getId());
					
				}
			}
		}
		
		
		
		for(String temp : messageList){
			System.out.println(temp);
		}
	}
	
	
	
	@RequestMapping(value="dealRepeatKeyword")
	public void repeatKeyword() {
			//根据行业查询关键词
			BotSentenceKeywordTemplateExample keywordTempalte = new BotSentenceKeywordTemplateExample();
			List<BotSentenceKeywordTemplate> templateList = botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(keywordTempalte);
			
        	for(BotSentenceKeywordTemplate temp : templateList) {
        		//if(9028== temp.getId()) {
        			List<String> newkeywords = new ArrayList<>();
            		
            		String tempKeywords = temp.getKeywords();
            		if(StringUtils.isNotBlank(tempKeywords)) {
            			tempKeywords = tempKeywords.substring(1, tempKeywords.length() - 1);
    	        		tempKeywords = BotSentenceUtil.generateShowKeywords(tempKeywords);
    	        		List<String> industryKeywords = BotSentenceUtil.StringToList(tempKeywords);
    	        		for(String keyword : industryKeywords) {
    	        			if(newkeywords.contains(keyword)) {
    	        				logger.info(keyword+ "   重复");
    	        			}else {
    	        				newkeywords.add(keyword);
    	        			}
    	        		}
            		}
            		
            		//更新关键词
            		String keywords = BotSentenceUtil.generateKeywords(BotSentenceUtil.listToString(newkeywords));
            		temp.setKeywords(keywords);
            		botSentenceKeywordTemplateMapper.updateByPrimaryKeyWithBLOBs(temp);
        		//}
        		
        	}
	}

	
	@RequestMapping(value="initVariable")
	public void initVariable(@RequestHeader String userId) {
		BotSentenceProcessExample example = new BotSentenceProcessExample();
		//example.createCriteria().andProcessIdEqualTo("20190119PRO00015161126");
		List<BotSentenceProcess> list = botSentenceProcessMapper.selectByExample(example);
		for(BotSentenceProcess process : list) {
			BotSentenceOptionsExample optionExample = new BotSentenceOptionsExample();
			optionExample.createCriteria().andProcessIdEqualTo(process.getProcessId());
			int num1 = botSentenceOptionsMapper.countByExample(optionExample);
			if(num1 == 0) {
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
			}
			
			BotSentenceSurveyExample surveyExample = new BotSentenceSurveyExample();
			surveyExample.createCriteria().andProcessIdEqualTo(process.getProcessId());
			int num2 = botSentenceSurveyMapper.countByExample(surveyExample);
			if(num2 == 0) {
				//初始化回访数据
				BotSentenceDomainExample domainExample = new BotSentenceDomainExample();
				domainExample.createCriteria().andProcessIdEqualTo(process.getProcessId()).andCategoryEqualTo(Constant.CATEGORY_TYPE_1);
				List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domainExample);
				for(BotSentenceDomain domain : domainList) {
					botsentenceVariableService.initDomainSurveyIntent(process.getProcessId(), domain.getDomainName(), process.getTemplateId(), userId);
				}
			}
		}
	}
	
}