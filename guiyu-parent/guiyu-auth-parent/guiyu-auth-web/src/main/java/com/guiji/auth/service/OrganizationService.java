package com.guiji.auth.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.constants.AuthConstants;
import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.auth.model.OrgRoleInfo;
import com.guiji.auth.model.OrganizationVO;
import com.guiji.auth.model.SaveOrgReq;
import com.guiji.auth.model.UpdateOrgReq;
import com.guiji.auth.util.DataLocalCacheUtil;
import com.guiji.auth.util.HttpClientUtil;
import com.guiji.auth.util.OrgUtil;
import com.guiji.botsentence.api.IBotSentenceProcess;
import com.guiji.botsentence.api.IBotSentenceTradeService;
import com.guiji.botsentence.api.entity.BotSentenceTemplateTradeVO;
import com.guiji.botsentence.api.entity.ServerResult;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.guiyu.message.component.FanoutSender;
import com.guiji.notice.api.INoticeSetting;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.UserAiCfgBaseInfoVO;
import com.guiji.user.dao.SysOrganizationMapper;
import com.guiji.user.dao.SysRoleMapper;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysMenu;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysOrganizationExample;
import com.guiji.user.dao.entity.SysOrganizationExample.Criteria;
import com.guiji.user.dao.entity.SysPrivilege;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.RedisUtil;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrganizationService {

	@Autowired
	private FanoutSender fanoutSender;
	@Autowired
	private SysOrganizationMapper sysOrganizationMapper;
	@Autowired
	private SysUserMapper sysUsermapper;
	@Autowired
	private IRobotRemote iRobotRemote;
	@Autowired
	private INoticeSetting noticeSetting; 
	@Autowired
	private SysRoleMapper mapper;
	@Autowired
	private IBotSentenceProcess botSentenceProcess;
	@Autowired
	private IBotSentenceTradeService botSentenceTradeService;
	@Autowired
	RoleService roleService;
	@Autowired
	PrivilegeService privilegeService;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;
	@Autowired
	private RedisUtil redisUtil;
	private static final String REDIS_ORG_BY_USERID = "REDIS_ORG_BY_USERID_";
	private static final String REDIS_ORG_BY_CODE = "REDIS_ORG_BY_CODE_";

	/**
	 * 新增组织
	 * @param record 
	 * @param orgCode 创建人所属组织
	 */
	@Transactional
	public void add(SysOrganization record,Long userId,String orgCode){
		//根据父级orgcode获取本组织编号
		String parentOrgCode = record.getCode();
        String subCode = this.getSubOrgCode(parentOrgCode);
		/*int num=sysOrganizationMapper.countCode(record.getCode());
		String code=record.getCode()+"."+(num+1);*/
		record.setCode(subCode);
		sysOrganizationMapper.insert(record);
//		requestThirdApi_add(record,userId);
		fanoutSender.send("AddOrgNotice.fanoutExchange.Auth", record.getId().toString());
		if(record.getProduct()!=null && !record.getProduct().isEmpty()) {
			//如果参数产品不为空，那么以选择的产品为准
			sysOrganizationMapper.insertOrganizationProduct(record.getId().longValue(),record.getCreateId(),record.getProduct());
		}else {
			//否则新增组织时，以组织上级组织的产品为准 
			SysOrganization parentOrg = this.getOrgByCode(parentOrgCode);
			if(parentOrg.getProduct()==null || parentOrg.getProduct().isEmpty()) {
				log.error("组织：{} 没有配置产品，发生异常！",record.getCode());
			}else {
				sysOrganizationMapper.insertOrganizationProduct(record.getId().longValue(),record.getCreateId(),parentOrg.getProduct());
			}
		}
		if (record != null && record.getIndustryIds() != null && !record.getIndustryIds().isEmpty()) {
			//给企业绑定行业资源
			privilegeService.savePrivlegeTree(null,record.getCreateId().intValue(), subCode, AuthObjTypeEnum.ORG.getCode(), record.getId().toString(), ResourceTypeEnum.TRADE.getCode(), record.getIndustryIds());
		}
		if (record != null && record.getMenuIds() != null && !record.getMenuIds().isEmpty()) {
			//给企业绑定菜单资源
			privilegeService.savePrivlegeTree(null,record.getCreateId().intValue(), subCode, AuthObjTypeEnum.ORG.getCode(), record.getId().toString(), ResourceTypeEnum.MENU.getCode(), record.getMenuIds());
		}
		//新增企业后，初始化一条企业管理员信息
		SysRole role = new SysRole();
		role.setName("企业管理员");
		role.setDesc("企业管理员");
		role.setCreateId(record.getCreateId());
		role.setUpdateId(record.getCreateId());
		role.setDelFlag(0);
		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
		role.setOrgCode(subCode); //角色所属组织
		role.setDataAuthLevel(3);	//默认管理员可以查询：本组织以及下级组织
		role.setInitRole(1);//接口增加的角色都是非初始化数据，只有初始化才是
		role.setSuperAdmin(1);//接口增加的角色都是非超级管理员，只有初始化才是
		mapper.insert(role);
		//为新增的角色赋默认组织的菜单
		privilegeService.savePrivlege(userId.intValue(), subCode, AuthObjTypeEnum.ROLE.getCode(), role.getId().toString(), ResourceTypeEnum.MENU.getCode(), record.getMenuIds());
		try {
			noticeSetting.addNoticeSetting(record.getCode());
		} catch (Exception e) {
			//通知失败，不做处理
			log.error("新增组织成功，消息通知失败!",e);
		}
	}
	
	@Transactional
	public void delte(SysOrganization record){
		record.setDelFlag(1);
		sysOrganizationMapper.updateByPrimaryKeySelective(record);
		redisUtil.del(REDIS_ORG_BY_CODE+record.getCode());
        redisUtil.delVague(REDIS_ORG_BY_USERID);
	}
	
	@Transactional
	public void update(SysOrganization record,Long updateUser){
		
		isRobotNumRight(record); // 判断配置机器人数量
//		requestThirdApi_update(record,updateUser);
		sysOrganizationMapper.updateByPrimaryKeySelective(record);
		if(!AuthConstants.ROOT_ORG_CODE.equals(record.getCode())) {
			if (record != null && record.getProduct() != null && !record.getProduct().isEmpty()) {
				sysOrganizationMapper.updateOrganizationProduct(record.getId().longValue(),record.getUpdateId(),record.getProduct());
			}
			if (record != null && record.getIndustryIds() != null && !record.getIndustryIds().isEmpty()) {
				//给企业绑定行业资源
				privilegeService.savePrivlegeTree(null,updateUser.intValue(), record.getCode(), AuthObjTypeEnum.ORG.getCode(), record.getId().toString(), ResourceTypeEnum.TRADE.getCode(), record.getIndustryIds());
			}
			if (record != null && record.getMenuIds() != null && !record.getMenuIds().isEmpty()) {
				//给企业绑定菜单资源
				privilegeService.savePrivlegeTree(null,updateUser.intValue(), record.getCode(), AuthObjTypeEnum.ORG.getCode(), record.getId().toString(), ResourceTypeEnum.MENU.getCode(), record.getMenuIds());
			}
		}else {
			//系统不需要通过前端绑定行业/菜单资源
			//系统数据通过资源初始化或者资源新增时的初始化绑定
		}
		redisUtil.del(REDIS_ORG_BY_CODE+record.getCode());
		redisUtil.delVague(REDIS_ORG_BY_USERID);
	}

	private String requestThirdApi_login()
	{
		String loginUrl = "http://192.168.1.60:18080/cloud-auth/userLogin";
		Map<String,Object> userLoginReqVO = new HashMap<>();
		Map<String,Object> userLoginPsdReqVo = new HashMap<>();
		Map<String,String> loginMapChild = new HashMap<>();
		loginMapChild.put("loginid", "java");
		loginMapChild.put("password", "123456");
		userLoginPsdReqVo.put("userLoginPsdReqVo", loginMapChild);
		userLoginReqVO.put("userLoginReqVO", userLoginPsdReqVo);
		String loginRestult = HttpClientUtil.post(loginUrl,userLoginReqVO);
		String access_token = JSONObject.parseObject(loginRestult).getJSONObject("data").getJSONObject("gjToken").getString("access_token");
		return access_token;
	}
	
	private void requestThirdApi_add(SysOrganization record, Long userId)
	{
		String access_token = requestThirdApi_login();
		String postUrl = "http://192.168.1.60:18080/kf-workorder/org/remote/save?access_token="+access_token;
		String restult = HttpClientUtil.post(postUrl,new SaveOrgReq(record.getId(),record.getType(),record.getName(),record.getCode(),record.getSubCode(),userId,record.getCreateTime()));
		String rspCode = JSONObject.parseObject(restult).getString("rspCode");
		if(!"000000".equals(rspCode)){
			throw new GuiyuException("请求三方接口失败!");
		}
	}

	private void requestThirdApi_update(SysOrganization record, Long updateUser)
	{
		if(record.getRobot()==null && record.getStartDate()==null && record.getEndDate()==null){return;}
		String access_token = requestThirdApi_login();
		String url = "http://192.168.1.60:18080/kf-workorder/org/remote/updateResource?access_token="+access_token;
		SysOrganization organization = sysOrganizationMapper.selectByPrimaryKey(record.getId().longValue());
		UpdateOrgReq req = new UpdateOrgReq(organization.getId(),null,organization.getType(),organization.getCode(), 
				organization.getRobot(), organization.getStartDate(), organization.getEndDate(), record.getRobot(), record.getStartDate(), 
				record.getEndDate(), updateUser, new Date());
		String orgCode = organization.getCode();
		Integer type = organization.getType();
		Integer superAgentId = null;
		while(type != 1)
		{
			SysOrganization org = getParentOrg(orgCode);
			orgCode = org.getCode();
			superAgentId = org.getId();
			type = org.getType();
		}
		req.setSuperAgentId(superAgentId);
		String restult = HttpClientUtil.post(url, req);
		String rspCode = JSONObject.parseObject(restult).getString("rspCode");
		if(!"000000".equals(rspCode)){
			throw new GuiyuException("请求三方接口失败!");
		}
	}

	private void isRobotNumRight(SysOrganization record)
	{
		if(record.getRobot() != null)
		{
			SysOrganization organization = sysOrganizationMapper.selectByPrimaryKey(record.getId().longValue());
			SysOrganization parentOrg = getParentOrg(organization.getCode());
			List<SysOrganization> brothers = queryBrotherOrg(organization.getCode());
			int i = 0;
			if(brothers != null && CollectionUtils.isNotEmpty(brothers)){
				for(SysOrganization org : brothers){
					i += org.getRobot();
				}
			}
			if(!"1".equals(parentOrg.getCode())){
				if(record.getRobot() + i > parentOrg.getRobot()){
					throw new GuiyuException("配置机器人数之和超过父及企业！");
				}
			}
			List<SysOrganization> children = queryChildrenOrg(organization.getCode());
			int j = 0;
			if(children != null && CollectionUtils.isNotEmpty(children)){
				for(SysOrganization org : children){
					j += org.getRobot();
				}
			}
			if(record.getRobot() < j){
				throw new GuiyuException("配置机器人数低于子企业配置机器人数之和！");
			}
		}
	}
	
	public Page<OrganizationVO> selectByPage(Page<Object> page, Long userId, Integer authLevel, String orgCode,String orgName,Integer type){
		Page<OrganizationVO> rtnPage = new Page<OrganizationVO>();
		SysOrganizationExample example=new SysOrganizationExample();
		Criteria criteria = example.createCriteria();
		criteria.andDelFlagEqualTo(0);
		if(authLevel == 1) {
			criteria.andCreateIdEqualTo(userId);
		} else if(authLevel == 2) {
			criteria.andCodeEqualTo(orgCode);
		}else if(authLevel == 3) {
			criteria.andCodeLike(orgCode + "%");
		}
		if(StrUtils.isNotEmpty(orgName)) {
			criteria.andNameLike("%"+orgName+"%");
		}
		if(type!=null) {
			criteria.andTypeEqualTo(type);
		}
		int totalRecord=sysOrganizationMapper.countByExample(example);
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int limitStart = (pageNo-1)*pageSize;	//起始条数
		int limitEnd = pageSize;	//查询条数
		example.setLimitStart(limitStart);
		example.setLimitEnd(limitEnd);
		example.setOrderByClause("id desc");
		List<SysOrganization> orgList = sysOrganizationMapper.selectByExample(example);
		List<OrganizationVO> voList = new ArrayList<OrganizationVO>();
		if(orgList!=null && !orgList.isEmpty()) {
			Map<String,SysOrganization> parentOrgMap = new HashMap<String,SysOrganization>();
			for(SysOrganization org : orgList) {
				OrganizationVO vo = new OrganizationVO();
				BeanUtil.copyProperties(org, vo);
				//填充创建人
				if(org.getCreateId()!=null) {
					SysUser sysUser = dataLocalCacheUtil.queryUser(org.getCreateId().intValue());
					if(sysUser!=null) {
						vo.setCreateName(sysUser.getUsername());
					}
				}
				//填充更新人
				if(org.getUpdateId()!=null) {
					SysUser sysUser = dataLocalCacheUtil.queryUser(org.getUpdateId().intValue());
					if(sysUser!=null) {
						vo.setUpdateName(sysUser.getUsername());
					}
				}
				String code = org.getCode();
				if(StrUtils.isNotEmpty(code)) {
					String parentOrgCode = OrgUtil.getParentOrg(code);
					if(StrUtils.isNotEmpty(parentOrgCode)) {
						if(parentOrgMap.get(parentOrgCode)!=null) {
							vo.setParentName(parentOrgMap.get(parentOrgCode).getName()); //上级组织名称
						}else {
							SysOrganization parentOrg = this.getOrgByCode(parentOrgCode);
							vo.setParentName(parentOrg.getName()); //上级组织名称
							parentOrgMap.put(parentOrgCode, parentOrg);
						}
						vo.setParentOrgCode(parentOrgCode);
					}
				}
				vo.setRobot(vo.getRobot()==null?0:vo.getRobot());
				vo.setLine(vo.getLine()==null?0:vo.getLine());
				vo.setBotstence(vo.getBotstence()==null?0:vo.getBotstence());
				voList.add(vo);
				rtnPage.setRecords(voList);
			}
		}
		rtnPage.setPageNo(pageNo);
		rtnPage.setPageSize(pageSize);
		rtnPage.setTotal(totalRecord);
		return rtnPage;
	}
	
	public Page<Map> selectOpenByPage(Page<Map> page, Long userId, Integer authLevel, String orgCode)
	{
		SysOrganizationExample example = new SysOrganizationExample();
		Criteria criteria = example.createCriteria();
		criteria.andDelFlagEqualTo(0).andOpenEqualTo(1);
		if (authLevel == 1){
			criteria.andCreateIdEqualTo(userId);
		} else if (authLevel == 2){
			criteria.andCodeEqualTo(orgCode);
		} else if (authLevel == 3){
			criteria.andCodeLike(orgCode + "%");
		}
		if (!StringUtils.isEmpty(page.getOrgName())){
			criteria.andNameLike("%" + page.getOrgName() + "%");
		}
		int num = sysOrganizationMapper.countByExample(example);
		List<Map> list = sysOrganizationMapper.selectOpenByPage(page);
		for (Map map : list){
			ServerResult<Integer> result = botSentenceProcess.countTemplateByOrgCode((String) map.get("code"));
			if (result != null){
				map.put("botstence", result.getData());
			}
		}
		page.setTotal(num);
		page.setRecords(list);
		return page;
	}
	
	public List<SysOrganization> getOrgByType(Integer type){
		SysOrganizationExample example=new SysOrganizationExample();
		if(StringUtils.isEmpty(type)){
			example.createCriteria().andDelFlagEqualTo(0);
		}else{
			example.createCriteria().andDelFlagEqualTo(0).andTypeEqualTo(type);
		}
		return sysOrganizationMapper.selectByExample(example);
	}
	
	/**
	 * 按权限查询当前企业以及下级企业
	 * @param orgCode
	 * @return
	 */
	public List<SysOrganization> getAuthOrgList(Long userId, Integer authLevel, String orgCode)
	{
		SysOrganizationExample example = new SysOrganizationExample();
		if(authLevel == 1) {
			example.createCriteria().andCreateIdEqualTo(userId);
		} else if(authLevel == 2) {
			example.createCriteria().andCodeEqualTo(orgCode);
		}else if(authLevel == 3) {
			example.createCriteria().andCodeLike(orgCode + "%");
		}
		return sysOrganizationMapper.selectByExample(example);
	}
	
	public List<SysOrganization> getOrgByUserId(Long userId){
		return sysOrganizationMapper.getOrgByUserId(userId);
	}
	
	public boolean checkName(String name){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andNameEqualTo(name).andDelFlagEqualTo(0);
		int num=sysOrganizationMapper.countByExample(example);
		return num==0;
	}
	
	public boolean checkName(String name,Long id){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andNameEqualTo(name).andDelFlagEqualTo(0).andIdNotEqualTo(id.intValue());
		int num=sysOrganizationMapper.countByExample(example);
		return num==0;
	}
	
	public List<SysOrganization> getOrgNotOpen(Long userId, Integer authLevel, String orgCode)
	{
		SysOrganizationExample example = new SysOrganizationExample();
		Criteria criteria = example.createCriteria();
		criteria.andDelFlagEqualTo(0).andOpenEqualTo(0);
		if (authLevel == 1){
			criteria.andCreateIdEqualTo(userId);
		} else if (authLevel == 2){
			criteria.andCodeEqualTo(orgCode);
		} else if (authLevel == 3){
			criteria.andCodeLike(orgCode + "%");
		}
		return sysOrganizationMapper.selectByExample(example);
	}
	
	public boolean existChildren(SysOrganization record){
		return sysOrganizationMapper.existChildren(record);
	}
	
	public SysOrganization getOrgByCode(String code)
	{
		SysOrganization sysOrganization = (SysOrganization) redisUtil.get(REDIS_ORG_BY_CODE + code);
		if (sysOrganization == null)
		{
			SysOrganizationExample example = new SysOrganizationExample();
			example.createCriteria().andDelFlagEqualTo(0).andCodeEqualTo(code);
			List<SysOrganization> list = sysOrganizationMapper.selectByExample(example);
			if (CollectionUtils.isNotEmpty(list))
			{
				sysOrganization = list.get(0);
				List<Integer> product = sysOrganizationMapper.getProductByOrganizationId(sysOrganization.getId().longValue());
				sysOrganization.setProduct(product);
				redisUtil.set(REDIS_ORG_BY_CODE + code, sysOrganization);
			}
		}
		return sysOrganization;
	}

	/**
	 * 组织代码生成规则
	 * @param orgCode
	 * @return
	 */
	public synchronized String getSubOrgCode(String orgCode) {
		String subOrgCode = null;
		SysOrganization sysOrganization = null;
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andCodeEqualTo(orgCode);
		List<SysOrganization> sysOrganizationList = sysOrganizationMapper.selectByExample(example);
		if (sysOrganizationList != null && !sysOrganizationList.isEmpty()) {
			sysOrganization = sysOrganizationList.get(0);
		}
		if (sysOrganization != null) {
			if (StringUtils.isEmpty(sysOrganization.getSubCode())) {
				if(AuthConstants.ROOT_ORG_CODE.equals(sysOrganization.getCode())) {
					subOrgCode = sysOrganization.getCode() + ".1.";
				}else {
					subOrgCode = sysOrganization.getCode() + "1.";
				}
			} else {
				String code = sysOrganization.getSubCode();
				int last2 = code.lastIndexOf(".",code.lastIndexOf(".")-1);	//倒数第2个.
				String preOrgCode = code.substring(0,last2+1);
				String lastOrgCode = code.substring(last2+1,code.lastIndexOf("."));
				int lastOrgCodeNumber = Integer.valueOf(lastOrgCode) + 1;
				subOrgCode = preOrgCode+lastOrgCodeNumber+".";
			}
			SysOrganization sysOrganizationUpdate = new SysOrganization();
			sysOrganizationUpdate.setId(sysOrganization.getId());
			sysOrganizationUpdate.setCode(orgCode);
			sysOrganizationUpdate.setSubCode(subOrgCode);
			sysOrganizationMapper.updateByPrimaryKeySelective(sysOrganizationUpdate);
		}

		return subOrgCode;
	}
	

	public int countRobotByUserId(Long userId){
		int countRobot = 0;
		SysUser sysUser = sysUsermapper.getUserById(userId);
		List<SysRole> sysRoleList = sysUsermapper.getRoleByUserId(userId);
		String orgCode = null;
		if (sysUser != null) {
			orgCode = sysUser.getOrgCode();
			if (sysRoleList != null && sysRoleList.size() > 0) {
				if (sysRoleList.get(0).getId() == 4) {
					Result.ReturnData<UserAiCfgBaseInfoVO> returnData = iRobotRemote.queryCustBaseAccount(String.valueOf(userId));
					if (returnData.success && returnData.getBody() != null) {
						countRobot = returnData.getBody().getAiTotalNum();
					}
				} else {
					countRobot = sysOrganizationMapper.countRobotByUserId(orgCode);
				}
			}
		}
		return countRobot;
	}

	public List<Integer> getProductByOrganizationId(Long organizationId) {
		return sysOrganizationMapper.getProductByOrganizationId(organizationId);
	}
	
	public List<Integer> getOrgByProductId(Integer productId) {
		return sysOrganizationMapper.getOrgByProductId(productId);
	}

	/**
	 * 查询组织绑定的行业模板
	 * @param orgCode
	 * @return
	 */
	public List<String> getIndustryByOrgCode(String orgCode) {
		if(StrUtils.isNotEmpty(orgCode)) {
			List<String> tradeList = new ArrayList<String>();
			SysOrganization org = this.getOrgByCode(orgCode);
			if(org!=null) {
				List<SysPrivilege> list = privilegeService.queryPrivilegeListByAuth(null,org.getId().toString(), AuthObjTypeEnum.ORG.getCode(), ResourceTypeEnum.TRADE.getCode());
				if(list!=null && !list.isEmpty()) {
					for(SysPrivilege privilege:list) {
						tradeList.add(privilege.getResourceId());
					}
				}
			}
			return tradeList;
		}
		return null;
	}

	public List<SysOrganization> getOrgByOrgCodeOrgName(Long userId, Integer authLevel, String orgCode,String orgName){
		SysOrganizationExample example = new SysOrganizationExample();
		if(authLevel == 1) {
			example.createCriteria().andCreateIdEqualTo(userId);
		} else if(authLevel == 2) {
			example.createCriteria().andCodeEqualTo(orgCode);
		}else if(authLevel == 3) {
			example.createCriteria().andCodeLike(orgCode + "%");
		}
		if(!StringUtils.isEmpty(orgName)){
			example.createCriteria().andNameLike("%" + orgName + "%");
		}
		example.createCriteria().andDelFlagEqualTo(0);
		return sysOrganizationMapper.selectByExample(example);
	}

	/**
	 * 按权限查询当前企业以及下级企业 并以树形结构返回
	 * @param orgCode
	 * @return
	 */
	public List<OrgRoleInfo> getAuthOrgTree(String orgName, Long userId, Integer authLevel, String orgCode ,boolean isNeedRole){
		List<OrgRoleInfo> resultList = new ArrayList<>();
		SysOrganizationExample example=new SysOrganizationExample();
		Criteria criteria = example.createCriteria();
		if(authLevel == 1 || authLevel == 2){
			criteria.andCodeEqualTo(orgCode);
		}else {
			criteria.andCodeLike(orgCode+"%");
		}
		if(orgName != null){
			criteria.andNameLike("%"+orgName+"%");
		}
		example.setOrderByClause("code");
		List<SysOrganization> allOrgs = sysOrganizationMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(allOrgs)){return null;}
		
		List<OrgRoleInfo> orgInfos = new ArrayList<>();
		// 在所有组织下添加对应的角色
		for(SysOrganization org : allOrgs) 
		{
			OrgRoleInfo orgInfo = this.org2OrgRoleInfo(org);
			String code = org.getCode();
			List<SysRole> roleList = roleService.getRolesByOrg(code);
			if (CollectionUtils.isNotEmpty(roleList)){
				for (SysRole role : roleList){
					orgInfo.addChild(this.role2OrgRoleInfo(role));
				}
			}
			orgInfos.add(orgInfo); //所有添加了角色的组织集合
		}
		
		Map<String, OrgRoleInfo> map = new HashMap<>();
		orgInfos.stream().forEach(orgInfo -> map.put(orgInfo.getOrgCode(), orgInfo));
		Comparator<OrgRoleInfo> comparator1 = (o1, o2) -> o2.getOrgCode().split("\\.").length - o1.getOrgCode().split("\\.").length;
		Collections.sort(orgInfos, comparator1);
		for(OrgRoleInfo orgInfo : orgInfos)
		{
			String code = orgInfo.getOrgCode();
			if(code.equals("1")){continue;}
			String parentCode = getParentOrgCode(code);
			if(map.containsKey(parentCode))
			{
				OrgRoleInfo parentOrgInfo = map.get(parentCode);
				parentOrgInfo.addChild(orgInfo);
				map.remove(code);
				map.put(parentCode, parentOrgInfo);
			}
		}
		Comparator<OrgRoleInfo> comparator2 = (o1, o2) -> o1.getOrgCode().split("\\.").length - o2.getOrgCode().split("\\.").length;
		map.values().stream().forEach(orgInfo -> resultList.add(orgInfo));
		Collections.sort(resultList, comparator2);
		
		return resultList;
	}
	
	/**
	 * 组织转VO
	 * @param org
	 * @return
	 */
	private OrgRoleInfo org2OrgRoleInfo(SysOrganization org) {
		OrgRoleInfo vo = new OrgRoleInfo();
		vo.setId(org.getId().longValue());
		vo.setName(org.getName());
		vo.setType(1);
		vo.setOrgCode(org.getCode());
		return vo;
	}
	/**
	 * 角色转VO
	 * @param org
	 * @return
	 */
	private OrgRoleInfo role2OrgRoleInfo(SysRole sysRole) {
		OrgRoleInfo vo = new OrgRoleInfo();
		BeanUtil.copyProperties(sysRole, vo);
		vo.setId(Long.valueOf(sysRole.getId()));
		vo.setName(sysRole.getName());
		vo.setType(2);
		vo.setOrgCode(sysRole.getOrgCode());
		if(sysRole.getCreateId()!=null) {
			SysUser sysUser = dataLocalCacheUtil.queryUser(sysRole.getCreateId().intValue());
			if(sysUser!=null) {
				vo.setCreateName(sysUser.getUsername());
			}
		}
		if(sysRole.getUpdateId()!=null) {
			SysUser sysUser = dataLocalCacheUtil.queryUser(sysRole.getUpdateId().intValue());
			if(sysUser!=null) {
				vo.setUpdateName(sysUser.getUsername());
			}
		}
		return vo;
	}
	
	private List<SysMenu> parseTree(List<SysMenu> allMenu){
		Map<Integer,SysMenu> map=new HashMap<>();
		List<SysMenu> list=new ArrayList<>();
		allMenu.stream().forEach((item)->{
			Integer pid=item.getPid();
			if(0==pid){
				list.add(item);
				map.put(item.getId(), item);
			}else{
				SysMenu parent=map.get(pid);
				if(parent!=null){
					parent.getChild().add(item);
					map.put(item.getId(), item);
				}
			}
		});
		return list;
	}
	
	
	/**
	 * 查询系统关联的话术
	 * 匹配产品选择的话术
	 * 
	 * @param productId
	 * @return
	 */
	public Map<String,Object> getTemplateTradeByTopOrg(Integer productId){
		Map<String,Object> map=new HashMap<String,Object>();
		//获取顶层企业的模板
		List<SysPrivilege> topOrgTradeList = privilegeService.queryPrivilegeListByAuth(null,AuthConstants.ROOT_ORG_CODE, AuthObjTypeEnum.ORG.getCode(), ResourceTypeEnum.TRADE.getCode());
		if(topOrgTradeList!=null && !topOrgTradeList.isEmpty()) {
			List<String> topOrgTempList = this.getTemplateList(topOrgTradeList);
			//产品关联的行业-话术模板树形
			ServerResult<List<BotSentenceTemplateTradeVO>> topOrgTempTradeTreeData = botSentenceTradeService.queryTradeListByTemplateIdList(topOrgTempList);
			if(topOrgTempTradeTreeData!=null && topOrgTempTradeTreeData.getData()!=null) {
				map.put("trades", topOrgTempTradeTreeData.getData());
				if(productId!=null) {
					List<SysPrivilege> productTradeList = privilegeService.queryPrivilegeListByAuth(null,productId.toString(), AuthObjTypeEnum.PRODUCT.getCode(), ResourceTypeEnum.TRADE.getCode());
					if(productTradeList!=null && !productTradeList.isEmpty()) {
						map.put("selected", this.getTemplateList(productTradeList));
					}
				}
			}
		}
		return map;
	}

	
	/**
	 * 查询该产品关联的话术模板/行业
	 * 同时返回本企业关联的行业/模板
	 * @param productId
	 * @return
	 */
	public Map<String,Object> getTemplateTradeByProductAndOrg(Integer productId,String orgCode){
		Map<String,Object> map=new HashMap<String,Object>();
		if(productId!=null) {
			//获取产品关联的行业模板
			List<SysPrivilege> productTradeList = privilegeService.queryPrivilegeListByAuth(null,productId.toString(), AuthObjTypeEnum.PRODUCT.getCode(), ResourceTypeEnum.TRADE.getCode());
			if(productTradeList!=null && !productTradeList.isEmpty()) {
				List<String> productTempList = this.getTemplateList(productTradeList);
				//产品关联的行业-话术模板树形
				ServerResult<List<BotSentenceTemplateTradeVO>> productTempTradeTreeData = botSentenceTradeService.queryTradeListByTemplateIdList(productTempList);
				if(productTempTradeTreeData!=null && productTempTradeTreeData.getData()!=null) {
					map.put("trades", productTempTradeTreeData.getData());
					if(StrUtils.isNotEmpty(orgCode)) {
						SysOrganization organization = this.getOrgByCode(orgCode);
						if(organization!=null) {
							List<SysPrivilege> orgTradeList = privilegeService.queryPrivilegeListByAuth(null,organization.getId().toString(), AuthObjTypeEnum.ORG.getCode(), ResourceTypeEnum.TRADE.getCode());
							if(orgTradeList!=null && !orgTradeList.isEmpty()) {
								map.put("selected", this.getTemplateList(orgTradeList));
							}
						}
					}
				}
			}
		}
		return map;
	}
	
	
	/**
	 * 按组织查询组织关联的行业-模板树形数据
	 * orgCode挂在pOrgCode下
	 * @param pOrgCode
	 * @param orgCode
	 * @return
	 */
	public Map<String,Object> getTemplateTradeByOrg(String pOrgCode,String orgCode){
		Map<String,Object> map=new HashMap<String,Object>();
		if(StrUtils.isNotEmpty(pOrgCode)) {
			//获取产品关联的行业模板
			SysOrganization pOrganization = this.getOrgByCode(pOrgCode);
			if(pOrganization!=null) {
				List<SysPrivilege> pOrgTradeList = privilegeService.queryPrivilegeListByAuth(null,pOrganization.getId().toString(), AuthObjTypeEnum.ORG.getCode(), ResourceTypeEnum.TRADE.getCode());
				if(pOrgTradeList!=null && !pOrgTradeList.isEmpty()) {
					List<String> pOrgTempList = this.getTemplateList(pOrgTradeList);
					//上级组织关联的行业-话术模板树形
					ServerResult<List<BotSentenceTemplateTradeVO>> pOrgTempTradeTreeData = botSentenceTradeService.queryTradeListByTemplateIdList(pOrgTempList);
					if(pOrgTempTradeTreeData!=null && pOrgTempTradeTreeData.getData()!=null) {
						map.put("trades", pOrgTempTradeTreeData.getData());
						if(StrUtils.isNotEmpty(orgCode)) {
							SysOrganization organization = this.getOrgByCode(orgCode);
							if(organization!=null) {
								List<SysPrivilege> orgTradeList = privilegeService.queryPrivilegeListByAuth(null,organization.getId().toString(), AuthObjTypeEnum.ORG.getCode(), ResourceTypeEnum.TRADE.getCode());
								if(orgTradeList!=null && !orgTradeList.isEmpty()) {
									map.put("selected", this.getTemplateList(orgTradeList));
								}
							}
						}
					}
				}
			}
		}
		return map;
	}
	
	
	/**
	 * 权限列表转模板list
	 * @param list
	 * @return
	 */
	private List<String> getTemplateList(List<SysPrivilege> list){
		if(list!=null && !list.isEmpty()) {
			List<String> tempList = new ArrayList<String>();
			for(SysPrivilege privilege : list) {
				tempList.add(privilege.getResourceId());
			}
			return tempList;
		}
		return null;
	}
	
	public List<Integer> getSubOrgIdByOrgId(Integer orgId) {
		return sysOrganizationMapper.getSubOrgIdByOrgId(orgId);
	}

	public List<Integer> getAllOrgId() {
		return sysOrganizationMapper.getAllOrgId();
	}

	public List<Map> querySubOrgByOrgId(Integer orgId)
	{
		return sysOrganizationMapper.querySubOrgByOrgId(orgId);
	}

	public List<Map> getSubOrgByAuthLevel(Long userId, Integer authLevel, String orgCode)
	{
		return sysOrganizationMapper.getSubOrgByAuthLevel(userId,authLevel,orgCode);
	}
	
	/**
	 * 获取上一级组织
	 * 系统的上一级就是自己
	 */
	public SysOrganization getParentOrg(String orgCode)
	{
		if("1".equals(orgCode)){
			 return this.getOrgByCode(orgCode);
		}
		String parentOrgCode = orgCode.substring(0, orgCode.lastIndexOf(".")).substring(0, orgCode.substring(0, orgCode.lastIndexOf(".")).lastIndexOf(".")+1);
		parentOrgCode = "1.".equals(parentOrgCode)? "1" : parentOrgCode;
		return this.getOrgByCode(parentOrgCode);
	}
	
	/**
	 * 获取上一级组织
	 * 系统的上一级就是自己
	 */
	public String getParentOrgCode(String orgCode)
	{
		if("1".equals(orgCode)){
			 return orgCode;
		}
		String parentOrgCode = orgCode.substring(0, orgCode.lastIndexOf(".")).substring(0, orgCode.substring(0, orgCode.lastIndexOf(".")).lastIndexOf(".")+1);
		return parentOrgCode = "1.".equals(parentOrgCode)? "1" : parentOrgCode;
	}
	
	/**
	 * 获取所有下一级组织
	 */
	public List<SysOrganization> queryChildrenOrg(String orgCode)
	{
		return sysOrganizationMapper.queryChildrenOrg(orgCode);
	}
	
	/**
	 * 获取兄弟（同级）组织
	 */
	public List<SysOrganization> queryBrotherOrg(String orgCode)
	{
		SysOrganization parentOrg = getParentOrg(orgCode);
		List<SysOrganization> brothers = queryChildrenOrg(parentOrg.getCode());
		Iterator<SysOrganization> iterator = brothers.iterator();
		while(iterator.hasNext()){
			SysOrganization org = iterator.next();
			if(org.getCode().equals(orgCode)){
				iterator.remove();
			}
		}
		return brothers;
	}

	public List<SysOrganization> getOrgByUserIdAuthLevel(Long userId, Integer authLevel, String orgCode)
	{
		SysOrganizationExample example = new SysOrganizationExample();
		if(authLevel == 1) {
			example.createCriteria().andCreateIdEqualTo(userId);
		} else if(authLevel == 2) {
			example.createCriteria().andCodeEqualTo(orgCode);
		}else if(authLevel == 3) {
			example.createCriteria().andCodeLike(orgCode + "%");
		}
		return sysOrganizationMapper.selectByExample(example);
	}

	public Map getOrgByUsername(String username)
	{
		return sysOrganizationMapper.getOrgByUsername(username);
	}
}
