package com.guiji.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.guiji.auth.constants.AuthConstants;
import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.auth.model.OrgRoleInfo;
import com.guiji.auth.model.OrganizationVO;
import com.guiji.auth.util.DataLocalCacheUtil;
import com.guiji.auth.util.OrgUtil;
import com.guiji.botsentence.api.IBotSentenceProcess;
import com.guiji.botsentence.api.IBotSentenceTradeService;
import com.guiji.botsentence.api.entity.BotSentenceTemplateTradeVO;
import com.guiji.botsentence.api.entity.ServerResult;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.notice.api.INoticeSetting;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.UserAiCfgBaseInfoVO;
import com.guiji.user.dao.SysOrganizationMapper;
import com.guiji.user.dao.SysRoleMapper;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysMenu;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysOrganizationExample;
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
	private QueueSender queueSender;
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
		queueSender.send("OrgIdMQ.direct.Auth", record.getId().toString());
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
			privilegeService.savePrivlegeTree(record.getCreateId().intValue(), subCode, AuthObjTypeEnum.ORG.getCode(), record.getId().toString(), ResourceTypeEnum.TRADE.getCode(), record.getIndustryIds());
		}
		if (record != null && record.getMenuIds() != null && !record.getMenuIds().isEmpty()) {
			//给企业绑定菜单资源
			privilegeService.savePrivlegeTree(record.getCreateId().intValue(), subCode, AuthObjTypeEnum.ORG.getCode(), record.getId().toString(), ResourceTypeEnum.MENU.getCode(), record.getMenuIds());
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
		privilegeService.savePrivlege(userId.intValue(), orgCode, AuthObjTypeEnum.ROLE.getCode(), role.getId().toString(), ResourceTypeEnum.MENU.getCode(), record.getMenuIds());
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
	public void update(SysOrganization record,Long updateUser,String orgCode){
		sysOrganizationMapper.updateByPrimaryKeySelective(record);
		if(!AuthConstants.ROOT_ORG_CODE.equals(record.getCode())) {
			if (record != null && record.getProduct() != null && !record.getProduct().isEmpty()) {
				sysOrganizationMapper.updateOrganizationProduct(record.getId().longValue(),record.getUpdateId(),record.getProduct());
			}
			if (record != null && record.getIndustryIds() != null && !record.getIndustryIds().isEmpty()) {
				//给企业绑定行业资源
				privilegeService.savePrivlegeTree(updateUser.intValue(), orgCode, AuthObjTypeEnum.ORG.getCode(), record.getId().toString(), ResourceTypeEnum.TRADE.getCode(), record.getIndustryIds());
			}
			if (record != null && record.getMenuIds() != null && !record.getMenuIds().isEmpty()) {
				//给企业绑定菜单资源
				privilegeService.savePrivlegeTree(updateUser.intValue(), orgCode, AuthObjTypeEnum.ORG.getCode(), record.getId().toString(), ResourceTypeEnum.MENU.getCode(), record.getMenuIds());
			}
		}else {
			//系统不需要通过前端绑定行业/菜单资源
			//系统数据通过资源初始化或者资源新增时的初始化绑定
		}
		redisUtil.del(REDIS_ORG_BY_CODE+record.getCode());
		redisUtil.delVague(REDIS_ORG_BY_USERID);
	}
	
	public Page<OrganizationVO> selectByPage(Page<Object> page, Long userId, Integer authLevel, String orgCode,String orgName,Integer type){
		Page<OrganizationVO> rtnPage = new Page<OrganizationVO>();
		SysOrganizationExample example=new SysOrganizationExample();
		com.guiji.user.dao.entity.SysOrganizationExample.Criteria criteria = example.createCriteria();
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
	
	public Page<Map> selectOpenByPage(Page<Map> page){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andDelFlagEqualTo(0).andOpenEqualTo(1);
		if(!StringUtils.isEmpty(page.getOrgName())) {
			example.createCriteria().andNameLike("%"+page.getOrgName()+"%");
		}
		if(!StringUtils.isEmpty(page.getOrgCode())) {
			example.createCriteria().andCodeLike(page.getOrgCode()+"%");
		}
		int num=sysOrganizationMapper.countByExample(example);
		List<Map> list=sysOrganizationMapper.selectOpenByPage(page);
		for(Map map : list)
		{
			ServerResult<Integer> result = botSentenceProcess.countTemplateByOrgCode((String) map.get("code"));
			if(result != null) {
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
	public List<SysOrganization> getAuthOrgList(String orgCode){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andCodeLike(orgCode+"%");
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
	
	public List<SysOrganization> getOrgNotOpen(){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andDelFlagEqualTo(0).andOpenEqualTo(0).andTypeNotEqualTo(1);
		return sysOrganizationMapper.selectByExample(example);
	}
	
	public boolean existChildren(SysOrganization record){
		return sysOrganizationMapper.existChildren(record);
	}
	
	public SysOrganization getOrgByCode(String code){
		SysOrganization sysOrganization = (SysOrganization) redisUtil.get(REDIS_ORG_BY_CODE+code);
		if (sysOrganization == null) {
			SysOrganizationExample example=new SysOrganizationExample();
			example.createCriteria().andDelFlagEqualTo(0).andCodeEqualTo(code);
			List<SysOrganization> list=sysOrganizationMapper.selectByExample(example);
			if(list.size()>0){
				sysOrganization = list.get(0);
			}
            List<Integer> product = sysOrganizationMapper.getProductByOrganizationId(sysOrganization.getId().longValue());
			sysOrganization.setProduct(product);
			redisUtil.set(REDIS_ORG_BY_CODE+code,sysOrganization);
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
				List<SysPrivilege> list = privilegeService.queryPrivilegeListByAuth(org.getId().toString(), AuthObjTypeEnum.ORG.getCode(), ResourceTypeEnum.TRADE.getCode());
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

	public List<SysOrganization> getOrgByOrgCodeOrgName(String orgCode,String orgName){
		return sysOrganizationMapper.getOrgByOrgCodeOrgName(orgCode,orgName);
	}

	/**
	 * 按权限查询当前企业以及下级企业 并以树形结构返回
	 * @param orgCode
	 * @return
	 */
	public List<OrgRoleInfo> getAuthOrgTree(String orgCode,boolean isNeedRole){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andCodeLike(orgCode+"%");
		example.setOrderByClause(" code");
		List<SysOrganization> allList = sysOrganizationMapper.selectByExample(example);
		if(allList!=null && !allList.isEmpty()) {
			Map<String,OrgRoleInfo> map=new HashMap<>();
			List<OrgRoleInfo> list=new ArrayList<>();
			for(SysOrganization item:allList) {
				String code=item.getCode();
				if(orgCode.equals(code)){
					OrgRoleInfo root = this.org2OrgRoleInfo(item);
					if(isNeedRole) {
						//如果需要挂角色
						List<SysRole> roleList = roleService.getRolesByOrg(code);
						if(roleList!=null && !roleList.isEmpty()) {
							for(SysRole role:roleList) {
								if(root.getChildren()==null || root.getChildren().isEmpty()) {
									root.setChildren(new ArrayList<OrgRoleInfo>());
								}
								root.getChildren().add(this.role2OrgRoleInfo(role));
							}
						}
					}
					list.add(root);
					String rootCode = item.getCode();
					if(!rootCode.endsWith(".")) {
						rootCode = rootCode + ".";
					}
					map.put(rootCode, root);
				}else{
					int last2 = item.getCode().lastIndexOf(".",item.getCode().lastIndexOf(".")-1);	//倒数第2个.
					OrgRoleInfo parent=map.get(item.getCode().substring(0, last2+1));
					OrgRoleInfo current = this.org2OrgRoleInfo(item);
					if(parent!=null){
						if(isNeedRole) {
							//如果需要挂角色
							List<SysRole> roleList = roleService.getRolesByOrg(item.getCode());
							if(roleList!=null && !roleList.isEmpty()) {
								for(SysRole role:roleList) {
									if(current.getChildren()==null || current.getChildren().isEmpty()) {
										current.setChildren(new ArrayList<OrgRoleInfo>());
									}
									current.getChildren().add(this.role2OrgRoleInfo(role));
								}
							}
						}
						if(parent.getChildren()==null || parent.getChildren().isEmpty()) {
							parent.setChildren(new ArrayList<OrgRoleInfo>());
						}
						parent.getChildren().add(current);
						map.put(item.getCode(), current);
					}
				}
			}
			return list;
		}
		return null;
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
		List<SysPrivilege> topOrgTradeList = privilegeService.queryPrivilegeListByAuth(AuthConstants.ROOT_ORG_CODE, AuthObjTypeEnum.ORG.getCode(), ResourceTypeEnum.TRADE.getCode());
		if(topOrgTradeList!=null && !topOrgTradeList.isEmpty()) {
			List<String> topOrgTempList = this.getTemplateList(topOrgTradeList);
			//产品关联的行业-话术模板树形
			ServerResult<List<BotSentenceTemplateTradeVO>> topOrgTempTradeTreeData = botSentenceTradeService.queryTradeListByTemplateIdList(topOrgTempList);
			if(topOrgTempTradeTreeData!=null && topOrgTempTradeTreeData.getData()!=null) {
				map.put("trades", topOrgTempTradeTreeData.getData());
				if(productId!=null) {
					List<SysPrivilege> productTradeList = privilegeService.queryPrivilegeListByAuth(productId.toString(), AuthObjTypeEnum.PRODUCT.getCode(), ResourceTypeEnum.TRADE.getCode());
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
			List<SysPrivilege> productTradeList = privilegeService.queryPrivilegeListByAuth(productId.toString(), AuthObjTypeEnum.PRODUCT.getCode(), ResourceTypeEnum.TRADE.getCode());
			if(productTradeList!=null && !productTradeList.isEmpty()) {
				List<String> productTempList = this.getTemplateList(productTradeList);
				//产品关联的行业-话术模板树形
				ServerResult<List<BotSentenceTemplateTradeVO>> productTempTradeTreeData = botSentenceTradeService.queryTradeListByTemplateIdList(productTempList);
				if(productTempTradeTreeData!=null && productTempTradeTreeData.getData()!=null) {
					map.put("trades", productTempTradeTreeData.getData());
					if(StrUtils.isNotEmpty(orgCode)) {
						SysOrganization organization = this.getOrgByCode(orgCode);
						if(organization!=null) {
							List<SysPrivilege> orgTradeList = privilegeService.queryPrivilegeListByAuth(organization.getId().toString(), AuthObjTypeEnum.ORG.getCode(), ResourceTypeEnum.TRADE.getCode());
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
				List<SysPrivilege> pOrgTradeList = privilegeService.queryPrivilegeListByAuth(pOrganization.getId().toString(), AuthObjTypeEnum.ORG.getCode(), ResourceTypeEnum.TRADE.getCode());
				if(pOrgTradeList!=null && !pOrgTradeList.isEmpty()) {
					List<String> pOrgTempList = this.getTemplateList(pOrgTradeList);
					//上级组织关联的行业-话术模板树形
					ServerResult<List<BotSentenceTemplateTradeVO>> pOrgTempTradeTreeData = botSentenceTradeService.queryTradeListByTemplateIdList(pOrgTempList);
					if(pOrgTempTradeTreeData!=null && pOrgTempTradeTreeData.getData()!=null) {
						map.put("trades", pOrgTempTradeTreeData.getData());
						if(StrUtils.isNotEmpty(orgCode)) {
							SysOrganization organization = this.getOrgByCode(orgCode);
							if(organization!=null) {
								List<SysPrivilege> orgTradeList = privilegeService.queryPrivilegeListByAuth(organization.getId().toString(), AuthObjTypeEnum.ORG.getCode(), ResourceTypeEnum.TRADE.getCode());
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
}
