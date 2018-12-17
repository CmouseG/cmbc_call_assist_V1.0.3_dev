package com.guiji.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.guiji.common.model.Page;
import com.guiji.user.dao.SysOrganizationMapper;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysOrganizationExample;

@Service
public class OrganizationService {

	@Autowired
	private SysOrganizationMapper sysOrganizationMapper;
	
	public void add(SysOrganization record){
		String type=record.getType();
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andCodeLike(record.getCode()+"._");
		int num=sysOrganizationMapper.countByExample(example);
		String code=record.getCode()+"."+(num+1);
		record.setCode(code);
		sysOrganizationMapper.insert(record);
	}
	
	public void delte(SysOrganization record){
		record.setDelFlag("1");
		sysOrganizationMapper.updateByPrimaryKeySelective(record);
	}
	
	public void update(SysOrganization record){
		sysOrganizationMapper.updateByPrimaryKeySelective(record);
	}
	
	public Page<Object> selectByPage(Page<Object> page){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andDelFlagEqualTo("0");
		int num=sysOrganizationMapper.countByExample(example);
		List<Object> list=sysOrganizationMapper.selectByPage(page);
		page.setTotal(num);
		page.setRecords(list);
		return page;
	}
	
	public List<SysOrganization> getOrgByType(String type){
		SysOrganizationExample example=new SysOrganizationExample();
		if(StringUtils.isEmpty(type)){
			example.createCriteria().andDelFlagEqualTo("0");
		}else{
			example.createCriteria().andDelFlagEqualTo("0").andTypeEqualTo(type);
		}
		return sysOrganizationMapper.selectByExample(example);
	}
}
