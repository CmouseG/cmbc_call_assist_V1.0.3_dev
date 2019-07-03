package com.guiji.botsentence.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.botsentence.api.entity.BotSentenceProcess;
import com.guiji.botsentence.api.entity.BotSentenceProcessVO;
import com.guiji.botsentence.api.entity.ServerResult;

@FeignClient("guiyu-botstence-web")
public interface IBotSentenceProcess {
	
	@RequestMapping(value="/botSentenceProcess/createBotSentenceProcess")
	public ServerResult<String> createBotSentenceProcess(@RequestParam("paramVO")BotSentenceProcessVO paramVO,@RequestParam("userId") Long userId);
	
	@RequestMapping(value="/botSentenceProcess/getTemplateById")
	public ServerResult<List<BotSentenceProcess>> getTemplateById(@RequestParam("templateId")String templateId);

	@RequestMapping(value="/botSentenceProcess/getTemplateByIds")
	ServerResult<List<BotSentenceProcess>> getTemplateByIds(@RequestParam("templateIds") List<String> templateIds);

	@RequestMapping(value="/available/countTemplateByOrgCode")
	public ServerResult<Integer> countTemplateByOrgCode(@RequestParam("orgCode")String orgCode);
}
