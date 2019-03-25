package com.guiji.auth.controller;

import com.guiji.auth.api.IOrg;
import com.guiji.auth.exception.CheckConditionException;
import com.guiji.auth.model.OrgVO;
import com.guiji.auth.service.OrganizationService;
import com.guiji.auth.service.UserService;
import com.guiji.botsentence.api.entity.BotSentenceTemplateTradeVO;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("organization")
public class OrganizationController implements IOrg{

	@Autowired
	private UserService userService;
	@Autowired
	private OrganizationService organizationService;

	@RequestMapping("add")
	public SysOrganization add(SysOrganization record,@RequestHeader long userId) throws CheckConditionException{
		if(!organizationService.checkName(record.getName())){
			throw new CheckConditionException("00010009");
		}
		record.setOpen(0);
		record.setDelFlag(0);
		record.setCreateId(userId);
		record.setUpdateId(userId);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		organizationService.add(record);
		return record;
	}
	
	@RequestMapping("delete")
	public void delte(SysOrganization record,@RequestHeader long userId) throws CheckConditionException{
		if(organizationService.existChildren(record)){
			throw new CheckConditionException("00010010");
		}
		record.setUpdateId(userId);
		record.setUpdateTime(new Date());
		organizationService.delte(record);
	}
	
	@RequestMapping("update")
	public void update(SysOrganization record,@RequestHeader long userId) throws CheckConditionException{
		if(!StringUtils.isEmpty(record.getName())){
			if(!organizationService.checkName(record.getName(),record.getId())){
				throw new CheckConditionException("00010009");
			}
		}
		record.setUpdateId(userId);
		record.setUpdateTime(new Date());
		organizationService.update(record);
	}
	
	@RequestMapping("selectByPage")
	public Page<Object> selectByPage(Page<Object> page){
		return organizationService.selectByPage(page);
	}
	
	@RequestMapping("selectOpenByPage")
	public Page<Map> selectOpenByPage(Page<Map> page){
		return organizationService.selectOpenByPage(page);
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
	 * 获取未开户
	 * @return
	 */
	@RequestMapping("getOrgNotOpen")
	public List<SysOrganization> getOrgNotOpen(){
		return organizationService.getOrgNotOpen();
	}
	
	@RequestMapping("getOrgByUserId")
	public List<SysOrganization> getOrgByUserId(@RequestHeader Long userId){
		return organizationService.getOrgByUserId(userId);
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

	@RequestMapping("getIndustryByOrganizationId")
	public List<String> getIndustryByOrganizationId(Long organizationId){
		return organizationService.getIndustryByOrganizationId(organizationId);
	}

	@RequestMapping("getIndustryByOrgCode")
	public ReturnData<List<String>> getIndustryByOrgCode(String orgCode){
		return Result.ok(organizationService.getIndustryByOrgCode(orgCode));
	}

	@RequestMapping("getOrgByOrgCodeOrgName")
	public List<SysOrganization> getOrgByOrgCodeOrgName(String orgCode,String orgName){
		return organizationService.getOrgByOrgCodeOrgName(orgCode,orgName);
	}

	/**
	 * 前台使用
	 */
	@RequestMapping("getIndustrysByOrgCode")
	public ReturnData<List<BotSentenceTemplateTradeVO>> getIndustrysByOrgCode(String orgCode){
		return Result.ok(organizationService.getIndustrysByOrgCode(orgCode));
	}

	@RequestMapping("getSubOrgIdByOrgId")
	public ReturnData<List<Integer>> getSubOrgIdByOrgId(Integer orgId) {
		return Result.ok(organizationService.getSubOrgIdByOrgId(orgId));
	}

	@RequestMapping("getAllOrgId")
	public ReturnData<List<Integer>> getAllOrgId() {
		return Result.ok(organizationService.getAllOrgId());
	}

	@RequestMapping("queryAllOrgByUserId")
	public ReturnData<List<OrgVO>> queryAllOrgByUserId(@RequestHeader Long userId) {
		List<OrgVO> organizationList = new ArrayList<>();
		SysOrganization organization = userService.getOrgByUserId(userId);
		List<Map> orgVOMap = organizationService.querySubOrgByOrgId(organization.getId().intValue());
		for (Map orgMap : orgVOMap) {
			OrgVO orgVo = new OrgVO();
			orgVo.setOrgId((Integer) orgMap.get("id"));
			orgVo.setOrgName((String) orgMap.get("name"));
			organizationList.add(orgVo);
		}

		OrgVO orgVo = new OrgVO();
		orgVo.setOrgId(organization.getId().intValue());
		orgVo.setOrgName(organization.getName());
		organizationList.add(orgVo);
		return Result.ok(organizationList);
	}
	
}
