package com.guiji.auth.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysOrganization;

import java.util.List;

@FeignClient("guiyu-auth-web")
public interface IOrg {
	
	@RequestMapping("/organization/getOrgByCode")
	public ReturnData<SysOrganization> getOrgByCode(@RequestParam("code") String code);

	@RequestMapping("/organization/getIndustryByOrgCode")
	public ReturnData<List<String>> getIndustryByOrgCode(@RequestParam("orgCode") String orgCode);
}
