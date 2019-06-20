package com.guiji.auth.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.common.exception.GuiyuException;
import com.guiji.user.dao.SysBusinessMapper;
import com.guiji.user.dao.SysOrganizationMapper;
import com.guiji.user.dao.entity.SysBusiness;
import com.guiji.user.dao.entity.SysBusinessExample;
import com.guiji.user.dao.entity.SysBusinessExample.Criteria;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysOrganizationExample;

@Service
public class BussinessService
{
	@Autowired
	private SysBusinessMapper businessMapper;
	@Autowired
	private SysOrganizationMapper organizationMapper;

	/**
	 * 新增商务
	 */
	public void addBussiness(String businessName, String businessEmail, Long userId, String orgCode)
	{
		if(StringUtils.isEmpty(businessName) || StringUtils.isEmpty(businessEmail)){
			throw new GuiyuException("商务名称或商务邮箱不能为空！");
		}
		SysBusinessExample example = new SysBusinessExample();
		example.createCriteria().andBusinessEmailEqualTo(businessEmail);
		List<SysBusiness> businesses = businessMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(businesses)){
			throw new GuiyuException("此邮箱已存在！");
		}
		SysBusiness business = new SysBusiness();
		business.setBusinessName(businessName);
		business.setBusinessEmail(businessEmail);
		business.setOrgCode(orgCode);
		business.setCreateId(userId.intValue());
		business.setCreateTime(new Date());
		businessMapper.insert(business);
	}
	
	/**
	 * 查询商务列表
	 */
	public List<SysBusiness> queryBusinessList(Long userId, String orgCode, Integer authLevel)
	{
		SysBusinessExample example = new SysBusinessExample();
		Criteria criteria = example.createCriteria();
		if(authLevel == 1) {
			criteria.andCreateIdEqualTo(userId.intValue());
		} else if(authLevel == 2) {
			criteria.andOrgCodeEqualTo(orgCode);
		}else if(authLevel == 3) {
			criteria.andOrgCodeLike(orgCode + "%");
		}
		return businessMapper.selectByExample(example);
	}
	
	/**
	 * 删除商务
	 */
	public void delBusiness(Integer id)
	{
		SysOrganizationExample example = new SysOrganizationExample();
		example.createCriteria().andBussinessIdEqualTo(id);
		List<SysOrganization> organizations = organizationMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(organizations)){
			throw new GuiyuException("您的商务邮箱已绑定组织，无法删除");
		}
		businessMapper.deleteByPrimaryKey(id);
	}
	
}
