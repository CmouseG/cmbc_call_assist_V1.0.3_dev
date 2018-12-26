package com.guiji.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guiji.common.model.Page;
import com.guiji.user.dao.SysOrganizationMapper;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysOrganizationExample;
import com.guiji.user.dao.entity.SysOrganizationExample.Criteria;

@Service
public class OrganizationService {

	@Autowired
	private SysOrganizationMapper sysOrganizationMapper;
	
	public void add(SysOrganization record){
        String subCode = this.getSubOrgCode(record.getCode());
		/*int num=sysOrganizationMapper.countCode(record.getCode());
		String code=record.getCode()+"."+(num+1);*/
		record.setCode(subCode);
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
	
	public Page<Object> selectOpenByPage(Page<Object> page){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andDelFlagEqualTo("0").andOpenEqualTo("1");
		int num=sysOrganizationMapper.countByExample(example);
		List<Object> list=sysOrganizationMapper.selectOpenByPage(page);
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
	
	@RequestMapping("getOrgByUserId")
	public List<SysOrganization> getOrgByUserId(Long userId){
		return sysOrganizationMapper.getOrgByUserId(userId);
	}
	
	public boolean checkName(String name){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andNameEqualTo(name).andDelFlagEqualTo("0");
		int num=sysOrganizationMapper.countByExample(example);
		return num==0;
	}
	
	public boolean checkName(String name,Long id){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andNameEqualTo(name).andDelFlagEqualTo("0").andIdNotEqualTo(id);
		int num=sysOrganizationMapper.countByExample(example);
		return num==0;
	}
	
	public List<SysOrganization> getOrgNotOpen(){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andDelFlagEqualTo("0").andOpenEqualTo("0").andTypeNotEqualTo("1");
		return sysOrganizationMapper.selectByExample(example);
	}
	
	public boolean existChildren(SysOrganization record){
		return sysOrganizationMapper.existChildren(record);
	}
	
	public SysOrganization getOrgByCode(String code){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andDelFlagEqualTo("0").andCodeEqualTo(code);
		List<SysOrganization> list=sysOrganizationMapper.selectByExample(example);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public String getSubOrgCode(String orgCode) {
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
				subOrgCode = sysOrganization.getCode() + ".1";
			} else {
				String preOrgCode = sysOrganization.getSubCode().substring(0,sysOrganization.getSubCode().lastIndexOf("."));
				String lastOrgCode = sysOrganization.getSubCode().substring(sysOrganization.getSubCode().lastIndexOf(".")+1,sysOrganization.getSubCode().length());
				int lastOrgCodeNumber = Integer.valueOf(lastOrgCode) + 1;
				subOrgCode = preOrgCode + "." + lastOrgCodeNumber;
			}
			SysOrganization sysOrganizationUpdate = new SysOrganization();
			sysOrganizationUpdate.setId(sysOrganization.getId());
			sysOrganizationUpdate.setCode(orgCode);
			sysOrganizationUpdate.setSubCode(subOrgCode);
			sysOrganizationMapper.updateByPrimaryKeySelective(sysOrganizationUpdate);
		}

		return subOrgCode;
	}
}
