package ai.guiji.botsentence.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.Logger;
import com.mysql.fabric.xmlrpc.base.Array;

import ai.guiji.botsentence.constant.Constant;
import ai.guiji.botsentence.controller.server.vo.ImportLabelVO;
import ai.guiji.botsentence.crm.dao.TabAdministratorMapper;
import ai.guiji.botsentence.crm.dao.TabCustomerMapper;
import ai.guiji.botsentence.crm.dao.TabIndustryMapper;
import ai.guiji.botsentence.crm.dao.TabTempMapper;
import ai.guiji.botsentence.crm.dao.entity.TabAdministrator;
import ai.guiji.botsentence.crm.dao.entity.TabAdministratorExample;
import ai.guiji.botsentence.crm.dao.entity.TabCustomer;
import ai.guiji.botsentence.crm.dao.entity.TabCustomerExample;
import ai.guiji.botsentence.crm.dao.entity.TabIndustry;
import ai.guiji.botsentence.crm.dao.entity.TabIndustryExample;
import ai.guiji.botsentence.crm.dao.entity.TabTemp;
import ai.guiji.botsentence.crm.dao.entity.TabTempExample;
import ai.guiji.botsentence.dao.BotSentenceAdditionMapper;
import ai.guiji.botsentence.dao.BotSentenceBranchMapper;
import ai.guiji.botsentence.dao.BotSentenceDomainMapper;
import ai.guiji.botsentence.dao.BotSentenceIndustryMapper;
import ai.guiji.botsentence.dao.BotSentenceIntentMapper;
import ai.guiji.botsentence.dao.BotSentenceLabelMapper;
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.BotSentenceTemplateMapper;
import ai.guiji.botsentence.dao.UserAccountIndustryRelationMapper;
import ai.guiji.botsentence.dao.UserAccountMapper;
import ai.guiji.botsentence.dao.VoliceInfoMapper;
import ai.guiji.botsentence.dao.entity.BotSentenceAddition;
import ai.guiji.botsentence.dao.entity.BotSentenceBranch;
import ai.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import ai.guiji.botsentence.dao.entity.BotSentenceDomain;
import ai.guiji.botsentence.dao.entity.BotSentenceDomainExample;
import ai.guiji.botsentence.dao.entity.BotSentenceIndustry;
import ai.guiji.botsentence.dao.entity.BotSentenceIndustryExample;
import ai.guiji.botsentence.dao.entity.BotSentenceIntent;
import ai.guiji.botsentence.dao.entity.BotSentenceIntentExample;
import ai.guiji.botsentence.dao.entity.BotSentenceLabel;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import ai.guiji.botsentence.dao.entity.BotSentenceTemplate;
import ai.guiji.botsentence.dao.entity.BotSentenceTemplateExample;
import ai.guiji.botsentence.dao.entity.UserAccount;
import ai.guiji.botsentence.dao.entity.UserAccountIndustryRelation;
import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.entity.VoliceInfoExample;
import ai.guiji.component.client.util.BeanUtil;
import ai.guiji.component.exception.CommonException;
import ai.guiji.user.dao.SysUserMapper;
import ai.guiji.user.dao.SysUserRoleMapper;
import ai.guiji.user.dao.entity.SysUser;
import ai.guiji.user.dao.entity.SysUserExample;
import ai.guiji.user.dao.entity.SysUserRole;

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
	TabAdministratorMapper tabAdministratorMapper;
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Autowired
	TabCustomerMapper tabCustomerMapper;
	
	@Autowired
	private UserAccountMapper userAccountMapper;
	
	@Autowired
	private UserAccountIndustryRelationMapper relationMapper;
	
	@Autowired
	private SysUserMapper userMapper;
	
	@Autowired
	private SysUserRoleMapper userRoleMapper;
	
	@Autowired
	private BotSentenceIndustryMapper botSentenceIndustryMapper;
	
	@Autowired
	private TabIndustryMapper tabIndustryMapper;
	
	@Autowired
	private UserAccountIndustryRelationMapper userAccountIndustryRelationMapper;
	
	@Autowired
	private TabTempMapper tabTempMapper;
	
	@Autowired
	private BotSentenceLabelMapper botSentenceLabelMapper;
	
	@Autowired
	private BotSentenceAdditionMapper botSentenceAdditionMapper;
	
	@Autowired
	private VoliceInfoMapper voliceInfoMapper;
	
	@Autowired
	private BotSentenceIntentMapper botSentenceIntentMapper;
	
	
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
	
	@RequestMapping(value="initUser")
	@Transactional
	public void initUser(){
		
		Map<String, String> industryMap = new HashMap<>();
		
		//初始化行业数据
		List<TabIndustry> tabIndustryList = tabIndustryMapper.selectByExample(new TabIndustryExample());
		
		if(null != tabIndustryList && tabIndustryList.size() > 0 ) {
			for(TabIndustry temp : tabIndustryList) {
				
				industryMap.put(temp.getId()+"", temp.getName());
				
				//判断行业是否存在，如果存在，则不保存
				/*BotSentenceIndustryExample example = new BotSentenceIndustryExample();
				example.createCriteria().andIndustryNameEqualTo(temp.getName());
				int num = botSentenceIndustryMapper.countByExample(example);
				if(num < 1) {
					BotSentenceIndustry industry = new BotSentenceIndustry();
					industry.setIndustryName(temp.getName());
					industry.setCrtTime(new Date(System.currentTimeMillis()));
					industry.setCrtUser("init");
					botSentenceIndustryMapper.insert(industry);
					logger.info("保存行业: " + temp.getName());
				}else {
					logger.info("行业【" + temp.getName() + "】已存在...");
				}*/
				
			}
		}
		
		
		TabAdministratorExample example = new TabAdministratorExample();
		List<TabAdministrator> userList = tabAdministratorMapper.selectByExample(example);
		if(null != userList && userList.size() > 0) {
			for(TabAdministrator temp : userList) {
				
				//判断该用户是否已经存在，如果存在，则不处理
				/*SysUserExample userExample = new SysUserExample();
				userExample.createCriteria().andUseridEqualTo(temp.getUsername());
				int num = userMapper.countByExample(userExample);
				
				if(num > 0) {
					logger.info("当前用户【" + temp.getUsername() + "】已存在>>>>>>>");
					continue;
				}
				
				//保存用户信息
				SysUser user = new SysUser();
				user.setUserid(temp.getUsername());
				user.setName(temp.getUsername());
				user.setPassword(Constant.DEFAULT_PASSWORD);
				user.setTokenPassword(Constant.DEFAULT_PASSWORD);
				user.setEmail("init");
				user.setCreateTime(new Date(System.currentTimeMillis()));
				userMapper.insert(user);
				
				//创建用户角色
				SysUserRole role = new SysUserRole();
				if(3==temp.getGroupid()) {//制作话术权限
					role.setRoleId("001");
				}else if(1 == temp.getGroupid()) {//审核权限
					role.setRoleId("002");
				}
				role.setUserId(temp.getUsername());
				role.setCreateTime(new Date(System.currentTimeMillis()));
				role.setCreateBy("init");
				userRoleMapper.insert(role);
				
				//保存账号相关信息
				TabCustomer tabCustomer = tabCustomerMapper.selectByPrimaryKey(temp.getCustomerId());
				if(null != tabCustomer) {
					UserAccount userAccount = new UserAccount();
					userAccount.setMachineCode(tabCustomer.getMachineCode());
					userAccount.setAccountNo(temp.getUsername());
					userAccount.setCrtTime(new Date(System.currentTimeMillis()));
					userAccount.setCrtUser("init");
					userAccount.setHost(temp.getUsername().split("-")[0]);
					userAccountMapper.insert(userAccount);
				}*/
				
				//保存账号与行业关系
				if(StringUtils.isNotBlank(temp.getIndustrytemp())) {
					String [] industry_array = temp.getIndustrytemp().split(",");
					for(int i = 0 ; i < industry_array.length ; i++) {
						UserAccountIndustryRelation relation = new UserAccountIndustryRelation();
						
						System.out.println("industry_id==" + industry_array[i]);
						
						if(StringUtils.isNotBlank(industry_array[i])) {
							String industryName = industryMap.get(industry_array[i]);
							
							if(StringUtils.isNotBlank(industryName)) {
								//获取数据库已保存的行业信息
								/*BotSentenceIndustryExample industryExample = new BotSentenceIndustryExample();
								industryExample.createCriteria().andIndustryNameEqualTo(industryName);
								List<BotSentenceIndustry> industryList = botSentenceIndustryMapper.selectByExample(industryExample);
								if(null != industryList && industryList.size() > 0) {
									relation.setIndustryId(industryList.get(0).getIndustryId()+"");
									relation.setIndustryName(industryList.get(0).getIndustryName());
								}*/
								
								BotSentenceTemplateExample templateExample = new BotSentenceTemplateExample();
								templateExample.createCriteria().andTemplateNameEqualTo(industryName);
								List<BotSentenceTemplate>  list = botSentenceTemplateMapper.selectByExample(templateExample);
								if(null != list && list.size() > 0) {
									relation.setIndustryId(list.get(0).getProcessId()+"");
									relation.setIndustryName(list.get(0).getTemplateName());
									relation.setAccountNo(temp.getUsername());
									relation.setAccountName(temp.getUsername());
									relation.setCrtTime(new Date(System.currentTimeMillis()));
									relation.setCrtUser("init");
									userAccountIndustryRelationMapper.insert(relation);
								}
							}
						}
					}
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
					initLabel(process.getTemplateId(), process.getProcessId());
					initAdditon(process.getTemplateId(), process.getProcessId());
					initRefuse(process.getProcessId(), process.getTemplateId());
				}
			}
		}
	}
	
	@Transactional
	private void initDomain(List<BotSentenceDomain> domainList) {

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
	private void initBranch(String processId, List<BotSentenceDomain> domainList) {
		
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
	private void initLabel(String template_id, String processId) {
		TabTempExample example = new TabTempExample();
		example.createCriteria().andKeyStrEqualTo(template_id);
		List<TabTemp> list = tabTempMapper.selectByExampleWithBLOBs(example);
		if(null != list && list.size() > 0) {
			TabTemp temp = list.get(0);
			String labels = temp.getUserLabelRules();
			
			if(StringUtils.isNotBlank(labels)) {

				JSONObject statJSONObject = JSON.parseObject(labels);
				for (Entry<String, Object> entry : statJSONObject.entrySet()) {
					String labelName = entry.getKey();
					
					if(labelName.equals("A") || labelName.equals("B") || labelName.equals("C") || labelName.equals("D") ||
							labelName.equals("E")) {
						JSONObject jSONObject = (JSONObject) entry.getValue();
						

						
						ImportLabelVO importLabelVO = JSON.parseObject(jSONObject.toJSONString(), ImportLabelVO.class);
						
						
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
						botSentenceLabel.setCrtTime(new Date(System.currentTimeMillis()));
						botSentenceLabel.setCrtUser("import");
						botSentenceLabelMapper.insertSelective(botSentenceLabel);
					}
					
				}
			}
		}
	}
	
	@Transactional
	private void initAdditon(String template_id, String processId) {
		
		TabTempExample example = new TabTempExample();
		example.createCriteria().andKeyStrEqualTo(template_id);
		List<TabTemp> list = tabTempMapper.selectByExampleWithBLOBs(example);
		if(null != list && list.size() > 0) {
			TabTemp temp = list.get(0);
			String simTxt = "";
			String options = temp.getOptions();
			if(null != temp.getSimDict()) {
				simTxt = new String(temp.getSimDict());
			}
			
			String stopwordsTxt= ",\r\n" + 
					"?\r\n" + 
					"、\r\n" + 
					"。\r\n" + 
					"“\r\n" + 
					"”\r\n" + 
					"《\r\n" + 
					"》\r\n" + 
					"！\r\n" + 
					"，\r\n" + 
					"：\r\n" + 
					"；\r\n" + 
					"？\r\n" + 
					"的\r\n" + 
					"吗\r\n" + 
					"吧\r\n" + 
					"呀\r\n" + 
					"了\r\n" + 
					"嘛";
			String weightsTxt = "keyword,weight\r\n" + 
					"贷款,100\r\n" + 
					"落户,100\r\n" + 
					"生意,100\r\n" + 
					"回本\r\n" + 
					"注册公司,100\r\n" + 
					"可以,50\r\n" + 
					"楼距,100\r\n" + 
					"多大,50\r\n" + 
					"哦,1";
			//String templateJson = temp.get
			
			String userdictTxt = "哦 100\r\n" + 
					"电话忙 500\r\n" + 
					"首付 500\r\n" + 
					"免贵 500\r\n" + 
					"不需要 500\r\n" + 
					"从哪 500\r\n" + 
					"从哪儿 500\r\n" + 
					"从哪里 500\r\n" + 
					"不要 500\r\n" + 
					"楼盘面积 500\r\n" + 
					"不要再打 500\r\n" + 
					"我很忙 500\r\n" + 
					"多大面积 500\r\n" + 
					"真的吗 500\r\n" + 
					"不知道 500\r\n" + 
					"别烦 500\r\n" + 
					"多少钱 500\r\n" + 
					"多高 500\r\n" + 
					"没时间 500\r\n" + 
					"怎么去 500\r\n" + 
					"好的 500\r\n" + 
					"多大 500\r\n" + 
					"可以优惠 500\r\n" + 
					"在哪 500\r\n" + 
					"多少年 500\r\n" + 
					"拿地 500\r\n" + 
					"不清楚 500\r\n" + 
					"不便宜 500\r\n" + 
					"不太清楚 500\r\n" + 
					"不贵 500\r\n" + 
					"多少平 500\r\n" + 
					"好贵 500\r\n" + 
					"楼盘地址 500\r\n" + 
					"哪个站 500\r\n" + 
					"配套设施 500\r\n" + 
					"好一点 500\r\n" + 
					"楼盘 500\r\n" + 
					"地铁 1500\r\n" + 
					"房子 500\r\n" + 
					"样板房 500\r\n" + 
					"性价比 500\r\n" + 
					"区 500\r\n" + 
					"微信 500\r\n" + 
					"你叫什么 500\r\n" + 
					"你姓什么 500\r\n" + 
					"叫什么 500\r\n" + 
					"姓什么 500\r\n" + 
					"怎么称呼 500\r\n" + 
					"你贵姓 500\r\n" + 
					"您贵姓 500\r\n" + 
					"你怎么称呼 500\r\n" + 
					"怎么这么多人给我打电话 500\r\n" + 
					"不感兴趣 500\r\n" + 
					"我买了 500\r\n" + 
					"买好了 500\r\n" + 
					"已经买过了 500\r\n" + 
					"是不是真的 500\r\n" + 
					"位置不好 500\r\n" + 
					"地段不好 500\r\n" + 
					"房子怎么样 500\r\n" + 
					"你说 1000\r\n" + 
					"不要再打 500\r\n" + 
					"打过 500\r\n" + 
					"好多次 500\r\n" + 
					"怎么天天 500\r\n" + 
					"打给我 500\r\n" + 
					"太不专业 500\r\n" + 
					"都不知道 500\r\n" + 
					"预售证 500\r\n" + 
					"打错 500\r\n" + 
					"开间 500\r\n" + 
					"进深 500\r\n" + 
					"人气 500\r\n" + 
					"太偏 500\r\n" + 
					"限贷 500\r\n" + 
					"结顶 500\r\n" + 
					"开始卖 500\r\n" + 
					"公摊 500\r\n" + 
					"多大户型 500\r\n" + 
					"房子多大 500\r\n" + 
					"听不清楚 500\r\n" + 
					"听不清 500\r\n" + 
					"听不到 500\r\n" + 
					"不考虑 500\r\n" + 
					"不想了解 500\r\n" + 
					"不了解 500\r\n" + 
					"怎么卖 500\r\n" + 
					"高了点 500\r\n" + 
					"多少一平 500\r\n" + 
					"不熟 500\r\n" + 
					"没去过 500\r\n" + 
					"没听过 500\r\n" + 
					"说得具体一点 500\r\n" + 
					"说得清楚一点 500\r\n" + 
					"价格太贵 500\r\n" + 
					"干嘛的 500\r\n" + 
					"做什么 500\r\n" + 
					"没卖房子 500\r\n" + 
					"多少一方 500\r\n" + 
					"小一点 500\r\n" + 
					"大一点 500\r\n" + 
					"小的 500\r\n" + 
					"大的 500\r\n" + 
					"大面积 500\r\n" + 
					"小面积 500\r\n" + 
					"有点小 500\r\n" + 
					"没听清楚 500\r\n" + 
					"人流量 500\r\n" + 
					"拿房 500\r\n" + 
					"看一下 500";
			
			BotSentenceAddition addition = new BotSentenceAddition();
			
			addition.setOptionsJson(options);
			addition.setProcessId(processId);
			addition.setSimTxt(simTxt);
			addition.setStopwordsTxt(stopwordsTxt);
			addition.setTemplateJson("import");
			addition.setUserdictTxt(userdictTxt);
			addition.setWeightsTxt(weightsTxt);
			
			botSentenceAdditionMapper.insert(addition);
		}
	}
	
	@Transactional
	private void initRefuse(String processId, String template_id) {
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
	
}
