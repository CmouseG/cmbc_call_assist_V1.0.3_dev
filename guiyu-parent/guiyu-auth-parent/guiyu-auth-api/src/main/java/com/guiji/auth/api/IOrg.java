package com.guiji.auth.api;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.auth.model.OrgVO;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysOrganization;

@FeignClient("guiyu-auth-web")
public interface IOrg {
	
	@RequestMapping("/organization/getOrgByCode")
	public ReturnData<SysOrganization> getOrgByCode(@RequestParam("code") String code);

	@RequestMapping("/organization/getIndustryByOrgCode")
	public ReturnData<List<String>> getIndustryByOrgCode(@RequestParam("orgCode") String orgCode);
	
	@RequestMapping("/organization/getSubOrgIdByOrgId")
	public ReturnData<List<Integer>> getSubOrgIdByOrgId(@RequestParam("orgId") Integer orgId);
	
	@RequestMapping("/organization/getAllOrgId")
	public ReturnData<List<Integer>> getAllOrgId();
	
	@RequestMapping("/organization/queryAllOrgByUserId")
	public ReturnData<List<OrgVO>> queryAllOrgByUserId(@RequestParam("userId") Long userId);
	
	@RequestMapping("/organization/getOrgByUsername")
	public ReturnData<Map> getOrgByUsername(@RequestParam("username") String username);
}
