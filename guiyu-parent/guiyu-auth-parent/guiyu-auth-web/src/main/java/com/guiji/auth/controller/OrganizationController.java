package com.guiji.auth.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.service.OrganizationService;
import com.guiji.common.model.Page;
import com.guiji.user.dao.entity.SysOrganization;

@RestController
@RequestMapping("organization")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;
	
	
	@RequestMapping("add")
	public SysOrganization add(SysOrganization record,@RequestHeader long userId){
		record.setCreateId(userId);
		record.setUpdateId(userId);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		organizationService.add(record);
		return record;
	}
	
	@RequestMapping("delete")
	public void delte(SysOrganization record,@RequestHeader long userId){
		record.setUpdateId(userId);
		record.setUpdateTime(new Date());
		organizationService.delte(record);
	}
	
	@RequestMapping("update")
	public void update(SysOrganization record,@RequestHeader long userId){
		record.setUpdateId(userId);
		record.setUpdateTime(new Date());
		organizationService.update(record);
	}
	
	@RequestMapping("selectByPage")
	public Page<Object> selectByPage(Page<Object> page){
		return organizationService.selectByPage(page);
	}
	
	/**
	 * 获取全部的代理
	 * @param type
	 * @return
	 */
	@RequestMapping("getOrgByType")
	public List<SysOrganization> getOrgByType(String type){
		return organizationService.getOrgByType(type);
	}
	
	@RequestMapping("getOrgByUserId")
	public List<SysOrganization> getOrgByUserId(@RequestHeader Long userId){
		return organizationService.getOrgByUserId(userId);
	}
	
	@RequestMapping("getAdminOrgByUserId")
	public List<SysOrganization> getAdminOrgByUserId(Long userId){
		return organizationService.getOrgByUserId(userId);
	}
}
