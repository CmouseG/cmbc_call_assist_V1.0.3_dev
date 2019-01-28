package com.guiji.botsentence.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.botsentence.api.entity.BotSentenceIndustryVO;
import com.guiji.botsentence.api.entity.ServerResult;

@FeignClient("guiyu-botstence-web")
public interface IBotSentenceTemplate {
	
	/**
	 * 查询行业模板接口
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/botSentenceTemplate/queryIndustryTemplate")
	public ServerResult<List<BotSentenceIndustryVO>> queryIndustryTemplate(@RequestParam("userId")Long userId);
	
	
	/**
	 * 根据账号查询行业模板树型结构
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/botSentenceProcess/queryIndustryListByAccountNo")
	public ServerResult<List<BotSentenceIndustryVO>> queryIndustryListByAccountNo(@RequestParam("userId")Long userId);
	
	
}
