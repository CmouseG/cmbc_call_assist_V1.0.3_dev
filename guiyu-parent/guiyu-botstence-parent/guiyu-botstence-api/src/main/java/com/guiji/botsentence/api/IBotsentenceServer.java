package com.guiji.botsentence.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.botsentence.api.entity.BotSentenceTemplateIndustryVO;
import com.guiji.botsentence.api.entity.ServerResult;

@FeignClient("guiyu-botstence-web")
public interface IBotsentenceServer {
	
	/**
	 * 如果accountNo为空，则表示查询所有可绑定话术
	 * @param accountNo
	 * @param host
	 * @return
	 */
	@RequestMapping(value = "/botsentenceServer/getIndustryList")
	public ServerResult<List<BotSentenceTemplateIndustryVO>> getIndustryList(@RequestParam("accountNo") String accountNo,@RequestParam("host")String host);
	
}
