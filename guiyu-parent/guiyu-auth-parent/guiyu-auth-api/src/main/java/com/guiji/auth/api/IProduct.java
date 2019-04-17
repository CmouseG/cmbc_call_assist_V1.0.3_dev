package com.guiji.auth.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.guiji.auth.model.ProductTemplatesVO;

@FeignClient("guiyu-auth-web")
public interface IProduct
{
	/**
	 * 保存创建的模版到关联关系（privilege）
	 */
	@PostMapping("/product/saveProductTemplates")
	public void saveProductTemplates(@RequestBody ProductTemplatesVO productTemplates);
}
