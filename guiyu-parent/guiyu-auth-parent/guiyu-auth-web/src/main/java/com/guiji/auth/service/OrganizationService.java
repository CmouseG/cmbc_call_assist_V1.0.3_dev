package com.guiji.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.common.model.Page;
import com.guiji.user.dao.SysOrganizationMapper;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysOrganizationExample;

@Service
public class OrganizationService {

	@Autowired
	private SysOrganizationMapper sysOrganizationMapper;
	
	//代理商类型
	private static final String DLS_ORG="1";
	
	//企业类型
	private static final String QY_ORG="2";
	
	public void add(SysOrganization record){
		String type=record.getType();
		SysOrganizationExample example=new SysOrganizationExample();
		if(DLS_ORG.equals(type)){
			example.createCriteria().andTypeEqualTo(DLS_ORG);
			int code=sysOrganizationMapper.countByExample(example);
			record.setCode(String.valueOf(code+1));
		}else if(QY_ORG.equals(type)){
			example.createCriteria().andTypeEqualTo(QY_ORG).andCodeLike(record.getCode()+"%");
			int num=sysOrganizationMapper.countByExample(example);
			String code=record.getCode()+(num+1);
			record.setCode(code);
		}
		sysOrganizationMapper.insert(record);
	}
	
	public void delte(SysOrganization record){
		record.setDelFlag("1");
		sysOrganizationMapper.updateByPrimaryKeySelective(record);
	}
	
	public void update(SysOrganization record){
		sysOrganizationMapper.updateByPrimaryKeySelective(record);
	}
	
	public Page<SysOrganization> selectByPage(Page<SysOrganization> page){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andDelFlagEqualTo("0");
		int num=sysOrganizationMapper.countByExample(example);
		example.setLimitStart((page.getPageNo()-1)*page.getPageSize());
		example.setLimitEnd(page.getPageNo()*page.getPageSize());
		List<SysOrganization> list=sysOrganizationMapper.selectByExample(example);
		page.setTotal(num);
		page.setRecords(list);
		return page;
	}
	
	public List<SysOrganization> getOrgByType(String type){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andDelFlagEqualTo("0").andTypeEqualTo(type);
		return sysOrganizationMapper.selectByExample(example);
	}
}
