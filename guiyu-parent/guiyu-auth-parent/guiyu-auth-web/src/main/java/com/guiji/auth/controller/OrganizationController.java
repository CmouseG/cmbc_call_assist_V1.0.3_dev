package com.guiji.auth.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.guiji.auth.api.IOrg;
import com.guiji.auth.exception.CheckConditionException;
import com.guiji.auth.model.OrgRoleInfo;
import com.guiji.auth.model.OrgVO;
import com.guiji.auth.model.OrganizationVO;
import com.guiji.auth.service.OrganizationService;
import com.guiji.auth.service.UserService;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysOrganization;

@RestController
@RequestMapping("organization")
public class OrganizationController implements IOrg{

	private Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private OrganizationService organizationService;

	@Jurisdiction("system_organization_add")
	@RequestMapping("add")
	public SysOrganization add(SysOrganization record,@RequestHeader long userId, @RequestHeader String orgCode) throws Exception
	{
		if(!organizationService.checkName(record.getName())){
			throw new GuiyuException("组织名称已存在，请您更换组织名称！");
		}
		int leval = record.getCode().length() - record.getCode().replace(".", "").length();
		if (leval > 5) {
			throw new GuiyuException("00010018", "对不起，组织结构层级不能超过5层！");
		}
		record.setOpen(0);
		if(record.getType() == 1){
			record.setOpen(1);
		}
		record.setDelFlag(0);
		record.setRobot(0);
		record.setCreateId(userId);
		record.setUpdateId(userId);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		organizationService.add(record,userId,orgCode);
		return record;
	}
	
	@Jurisdiction("system_organization_delete")
	@RequestMapping("delete")
	public void delte(SysOrganization record,@RequestHeader long userId) throws Exception{
		if(organizationService.existChildren(record)){
			throw new CheckConditionException("00010010");
		}
		record.setUpdateId(userId);
		record.setUpdateTime(new Date());
		organizationService.delte(record);
	}
	
	@Jurisdiction("system_organization_edit,system_account_add,system_account_robot,system_account_delay")
	@RequestMapping("update")
	public void update(SysOrganization record,
			@RequestHeader long userId,@RequestHeader String orgCode) throws CheckConditionException{
		if(!StringUtils.isEmpty(record.getName())){
			if(!organizationService.checkName(record.getName(),record.getId().longValue())){
				throw new CheckConditionException("00010009");
			}
		}
		record.setUpdateId(userId);
		record.setUpdateTime(new Date());
		organizationService.update(record,userId);
	}
	
	@RequestMapping("selectByPage")
	public Page<OrganizationVO> selectByPage(Page<Object> page,String orgName,Integer type, @RequestHeader Long userId, @RequestHeader Integer authLevel, @RequestHeader String orgCode){
		return organizationService.selectByPage(page, userId, authLevel, orgCode,orgName,type);
	}
	
	@RequestMapping("selectOpenByPage")
	public Page<Map> selectOpenByPage(Page<Map> page, @RequestHeader Long userId, @RequestHeader Integer authLevel, @RequestHeader String orgCode){
		page.setUserId(userId);
		page.setAuthLevel(authLevel);
		page.setOrgCode(orgCode);
		return organizationService.selectOpenByPage(page,userId,authLevel,orgCode);
	}
	
	/**
	 * 获取全部的代理
	 * @param type
	 * @return
	 */
	@RequestMapping("getOrgByType")
	public List<SysOrganization> getOrgByType(Integer type){
		return organizationService.getOrgByType(type);
	}
	
	
	/**
	 * 获取企业树
	 * @param orgCode
	 * @return
	 */
	@RequestMapping("getAuthOrgList")
	public List<SysOrganization> getAuthOrgList(@RequestHeader Long userId, @RequestHeader Integer authLevel, @RequestHeader String orgCode){
		return organizationService.getAuthOrgList(userId, authLevel, orgCode);
	}
	
	/**
	 * 获取未开户
	 * @return
	 */
	@RequestMapping("getOrgNotOpen")
	public List<SysOrganization> getOrgNotOpen(@RequestHeader Long userId, @RequestHeader Integer authLevel, @RequestHeader String orgCode){
		return organizationService.getOrgNotOpen(userId, authLevel, orgCode);
	}
	
	@RequestMapping("getOrgByUserId")
	public List<SysOrganization> getOrgByUserId(@RequestHeader Long userId, @RequestHeader Integer authLevel, @RequestHeader String orgCode){
		return organizationService.getOrgByUserIdAuthLevel(userId,authLevel,orgCode);
	}
	
	@RequestMapping("getAdminOrgByUserId")
	public List<SysOrganization> getAdminOrgByUserId(Long userId){
		return organizationService.getOrgByUserId(userId);
	}
	
	@RequestMapping("getOrgByCode")
	public ReturnData<SysOrganization> getOrgByCode(String code){
		return Result.ok(organizationService.getOrgByCode(code));
	} 

	@RequestMapping("countRobotByUserId")
	public int countRobotByUserId(@RequestHeader Long userId){
		return organizationService.countRobotByUserId(userId);
	}

	@RequestMapping("getProductByOrganizationId")
	public List<Integer> getProductByOrganizationId(Long organizationId){
		return organizationService.getProductByOrganizationId(organizationId);
	}

	@RequestMapping("getIndustryByOrgCode")
	public ReturnData<List<String>> getIndustryByOrgCode(String orgCode){
		return Result.ok(organizationService.getIndustryByOrgCode(orgCode));
	}

	@RequestMapping("getOrgByOrgCodeOrgName")
	public List<SysOrganization> getOrgByOrgCodeOrgName(String code,String orgName,
			@RequestHeader Long userId, @RequestHeader Integer authLevel, @RequestHeader String orgCode){
		return organizationService.getOrgByOrgCodeOrgName(userId,authLevel,orgCode,orgName);
	}
	
	
	/**
	 * 产品管理使用
	 * 行业模板树
	 * @param productId 产品id
	 * @return
	 */
	@RequestMapping("getTemplateTradeByTopOrg")
	public ReturnData<Map<String,Object>> getTemplateTradeByTopOrg(Integer productId){
		return Result.ok(organizationService.getTemplateTradeByTopOrg(productId));
	}

	
	/**
	 * 组织管理使用
	 * 查询产品-行业
	 * 以及本企业-行业 数据-树形结构
	 * @param productId 产品id
	 * @param orgCode 选择的组织code
	 * @return
	 */
	@RequestMapping("getTemplateTradeByProductAndOrg")
	public ReturnData<Map<String,Object>> getTemplateTradeByProductAndOrg(Integer productId,String orgCode){
		return Result.ok(organizationService.getTemplateTradeByProductAndOrg(productId,orgCode));
	}
	
	
	/**
	 * 组织管理使用
	 * 按组织查询
	 * 行业-模板权限 查询
	 * 查询上级企业/本企业的行业-模板树形数据
	 * @param orgCode
	 * @return
	 */
	@RequestMapping("getIndustrysByOrgId")
	public ReturnData<Map<String,Object>> getTemplateTradeByOrg(
			String parentCode,
			String targetOrgCode){
		return Result.ok(organizationService.getTemplateTradeByOrg(parentCode,targetOrgCode));
	}

	/**
	 * 查询 组织-角色 树
	 * @param orgCode
	 * @return
	 */
	@PostMapping("getAuthOrgTree")
	public ReturnData<List<OrgRoleInfo>> getAuthOrgTree(String orgName,
			@RequestHeader Long userId, @RequestHeader Integer authLevel, @RequestHeader String orgCode)
	{
		return Result.ok(organizationService.getAuthOrgTree(orgName, userId,authLevel,orgCode,true));
	}
	
	/**
	 * 没有数据权限控制的获取本组织及下级组织
	 */
	@RequestMapping("queryAllOrgByUserId")
	public ReturnData<List<OrgVO>> queryAllOrgByUserId(@RequestHeader Long userId) {
		List<OrgVO> organizationList = new ArrayList<>();
		SysOrganization organization = userService.getOrgByUserId(userId);
		List<Map> orgVOMap = organizationService.querySubOrgByOrgId(organization.getId());
		for (Map orgMap : orgVOMap) {
			OrgVO orgVo = new OrgVO();
			orgVo.setOrgId((Integer) orgMap.get("id"));
			orgVo.setOrgName((String) orgMap.get("name"));
			organizationList.add(orgVo);
		}
		return Result.ok(organizationList);
	}
	
	/**
	 * 根据数据权限控制获取本人，本组织及下级组织
	 */
	@RequestMapping("getAllOrgByUserId")
	public ReturnData<List<OrgVO>> getAllOrgByUserId(@RequestHeader Long userId, @RequestHeader Integer authLevel, @RequestHeader String orgCode) {
		List<OrgVO> organizationList = new ArrayList<>();
		List<Map> orgVOMap = organizationService.getSubOrgByAuthLevel(userId,authLevel,orgCode);
		for (Map orgMap : orgVOMap) {
			OrgVO orgVo = new OrgVO();
			orgVo.setOrgId((Integer) orgMap.get("id"));
			orgVo.setOrgName((String) orgMap.get("name"));
			orgVo.setOrgCode((String) orgMap.get("code"));
			organizationList.add(orgVo);
		}
		return Result.ok(organizationList);
	}

	@RequestMapping("getSubOrgIdByOrgId")
	public ReturnData<List<Integer>> getSubOrgIdByOrgId(Integer orgId) {
		return Result.ok(organizationService.getSubOrgIdByOrgId(orgId));
	}

	@RequestMapping("getAllOrgId")
	public ReturnData<List<Integer>> getAllOrgId() {
		return Result.ok(organizationService.getAllOrgId());
	}
	
	@RequestMapping("getOrgByUsername")
	public ReturnData<Map> getOrgByUsername(@RequestParam("username") String username)
	{
		logger.info("getOrgByUsername request userName:{}", username);
		return Result.ok(organizationService.getOrgByUsername(username));
	}
	
}
