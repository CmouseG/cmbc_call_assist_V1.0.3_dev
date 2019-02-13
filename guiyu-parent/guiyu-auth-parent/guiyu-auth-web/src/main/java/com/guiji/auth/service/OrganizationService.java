package com.guiji.auth.service;

import java.util.List;

import com.guiji.component.result.Result;
import com.guiji.notice.api.INoticeSetting;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.UserAiCfgBaseInfoVO;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.RedisUtil;
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
	@Autowired
	private SysUserMapper sysUsermapper;
	@Autowired
	private IRobotRemote iRobotRemote;
	@Autowired
	private INoticeSetting noticeSetting;
	@Autowired
	private RedisUtil redisUtil;
	private static final String REDIS_ORG_BY_USERID = "REDIS_ORG_BY_USERID_";
	private static final String REDIS_ORG_BY_CODE = "REDIS_ORG_BY_CODE_";

	public void add(SysOrganization record){
        String subCode = this.getSubOrgCode(record.getCode());
		/*int num=sysOrganizationMapper.countCode(record.getCode());
		String code=record.getCode()+"."+(num+1);*/
		record.setCode(subCode);
		sysOrganizationMapper.insert(record);
		sysOrganizationMapper.insertOrganizationProduct(record.getId(),record.getCreateId(),record.getProduct());
		sysOrganizationMapper.insertOrganizationIndustry(record.getId(),record.getCode(),record.getCreateId(),record.getIndustryIds());
		noticeSetting.addNoticeSetting(record.getCode());
	}
	
	public void delte(SysOrganization record){
		record.setDelFlag(1);
		sysOrganizationMapper.updateByPrimaryKeySelective(record);
		redisUtil.del(REDIS_ORG_BY_CODE+record.getCode());
        redisUtil.delVague(REDIS_ORG_BY_USERID);
	}
	
	public void update(SysOrganization record){
		sysOrganizationMapper.updateByPrimaryKeySelective(record);
		if (record != null && record.getProduct() != null && !record.getProduct().isEmpty()) {
			sysOrganizationMapper.updateOrganizationProduct(record.getId(),record.getUpdateId(),record.getProduct());
		}

		if (record != null && record.getIndustryIds() != null && !record.getIndustryIds().isEmpty()) {
			sysOrganizationMapper.updateOrganizationIndustry(record.getId(),record.getCode(),record.getUpdateId(),record.getIndustryIds());
		}
		redisUtil.del(REDIS_ORG_BY_CODE+record.getCode());
		redisUtil.delVague(REDIS_ORG_BY_USERID);
	}
	
	public Page<Object> selectByPage(Page<Object> page){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andDelFlagEqualTo(0);
		int num=sysOrganizationMapper.countByExample(example);
		List<Object> list=sysOrganizationMapper.selectByPage(page);
		page.setTotal(num);
		page.setRecords(list);
		return page;
	}
	
	public Page<Object> selectOpenByPage(Page<Object> page){
		SysOrganizationExample example=new SysOrganizationExample();
		example.createCriteria().andDelFlagEqualTo(0).andOpenEqualTo(1);
		int num=sysOrganizationMapper.countByExample(example);
		List<Object> list=sysOrganizationMapper.selectOpenByPage(page);
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
		example.createCriteria().andNameEqualTo(name).andDelFlagEqualTo(0).andIdNotEqualTo(id);
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
            List<Integer> product = sysOrganizationMapper.getProductByOrganizationId(sysOrganization.getId());
			sysOrganization.setProduct(product);
			redisUtil.set(REDIS_ORG_BY_CODE+code,sysOrganization);
		}
		return sysOrganization;
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

	public List<String> getIndustryByOrganizationId(Long organizationId) {
		return sysOrganizationMapper.getIndustryByOrganizationId(organizationId);
	}

	public List<String> getIndustryByOrgCode(String orgCode) {
		return sysOrganizationMapper.getIndustryByOrgCode(orgCode);
	}

	public List<SysOrganization> getOrgByOrgCodeOrgName(String orgCode,String orgName){
		return sysOrganizationMapper.getOrgByOrgCodeOrgName(orgCode,orgName);
	}

}
