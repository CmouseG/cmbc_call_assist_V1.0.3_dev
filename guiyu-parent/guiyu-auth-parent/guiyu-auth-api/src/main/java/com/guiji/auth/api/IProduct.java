package com.guiji.auth.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("guiyu-auth-web")
public interface IProduct
{
	/**
	 * 保存创建的模版到关联关系（privilege）
	 */
	@RequestMapping("/product/saveProductTemplates")
	public void saveProductTemplates(@RequestParam("templateIds") List<String> templateIds);
}
